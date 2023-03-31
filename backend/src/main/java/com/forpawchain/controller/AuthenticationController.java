package com.forpawchain.controller;

import com.forpawchain.domain.Entity.AuthenticationType;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.domain.dto.response.UserResDto;
import com.forpawchain.service.AuthenticationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "권한 API")
public class AuthenticationController {
    private final UserController userController;
    private final AuthenticationService authService;

    @PostMapping
    @ApiOperation(value = "친구 권한 주기", notes = "요청한 동물과 사용자 사이에 친구 권한을 준다.")
    public ResponseEntity<?> giveFriendAuthentication(long receiver, String pid) {
        try {
            // UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
            // authService.giveFriendAuthentication(userInfoResDto.getUid(), receiver, pid);
            authService.giveFriendAuthentication(1L, receiver, pid);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
    @PutMapping
    @ApiOperation(value = "권한 삭제", notes = "요청한 동물과 사용자 사이의 관계를 끊는다.")
    public ResponseEntity<?> removeAuthentication(String pid) {
        try {
            // UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
            // authService.removeAuthentication(userInfoResDto.getUid(), pid);
            authService.removeAuthentication(1L, pid);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @GetMapping
    @ApiOperation(value = "모든 권한 조회", notes = "요청한 동물에 권한을 갖고 있는 사용자 목록을 불러온다.")
    public ResponseEntity<?> getAllAuthenicatedUser(String pid) {
        try {
            // UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
            // List<UserResDto> userList = authService.getAllAuthenicatedUser(userInfoResDto.getUid(), pid);
            List<UserResDto> userList = authService.getAllAuthenicatedUser(1L, pid);

            HashMap<String, List> map = new HashMap<>();
            map.put("content", userList);

            return ResponseEntity.accepted().body(map);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @PutMapping("/hand")
    @ApiOperation(value = "주인 권한 주기", notes = "요청한 동물과 사용자 사이에 주인 권한을 준다.")
    public ResponseEntity<?> giveMasterAuthentication(long receiver, String pid) {
        try {
            // UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
            // authService.giveMasterAuthentication(userInfoResDto.getUid(), receiver, pid);
            authService.giveMasterAuthentication(1L, receiver, pid);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @GetMapping("/pet")
    @ApiOperation(value = "동물에 대한 권한 조회", notes = "어떤 동물에 대해서, 내가 어떤 권한을 갖고 있는지 조회한다.")
    public ResponseEntity<HashMap<String, String>> getAuthenticationOfPid(String pid) {
        // UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
        HashMap<String, String> map = new HashMap<>();

        // AuthenticationType authenticationType = authService.getAuthenticationOfPid(userInfoResDto.getUid(), pid);
        AuthenticationType authenticationType = authService.getAuthenticationOfPid(1L, pid);
        map.put("content", authenticationType.toString());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/date")
    @ApiOperation(value = "동물과 함께한 날 조회", notes = "어떤 동물과 함께하기 시작한 날짜를 조회한다.")
    public ResponseEntity<HashMap<String, String>> getRegDate(String pid) {
        // UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
        HashMap<String, String> map = new HashMap<>();

        // String regDate = authService.getRegDate(userInfoResDto.getUid(), pid);
        String regDate = authService.getRegDate(1L, pid);
        map.put("content", regDate);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
