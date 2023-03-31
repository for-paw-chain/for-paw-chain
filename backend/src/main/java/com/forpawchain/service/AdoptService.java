package com.forpawchain.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;

public interface AdoptService {
	PageImpl<AdoptListResDto> getAdoptList(int pageNo, String type, Integer spayed, String sex);

	List<AdoptListResDto> getAdoptAd();

	AdoptDetailResDto getAdoptDetail(String pid);

	void registAdopt(AdoptDetailReqDto adoptDetailReqDto, long uid, MultipartFile imageFile) throws IOException;

	void modifyAdopt(AdoptDetailReqDto adoptDetailReqDto, long uid, MultipartFile imageFile) throws IOException;

	void removeAdopt(String pid, long uid);

	List<AdoptListResDto> getAdoptMyList(long uid);
}
