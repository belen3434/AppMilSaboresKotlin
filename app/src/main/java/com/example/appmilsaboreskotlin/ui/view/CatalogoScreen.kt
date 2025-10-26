package com.example.appmilsaboreskotlin.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.appmilsaboreskotlin.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack


data class Catalogo(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val img: Int
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(onNavigateBack: () -> Unit) {
    val productos = listOf(
        Catalogo(1, "Torta de Chocolate", "Bizcocho húmedo con ganache artesanal.", 15000, R.drawable.torta_chocolate),
        Catalogo(2, "Tiramisu", "Cremoso y fresco, con base de galletas y salsa tropical.", 13000, R.drawable.tiramisu),
        Catalogo(3, "Tarta Santiago", "Pack de 6 unidades con crema pastelera y decoración temática.", 10000, R.drawable.tarta_santiago),
        Catalogo(4, "Cheescake", "Clásico con base crujiente y suave merengue italiano.", 12000, R.drawable.cheescake)
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Productos") },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                // esto asegura que el contenido no se solape con la barra superior
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(productos) { producto ->
                    ProductoCard(producto)
                }
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Catalogo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = producto.img),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text(producto.descripcion, style = MaterialTheme.typography.bodyMedium)
                Text("Precio: $${producto.precio}", style = MaterialTheme.typography.bodyMedium)
            }

        }
    }
}
