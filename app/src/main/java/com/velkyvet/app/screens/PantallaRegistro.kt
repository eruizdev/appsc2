package com.velkyvet.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun PantallaRegistro(
    alTerminar: () -> Unit,
    alVolver: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    var verContrasena by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoRosa)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Boton atras arriba a la izquierda
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = alVolver) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Morado)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("VelkyVet App", fontSize = 13.sp, color = TextoGris)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Registrarse", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Morado)
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier.width(48.dp).height(3.dp).background(Morado, RoundedCornerShape(2.dp)))
        Spacer(modifier = Modifier.height(8.dp))
        Text("Ingresa tus datos para registrarte", fontSize = 14.sp, color = TextoGris)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contraseña con opcion de ver
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
                        if (verContrasena) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = null, tint = MoradoClaro
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmar,
            onValueChange = { confirmar = it },
            label = { Text("Confirmar Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoradoClaro)
        )

        // Mensaje de error si algo falla
        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                when {
                    email.isBlank() || contrasena.isBlank() -> error = "Completa todos los campos"
                    contrasena != confirmar                 -> error = "Las contraseñas no coinciden"
                    contrasena.length < 6                  -> error = "Mínimo 6 caracteres"
                    else                                   -> alTerminar()
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Morado)
        ) {
            Text("Registrarse", fontSize = 16.sp, color = Blanco, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Ya tienes una cuenta?",
            fontSize = 14.sp,
            color = Morado,
            modifier = Modifier.clickable { alVolver() }
        )
    }
}
