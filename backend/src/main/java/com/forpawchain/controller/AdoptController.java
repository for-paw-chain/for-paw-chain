package com.forpawchain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.forpawchain.service.AdoptService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adopt")
public class AdoptController {

    private final AdoptService adoptService;

    /**
     * 입양 광고 랜덤 목록 조회
     */
    @GetMapping("/ad")
    public ResponseEntity<?> getAdoptAd() {


        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * 입양 공고 목록 조회
     */
    @GetMapping
    public ResponseEntity<?> getAdoptList(@RequestParam("pageno") int pageNo, @RequestParam("type") int type,
        @RequestParam("kind") int kind, @RequestParam("sex") int sex) {

        adoptService.getAdoptList(pageNo, type, kind, sex);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * 입양 공고 상세 조회
     */
    @GetMapping("/{pid}")
    public ResponseEntity<?> getAdoptDetail(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     *  입양 공고 작성
     */
    @PostMapping
    public ResponseEntity<?> registAdopt(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     *  입양 공고 수정
     */
    @PutMapping
    public ResponseEntity<?> modifyAdopt(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     *  입양 공고 삭제
     */
    @DeleteMapping("/{pid}")
    public ResponseEntity<?> removeAdopt(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     *  내가 쓴 입양 공고 조회
     */
    @GetMapping("/article")
    public ResponseEntity<?> getAdoptMyList(@RequestHeader("Access-Token") String accessToken) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
