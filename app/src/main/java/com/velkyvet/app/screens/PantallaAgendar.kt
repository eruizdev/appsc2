package com.velkyvet.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.velkyvet.app.ui.theme.*
import kotlinx.coroutines.launch

// Formulario para crear una cita nueva
@Composable
fun PantallaAgendar(nav: NavHostController) {
    val contexto = LocalContext.current
    val db = remember { BaseDatos.obtener(contexto) }
    val scope = rememberCoroutineScope()

    var nombreMascota by remember { mutableStateOf("") }
    var edad          by remember { mutableStateOf("") }
    var contacto      by remember { mutableStateOf("") }
    var raza          by remember { mutableStateOf("") }
    var especialidad  by remember { mutableStateOf("") }
    var horario       by remember { mutableStateOf("") }
    var error         by remember { mutableStateOf("") }
    var guardado      by remember { mutableStateOf(false) }

    // Los tres horarios disponibles para elegir
    val horarios = listOf("2:00 PM", "3:30 PM", "5:00 PM")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoRosa)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        // Encabezado con boton de cerrar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Agenda una cita", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Morado)
                Box(modifier = Modifier.width(48.dp).height(3.dp).background(Morado, RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.height(4.dp))
                Text("General formulario", fontSize = 13.sp, color = TextoGris)
            }
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Filled.Close, contentDescription = "Cerrar", tint = TextoGris)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campos del formulario
        CampoTexto("Nombre de la mascota", nombreMascota) { nombreMascota = it }
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto("Edad", edad) { edad = it }
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto("Número de contacto", contacto) { contacto = it }
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto("Raza", raza) { raza = it }
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto("Especialidad (ej: Odontología)", especialidad) { especialidad = it }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Horarios disponibles", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
        Spacer(modifier = Modifier.height(12.dp))

        // Selector de horario - el elegido queda morado
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            horarios.forEach { h ->
                val seleccionado = h == horario
                Box(
                    modifier = Modifier
                        .background(
                            color = if (seleccionado) Morado else Blanco,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (seleccionado) Morado else TextoGris,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { horario = h }
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = h,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (seleccionado) Blanco else TextoOscuro
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Programa una cita, verifica su disponibilidad", fontSize = 13.sp, color = TextoGris)

        // Mensaje de error
        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
        }

        // Mensaje de exito en verde cuando se guarda
        if (guardado) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VerdePositivo, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Text("¡Cita agendada correctamente!", color = TextoOscuro, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                when {
                    nombreMascota.isBlank() -> error = "Ingresa el nombre de la mascota"
                    horario.isBlank()       -> error = "Selecciona un horario"
                    else -> {
                        error = ""
                        scope.launch {
                            db.citaDao().insertar(
                                Cita(
                                    nombreMascota = nombreMascota,
                                    especialidad  = especialidad.ifBlank { "Consulta general" },
                                    horario       = horario
                                )
                            )
                        }
                        guardado = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Morado)
        ) {
            Text("Programar cita", fontSize = 16.sp, color = Blanco, fontWeight = FontWeight.SemiBold)
        }
    }
}

// Campo de texto reutilizable para el formulario
@Composable
fun CampoTexto(etiqueta: String, valor: String, alCambiar: (String) -> Unit) {
    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        label = { Text(etiqueta) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
    )
}
