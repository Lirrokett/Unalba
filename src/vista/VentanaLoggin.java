package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Main;
import excepciones.LogginException;
import modelo.Maquinista;
import modelo.Pasajero;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField; // Importa JPasswordField

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class VentanaLoggin extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldDNI;
    private JPasswordField passwordFieldContrasena; // Cambiado a JPasswordField
    private JButton btnLoggin, btnRegistrarse, btnSalir, btnContraOlvidada;

    /**
     * Launch the application.
     */
    /*
    * public static void main(String[] args) { EventQueue.invokeLater(new
    * Runnable() { public void run() { try { VentanaLoggin frame = new
    * VentanaLoggin(); frame.setVisible(true); } catch (Exception e) {
    * e.printStackTrace(); } } }); }
    * 
    * /** Create the frame.
     */

    public VentanaLoggin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        textFieldDNI = new JTextField();
        textFieldDNI.setBounds(103, 73, 203, 19);
        contentPane.add(textFieldDNI);
        textFieldDNI.setColumns(10);

        passwordFieldContrasena = new JPasswordField(); // Usamos JPasswordField
        passwordFieldContrasena.setBounds(103, 130, 203, 19); // Asigna las mismas coordenadas
        contentPane.add(passwordFieldContrasena);

        JLabel lblNewLabel = new JLabel("DNI");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(176, 45, 62, 13);
        contentPane.add(lblNewLabel);

        JLabel lblContrasea = new JLabel("Contraseña");
        lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblContrasea.setBounds(163, 107, 75, 13);
        contentPane.add(lblContrasea);

        btnLoggin = new JButton("Loggin");
        btnLoggin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLoggin.setBounds(86, 173, 104, 33);
        contentPane.add(btnLoggin);
        /* Loggin */
        btnLoggin.addActionListener(this);

        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setBackground(Color.LIGHT_GRAY);
        btnRegistrarse.setBounds(10, 232, 85, 21);
        contentPane.add(btnRegistrarse);
        /* Registrarse */
        btnRegistrarse.addActionListener(this);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(341, 232, 85, 21);
        contentPane.add(btnSalir);
        /* Salir */
        btnSalir.addActionListener(this);
        
        btnContraOlvidada = new JButton("Has olvidado tu contraseña");
        btnContraOlvidada.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnContraOlvidada.setBounds(211, 182, 155, 19);
        contentPane.add(btnContraOlvidada);
        btnContraOlvidada.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(btnSalir)) {
            salir();
        } else if (e.getSource().equals(btnLoggin)) {
            loggin();
        } else if (e.getSource().equals(btnRegistrarse)) {
            registro();
        } else if (e.getSource().equals(btnContraOlvidada)) {
            contraOlvidada();
        }
    } 

    private void salir() {
        dispose();
    }

    private void loggin() {
        String DNI = textFieldDNI.getText();
        char[] contrasena = passwordFieldContrasena.getPassword(); // Obtener la contraseña del JPasswordField

        try {

            String tipoUsuario = Main.loggin(DNI, new String(contrasena)); // Convertimos el char[] a String

            if (tipoUsuario.equals("maquinista")) {
                VentanaMaquinista ventM = new VentanaMaquinista(this, true);
                ventM.setVisible(true);
            } else if (tipoUsuario.equals("pasajero")) {
                VentanaPasajeros ventP = new VentanaPasajeros(this, true);
                ventP.setVisible(true);
            }
        } catch (LogginException e) {
            e.printStackTrace();
        }

    }
    

    private void registro() {
        VentanaRegistro venReg = new VentanaRegistro(this, true);
        venReg.setVisible(true);
    }
    
    
    private void contraOlvidada() {
        String DNI = textFieldDNI.getText().trim();

        if (DNI.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            VentanaContraOlvidada venOlv = new VentanaContraOlvidada(this, true, DNI); 
            venOlv.setVisible(true);
        }
    }
    
    
    private void comprobarDNI() {
        Pasajero paj = new Pasajero();
        
        paj.setDni(textFieldDNI.getText());
        
        // Main.comprobarDNI();
        
    }
    
}
