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
import com.velkyvet.app.database.Mascota
import com.velkyvet.app.navigation.Rutas
import com.velkyvet.app.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun PantallaMascotas(nav: NavHostController) {
    val contexto = LocalContext.current
    val db = remember { BaseDatos.obtener(contexto) }
    val scope = rememberCoroutineScope()

    // collectAsState convierte el Flow de Room en algo que Compose puede leer
    val mascotas by db.mascotaDao().obtenerTodas().collectAsState(initial = null)

    // Controlamos si mostrar el formulario de agregar
    var mostrarFormulario by remember { mutableStateOf(false) }

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
                Text("Mis Mascotas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Morado)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarFormulario = true },
                containerColor = Morado
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar mascota", tint = Blanco)
            }
        },
        bottomBar = { BarraInferior(nav = nav, pantallaActual = Rutas.MASCOTAS) },
        containerColor = FondoRosa
    ) { padding ->

        // CA 3.3: Mientras los datos cargan (null) mostramos el spinner
        when {
            mascotas == null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Morado)
                }
            }

            mascotas!!.isEmpty() -> {
                // No hay mascotas guardadas todavia
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.Pets,
                            contentDescription = null,
                            tint = TextoGris,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No tienes mascotas registradas", color = TextoGris, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Toca + para agregar tu primera mascota", color = TextoGris, fontSize = 13.sp)
                    }
                }
            }

            else -> {
                // Mostramos la lista de mascotas
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
                            "Bellow are the items in your cart.",
                            fontSize = 13.sp,
                            color = TextoGris
                        )
                    }
                    items(mascotas!!, key = { it.id }) { mascota ->
                        TarjetaMascota(
                            mascota = mascota,
                            alEliminar = {
                                scope.launch { db.mascotaDao().eliminar(mascota) }
                            }
                        )
                    }
                }
            }
        }
    }

    // Dialogo para agregar mascota nueva
    if (mostrarFormulario) {
        DialogAgregarMascota(
            alCerrar = { mostrarFormulario = false },
            alGuardar = { nombre, edad, raza, contacto ->
                scope.launch {
                    db.mascotaDao().insertar(
                        Mascota(nombre = nombre, edad = edad, raza = raza, contacto = contacto)
                    )
                }
                mostrarFormulario = false
            }
        )
    }
}

// Tarjeta individual de cada mascota
@Composable
fun TarjetaMascota(mascota: Mascota, alEliminar: () -> Unit) {
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
            // Icono circular
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Morado.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Pets, contentDescription = null, tint = Morado, modifier = Modifier.size(28.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Datos de la mascota
            Column(modifier = Modifier.weight(1f)) {
                Text(mascota.nombre, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
                Text("${mascota.edad} de edad", fontSize = 13.sp, color = TextoGris)
                Text(mascota.raza, fontSize = 13.sp, color = TextoGris)
            }

            // Boton de eliminar
            IconButton(onClick = alEliminar) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

// Dialogo flotante para agregar una mascota
@Composable
fun DialogAgregarMascota(
    alCerrar: () -> Unit,
    alGuardar: (String, String, String, String) -> Unit
) {
    var nombre   by remember { mutableStateOf("") }
    var edad     by remember { mutableStateOf("") }
    var raza     by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = alCerrar,
        containerColor = Blanco,
        title = {
            Text("Agregar mascota", fontWeight = FontWeight.Bold, color = Morado)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de la mascota") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
                )
                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
                )
                OutlinedTextField(
                    value = raza,
                    onValueChange = { raza = it },
                    label = { Text("Raza") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
                )
                OutlinedTextField(
                    value = contacto,
                    onValueChange = { contacto = it },
                    label = { Text("Número de contacto") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (nombre.isNotBlank()) alGuardar(nombre, edad, raza, contacto) },
                colors = ButtonDefaults.buttonColors(containerColor = Morado)
            ) {
                Text("Guardar", color = Blanco)
            }
        },
        dismissButton = {
            TextButton(onClick = alCerrar) {
                Text("Cancelar", color = TextoGris)
            }
        }
    )
}
