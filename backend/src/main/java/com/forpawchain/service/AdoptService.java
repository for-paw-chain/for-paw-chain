package com.forpawchain.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;

public interface AdoptService {

	Page<AdoptListResDto> getAdoptList(int pageNo, String type, Integer spayed, String sex);

	List<AdoptListResDto> getAdoptAd();

	AdoptDetailResDto getAdoptDetail(String pid);

	void registAdopt(AdoptDetailReqDto adoptDetailReqDto, Long uid);

	void modifyAdopt(AdoptDetailReqDto adoptDetailReqDto);

	void removeAdopt(String pid);

	List<AdoptListResDto> getAdoptMyList(Long uid);
}
