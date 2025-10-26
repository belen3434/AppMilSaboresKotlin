package com.example.appmilsaboreskotlin.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appmilsaboreskotlin.R
import com.example.appmilsaboreskotlin.model.Usuario
import com.example.appmilsaboreskotlin.ui.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuariosScreen(
    viewModel: UsuarioViewModel,
    navController: NavController,
    onNavigateBack: () -> Unit
) {
    val listaUsuarios by viewModel.usuarios.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (listaUsuarios.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay usuarios registrados aún.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listaUsuarios) { usuario ->
                        UsuarioCard(
                            usuario = usuario,
                            onEliminar = { viewModel.eliminarUsuario(usuario) },
                            onVerPerfil = { seleccionado ->
                                navController.navigate("usuario/${seleccionado.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UsuarioCard(usuario: Usuario, onEliminar: () -> Unit, onVerPerfil: (Usuario) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVerPerfil(usuario) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = usuario.imagenUri ?: R.drawable.logo_reg,
                contentDescription = "Imagen del usuario",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .padding(end = 12.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(" ${usuario.nombre}", style = MaterialTheme.typography.titleMedium)
                Text(" ${usuario.correo}", style = MaterialTheme.typography.bodyMedium)
                Text(" Rut: ${usuario.rut}", style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar usuario")
            }
        }
    }
}
