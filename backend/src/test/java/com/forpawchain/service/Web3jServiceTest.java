package com.forpawchain.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.protocol.core.methods.response.EthAccounts;
// import org.web3j.protocol.core.methods.response.EthAccounts;

@SpringBootTest
class Web3jServiceTest {

	private Web3jService web3jService;

	@Autowired
	public Web3jServiceTest(Web3jService web3jService) {
		this.web3jService = web3jService;
	}

	// @Test
	// public void getBlockNumber() throws ExecutionException, InterruptedException {
	// 	EthBlockNumber ethBlockNumber = web3jService.getBlockNumber();
	// 	long id = ethBlockNumber.getId();
	// 	BigInteger blockNumber = ethBlockNumber.getBlockNumber();
	//
	// 	System.out.println("id = " + id);
	// 	System.out.println("blockNumber = " + blockNumber);
	// }

	@Test
	public void getEthAccounts() throws ExecutionException, InterruptedException {

		System.out.println(web3jService);
		EthAccounts ethAccounts = web3jService.getEthAccounts();
		// List<String> accounts = ethAccounts.getAccounts();
		// System.out.println("accounts = " + accounts);
		//
		// Assertions.assertThat(accounts.get(0)).isEqualTo("0x19020C8DE459be59C3406770520C961DA2fda091");
	}

	// @Test
	// public void getTransactionCount() throws ExecutionException, InterruptedException {
	// 	EthGetTransactionCount ethGetTransactionCount = web3jService.getTransactionCount();
	// 	BigInteger transactionCount = ethGetTransactionCount.getTransactionCount();
	// 	System.out.println("transactionCount = " + transactionCount);
	// }
	//
	// @Test
	// public void getEthBalance() throws ExecutionException, InterruptedException {
	// 	EthGetBalance ethGetBalance = web3jService.getEthBalance();
	// 	BigInteger balance = ethGetBalance.getBalance();
	// 	System.out.println("balance = " + balance);
	// }
	//
	// @Test
	// public void getContractName() throws Exception {
	// 	String contractName = web3jService.getContractName();
	// 	System.out.println("contractName = " + contractName);
	// }
	//
	// @Test
	// public void currentCount() throws Exception {
	// 	BigInteger currentCount = web3jService.currentCount();
	// 	System.out.println("currentCount = " + currentCount);
	// }
	//
	// @Test
	// public void nftCreate() throws ExecutionException, InterruptedException {
	// 	TransactionReceipt transactionReceipt = web3jService.nftCreate();
	// 	System.out.println("transactionReceipt = " + transactionReceipt);
	// }
	//
	// @Test
	// public void transferEventFlowable() throws Exception {
	// 	web3jService.transferEventFlowable();
	// }
}