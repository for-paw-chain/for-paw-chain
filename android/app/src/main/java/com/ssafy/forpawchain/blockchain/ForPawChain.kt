package com.ssafy.forpawchain.blockchain

import com.ssafy.forpawchain.model.domain.Data
import com.ssafy.forpawchain.model.domain.HistoryDTO
import okhttp3.internal.wait
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import java.math.BigInteger
import kotlin.concurrent.thread

class ForPawChain {
    companion object {
        val web3 =
            Web3j.build(InfuraHttpService("https://sepolia.infura.io/v3/2849717dc6944af6a40ccf1540bdcb91"))

        //        val web3 = Web3jFactory.build()
        val web3ClientVersion = web3.web3ClientVersion().sendAsync().get()

        // gas limit
        val gasLimit: BigInteger = BigInteger.valueOf(3000000)

        // gas price
        val gasPrice: BigInteger = BigInteger.valueOf(3000)

        var contractAddress = ""
        var credentials =
            Credentials.create("6169940ca8cb18384b5000199566c387da4f8d9caed51ffe7921b93c488d2544")

        fun setBlockChain(ca: String, cred: String) {
            this.contractAddress = ca
            this.credentials = Credentials.create(cred)
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
                            hash.value
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