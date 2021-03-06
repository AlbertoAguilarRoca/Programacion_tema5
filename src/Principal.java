/*
 ---------------------------------
 * Autor: Alberto Aguilar Roca
 ---------------------------------
 */
/*
 ---------------------
 * Bibliografía:
 ---------------------
* https://www.discoduroderoer.es/clases-filereader-y-filewriter-para-ficheros-de-texto-en-java/
* http://lineadecodigo.com/java/numero-de-lineas-de-un-fichero/ 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) throws IOException {
		
		/*Inicializamos el array con la lista de productos*/
		Producto[] listaproductos = null;
		
		Scanner sr = new Scanner (System.in);
		int eleccion = 0;
		while(eleccion!=7) {
			System.out.println("¿Qué acción desea realizar?\n");
			System.out.println("\t1. Cargar una lista de la compra.");
			System.out.println("\t2. Mostrar productos de la lista (Antes debe haber sido cargado un archivo).");
			System.out.println("\t3. Modificar datos de los productos (Antes debe haber sido cargado un archivo).\n");
			eleccion = sr.nextInt();
			switch(eleccion) {
				case 1: listaproductos = cargarArchivo();
						break;
				case 2: imprimeProductos(listaproductos);
						break;
				case 3: modificaLista(listaproductos);
						break;
				case 7: System.out.println("Gracias por usar el gestor de listas de la compra. Hasta pronto.");
						break;
				default: System.out.println("Acción invalida\n");
			}
		}

	}// end main

	/* Funcion para cargar un archivo */
	public static Producto[] cargarArchivo() throws IOException  {

		/* Iniciamos el Buffer de entrada de datos */
		BufferedReader entrada = null;

		try {
			/* Creamos un manejador (handle) de ficheros de lectura */
			System.out.println("\nEscribe el nombre de la lista de la compra (incluye la extension).");
			Scanner sc = new Scanner(System.in);
			String archivo = sc.nextLine();
			
			/*Contamos cuantas lineas tiene el archivo que nos pasa el usuario con 
			 * la función cuentalineas*/
			final int NUMERO_PRODUCTO = cuentaLineas(archivo);

			FileReader lector = new FileReader(archivo.trim());/*Añadimos trim por si el usuario inserta algún espacio*/
			entrada = new BufferedReader(lector);

			/* Leemos linea a linea (Parsear) */
			String linea;
			Producto[] listaproductos = new Producto[NUMERO_PRODUCTO];
			int j = 0;
			while ((linea = entrada.readLine()) != null) {
				// Vamos añadiendo la info a variables individuales

				String nombreProducto = "";
				int i = 0;
				while (linea.charAt(i) != ',') {
					nombreProducto += linea.charAt(i);
					i++;
				}
				i++;
				String marca = "";
				while (linea.charAt(i) != ',') {
					marca += linea.charAt(i);
					i++;
				}
				i++;
				String precio = "";
				while (linea.charAt(i) != ',') {
					precio += linea.charAt(i);
					i++;
				}
				i++;
				String cantidad = "";
				while (i < linea.length()) {
					cantidad += linea.charAt(i);
					i++;
				}

				// introducimos todos los datos de un producto en un nuevo objeto
				listaproductos[j] = new Producto(nombreProducto, marca, Double.parseDouble(precio), Integer.parseInt(cantidad));
				j++;
			} // end while
			
			return listaproductos;
			
		} catch (FileNotFoundException file) {
			System.out.println("\n"+file.getMessage()+" Por favor, vuelva a intentarlo.\n");			
			/*En caso de fallo, volvemos a solicitar el nombre del archivo a cargar. Lo retornamos 
			 * ya que se debe retornar un array de tipo Producto*/
			return cargarArchivo();
		} finally {
			if (entrada != null) {
				System.out.println("\nCarga finalizada.\n");
				//entrada.close();
			}			
		}
	}// end cargar archivo
	
	/*Funcion para imprimir productos*/
	public static void imprimeProductos(Producto[] lista) {
		/*Buble for para mostrar los productos con un numero delante*/
		for (int i = 0; i < lista.length; i++) {
			System.out.println("- "+(i+1)+". "+lista[i].toString());
		}

	}

	/*Cuenta las lineas de un archivo*/
	public static int cuentaLineas(String fichero) throws IOException {
		
		BufferedReader archivo = null;
		int numeroLineas=0;
		try {
			FileReader lector = new FileReader(fichero);
			archivo = new BufferedReader(lector);

			String linea;
			while ((linea = archivo.readLine()) != null) {
				numeroLineas++;
			}
			
		} finally {
			if (archivo != null) {
				archivo.close();
			}
		}
		
		return numeroLineas;
		
	} //end cuentaLineas

	public static void modificaLista(Producto[] lista) {
		Scanner sr = new Scanner (System.in);

		System.out.println("\n¿Qué acción desea realizar?\n");
		System.out.println("\t1. Modificar un producto");
		System.out.println("\t2. Modificar una marca");
		System.out.println("\t3. Modificar un precio");
		System.out.println("\t4. Modificar una cantidad");
		System.out.println("\t5. Modificar todo el producto\n");
		int eleccion = sr.nextInt();
		switch(eleccion) {
			case 1: modificaNombre(lista);
					break;
			case 2: modificaMarca(lista);
					break;
			case 3: modificaPrecio(lista);
					break;
			case 4: modificaCantidad(lista);
					break;
			case 5: modificaProducto(lista);
					break;
			default: System.out.println("Acción invalida\n");
		}
	}// end modificalista
	
	public static void modificaNombre(Producto[] lista) {
		/*Imprime la lista de nuevo, por si el usuario no la ha cargado previamente*/
		imprimeProductos(lista);
		System.out.println("\n¿Qué producto quieres modificar? (Selecciones un número)");
		Scanner sr = new Scanner(System.in);
		Scanner pt = new Scanner(System.in);
		int numeroProducto = sr.nextInt() - 1;//Restamos uno para que concuerde con el array
		
		/*En caso de que el usuario escriba un numero mayor al total del array*/
		if(numeroProducto > lista.length) {
			while(numeroProducto > lista.length) {
				System.out.println("\nEl número debe ser menor al último número de la lista. Por favor, vuelve a introducirlo.");
				numeroProducto = sr.nextInt() - 1;
			}
		}
		
		System.out.println("El valor del numero es: "+numeroProducto);
		
		System.out.println("\nEscribe el nuevo nombre del producto.");
		
		String producto = pt.nextLine();
		
		lista[numeroProducto].setNombreProducto(producto.trim());
		
		System.out.println("\nEl producto ha sido modificado -> "+lista[numeroProducto].getNombreProducto());
		
	}//end modificaNombre
	
	public static void modificaMarca(Producto[] lista) {
		/*Imprime la lista de nuevo, por si el usuario no la ha cargado previamente*/
		imprimeProductos(lista);
		System.out.println("\n¿Qué producto quieres modificar? (Selecciones un número)");
		Scanner sr = new Scanner(System.in);
		Scanner pt = new Scanner(System.in);
		int numeroProducto = sr.nextInt()- 1; //Restamos uno para que concuerde con el array
		
		/*En caso de que el usuario escriba un numero mayor al total del array*/
		if(numeroProducto > lista.length) {
			while(numeroProducto > lista.length) {
				System.out.println("\nEl número debe ser menor al último número de la lista. Por favor, vuelve a introducirlo.");
				numeroProducto = sr.nextInt() - 1;
			}
		}
		
		System.out.println("\nEscribe la nueva marca del producto.");
		
		String producto = pt.nextLine();
		
		lista[numeroProducto].setMarcaProducto(producto.trim());
		
		System.out.println("\nEl producto ha sido modificado -> "+lista[numeroProducto].getMarcaProducto());
		
	}//end modificaMarca
	
	public static void modificaPrecio(Producto[] lista) {
		/*Imprime la lista de nuevo, por si el usuario no la ha cargado previamente*/
		imprimeProductos(lista);
		System.out.println("\n¿Qué producto quieres modificar? (Selecciones un número)");
		Scanner sr = new Scanner(System.in);
		Scanner pt = new Scanner(System.in);
		
		int numeroProducto = sr.nextInt() - 1;
		
		/*En caso de que el usuario escriba un numero mayor al total del array*/
		if(numeroProducto > lista.length) {
			while(numeroProducto > lista.length) {
				System.out.println("\nEl número debe ser menor al último número de la lista. Por favor, vuelve a introducirlo.");
				numeroProducto = sr.nextInt() - 1;
			}
		}
		
		System.out.println("\nEscribe el nuevo precio del producto.");
		
		double producto = pt.nextDouble();
		
		lista[numeroProducto].setPrecio(producto);
		
		System.out.println("\nEl producto ha sido modificado -> "+lista[numeroProducto].getPrecio());
		
	}//end modificaPrecio
	
	public static void modificaCantidad(Producto[] lista) {
		/*Imprime la lista de nuevo, por si el usuario no la ha cargado previamente*/
		imprimeProductos(lista);
		System.out.println("\n¿Qué producto quieres modificar? (Selecciones un número)");
		Scanner sr = new Scanner(System.in);
		Scanner pt = new Scanner(System.in);
		int numeroProducto = sr.nextInt()-1;
		
		/*En caso de que el usuario escriba un numero mayor al total del array*/
		if(numeroProducto > lista.length) {
			while(numeroProducto > lista.length) {
				System.out.println("\nEl número debe ser menor al último número de la lista. Por favor, vuelve a introducirlo.");
				numeroProducto = sr.nextInt() - 1;
			}
		}
		
		System.out.println("\nEscribe la nueva cantidad del producto.");
		
		int producto = pt.nextInt();
		
		lista[numeroProducto].setCantidad(producto);
		
		System.out.println("\nEl producto ha sido modificado -> "+lista[numeroProducto].getCantidad());
		
	}//end modificaCantidad
	
	public static void modificaProducto(Producto[] lista) {
		/*Imprime la lista de nuevo, por si el usuario no la ha cargado previamente*/
		imprimeProductos(lista);
		System.out.println("\n¿Qué producto quieres modificar? (Selecciones un número)");
		Scanner sr = new Scanner(System.in);
		Scanner pt = new Scanner(System.in);
		int numeroProducto = sr.nextInt()-1;
		
		/*En caso de que el usuario escriba un numero mayor al total del array*/
		if(numeroProducto > lista.length) {
			while(numeroProducto > lista.length) {
				System.out.println("\nEl número debe ser menor al último número de la lista. Por favor, vuelve a introducirlo.");
				numeroProducto = sr.nextInt() - 1;
			}
		}
		
		/*MODIFICAMOS EL NOMBRE*/
		
		System.out.println("\nEscribe el nuevo nombre del producto.");
		
		String nombreproducto = pt.nextLine();
		
		lista[numeroProducto].setNombreProducto(nombreproducto);
		
		/*MODIFICAMOS LA MARCA*/
		
		System.out.println("\nEscribe la nueva marca del producto.");
		
		String marcaproducto = pt.nextLine();
		
		lista[numeroProducto].setMarcaProducto(marcaproducto);
		
		/*MODIFICAMOS EL PRECIO*/
		
		System.out.println("\nEscribe el nuevo precio del producto.");
		
		double precioproducto = pt.nextDouble();
		
		lista[numeroProducto].setPrecio(precioproducto);
		
		/*MODIFICAMOS LA CANTIDAD*/
		
		System.out.println("\nEscribe la nueva cantidad del producto.");
		
		int cantidadproducto = pt.nextInt();
		
		lista[numeroProducto].setCantidad(cantidadproducto);
		
		/*Lo imprimimos*/
		
		System.out.println("\nEl producto ha sido modificado -> "+lista[numeroProducto].toString());
		
	}//end modificaCantidad
	
}// end class
