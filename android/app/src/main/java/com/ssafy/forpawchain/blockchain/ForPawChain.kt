package com.ssafy.forpawchain.blockchain

import android.util.Log
import com.ssafy.forpawchain.model.domain.Data
import com.ssafy.forpawchain.model.domain.HistoryDTO
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.Utf8String
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import kotlin.concurrent.thread


class ForPawChain {
    companion object {
        val TAG: String? = this::class.qualifiedName

        // https://sepolia.infura.io/v3/2849717dc6944af6a40ccf1540bdcb91
        var web3 =
//            Web3j.build(InfuraHttpService("https://sepolia.infura.io/v3/2849717dc6944af6a40ccf1540bdcb91"))
            Web3j.build(HttpService("http://3.39.235.238:8545/"))


        // gas limit
        val gasLimit: BigInteger = BigInteger.valueOf(30_000_000)

        // gas price
        val gasPrice: BigInteger = BigInteger.valueOf(3000)

        var contractAddress = ""
        var credentials =
            Credentials.create("6169940ca8cb18384b5000199566c387da4f8d9caed51ffe7921b93c488d2544")

        fun setBlockChain(ca: String, cred: String) {
            this.contractAddress = ca
            this.credentials = Credentials.create(cred)
        }

        fun createHistory(
            title: String,
            body: String,
            items: ArrayList<Data>,
            hash: String
        ): Boolean {
            thread {
                val transactionManager = RawTransactionManager(web3, credentials, 111)


                val contract =
                    Test_sol_ForPawChain.load(
                        contractAddress,
                        web3,
                        transactionManager,
                        gasPrice,
                        gasLimit
                    )
                val data = contract.addHistory(title, body, hash).send()

                data.blockNumber

                val size = contract.size.toInt() - 1
                for (item in items) {
                    Log.d(TAG, "추가 시작")
                    contract.addItem(BigInteger(size.toString()), item.title, item.body).send()
                    Log.d(TAG, "추가 끝")

                }
            }

            return true
        }

        fun getHistory(): ArrayList<HistoryDTO> {
            var result: ArrayList<HistoryDTO> = ArrayList()
            val contract =
                Test_sol_ForPawChain.load(contractAddress, web3, credentials, gasPrice, gasLimit)
            thread {
                val size: BigInteger = contract.size
                for (i in 0 until size.toInt()) {
                    val history = contract.getHistory(BigInteger(i.toString())).send()
                    val title = history[0] as Utf8String
                    val body = history[1] as Utf8String
                    val writer = history[2] as Address
                    val extra_size = history[3] as DynamicArray<*>
                    var extra: ArrayList<Data> = ArrayList()
                    for (index in extra_size.value) {
                        val temp = contract.getItem(index.value as BigInteger?).send()
                        val extra_title = temp[0] as Utf8String
                        val extra_body = temp[1] as Utf8String
                        extra.add(
                            Data(
                                extra_title.value.toString(),
                                extra_body.value.toString()
                            )
                        )
                    }
                    val hash = history[4] as Utf8String
                    result.add(
                        HistoryDTO(
                            title.value.toString(),
                            body.value.toString(),
                            extra,
                            writer.value,
                            hash.value,
                            "2022-03-03 오후 03:05:27" // TODO: 시간 작업 필요
                        )
                    )
                }
            }.join()
            return result
        }
        /*
        val contract =
                Test_sol_ForPawChain.load(contractAddress, web3, credentials, gasPrice, gasLimit)
//            contract.addHistory("title3", "body3", "hash").send()

            val size = contract.size
            val itemSize = contract.getItemSize(BigInteger("1"))
            val history = contract.getHistory(BigInteger("0")).send()
            val item = contract.getItem(BigInteger("0")).send()
         */
    }
}