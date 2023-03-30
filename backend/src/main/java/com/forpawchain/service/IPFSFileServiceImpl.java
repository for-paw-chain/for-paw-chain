package com.forpawchain.service;

import com.forpawchain.config.IPFSConfig;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class IPFSFileServiceImpl implements IPFSFileService {
    final private IPFSConfig ipfsConfig;

    @Override
    public String saveFile(MultipartFile file) {
        try {
            InputStream stream = new ByteArrayInputStream(file.getBytes());
            NamedStreamable.InputStreamWrapper inputStreamWrapper = new NamedStreamable.InputStreamWrapper(stream);

            MerkleNode merkleNode = ipfsConfig.getIPFS().add(inputStreamWrapper).get(0);

            return merkleNode.hash.toBase58();
        } catch (IOException e) {
            throw new BaseException(ErrorMessage.IPFS_FILE_EXCEPTION);
        }
    }

    @Override
    public byte[] loadFile(String hash) {
        Multihash filePointer = Multihash.fromBase58(hash);
        try {
            return ipfsConfig.getIPFS().cat(filePointer);
        } catch (IOException e) {
            throw new BaseException(ErrorMessage.IPFS_CONNECTION_EXCEPTION);
        }
    }

//    public MultipartFile convertByteArrayToMultipartFile(String fileName, byte[] fileBytes) throws IOException {
//        // 파일 이름이나 바이트 배열이 null이거나 빈 문자열이면 MultipartFile을 생성할 수 없습니다.
//        if(StringUtils.isEmpty(fileName) || fileBytes == null || fileBytes.length == 0) {
//            return null;
//        }
//
//        // 바이트 배열을 사용하여 MultipartFile 생성
//        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName,
//                ContentType.APPLICATION_OCTET_STREAM.toString(), new ByteArrayInputStream(fileBytes));
//
//        return multipartFile;
//    }
}
