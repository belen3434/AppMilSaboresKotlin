package com.example.appmilsaboreskotlin.ui.viewmodel

data class UsuarioUIState(
    val nombre: String = "",
    val correo: String = "",
    val rut: String = "",
    val errores: UsuarioErrores = UsuarioErrores(),
    val guardadoExitoso: Boolean = false
)