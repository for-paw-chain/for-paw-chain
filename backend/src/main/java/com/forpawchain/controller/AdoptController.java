package com.forpawchain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.service.AdoptService;
import com.forpawchain.service.AdoptServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adopt")
public class AdoptController {

    // private final AdoptServiceImpl adoptService;
    //
    // /**
    //  * 입양 광고 랜덤 목록 조회
    //  */
    // @GetMapping("/ad")
    // public ResponseEntity<List<AdoptListResDto>> getAdoptAd() {
    //
    //     List<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptAd();
    //     return new ResponseEntity<List<AdoptListResDto>>(adoptListResDtoList, HttpStatus.OK);
    // }
    //
    // /**
    //  * 입양 공고 목록 조회
    //  */
    // @GetMapping
    // public ResponseEntity<List<AdoptListResDto>> getAdoptList(@RequestParam("pageno") int pageNo, @RequestParam("type") int type,
    //     @RequestParam("kind") int kind, @RequestParam("sex") int sex) {
    //
    //     List<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptList(pageNo, type, kind, sex);
    //     return new ResponseEntity<List<AdoptListResDto>>(adoptListResDtoList, HttpStatus.OK);
    // }
    //
    // /**
    //  * 입양 공고 상세 조회
    //  */
    // @GetMapping("/{pid}")
    // public ResponseEntity<AdoptDetailResDto> getAdoptDetail(@PathVariable("pid") String pid) {
    //
    //     AdoptDetailResDto adoptDetailResDto = adoptService.getAdoptDetail(pid);
    //     return new ResponseEntity<AdoptDetailResDto>(adoptDetailResDto, HttpStatus.OK);
    // }
    //
    // /**
    //  *  입양 공고 작성
    //  */
    // @PostMapping
    // public ResponseEntity<Void> registAdopt(@RequestHeader("Access-Token") String accessToken, @RequestBody
    //     AdoptDetailReqDto adoptDetailReqDto) {
    //
    //     adoptService.registAdopt(adoptDetailReqDto);
    //     return new ResponseEntity<Void>(HttpStatus.CREATED);
    // }
    //
    // /**
    //  *  입양 공고 수정
    //  */
    // @PutMapping
    // public ResponseEntity<Void> modifyAdopt(@RequestHeader("Access-Token") String accessToken, @RequestBody
    // AdoptDetailReqDto adoptDetailReqDto) {
    //
    //     adoptService.modifyAdopt(adoptDetailReqDto);
    //     return new ResponseEntity<Void>(HttpStatus.CREATED);
    // }
    //
    // /**
    //  *  입양 공고 삭제
    //  */
    // @DeleteMapping("/{pid}")
    // public ResponseEntity<Void> removeAdopt(@RequestHeader("Access-Token") String accessToken,
    //     @PathVariable("pid") String pid) {
    //
    //     adoptService.removeAdopt(pid);
    //     return new ResponseEntity<Void>(HttpStatus.OK);
    // }
    //
    // /**
    //  *  내가 쓴 입양 공고 조회
    //  */
    // @GetMapping("/article")
    // public ResponseEntity<List<AdoptListResDto>> getAdoptMyList(@RequestHeader("Access-Token") String accessToken) {
    //
    //     String uid = "";
    //     List<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptMyList(uid);
    //     return new ResponseEntity<List<AdoptListResDto>>(adoptListResDtoList, HttpStatus.OK);
    // }
}
