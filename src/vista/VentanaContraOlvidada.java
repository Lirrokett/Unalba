package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Main;
import excepciones.LogginException;
import modelo.Pasajero;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class VentanaContraOlvidada extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNuevaContra;
	private JButton btnSalir, btnCambiarContrasena;
	private JTextField textFieldDNI;

	/**
	 * Launch the application.
	 * @param b 
	 * @param ventanaLoggin 
	 * @param nombre 
	 */
	/*
	public static void main(String[] args) {
		try {
			VentanaContraOlvidada dialog = new VentanaContraOlvidada();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaContraOlvidada(VentanaLoggin ventanaLoggin, boolean b, String DNI) {
		super(ventanaLoggin);
		this.setModal(b);
		
		setBounds(100, 100, 383, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Introduce la nueva contraseña");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(82, 93, 191, 18);
		contentPanel.add(lblNewLabel);
		
		textFieldNuevaContra = new JTextField();
		textFieldNuevaContra.setBounds(82, 121, 191, 19);
		contentPanel.add(textFieldNuevaContra);
		textFieldNuevaContra.setColumns(10);
		
		btnCambiarContrasena = new JButton("Cambiar contraseña");
		btnCambiarContrasena.setBounds(55, 173, 125, 21);
		contentPanel.add(btnCambiarContrasena);
		btnCambiarContrasena.addActionListener(this);
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(209, 173, 85, 21);
		contentPanel.add(btnSalir);
		btnSalir.addActionListener(this);
		
		JLabel lblNombre = new JLabel("DNI");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(164, 21, 25, 18);
		contentPanel.add(lblNombre);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setColumns(10);
		textFieldDNI.setBounds(82, 49, 191, 19);
		contentPanel.add(textFieldDNI);
		
		textFieldDNI.setText(DNI);
	    textFieldDNI.setEditable(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(btnSalir)) {
			salir();
		} else if (e.getSource().equals(btnCambiarContrasena)) {
			cambioContrasena();
		} 
		/*Falta el comprobar que cuando entre aqui sea un dni valido.*/
	}
	
	private void salir() {
		dispose();
	}
	
	/*Añadir un mensaje de que se ha cambiado correctamente la contraseña*/
	private void cambioContrasena() {
		Pasajero paj = new Pasajero();
		
		paj.setContrasena(textFieldNuevaContra.getText());
		paj.setDni(textFieldDNI.getText());
		
		try {
			Main.cambiarContra(paj);
		} catch (LogginException e) {
			e.printStackTrace();
		}
	}
}
