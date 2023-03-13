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
import com.forpawchain.repository.DoctorLicenseRepository;
import com.forpawchain.service.DoctorLicenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/license")
public class DoctorLicenseController {

	private final DoctorLicenseService doctorLicenseService;

	@PostMapping
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
}
