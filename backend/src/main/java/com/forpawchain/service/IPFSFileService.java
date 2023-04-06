package com.forpawchain.service;

import org.springframework.web.multipart.MultipartFile;

public interface IPFSFileService {
    String saveFile(MultipartFile file);

    byte[] loadFile(String hash);
}