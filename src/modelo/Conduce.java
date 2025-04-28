package modelo;

import java.sql.Date;

public class Conduce {

	private String ID;
	private String DNI;
	private Date fecha_conduce;
	
		
	public Date getFecha_conduce() {
		return fecha_conduce;
	}
	public void setFecha_conduce(Date fecha_conduce) {
		this.fecha_conduce = fecha_conduce;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	
	@Override
	public String toString() {
		return "Conduce [ID=" + ID + ", DNI=" + DNI + ", fecha_conduce=" + fecha_conduce + "]";
	}

}
