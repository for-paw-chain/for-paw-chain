package com.forpawchain.service;

import org.springframework.web.bind.annotation.RequestParam;

import com.forpawchain.domain.dto.response.AdoptListResDto;

public interface AdoptService {

	AdoptListResDto getAdoptList(int pageNo, int type, int kind, int sex);
}
