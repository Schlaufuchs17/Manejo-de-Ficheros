package es.edix.controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.edix.modelo.Coche;

public class Controller implements Serializable{
	
	
	private static final long serialVersionUID = -6944691566078896150L;
	public ArrayList<Coche> almacen;
	private int acumulador = 0;
	
	public Controller() {
		super();
		almacen = new ArrayList<Coche>();
	}
	
	public String rellenarAlmacen(File file){
		try (FileInputStream fis = new FileInputStream(file);
			 ObjectInputStream ois = new ObjectInputStream(fis);) {
								
				@SuppressWarnings("unchecked")
				ArrayList<Coche> lista = (ArrayList<Coche>)ois.readObject();
				almacen = lista;
				return "Coches cargados";				
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
		return null; 
	}
	
	/*Exportamos el archivo "coches.dat" con unos coches ya guardados,
	 * para tener un listado cargado */
	public void exportarAlmacenData() {

		try (FileOutputStream fos = new FileOutputStream(new File("coches.dat"));
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {

			oos.writeObject(almacen);
			System.out.println("Archivo codificado en coches.dat");
		} catch (IOException e) {
			System.out.println("Error al exportar");
			e.printStackTrace();
		}

	}

	/*Con el metodo "exportarAlmacenExcel" exportamos el listado de coches al formato .csv*/
	public void exportarAlmacenExcel() {
		try (FileWriter fw = new FileWriter(new File("Almacen.csv")); 
			 BufferedWriter bw = new BufferedWriter(fw);) {

			for (int i = 0; i < almacen.size(); i++) {
				bw.write(almacen.get(i).toString());
				bw.newLine();
			}
			bw.flush();
			System.out.println("Archivo exportado a Almacen.csv");
		} catch (IOException e) {
			System.out.println("Error al exportar");
			e.printStackTrace();
		}
	}
	
	
	/*Con este metodo podemos añadir un cche nuevo al listado,
	 * pero con el condicional if-else detectamos si la matricula que hemos
	 * introducido esta repetida, y no nos dejara añadirlo*/
	public String anadirCoche(Coche coche) {
		if(buscarCocheMatricula(coche.getMatricula()) != null) {
			return "La matricula ya esta registrada, no puede registrar este coche";
		}else {			
			Collections.sort(almacen);
			coche.setId(comprobador(acumulador));
			almacen.add(coche);
			return "Coche anadido";
		}
		
	}
	
	/*Con el metodo "buscarCocheMatricula" podemos buscar las matriculas que
	 * estan registradas*/
	public String buscarCocheMatricula(String matricula) {
		Coche coche;
		for (int i = 0; i < almacen.size(); i++) {
			coche = almacen.get(i);

			if (coche.getMatricula().equalsIgnoreCase(matricula)) {
				return coche.toString();
			}
		}
		
		return null;
	}
	
	public Coche cocheId(int id) {
		for (Coche c : almacen) {
			if (c.getId() == id) {
				return c;
			}
		}
		System.out.println("ID no encontrada");
		return null;
	}
	
	/*Con el metodo "delete" podemos borrar los coches que esten en el listado de memoria,
	 * pero con las excepciones try-catch no nos dejara borrar ninguno que no este añadido*/
	public Coche delete(int id) {
		try {
			Coche c = cocheId(id);
			int n = almacen.indexOf(c);			
			return almacen.remove(n);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No existe esa ID en el almacen");
			return null;
		}
	}
	
	/*Con el metodo "list" y el condicional if-else podemos lanzar un mensaje por consola
	 * si no hay nada en el almacen*/
	public void list(){
		if(!almacen.isEmpty()) {
			Collections.sort(almacen);
			for (Coche c : almacen) {
				System.out.println(c.toString());
			}
		}else {
			System.out.println("No hay ningun coche en el almacen");			
		}
	}
	
	public int comprobador(int id) {
		for(int i=0;i<almacen.size();i++) {
			if(almacen.get(i).getId()==id) {
				++id;
			}
		}
		return id;	
	}
		
}