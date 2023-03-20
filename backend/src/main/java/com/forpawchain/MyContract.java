package com.forpawchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

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

	private static final String BINARY = "0x60606040523415600e57600080fd5b60848061001c6000396000f300606060405260043610603f576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063b818dacd146044575b600080fd5b3415604e57600080fd5b60546056565b005b5600a165627a7a72305820bebcbbdf06550591bc772dfcb0eadc842f95953869feb7a9528bac91487d95240029";

	protected static final HashMap<String, String> _addresses;

	static {
		_addresses = new HashMap<>();
	}

	protected MyContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	protected MyContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	public static RemoteCall<MyContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		return deployRemoteCall(MyContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
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

	public static MyContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		return new MyContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
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
