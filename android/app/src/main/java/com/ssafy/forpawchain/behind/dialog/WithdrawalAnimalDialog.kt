package com.ssafy.forpawchain.behind.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.ssafy.forpawchain.behind.fragment.MyPawFragment
import com.ssafy.forpawchain.behind.fragment.PermissionPawFragment
import com.ssafy.forpawchain.databinding.DialogPermissionDeleteBinding
import com.ssafy.forpawchain.databinding.DialogWithdrawalAnimalBinding
import com.ssafy.forpawchain.databinding.DialogWithdrawalBinding
import com.ssafy.forpawchain.databinding.FragmentAdoptAddBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.interfaces.IWithdrawalAnimal
import com.ssafy.forpawchain.model.room.UserInfo
import com.ssafy.forpawchain.model.service.AuthService
import com.ssafy.forpawchain.util.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class WithdrawalAnimalDialog(myPawListDTO: MyPawListDTO, context: Context, dialogInterface: IWithdrawalAnimal) :
    Dialog(context) {
    companion object {
        val TAG: String? = this::class.qualifiedName

    }
    private var mBinding: DialogWithdrawalAnimalBinding? = null
    private val binding get() = mBinding!!

    private var dialogInterface: IWithdrawalAnimal? = null
    private var mMyPawListDTO: MyPawListDTO? = null

    init {
        this.dialogInterface = dialogInterface
        this.mMyPawListDTO = myPawListDTO;
    }
    var token = PreferenceManager().getString(context, "token")!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogWithdrawalAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView7.setImageDrawable(mMyPawListDTO?.profile?.value)
        binding.imageView100.setText(mMyPawListDTO?.name?.value.toString())
        binding.textView25.setText(mMyPawListDTO?.name?.value.toString())
        binding.imageView101.setText("#" + mMyPawListDTO?.code?.value.toString())

        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) {
                AuthService().getRegDateAuth(
                    UserInfo.uid.toInt(), mMyPawListDTO?.code?.value.toString(),
                    token
                ).enqueue(object :
                    Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if (response.isSuccessful) {
                            // 정상적으로 통신이 성공된 경우
                            // call
                            val date = response.body()?.get("content").toString()
                            val dateList = date.split("-".toRegex()).dropLastWhile { it.isEmpty() } .toTypedArray()

                            val year = dateList[0].substring(1)
                            val month = dateList[1]
                            val day = dateList[2].substring(0, 2)

                            val past: LocalDate = LocalDate.of(year.toInt(), month.toInt(), day.toInt())
                            val now: LocalDate = LocalDate.now()
                            val days: Long = ChronoUnit.DAYS.between(past, now)
                            Log.d(TAG, "onResponse 성공");

                            binding.textView26.setText(days.toString())
                        } else {
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Log.d(TAG, "onResponse 실패")
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                        Log.d(TAG, "onFailure 에러: " + t.message.toString());
                    }
                })
            }
        }
        Log.d(TAG, "데이터 바인딩 성공")


        binding.deleteBtn.setOnClickListener {
            mMyPawListDTO?.let { it1 ->
                this.dialogInterface?.onDeleteBtnClick(binding.imageView101.text.toString(),
                    it1
                )
            }
            dismiss()

        }
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

    }
}