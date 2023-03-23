package com.forpawchain.service;

import java.util.List;

import javax.transaction.Transactional;

import com.forpawchain.domain.Entity.AuthenticationType;
import org.springframework.stereotype.Service;

import com.forpawchain.domain.dto.request.RegistPetInfoReqDto;
import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.domain.dto.response.PetInfoResDto;
import com.forpawchain.domain.Entity.PetEntity;
import com.forpawchain.domain.Entity.PetInfoEntity;
import com.forpawchain.repository.AuthenticationRepository;
import com.forpawchain.repository.PetInfoRepository;
import com.forpawchain.repository.PetRegRepository;
import com.forpawchain.repository.PetRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {
	private final PetRepository petRepository;
	private final PetInfoRepository petInfoRepository;
	private final PetRegRepository petRegRepository;
	private final AuthenticationRepository authenticationRepository;

	@Override
	public List<PetDefaultInfoResDto> getMyPetList(Long userId) {
		List<PetDefaultInfoResDto> myPetList = petRegRepository.findAuthAndInfo(userId);
		return myPetList;
	}

	@Override
	@Transactional
	public void registPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto) {
		// 견적사항 등록 권한 체크
		AuthenticationType type = authenticationRepository.findTypeByAuthIdUidAndAuthIdPid(userId, registPetInfoReqDto.getPid());
		if (!AuthenticationType.MASTER.equals(type)) {
			// 에러
		}

		// 이미 등록되어 있는지 체크
		PetInfoEntity petInfoEntity = petInfoRepository.findByPid(registPetInfoReqDto.getPid());
		if (petInfoEntity != null) {
			// 에러
		}

		// 등록하기
		petInfoRepository.save(registPetInfoReqDto.toEntity());
	}

	@Override
	@Transactional
	public void modifyPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto) {
		// 견적사항 등록 권한 체크
		AuthenticationType type = authenticationRepository.findTypeByAuthIdUidAndAuthIdPid(userId, registPetInfoReqDto.getPid());
		if (!AuthenticationType.MASTER.equals(type)) {
			// 에러
		}

		// 수정하기
		petInfoRepository.save(registPetInfoReqDto.toEntity());
	}

	@Override
	public PetInfoResDto getPetInfo(String pid) {
		// Pet이 등록되어 있지 않는 경우, DB 저장
		if (petRepository.findByPid(pid) == null && petRegRepository.findByPid(pid) != null) {
			PetEntity petEntity = PetEntity.builder().pid(pid).build();
			petRepository.save(petEntity);
		}

		PetInfoResDto petInfoResDto = petRegRepository.findRegAndInfoByPid(pid);
		return petInfoResDto;
	}
}
