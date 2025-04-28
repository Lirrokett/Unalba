package vista;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controlador.Main;
import excepciones.LogginException;
import modelo.Maquinista;

public class VentanaMostrarMaquinistas extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private VentanaMaquinista ventanaMaquinista; // <-- Agrega esto

    
    /**
     * Constructor de la ventana.
     * @param estaMaquinista 
     */
    public VentanaMostrarMaquinistas(VentanaMaquinista ventanaMaquinista, boolean b, boolean estaMaquinista) {
        super(ventanaMaquinista);
        this.setModal(b);
        this.ventanaMaquinista = ventanaMaquinista;
        
        setTitle("Lista de Maquinistas");
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout());

        String[] columnNames = { "DNI", "Nombre", "Apellido", "ES_ADMIN", "Contraseña", "Fecha Nac." };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Esto hace que ninguna celda sea editable
            }
        };
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Doble clic para pasar los datos
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {                  	                  	
                    	if(!estaMaquinista) {
	                        String dni = (String) model.getValueAt(row, 0);
	                        String nombre = (String) model.getValueAt(row, 1);
	                        String apellido = (String) model.getValueAt(row, 2);
	                        boolean esAdmin = (Boolean) model.getValueAt(row, 3);
	                        String contrasena = (String) model.getValueAt(row, 4);
	                        java.sql.Date fechaSQL = (java.sql.Date) model.getValueAt(row, 5);
	                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	                        String fechaNac = sdf.format(fechaSQL);
	
	                        VentanaMaquinista ventanaMaq = new VentanaMaquinista(null, true);
	                        ventanaMaq.cargarDatosCRUD(dni, nombre, apellido, esAdmin, contrasena, fechaNac);
	                        dispose();
	                        ventanaMaq.setVisible(true);

	                    } else {
	                        String dni = (String) model.getValueAt(row, 0);
	                        String nombre = (String) model.getValueAt(row, 1);
	                        String apellido = (String) model.getValueAt(row, 2);

	                        ventanaMaquinista.cargarDatosAsignarMaquinista(dni, nombre, apellido);
	                        dispose();
	                        ventanaMaquinista.setVisible(true); 
	                        ventanaMaquinista.getTabbedPane().setSelectedIndex(2);
	                    }
                    }
                }
            }
        });
        cargarDatos();
    }

    private void cargarDatos() {
        try {
            Maquinista maq = new Maquinista();
            Object[][] datos = Main.mostrarDatosMaquinista(maq);
            actualizarDatos(datos);
        } catch (LogginException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatos(Object[][] datos) {
        model.setRowCount(0);
        for (Object[] fila : datos) {
            model.addRow(fila);
        }
    }
   
}
