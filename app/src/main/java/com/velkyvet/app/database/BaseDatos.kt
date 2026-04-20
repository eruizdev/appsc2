package com.velkyvet.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Le decimos a Room cuales son nuestras tablas
@Database(
    entities = [Mascota::class, Cita::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mascotaDao(): MascotaDao
    abstract fun citaDao(): CitaDao
}

// Singleton para que solo exista una base de datos en toda la app
object BaseDatos {
    private var instancia: AppDatabase? = null

    fun obtener(context: Context): AppDatabase {
        // Si ya existe la usamos, si no, la creamos
        if (instancia == null) {
            instancia = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "velkyvet_db"
            ).build()
        }
        return instancia!!
    }
}
