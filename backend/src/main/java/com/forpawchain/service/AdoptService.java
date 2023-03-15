package com.forpawchain.service;

import java.util.List;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;

public interface AdoptService {

	List<AdoptListResDto> getAdoptList(int pageNo, int type, int kind, int sex);

	List<AdoptListResDto> getAdoptAd();

	AdoptDetailResDto getAdoptDetail(String pid);

	void registAdopt(AdoptDetailReqDto adoptDetailReqDto, Long uid);

	void modifyAdopt(AdoptDetailReqDto adoptDetailReqDto);

	void removeAdopt(String pid);

	List<AdoptListResDto> getAdoptMyList(Long uid);
}
