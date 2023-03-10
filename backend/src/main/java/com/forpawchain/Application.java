package com.forpawchain;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forpawchain.service.Web3jService;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
