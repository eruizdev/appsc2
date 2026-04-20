package com.velkyvet.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.velkyvet.app.navigation.NavegacionPrincipal
import com.velkyvet.app.ui.theme.VelkyVetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VelkyVetTheme {
                val nav = rememberNavController()
                NavegacionPrincipal(nav = nav)
            }
        }
    }
}
