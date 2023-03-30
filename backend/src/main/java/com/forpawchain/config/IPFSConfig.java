package com.forpawchain.config;

import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import io.ipfs.api.IPFS;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IPFSConfig {
    private IPFS ipfs;
    public IPFS getIPFS() {
        if (ipfs != null) {
            return ipfs;
        } else {
            try {
                ipfs = new IPFS("/ip4/172.20.0.3/tcp/5001");
                // ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
                // ipfs = new IPFS("/ip4/3.39.235.238/tcp/5001");
                // ipfs = new IPFS("/ip4/70.12.247.105/tcp/5001");
                return ipfs;
            } catch (Exception e) {
                e.printStackTrace();
                throw new BaseException(ErrorMessage.IPFS_CONNECTION_EXCEPTION);
            }
        }
    }
}
