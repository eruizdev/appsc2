package com.velkyvet.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.velkyvet.app.ui.theme.*

@Composable
fun PantallaLogin(
    alIniciarSesion: () -> Unit,
    alRegistrarse: () -> Unit,
    alOlvidar: () -> Unit
) {
    // Guardamos lo que escribe el usuario
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var verContrasena by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoRosa)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("VelkyVet App", fontSize = 13.sp, color = TextoGris)

        Spacer(modifier = Modifier.height(8.dp))

        // Titulo con linea morada abajo
        Text("Inicio de sesión", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Morado)
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier.width(48.dp).height(3.dp).background(Morado, RoundedCornerShape(2.dp)))

        Spacer(modifier = Modifier.height(8.dp))
        Text("Inicia sesión con tus credenciales", fontSize = 14.sp, color = TextoGris)
        Spacer(modifier = Modifier.height(32.dp))

        // Campo email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MoradoClaro,
                unfocusedBorderColor = TextoGris
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo contraseña con ojo para mostrar/ocultar
        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = if (verContrasena) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { verContrasena = !verContrasena }) {
                    Icon(
                        imageVector = if (verContrasena) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = null,
                        tint = MoradoClaro
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MoradoClaro,
                unfocusedBorderColor = TextoGris
            )
        )

        // Mostramos error si los campos estan vacios
        if (mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(mensajeError, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Boton de entrar
        Button(
            onClick = {
                if (email.isBlank() || contrasena.isBlank()) {
                    mensajeError = "Por favor completa todos los campos"
                } else {
                    alIniciarSesion()
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Morado)
        ) {
            Text("Iniciar sesión", fontSize = 16.sp, color = Blanco, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Links de abajo
        Text(
            text = "¿Olvidaste tu contraseña?",
            fontSize = 14.sp,
            color = Morado,
            modifier = Modifier.clickable { alOlvidar() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Crea una nueva cuenta",
            fontSize = 14.sp,
            color = MoradoClaro,
            modifier = Modifier.clickable { alRegistrarse() }
        )
    }
}
