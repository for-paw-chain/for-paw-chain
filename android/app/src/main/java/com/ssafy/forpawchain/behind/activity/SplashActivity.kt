package com.ssafy.forpawchain.behind.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.blockchain.Test_sol_ForPawChain
import com.ssafy.forpawchain.blockchain.Test_sol_MyContract
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import java.math.BigInteger
import kotlin.concurrent.thread


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val web3 =
            Web3j.build(InfuraHttpService("https://sepolia.infura.io/v3/2849717dc6944af6a40ccf1540bdcb91"))

        //        val web3 = Web3jFactory.build()
        val web3ClientVersion = web3.web3ClientVersion().sendAsync().get()

        // contract address
        val contractAddress = "0x789bE5eC74330cd64d007a15bD273fCC27fEE6bB"

        // gas limit
        val gasLimit: BigInteger = BigInteger.valueOf(3000000)

        // gas price
        val gasPrice: BigInteger = BigInteger.valueOf(3000)

        // create credentials w/ your private key
        val credentials =
            Credentials.create("6169940ca8cb18384b5000199566c387da4f8d9caed51ffe7921b93c488d2544")
        thread {

            val contract =
                Test_sol_ForPawChain.load(contractAddress, web3, credentials, gasPrice, gasLimit)
            contract.addHistory("title3", "body3", "hash").send()

//            val size = contract.size
//            val itemSize = contract.getItemSize(BigInteger("1"))
//            val history = contract.getHistory(BigInteger("0")).send()
//            val item = contract.getItem(BigInteger("0")).send()
            print("")
        }
        startLoading();
    }

    private fun startLoading() {
        // TODO: 주석 해제 꼭 필요
//        val handler = Handler()
//        handler.postDelayed(Runnable {
//            // Splash Screen이 뜨고 나서 실행될 Activity 연결
//            startActivity(Intent(applicationContext, LoginActivity::class.java))
//            finish()
//        }, 2000)
    }
}