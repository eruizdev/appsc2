package com.velkyvet.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.velkyvet.app.screens.*

// Aqui conectamos todas las pantallas con sus rutas
// NavHost es como el mapa de la app
@Composable
fun NavegacionPrincipal(nav: NavHostController) {
    NavHost(
        navController = nav,
        startDestination = Rutas.BIENVENIDA
    ) {

        // Pantalla de bienvenida - la primera que ve el usuario
        composable(Rutas.BIENVENIDA) {
            PantallaBienvenida(
                alEntrar = { nav.navigate(Rutas.LOGIN) }
            )
        }

        // Login
        composable(Rutas.LOGIN) {
            PantallaLogin(
                alIniciarSesion = {
                    // Borramos el historial para que no pueda volver al login con el boton atras
                    nav.navigate(Rutas.HOME) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                },
                alRegistrarse = { nav.navigate(Rutas.REGISTRO) },
                alOlvidar = { nav.navigate(Rutas.OLVIDE) }
            )
        }

        // Registro de cuenta nueva
        composable(Rutas.REGISTRO) {
            PantallaRegistro(
                alTerminar = {
                    nav.navigate(Rutas.LOGIN) {
                        popUpTo(Rutas.REGISTRO) { inclusive = true }
                    }
                },
                alVolver = { nav.popBackStack() }
            )
        }

        // Recuperar contraseña
        composable(Rutas.OLVIDE) {
            PantallaOlvide(
                alVolver = { nav.popBackStack() }
            )
        }

        // Home - pantalla principal con los accesos rapidos
        composable(Rutas.HOME) {
            PantallaHome(
                nav = nav,
                alCerrarSesion = {
                    nav.navigate(Rutas.LOGIN) {
                        popUpTo(Rutas.HOME) { inclusive = true }
                    }
                }
            )
        }

        // Lista de mascotas
        composable(Rutas.MASCOTAS) {
            PantallaMascotas(nav = nav)
        }

        // Formulario para agendar cita
        composable(Rutas.AGENDAR) {
            PantallaAgendar(nav = nav)
        }

        // Lista de citas agendadas
        composable(Rutas.CITAS) {
            PantallaCitas(nav = nav)
        }

        // Reportes de gastos
        composable(Rutas.REPORTES) {
            PantallaReportes(nav = nav)
        }

        // Perfil del usuario
        composable(Rutas.PERFIL) {
            PantallaPerfil(
                nav = nav,
                alCerrarSesion = {
                    nav.navigate(Rutas.LOGIN) {
                        popUpTo(Rutas.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}
