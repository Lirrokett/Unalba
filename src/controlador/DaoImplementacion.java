package controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import excepciones.LogginException;
import excepciones.RegistroExcepcion;
import modelo.Conduce;
import modelo.Maquinista;
import modelo.Pasajero;
import modelo.Tren;

public class DaoImplementacion implements Dao {

	/* Tiene que haber 3 o 4 excepciones */

	private ResourceBundle configFile;
	private String urlBD;
	private String userBD;
	private String passwordBD;

	private Connection con;
	private PreparedStatement stmt;

	final String LOGGIN_PASAJERO = "SELECT DNI FROM pasajero WHERE DNI = ? AND  contrasena = ?";
	final String LOGGIN_MAQUINISTA = "SELECT DNI FROM maquinista WHERE DNI = ? AND contrasena = ?";
	final String MAQUINISTA_ES_ADMIN = "SELECT es_admin FROM MAQUINISTA WHERE es_admin = ?";
	final String REGISTRO = "INSERT INTO PASAJERO (dni, nombre, apellido, fecha_nac, email, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
	final String CAMBIAR_CONTRA_PAJ = "UPDATE PASAJERO SET contrasena = ? WHERE DNI = ?";
	final String CAMBIAR_CONTRA_MAQ = "UPDATE MAQUINISTA SET contrasena = ? where DNI = ?";
	final String ALTA_MAQUINISTA = "INSERT INTO MAQUINISTA (DNI,nombre, apellido, contrasena,  fecha_nac, es_admin) VALUES (? ,? ,? ,? ,? ,?)";

	final String MOSTRAR_DATOS_MAQUINISTA = "SELECT DNI, nombre, apellido, es_admin, contrasena, fecha_nac FROM MAQUINISTA";

	final String MODIFICAR_MAQUINISTA = "UPDATE MAQUINISTA SET nombre = ?, apellido = ?, contrasena = ?, fecha_nac = ?, es_admin = ? WHERE DNI = ?";
	final String BAJA_MAQUINISTA = "DELETE FROM maquinista WHERE dni = ?";

	final String MOSTRAR_DATOS_TREN = "SELECT ID, nombre, plazas, precio, origen, destino FROM TREN";

	final String ALTA_TREN = "INSERT INTO TREN (ID, nombre, plazas, plazas_ocupadas, precio, origen, destino) VALUES (?, ?, ?, ?, ?, ?, ?)";
	final String BAJA_TREN = "DELETE FROM tren WHERE ID = ?";
	final String MODIFICAR_TREN = "UPDATE TREN SET ID = ?, nombre = ?, plazas = ?, plazas_ocupadas = ?, precio = ?, origen = ?, destino = ? WHERE ID = ?";

	final String ASIGNAR_MAQUINISTA = "INSERT INTO CONDUCE (DNI, ID, FECHA) VALUES (?, ?, ?)";

	public DaoImplementacion() {
		this.configFile = ResourceBundle.getBundle("modelo.configClase");
		this.urlBD = this.configFile.getString("Conn");
		this.userBD = this.configFile.getString("DBUser");
		this.passwordBD = this.configFile.getString("DBPass");
	}

	private void openConnection() throws ClassNotFoundException {
		try {

			/*
			 * con = DriverManager.getConnection(
			 * "jdbc:mysql://localhost:3306/UNALBA_SL?serverTimezone=Europe/Madrid&useSSL=false",
			 * "root", "abcd*1234");
			 */
			con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
		} catch (SQLException e) {
			System.out.println("Error al intentar abrir la BD");
		}
	}

	private void closeConnection() throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
		if (con != null)
			con.close();
	}

	// cambiar de string a un objeto

	@Override
	public String loggin(String DNI, String contrasena) throws LogginException {
		ResultSet rs = null;
		String tipoUsuario = "desconocido";

		try {
			openConnection();

			stmt = con.prepareStatement(LOGGIN_MAQUINISTA);
			stmt.setString(1, DNI);
			stmt.setString(2, contrasena);
			rs = stmt.executeQuery();

			if (rs.next()) {
				tipoUsuario = "maquinista";
			} else {
				stmt = con.prepareStatement(LOGGIN_PASAJERO);
				stmt.setString(1, DNI);
				stmt.setString(2, contrasena);
				rs = stmt.executeQuery();

				if (rs.next()) {
					tipoUsuario = "pasajero";
				}
			}

			if (tipoUsuario.equals("desconocido")) {
				throw new LogginException("Error: Usuario o contraseña incorrectos.");
			}

			return tipoUsuario;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new LogginException("Error al intentar iniciar sesión.");
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void registro(Pasajero paj) throws RegistroExcepcion {

		try {
			openConnection();

			stmt = con.prepareStatement(REGISTRO);

			stmt.setString(1, paj.getDni());
			stmt.setString(2, paj.getNombre());
			stmt.setString(3, paj.getApellido());
			stmt.setDate(4, (Date) paj.getFecha_nac());
			stmt.setString(5, paj.getEmail());
			stmt.setString(6, paj.getContrasena());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RegistroExcepcion("Error de SQL al hacer el registro");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void cambiarContra(Pasajero paj) throws LogginException {
		try {
			openConnection();
			String tipoUsuario = "desconocido";
			int filasAfectadas = 0;

			stmt = con.prepareStatement(CAMBIAR_CONTRA_MAQ);
			stmt.setString(1, paj.getContrasena());
			stmt.setString(2, paj.getDni());
			filasAfectadas = stmt.executeUpdate();

			if (filasAfectadas > 0) {
				tipoUsuario = "maquinista";
			} else {
				stmt = con.prepareStatement(CAMBIAR_CONTRA_PAJ);
				stmt.setString(1, paj.getContrasena());
				stmt.setString(2, paj.getDni());
				filasAfectadas = stmt.executeUpdate();

				if (filasAfectadas > 0) {
					tipoUsuario = "pasajero";
				}
			}

			if (filasAfectadas == 0) {
				throw new LogginException("Error: No se encontró el usuario con el DNI proporcionado.");
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new LogginException("Error al intentar cambiar la contraseña.");
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* Ya se mirara */
	@Override
	public void comprobarDNI(Pasajero paj) throws LogginException {

	}

	@Override
	public void altaMaquinista(Maquinista maq) throws LogginException {
		try {
			openConnection();

			stmt = con.prepareStatement(ALTA_MAQUINISTA);

			stmt.setString(1, maq.getDNI());
			stmt.setString(2, maq.getNombre());
			stmt.setString(3, maq.getApellido());
			stmt.setString(4, maq.getContrasena());
			stmt.setDate(5, maq.getFecha_nac());
			stmt.setBoolean(6, maq.isEs_admin());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LogginException("Error de SQL al insertar el maquinista");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Object[][] mostrarDatosMaquinista(Maquinista maq) throws LogginException {
		List<Object[]> listaMaquinistas = new ArrayList<>();
		ResultSet rs = null;

		try {
			openConnection();
			stmt = con.prepareStatement(MOSTRAR_DATOS_MAQUINISTA);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Object[] fila = { rs.getString("DNI"), rs.getString("nombre"), rs.getString("apellido"),
						rs.getBoolean("es_admin"), rs.getString("contrasena"), rs.getDate("fecha_nac") };
				listaMaquinistas.add(fila);
			}

			return listaMaquinistas.toArray(new Object[0][0]);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new LogginException("Error al obtener datos de maquinistas.");
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void modificarMaquinista(Maquinista maq) throws LogginException {
		try {
			openConnection();

			stmt = con.prepareStatement(MODIFICAR_MAQUINISTA);

			stmt.setString(1, maq.getNombre());
			stmt.setString(2, maq.getApellido());
			stmt.setString(3, maq.getContrasena());
			stmt.setDate(4, maq.getFecha_nac());
			stmt.setBoolean(5, maq.isEs_admin());
			stmt.setString(6, maq.getDNI());

			int filasAfectadas = stmt.executeUpdate();

			if (filasAfectadas == 0) {
				throw new LogginException("Error: No se ha encontrado el maquinista con el DNI proporcionado.");
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new LogginException("Error al intentar modificar los datos del maquinista.");
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void eliminarMaquinista(Maquinista maq) throws LogginException {
		try {
			openConnection();

			stmt = con.prepareStatement(BAJA_MAQUINISTA);
			stmt.setString(1, maq.getDNI());

			int filasAfectadas = stmt.executeUpdate();

			if (filasAfectadas == 0) {
				throw new LogginException("Error: No se encontró un maquinista con el DNI proporcionado.");
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new LogginException("Error al intentar eliminar el maquinista.");
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Object[][] mostrarDatosTren(Tren tren) throws LogginException {
		List<Object[]> listaTrenes = new ArrayList<>();
		ResultSet rs = null;

		try {
			openConnection();
			stmt = con.prepareStatement(MOSTRAR_DATOS_TREN);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Object[] fila = { rs.getString("ID"), rs.getString("nombre"), rs.getInt("plazas"),
						rs.getDouble("precio"), rs.getString("origen"), rs.getString("destino") };
				listaTrenes.add(fila);
			}

			return listaTrenes.toArray(new Object[0][0]);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new LogginException("Error al obtener datos de trenes.");
		} finally {
			try {
				if (rs != null)
					rs.close();
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void altaTren(Tren tren) throws LogginException {
		try {
			openConnection();

			stmt = con.prepareStatement(ALTA_TREN);

			stmt.setString(1, tren.getID());
			stmt.setString(2, tren.getNombre());
			stmt.setInt(3, tren.getPlazas());
			stmt.setDouble(4, tren.getPrecio());
			stmt.setString(5, tren.getOrigen());
			stmt.setString(6, tren.getDestino());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LogginException("Error de SQL al dar de alta el tren.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void bajaTren(Tren tren) throws LogginException {
		try {
			openConnection();

			stmt = con.prepareStatement(BAJA_TREN);
			stmt.setString(1, tren.getID());

			int filasAfectadas = stmt.executeUpdate();

			if (filasAfectadas == 0) {
				throw new LogginException("Error: No se encontró un tren con el ID proporcionado.");
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new LogginException("Error al intentar eliminar el tren.");
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void modificarTren(Tren tren) throws LogginException {
		try {
			openConnection();

			stmt = con.prepareStatement(MODIFICAR_TREN);

			stmt.setString(1, tren.getID());
			stmt.setString(2, tren.getNombre());
			stmt.setInt(3, tren.getPlazas());
			stmt.setDouble(4, tren.getPrecio());
			stmt.setString(5, tren.getOrigen());
			stmt.setString(6, tren.getDestino());
			stmt.setString(7, tren.getID());

			int filasAfectadas = stmt.executeUpdate();

			if (filasAfectadas == 0) {
				throw new LogginException("Error: No se encontró un tren con el ID proporcionado.");
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new LogginException("Error al intentar modificar los datos del tren.");
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void asignarMaquinista(Conduce cond) throws LogginException {

		try {
			openConnection();

			stmt = con.prepareStatement(ASIGNAR_MAQUINISTA);
			
			stmt.setString(1, cond.getDNI());
			stmt.setString(2, cond.getID());
			stmt.setDate(3, cond.getFecha_conduce());

			int filasAfectadas = stmt.executeUpdate();

			if (filasAfectadas == 0) {
				throw new LogginException("Error: No se pudo asignar el maquinista al tren.");
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new LogginException("Error al intentar asignar el maquinista al tren.");
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
