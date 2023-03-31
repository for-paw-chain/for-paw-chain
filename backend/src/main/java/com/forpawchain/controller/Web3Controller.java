package com.forpawchain.controller;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

import com.forpawchain.domain.dto.request.LicenseReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.service.Web3Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web3")
@Api(tags = "스마트 컨트랙트 및 지갑 API")
public class Web3Controller {
	private final UserController userController;
	private final Web3Service web3Service;

	@GetMapping("block-no")
	@ApiOperation(value = "현재 블록번호 반환")
	public EthBlockNumber getBlockNumber() throws ExecutionException, InterruptedException {
		return web3Service.getBlockNumber();
	}

	@PostMapping("/license")
	@ApiOperation(value = "의사 인증", notes = "입력한 의사 정보가 정부 DB에 들어있으면 의사임이 인증된다. 지갑이 생성되고 private key가 반환된다.")
	public ResponseEntity<HashMap<String, String>> authDoctor(@RequestBody LicenseReqDto licenseReqDto) {
		UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
		HashMap<String, String> map = new HashMap<>();
		String privateKey = null;

		try {
			privateKey = web3Service.createWallet(userInfoResDto.getUid(), licenseReqDto);
			map.put("content", privateKey);
		} catch (Exception e) {
			return new ResponseEntity<>(map, HttpStatus.SERVICE_UNAVAILABLE);
		}

		if (privateKey == null) {
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>(map, HttpStatus.CREATED);
		}
	}

	@PostMapping("eth")
	@ApiOperation(value = "이더 충전", notes = "로그인 유저의 지갑으로 이더를 충전해준다.")
	public ResponseEntity<?> sendEth() {
		UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
		String toAddress = userInfoResDto.getWa();

		try {
			web3Service.sendEth(toAddress);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}

	@PostMapping("contract/{pid}")
	@ApiOperation(value = "컨트랙트 주소 조회", notes = "해당 동물의 컨트랙트 주소를 반환한다. 컨트랜트 주소가 없으면 새 컨트랙트를 배포한다.")
	public ResponseEntity<HashMap<String, String>> deployContract(@PathVariable("pid") String pid) throws Exception {
		HashMap<String, String> map = new HashMap<>();

		try {
			String ca = web3Service.deployContract(pid);
			map.put("content", ca);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(map, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@GetMapping("wallet/{wa}")
	@ApiOperation(value = "지갑으로 의사 조회", notes = "지갑 주소로 의사를 조회해서 의사의 이름을 반환한다.")
	public ResponseEntity<HashMap<String, String>> findDoctor(@PathVariable("wa") String wa) {
		HashMap<String, String> map = new HashMap<>();

		try {
			String name = web3Service.findDoctor(wa);
			map.put("content", name);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(map, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
