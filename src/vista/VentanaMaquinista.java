package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controlador.Main;
import excepciones.LogginException;
import excepciones.RegistroExcepcion;
import modelo.Conduce;
import modelo.Maquinista;
import modelo.Tren;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class VentanaMaquinista extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static VentanaMaquinista instancia = null; // Campo estático para controlar la instancia única
	private JTabbedPane tabbedPane;
	private JPanel panelCRUDMaquinistas, panelCRUDTrenes, panelAsignacion;
	private JButton btnAltaMaquinista, btnBajaMaquinista, btnModificarMaquinista;
	private JButton btnAltaTren, btnBajaTren, btnModificarTren, btnCargar, btnLimpiar, btnCargarTren, btnLimpiarTren;
	private JButton btnAsignarMaquinista, btnElegirMaquinista;
	private JTextField txtDni, txtNombre, txtApellido, txtContrasena;
	private JDateChooser dateChooserFechaNac, dateChooserFechaConduc;
	private boolean comprobante = false;
	private JCheckBox chckbxEsAdmin;
	private JTextField txtID, txtNombreTren, txtOrigen, txtDestino, txtPlazas, txtPrecio;
	private JTextField txtNombreAsignar;
	private JTextField txtTrenAsignar;
	private JButton btnElegirTren;
	private boolean estaMaquinista = false;
	private boolean estaTren = false;
	private JLabel lblDatosDelTren;
	private JLabel lblFechaDelViaje;

	public VentanaMaquinista(VentanaLoggin ventanaLoggin, boolean modal) {
		setTitle("Gestión de Maquinistas y Trenes");
		setBounds(100, 100, 600, 499);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(modal);

		tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// Panel CRUD Maquinistas
		panelCRUDMaquinistas = new JPanel();
		panelCRUDMaquinistas.setLayout(null);
		tabbedPane.addTab("CRUD Maquinistas", panelCRUDMaquinistas);

		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDni.setBounds(50, 30, 100, 30);
		panelCRUDMaquinistas.add(lblDni);

		txtDni = new JTextField(10);
		txtDni.setBounds(200, 30, 150, 30);
		panelCRUDMaquinistas.add(txtDni);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(50, 70, 100, 30);
		panelCRUDMaquinistas.add(lblNombre);

		txtNombre = new JTextField(10);
		txtNombre.setBounds(200, 70, 150, 30);
		panelCRUDMaquinistas.add(txtNombre);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApellido.setBounds(50, 110, 100, 30);
		panelCRUDMaquinistas.add(lblApellido);

		txtApellido = new JTextField(10);
		txtApellido.setBounds(200, 110, 150, 30);
		panelCRUDMaquinistas.add(txtApellido);

		JLabel lblFechaNac = new JLabel("Fecha de Nacimiento:");
		lblFechaNac.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFechaNac.setBounds(50, 150, 150, 30);
		panelCRUDMaquinistas.add(lblFechaNac);

		// JDateChooser para la fecha de nacimiento con validación
		dateChooserFechaNac = new JDateChooser();
		dateChooserFechaNac.setBounds(200, 150, 150, 30);
		dateChooserFechaNac.setDateFormatString("dd/MM/yyyy");
		panelCRUDMaquinistas.add(dateChooserFechaNac);

		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContrasena.setBounds(50, 190, 100, 30);
		panelCRUDMaquinistas.add(lblContrasena);

		txtContrasena = new JTextField(10);
		txtContrasena.setBounds(200, 190, 150, 30);
		panelCRUDMaquinistas.add(txtContrasena);

		// Botones CRUD Maquinista
		btnAltaMaquinista = new JButton("Alta Maquinista");
		btnAltaMaquinista.setBounds(50, 280, 140, 30);
		panelCRUDMaquinistas.add(btnAltaMaquinista);
		btnAltaMaquinista.addActionListener(this);

		btnBajaMaquinista = new JButton("Baja Maquinista");
		btnBajaMaquinista.setBounds(200, 280, 140, 30);
		panelCRUDMaquinistas.add(btnBajaMaquinista);
		btnBajaMaquinista.addActionListener(this);

		btnModificarMaquinista = new JButton("Modificar Maquinista");
		btnModificarMaquinista.setBounds(350, 280, 180, 30);
		panelCRUDMaquinistas.add(btnModificarMaquinista);
		btnModificarMaquinista.addActionListener(this);

		JLabel lblEsAdmin = new JLabel("Es admin");
		lblEsAdmin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEsAdmin.setBounds(50, 230, 100, 30);
		panelCRUDMaquinistas.add(lblEsAdmin);

		chckbxEsAdmin = new JCheckBox("");
		chckbxEsAdmin.setBounds(200, 237, 21, 21);
		panelCRUDMaquinistas.add(chckbxEsAdmin);

		btnCargar = new JButton("Cargar");
		btnCargar.setBounds(432, 37, 85, 21);
		panelCRUDMaquinistas.add(btnCargar);
		btnCargar.addActionListener(this);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(432, 77, 85, 21);
		panelCRUDMaquinistas.add(btnLimpiar);
		btnLimpiar.addActionListener(this);

		// Panel CRUD Trenes
		panelCRUDTrenes = new JPanel();
		panelCRUDTrenes.setLayout(null);
		tabbedPane.addTab("CRUD Trenes", panelCRUDTrenes);

		// Etiquetas y campos de texto para el CRUD de trenes

		JLabel lblID = new JLabel("ID del Tren:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID.setBounds(21, 50, 120, 30);
		panelCRUDTrenes.add(lblID);

		txtID = new JTextField(10);
		txtID.setBounds(150, 50, 150, 30);
		panelCRUDTrenes.add(txtID);

		JLabel lblNombreTren = new JLabel("Nombre:");
		lblNombreTren.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreTren.setBounds(21, 100, 120, 30);
		panelCRUDTrenes.add(lblNombreTren);

		txtNombreTren = new JTextField(10);
		txtNombreTren.setBounds(150, 100, 150, 30);
		panelCRUDTrenes.add(txtNombreTren);

		JLabel lblOrigen = new JLabel("Origen:");
		lblOrigen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOrigen.setBounds(21, 150, 120, 30);
		panelCRUDTrenes.add(lblOrigen);

		txtOrigen = new JTextField(10);
		txtOrigen.setBounds(150, 150, 150, 30);
		panelCRUDTrenes.add(txtOrigen);

		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDestino.setBounds(21, 200, 120, 30);
		panelCRUDTrenes.add(lblDestino);

		txtDestino = new JTextField(10);
		txtDestino.setBounds(150, 200, 150, 30);
		panelCRUDTrenes.add(txtDestino);

		JLabel lblPlazas = new JLabel("Plazas:");
		lblPlazas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlazas.setBounds(21, 250, 120, 30);
		panelCRUDTrenes.add(lblPlazas);

		txtPlazas = new JTextField(10);
		txtPlazas.setBounds(150, 250, 150, 30);
		panelCRUDTrenes.add(txtPlazas);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecio.setBounds(21, 291, 120, 30);
		panelCRUDTrenes.add(lblPrecio);

		txtPrecio = new JTextField(10);
		txtPrecio.setBounds(150, 293, 150, 30);
		panelCRUDTrenes.add(txtPrecio);

		// Botones CRUD Tren
		btnAltaTren = new JButton("Alta Tren");
		btnAltaTren.setBounds(49, 362, 120, 30); // Ajusta las posiciones y tamaños
		panelCRUDTrenes.add(btnAltaTren);
		btnAltaTren.addActionListener(this);

		btnBajaTren = new JButton("Baja Tren");
		btnBajaTren.setBounds(193, 362, 120, 30); // Ajusta las posiciones y tamaños
		panelCRUDTrenes.add(btnBajaTren);
		btnBajaTren.addActionListener(this);

		btnModificarTren = new JButton("Modificar Tren");
		btnModificarTren.setBounds(334, 362, 140, 30); // Ajusta las posiciones y tamaños
		panelCRUDTrenes.add(btnModificarTren);
		btnModificarTren.addActionListener(this);

		btnCargarTren = new JButton("Cargar");
		btnCargarTren.setBounds(425, 57, 85, 21);
		panelCRUDTrenes.add(btnCargarTren);
		btnCargarTren.addActionListener(this);

		btnLimpiarTren = new JButton("Limpiar");
		btnLimpiarTren.setBounds(425, 107, 85, 21);
		panelCRUDTrenes.add(btnLimpiarTren);
		btnLimpiarTren.addActionListener(this);

		// Panel Asignar Maquinista
		panelAsignacion = new JPanel();
		panelAsignacion.setLayout(null);
		tabbedPane.addTab("Asignar Maquinista", panelAsignacion);

		// Botón Asignar Maquinista
		btnAsignarMaquinista = new JButton("Asignar Maquinista");
		btnAsignarMaquinista.setBounds(203, 329, 147, 30);
		panelAsignacion.add(btnAsignarMaquinista);
		btnAsignarMaquinista.addActionListener(this);

		txtNombreAsignar = new JTextField();
		txtNombreAsignar.setBounds(84, 61, 328, 19);
		panelAsignacion.add(txtNombreAsignar);
		txtNombreAsignar.setColumns(10);

		txtTrenAsignar = new JTextField();
		txtTrenAsignar.setColumns(10);
		txtTrenAsignar.setBounds(84, 145, 328, 19);
		panelAsignacion.add(txtTrenAsignar);

		dateChooserFechaConduc = new JDateChooser();
		dateChooserFechaConduc.setBounds(84, 219, 328, 30);
		dateChooserFechaConduc.setDateFormatString("dd/MM/yyyy");
		dateChooserFechaConduc.setMinSelectableDate(new Date());
		panelAsignacion.add(dateChooserFechaConduc);

		btnElegirMaquinista = new JButton("Elegir Maquinista");
		btnElegirMaquinista.setBounds(433, 60, 138, 21);
		panelAsignacion.add(btnElegirMaquinista);
		btnElegirMaquinista.addActionListener(this);

		btnElegirTren = new JButton("Elegir Tren");
		btnElegirTren.setBounds(433, 144, 138, 21);
		panelAsignacion.add(btnElegirTren);

		JLabel lblNewLabel = new JLabel("Datos Del Maquinista");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(200, 24, 141, 20);
		panelAsignacion.add(lblNewLabel);

		lblDatosDelTren = new JLabel("Datos Del Tren");
		lblDatosDelTren.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDatosDelTren.setBounds(223, 115, 106, 20);
		panelAsignacion.add(lblDatosDelTren);

		lblFechaDelViaje = new JLabel("Fecha Del Viaje");
		lblFechaDelViaje.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFechaDelViaje.setBounds(223, 189, 106, 20);
		panelAsignacion.add(lblFechaDelViaje);
		btnElegirTren.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAltaMaquinista)) {
			validarFechaNacimiento();

			if (comprobante) {
				altaMaquinista();
			}
		} else if (e.getSource().equals(btnBajaMaquinista)) {
			bajaMaquinista();
		} else if (e.getSource().equals(btnModificarMaquinista)) {
			modificarMaquinista();
		} else if (e.getSource().equals(btnAltaTren)) {
			altaTren();
		} else if (e.getSource().equals(btnBajaTren)) {
			bajaTren();
		} else if (e.getSource().equals(btnModificarTren)) {
			modificarTren();
		} else if (e.getSource().equals(btnAsignarMaquinista)) {
			asignarMaquinista();
			
			limpiarAsignar();
		} else if (e.getSource().equals(btnCargar)) {
			this.dispose();
			cargar();
		} else if (e.getSource().equals(btnLimpiar)) {
			limpiar();
		} else if (e.getSource().equals(btnCargarTren)) {
			this.dispose();
			cargarTren();

			tabbedPane.setSelectedIndex(1);
		} else if (e.getSource().equals(btnLimpiarTren)) {
			limpiarTren();
		} else if (e.getSource().equals(btnElegirTren)) {
			this.dispose();
			estaTren = true;
			cargarTren();

			tabbedPane.setSelectedIndex(1);
		} else if (e.getSource().equals(btnElegirMaquinista)) {
			this.dispose();
			estaMaquinista = true;
			cargar();

			tabbedPane.setSelectedIndex(1);
		}
	}

	private void cargarTren() {
		VentanaMostrarTren venTren = new VentanaMostrarTren(this, true, estaTren);
		venTren.setVisible(true);

		tabbedPane.setSelectedComponent(panelCRUDTrenes);
	}

	private void limpiarTren() {
		txtID.setText("");
		txtNombreTren.setText("");
		txtOrigen.setText("");
		txtDestino.setText("");
		txtPlazas.setText("");
		txtPrecio.setText("");
	}

	private void limpiar() {
		txtDni.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtContrasena.setText("");
		chckbxEsAdmin.setSelected(false);
		dateChooserFechaNac.setDate(null);
	}

	private void limpiarAsignar() {
		txtNombreAsignar.setText("");
		txtTrenAsignar.setText("");
		dateChooserFechaConduc.setDate(null);
	}

	private void cargar() {
		VentanaMostrarMaquinistas venMaq = new VentanaMostrarMaquinistas(this, true, estaMaquinista);
		venMaq.setVisible(true);
	}

	private void validarFechaNacimiento() {
		Date fechaSeleccionada = dateChooserFechaNac.getDate();

		if (fechaSeleccionada != null) {
			LocalDate fechaNacimiento = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fechaActual = LocalDate.now();

			int edad = Period.between(fechaNacimiento, fechaActual).getYears();

			if (fechaNacimiento.isAfter(fechaActual)) {
				JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser en el futuro.", "Error",
						JOptionPane.ERROR_MESSAGE);
				dateChooserFechaNac.setDate(null);
				comprobante = false;
			} else if (edad < 18) {
				JOptionPane.showMessageDialog(this, "Debes ser mayor de 18 años para registrarte.", "Error",
						JOptionPane.ERROR_MESSAGE);
				dateChooserFechaNac.setDate(null);
				comprobante = false;
			} else {
				comprobante = true;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de nacimiento.", "Error",
					JOptionPane.ERROR_MESSAGE);
			comprobante = false;
		}
	}

	private void altaMaquinista() {
		Maquinista maq = new Maquinista();

		maq.setDNI(txtDni.getText());
		maq.setNombre(txtNombre.getText());
		maq.setApellido(txtApellido.getText());
		maq.setContrasena(txtContrasena.getText());
		maq.setFecha_nac(new java.sql.Date(dateChooserFechaNac.getDate().getTime()));
		maq.setEs_admin(chckbxEsAdmin.isSelected());

		try {
			Main.altaMaquinista(maq);
			JOptionPane.showMessageDialog(this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		} catch (LogginException e) {
			e.printStackTrace();
		}
	}

	private void bajaMaquinista() {
		Maquinista maq = new Maquinista();

		maq.setDNI(txtDni.getText());

		try {
			Main.eliminarMaquinista(maq);
			JOptionPane.showMessageDialog(this, "Maquinista eliminado exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			limpiar();
		} catch (LogginException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al eliminar el maquinista", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modificarMaquinista() {
		Maquinista maq = new Maquinista();

		maq.setDNI(txtDni.getText());
		maq.setNombre(txtNombre.getText());
		maq.setApellido(txtApellido.getText());
		maq.setContrasena(txtContrasena.getText());
		maq.setFecha_nac(new java.sql.Date(dateChooserFechaNac.getDate().getTime()));
		maq.setEs_admin(chckbxEsAdmin.isSelected());

		try {
			Main.modificarMaquinista(maq);
			JOptionPane.showMessageDialog(this, "Maquinista modificado exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			limpiar();
		} catch (LogginException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al modificar el maquinista", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void altaTren() {
		Tren tren = new Tren();

		tren.setID(txtID.getText());
		tren.setNombre(txtNombreTren.getText());
		tren.setPlazas(Integer.parseInt(txtPlazas.getText()));
		tren.setPrecio(Float.parseFloat(txtPrecio.getText()));
		tren.setOrigen(txtOrigen.getText());
		tren.setDestino(txtDestino.getText());

		try {
			Main.altaTren(tren);
			JOptionPane.showMessageDialog(this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			limpiarTren();
		} catch (LogginException e) {
			e.printStackTrace();
		}
	}

	private void bajaTren() {
		Tren tren = new Tren();

		tren.setID(txtID.getText());

		try {
			Main.bajaTren(tren);
			JOptionPane.showMessageDialog(this, "Tren eliminado exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			limpiarTren();
		} catch (LogginException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al eliminar el maquinista", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modificarTren() {
		Tren tren = new Tren();

		tren.setDestino(txtID.getText());
		tren.setID(txtID.getText());
		tren.setNombre(txtNombreTren.getText());
		tren.setPlazas(Integer.parseInt(txtPlazas.getText()));
		tren.setPrecio(Float.parseFloat(txtPrecio.getText()));
		tren.setOrigen(txtOrigen.getText());
		tren.setDestino(txtDestino.getText());

		try {
			Main.modificarTren(tren);
			JOptionPane.showMessageDialog(this, "Tren modificado exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			limpiarTren();
		} catch (LogginException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al modificar el maquinista", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void asignarMaquinista() {
		// Texto de ejemplo en txtNombreAsignar: "DNI: 12345678A Nombre: Juan Apellido:
		// Pérez"
		String textoMaquinista = txtNombreAsignar.getText().trim();
		// Separamos en la primera aparición de los caracteres dobles de espacio
		String parteDNI = textoMaquinista.split("\\s{2,}")[0]; // "DNI: 12345678A"
		String dniMaquinista = parteDNI.split(":")[1].trim(); // "12345678A"

		// Texto de ejemplo en txtTrenAsignar: "ID: TREN01 Nombre: Expreso Origen: X
		// Destino: Y"
		String textoTren = txtTrenAsignar.getText().trim();
		String parteID = textoTren.split("\\s{2,}")[0]; // "ID: TREN01"
		String idTren = parteID.split(":")[1].trim(); // "TREN01"

		Date fechaSeleccionada = dateChooserFechaConduc.getDate();
		if (fechaSeleccionada == null) {
			JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha válida para la asignación.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Conversión a java.sql.Date
		java.sql.Date fechaConduccion = new java.sql.Date(fechaSeleccionada.getTime());

		Conduce cond = new Conduce();
		cond.setDNI(dniMaquinista);
		cond.setID(idTren);
		cond.setFecha_conduce(fechaConduccion);

		try {
			Main.asignarMaquinista(cond);
			JOptionPane.showMessageDialog(this,
					"Maquinista " + dniMaquinista + " asignado correctamente al tren " + idTren, "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al asignar maquinista: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void salir() {
		dispose();
	}

	public void cargarDatosCRUD(String dni, String nombre, String apellido, boolean esAdmin, String contrasena,
			String fechaNac) {
		txtDni.setText(dni);
		txtNombre.setText(nombre);
		txtApellido.setText(apellido);
		chckbxEsAdmin.setSelected(esAdmin);
		txtContrasena.setText(contrasena);

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(fechaNac);
			dateChooserFechaNac.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar la fecha de nacimiento", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void cargarDatosTren(String id, String nombre, int plazas, float precio, String origen, String destino) {

		txtID.setText(id);
		txtNombreTren.setText(nombre);
		txtPlazas.setText(String.valueOf(plazas));
		txtPrecio.setText(String.valueOf(precio));
		txtOrigen.setText(origen);
		txtDestino.setText(destino);
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void cargarDatosAsignarMaquinista(String dni, String nombre, String apellido) {
		txtNombreAsignar.setText("DNI: " + dni + "    Nombre: " + nombre + "    Apellido: " + apellido);
	}

	public void cargarDatosAsignarTren(String id, String nombre, String origen, String destino) {
		txtTrenAsignar
				.setText("ID: " + id + "    Nombre: " + nombre + "    Origen: " + origen + "    Destino: " + destino);
	}

}