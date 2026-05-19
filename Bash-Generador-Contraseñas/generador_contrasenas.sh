#!/bin/bash
#Generador de contraseñas aleatorias

caracteres_minusculas="abcdefghijklmnopqrstuvwxyz"
caracteres_mayusculas="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
caracteres_numeros="0123456789"
caracteres_especiales="!@#$%&*()-_=+[]{}?"

#Variables de control
longitud=12
usar_mayusculas="s"
usar_numeros="s"
usar_especiales="s"

echo "Bienvenido al generador de contraseñas 🔐"

read -p "Introduce la longitud de la contraseña (por defecto 12): " entrada_longitud
if [[ $entrada_longitud =~ ^[0-9]+$ ]] && (( entrada_longitud > 0 )); then
    longitud=$entrada_longitud
fi

read -p "¿Deseas incluir letras mayúsculas? (s/n, por defecto s): " entrada_mayusculas
if [[ -n $entrada_mayusculas ]]; then
    usar_mayusculas=${entrada_mayusculas,,}
fi

read -p "¿Deseas incluir números? (s/n, por defecto s): " entrada_numeros
if [[ -n $entrada_numeros ]]; then
    usar_numeros=${entrada_numeros,,}
fi

read -p "¿Deseas incluir caracteres especiales? (s/n, por defecto s): " entrada_especiales
if [[ -n $entrada_especiales ]]; then
    usar_especiales=${entrada_especiales,,}
fi

#Construcción de la lista de caracteres permitidos
caracteres_totales="$caracteres_minusculas"

if [[ $usar_mayusculas == "s" ]]; then
    caracteres_totales="$caracteres_totales$caracteres_mayusculas"
fi

if [[ $usar_numeros == "s" ]]; then
    caracteres_totales="$caracteres_totales$caracteres_numeros"
fi

if [[ $usar_especiales == "s" ]]; then
    caracteres_totales="$caracteres_totales$caracteres_especiales"
fi

#Generación de la contraseña
contrasena=""
longitud_caracteres=${#caracteres_totales}

for (( i=0; i<longitud; i++ )); do
    indice=$(( RANDOM % longitud_caracteres ))
    caracter_aleatorio=${caracteres_totales:$indice:1}
    contrasena="$contrasena$caracter_aleatorio"
done

echo "--------------------------------------"
echo "Tu contraseña generada es: $contrasena"
echo "--------------------------------------"

#Guardar en un fichero opcional
read -p "¿Quieres guardar la contraseña en un archivo? (s/n): " guardar
guardar=${guardar,,}

if [[ $guardar == "s" ]]; then
    read -p "Introduce el nombre para identificar esta contraseña: " servicio
    echo "$servicio: $contrasena" >> contraseñas_guardadas.txt
    echo "Contraseña guardada con éxito en contraseñas_guardadas.txt"
fi

echo "¡Gracias por usar el generador de contraseñas! ¡Hasta luego! 👋"
