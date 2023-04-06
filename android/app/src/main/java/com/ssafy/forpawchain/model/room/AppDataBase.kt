package com.ssafy.forpawchain.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.forpawchain.model.domain.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}