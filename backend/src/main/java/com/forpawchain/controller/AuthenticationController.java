package com.forpawchain.controller;

import com.forpawchain.domain.dto.response.UserResDto;
import com.forpawchain.domain.entity.UserEntity;
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
    public ResponseEntity<?> giveFriendAuthentication(String  pid, long receiver) {
        try {
            authService.giveFriendAuthentication(receiver, pid);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
    @PutMapping
    public ResponseEntity<?> removeAuthentication(String pid, long receiver) {
        try {
            authService.removeAuthentication(receiver, pid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllAuthenicatedUser(String pid) {
        try {
            List<UserResDto> userList = authService.getAllAuthenicatedUser(pid);
            return ResponseEntity.accepted().body(userList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
    @PutMapping("/hand")
    public ResponseEntity<?> giveMasterAuthentication(String pid, long receiver) {
        try {
            authService.giveFriendAuthentication(receiver, pid);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
