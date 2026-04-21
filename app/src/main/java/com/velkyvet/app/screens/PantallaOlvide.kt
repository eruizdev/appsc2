package com.velkyvet.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.velkyvet.app.ui.theme.*

@Composable
fun PantallaOlvide(alVolver: () -> Unit) {
    var email by remember { mutableStateOf("") }
    // Cuando es true mostramos el mensaje de exito
    var enviado by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoRosa)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = alVolver) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Morado)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("VelkyVet App", fontSize = 13.sp, color = TextoGris)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Recuperación de cuenta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Morado)
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier.width(48.dp).height(3.dp).background(Morado, RoundedCornerShape(2.dp)))
        Spacer(modifier = Modifier.height(8.dp))
        Text("Ingresa tu correo para restablecer tu contraseña", fontSize = 14.sp, color = TextoGris)

        Spacer(modifier = Modifier.height(32.dp))

        // Si ya enviamos el correo mostramos mensaje verde, si no el formulario
        if (enviado) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VerdePositivo, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Correo de recuperación enviado", color = TextoOscuro, fontSize = 14.sp)
            }
        } else {
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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { if (email.isNotBlank()) enviado = true },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Morado)
            ) {
                Text("Restablecer", fontSize = 16.sp, color = Blanco, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
