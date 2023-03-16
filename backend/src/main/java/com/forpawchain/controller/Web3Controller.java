package com.forpawchain.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;

import com.forpawchain.domain.dto.request.LicenseReqDto;
import com.forpawchain.service.Web3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web3")
public class Web3Controller {

	private final Web3Service doctorLicenseService;

	/**
	 * 의사 지갑 생성
	 */
	@PostMapping("/license")
	public ResponseEntity<String> authDoctor(@RequestBody LicenseReqDto licenseReqDto) throws
		InvalidAlgorithmParameterException,
		CipherException,
		IOException,
		NoSuchAlgorithmException,
		NoSuchProviderException {

		String privateKey = doctorLicenseService.createWallet(licenseReqDto);

		if (privateKey == null) {
			return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(privateKey, HttpStatus.CREATED);
		}
	}

	/**
	 * 서버 지갑에서
	 * 요청으로 받은 private key 지갑으로
	 * 이더 전송
	 */
	@PostMapping("eth")
	public void chargeEth() {}
}
