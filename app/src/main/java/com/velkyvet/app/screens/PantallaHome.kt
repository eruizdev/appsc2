package com.velkyvet.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.velkyvet.app.navigation.Rutas
import com.velkyvet.app.ui.theme.*

// Pantalla principal de la app - muestra los 4 accesos rapidos como tarjetas
@Composable
fun PantallaHome(nav: NavHostController, alCerrarSesion: () -> Unit) {
    var busqueda by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BarraInferior(nav = nav, pantallaActual = Rutas.HOME) },
        containerColor = Blanco
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            // Fila superior con el nombre de la app y el boton de cerrar sesion
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "VelkyVet APP",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Morado
                )
                IconButton(onClick = alCerrarSesion) {
                    Icon(Icons.Filled.PowerSettingsNew, contentDescription = "Salir", tint = Morado)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de busqueda estilo simple con linea inferior
            Text(
                "Buscar...",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextoGris
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Morado)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Primera fila de tarjetas: Mascotas y Citas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TarjetaMenu(
                    icono = Icons.Filled.Pets,
                    titulo = "Administra tus mascotas",
                    etiquetaBoton = "Mascotas",
                    colorFondo = TarjetaVerde,
                    modifier = Modifier.weight(1f),
                    alTocar = { nav.navigate(Rutas.MASCOTAS) }
                )
                TarjetaMenu(
                    icono = Icons.Filled.CalendarMonth,
                    titulo = "Agenda y administra tus citas",
                    etiquetaBoton = "Citas",
                    colorFondo = TarjetaCrema,
                    modifier = Modifier.weight(1f),
                    alTocar = { nav.navigate(Rutas.CITAS) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Segunda fila: Reportes y Perfil
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TarjetaMenu(
                    icono = Icons.Filled.BarChart,
                    titulo = "Tus reportes y gastos",
                    etiquetaBoton = "Reportes",
                    colorFondo = TarjetaVerdeClaro,
                    modifier = Modifier.weight(1f),
                    alTocar = { nav.navigate(Rutas.REPORTES) }
                )
                TarjetaMenu(
                    icono = Icons.Filled.Person,
                    titulo = "Personaliza tu perfil",
                    etiquetaBoton = "Perfil",
                    colorFondo = TarjetaRosa,
                    modifier = Modifier.weight(1f),
                    alTocar = { nav.navigate(Rutas.PERFIL) }
                )
            }
        }
    }
}

// Tarjeta con icono, descripcion arriba y boton morado abajo
@Composable
fun TarjetaMenu(
    icono: ImageVector,
    titulo: String,
    etiquetaBoton: String,
    colorFondo: Color,
    modifier: Modifier = Modifier,
    alTocar: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(200.dp)
            .clickable { alTocar() },
        shape = RoundedCornerShape(20.dp),
        color = colorFondo
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icono arriba
            Icon(
                icono,
                contentDescription = etiquetaBoton,
                tint = TextoOscuro,
                modifier = Modifier.size(40.dp)
            )

            // Descripcion en el medio
            Text(
                text = titulo,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextoOscuro,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Boton morado abajo
            Button(
                onClick = alTocar,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Morado),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(
                    etiquetaBoton,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Blanco
                )
            }
        }
    }
}
