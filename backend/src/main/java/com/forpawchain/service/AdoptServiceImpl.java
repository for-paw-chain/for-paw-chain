package com.forpawchain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.domain.Entity.AdoptEntity;
import com.forpawchain.domain.Entity.PetEntity;
import com.forpawchain.domain.Entity.UserEntity;
import com.forpawchain.repository.AdoptRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AdoptServiceImpl implements AdoptService {

	private final AdoptRepository adoptRepository;
	private final UserRepository userRepository;
	private final PetRepository petRepository;

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

		return adoptDetailResDto;
	}

	@Override
	public void registAdopt(AdoptDetailReqDto adoptDetailReqDto, Long uid) {

		String pid = adoptDetailReqDto.getPid();
		PetEntity petEntity = petRepository.findByPid(pid);
		UserEntity userEntity = userRepository.findByUid(uid);

		// 유기견 추가
		AdoptEntity adoptEntity = AdoptEntity.builder()
			.pid(adoptDetailReqDto.getPid())
			.uid(uid)
			.profile(adoptDetailReqDto.getProfile())
			.tel(adoptDetailReqDto.getTel())
			.etc(adoptDetailReqDto.getEtc())
			.pet(petEntity)
			.user(userEntity)
			.build();

		adoptRepository.save(adoptEntity);
	}

	@Override
	public void modifyAdopt(AdoptDetailReqDto adoptDetailReqDto) {
		String pid = adoptDetailReqDto.getPid();
		AdoptEntity adoptEntity = adoptRepository.findByPid(pid);

		String profile = adoptDetailReqDto.getProfile();
		String etc = adoptDetailReqDto.getEtc();
		String tel = adoptDetailReqDto.getTel();

		adoptEntity.updateAdopt(profile, etc, tel);
	}

	@Override
	public void removeAdopt(String pid) {
		// Pet의 lost 여부 변경
		PetEntity petEntity = petRepository.findByPid(pid);
		petEntity.updatePetLost(false);
		petRepository.save(petEntity);

		// 분양 공고 삭제
		adoptRepository.deleteByPid(pid);
	}

	@Override
	public List<AdoptListResDto> getAdoptMyList(Long uid) {
		List<AdoptListResDto> list = adoptRepository.findByUid(uid);
		return list;
	}
}
