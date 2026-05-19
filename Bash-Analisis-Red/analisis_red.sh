#!/bin/bash

while true; do
    echo "Análisis basico de la red del sistema"
    echo "---"
    echo "Selecciona una opción"
    echo "1. Mostrar configuracion de red"
    echo "2. Comprobar conectividad de red (ping)"
    echo "3. Mostrar la tabla de rutas"
    echo "4. Resolver nombres de dominio"
    echo "5. Mostrar conexiones activas"
    echo "6. Mostrar la direccion MAC"
    echo "7. SALIR"
    echo "-"

    # MAC es la direccion fisica
    read -p "Introduce una opcion del 1 al 7: " opcion

    case $opcion in
        1)
            # Nota: puede requerir instalar net-tools (sudo apt install net-tools)
            echo "Configuracion de red: "
            ifconfig
            ;;
        2)
            read -p "Introduce una IP o un dominio a comprobar: " destino
            echo "Haciendo un ping a $destino"
            ping -c 4 "$destino"
            ;;
        3)
            echo "La tabla de rutas es: "
            ip route
            ;;
        4)
            read -p "Introduce el dominio a resolver: " dominio
            echo "Resolviendo el dominio..."
            nslookup "$dominio"
            ;;
        5)
            echo "Conexiones activas"
            ss -tuln
            ;;
        6)
            echo "Direccion MAC de las interfaces de red"
            ip link
            ;;
        7)
            echo "Saliendo del Sistema..."
            exit 0
            ;;
        *)
            echo "Opcion no válida, por favor, introduce un numero del 1 al 7"
            ;;
    esac
done
