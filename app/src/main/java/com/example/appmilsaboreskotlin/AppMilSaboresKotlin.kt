package com.example.appmilsaboreskotlin

import android.app.Application
import androidx.room.Room
import com.example.appmilsaboreskotlin.model.AppDatabase
import com.example.appmilsaboreskotlin.repository.UsuarioRepository


class AppMilSaboresKotlin : Application() {

    private val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "mil_sabores_db"
        ).fallbackToDestructiveMigration().build()
    }


    val repository by lazy {
        UsuarioRepository(database.usuarioDao())
    }
}
