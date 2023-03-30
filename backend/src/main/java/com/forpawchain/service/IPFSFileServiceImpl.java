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
    // IPFS에 파일 저장 후 Hash 반환
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
    // IPFS파일의 해쉬값으로 파일 반환
    public byte[] loadFile(String hash) {
        Multihash filePointer = Multihash.fromBase58(hash);
        try {
            return ipfsConfig.getIPFS().cat(filePointer);
        } catch (IOException e) {
            throw new BaseException(ErrorMessage.IPFS_CONNECTION_EXCEPTION);
        }
    }
}
