package com.forpawchain.service;

import org.springframework.web.multipart.MultipartFile;

public interface IPFSFileService {
    // IPFS에 파일 저장 후 Hash 반환
    String saveFile(MultipartFile file);

    // IPFS파일의 해쉬값으로 파일 반환
    byte[] loadFile(String hash);
}
