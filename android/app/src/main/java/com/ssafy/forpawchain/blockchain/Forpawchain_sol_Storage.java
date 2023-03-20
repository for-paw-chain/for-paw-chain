package com.ssafy.forpawchain.blockchain;

import android.util.Log;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class Forpawchain_sol_Storage extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506108aa806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80638f88708b14610046578063a828f0bd14610074578063de8fa43114610089575b600080fd5b6100596100543660046104ee565b61009a565b60405161006b9695949392919061054d565b60405180910390f35b610087610082366004610656565b610398565b005b60005460405190815260200161006b565b600080606080606080600087815481106100b6576100b6610715565b9060005260206000200154600188815481106100d4576100d4610715565b9060005260206000200154600289815481106100f2576100f2610715565b9060005260206000200160038a8154811061010f5761010f610715565b9060005260206000200160048b8154811061012c5761012c610715565b9060005260206000200160058c8154811061014957610149610715565b9060005260206000200183805461015f9061072b565b80601f016020809104026020016040519081016040528092919081815260200182805461018b9061072b565b80156101d85780601f106101ad576101008083540402835291602001916101d8565b820191906000526020600020905b8154815290600101906020018083116101bb57829003601f168201915b505050505093508280546101eb9061072b565b80601f01602080910402602001604051908101604052809291908181526020018280546102179061072b565b80156102645780601f1061023957610100808354040283529160200191610264565b820191906000526020600020905b81548152906001019060200180831161024757829003601f168201915b505050505092508180546102779061072b565b80601f01602080910402602001604051908101604052809291908181526020018280546102a39061072b565b80156102f05780601f106102c5576101008083540402835291602001916102f0565b820191906000526020600020905b8154815290600101906020018083116102d357829003601f168201915b505050505091508080546103039061072b565b80601f016020809104026020016040519081016040528092919081815260200182805461032f9061072b565b801561037c5780601f106103515761010080835404028352916020019161037c565b820191906000526020600020905b81548152906001019060200180831161035f57829003601f168201915b5050505050905095509550955095509550955091939550919395565b60008054600181810183557f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563909101889055805480820182557fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6018790556002805491820181559091527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace0161042e85826107b4565b50600380546001810182556000919091527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b0161046b84826107b4565b50600480546001810182556000919091527f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b016104a883826107b4565b50600580546001810182556000919091527f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db0016104e582826107b4565b50505050505050565b60006020828403121561050057600080fd5b5035919050565b6000815180845260005b8181101561052d57602081850181015186830182015201610511565b506000602082860101526020601f19601f83011685010191505092915050565b86815285602082015260c06040820152600061056c60c0830187610507565b828103606084015261057e8187610507565b905082810360808401526105928186610507565b905082810360a08401526105a68185610507565b9998505050505050505050565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126105da57600080fd5b813567ffffffffffffffff808211156105f5576105f56105b3565b604051601f8301601f19908116603f0116810190828211818310171561061d5761061d6105b3565b8160405283815286602085880101111561063657600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060008060008060c0878903121561066f57600080fd5b8635955060208701359450604087013567ffffffffffffffff8082111561069557600080fd5b6106a18a838b016105c9565b955060608901359150808211156106b757600080fd5b6106c38a838b016105c9565b945060808901359150808211156106d957600080fd5b6106e58a838b016105c9565b935060a08901359150808211156106fb57600080fd5b5061070889828a016105c9565b9150509295509295509295565b634e487b7160e01b600052603260045260246000fd5b600181811c9082168061073f57607f821691505b60208210810361075f57634e487b7160e01b600052602260045260246000fd5b50919050565b601f8211156107af57600081815260208120601f850160051c8101602086101561078c5750805b601f850160051c820191505b818110156107ab57828155600101610798565b5050505b505050565b815167ffffffffffffffff8111156107ce576107ce6105b3565b6107e2816107dc845461072b565b84610765565b602080601f83116001811461081757600084156107ff5750858301515b600019600386901b1c1916600185901b1785556107ab565b600085815260208120601f198616915b8281101561084657888601518255948401946001909101908401610827565b50858210156108645787850151600019600388901b60f8161c191681555b5050505050600190811b0190555056fea26469706673582212203d0870b125d8cfc37d4a083cfaaf2010e3df24a283ae1a6b4f145d8b491f8b7564736f6c63430008130033";

    public static final String FUNC_GETSIZE = "getSize";

    public static final String FUNC_RETRIEVE = "retrieve";

    public static final String FUNC_STORE = "store";

    @Deprecated
    protected Forpawchain_sol_Storage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Forpawchain_sol_Storage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Forpawchain_sol_Storage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Forpawchain_sol_Storage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<?> getSize() {
        final Function function = new Function(FUNC_GETSIZE,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<List<Type>> retrieve(BigInteger idx) {
        Function function = new Function(
                "retrieve", // 호출할 함수 이름
                Collections.singletonList(new Uint256(idx)), // 인수 목록
                Arrays.asList(
                        new TypeReference<Uint256>() {
                        }, // 반환 값 형식 목록
                        new TypeReference<Uint256>() {
                        },
                        new TypeReference<Utf8String>() {
                        },
                        new TypeReference<Utf8String>() {
                        },
                        new TypeReference<Utf8String>() {
                        },
                        new TypeReference<Utf8String>() {
                        }
                )
        );
//        try {
//            RemoteCall<List<Type>> remoteCall = executeRemoteCallMultipleValueReturn(function);
//            List<Type> result = remoteCall.send();
//            Uint256 value1 = (Uint256) result.get(0);
//            Uint256 value2 = (Uint256) result.get(1);
//            Utf8String value3 = (Utf8String) result.get(2);
//            Utf8String value4 = (Utf8String) result.get(3);
//            Utf8String value5 = (Utf8String) result.get(4);
//            Utf8String value6 = (Utf8String) result.get(5);
//            Log.d("TAG", "");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        return executeRemoteCallMultipleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> store(BigInteger num, BigInteger d, String t, String c, String hash1, String hash2) {
        final Function function = new Function(
                FUNC_STORE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(num),
                        new org.web3j.abi.datatypes.generated.Uint256(d),
                        new org.web3j.abi.datatypes.Utf8String(t),
                        new org.web3j.abi.datatypes.Utf8String(c),
                        new org.web3j.abi.datatypes.Utf8String(hash1),
                        new org.web3j.abi.datatypes.Utf8String(hash2)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Forpawchain_sol_Storage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Forpawchain_sol_Storage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Forpawchain_sol_Storage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Forpawchain_sol_Storage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Forpawchain_sol_Storage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Forpawchain_sol_Storage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Forpawchain_sol_Storage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Forpawchain_sol_Storage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Forpawchain_sol_Storage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Forpawchain_sol_Storage.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Forpawchain_sol_Storage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Forpawchain_sol_Storage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Forpawchain_sol_Storage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Forpawchain_sol_Storage.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Forpawchain_sol_Storage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Forpawchain_sol_Storage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
