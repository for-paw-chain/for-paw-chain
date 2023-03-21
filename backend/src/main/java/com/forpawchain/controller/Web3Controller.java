package com.forpawchain.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

import com.forpawchain.domain.dto.request.LicenseReqDto;
import com.forpawchain.service.Web3Service;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web3")
public class Web3Controller {

	private final Web3Service web3Service;

	/**
	 * 현재 블록 번호
	 */
	@GetMapping("block-no")
	public EthBlockNumber getBlockNumber() throws ExecutionException, InterruptedException {
		return web3Service.getBlockNumber();
	}

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

		// accesstoken으로 uid 값 받아와야됨
		Long uid = 1L;

		String privateKey = web3Service.createWallet(uid, licenseReqDto);

		if (privateKey == null) {
			return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(privateKey, HttpStatus.CREATED);
		}
	}

	/**
	 * 서버 지갑에
	 * 로그인 유저의 지갑으로
	 * 이더 전송
	 */
	@PostMapping("eth")
	@ApiOperation(value = "이더 충전", notes = "로그인 유저의 지갑으로 이더를 충전해준다.")
	public void sendEth() throws Exception {
		// String toAddress = "0x4ba7a38538d48f05816909e572fedc18cc3ab7bb";
		// accesstoken으로 uid 값 받아와야됨
		Long uid = 1L;

		String toAddress = web3Service.getAddress(uid);
		System.out.println("지갑 주소 : " + toAddress);
		web3Service.sendEth(toAddress);
	}
	
	@PostMapping("contract/{pid}")
	@ApiOperation(value = "컨트랙트 배포", notes = "하나의 동물 당 하나의 컨트랙트를 배포한다.")
	public void deployContract(@PathVariable("pid") String pid) throws Exception {
		web3Service.deployContract(pid);
	}
}
