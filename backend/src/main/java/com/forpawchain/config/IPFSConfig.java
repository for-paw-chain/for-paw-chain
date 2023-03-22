package com.forpawchain.config;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class IPFSConfig {
    public IPFS ipfs;
    public IPFSConfig() {
        // ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
    }
}
