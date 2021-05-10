/**
 * 
 */
package ui;

import java.io.IOException;

/**
 * @author ALEX JR
 * @author Santiago Arevalo
 */
public class Main {
	
	private Menu menu;
	/**
	 * 
	 */
	private Main() {
		menu = new Menu ();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main ();
		System.out.println("¡Bienvenido a Serpientes y Escaleras!");
		try {
			main.menu.startProgram();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
