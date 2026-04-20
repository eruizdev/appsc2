package com.velkyvet.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla de mascotas en la base de datos local
@Entity(tableName = "mascotas")
data class Mascota(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val edad: String,
    val raza: String,
    val contacto: String
)

// Tabla de citas en la base de datos local
@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreMascota: String,
    val especialidad: String,
    val horario: String,
    val estado: String = "Pendiente"
)
