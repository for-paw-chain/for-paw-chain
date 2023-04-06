package com.ssafy.forpawchain.blockchain;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class Test_sol_MyContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610ccd806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80633129e77314610067578063ab0ce2ca14610091578063c8691b2a146100a6578063cb544212146100ca578063de8fa431146100dd578063e2883a76146100ef575b600080fd5b61007a610075366004610786565b610102565b6040516100889291906107e5565b60405180910390f35b6100a461009f36600461094a565b61026e565b005b6100b96100b4366004610786565b61035d565b604051610088959493929190610a18565b6100a46100d8366004610aa4565b610636565b6001545b604051908152602001610088565b6100e16100fd366004610786565b6106f7565b6060806000838154811061011857610118610b11565b90600052602060002090600202016000016000848154811061013c5761013c610b11565b906000526020600020906002020160010181805461015990610b27565b80601f016020809104026020016040519081016040528092919081815260200182805461018590610b27565b80156101d25780601f106101a7576101008083540402835291602001916101d2565b820191906000526020600020905b8154815290600101906020018083116101b557829003601f168201915b505050505091508080546101e590610b27565b80601f016020809104026020016040519081016040528092919081815260200182805461021190610b27565b801561025e5780601f106102335761010080835404028352916020019161025e565b820191906000526020600020905b81548152906001019060200180831161024157829003601f168201915b5050505050905091509150915091565b6040805160a081018252868152602081018690526001600160a01b038516918101919091526060810183905260808101829052600180548082018255600091909152815182916005027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6019081906102e69082610bb0565b50602082015160018201906102fb9082610bb0565b5060408201516002820180546001600160a01b0319166001600160a01b039092169190911790556060820151805161033d916003840191602090910190610726565b50608082015160048201906103529082610bb0565b505050505050505050565b60608060006060806001868154811061037857610378610b11565b90600052602060002090600502016000016001878154811061039c5761039c610b11565b9060005260206000209060050201600101600188815481106103c0576103c0610b11565b906000526020600020906005020160020160009054906101000a90046001600160a01b0316600189815481106103f8576103f8610b11565b906000526020600020906005020160030160018a8154811061041c5761041c610b11565b906000526020600020906005020160040184805461043990610b27565b80601f016020809104026020016040519081016040528092919081815260200182805461046590610b27565b80156104b25780601f10610487576101008083540402835291602001916104b2565b820191906000526020600020905b81548152906001019060200180831161049557829003601f168201915b505050505094508380546104c590610b27565b80601f01602080910402602001604051908101604052809291908181526020018280546104f190610b27565b801561053e5780601f106105135761010080835404028352916020019161053e565b820191906000526020600020905b81548152906001019060200180831161052157829003601f168201915b505050505093508180548060200260200160405190810160405280929190818152602001828054801561059057602002820191906000526020600020905b81548152602001906001019080831161057c575b505050505091508080546105a390610b27565b80601f01602080910402602001604051908101604052809291908181526020018280546105cf90610b27565b801561061c5780601f106105f15761010080835404028352916020019161061c565b820191906000526020600020905b8154815290600101906020018083116105ff57829003601f168201915b505050505090509450945094509450945091939590929450565b604080518082019091528281526020810182905260008054600181018255908052815182916002027f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e5630190819061068d9082610bb0565b50602082015160018201906106a29082610bb0565b505050600184815481106106b8576106b8610b11565b906000526020600020906005020160030160016000805490506106db9190610c70565b8154600181018355600092835260209092209091015550505050565b60006001828154811061070c5761070c610b11565b600091825260209091206003600590920201015492915050565b828054828255906000526020600020908101928215610761579160200282015b82811115610761578251825591602001919060010190610746565b5061076d929150610771565b5090565b5b8082111561076d5760008155600101610772565b60006020828403121561079857600080fd5b5035919050565b6000815180845260005b818110156107c5576020818501810151868301820152016107a9565b506000602082860101526020601f19601f83011685010191505092915050565b6040815260006107f8604083018561079f565b828103602084015261080a818561079f565b95945050505050565b634e487b7160e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff8111828210171561085257610852610813565b604052919050565b600082601f83011261086b57600080fd5b813567ffffffffffffffff81111561088557610885610813565b610898601f8201601f1916602001610829565b8181528460208386010111156108ad57600080fd5b816020850160208301376000918101602001919091529392505050565b600082601f8301126108db57600080fd5b8135602067ffffffffffffffff8211156108f7576108f7610813565b8160051b610906828201610829565b928352848101820192828101908785111561092057600080fd5b83870192505b8483101561093f57823582529183019190830190610926565b979650505050505050565b600080600080600060a0868803121561096257600080fd5b853567ffffffffffffffff8082111561097a57600080fd5b61098689838a0161085a565b9650602088013591508082111561099c57600080fd5b6109a889838a0161085a565b9550604088013591506001600160a01b03821682146109c657600080fd5b909350606087013590808211156109dc57600080fd5b6109e889838a016108ca565b935060808801359150808211156109fe57600080fd5b50610a0b8882890161085a565b9150509295509295909350565b60a081526000610a2b60a083018861079f565b602083820381850152610a3e828961079f565b6001600160a01b03881660408601528481036060860152865180825282880193509082019060005b81811015610a8257845183529383019391830191600101610a66565b50508481036080860152610a96818761079f565b9a9950505050505050505050565b600080600060608486031215610ab957600080fd5b83359250602084013567ffffffffffffffff80821115610ad857600080fd5b610ae48783880161085a565b93506040860135915080821115610afa57600080fd5b50610b078682870161085a565b9150509250925092565b634e487b7160e01b600052603260045260246000fd5b600181811c90821680610b3b57607f821691505b602082108103610b5b57634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115610bab57600081815260208120601f850160051c81016020861015610b885750805b601f850160051c820191505b81811015610ba757828155600101610b94565b5050505b505050565b815167ffffffffffffffff811115610bca57610bca610813565b610bde81610bd88454610b27565b84610b61565b602080601f831160018114610c135760008415610bfb5750858301515b600019600386901b1c1916600185901b178555610ba7565b600085815260208120601f198616915b82811015610c4257888601518255948401946001909101908401610c23565b5085821015610c605787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b81810381811115610c9157634e487b7160e01b600052601160045260246000fd5b9291505056fea2646970667358221220978771796469359d4141553e5c9fbfa65f0c719faae117528a55d36e7ceca1d164736f6c63430008130033";

    public static final String FUNC_ADDHISTORY = "addHistory";

    public static final String FUNC_ADDITEM = "addItem";

    public static final String FUNC_GETHISTORY = "getHistory";

    public static final String FUNC_GETITEM = "getItem";

    public static final String FUNC_GETITEMSIZE = "getItemSize";

    public static final String FUNC_GETSIZE = "getSize";

    @Deprecated
    protected Test_sol_MyContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Test_sol_MyContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Test_sol_MyContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Test_sol_MyContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addHistory(String _title, String _body, String _writer, List<BigInteger> _itemIndexes, String _fileHash) {
        final Function function = new Function(
                FUNC_ADDHISTORY,
                Arrays.<Type>asList(new Utf8String(_title),
                        new Utf8String(_body),
                        new Address(_writer),
                        new DynamicArray<Uint256>(
                                org.web3j.abi.Utils.typeMap(_itemIndexes, Uint256.class)),
                        new Utf8String(_fileHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addItem(BigInteger index, String _title, String _body) {
        final Function function = new Function(
                FUNC_ADDITEM,
                Arrays.<Type>asList(new Uint256(index),
                        new Utf8String(_title),
                        new Utf8String(_body)),
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
    public static Test_sol_MyContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Test_sol_MyContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Test_sol_MyContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Test_sol_MyContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Test_sol_MyContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Test_sol_MyContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Test_sol_MyContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Test_sol_MyContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Test_sol_MyContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Test_sol_MyContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Test_sol_MyContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Test_sol_MyContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Test_sol_MyContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Test_sol_MyContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Test_sol_MyContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Test_sol_MyContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
