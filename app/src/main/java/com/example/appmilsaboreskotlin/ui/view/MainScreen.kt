package com.example.appmilsaboreskotlin.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.appmilsaboreskotlin.R
import androidx.compose.animation.core.*

@Composable
fun MainScreen(
    onNavigateToRegistro: () -> Unit,
    onNavigateToUsuario: () -> Unit,
    onNavigateToQuienesSomos: () -> Unit,
    onNavigateToCatalogo: () -> Unit,
    onNavigateToUsuarios: () -> Unit
) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 900,
                easing = FastOutSlowInEasing
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.size(150.dp).scale(scale.value)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onNavigateToRegistro, modifier = Modifier.fillMaxWidth()) {
            Text("Registro")
        }


        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onNavigateToUsuarios, modifier = Modifier.fillMaxWidth()) {
            Text("Usuarios")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onNavigateToCatalogo, modifier = Modifier.fillMaxWidth()) {
            Text("Catálogo")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onNavigateToQuienesSomos, modifier = Modifier.fillMaxWidth()) {
            Text("Quienes Somos")
        }
    }
}