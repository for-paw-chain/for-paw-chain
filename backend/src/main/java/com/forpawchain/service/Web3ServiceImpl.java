package com.forpawchain.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
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

@Service
@RequiredArgsConstructor
public class Web3ServiceImpl implements Web3Service {

	private final Logger LOGGER = LoggerFactory.getLogger(Web3ServiceImpl.class);

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

	/**
	 * Web3j, Credentials, TransactionManager를 초기화
	 */
	private void setting() {
		if (web3j == null || credentials == null || transactionManager == null) {
			web3j = Web3j.build(new HttpService(NETWORK));
			credentials = Credentials.create(fromPrivateKey);
			transactionManager = new RawTransactionManager(web3j, credentials, Long.parseLong(CHAINID));
		}
	}

	/**
	 * 현재 블록 번호
 	 */
	public EthBlockNumber getBlockNumber() throws ExecutionException, InterruptedException {
		setting();
		return web3j.ethBlockNumber().sendAsync().get();
	}

	/**
	 * 스마트 컨트랙트 배포
	 */
	public String deployContract(String pid) throws Exception {
		setting();
		PetEntity petEntity = petRepository.findByPid(pid);

		//존재하지 않는 pid일 경우 예외 발생
		if (petEntity == null) {
			throw new BaseException(ErrorMessage.NOT_EXIST_CONTENT);
		}

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
			LOGGER.info("컨트랙트 주소 : " + validAddr);

			//배포된 컨트랙트 주소를 Pet DB에 저장
			petEntity.updatePetCa(validAddr);
			petRepository.save(petEntity);
		}

		return petEntity.getCa();
	}

	/**
	 * 의사가 맞는지 확인
	 */
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
	public String createWallet(long uid, LicenseReqDto licenseReqDto) throws
		CipherException,
		IOException,
		InvalidAlgorithmParameterException,
		NoSuchAlgorithmException,
		NoSuchProviderException {

		setting();

		UserEntity userEntity = userRepository.findByUid(uid);
		String privateKey = null;

		// 이미 지갑을 생성한 의사임
		if (userEntity.getWa() != null) {
			throw new BaseException(ErrorMessage.EXIST_WALLET);
		}

		// 의사 계정이 맞는지 확인
		if (checkLicense(licenseReqDto)) {
			// 비밀번호를 이용해 새 지갑 파일을 생성
			String password = "1234";
			String fileName = WalletUtils.generateNewWalletFile(password,
				new File("\\C:\\Users\\SSAFY\\Desktop\\wallet"));
				// new File("\\home\\ubuntu\\dev\\eth\\keystore"));

			// 비밀번호를 이용해 파일로부터 지갑을 로드해오기
			String walletFilePath = "C:\\Users\\SSAFY\\Desktop\\wallet\\" + fileName;
			// String walletFilePath = "\\home\\ubuntu\\dev\\eth\\keystore" + fileName;

			Credentials myCredentials = WalletUtils.loadCredentials(password, walletFilePath);

			// 지갑의 프라이빗 키
			privateKey = myCredentials.getEcKeyPair().getPrivateKey().toString(16);

			// 지갑 주소와 지갑의 프라이빗 키를 DB에 저장
			userEntity.updateWa(myCredentials.getAddress());
			userRepository.save(userEntity);
		}

		return privateKey;
	}

	public void sendEth(String toAddress) throws Exception {
		setting();

		// 목적지 주소로 보내려는 이더의 양
		BigInteger value = Convert.toWei("1.0", Convert.Unit.ETHER).toBigInteger();
		
		// 트랜잭션(이더) 전송
		EthSendTransaction ethSendTransaction = transactionManager.sendTransaction(DefaultGasProvider.GAS_PRICE,
			DefaultGasProvider.GAS_LIMIT, toAddress, "",
			value);
	}

	/**
	 * 해당 유저의 지갑 주소를 DB에서 조회한다.
	 * @param uid
	 */
	public String getAddress(long uid) {
		setting();
		UserEntity userEntity = userRepository.findByUid(uid);
		return userEntity.getWa();
	}
}
