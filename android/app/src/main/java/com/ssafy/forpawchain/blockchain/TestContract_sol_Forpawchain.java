package com.ssafy.forpawchain.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class TestContract_sol_Forpawchain extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610d53806100206000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c806386ebc6bc1161006657806386ebc6bc146101075780638f88708b1461011c578063a78dac0d1461013f578063baa0b80614610152578063de8fa4311461016557600080fd5b8063210ab8c71461009857806335a1223a146100c1578063486556ce146100e157806360c757ba146100f4575b600080fd5b6100ab6100a6366004610845565b610176565b6040516100b891906108a4565b60405180910390f35b6100d46100cf366004610845565b61026c565b6040516100b89190610906565b6100d46100ef366004610845565b610318565b6100d4610102366004610845565b610328565b61011a610115366004610a76565b610338565b005b61012f61012a366004610845565b610473565b6040516100b89493929190610b48565b6100d461014d366004610ba0565b61072c565b6100d4610160366004610845565b610770565b6004546040519081526020016100b8565b60606004828154811061018b5761018b610bc2565b90600052602060002001805480602002602001604051908101604052809291908181526020016000905b828210156102615783829060005260206000200180546101d490610bd8565b80601f016020809104026020016040519081016040528092919081815260200182805461020090610bd8565b801561024d5780601f106102225761010080835404028352916020019161024d565b820191906000526020600020905b81548152906001019060200180831161023057829003601f168201915b5050505050815260200190600101906101b5565b505050509050919050565b6001818154811061027c57600080fd5b90600052602060002001600091509050805461029790610bd8565b80601f01602080910402602001604051908101604052809291908181526020018280546102c390610bd8565b80156103105780601f106102e557610100808354040283529160200191610310565b820191906000526020600020905b8154815290600101906020018083116102f357829003601f168201915b505050505081565b6003818154811061027c57600080fd5b6000818154811061027c57600080fd5b600080546001810182559080527f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563016103718682610c5d565b506001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6016103ad8582610c5d565b50600280546001810182556000919091527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace016103ea8482610c5d565b50600380546001810182556000919091527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b016104278382610c5d565b5060048054600181018255600091909152815161046b917f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b01906020840190610780565b505050505050565b6060806060806000858154811061048c5761048c610bc2565b90600052602060002001600186815481106104a9576104a9610bc2565b90600052602060002001600287815481106104c6576104c6610bc2565b90600052602060002001600388815481106104e3576104e3610bc2565b906000526020600020018380546104f990610bd8565b80601f016020809104026020016040519081016040528092919081815260200182805461052590610bd8565b80156105725780601f1061054757610100808354040283529160200191610572565b820191906000526020600020905b81548152906001019060200180831161055557829003601f168201915b5050505050935082805461058590610bd8565b80601f01602080910402602001604051908101604052809291908181526020018280546105b190610bd8565b80156105fe5780601f106105d3576101008083540402835291602001916105fe565b820191906000526020600020905b8154815290600101906020018083116105e157829003601f168201915b5050505050925081805461061190610bd8565b80601f016020809104026020016040519081016040528092919081815260200182805461063d90610bd8565b801561068a5780601f1061065f5761010080835404028352916020019161068a565b820191906000526020600020905b81548152906001019060200180831161066d57829003601f168201915b5050505050915080805461069d90610bd8565b80601f01602080910402602001604051908101604052809291908181526020018280546106c990610bd8565b80156107165780601f106106eb57610100808354040283529160200191610716565b820191906000526020600020905b8154815290600101906020018083116106f957829003601f168201915b5050505050905093509350935093509193509193565b6004828154811061073c57600080fd5b90600052602060002001818154811061075457600080fd5b9060005260206000200160009150915050805461029790610bd8565b6002818154811061027c57600080fd5b8280548282559060005260206000209081019282156107c6579160200282015b828111156107c657825182906107b69082610c5d565b50916020019190600101906107a0565b506107d29291506107d6565b5090565b808211156107d25760006107ea82826107f3565b506001016107d6565b5080546107ff90610bd8565b6000825580601f1061080f575050565b601f01602090049060005260206000209081019061082d9190610830565b50565b5b808211156107d25760008155600101610831565b60006020828403121561085757600080fd5b5035919050565b6000815180845260005b8181101561088457602081850181015186830182015201610868565b506000602082860101526020601f19601f83011685010191505092915050565b6000602080830181845280855180835260408601915060408160051b870101925083870160005b828110156108f957603f198886030184526108e785835161085e565b945092850192908501906001016108cb565b5092979650505050505050565b602081526000610919602083018461085e565b9392505050565b634e487b7160e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff8111828210171561095f5761095f610920565b604052919050565b600082601f83011261097857600080fd5b813567ffffffffffffffff81111561099257610992610920565b6109a5601f8201601f1916602001610936565b8181528460208386010111156109ba57600080fd5b816020850160208301376000918101602001919091529392505050565b600082601f8301126109e857600080fd5b8135602067ffffffffffffffff80831115610a0557610a05610920565b8260051b610a14838201610936565b9384528581018301938381019088861115610a2e57600080fd5b84880192505b85831015610a6a57823584811115610a4c5760008081fd5b610a5a8a87838c0101610967565b8352509184019190840190610a34565b98975050505050505050565b600080600080600060a08688031215610a8e57600080fd5b853567ffffffffffffffff80821115610aa657600080fd5b610ab289838a01610967565b96506020880135915080821115610ac857600080fd5b610ad489838a01610967565b95506040880135915080821115610aea57600080fd5b610af689838a01610967565b94506060880135915080821115610b0c57600080fd5b610b1889838a01610967565b93506080880135915080821115610b2e57600080fd5b50610b3b888289016109d7565b9150509295509295909350565b608081526000610b5b608083018761085e565b8281036020840152610b6d818761085e565b90508281036040840152610b81818661085e565b90508281036060840152610b95818561085e565b979650505050505050565b60008060408385031215610bb357600080fd5b50508035926020909101359150565b634e487b7160e01b600052603260045260246000fd5b600181811c90821680610bec57607f821691505b602082108103610c0c57634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115610c5857600081815260208120601f850160051c81016020861015610c395750805b601f850160051c820191505b8181101561046b57828155600101610c45565b505050565b815167ffffffffffffffff811115610c7757610c77610920565b610c8b81610c858454610bd8565b84610c12565b602080601f831160018114610cc05760008415610ca85750858301515b600019600386901b1c1916600185901b17855561046b565b600085815260208120601f198616915b82811015610cef57888601518255948401946001909101908401610cd0565b5085821015610d0d5787850151600019600388901b60f8161c191681555b5050505050600190811b0190555056fea26469706673582212209878bb4296fb8bece4e8868ce9ea770f75f99cfe84e88cecca943abb3a300fc064736f6c63430008130033";

    public static final String FUNC_CONTENT = "content";

    public static final String FUNC_DATE = "date";

    public static final String FUNC_GETSIZE = "getSize";

    public static final String FUNC_HASH = "hash";

    public static final String FUNC_RETRIEVE = "retrieve";

    public static final String FUNC_RETRIEVEHASH = "retrieveHash";

    public static final String FUNC_STORE = "store";

    public static final String FUNC_TITLE = "title";

    public static final String FUNC_WA = "wa";

    @Deprecated
    protected TestContract_sol_Forpawchain(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TestContract_sol_Forpawchain(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TestContract_sol_Forpawchain(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TestContract_sol_Forpawchain(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> content(BigInteger param0) {
        final Function function = new Function(
                FUNC_CONTENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> date(BigInteger param0) {
        final Function function = new Function(
                FUNC_DATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> getSize() {
        final Function function = new Function(
                FUNC_GETSIZE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> hash(BigInteger param0, BigInteger param1) {
        final Function function = new Function(
                FUNC_HASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> retrieve(BigInteger idx) {
        final Function function = new Function(
                FUNC_RETRIEVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(idx)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> retrieveHash(BigInteger idx) {
        final Function function = new Function(
                FUNC_RETRIEVEHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(idx)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> store(String d, String t, String c, String w, List<String> h) {
        final Function function = new Function(
                FUNC_STORE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(d), 
                new org.web3j.abi.datatypes.Utf8String(t), 
                new org.web3j.abi.datatypes.Utf8String(c), 
                new org.web3j.abi.datatypes.Utf8String(w), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.Utils.typeMap(h, org.web3j.abi.datatypes.Utf8String.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> title(BigInteger param0) {
        final Function function = new Function(
                FUNC_TITLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> wa(BigInteger param0) {
        final Function function = new Function(
                FUNC_WA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static TestContract_sol_Forpawchain load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestContract_sol_Forpawchain(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TestContract_sol_Forpawchain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestContract_sol_Forpawchain(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TestContract_sol_Forpawchain load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TestContract_sol_Forpawchain(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TestContract_sol_Forpawchain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TestContract_sol_Forpawchain(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TestContract_sol_Forpawchain> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestContract_sol_Forpawchain.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestContract_sol_Forpawchain> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestContract_sol_Forpawchain.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TestContract_sol_Forpawchain> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestContract_sol_Forpawchain.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestContract_sol_Forpawchain> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestContract_sol_Forpawchain.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
