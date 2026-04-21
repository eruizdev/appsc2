package com.velkyvet.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val colores = lightColorScheme(
    primary         = Morado,
    onPrimary       = Blanco,
    secondary       = MoradoClaro,
    onSecondary     = Blanco,
    background      = FondoRosa,
    onBackground    = TextoOscuro,
    surface         = Blanco,
    onSurface       = TextoOscuro,
    surfaceVariant  = Blanco
)

@Composable
fun VelkyVetTheme(contenido: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colores,
        content = contenido
    )
}
