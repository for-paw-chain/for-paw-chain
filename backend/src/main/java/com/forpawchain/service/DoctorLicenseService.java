package com.forpawchain.service;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;

import com.forpawchain.domain.dto.request.LicenseReqDto;
import com.forpawchain.domain.entity.DoctorLicenseEntity;
import com.forpawchain.repository.DoctorLicenseRepository;

import lombok.RequiredArgsConstructor;
// import org.web3j.protocol.Web3j;
// import org.web3j.protocol.core.DefaultBlockParameter;
// import org.web3j.protocol.core.methods.response.EthAccounts;
// import org.web3j.protocol.core.methods.response.EthBlockNumber;
// import org.web3j.protocol.core.methods.response.EthGetBalance;
// import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

// @RequiredArgsConstructor
@Service
@RequiredArgsConstructor
public class DoctorLicenseService {

	private final DoctorLicenseRepository doctorLicenseRepository;
	// private final NFT nft;

	// 현재 블록 번호
	// public EthBlockNumber getBlockNumber() throws ExecutionException, InterruptedException {
	// 	return web3j.ethBlockNumber().sendAsync().get();
	// }

	/**
	 * 의사가 맞는지 확인
	 */
	public boolean checkLicense(LicenseReqDto licenseReqDto) {
		String name = licenseReqDto.getName();
		String registnum = licenseReqDto.getRegistnum();
		String tel = licenseReqDto.getTel();
		int telecom = licenseReqDto.getTelecom();
		Optional<DoctorLicenseEntity> findDoctorLicense = doctorLicenseRepository.findByNameAndRegistnumAndTelAndTelecom(
			name, registnum, tel, telecom);

		if (findDoctorLicense.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	// // 지정된 주소의 계정
	// public EthAccounts getEthAccounts() throws ExecutionException, InterruptedException {
	// 	System.out.println("------------");
	// 	System.out.println(web3j.ethAccounts().sendAsync());
	// 	System.out.println("------------");
	// 	return web3j.ethAccounts().sendAsync().get();
	// }



	/**
	 * 지갑 생성
	 * private key만 있으면 사용 가능.
	 * private key를 프론트에 전달해주기. db에는 저장 안함
	 */
	public String createWallet(LicenseReqDto licenseReqDto) throws
		CipherException,
		IOException,
		InvalidAlgorithmParameterException,
		NoSuchAlgorithmException,
		NoSuchProviderException {

		String privateKey = null;

		// 의사 계정이 맞는지 확인
		if (checkLicense(licenseReqDto)) {
			// Connect to Ethereum client using HTTP provider
			Web3j web3j = Web3j.build(new HttpService("https://sepolia.infura.io/v3/ccbf710f49e54b2c867e185af221ffa9"));

			// Generate a new wallet file using a password
			String password = "myPassword";
			String fileName = WalletUtils.generateNewWalletFile(password, new File("C:\\Users\\SSAFY\\Desktop\\wallet"));

			// Load the wallet from file using the password
			String walletFilePath = "C:\\Users\\SSAFY\\Desktop\\wallet\\" + fileName;
			Credentials credentials = WalletUtils.loadCredentials(password, walletFilePath);

			// Print the wallet address
			System.out.println("Wallet address: " + credentials.getAddress());

			privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
		}

		return privateKey;
	}
/////////////////////////////////////////////
	// // 계좌 거래 건수
	// public EthGetTransactionCount getTransactionCount() throws ExecutionException, InterruptedException {
	// 	EthGetTransactionCount result = new EthGetTransactionCount();
	// 	result = web3j.ethGetTransactionCount(WALLET_ADDRESS,
	// 			DefaultBlockParameter.valueOf("latest"))
	// 		.sendAsync()
	// 		.get();
	// 	return result;
	// }
	//
	// // 계정 잔액
	// public EthGetBalance getEthBalance() throws ExecutionException, InterruptedException {
	// 	return web3j.ethGetBalance(WALLET_ADDRESS,
	// 			DefaultBlockParameter.valueOf("latest"))
	// 		.sendAsync()
	// 		.get();
	// }

	// 스마트컨트랙트명 가져오기
	// public String getContractName() throws Exception {
	// 	return nft.name().send();
	// }

	// nft 발행 건수
	// public BigInteger currentCount() throws Exception {
	// 	return nft.balanceOf(WALLET_ADDRESS).send();
	// }

	// nft 발행
	// public TransactionReceipt nftCreate() throws ExecutionException, InterruptedException {
	// 	System.out.println("nftCreate start : " + LocalDateTime.now());
	// 	TransactionReceipt transactionReceipt = nft.create(WALLET_ADDRESS, "ipfs://QmNZLXLk8nWG4PMdcCWAGpgW12hAhiV375YeFpaCLisfBi").sendAsync().get();
	// 	System.out.println("nftCreate end : " + LocalDateTime.now());
	//
	// 	return transactionReceipt;
	// }

	// nft 거래건이 있을경우 subscribe에 등록한 함수 실행
	// public void transferEventFlowable() throws Exception {
	// 	web3j.ethLogFlowable(getEthFilter())
	// 		.subscribe(log -> {
	// 			System.out.println("log = " + log);
	// 			String data = log.getData();
	// 			System.out.println("data = " + data);
	// 			String address = log.getAddress();
	// 			System.out.println("address = " + address);
	// 		});
	//
	// 	Thread.sleep(10000000);
	// }

	// 이더리움 블록체인에서 발생하는 이벤트를 필터링하는데 사용(여기에서는 Transfer(거래)만 허용)
	// private EthFilter getEthFilter() throws Exception {
	// 	EthBlockNumber blockNumber = getBlockNumber();
	// 	EthFilter ethFilter = new EthFilter(DefaultBlockParameter.valueOf(blockNumber.getBlockNumber()), DefaultBlockParameterName.LATEST, CONTRACT_ADDRESS);
	//
	// 	Event event = new Event("Transfer",
	// 		Arrays.asList(
	// 			new TypeReference<Address>(true) {
	// 				// from
	// 			},
	// 			new TypeReference<Address>(true) {
	// 			},
	// 			new TypeReference<Uint256>(false) {
	// 				// amount
	// 			}
	// 		));
	// 	String topicData = EventEncoder.encode(event);
	// 	ethFilter.addSingleTopic(topicData);
	// 	ethFilter.addNullTopic();// filter: event type (topic[0])
	// 	//ethFilter.addOptionalTopics("0x"+ TypeEncoder.encode(new Address("")));
	//
	// 	return ethFilter;
	// }
}
