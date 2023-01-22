package es.edix.vista;

import java.io.File;
import java.util.Scanner;

import es.edix.controlador.Controller;
import es.edix.modelo.Coche;

public class Main {

	public static void main(String[] args) {

		Controller controlador = new Controller();
		File file = new File("coches.dat");
		
		if(file.exists()) {
			controlador.rellenarAlmacen(file);
			System.out.println("Almacen importado con exito \n");
		}
		
		try(Scanner sc = new Scanner(System.in)) {
			
			boolean continuar = true;
			int num;
			Coche coche;
			do {
				System.out.println("Elija una opcion:\n" + 
				"1. Anadir coche\n"+ 
				"2. Borrar coche\n" + 
				"3. Consultar coche por ID\n" + 
				"4. Listado de coches en memoria\n" + 
				"5. Exportar coches a archivo csv\n" + 
				"6. Salir");
				num = Integer.parseInt(sc.nextLine());

				switch (num) {

			//Añadir coche
				case 1:
					coche = new Coche();
					System.out.println("Introduzca la marca");
					coche.setMarca(sc.nextLine());
					System.out.println("Introduzca el modelo");
					coche.setModelo(sc.nextLine());
					System.out.println("Introduzca la matricula");
					coche.setMatricula(sc.nextLine());
					System.out.println("Introduzca el color");
					coche.setColor(sc.nextLine());
					controlador.anadirCoche(coche);
					break;

			//Borrar coche		
				case 2:
					System.out.println("Introduzca ID del coche que desea borrar");
					controlador.delete(Integer.parseInt(sc.nextLine()));
					break;

			//Buscar coche por ID		
				case 3:
					System.out.println("Introduzca ID del coche que desea buscar");
					coche = controlador.cocheId(Integer.parseInt(sc.nextLine()));
					System.out.println(coche.toString());
					break;

			//Pedir listado de coches		
				case 4:
					System.out.println("Listado de coches:");
					controlador.list();
					break;

			//Exportar listado de coches a csv		
				case 5:
					controlador.exportarAlmacenExcel();
					break;

			//Salir		
				case 6:
					System.out.println("Saliendo");
					controlador.exportarAlmacenData();
					continuar = false;
					break;

			//Menu de opciones		
				default:
					System.out.println("Elija una opcion");
				}

			} while (continuar);
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
