package com.ssafy.forpawchain.model.interfaces

import com.ssafy.forpawchain.model.domain.MyPawListDTO

interface IWithdrawalAnimal {
    fun onDeleteBtnClick(pid: String, myPawListDTO: MyPawListDTO)
}