package com.ssafy.forpawchain.model.room

import androidx.room.*
import com.ssafy.forpawchain.model.domain.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE uid = :id")
    fun getUserById(id: String): User

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}