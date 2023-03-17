package com.forpawchain.controller;

import com.forpawchain.service.IPFSFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class IPFSController {
    final private IPFSFileService ipfsService;

    @PostMapping("/upload")
    public String saveFile(@RequestPart MultipartFile file) {
        MultipartFile tmpfile = file;
        return ipfsService.saveFile(tmpfile);
    }

    @GetMapping("/file/{hash}")
    public ResponseEntity<byte[]> loadFile(@PathVariable("hash")String hash) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", MediaType.IMAGE_PNG_VALUE);
        httpHeaders.add("Content-type", MediaType.IMAGE_JPEG_VALUE);
        byte[] bytes = ipfsService.loadFile(hash);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(bytes);
    }
}
