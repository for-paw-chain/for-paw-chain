package com.forpawchain.service;

import java.util.List;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.domain.entity.AdoptEntity;
import com.forpawchain.domain.entity.PetEntity;
import com.forpawchain.domain.entity.UserEntity;
import com.forpawchain.repository.AdoptRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdoptServiceImpl implements AdoptService {

	private final AdoptRepository adoptRepository;
	private final UserRepository userRepository;
	private final PetRepository petRepository;

	@Override
	public List<AdoptListResDto> getAdoptList(int pageNo, int type, int kind, int sex) {
		//adoptRepository.
		return null;
	}

	@Override
	public List<AdoptListResDto> getAdoptAd() {
		List<AdoptListResDto> list = adoptRepository.findTop10ByRand();
		return list;
	}

	@Override
	public AdoptDetailResDto getAdoptDetail(String pid) {
		return null;
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
			.profile1(adoptDetailReqDto.getProfile1())
			.profile2(adoptDetailReqDto.getProfile2())
			.tel(adoptDetailReqDto.getTel())
			.etc(adoptDetailReqDto.getEtc())
			.pet(petEntity)
			.user(userEntity)
			.build();

		if (adoptDetailReqDto.getProfile2() != null) {
			adoptEntity.setProfile2(adoptDetailReqDto.getProfile2());
		}

		adoptRepository.save(adoptEntity);
	}

	@Override
	public void modifyAdopt(AdoptDetailReqDto adoptDetailReqDto) {

	}

	@Override
	public void removeAdopt(String pid) {
		adoptRepository.deleteByPid(pid);
	}

	@Override
	public List<AdoptListResDto> getAdoptMyList(Long uid) {
		List<AdoptListResDto> list = adoptRepository.findByUid(uid);
		return list;
	}
}
