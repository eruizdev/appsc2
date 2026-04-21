package com.velkyvet.app.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.velkyvet.app.navigation.Rutas
import com.velkyvet.app.ui.theme.*

// Barra de abajo que aparece en todas las pantallas internas
@Composable
fun BarraInferior(nav: NavHostController, pantallaActual: String) {

    NavigationBar(containerColor = Blanco) {

        // Los 4 tabs de la barra
        val tabs = listOf(
            Triple(Rutas.MASCOTAS, Icons.Filled.Pets, "Mascotas"),
            Triple(Rutas.CITAS,    Icons.Filled.CalendarMonth, "Citas"),
            Triple(Rutas.REPORTES, Icons.Filled.BarChart, "Reportes"),
            Triple(Rutas.PERFIL,   Icons.Filled.Person, "Perfil")
        )

        tabs.forEach { (ruta, icono, nombre) ->
            val estaSeleccionado = pantallaActual == ruta

            NavigationBarItem(
                selected = estaSeleccionado,
                onClick = {
                    if (!estaSeleccionado) {
                        nav.navigate(ruta) {
                            popUpTo(Rutas.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(icono, contentDescription = nombre) },
                label = { Text(nombre, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Morado,
                    selectedTextColor   = Morado,
                    unselectedIconColor = TextoGris,
                    unselectedTextColor = TextoGris,
                    indicatorColor      = FondoRosa
                )
            )
        }
    }
}
