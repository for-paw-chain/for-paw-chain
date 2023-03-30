package com.forpawchain.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forpawchain.domain.dto.request.RegistPetInfoReqDto;
import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.domain.dto.response.PetInfoResDto;
import com.forpawchain.domain.Entity.PetEntity;
import com.forpawchain.domain.Entity.PetInfoEntity;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.repository.AuthenticationRepository;
import com.forpawchain.repository.PetInfoRepository;
import com.forpawchain.repository.PetRegRepository;
import com.forpawchain.repository.PetRepository;
import com.google.cloud.storage.Blob;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {
	private final PetRepository petRepository;
	private final PetInfoRepository petInfoRepository;
	private final PetRegRepository petRegRepository;
	private final AuthenticationRepository authenticationRepository;
	private final GCSService gcsService;

	/**
	 * 나의 반려동물 목록 조회
	 * @param userId
	 * @return List<PetDefaultInfoResDto>
	 */
	@Override
	public List<PetDefaultInfoResDto> getMyPetList(Long userId) {
		List<PetDefaultInfoResDto> myPetList = petRegRepository.findAuthAndInfo(userId);

		// 반려동물이 없는 경우
		if (myPetList.size() == 0) {
			throw new BaseException(ErrorMessage.PETLIST_NOT_FOUND);
		}

		return myPetList;
	}

	/**
	 * 견적사항 등록
	 * @param userId
	 * @param registPetInfoReqDto
	 * @param image
	 */
	@Override
	@Transactional
	public void registPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto, MultipartFile image) throws
		IOException {
		// 견적사항 등록 권한 체크 (주인만 가능)
		String type = authenticationRepository.findTypeByAuthIdUidAndAuthIdPid(userId, registPetInfoReqDto.getPid());
		if (type == null || !type.equals("MASTER")){
			throw new BaseException(ErrorMessage.NOT_PERMISSION_EXCEPTION);
		}

		// 이미 등록되어 있는지 체크
		PetInfoEntity petInfoEntity = petInfoRepository.findByPid(registPetInfoReqDto.getPid())
			.orElseThrow(() -> new BaseException(ErrorMessage.EXIST_CONTENT));

		// 모든 내용을 null이나 공백만 사용하여 등록하는 경우
		if ((registPetInfoReqDto.getEtc() == null || registPetInfoReqDto.getEtc().stripLeading().stripTrailing().equals(""))
			&& (registPetInfoReqDto.getTel() == null || registPetInfoReqDto.getTel().stripLeading().stripTrailing().equals(""))
			&& (registPetInfoReqDto.getRegion() == null || registPetInfoReqDto.getRegion().stripLeading().stripTrailing().equals(""))
			&& (registPetInfoReqDto.getBirth() == null || registPetInfoReqDto.getBirth() == null)
			&& image == null){
			throw new BaseException(ErrorMessage.NOT_EXIST_REGISTCONTENT);
		}

		// 이미지 저장
		if (image != null){
			Blob blob = gcsService.uploadFileToGCS(image);
			String imageUrl = blob.getMediaLink();
			petInfoRepository.save(registPetInfoReqDto.toEntityAndImage(imageUrl));
		} else {
			petInfoRepository.save(registPetInfoReqDto.toEntity());
		}
	}

	/**
	 * 동물의 견적사항 수정
	 * @param userId
	 * @param registPetInfoReqDto
	 * @param image
	 */
	@Override
	@Transactional
	public void modifyPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto, MultipartFile image) throws
		IOException {
		// 견적사항 등록 권한 체크 (주인만 가능)
		String type = authenticationRepository.findTypeByAuthIdUidAndAuthIdPid(userId, registPetInfoReqDto.getPid());
		if (type == null || !type.equals("MASTER")){
			throw new BaseException(ErrorMessage.NOT_PERMISSION_EXCEPTION);
		}

		// 모든 내용을 null이나 공백만 사용하여 등록하는 경우
		if ((registPetInfoReqDto.getEtc() == null || registPetInfoReqDto.getEtc().stripLeading().stripTrailing().equals(""))
			&& (registPetInfoReqDto.getTel() == null || registPetInfoReqDto.getTel().stripLeading().stripTrailing().equals(""))
			&& (registPetInfoReqDto.getRegion() == null || registPetInfoReqDto.getRegion().stripLeading().stripTrailing().equals(""))
			&& (registPetInfoReqDto.getBirth() == null || registPetInfoReqDto.getBirth() == null)
			&& image == null){
			throw new BaseException(ErrorMessage.NOT_EXIST_REGISTCONTENT);
		}

		// 이미지 저장
		if (image != null){
			Blob blob = gcsService.uploadFileToGCS(image);
			String imageUrl = blob.getMediaLink();
			petInfoRepository.save(registPetInfoReqDto.toEntityAndImage(imageUrl));
		} else {
			petInfoRepository.save(registPetInfoReqDto.toEntity());
		}
	}

	/**
	 * 동물의 기본 정보 및 견적사항 조회
	 * @param pid
	 * @return PetInfoResDto
	 */
	@Override
	public PetInfoResDto getPetInfo(String pid) {
		// PetRegEntity에는 Pet이 있지만, PetEntity에 동물 정보가 없는 경우, PetEntity 저장
		if (petRepository.findByPid(pid) == null && petRegRepository.findByPid(pid) != null) {
			PetEntity petEntity = PetEntity.builder().pid(pid).build();
			petRepository.save(petEntity);
		}

		PetInfoResDto petInfoResDto = petRegRepository.findRegAndInfoByPid(pid);
		return petInfoResDto;
	}
}
