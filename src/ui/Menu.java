package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Game;

public class Menu {
	
	private final static int START = 1;
	private final static int SHOW_RANKING = 2;
	private final static int EXIT = 3;
	
	private final static String MENU = "menu";
	private final static String SIMULATION = "simul";
	private final static String SHOW_ORIGINAL_BOARD = "num";

	private Game game;
	
	private BufferedReader br;
		
	public Menu() {
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void showMenu() {
		System.out.println("(1) Empezar nuevo juego");
		System.out.println("(2) Ver tablero de posiciones");
		System.out.println("(3) Salir del programa");
	}
	
	public int readOption() throws NumberFormatException, IOException {
		int choice = Integer.parseInt(br.readLine());
		return choice;
	}

	public void readGame() throws IOException {
		System.out.println("Ingrese la informacion del juego en una sola linea con el siguiente formato:");
		System.out.println("N°filas N°columnas N°serpientes N°escaleras N°jugadores/Simbolos\nPuede ingresar el numero de jugadores y los simbolos seran asigandos aleatoriamente o ingresar los simbolos de cada uno sin separacion... ");
		String firstLine = br.readLine();
		String[] parts = firstLine.split(" ");
		int rows = Integer.parseInt(parts[0]);
		int columns = Integer.parseInt(parts[1]);
		int snakes = Integer.parseInt(parts[2]);
		int ladders = Integer.parseInt(parts[3]);
		
		if(parts[4].contains("*") || parts[4].contains("!") || parts[4].contains("O") || parts[4].contains("X") || parts[4].contains("%") || parts[4].contains("$") || parts[4].contains("#") || parts[4].contains("+") || parts[4].contains("&")) {
			String players = parts[4];
			game = new Game(rows, columns, snakes, ladders, players);
		}else {
			int players = Integer.parseInt(parts[4]);
			game = new Game(rows, columns, snakes, ladders, players);
		}
		
		System.out.println(game.boardToString());
		System.out.println("Esperando salto de linea para continuar...");
		playGame(br.readLine());
		
	}
	
	public void playGame(String input) throws IOException {
		if(!input.equals(MENU) && !input.equals(SIMULATION) && !input.equals(SHOW_ORIGINAL_BOARD)) {
			System.out.println(game.gameToString());
			playGame(br.readLine());
		}else if(input.equals(MENU)) {
			startProgram();
		}else if(input.equals(SIMULATION)) {
			playGame("");
		}else if(input.equals(SHOW_ORIGINAL_BOARD)) {
			System.out.println(game.boardToString());
			System.out.println("Esperando salto de linea para continuar...");
			playGame(br.readLine());
		}
	}
	
	public void showRankingBoard() {
		System.out.println(game.showRankingTree());
	}
	
	public void doOperation(int choice) {
		switch(choice) {
			case START:
				try {
					readGame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case SHOW_RANKING:
				showRankingBoard();
				break;
			case EXIT:
				System.out.println("¡Adios!");
				break;
			default:
				System.out.println("Opcion invalida, repita nuevamente");
		}
	}
	
	public void startProgram() throws NumberFormatException, IOException  {
		showMenu();
		int option = readOption();
		doOperation(option);
		startProgram(option);
	}
	
	private void startProgram(int option) {
		if(option != EXIT) {
			showMenu();
			try {
				option = readOption();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			doOperation(option);
			startProgram(option);
		}
	}
}
