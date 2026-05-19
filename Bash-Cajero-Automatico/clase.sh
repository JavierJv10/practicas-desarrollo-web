#!/bin/bash

saldo=2000

 
mostrar_saldo() {
    echo "Tu saldo actual es: $saldo"
}

depositar() {
    read -p "Ingresa la cantidad de dinero a depositar: " cantidad
    if [[ ! $cantidad =~ ^[0-9]+$ ]] || (( cantidad <=0 )); then
     echo "Error, introduce un número válido"
    else
     saldo=$(( saldo + cantidad ))
     echo "Has depositado: $cantidad. Tu saldo es ahora: $saldo"
    fi
}


retirar() {
    echo " Tu saldo actual es : $saldo"
    read -p "Ingresa la cantidad de dinero a retirar: " cantidad
    
    if [[ ! $cantidad =~ ^[0-9]+$ ]] || (( cantidad <=0 )); then
     echo "Error, introduce un número válido"

    elif (( cantidad > saldo )); then
    echo " Fondos insuficientes"
    else 
     saldo=$(( saldo - cantidad ))
     echo "Has retirado: $cantidad. Su nuevo saldo es: $saldo"
     fi
}

menu() {
    while true; do
      echo "Bienvenido al cajero robomoney 3000 🦊"
      echo "1. Consultar el saldo"
      echo "2. Hacer un depósito"
      echo "3. Hacer un retiro"
      echo "4. Salir"
      read -p "Selecciona una opción del 1 al 4: " opcion
        case $opcion in
         1)
          clear
          mostrar_saldo
           
          ;;
         2)
          clear
          depositar
         
          ;;
         3) 
         clear
         retirar
         
           ;;
         4) 
          clear
         echo "Adiós , gracias por darnos tu dinero jeje " 
            sleep 2
            
            exit 0
            ;;
        *) echo "Opción no válida, selecciona un número de 1 a 4" 
         continue ;;
        esac 
    done      

}

menu 