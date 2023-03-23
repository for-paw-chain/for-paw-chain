package com.forpawchain.controller;

import com.forpawchain.domain.dto.response.UserResDto;
import com.forpawchain.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping
    public ResponseEntity<?> giveFriendAuthentication(@RequestHeader("access-token") String accessToken, long receiver, String pid) {
        try {
            long uid = 1;
            authService.giveFriendAuthentication(uid, receiver, pid);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
    @PutMapping
    public ResponseEntity<?> removeAuthentication(@RequestHeader("access-token") String accessToken, long receiver, String pid) {
        try {
            long uid = 1;
            authService.removeAuthentication(uid, receiver, pid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllAuthenicatedUser(@RequestHeader("access-token") String accessToken, String pid) {
        try {
            long uid = 1;
            List<UserResDto> userList = authService.getAllAuthenicatedUser(uid, pid);
            return ResponseEntity.accepted().body(userList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
    @PutMapping("/hand")
    public ResponseEntity<?> giveMasterAuthentication(@RequestHeader("access-token") String accessToken, long receiver, String pid) {
        try {
            long uid = 6;
            authService.giveMasterAuthentication(uid, receiver, pid);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
