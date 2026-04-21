#!/bin/bash

echo ""
echo "=== VelkyVet - Setup inicial ==="
echo ""

JAR_PATH="gradle/wrapper/gradle-wrapper.jar"

if [ -f "$JAR_PATH" ]; then
  echo "gradle-wrapper.jar ya existe, nada que hacer."
else
  echo "Descargando gradle-wrapper.jar..."
  curl -L -o "$JAR_PATH" \
    "https://raw.githubusercontent.com/gradle/gradle/v8.7.0/gradle/wrapper/gradle-wrapper.jar" \
    2>/dev/null

  if [ -f "$JAR_PATH" ] && [ -s "$JAR_PATH" ]; then
    echo "Descarga exitosa."
  else
    echo ""
    echo "No se pudo descargar automaticamente."
    echo "Descargalo manualmente desde:"
    echo "https://raw.githubusercontent.com/gradle/gradle/v8.7.0/gradle/wrapper/gradle-wrapper.jar"
    echo "y pegalo en: gradle/wrapper/gradle-wrapper.jar"
    echo ""
    exit 1
  fi
fi

chmod +x gradlew

echo ""
echo "Listo. Abre este proyecto en Android Studio."
echo ""
