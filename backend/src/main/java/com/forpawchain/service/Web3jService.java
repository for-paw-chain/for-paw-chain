package com.forpawchain.service;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthAccounts;
// import org.web3j.protocol.Web3j;
// import org.web3j.protocol.core.DefaultBlockParameter;
// import org.web3j.protocol.core.methods.response.EthAccounts;
// import org.web3j.protocol.core.methods.response.EthBlockNumber;
// import org.web3j.protocol.core.methods.response.EthGetBalance;
// import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
@Service("web3jService")
public class Web3jService {

	private final Web3j web3j;
	// private final NFT nft;

	@Autowired
	public Web3jService(Web3j web3j) {
		this.web3j = web3j;
	}

	@Value("${metamask.WALLET_ADDRESS}")
	private String WALLET_ADDRESS;

	@Value("${metamask.CONTRACT_ADDRESS}")
	private String CONTRACT_ADDRESS;


	// 현재 블록 번호
	// public EthBlockNumber getBlockNumber() throws ExecutionException, InterruptedException {
	// 	return web3j.ethBlockNumber().sendAsync().get();
	// }

	// 지정된 주소의 계정
	public EthAccounts getEthAccounts() throws ExecutionException, InterruptedException {
		return web3j.ethAccounts().sendAsync().get();
	}

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
