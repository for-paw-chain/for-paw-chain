package com.ssafy.forpawchain.blockchain;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
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
public class Test_sol_ForPawChain extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610bba806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80633129e77314610067578063c8691b2a14610091578063cb544212146100b5578063dcc77ce6146100ca578063de8fa431146100dd578063e2883a76146100ef575b600080fd5b61007a610075366004610786565b610102565b6040516100889291906107e5565b60405180910390f35b6100a461009f366004610786565b61026e565b604051610088959493929190610813565b6100c86100c3366004610942565b610547565b005b6100c86100d83660046109af565b610608565b6001545b604051908152602001610088565b6100e16100fd366004610786565b6106f7565b60608060008381548110610118576101186109fe565b90600052602060002090600202016000016000848154811061013c5761013c6109fe565b906000526020600020906002020160010181805461015990610a14565b80601f016020809104026020016040519081016040528092919081815260200182805461018590610a14565b80156101d25780601f106101a7576101008083540402835291602001916101d2565b820191906000526020600020905b8154815290600101906020018083116101b557829003601f168201915b505050505091508080546101e590610a14565b80601f016020809104026020016040519081016040528092919081815260200182805461021190610a14565b801561025e5780601f106102335761010080835404028352916020019161025e565b820191906000526020600020905b81548152906001019060200180831161024157829003601f168201915b5050505050905091509150915091565b606080600060608060018681548110610289576102896109fe565b9060005260206000209060050201600001600187815481106102ad576102ad6109fe565b9060005260206000209060050201600101600188815481106102d1576102d16109fe565b906000526020600020906005020160020160009054906101000a90046001600160a01b031660018981548110610309576103096109fe565b906000526020600020906005020160030160018a8154811061032d5761032d6109fe565b906000526020600020906005020160040184805461034a90610a14565b80601f016020809104026020016040519081016040528092919081815260200182805461037690610a14565b80156103c35780601f10610398576101008083540402835291602001916103c3565b820191906000526020600020905b8154815290600101906020018083116103a657829003601f168201915b505050505094508380546103d690610a14565b80601f016020809104026020016040519081016040528092919081815260200182805461040290610a14565b801561044f5780601f106104245761010080835404028352916020019161044f565b820191906000526020600020905b81548152906001019060200180831161043257829003601f168201915b50505050509350818054806020026020016040519081016040528092919081815260200182805480156104a157602002820191906000526020600020905b81548152602001906001019080831161048d575b505050505091508080546104b490610a14565b80601f01602080910402602001604051908101604052809291908181526020018280546104e090610a14565b801561052d5780601f106105025761010080835404028352916020019161052d565b820191906000526020600020905b81548152906001019060200180831161051057829003601f168201915b505050505090509450945094509450945091939590929450565b604080518082019091528281526020810182905260008054600181018255908052815182916002027f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e5630190819061059e9082610a9d565b50602082015160018201906105b39082610a9d565b505050600184815481106105c9576105c96109fe565b906000526020600020906005020160030160016000805490506105ec9190610b5d565b8154600181018355600092835260209092209091015550505050565b6040805160a081018252848152602080820185905233828401819052835160008082529281019094526060830193909352608082018490526001805480820182559152815182916005027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6019081906106819082610a9d565b50602082015160018201906106969082610a9d565b5060408201516002820180546001600160a01b0319166001600160a01b03909216919091179055606082015180516106d8916003840191602090910190610726565b50608082015160048201906106ed9082610a9d565b5050505050505050565b60006001828154811061070c5761070c6109fe565b600091825260209091206003600590920201015492915050565b828054828255906000526020600020908101928215610761579160200282015b82811115610761578251825591602001919060010190610746565b5061076d929150610771565b5090565b5b8082111561076d5760008155600101610772565b60006020828403121561079857600080fd5b5035919050565b6000815180845260005b818110156107c5576020818501810151868301820152016107a9565b506000602082860101526020601f19601f83011685010191505092915050565b6040815260006107f8604083018561079f565b828103602084015261080a818561079f565b95945050505050565b60a08152600061082660a083018861079f565b602083820381850152610839828961079f565b6001600160a01b03881660408601528481036060860152865180825282880193509082019060005b8181101561087d57845183529383019391830191600101610861565b50508481036080860152610891818761079f565b9a9950505050505050505050565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126108c657600080fd5b813567ffffffffffffffff808211156108e1576108e161089f565b604051601f8301601f19908116603f011681019082821181831017156109095761090961089f565b8160405283815286602085880101111561092257600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060006060848603121561095757600080fd5b83359250602084013567ffffffffffffffff8082111561097657600080fd5b610982878388016108b5565b9350604086013591508082111561099857600080fd5b506109a5868287016108b5565b9150509250925092565b6000806000606084860312156109c457600080fd5b833567ffffffffffffffff808211156109dc57600080fd5b6109e8878388016108b5565b9450602086013591508082111561097657600080fd5b634e487b7160e01b600052603260045260246000fd5b600181811c90821680610a2857607f821691505b602082108103610a4857634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115610a9857600081815260208120601f850160051c81016020861015610a755750805b601f850160051c820191505b81811015610a9457828155600101610a81565b5050505b505050565b815167ffffffffffffffff811115610ab757610ab761089f565b610acb81610ac58454610a14565b84610a4e565b602080601f831160018114610b005760008415610ae85750858301515b600019600386901b1c1916600185901b178555610a94565b600085815260208120601f198616915b82811015610b2f57888601518255948401946001909101908401610b10565b5085821015610b4d5787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b81810381811115610b7e57634e487b7160e01b600052601160045260246000fd5b9291505056fea2646970667358221220708736f7913e4516263f10035f12e7d7dac7f321a6d9435ec5a8e47212ea1f3564736f6c63430008130033";

    public static final String FUNC_ADDHISTORY = "addHistory";

    public static final String FUNC_ADDITEM = "addItem";

    public static final String FUNC_GETHISTORY = "getHistory";

    public static final String FUNC_GETITEM = "getItem";

    public static final String FUNC_GETITEMSIZE = "getItemSize";

    public static final String FUNC_GETSIZE = "getSize";

    @Deprecated
    protected Test_sol_ForPawChain(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Test_sol_ForPawChain(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Test_sol_ForPawChain(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Test_sol_ForPawChain(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addHistory(String _title, String _body, String _fileHash) {
        final Function function = new Function(
                FUNC_ADDHISTORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_title), 
                new org.web3j.abi.datatypes.Utf8String(_body), 
                new org.web3j.abi.datatypes.Utf8String(_fileHash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addItem(BigInteger index, String _title, String _body) {
        final Function function = new Function(
                FUNC_ADDITEM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index), 
                new org.web3j.abi.datatypes.Utf8String(_title), 
                new org.web3j.abi.datatypes.Utf8String(_body)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<?> getHistory(BigInteger index) {
        final Function function = new Function(
                FUNC_GETHISTORY,
                // 인자 타입
                Collections.singletonList(new Uint256(index)),
                // 리턴 타입
                Arrays.asList(
                        new TypeReference<Utf8String>() {
                        },
                        new TypeReference<Utf8String>() {
                        },
                        new TypeReference<Address>() {
                        },
                        new TypeReference<DynamicArray<Uint256>>() {
                        },
                        new TypeReference<Utf8String>() {
                        }
                ));
        return executeRemoteCallMultipleValueReturn(function);
    }

    public RemoteCall<?> getItem(BigInteger index) {
        final Function function = new Function(
                FUNC_GETITEM,
                Collections.singletonList(new Uint256(index)),
                Arrays.asList(
                        new TypeReference<Utf8String>() {
                        },
                        new TypeReference<Utf8String>() {
                        }
                ));
        return executeRemoteCallMultipleValueReturn(function);
    }

    public BigInteger getItemSize(BigInteger index) throws IOException {
        final Function function = new Function(
                FUNC_GETITEMSIZE,
                Collections.singletonList(new Uint256(index)),
                Collections.singletonList(
                        new TypeReference<Uint256>() {
                        }
                ));
        return executeCallSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger getSize() throws IOException {
        final Function function = new Function(
                FUNC_GETSIZE,
                Collections.emptyList(),
                Collections.singletonList(
                        new TypeReference<Uint256>() {
                        }
                ));
        return executeCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Test_sol_ForPawChain load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Test_sol_ForPawChain(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Test_sol_ForPawChain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Test_sol_ForPawChain(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Test_sol_ForPawChain load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Test_sol_ForPawChain(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Test_sol_ForPawChain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Test_sol_ForPawChain(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Test_sol_ForPawChain> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Test_sol_ForPawChain.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Test_sol_ForPawChain> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Test_sol_ForPawChain.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Test_sol_ForPawChain> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Test_sol_ForPawChain.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Test_sol_ForPawChain> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Test_sol_ForPawChain.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
