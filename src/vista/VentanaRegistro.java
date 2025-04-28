package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import controlador.Main;
import excepciones.RegistroExcepcion;
import modelo.Pasajero;

public class VentanaRegistro extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldDNI;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldEmail;
    private JPasswordField passwordField;
    private JButton btnRegistro, btnSalir;
    private JDateChooser dateChooserFechaNac;

    public VentanaRegistro(VentanaLoggin ventanaLoggin, boolean b) {
        super(ventanaLoggin);
        this.setModal(b);

        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblDNI = new JLabel("DNI");
        lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDNI.setBounds(10, 32, 71, 16);
        contentPanel.add(lblDNI);

        textFieldDNI = new JTextField();
        textFieldDNI.setBounds(91, 31, 287, 19);
        contentPanel.add(textFieldDNI);
        textFieldDNI.setColumns(10);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNombre.setBounds(10, 63, 71, 16);
        contentPanel.add(lblNombre);

        textFieldNombre = new JTextField();
        textFieldNombre.setColumns(10);
        textFieldNombre.setBounds(91, 60, 287, 19);
        contentPanel.add(textFieldNombre);

        JLabel lblApellido = new JLabel("Apellido");
        lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblApellido.setBounds(10, 92, 71, 16);
        contentPanel.add(lblApellido);

        textFieldApellido = new JTextField();
        textFieldApellido.setColumns(10);
        textFieldApellido.setBounds(91, 89, 287, 19);
        contentPanel.add(textFieldApellido);

        JLabel lblFechaNac = new JLabel("Fecha Nac");
        lblFechaNac.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFechaNac.setBounds(10, 121, 71, 16);
        contentPanel.add(lblFechaNac);

        dateChooserFechaNac = new JDateChooser();
        dateChooserFechaNac.setBounds(91, 118, 287, 19);
        contentPanel.add(dateChooserFechaNac);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEmail.setBounds(10, 147, 71, 16);
        contentPanel.add(lblEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setColumns(10);
        textFieldEmail.setBounds(91, 147, 287, 19);
        contentPanel.add(textFieldEmail);

        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblContrasena.setBounds(10, 175, 71, 16);
        contentPanel.add(lblContrasena);

        passwordField = new JPasswordField();
        passwordField.setBounds(91, 176, 287, 19);
        contentPanel.add(passwordField);

        btnRegistro = new JButton("Registrarse");
        btnRegistro.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnRegistro.setBounds(170, 206, 108, 33);
        contentPanel.add(btnRegistro);
        btnRegistro.addActionListener(this);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(355, 237, 71, 16);
        contentPanel.add(btnSalir);
        btnSalir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnSalir)) {
            salir();
        } else if (e.getSource().equals(btnRegistro)) {
            registro();
        }
    }

    public void registro() {
        Pasajero paj = new Pasajero();
        paj.setDni(textFieldDNI.getText());
        paj.setNombre(textFieldNombre.getText());
        paj.setApellido(textFieldApellido.getText());
        
        if (dateChooserFechaNac.getDate() != null) {
            LocalDate fechaNacimiento = dateChooserFechaNac.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            paj.setFecha_nac(Date.valueOf(fechaNacimiento));
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha de nacimiento", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        paj.setEmail(textFieldEmail.getText());
        paj.setContrasena(new String(passwordField.getPassword()));
        
        try {
            Main.registro(paj);
            JOptionPane.showMessageDialog(this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RegistroExcepcion e) {
            e.printStackTrace();
        }
    }

    public void salir() {
        dispose();
    }
}
