package modelo;

import java.util.Date;

public class Viaja {

	private String ID;
	private String dni;
	private Date fecha;
	private int plazasOcupadas;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getPlazasOcupadas() {
		return plazasOcupadas;
	}
	public void setPlazasOcupadas(int plazasOcupadas) {
		this.plazasOcupadas = plazasOcupadas;
	}
	
	@Override
	public String toString() {
		return "Viaja [ID=" + ID + ", dni=" + dni + ", fecha=" + fecha + "]";
	}
	
}
