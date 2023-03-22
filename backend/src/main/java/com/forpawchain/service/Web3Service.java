package com.forpawchain.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

import com.forpawchain.domain.dto.request.LicenseReqDto;

public interface Web3Service {
	EthBlockNumber getBlockNumber() throws ExecutionException, InterruptedException;
	String deployContract(String pid) throws Exception;
	boolean checkLicense(LicenseReqDto licenseReqDto);
	String createWallet(long uid, LicenseReqDto licenseReqDto) throws
			CipherException,
			IOException,
			InvalidAlgorithmParameterException,
			NoSuchAlgorithmException,
			NoSuchProviderException;
	void sendEth(String toAddress) throws Exception;
	String getAddress(long uid);
}
