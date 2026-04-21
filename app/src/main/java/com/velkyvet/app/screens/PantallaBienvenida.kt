package com.velkyvet.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.velkyvet.app.ui.theme.*

// Primera pantalla que ve el usuario al abrir la app
@Composable
fun PantallaBienvenida(alEntrar: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoRosa)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Icono de la app
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Morado, RoundedCornerShape(30.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Pets,
                contentDescription = null,
                tint = Blanco,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Nombre de la app
        Text(
            text = "VelkyVet APP",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Morado,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Descripcion corta
        Text(
            text = "Gestiona a tus mascotas y sus citas veterinarias",
            fontSize = 16.sp,
            color = TextoGris,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Boton para entrar a la app
        Button(
            onClick = alEntrar,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Morado)
        ) {
            Text(
                text = "Ingresemos a la app",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Blanco
            )
        }
    }
}
