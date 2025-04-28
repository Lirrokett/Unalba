package modelo;

import java.sql.Date;

public class Maquinista {
	private String DNI;
	private String nombre;
	private String apellido;
	private String contrasena;
	private Date fecha_nac;
	/*Cambiarlo en el enunciado y base de datos*/
	private boolean es_admin;
	
	
	public Date getFecha_nac() {
		return fecha_nac;
	}
	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public boolean isEs_admin() {
		return es_admin;
	}
	public void setEs_admin(boolean es_admin) {
		this.es_admin = es_admin;
	}
	
	@Override
	public String toString() {
		return "Maquinista [DNI=" + DNI + ", nombre=" + nombre + ", apellido=" + apellido + ", contrasena="
				+ contrasena + ", es_admin=" + es_admin + "]";
	}
	
}
