package com.forpawchain.controller;

import static com.forpawchain.exception.ErrorMessage.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.exception.BaseException;
import com.forpawchain.service.AdoptService;
import com.forpawchain.service.AdoptServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adopt")
@Api(tags = "입양 공고 API")
public class AdoptController {

    private final AdoptService adoptService;

    @GetMapping("/ad")
	@ApiOperation(value = "입양 광고 랜덤 목록 10개 조회", notes = "랜덤으로 입양 공고문 10개 리스트를 반환한다. 광고용이다.")
    public ResponseEntity<List<AdoptListResDto>> getAdoptAd() {

        List<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptAd();
        return new ResponseEntity<>(adoptListResDtoList, HttpStatus.OK);
    }

    @GetMapping
	@ApiOperation(value = "입양 공고 목록 조회")
    public ResponseEntity<PageImpl<AdoptListResDto>> getAdoptList(
        @Parameter(description = "페이지 번호") @RequestParam("pageno") int pageNo,
        @Parameter(description = "중성화여부. null:전체 1:true 0:false")
        @RequestParam(value = "spayed",required = false) Integer spayed,
        @Parameter(description = "종류. null:전체 'DOG':강아지 'CAT':고양이 'ETC':기타")
        @RequestParam(value = "type", required = false) String type,
        @Parameter(description = "성별. null:전체 'MALE':남아 'FEMALE':여아")
        @RequestParam(value = "sex", required = false) String sex) {

        //요청하는 페이지 번호가 0보다 작으면 안 된다.
        if (pageNo < 0) {
            throw new BaseException(VALIDATION_FAIL_EXCEPTION);
        }

        //검색 조건으로 '중성화 여부'를 받아올 경우, 이 값은 0 또는 1이어야 한다.
        if (spayed != null && spayed != 0 && spayed != 1) {
            throw new BaseException(VALIDATION_FAIL_EXCEPTION);
        }

        //검색 조건으로 '타입'을 받아올 경우, 이 값은 'DOG' 또는 'CAT' 또는 'ETC'이어야 한다.
        if (type != null && type != "DOG" && type != "CAT" && type != "ETC") {
            throw new BaseException(VALIDATION_FAIL_EXCEPTION);
        }

        //검색 조건으로 '성별'을 받아올 경우, 이 값은 'MALE' 또는 'FEMALE'이어야 한다.
        if (sex != null && sex != "MALE" && sex != "FEMALE") {
            throw new BaseException(VALIDATION_FAIL_EXCEPTION);
        }

        PageImpl<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptList(pageNo, type, spayed, sex);
        return new ResponseEntity<>(adoptListResDtoList, HttpStatus.OK);
    }

    @GetMapping("/{pid}")
	@ApiOperation(value = "입양 공고 상세 조회", notes = "동물등록번호(pid)를 요청하면 해당 동물의 입양 공고 상세 내용을 반환한다.")
    public ResponseEntity<AdoptDetailResDto> getAdoptDetail(@PathVariable("pid") String pid) {
        AdoptDetailResDto adoptDetailResDto = adoptService.getAdoptDetail(pid);
        return new ResponseEntity<>(adoptDetailResDto, HttpStatus.OK);
    }

    // @PostMapping(consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping
	@ApiOperation(value = "입양 공고 작성", notes = "입양 공고를 작성한다. 누가 작성했는지는 access token으로 파악한다.")
    public ResponseEntity<Void> registAdopt(@RequestHeader("Access-Token") String accessToken,
        @RequestPart(name = "content") AdoptDetailReqDto adoptDetailReqDto, @RequestPart(name = "profile") MultipartFile imageFile) throws IOException {
        long uid = 1L;  // 액세스 토큰에서 uid 뽑아내는 코드 필요함!
        adoptService.registAdopt(adoptDetailReqDto, uid, imageFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
	@ApiOperation(value = "입양 공고 수정", notes = "입양 공고의 내용을 수정한다.")
    public ResponseEntity<Void> modifyAdopt(@RequestHeader("Access-Token") String accessToken,
    AdoptDetailReqDto adoptDetailReqDto, @RequestPart MultipartFile imageFile) throws IOException {

        long uid = 1L;

        // 글을 쓴 본인이 맞는지 검증하는 과정 필요
        adoptService.modifyAdopt(adoptDetailReqDto, uid, imageFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{pid}")
	@ApiOperation(value = "입양 공고 삭제", notes = "동물등록번호(pid)를 입력하면 해당 동물의 분양 공고를 삭제한다.")
    public ResponseEntity<Void> removeAdopt(@RequestHeader("Access-Token") String accessToken,
        @PathVariable("pid") String pid) {

        long uid = 1L;

        adoptService.removeAdopt(pid, uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/article")
	@ApiOperation(value = "내가 쓴 입양 공고 조회", notes = "access token에서 uid 값을 추출해서, 해당 사용자가 쓴 분양 공고글 리스트를 반환한다.")
    public ResponseEntity<HashMap<String, List<AdoptListResDto>>> getAdoptMyList(@RequestHeader("Access-Token") String accessToken) {
        long uid = 1L;  // 액세스 토큰에서 uid 뽑아내는 코드 필요함!
        List<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptMyList(uid);

        HashMap<String, List<AdoptListResDto>> map = new HashMap<>();
        map.put("content", adoptListResDtoList);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
