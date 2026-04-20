# VelkyVet

App veterinaria para Android. Compose + Room + Navigation.

---

## Pantallas

- Bienvenida
- Login / Registro / Recuperar contraseña
- Home con accesos rápidos
- Mis Mascotas (agregar y eliminar, guardado con Room)
- Agendar Cita con selector de horario
- Mis Citas
- Reportes
- Perfil

---

## Cómo correrlo

**Necesitas:**
- Android Studio Ladybug o más reciente
- JDK 17
- Emulador o celular Android 8.0+ (API 26)

**Pasos:**

1. Descomprime el ZIP

2. Abre una terminal dentro de la carpeta `VelkyVet` y corre:

```bash
chmod +x setup.sh && ./setup.sh
```

Esto descarga el `gradle-wrapper.jar` que Android Studio necesita para sincronizar.

3. Abre Android Studio → File → Open → selecciona la carpeta `VelkyVet`

4. Espera a que Gradle sincronice (la primera vez tarda un poco)

5. Run ▶️

---

**Si el script no funciona o usas Windows:**

Descarga este archivo manualmente y ponlo en `gradle/wrapper/gradle-wrapper.jar`:

https://raw.githubusercontent.com/gradle/gradle/v8.7.0/gradle/wrapper/gradle-wrapper.jar

---

## Estructura

```
app/src/main/java/com/velkyvet/app/
├── MainActivity.kt
├── screens/        ← todas las pantallas
├── navigation/     ← NavHost y rutas
├── database/       ← Room
└── ui/theme/       ← colores y tema
```

---

## Tecnologías

Kotlin · Jetpack Compose · Room Database · Navigation Compose · Material 3

---

## Integrantes

Juan Esteban Giraldo Ruiz
Roberto Carlos Medina Parra

---

## Notas

- El login acepta cualquier email/contraseña no vacíos, no tiene backend
- Los datos de mascotas y citas se guardan con Room y persisten entre sesiones
- Para resetear los datos: desinstalar la app o borrar datos en Ajustes del celular
