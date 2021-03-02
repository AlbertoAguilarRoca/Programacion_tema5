
public class Producto {

	/*Atributos*/
	
	private String nombreProducto;
	private String marcaProducto;
	private double precio;
	private int cantidad;
	
	/*Constructor*/
	
	public Producto(String nombreProducto, String marcaProducto, double precio, int cantidad) {
		this.nombreProducto=nombreProducto;
		this.marcaProducto=marcaProducto;
		this.precio=precio;
		this.cantidad=cantidad;
	}
	
	/*Métodos del constructor*/
	
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	
	public String getNombreProducto() {
		return nombreProducto;
	}
	
	public void setMarcaProducto(String marcaProducto) {
		this.marcaProducto = marcaProducto;
	}
	
	public String getMarcaProducto() {
		return marcaProducto;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public double getCantidad() {
		return cantidad;
	}
	
	public String toString() {
		return nombreProducto+", "+marcaProducto+", "+precio+", "+cantidad;
	}
	
}//end class
