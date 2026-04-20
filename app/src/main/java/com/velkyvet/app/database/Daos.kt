package com.velkyvet.app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Funciones para manejar mascotas en la base de datos
@Dao
interface MascotaDao {

    // Trae todas las mascotas guardadas
    @Query("SELECT * FROM mascotas")
    fun obtenerTodas(): Flow<List<Mascota>>

    // Guarda una mascota nueva
    @Insert
    suspend fun insertar(mascota: Mascota)

    // Borra una mascota
    @Delete
    suspend fun eliminar(mascota: Mascota)
}

// Funciones para manejar citas en la base de datos
@Dao
interface CitaDao {

    // Trae todas las citas guardadas
    @Query("SELECT * FROM citas")
    fun obtenerTodas(): Flow<List<Cita>>

    // Guarda una cita nueva
    @Insert
    suspend fun insertar(cita: Cita)

    // Borra una cita
    @Delete
    suspend fun eliminar(cita: Cita)
}
