package com.forpawchain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.service.AdoptServiceImpl;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adopt")
public class AdoptController {

    private final AdoptServiceImpl adoptService;

    @GetMapping("/ad")
	@ApiOperation(value = "입양 광고 랜덤 목록 10개 조회", notes = "랜덤으로 입양 공고문 10개 리스트를 반환한다. 광고용이다.")
    public ResponseEntity<List<AdoptListResDto>> getAdoptAd() {

        List<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptAd();
        return new ResponseEntity<List<AdoptListResDto>>(adoptListResDtoList, HttpStatus.OK);
    }

    @GetMapping
	// @ApiOperation(value = "입양 공고 목록 조회")
    public ResponseEntity<List<AdoptListResDto>> getAdoptList(@RequestParam("pageno") int pageNo, @RequestParam("type") int type,
        @RequestParam("kind") int kind, @RequestParam("sex") int sex) {

        List<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptList(pageNo, type, kind, sex);
        return new ResponseEntity<List<AdoptListResDto>>(adoptListResDtoList, HttpStatus.OK);
    }

    @GetMapping("/{pid}")
	// @ApiOperation(value = "입양 공고 상세 조회")
    public ResponseEntity<AdoptDetailResDto> getAdoptDetail(@PathVariable("pid") String pid) {

        AdoptDetailResDto adoptDetailResDto = adoptService.getAdoptDetail(pid);
        return new ResponseEntity<AdoptDetailResDto>(adoptDetailResDto, HttpStatus.OK);
    }

    @PostMapping
	@ApiOperation(value = "입양 공고 작성", notes = "입양 공고를 작성한다. 누가 작성했는지는 access token으로 파악한다.")
    public ResponseEntity<Void> registAdopt(@RequestHeader("Access-Token") String accessToken, @RequestBody
        AdoptDetailReqDto adoptDetailReqDto) {

        Long uid = 1L;  // 액세스 토큰에서 uid 뽑아내는 코드 필요함!
        adoptService.registAdopt(adoptDetailReqDto, uid);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping
	// @ApiOperation(value = "입양 공고 수정")
    public ResponseEntity<Void> modifyAdopt(@RequestHeader("Access-Token") String accessToken, @RequestBody
    AdoptDetailReqDto adoptDetailReqDto) {

        adoptService.modifyAdopt(adoptDetailReqDto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{pid}")
	@ApiOperation(value = "입양 공고 삭제", notes = "동물등록번호(pid)를 입력하면 해당 동물의 분양 공고를 삭제한다.")
    public ResponseEntity<Void> removeAdopt(@RequestHeader("Access-Token") String accessToken,
        @PathVariable("pid") String pid) {

        adoptService.removeAdopt(pid);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/article")
	@ApiOperation(value = "내가 쓴 입양 공고 조회", notes = "access token에서 uid 값을 추출해서, 해당 사용자가 쓴 분양 공고글 리스트를 반환한다.")
    public ResponseEntity<List<AdoptListResDto>> getAdoptMyList(@RequestHeader("Access-Token") String accessToken) {
        Long uid = 1L;  // 액세스 토큰에서 uid 뽑아내는 코드 필요함!
        List<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptMyList(uid);
        return new ResponseEntity<List<AdoptListResDto>>(adoptListResDtoList, HttpStatus.OK);
    }
}
