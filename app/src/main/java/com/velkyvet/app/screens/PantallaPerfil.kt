package com.velkyvet.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.velkyvet.app.navigation.Rutas
import com.velkyvet.app.ui.theme.*

// Pantalla de perfil del usuario con opcion de editar nombre y email
@Composable
fun PantallaPerfil(nav: NavHostController, alCerrarSesion: () -> Unit) {

    // Datos del perfil guardados en memoria mientras corre la app
    var nombre  by remember { mutableStateOf("Maria Benjumea") }
    var email   by remember { mutableStateOf("maria@ejemplo.com") }

    // Variables temporales mientras el usuario edita
    var nombreTemp  by remember { mutableStateOf(nombre) }
    var emailTemp   by remember { mutableStateOf(email) }

    // Controla si mostramos el formulario de edicion
    var editando by remember { mutableStateOf(false) }
    // Muestra el mensaje verde despues de guardar
    var guardado by remember { mutableStateOf(false) }

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
                Text("Personalización", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Morado)
            }
        },
        bottomBar = { BarraInferior(nav = nav, pantallaActual = Rutas.PERFIL) },
        containerColor = FondoRosa
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // Avatar circular con icono de persona
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(Morado, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = null,
                    tint = Blanco,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre y email del usuario
            Text(nombre, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
            Text(email, fontSize = 14.sp, color = TextoGris)

            Spacer(modifier = Modifier.height(32.dp))

            // Tarjeta de opciones de cuenta
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Blanco,
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        "Configuraciones de cuenta",
                        fontSize = 12.sp,
                        color = TextoGris,
                        modifier = Modifier.padding(start = 16.dp, top = 14.dp, bottom = 4.dp)
                    )

                    // Fila de cambiar contraseña - solo navega, sin logica real
                    FilaOpcion(
                        icono    = Icons.Filled.Lock,
                        etiqueta = "Cambiar contraseña",
                        alTocar  = { }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = TextoGris.copy(alpha = 0.15f)
                    )

                    // Fila para abrir/cerrar el formulario de edicion
                    FilaOpcion(
                        icono    = Icons.Filled.Edit,
                        etiqueta = "Editar perfil",
                        alTocar  = {
                            // Copiamos los valores actuales para no pisar nada hasta que guarden
                            nombreTemp = nombre
                            emailTemp  = email
                            editando   = !editando
                            guardado   = false
                        }
                    )
                }
            }

            // Formulario de edicion, solo aparece si toca "Editar perfil"
            if (editando) {
                Spacer(modifier = Modifier.height(20.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Blanco,
                    shadowElevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "Editar datos",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Morado
                        )

                        OutlinedTextField(
                            value = nombreTemp,
                            onValueChange = { nombreTemp = it },
                            label = { Text("Nombre") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
                        )

                        OutlinedTextField(
                            value = emailTemp,
                            onValueChange = { emailTemp = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
                        )

                        Button(
                            onClick = {
                                if (nombreTemp.isNotBlank()) {
                                    // Guardamos los cambios
                                    nombre   = nombreTemp
                                    email    = emailTemp
                                    guardado = true
                                    editando = false
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Morado)
                        ) {
                            Text("Guardar", color = Blanco, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // Mensaje verde de confirmacion despues de guardar
            if (guardado) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(VerdePositivo, RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Perfil guardado", color = TextoOscuro, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Boton rojo de cerrar sesion
            OutlinedButton(
                onClick = alCerrarSesion,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(
                    Icons.Filled.PowerSettingsNew,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar sesión", fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// Fila reutilizable con icono, texto y flecha de ir
@Composable
fun FilaOpcion(icono: ImageVector, etiqueta: String, alTocar: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Blanco,
        onClick = alTocar
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icono, contentDescription = null, tint = Morado, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(etiqueta, fontSize = 15.sp, color = TextoOscuro, modifier = Modifier.weight(1f))
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = TextoGris)
        }
    }
}
