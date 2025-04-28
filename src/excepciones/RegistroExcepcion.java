package excepciones;

import javax.swing.JOptionPane;

public class RegistroExcepcion extends Exception{
	private String mensaje;

	public RegistroExcepcion(String mensaje) {
		this.mensaje=mensaje;
	}

	public void visualizarMensaje() {
		JOptionPane.showMessageDialog(null, this.mensaje, "Error", 0);
	}
}
