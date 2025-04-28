package excepciones;

import javax.swing.JOptionPane;

public class LogginException extends Exception{

	private String mensaje;
	
	public LogginException(String mensaje) {
		this.mensaje=mensaje;
	}
	
	public void visualizarMensaje() {
		JOptionPane.showMessageDialog(null, this.mensaje, "Error", 0);
	}
}
