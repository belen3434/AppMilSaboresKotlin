package com.example.appmilsaboreskotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmilsaboreskotlin.model.Usuario
import com.example.appmilsaboreskotlin.repository.UsuarioRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class UsuarioViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(UsuarioUIState())
    val uiState: StateFlow<UsuarioUIState> = _uiState.asStateFlow()


    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios.asStateFlow()

    private val _imagenUri = MutableStateFlow<String?>(null)
    val imagenUri: StateFlow<String?> = _imagenUri.asStateFlow()


    private val _usuarioSeleccionado = MutableStateFlow<Usuario?>(null)
    val usuarioSeleccionado: StateFlow<Usuario?> = _usuarioSeleccionado

    fun cargarUsuarioPorId(id: Int) {
        viewModelScope.launch {
            val usuario = repository.obtenerUsuarioPorId(id)
            _usuarioSeleccionado.value = usuario
        }
    }

    fun actualizarImagenUsuario(id: Int, nuevaUri: String) {
        viewModelScope.launch {
            repository.actualizarImagenUsuario(id, nuevaUri)
            cargarUsuarioPorId(id)
        }
    }

    fun setImagenUri(uri: String?) {
        _imagenUri.value = uri
    }


    init {
        cargarUsuarios()
    }


    fun onNombreChange(nuevo: String) {
        _uiState.update { it.copy(nombre = nuevo) }
    }
    fun resetGuardado() {
        _uiState.update { it.copy(guardadoExitoso = false) }
    }

    fun onCorreoChange(nuevo: String) {
        _uiState.update { it.copy(correo = nuevo) }
    }

    fun onrutChange(nuevo: String) {
        _uiState.update { it.copy(rut = nuevo) }
    }



    fun guardarUsuario() {
        val estado = _uiState.value
        val errores = validarCampos(estado)


        if (errores != UsuarioErrores()) {
            _uiState.update { it.copy(errores = errores, guardadoExitoso = false) }
            return
        }

        viewModelScope.launch {
            val usuario = Usuario(
                nombre = estado.nombre.trim(),
                correo = estado.correo.trim(),
                rut = estado.rut.toIntOrNull() ?: 0,
                imagenUri = _imagenUri.value

            )
            repository.insertarUsuario(usuario)
            _uiState.update {
                it.copy(
                    nombre = "",
                    correo = "",
                    rut = "",
                    errores = UsuarioErrores(),
                    guardadoExitoso = true
                )
            }
        }
    }


    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repository.eliminarUsuario(usuario)
        }
    }


    private fun validarCampos(estado: UsuarioUIState): UsuarioErrores {
        var nombreErr: String? = null
        var correoErr: String? = null
        var edadErr: String? = null
        var contrasenaErr: String? = null

        if (estado.nombre.isBlank()) nombreErr = "El nombre no puede estar vacío, por favor ingresa tu nombre"
        if (!estado.correo.contains("@")) correoErr = "Correo inválido, debe contener @"
        if (estado.rut.toIntOrNull() == null) edadErr = "Edad inválida, ingrese solo números y no letras."

        return UsuarioErrores(
            nombreError = nombreErr,
            correoError = correoErr,
            rutError = edadErr,
        )
    }


    fun cargarUsuarios() {
        viewModelScope.launch {
            repository.obtenerUsuarios().collect { lista ->
                _usuarios.value = lista
            }
        }
    }

    fun eliminarTodo() {
        viewModelScope.launch {
            repository.eliminarTodos()
        }
    }
}