package com.forpawchain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.repository.AdoptRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdoptServiceImpl implements AdoptService {

	// private final AdoptRepository adoptRepository;
	//
	@Override
	public List<AdoptListResDto>  getAdoptList(int pageNo, int type, int kind, int sex) {
		//adoptRepository.
		return null;
	}

	@Override
	public List<AdoptListResDto>  getAdoptAd() {
		return null;
	}

	@Override
	public AdoptDetailResDto getAdoptDetail(String pid) {
		return null;
	}

	@Override
	public void registAdopt(AdoptDetailReqDto adoptDetailReqDto) {

	}

	@Override
	public void modifyAdopt(AdoptDetailReqDto adoptDetailReqDto) {

	}

	@Override
	public void removeAdopt(String pid) {

	}

	@Override
	public List<AdoptListResDto> getAdoptMyList(String uid) {
		return null;
	}
}
