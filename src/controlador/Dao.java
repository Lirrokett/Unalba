package controlador;

import excepciones.LogginException;
import excepciones.RegistroExcepcion;
import modelo.Conduce;
import modelo.Maquinista;
import modelo.Pasajero;
import modelo.Tren;

public interface Dao {

	public String loggin(String DNI, String contrasena) throws LogginException;

	public void registro(Pasajero paj) throws RegistroExcepcion;

	public void cambiarContra(Pasajero paj) throws LogginException;

	public void comprobarDNI(Pasajero paj) throws LogginException;

	public void altaMaquinista(Maquinista maq) throws LogginException;

	public Object[][] mostrarDatosMaquinista(Maquinista maq) throws LogginException;

	public void modificarMaquinista(Maquinista maq) throws LogginException;

	public void eliminarMaquinista(Maquinista maq) throws LogginException;

	public Object[][] mostrarDatosTren(Tren tren) throws LogginException;

	public void altaTren(Tren tren) throws LogginException;

	public void bajaTren(Tren tren) throws LogginException;

	public void modificarTren(Tren tren) throws LogginException;

	public void asignarMaquinista(Conduce cond) throws LogginException;
}
