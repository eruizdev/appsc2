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
import com.velkyvet.app.navigation.Rutas
import com.velkyvet.app.ui.theme.*

// Pantalla que muestra un resumen de las citas agendadas
@Composable
fun PantallaReportes(nav: NavHostController) {
    val contexto = LocalContext.current
    val db = remember { BaseDatos.obtener(contexto) }

    // null mientras carga, lista cuando ya trajo los datos
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
                Text("Reportes", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Morado)
            }
        },
        bottomBar = { BarraInferior(nav = nav, pantallaActual = Rutas.REPORTES) },
        containerColor = FondoRosa
    ) { padding ->

        // CA 3.3: mientras carga mostramos el spinner
        when {
            citas == null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Morado)
                }
            }

            citas!!.isEmpty() -> {
                // Todavia no hay citas para reportar
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.BarChart,
                            contentDescription = null,
                            tint = TextoGris,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Sin datos por mostrar aún", color = TextoGris, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Las citas agendadas aparecerán aquí",
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

                    // Titulo de la seccion
                    item {
                        Text(
                            "Reporte de servicios",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        // Linea morada debajo del titulo
                        Box(
                            modifier = Modifier
                                .width(48.dp)
                                .height(3.dp)
                                .background(Morado, RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Una tarjeta por cada cita
                    items(citas!!) { cita ->
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
                                    Icon(
                                        Icons.Filled.Pets,
                                        contentDescription = null,
                                        tint = Morado,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        cita.nombreMascota,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextoOscuro
                                    )
                                    Text(cita.especialidad, fontSize = 13.sp, color = TextoGris)
                                }
                                // El precio esta en 0 porque no tenemos logica de precios
                                Text("$0", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Morado)
                            }
                        }
                    }

                    // Tarjeta de resumen total al final
                    item {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Reporte total",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(48.dp)
                                .height(3.dp)
                                .background(Morado, RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Blanco,
                            shadowElevation = 4.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {

                                // Agrupamos por especialidad para mostrar cuantas hay de cada tipo
                                val porEspecialidad = citas!!.groupBy { it.especialidad }
                                porEspecialidad.forEach { (especialidad, lista) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(especialidad, fontSize = 14.sp, color = TextoGris)
                                        Text("${lista.size} cita(s)", fontSize = 14.sp, color = TextoOscuro)
                                    }
                                }

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    color = TextoGris.copy(alpha = 0.2f)
                                )

                                // Numero total grande abajo
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Total ${citas!!.size} citas",
                                        fontSize = 14.sp,
                                        color = TextoGris
                                    )
                                    Text(
                                        "$0",
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Morado
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
