package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EvaluableCollections {

	public static void main(String[] args) {
		
		//1º y 2º ejercicio: Declarar e inicializar un ArrayList de tipo String llamado alumnos
		System.out.println("1º y 2º EJERCICIO: Declarar e inicializar un ArrayList de tipo String");
		ArrayList<String> alumnos = new ArrayList<>(
				List.of("Jesus", "Javier", "Ana", "Luisa", "Diego")
		);
		
		
		//3º ejercicio: Mostrar por pantalla todos los nombres indicando su posición
		System.out.println("3º EJERCICIO: Mostrar por pantalla todos los nombres indicando su posición");
		for (int i= 0; i < alumnos.size(); i++) {
			System.out.println("Posición " + i + ": " + alumnos.get(i));
		}
		
		
		System.out.println("---------------------------------------------------------------------------");
		//4º ejercicio: Ordenar alfabéticamente la lista de nombres
		System.out.println("4º EJERCICIO: Ordenar la lista alfabéticamente");
		Collections.sort(alumnos);
		
		for(String nombre : alumnos) {
			System.out.println(nombre);
		}
			
		
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("5º EJERCICIO: Operaciones CRUD");
		System.out.println("---------------------------------------------------------------------------");
		//5º ejercicio: Operacionnes CRUD
		//5.1 CREATE: añadir un nuevo nombre a la lista.
		System.out.println("5.1 EJERCICIO: CREATE: añadir un nuevo nombre a la lista.");
		alumnos.add("Pedro");
		System.out.println("---- Lista con nuevo nombre: " + alumnos);
		
		
		System.out.println("---------------------------------------------------------------------------");
		//5.2 READ: mostrar el nombre que se encuentra en una posición concreta.
		System.out.println("5.2 EJERCICIO: READ: mostrar el nombre que se encuentra en una posición concreta.");
		String alumnoLeido = alumnos.get(2);
		System.out.println("---- El alumno de la posición 2 es: " + alumnoLeido);
		
		
		System.out.println("---------------------------------------------------------------------------");
		//5.3 UPDATE: modificar el nombre que hay en una posición determinada.
		System.out.println("5.3 EJERCICIO: UPDATE: modificar el nombre que hay en una posición determinada.");
		alumnos.set(0, "Roberto");
		System.out.println("---- Lista actualizada tras UPDATE en posición 0: " + alumnos);
		
		
		System.out.println("---------------------------------------------------------------------------");
		//5.4 DELETE: eliminar un nombre de la lista.
		System.out.println("5.4 EJERCICIO: DELETE: eliminar un nombre de la lista.");
		alumnos.remove("Javier");
		System.out.println("---- Lista con nombre Javier eliminado: " + alumnos);
		
			
	}

}