import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {
	public static void main(String[] args) {
		AnalizadorLexico.init();
		AnalizadorSintactico.init();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Compilador");
		frame.setSize(250 + 6, 150 + 29);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		JPanel panel = (JPanel) frame.getContentPane();
		panel.setLayout(null);

		JLabel label = new JLabel();
		label.setText("Nombre del fichero:");
		label.setBounds(25, 25, 200, 25);
		panel.add(label);

		JTextField textField = new JTextField();
		textField.setBounds(25, 50, 200, 25);
		panel.add(textField);

		JButton button = new JButton();
		button.setText("Compilar");
		button.setBounds(75, 100, 100, 25);
		button.setFocusable(false);
		panel.add(button);

		ActionListener al = new ActionListener() {
			Thread thr;
			public void actionPerformed(ActionEvent e) {
				boolean res = compilar(textField.getText());
				if (thr != null)
					thr.interrupt();
				thr = new Thread() {
					public void run() {
						try {
							sleep(1000);
							button.setBackground(null);
						} catch (InterruptedException f) {
						}
					}
				};
				thr.start();
				if (res)
					button.setBackground(Color.green);
				else
					button.setBackground(Color.red);
			}
		};

		textField.addActionListener(al);
		button.addActionListener(al);
		frame.setVisible(true);
	}

	private static boolean compilar(String nombre) {
		String codigoFuente = leer(new File(nombre + ".txt"));
		if (codigoFuente == null)
			return false;

		TablaDeSimbolos.load();
		GestorErrores.load();
		AnalizadorLexico.load(codigoFuente);
		AnalizadorSintactico.generarParse();

		escribir(AnalizadorLexico.getListaTokens(), new File(nombre + "_tokens.txt"));
		escribir(AnalizadorSintactico.getParse(), new File(nombre + "_parse.txt"));
		escribir(TablaDeSimbolos.getTablas(), new File(nombre + "_tablas.txt"));
		escribir(GestorErrores.getErrors(), new File(nombre + "_errores.txt"));

		return true;
	}

	private static boolean escribir(String texto, File fichero) {
		try {
			FileWriter wr = new FileWriter(fichero);
			wr.write(texto, 0, texto.length());
			wr.close();
			return true;
		} catch (IOException f) {
			return false;
		}
	}

	private static String leer(File fichero) {
		try {
			FileReader fr = new FileReader(fichero);
			String res = "";
			int codigo;
			while ((codigo = fr.read()) != -1)
				res += (char) codigo;
			fr.close();
			return res;
		} catch (IOException f) {
			return null;
		}
	}
}
