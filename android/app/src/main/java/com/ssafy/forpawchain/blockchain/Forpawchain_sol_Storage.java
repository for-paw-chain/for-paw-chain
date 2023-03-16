package com.ssafy.forpawchain.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.primitive.Byte;
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
    private static final String BINARY = "608060405234801561001057600080fd5b50610c82806100206000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c8063a828f0bd11610066578063a828f0bd1461010f578063d60c4b2a14610124578063de8fa43114610137578063f1bb37571461013f578063fc5636581461015257600080fd5b80630b7ad54c146100985780632b57298b146100c15780632e64cec1146100e2578063929a79b1146100fc575b600080fd5b6100ab6100a63660046107fd565b610165565b6040516100b8919061085c565b60405180910390f35b6100d46100cf3660046107fd565b610214565b6040519081526020016100b8565b6100ea61023b565b6040516100b896959493929190610909565b6100ab61010a3660046107fd565b610654565b61012261011d366004610a2e565b610669565b005b6100ab6101323660046107fd565b6107bf565b6000546100d4565b6100ab61014d3660046107fd565b6107d4565b6100d46101603660046107fd565b6107e9565b60606003828154811061017a5761017a610aed565b90600052602060002001805461018f90610b03565b80601f01602080910402602001604051908101604052809291908181526020018280546101bb90610b03565b80156102085780601f106101dd57610100808354040283529160200191610208565b820191906000526020600020905b8154815290600101906020018083116101eb57829003601f168201915b50505050509050919050565b60006001828154811061022957610229610aed565b90600052602060002001549050919050565b6060806060806060806000600160026003600460058580548060200260200160405190810160405280929190818152602001828054801561029b57602002820191906000526020600020905b815481526020019060010190808311610287575b50505050509550848054806020026020016040519081016040528092919081815260200182805480156102ed57602002820191906000526020600020905b8154815260200190600101908083116102d9575b5050505050945083805480602002602001604051908101604052809291908181526020016000905b828210156103c157838290600052602060002001805461033490610b03565b80601f016020809104026020016040519081016040528092919081815260200182805461036090610b03565b80156103ad5780601f10610382576101008083540402835291602001916103ad565b820191906000526020600020905b81548152906001019060200180831161039057829003601f168201915b505050505081526020019060010190610315565b50505050935082805480602002602001604051908101604052809291908181526020016000905b8282101561049457838290600052602060002001805461040790610b03565b80601f016020809104026020016040519081016040528092919081815260200182805461043390610b03565b80156104805780601f1061045557610100808354040283529160200191610480565b820191906000526020600020905b81548152906001019060200180831161046357829003601f168201915b5050505050815260200190600101906103e8565b50505050925081805480602002602001604051908101604052809291908181526020016000905b828210156105675783829060005260206000200180546104da90610b03565b80601f016020809104026020016040519081016040528092919081815260200182805461050690610b03565b80156105535780601f1061052857610100808354040283529160200191610553565b820191906000526020600020905b81548152906001019060200180831161053657829003601f168201915b5050505050815260200190600101906104bb565b50505050915080805480602002602001604051908101604052809291908181526020016000905b8282101561063a5783829060005260206000200180546105ad90610b03565b80601f01602080910402602001604051908101604052809291908181526020018280546105d990610b03565b80156106265780601f106105fb57610100808354040283529160200191610626565b820191906000526020600020905b81548152906001019060200180831161060957829003601f168201915b50505050508152602001906001019061058e565b505050509050955095509550955095509550909192939495565b60606002828154811061017a5761017a610aed565b60008054600181810183557f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563909101889055805480820182557fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6018790556002805491820181559091527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace016106ff8582610b8c565b50600380546001810182556000919091527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b0161073c8482610b8c565b50600480546001810182556000919091527f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b016107798382610b8c565b50600580546001810182556000919091527f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db0016107b68282610b8c565b50505050505050565b60606005828154811061017a5761017a610aed565b60606004828154811061017a5761017a610aed565b600080828154811061022957610229610aed565b60006020828403121561080f57600080fd5b5035919050565b6000815180845260005b8181101561083c57602081850181015186830182015201610820565b506000602082860101526020601f19601f83011685010191505092915050565b60208152600061086f6020830184610816565b9392505050565b600081518084526020808501945080840160005b838110156108a65781518752958201959082019060010161088a565b509495945050505050565b600082825180855260208086019550808260051b84010181860160005b848110156108fc57601f198684030189526108ea838351610816565b988401989250908301906001016108ce565b5090979650505050505050565b60c08152600061091c60c0830189610876565b828103602084015261092e8189610876565b9050828103604084015261094281886108b1565b9050828103606084015261095681876108b1565b9050828103608084015261096a81866108b1565b905082810360a084015261097e81856108b1565b9998505050505050505050565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126109b257600080fd5b813567ffffffffffffffff808211156109cd576109cd61098b565b604051601f8301601f19908116603f011681019082821181831017156109f5576109f561098b565b81604052838152866020858801011115610a0e57600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060008060008060c08789031215610a4757600080fd5b8635955060208701359450604087013567ffffffffffffffff80821115610a6d57600080fd5b610a798a838b016109a1565b95506060890135915080821115610a8f57600080fd5b610a9b8a838b016109a1565b94506080890135915080821115610ab157600080fd5b610abd8a838b016109a1565b935060a0890135915080821115610ad357600080fd5b50610ae089828a016109a1565b9150509295509295509295565b634e487b7160e01b600052603260045260246000fd5b600181811c90821680610b1757607f821691505b602082108103610b3757634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115610b8757600081815260208120601f850160051c81016020861015610b645750805b601f850160051c820191505b81811015610b8357828155600101610b70565b5050505b505050565b815167ffffffffffffffff811115610ba657610ba661098b565b610bba81610bb48454610b03565b84610b3d565b602080601f831160018114610bef5760008415610bd75750858301515b600019600386901b1c1916600185901b178555610b83565b600085815260208120601f198616915b82811015610c1e57888601518255948401946001909101908401610bff565b5085821015610c3c5787850151600019600388901b60f8161c191681555b5050505050600190811b0190555056fea264697066735822122005ebde66b20a8a5141026be4f2639d992c51dac2e5abf4d94d0f657af06eb30764736f6c63430008130033";

    public static final String FUNC_GETCONTENT = "getContent";

    public static final String FUNC_GETDATE = "getDate";

    public static final String FUNC_GETHASH1 = "getHash1";

    public static final String FUNC_GETHASH2 = "getHash2";

    public static final String FUNC_GETNUMBER = "getNumber";

    public static final String FUNC_GETSIZE = "getSize";

    public static final String FUNC_GETTITLE = "getTitle";

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

    public RemoteCall<?> getContent(BigInteger idx) {
        final Function function = new Function(FUNC_GETCONTENT,
                Collections.singletonList(new Uint256(idx)),
                Collections.singletonList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<?> getDate(BigInteger idx) {
        final Function function = new Function(FUNC_GETDATE,
                Collections.singletonList(new Uint256(idx)),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<?> getHash1(BigInteger idx) {
        final Function function = new Function(FUNC_GETHASH1,
                Collections.singletonList(new Uint256(idx)),
                Collections.singletonList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<?> getHash2(BigInteger idx) {
        final Function function = new Function(FUNC_GETHASH2,
                Collections.singletonList(new Uint256(idx)),
                Collections.singletonList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<?> getNumber(BigInteger idx) {
        final Function function = new Function(FUNC_GETNUMBER,
                Collections.singletonList(new Uint256(idx)),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<?> getSize() {
        final Function function = new Function(FUNC_GETSIZE,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<?> getTitle(BigInteger idx) {
        final Function function = new Function(FUNC_GETTITLE,
                Collections.singletonList(new Uint256(idx)),
                Collections.singletonList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<?> retrieve() {
        final Function function = new Function(
                FUNC_RETRIEVE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
