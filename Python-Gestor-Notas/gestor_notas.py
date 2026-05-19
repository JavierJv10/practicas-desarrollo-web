# Función que imprime las opciones del menú en pantalla
def mostrar_menu():
    print ("\n--- MENÚ PRINCIPAL ---")
    print("1. Añadir una nota")
    print("2. Ver todas las notas")
    print("3. Buscar una nota por palabra clave")
    print("4. Eliminar una nota")
    print("5. Filtrar notas por categoría")
    print("6. Salir")

# Función principal con el bucle que mantiene el programa en marcha
def main():
    notas = []
    print("¡Bienvenido al Gestor de Notas!")

    while True:
        mostrar_menu()
        opcion = input ("\nElige una opción (1-6): ")

        if opcion == "1":
            añadir_nota(notas)
        elif opcion == "2":
            ver_notas(notas)
        elif opcion == "3":
            buscar_nota(notas)
        elif opcion == "4":
            eliminar_nota(notas)
        elif opcion == "5":
            filtrar_por_categoria(notas)
        elif opcion == "6":
            print("¡Hasta luego!")
            break
        else:
            print ("Opción no válida, prueba otra vez.")

# Función que pide datos al usuario y añade la nota a la lista
def añadir_nota(notas):
    # Pedimos el título y contenido de la nota al usuario
    titulo = input("Escribe el título de la nota: ")
    contenido = input("Escribe el contenido de la nota: ")
    categoria = input("Escribe la categoría (estudio/personal/trabajo): ")

    # Comprobamos si ya existe una nota con ese título
    for nota in notas:
        if nota["titulo"].lower() == titulo.lower():
            respuesta = input("Ya existe una nota con ese título. ¿Quieres sobreescribirla? (s/n): ")
            if respuesta.lower() == "s":
                nota["contenido"] = contenido
                print("Nota sobreescrita correctamente.")
            else:
                print ("No se ha guardado la nota.")
            return

    # Pedimos confirmación antes de guardar
    confirmacion = input ("¿Guardar esta nota? (s/n): ")
    if confirmacion.lower () == "s":
        notas.append({"titulo": titulo, "contenido": contenido, "categoria": categoria})
        print("Nota guardada correctamente.")
    else:
        print("Nota descartada.")

# Función que muestra todas las notas guardadas
def ver_notas(notas):
    # Comprobamos si hay notas guardadas
    if len(notas) == 0:
        print ("No hay notas guardadas.")
    else:
        print ("\n--- TUS NOTAS ---")
        for i, nota in enumerate(notas):
            print(f"{i+1}. Título: {nota['titulo']}")
            print(f"   Contenido: {nota['contenido']}")

# Función que busca notas por palabra clave en título o contenido
def buscar_nota(notas):
    # Pedimos la palabra clave al usuario
    palabra = input("Escribe la palabra clave a buscar: ")
    encontradas = []

    # Recorremos las notas buscando la palabra en título o contenido
    for nota in notas:
        if palabra.lower() in nota["titulo"].lower() or palabra.lower() in nota["contenido"].lower():
            encontradas.append(nota)

    if len(encontradas) == 0:
        print("No se encontraron notas con esa palabra.")
    else:
        print(f"\nSe encontraron {len(encontradas)} nota(s):")
        for i, nota in enumerate(encontradas):
            print(f"{i+1}. Título: {nota['titulo']}")
            print(f"   Contenido: {nota['contenido']}")

# Función que elimina una nota según el número elegido por el usuario
def eliminar_nota(notas):
    # Comprobamos si hay notas para eliminar
    if len(notas) == 0:
        print("No hay notas para eliminar.")
        return

    # Mostramos las notas numeradas
    ver_notas(notas)

    # Pedimos el número de la nota a eliminar y controlamos errores
    try:
        numero = int(input("\n¿Qué número de nota quieres eliminar? "))
        if numero < 1 or numero > len(notas):
            print("Numero no válido.")
            return
    except ValueError:
        print("Debes introducir un número.")
        return

    # Confirmamos antes de borrar
    confirmacion = input(f'¿Seguro que quieres eliminar "{notas[numero-1]["titulo"]}"? (s/n): ')
    if confirmacion.lower() == "s":
        notas.pop(numero - 1)
        print("Nota eliminada correctamente.")
    else:
        print("Operación cancelada.")
        
# Función que filtra y muestra las notas por categoría
def filtrar_por_categoria(notas):
    # Pedimos la categoría al usuario
    categoria = input("Escribe la categoría a buscar (estudio/personal/trabajo): ")
    encontradas = []
    
    # Recorremos las notas buscando la categoría
    for nota in notas:
        if nota["categoria"].lower() == categoria.lower():
            encontradas.append(nota)
    
    if len(encontradas) == 0:
        print("No hay notas con esa categoría.")
    else:
        print(f"\nNotas de la categoría '{categoria}':")
        for i, nota in enumerate(encontradas):
            print(f"{i+1}. Título: {nota['titulo']}")
            print(f"   Contenido: {nota['contenido']}")

main()

# Nombre: Javier Jimeno Villarreal
# Fecha: 09/04/2026
# Descripción: Gestor de notas personalizado que permite añadir, ver,
# buscar y eliminar notas desde la terminal usando funciones,
# listas de diccionarios, bucles y condicionales en Python.
# Ampliación: Se ha añadido la opción de categorizar las notas
# y filtrarlas por categoría.