package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Main;
import excepciones.LogginException;
import modelo.Tren;

public class VentanaMostrarTren extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private VentanaMaquinista ventanaMaquinista; // <-- Agrega esto

	/**
	 * Constructor de la ventana.
	 * 
	 * @param estaTren
	 * @param eleccionPestaña
	 * @param esta
	 */
	public VentanaMostrarTren(VentanaMaquinista ventanaMaquinista, boolean b, boolean estaTren) {
		super(ventanaMaquinista);
		this.setModal(b);
		this.ventanaMaquinista = ventanaMaquinista;

		setTitle("Lista de Trenes");
		setBounds(100, 100, 600, 400);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());

		String[] columnNames = { "ID", "Nombre", "Plazas", "Precio", "Origen", "Destino" };
		model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
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
						if (!estaTren) {
							String id = (String) model.getValueAt(row, 0);
							String nombre = (String) model.getValueAt(row, 1);
							int plazas = (Integer) model.getValueAt(row, 2);
							float precio = ((Number) model.getValueAt(row, 3)).floatValue();
							String origen = (String) model.getValueAt(row, 4);
							String destino = (String) model.getValueAt(row, 5);

							ventanaMaquinista.cargarDatosTren(id, nombre, plazas, precio, origen, destino);
							dispose();
							ventanaMaquinista.setVisible(true);
							ventanaMaquinista.getTabbedPane().setSelectedIndex(1); // Asegura que la pestaña "CRUD
																					// Trenes" se muestre
						} else {
							String id = (String) model.getValueAt(row, 0);
							String nombre = (String) model.getValueAt(row, 1);
							String origen = (String) model.getValueAt(row, 4);
							String destino = (String) model.getValueAt(row, 5);

							ventanaMaquinista.cargarDatosAsignarTren(id, nombre, origen, destino);
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
			Tren tren = new Tren();
			Object[][] datos = Main.mostrarDatosTren(tren);
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