package com.velkyvet.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.velkyvet.app.database.BaseDatos
import com.velkyvet.app.database.Cita
import com.velkyvet.app.navigation.Rutas
import com.velkyvet.app.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun PantallaCitas(nav: NavHostController) {
    val contexto = LocalContext.current
    val db = remember { BaseDatos.obtener(contexto) }
    val scope = rememberCoroutineScope()

    // null = cargando, lista vacia = no hay citas, lista con datos = hay citas
    val citas by db.citaDao().obtenerTodas().collectAsState(initial = null)

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Blanco)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { nav.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Morado)
                }
                Text("Mis Citas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Morado)
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { nav.navigate(Rutas.AGENDAR) },
                containerColor = Morado,
                icon = { Icon(Icons.Filled.Add, contentDescription = null, tint = Blanco) },
                text = {
                    Text(
                        "Agenda una cita",
                        color = Blanco,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        },
        bottomBar = { BarraInferior(nav = nav, pantallaActual = Rutas.CITAS) },
        containerColor = FondoRosa
    ) { padding ->

        // CA 3.3: estados de carga, vacio y con datos
        when {
            citas == null -> {
                // Cargando datos de Room
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Morado)
                }
            }

            citas!!.isEmpty() -> {
                // Sin citas aun
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.CalendarMonth,
                            contentDescription = null,
                            tint = TextoGris,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No tienes citas agendadas", color = TextoGris, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Programa una cita desde el botón de abajo",
                            color = TextoGris,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        Text(
                            "Gestiona y administra tus citas",
                            fontSize = 13.sp,
                            color = TextoGris
                        )
                    }
                    items(citas!!, key = { it.id }) { cita ->
                        TarjetaCita(
                            cita = cita,
                            alEliminar = {
                                scope.launch { db.citaDao().eliminar(cita) }
                            }
                        )
                    }
                }
            }
        }
    }
}

// Tarjeta de cada cita
@Composable
fun TarjetaCita(cita: Cita, alEliminar: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Blanco,
        shadowElevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Morado.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Pets, contentDescription = null, tint = Morado, modifier = Modifier.size(28.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(cita.nombreMascota, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
                Text(cita.especialidad, fontSize = 13.sp, color = MoradoClaro)
                Text("Cita a las ${cita.horario}", fontSize = 13.sp, color = TextoGris)
            }

            Column(horizontalAlignment = Alignment.End) {
                // Chip del estado
                Box(
                    modifier = Modifier
                        .background(SuperficieRosa, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(cita.estado, fontSize = 11.sp, color = TextoOscuro)
                }
                IconButton(onClick = alEliminar) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
