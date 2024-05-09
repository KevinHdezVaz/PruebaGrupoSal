package com.hevaz.pruebagruposal.data.local.di

import android.content.Context
import androidx.room.Room
import com.hevaz.pruebagruposal.data.local.AppDatabase


object DatabaseManager {
    lateinit var database: AppDatabase

    fun initialize(context: Context) {
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "users"
        ).build()
    }
}
