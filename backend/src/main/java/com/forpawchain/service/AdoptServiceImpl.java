package com.forpawchain.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.domain.Entity.AdoptEntity;
import com.forpawchain.domain.Entity.PetEntity;
import com.forpawchain.domain.Entity.UserEntity;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.repository.AdoptRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;
import com.google.cloud.storage.Blob;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AdoptServiceImpl implements AdoptService {

	private final AdoptRepository adoptRepository;
	private final UserRepository userRepository;
	private final PetRepository petRepository;
	private final GCSService gcsService;

	@Override
	public PageImpl<AdoptListResDto> getAdoptList(int pageNo, String type, Integer spayed, String sex) {
		PageRequest pageRequest = PageRequest.of(pageNo, 10);
		PageImpl<AdoptListResDto> adoptListResDtos = null;

		// 중성화여부가 null 일 때
		if (spayed == null) {
			adoptListResDtos = adoptRepository.findByTypeAndSex(type, sex, pageRequest);
		}
		// 중성화여부 조건 검색을 할 때
		else {
			adoptListResDtos = adoptRepository.findByTypeAndSexAndSpayed(type, sex, spayed, pageRequest);
		}

		return adoptListResDtos;
	}

	@Override
	public List<AdoptListResDto> getAdoptAd() {
		List<AdoptListResDto> list = adoptRepository.findTop10ByRand();
		return list;
	}

	@Override
	public AdoptDetailResDto getAdoptDetail(String pid) {
		AdoptDetailResDto adoptDetailResDto = adoptRepository.findDetailByPid(pid);

		//존재하지 않는 pid 이거나, 해당 pid에 쓰인 입양 공고가 없을 경우
		if (adoptDetailResDto == null) {
			throw new BaseException(ErrorMessage.NOT_EXIST_CONTENT);
		}

		return adoptDetailResDto;
	}

	@Override
	public void registAdopt(AdoptDetailReqDto adoptDetailReqDto, long uid, MultipartFile imageFile) throws IOException {

		String pid = adoptDetailReqDto.getPid();
		AdoptEntity adoptEntity = adoptRepository.findByPid(pid);

		//이미 입양 공고글이 작성되어 있을 경우
		if (adoptEntity != null) {
			throw new BaseException(ErrorMessage.EXIST_CONTENT);
		}

		PetEntity petEntity = petRepository.findByPid(pid);
		UserEntity userEntity = userRepository.findByUid(uid);

		//존재하지 않는 pid 이거나 uid
		if (petEntity == null || userEntity == null) {
			throw new BaseException(ErrorMessage.NOT_EXIST_CONTENT);
		}

		//파일 업로드
		Blob blob = gcsService.uploadFileToGCS(imageFile);
		String imageUrl = blob.getMediaLink();

		// 분양 공고 추가
		adoptEntity = AdoptEntity.builder()
			.pid(adoptDetailReqDto.getPid())
			.uid(uid)
			.profile(imageUrl)
			.tel(adoptDetailReqDto.getTel())
			.etc(adoptDetailReqDto.getEtc())
			.pet(petEntity)
			.user(userEntity)
			.build();

		adoptRepository.save(adoptEntity);
	}

	@Override
	public void modifyAdopt(AdoptDetailReqDto adoptDetailReqDto, long uid, MultipartFile imageFile) throws IOException {
		String pid = adoptDetailReqDto.getPid();
		AdoptEntity adoptEntity = adoptRepository.findByPid(pid);

		//글이 존재해야 수정할 수 있다.
		if (adoptEntity == null) {
			throw new BaseException(ErrorMessage.NOT_EXIST_CONTENT);
		}

		//글을 쓴 본인만이 수정할 수 있다.
		if (adoptEntity.getUid() != uid) {
			throw new BaseException(ErrorMessage.NOT_PERMISSION_EXCEPTION);
		}

		//파일 업로드
		Blob blob = gcsService.uploadFileToGCS(imageFile);
		String imageUrl = blob.getMediaLink();

		String etc = adoptDetailReqDto.getEtc();
		String tel = adoptDetailReqDto.getTel();

		adoptEntity.updateAdopt(imageUrl, etc, tel);
	}

	@Override
	public void removeAdopt(String pid, long uid) {

		AdoptEntity adoptEntity = adoptRepository.findByPid(pid);

		//글이 존재해야 삭제할 수 있다.
		if (adoptEntity == null) {
			throw new BaseException(ErrorMessage.NOT_EXIST_CONTENT);
		}

		//글을 쓴 본인만이 수정할 수 있다.
		if (adoptEntity.getUid() != uid) {
			throw new BaseException(ErrorMessage.NOT_PERMISSION_EXCEPTION);
		}

		// Pet의 lost 여부 변경
		PetEntity petEntity = petRepository.findByPid(pid);
		petEntity.updatePetLost(false);
		petRepository.save(petEntity);

		// 분양 공고 삭제
		adoptRepository.deleteByPid(pid);
	}

	@Override
	public List<AdoptListResDto> getAdoptMyList(Long uid) {

		UserEntity userEntity = userRepository.findByUid(uid);

		//존재하지 않는 유저일 경우
		if (userEntity == null || userEntity.isDel()) {
			throw new BaseException(ErrorMessage.NOT_USER_INFO);
		}

		List<AdoptListResDto> list = adoptRepository.findByUid(uid);
		return list;
	}
}
