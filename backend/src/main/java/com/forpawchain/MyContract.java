package com.forpawchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

// https://github.com/web3j/web3j/issues/1710
public class MyContract extends Contract {

	@Value("${web3.bytecode}")
	private static String BINARY;

	protected static final HashMap<String, String> _addresses;

	static {
		_addresses = new HashMap<>();
	}

	protected MyContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	protected MyContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
	}

	public static RemoteCall<MyContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		return deployRemoteCall(MyContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
	}

	public RemoteCall<TransactionReceipt> MyContractFunction() {
		final Function function = new Function(
			"MyContractFunction",
			Arrays.<Type>asList(),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public static MyContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		return new MyContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	protected String getStaticDeployedAddress(String networkId) {
		return _addresses.get(networkId);
	}

	public static String getPreviouslyDeployedAddress(String networkId) {
		return _addresses.get(networkId);
	}
}
