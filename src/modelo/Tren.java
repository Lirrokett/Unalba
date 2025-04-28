package modelo;

public class Tren {
	private String ID;
	private int plazas;
	//private int plazas_ocupadas;
	private float precio;
	private String nombre;
	private String origen;
	private String destino;
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getPlazas() {
		return plazas;
	}
	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	@Override
	public String toString() {
		return "Tren [ID=" + ID + ", plazas=" + plazas + ", precio=" + precio + ", nombre=" + nombre + ", origen="
				+ origen + ", destino=" + destino + "]";
	}
	
}
