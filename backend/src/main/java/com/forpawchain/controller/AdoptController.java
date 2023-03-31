package com.forpawchain.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.service.AdoptService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adopt")
@Api(tags = "입양 공고 API")
public class AdoptController {
    private final UserController userController;
    private final AdoptService adoptService;

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

        if (pageNo < 0) {
            // 요청하는 페이지 번호가 0보다 작으면 안 된다.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else if (spayed != null && spayed != 0 && spayed != 1) {
            // 검색 조건으로 '중성화 여부'를 받아올 경우, 이 값은 0 또는 1이어야 한다.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else if (type != null && type != "DOG" && type != "CAT" && type != "ETC") {
            // 검색 조건으로 '타입'을 받아올 경우, 이 값은 'DOG' 또는 'CAT' 또는 'ETC'이어야 한다.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else if (sex != null && sex != "MALE" && sex != "FEMALE") {
            // 검색 조건으로 '성별'을 받아올 경우, 이 값은 'MALE' 또는 'FEMALE'이어야 한다.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            PageImpl<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptList(pageNo, type, spayed, sex);

            return new ResponseEntity<>(adoptListResDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @GetMapping("/ad")
	@ApiOperation(value = "입양 광고 랜덤 목록 10개 조회", notes = "랜덤으로 입양 공고문 10개 리스트를 반환한다. 광고용이다.")
    public ResponseEntity<HashMap<String, List>> getAdoptAd() {
        HashMap<String, List> map = new HashMap<>();
        List<AdoptListResDto> adoptListResDtoList = null;

        try {
            adoptListResDtoList = adoptService.getAdoptAd();
            map.put("content", adoptListResDtoList);

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(map, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/{pid}")
	@ApiOperation(value = "입양 공고 상세 조회", notes = "동물등록번호(pid)를 요청하면 해당 동물의 입양 공고 상세 내용을 반환한다.")
    public ResponseEntity<AdoptDetailResDto> getAdoptDetail(@PathVariable("pid") String pid) {
        try {
            AdoptDetailResDto adoptDetailResDto = adoptService.getAdoptDetail(pid);

            return ResponseEntity.status(HttpStatus.OK).body(adoptDetailResDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @PostMapping
	@ApiOperation(value = "입양 공고 작성", notes = "입양 공고를 작성한다. 누가 작성했는지는 access token으로 파악한다.")
    public ResponseEntity<?> registAdopt(@RequestPart(name = "content") AdoptDetailReqDto adoptDetailReqDto,
        @RequestPart(name = "profile") MultipartFile imageFile) {

        UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();

        try {
            adoptService.registAdopt(adoptDetailReqDto, userInfoResDto.getUid(), imageFile);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @PutMapping
	@ApiOperation(value = "입양 공고 수정", notes = "입양 공고의 내용을 수정한다.")
    public ResponseEntity<?> modifyAdopt(@RequestPart(name = "content") AdoptDetailReqDto adoptDetailReqDto,
        @RequestPart(name = "profile") MultipartFile imageFile) {

        UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();

        try {
            adoptService.modifyAdopt(adoptDetailReqDto, userInfoResDto, imageFile);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @DeleteMapping("/{pid}")
	@ApiOperation(value = "입양 공고 삭제", notes = "동물등록번호(pid)를 입력하면 해당 동물의 분양 공고를 삭제한다.")
    public ResponseEntity<?> removeAdopt(@PathVariable("pid") String pid) {
        UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();

        try {
            adoptService.removeAdopt(pid, userInfoResDto);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @GetMapping("/article")
	@ApiOperation(value = "내가 쓴 입양 공고 조회", notes = "access token에서 uid 값을 추출해서, 해당 사용자가 쓴 분양 공고글 리스트를 반환한다.")
    public ResponseEntity<HashMap<String, List<AdoptListResDto>>> getAdoptMyList() {
        UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
        HashMap<String, List<AdoptListResDto>> map = new HashMap<>();

        try {
            List<AdoptListResDto> adoptListResDtoList = adoptService.getAdoptMyList(userInfoResDto.getUid());
            map.put("content", adoptListResDtoList);

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}