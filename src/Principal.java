/*
 ---------------------------------
 * Autor: Alberto Aguilar Roca
 ---------------------------------
 */
/*
 ---------------------
 * Bibliografía:
 ---------------------
* https://www.discoduroderoer.es/clases-filereader-y-filewriter-para-ficheros-de-texto-en-java/ -- Info acerca de la lectura y escritura de archivos
* http://lineadecodigo.com/java/numero-de-lineas-de-un-fichero/ -- Contar el número de lineas de un fichero 
* https://www.w3schools.com/java/java_arraylist.asp -- Cómo funcionan los arraylist
* https://decodigo.com/java-crear-archivos-de-texto -- Crear archivos de texto y escribir en ellos
* https://es.stackoverflow.com/questions/29408/como-limitar-la-cantidad-de-decimales-de-un-double/29410 --Limitar el número de decimales a dos
* https://www.campusmvp.es/recursos/post/java-como-listar-filtrar-y-obtener-informacion-de-carpetas-y-archivos.aspx -- Mostrar los archivos de una carpeta
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;

public class Principal {

	public static void main(String[] args) throws IOException {
		
		/*Inicializamos el array con la lista de productos*/
		ArrayList<Producto> listaproductos = new ArrayList<Producto>();
		
		Scanner sr = new Scanner (System.in);
		int eleccion = 0;
		while(eleccion!=9) {
			System.out.println("¿Qué acción desea realizar?\n");
			System.out.println("\t1. Crear una nueva lista de la compra.");
			System.out.println("\t2. Cargar una lista de la compra.");
			System.out.println("\t3. Mostrar productos de la lista (Antes debe haber sido cargado un archivo).");
			System.out.println("\t4. Modificar datos de los productos (Antes debe haber sido cargado un archivo).");
			System.out.println("\t5. Añadir un nuevo producto (Antes debe haber sido cargado un archivo).");
			System.out.println("\t6. Guardar modificaciones en una nueva lista (Antes debe haber sido cargado un archivo).");
			System.out.println("\t9. Finalizar programa.\n");
			eleccion = sr.nextInt();
			switch(eleccion) {
				case 1: nuevoArchivo();
						break;
				case 2: listaproductos = cargarArchivo(); //guardamos el arraylist en memoria para poder usarlo en el resto de funciones
						break;
				case 3: imprimeProductos(listaproductos);
						break;
				case 4: modificaLista(listaproductos);
						break;
				case 5: addToList(listaproductos);
						break;
				case 6: guardaArchivo(listaproductos);
						break;
				case 9: System.out.println("Gracias por usar el gestor de listas de la compra. Hasta pronto.");
						break;
				default: System.out.println("Acción invalida\n");
			}
		}

	}// end main

	/* Funcion para cargar un archivo */
	public static ArrayList<Producto> cargarArchivo() throws IOException  {

		/* Iniciamos el Buffer de entrada de datos */
		BufferedReader entrada = null;

		try {
			/*Mostramos al usuario los archivos que hay disponibles para cargar*/
			mostrarArchivos();
			
			System.out.println("\nEscribe el nombre de la lista de la compra (incluye la extension).");
			Scanner sc = new Scanner(System.in);
			String archivo = sc.nextLine();
			/* Creamos un manejador (handle) de ficheros de lectura */
			FileReader lector = new FileReader(archivo.trim());/*Añadimos trim por si el usuario inserta algún espacio*/
			entrada = new BufferedReader(lector);

			/* Leemos linea a linea (Parsear) */
			String linea;
			ArrayList<Producto> listaproductos = new ArrayList<Producto>();
			
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
				Producto producto = new Producto(nombreProducto, marca, Double.parseDouble(precio), Integer.parseInt(cantidad));
				listaproductos.add(producto);
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
				entrada.close();
			}			
		}
	}// end cargar archivo
	
	/*función para mostrar los archivos que hay en la carpeta*/
	public static void mostrarArchivos() {
	     try {
	    	 
	 		/*Obtenemos la ubicación de la carpeta*/
	    	 
	       String sCarpAct = System.getProperty("user.dir");
	       File carpeta = new File(sCarpAct);
	       /*El metodo list permitirá mostrar todos los archivos de la carpeta*/
	       String[] listado = carpeta.list();
	       
	       /*En caso de que no haya archivos .txt, mostrará el siguiente mensaje*/
	       if (listado == null || listado.length == 0) {
	           System.out.println("No hay listas de la compra dentro de la carpeta actual");
	           return;
	       }
	       else {
	    	   System.out.println("Listas de la compra disponibles para cargar:\n");
	           for (int i=0; i< listado.length; i++) {
	        	   /*Solo mostramos los archivos .txt*/
	               if (listado[i].contains(".txt")) {	            	   
	            	   System.out.println(listado[i]);	            	   
	               }     
	           }
	           System.out.println("\n-------------------");
	       }
	       
	       } catch(Exception e) {
	       e.printStackTrace();
	       }
	  
	}//end mostrarArchivos
	
	/*Funcion para imprimir productos*/
	public static void imprimeProductos(ArrayList<Producto> lista) {
		/*Buble for para mostrar los productos con un numero delante*/
		for (int i = 0; i < lista.size(); i++) {
			System.out.println("- "+(i+1)+". "+lista.get(i).toString());
		}
		
		double total = 0;
		
		for (int i = 0; i < lista.size(); i++) {
			double precio = lista.get(i).getPrecio() * lista.get(i).getCantidad();
			total += precio;
		}
		
		/*Clase para limitar el número de decimales a dos*/
		DecimalFormat decimal = new DecimalFormat("#.00");
		
		System.out.println("\n----------------------------------------");
		System.out.println("El coste total de la lista es: "+decimal.format(total)+" €");
		System.out.println("----------------------------------------\n");

	}

	public static void modificaLista(ArrayList<Producto> lista) {
		Scanner sr = new Scanner (System.in);
		
		/*Imprime la lista de nuevo, por si el usuario no la ha cargado previamente*/
		imprimeProductos(lista);
		/*El usuario selecciona qué producto quiere modificar*/
		System.out.println("\n¿Qué producto quieres modificar? (Selecciones un número)");
		int numeroProducto = sr.nextInt() - 1;//Restamos uno para que concuerde con el array
		
		/*En caso de que el usuario escriba un numero mayor al total del array, deberá escribirlo de nuevo*/
		if(numeroProducto > lista.size()-1) {
			while(numeroProducto > lista.size()-1) {
				System.out.println("\nEl número debe ser menor al último número de la lista. Por favor, vuelve a introducirlo.");
				numeroProducto = sr.nextInt() - 1;
			}
		}
		
		/*El usuario indica lo que quiere hacer con el producto seleccionado*/
		System.out.println("\n¿Qué quiere hacer con el producto?\n");
		System.out.println("\t1. Modificar el nombre");
		System.out.println("\t2. Modificar la marca");
		System.out.println("\t3. Modificar el precio");
		System.out.println("\t4. Modificar la cantidad");
		System.out.println("\t5. Modificar todo el producto");
		System.out.println("\t6. Eliminar el producto\n");
		int eleccion = sr.nextInt();
		switch(eleccion) {
			case 1: modificaNombre(lista, numeroProducto);
					break;
			case 2: modificaMarca(lista, numeroProducto);
					break;
			case 3: modificaPrecio(lista, numeroProducto);
					break;
			case 4: modificaCantidad(lista, numeroProducto);
					break;
			case 5: modificaProducto(lista, numeroProducto);
					break;
			case 6: eliminaProducto(lista, numeroProducto);
			break;
			default: System.out.println("Acción invalida\n");
		}
	}// end modificalista
	
	public static void modificaNombre(ArrayList<Producto> lista, int numeroProducto) {
		
		Scanner pt = new Scanner (System.in);
		/*El usuario escribe el nuevo producto*/
		System.out.println("\nEscribe el nuevo nombre del producto.");	
		String producto = pt.nextLine();
		
		lista.get(numeroProducto).setNombreProducto(producto.trim());
		
		System.out.println("\nEl producto ha sido modificado -> "+lista.get(numeroProducto).getNombreProducto());
		
	}//end modificaNombre
	
	public static void modificaMarca(ArrayList<Producto> lista, int numeroProducto) {
		Scanner pt = new Scanner (System.in);
		/*El usuario escribe la nueva marca del producto*/
		System.out.println("\nEscribe la nueva marca del producto.");	
		String producto = pt.nextLine();
		
		lista.get(numeroProducto).setMarcaProducto(producto.trim());
		
		System.out.println("\nEl producto ha sido modificado -> "+lista.get(numeroProducto).getMarcaProducto());
		
	}//end modificaMarca
	
	public static void modificaPrecio(ArrayList<Producto> lista, int numeroProducto) {
		Scanner pt = new Scanner (System.in);
		/*El usuario escribe el nuevo precio*/
		System.out.println("\nEscribe el nuevo precio del producto (¡Separa los decimales con una coma!)");	
		double precio = pt.nextDouble();
		
		lista.get(numeroProducto).setPrecio(precio);
		
		System.out.println("\nEl producto ha sido modificado -> "+lista.get(numeroProducto).getPrecio());
		
	}//end modificaPrecio
	
	public static void modificaCantidad(ArrayList<Producto> lista, int numeroProducto) {
		Scanner pt = new Scanner (System.in);
		/*El usuario escribe la nueva cantidad de producto*/
		System.out.println("\nEscribe la nueva cantidad del producto.");	
		int cantidad = pt.nextInt();
		
		lista.get(numeroProducto).setCantidad(cantidad);
		
		System.out.println("\nEl producto ha sido modificado -> "+lista.get(numeroProducto).getCantidad());
		
	}//end modificaCantidad
	
	public static void modificaProducto(ArrayList<Producto> lista, int numeroProducto) {

		/*modificamos el nombre*/
		
		modificaNombre(lista, numeroProducto);
		
		/*modificamos la marca*/
		
		modificaMarca(lista, numeroProducto);
		
		/*modificamos el precio*/
		
		modificaPrecio(lista, numeroProducto);
		
		/*modificamos la cantidad*/
		
		modificaCantidad(lista, numeroProducto);
		
		/*Lo imprimimos*/
		
		System.out.println("\nEl producto ha sido modificado -> "+lista.get(numeroProducto).toString());
		
	}//end modificaCantidad

	public static void eliminaProducto(ArrayList<Producto> lista, int numeroProducto) {

		/*Primero mostramos el producto que se elimina*/
		System.out.println("\nEl producto ha sido eliminado -> "+lista.get(numeroProducto).toString());
		/*Después lo borramos*/
		lista.remove(numeroProducto);
	}//end eliminaProductos
	
	public static void addToList(ArrayList<Producto> lista) {
		Scanner sr = new Scanner(System.in);
		/*El usuario escribe el nombre del producto*/
		System.out.println("\nEscriba el nombre del producto.\n");
		
		String nombre = sr.nextLine();
		
		/*El usuario escribe la marca del producto*/
		System.out.println("\nEscriba la marca del producto.\n");
		
		String marca = sr.nextLine();
		
		/*El usuario escribe el precio del producto*/
		System.out.println("\nEscriba el precio del producto.\n");
		
		double precio = sr.nextDouble();
		
		/*El usuario escribe la cantidad del producto*/
		System.out.println("\nEscriba la cantidad del producto.\n");
		
		int cantidad = sr.nextInt();
		
		Producto nuevoProducto = new Producto(nombre.trim(), marca.trim(), precio, cantidad);
		
		lista.add(nuevoProducto);

		System.out.println("\nEl producto ha sido añadido.\n");
	}

	public static void guardaArchivo(ArrayList<Producto> lista) throws IOException {
		
		try {
			Scanner sr = new Scanner(System.in);
			System.out.println("\nPor favor, escriba el nombre del nuevo archivo junto a la extensión .txt.");
			String nombreArchivo = sr.nextLine();
			
			/*Creamos el archivo*/
			
			File archivo = new File(nombreArchivo);
			
			/*Comprobamos si ya había sido creado previamente dicho archivo. Si no, se crea*/
			
			if(!archivo.exists()) {
				archivo.createNewFile();
			}
			
			/*Especificamos qué archivo vamos a modificar con el filewriter y
			Creamos un BufferWriter para escribir en el archivo*/
			FileWriter fw = new FileWriter(archivo);
			
			for (int i=0; i < lista.size(); i++) {
				fw.write(lista.get(i).getNombreProducto()+",");
				fw.write(lista.get(i).getMarcaProducto()+",");
				fw.write(lista.get(i).getPrecio()+",");			
				fw.write(lista.get(i).getCantidad()+"\n");
			}
			
			/*Cerramos el nuevo archivo creado*/
			fw.close();
			
		} finally {
			System.out.println("\nArchivo guardado.");
		}
		
	}//end guardaarchivo
	
	public static void nuevoArchivo() throws IOException {
		
		try {
			Scanner sr = new Scanner(System.in);
			Scanner pt = new Scanner(System.in);
			System.out.println("\nPor favor, escriba el nombre del nuevo archivo junto a la extensión .txt.");
			String nombreArchivo = sr.nextLine();
			
			/*Creamos el archivo*/
			
			File archivo = new File(nombreArchivo);
			
			/*Comprobamos si ya había sido creado previamente dicho archivo. Si no, se crea*/
			
			if(!archivo.exists()) {
				archivo.createNewFile();
			} else {
				System.out.println("Hemos sobreescrito el archivo.\n");
			}
			
			/*Especificamos qué archivo vamos a modificar con el filewriter
			para escribir en el archivo*/
			FileWriter fw = new FileWriter(archivo);
			
			
			String decision = "Y";
			String nombre, marca, precio, cantidad;
			while(!decision.equals("N")) {
				System.out.println("¿Desea añadir un producto? [Y/N]");
				decision = sr.nextLine();
				switch(decision.toUpperCase()) {
					case "Y":
						/*Nombre del producto*/
						System.out.println("\nNombre del producto:");
						nombre = pt.nextLine();
						
						/*Marca del producto*/
						System.out.println("\nMarca del producto:");
						marca = pt.nextLine();
						
						/*precio del producto*/
						System.out.println("\nPrecio del producto (¡Los decimales separados por punto!):");
						precio = pt.nextLine();
						
						/*Cantidad del producto*/
						System.out.println("\nCantidad de producto:");
						cantidad = pt.nextLine();
						
						fw.write(nombre+","+marca+","+precio+","+cantidad+"\n");
						
						break;
						
					case "N":
							break; 
					default: System.out.println("Comando invalido");
				}
				
			}
			
			/*Cerramos el nuevo archivo creado*/
			fw.close();
			
		} finally {
			System.out.println("\nArchivo guardado.");
		}
		
	}//end guardaarchivo
	
}// end class
