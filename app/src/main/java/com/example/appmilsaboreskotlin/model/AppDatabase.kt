package com.example.appmilsaboreskotlin.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Usuario::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
}