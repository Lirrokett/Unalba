package controlador;

import excepciones.LogginException;
import excepciones.RegistroExcepcion;
import modelo.Conduce;
import modelo.Maquinista;
import modelo.Pasajero;
import modelo.Tren;
import vista.VentanaLoggin;

public class Main {
	private static Dao dao = new DaoImplementacion();

	public static void main(String[] args) {
		VentanaLoggin ventanaLoggin = new VentanaLoggin();
		
		ventanaLoggin.setVisible(true);
	}
	
	public static String loggin(String DNI, String contrasena) throws LogginException{
		return dao.loggin(DNI, contrasena);
	}
	
	public static void registro(Pasajero paj) throws RegistroExcepcion{
		dao.registro(paj);
	}

	public static void cambiarContra(Pasajero paj) throws LogginException{
		dao.cambiarContra(paj);
	}

	public static void comprobarDNI(Pasajero paj) throws LogginException{
		dao.comprobarDNI(paj);
	}

	public static void altaMaquinista(Maquinista maq)  throws LogginException{
		dao.altaMaquinista(maq);
	}
	
	public static Object[][] mostrarDatosMaquinista(Maquinista maq) throws LogginException{
		return dao.mostrarDatosMaquinista(maq);
	}

	public static void modificarMaquinista(Maquinista maq) throws LogginException{
		dao.modificarMaquinista(maq);
	}
	
	public static void eliminarMaquinista(Maquinista maq) throws LogginException{
		dao.eliminarMaquinista(maq);
	}

	public static Object[][] mostrarDatosTren(Tren tren) throws LogginException{
		return dao.mostrarDatosTren(tren);
	}

	public static void altaTren(Tren tren) throws LogginException{
		dao.altaTren(tren);
	}

	public static void bajaTren(Tren tren) throws LogginException{
		dao.bajaTren(tren);
	}

	public static void modificarTren(Tren tren) throws LogginException{
		dao.modificarTren(tren);
	}

	public static void asignarMaquinista(Conduce cond) throws LogginException{
		dao.asignarMaquinista(cond);
	}

}
