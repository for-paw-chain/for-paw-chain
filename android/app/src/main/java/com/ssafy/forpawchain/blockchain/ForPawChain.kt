package com.ssafy.forpawchain.blockchain

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import com.ssafy.forpawchain.model.domain.Data
import com.ssafy.forpawchain.model.domain.HistoryDTO
import com.ssafy.forpawchain.model.room.AppDatabase
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread


class ForPawChain {
    companion object {
        val TAG: String? = this::class.qualifiedName

        // https://sepolia.infura.io/v3/2849717dc6944af6a40ccf1540bdcb91
        var web3 =
//            Web3j.build(InfuraHttpService("https://sepolia.infura.io/v3/2849717dc6944af6a40ccf1540bdcb91"))
            Web3j.build(HttpService("http://3.39.235.238:8545/"))

        // gas limit
        val gasLimit: BigInteger = BigInteger.valueOf(3000000)

        // gas price
        val gasPrice: BigInteger = BigInteger.valueOf(0)

        var contractAddress = ""
        var credentials =
            Credentials.create("faee15c534f72212de7f83070c68bade01071d0ca6256a761ea568cbcf832714")

        fun setBlockChain(ca: String, cred: String) {
            Log.d(TAG, "setBlockChain 프라이빗키:::::" + cred)
            this.contractAddress = ca
            this.credentials = Credentials.create(cred)
            Log.d(TAG, "지갑주소::::::" + credentials.address)
        }

        fun setWallet(cred: String) {
            Log.d(TAG, "setWallet 프라이빗키:::::" + cred)
            this.credentials = Credentials.create(cred)
            Log.d(TAG, "지갑주소::::::" + credentials.address)
        }

        fun createHistory( // 의료기록 작성
            title: String,
            body: String,
            items: ArrayList<Data>,
            hash: String
        ): Boolean {
            thread {
//                val db = Room.databaseBuilder(
//                    requireContext(),
//                    AppDatabase::class.java, "database-name"
//                ).build()
//                val userDao = db.userDao()
//                Credentials.create(db.userDao().getUserById("private").privateKey)

                val transactionManager = RawTransactionManager(web3, credentials, 7167)

                val contract =
                    Forpawchain_sol_ForPawChain.load( // contract 정보 load
                        contractAddress,
                        web3,
                        transactionManager,
                        gasPrice,
                        gasLimit
                    )

                Log.d(TAG, "컨트랙트 주소 : " + contractAddress)

                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formatted = currentDateTime.format(formatter)
                val data = contract.addHistory(title, body, formatted, hash).send()

                data.blockNumber // 채굴해도 사이즈가 안맞는 경우가 있기 때문에 대기

                val size = contract.size.toInt() - 1
                for (item in items) {
                    Log.d(TAG, "추가 시작")
                    contract.addItem(BigInteger(size.toString()), item.title, item.body).send()
                    Log.d(TAG, "추가 끝")

                }
            }

            return true
        }

        fun getHistory(): ArrayList<HistoryDTO> { // 의료 기록 읽기
            var result: ArrayList<HistoryDTO> = ArrayList()
            val contract =
                Forpawchain_sol_ForPawChain.load(
                    contractAddress,
                    web3,
                    credentials,
                    gasPrice,
                    gasLimit
                )
            thread {
                val size: BigInteger = contract.size
                for (i in 0 until size.toInt()) {
                    val history = contract.getHistory(BigInteger(i.toString())).send()
                    val title = history[0] as Utf8String
                    val body = history[1] as Utf8String
                    val date = history[2] as Utf8String
                    val writer = history[3] as Address
                    val extra_size = history[4] as DynamicArray<*>
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
                    val hash = history[5] as Utf8String
                    
                    result.add(
                        HistoryDTO(
                            title.value.toString(),
                            body.value.toString(),
                            extra,
                            writer.value,
                            hash.value,
                            date.value
                        )
                    )
                }
            }.join() // thread
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