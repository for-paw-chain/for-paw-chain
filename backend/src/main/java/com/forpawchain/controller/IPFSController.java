package com.forpawchain.controller;

import com.forpawchain.service.IPFSFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController("/ipfs")
@RequiredArgsConstructor
public class IPFSController {
    private final IPFSFileService ipfsService;

    @PostMapping("/upload")
    public ResponseEntity<?> saveFile(@RequestPart MultipartFile file) {
        try {
            Map<String, String> response = new HashMap<>();
            response.put("content", ipfsService.saveFile(file));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @GetMapping("/file/{hash}")
    public ResponseEntity<?> loadFile(@PathVariable("hash") String hash) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-type", MediaType.IMAGE_JPEG_VALUE);

            // Map<String, byte[]> response = new HashMap<>();
            // response.put("content", ipfsService.loadFile(hash));

            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(ipfsService.loadFile(hash));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
