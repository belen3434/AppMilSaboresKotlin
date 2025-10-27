package com.example.appmilsaboreskotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appmilsaboreskotlin.AppMilSaboresKotlin
import com.example.appmilsaboreskotlin.ui.theme.AppMilSaboresKotlinTheme
import com.example.appmilsaboreskotlin.ui.view.*
import com.example.appmilsaboreskotlin.ui.viewmodel.UsuarioViewModel
import com.example.appmilsaboreskotlin.ui.viewmodel.UsuarioViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        installSplashScreen()


        val repository = (application as AppMilSaboresKotlin).repository

        setContent {
            AppMilSaboresKotlinTheme {
                Surface(color = MaterialTheme.colorScheme.background) {


                    val navController = rememberNavController()


                    val factory = UsuarioViewModelFactory(repository)
                    val viewModel: UsuarioViewModel = viewModel(factory = factory)


                    NavHost(navController = navController, startDestination = "main") {

                        //  Pantalla principal
                        composable("main") {
                            MainScreen(
                                onNavigateToRegistro = { navController.navigate("formulario") },
                                onNavigateToUsuario= { navController.navigate("usuario") },
                                onNavigateToQuienesSomos = { navController.navigate("quienessomos") },
                                onNavigateToCatalogo = { navController.navigate("catalogo") },
                                onNavigateToUsuarios = { navController.navigate("usuarios") }
                            )
                        }


                        composable("formulario") {
                            FormularioScreen(
                                viewModel = viewModel,
                                onGuardado = {

                                    navController.navigate("usuarios") {
                                        popUpTo("main") { inclusive = false }
                                        launchSingleTop = true
                                    }
                                },
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }


                        composable("usuarios") {
                            UsuariosScreen(
                                viewModel = viewModel,
                                navController = navController,
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }


                        composable("usuario") {
                            UsuarioScreen(
                                usuarioId = null,
                                viewModel = viewModel,
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }

                        composable("usuario/{usuarioId}") { backStackEntry ->
                            val usuarioId = backStackEntry.arguments?.getString("usuarioId")
                            UsuarioScreen(
                                usuarioId = usuarioId,
                                viewModel = viewModel,
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }


                        composable("catalogo") {
                            CatalogoScreen(onNavigateBack = { navController.navigateUp() })
                        }


                        composable("quienessomos") {
                            QuienesSomosScreen(
                                onNavigateBack = { navController.navigateUp() },
                                onExitApp = { finish() }
                            )
                        }
                    }
                }
            }
        }
    }
}