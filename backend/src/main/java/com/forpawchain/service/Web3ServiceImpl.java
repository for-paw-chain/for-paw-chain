package com.forpawchain.service;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import com.forpawchain.MyContract;
import com.forpawchain.domain.dto.request.LicenseReqDto;
import com.forpawchain.domain.Entity.DoctorLicenseEntity;
import com.forpawchain.domain.Entity.PetEntity;
import com.forpawchain.domain.Entity.UserEntity;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.repository.DoctorLicenseRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class Web3ServiceImpl implements Web3Service {
	private final DoctorLicenseRepository doctorLicenseRepository;
	private final UserRepository userRepository;
	private final PetRepository petRepository;

	@Value("${web3.network.address}")
	private String NETWORK;
	@Value("${web3.network.chainId}")
	private String CHAINID;
	@Value("${web3.walletPrivateKey}")
	private String fromPrivateKey;

	private Web3j web3j;
	private Credentials credentials;
	private TransactionManager transactionManager;

	// 현재 블록 번호
	@Override
	public EthBlockNumber getBlockNumber() throws ExecutionException, InterruptedException {
		setting();
		return web3j.ethBlockNumber().sendAsync().get();
	}

	// 스마트 컨트랙트 배포
	@Override
	public String deployContract(String pid) throws Exception {
		setting();

		PetEntity petEntity = petRepository.findByPid(pid)
			.orElseThrow(() -> new BaseException(ErrorMessage.NOT_EXIST_CONTENT));

		//해당 동물에게 컨트랙트가 배포된 적 없다면 새로운 컨트랙트를 배포
		if (petEntity.getCa() == null) {
			MyContract contract = MyContract.deploy(
				web3j,
				transactionManager,
				DefaultGasProvider.GAS_PRICE,
				DefaultGasProvider.GAS_LIMIT
			).send();

			String invalidAddr = contract.getContractAddress();
			String validAddr = Keys.toChecksumAddress(invalidAddr);
			log.info("컨트랙트 주소 : " + validAddr);

			//배포된 컨트랙트 주소를 Pet DB에 저장
			PetEntity newPetNewEntity = PetEntity.builder()
				.pid(petEntity.getPid())
				.ca(validAddr)
				.lost(petEntity.isLost())
				.build();

			petRepository.save(newPetNewEntity);
		}

		return petEntity.getCa();
	}

	// 의사가 맞는지 확인
	@Override
	public boolean checkLicense(LicenseReqDto licenseReqDto) {
		setting();
		String name = licenseReqDto.getName();
		String registnum = licenseReqDto.getRegistnum();
		String tel = licenseReqDto.getTel();
		int telecom = licenseReqDto.getTelecom();

		Optional<DoctorLicenseEntity> findDoctorLicense = doctorLicenseRepository.findByNameAndRegistnumAndTelAndTelecom(
			name, registnum, tel, telecom);

		// 요청으로 들어온 입력 정보가 DB에 존재하면 의사 맞음
		if (findDoctorLicense.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 지갑 생성
	 * private key만 있으면 사용 가능.
	 * private key를 프론트에 전달해주기. db에는 저장 안함
	 */
	@Override
	public String createWallet(long uid, LicenseReqDto licenseReqDto) throws Exception {
		setting();

		UserEntity userEntity = userRepository.findByUid(uid).orElse(null);
		String privateKey = null;
		String address = null;

		// 이미 지갑을 생성한 의사임
		if (userEntity.getWa() != null) {
			throw new BaseException(ErrorMessage.EXIST_WALLET);
		}

		// 의사 계정이 맞는지 확인
		if (checkLicense(licenseReqDto)) {
			ECKeyPair ecKeyPair = Keys.createEcKeyPair();
			BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

			privateKey = privateKeyInDec.toString(16);
			address = Keys.getAddress(ecKeyPair);

			// 지갑 주소와 지갑의 프라이빗 키를 DB에 저장
			UserEntity newUserEntity = UserEntity.builder()
				.uid(userEntity.getUid())
				.id(userEntity.getId())
				.social(userEntity.getSocial())
				.name(userEntity.getName())
				.profile(userEntity.getProfile())
				.wa("0x" + address)
				.del(userEntity.isDel())
				.build();

			this.sendEth(address);

			userRepository.save(newUserEntity);
		}

		return privateKey;
	}

	// eth 전달
	@Override
	public void sendEth(String toAddress) throws Exception {
		setting();

		// 목적지 주소로 보내려는 이더의 양
		BigInteger value = Convert.toWei("1.0", Convert.Unit.ETHER).toBigInteger();
		
		// 트랜잭션(이더) 전송
		EthSendTransaction ethSendTransaction = transactionManager.sendTransaction
			(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, toAddress, "", value);
	}

	// 지갑 주소로 찾은 의사 이름 반환
	@Override
	public String findDoctor(String wa) {
		UserEntity userEntity = userRepository.findByWa(wa)
			.orElseThrow(() -> new BaseException(ErrorMessage.USER_NOT_FOUND));

		return userEntity.getName();
	}

	// Web3j, Credentials, TransactionManager를 초기화
	private void setting() {
		if (web3j == null || credentials == null || transactionManager == null) {
			web3j = Web3j.build(new HttpService(NETWORK));
			credentials = Credentials.create(fromPrivateKey);
			transactionManager = new RawTransactionManager(web3j, credentials, Long.parseLong(CHAINID));
		}
	}
}
