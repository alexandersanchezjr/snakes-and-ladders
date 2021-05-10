package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Game;
import model.RankingTree;

public class Menu {
	
	private final static int START = 1;
	private final static int SHOW_RANKING = 2;
	private final static int EXIT = 3;
	
	private final static String MENU = "menu";
	private final static String SIMULATION = "simul";
	private final static String SHOW_ORIGINAL_BOARD = "num";

	private Game game;
	private RankingTree rt;
	
	private BufferedReader br;
		
	public Menu() {
		br = new BufferedReader(new InputStreamReader(System.in));
		rt = new RankingTree();
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

	public void readGame() throws IOException, InterruptedException {
		System.out.println("Ingrese la informacion del juego en una sola linea con la siguiente informacion:");
		System.out.println("- n: Numero de filas\n" + "- m: Numero de columnas\n" + "- s: Numero de serpientes\n" + "- e: Numero de escaleras\n" + "- p: Numero de jugadores");
		System.out.println("\nDigite en una misma línea 5 números enteros positivos separados por espacio indicando n, m, s, e y p respectivamente");
		System.out.println("Puedes ingresar los simbolos (* ! O X % $ # + &) de los jugadores directamente en lugar del entero positivo 'p'.");
		System.out.println("\nUn ejemplo de entrada así sería:\n5 4 2 3 #%* (todo en la misma línea) que indica al programa que cree un tablero de tamaño 5x4, con 2 serpientes, 3 escaleras y 3 jugadores. "
				+ "\nEl primer jugador es #, el segundo es % y el tercero *.\r\n"
				+ "");
		String firstLine = br.readLine();
		String[] parts = firstLine.split(" ");
		int rows = Integer.parseInt(parts[0]);
		int columns = Integer.parseInt(parts[1]);
		int snakes = Integer.parseInt(parts[2]);
		int ladders = Integer.parseInt(parts[3]);
		
		
		if((snakes*2) + (ladders*2) > rows*columns) {
			System.out.println("La cantidad de serpientes y escaleras sobrepasa las dimensiones del tablero de juego, intente con otros valores\n");
			return;
		}
		else if(parts[4].contains("*") || parts[4].contains("!") || parts[4].contains("O") || parts[4].contains("X") || parts[4].contains("%") || parts[4].contains("$") || parts[4].contains("#") || parts[4].contains("+") || parts[4].contains("&")) {
				String players = parts[4];
				game = new Game(rows, columns, snakes, ladders, players);
			}else {
				int players = Integer.parseInt(parts[4]);
				game = new Game(rows, columns, snakes, ladders, players);
			}
		
		System.out.println(game.boardToString());
		System.out.println("Esperando salto de linea para continuar...");
		br.readLine();
		System.out.println(game.gameToString());
		System.out.println("Esperando salto de linea para continuar...");
		playGame(br.readLine());
		
	}
	
	public void playGame(String input) throws IOException, InterruptedException {
		if(input.equals("")) {
			int diceValue = (int) (Math.random() * 6 + 1);
			game.moveByTurn(diceValue);
			System.out.println("El jugador " + game.getTurn().getSymbol() + " ha lanzado el dado y obtuvo el puntaje " + diceValue + "\n");
			System.out.println(game.gameToString());
			if (game.hasWinner()) {
				System.out.println("El jugador " + game.getTurn().getSymbol() + " ha ganado el juego, con " + game.getTurn().getCont() + " movimientos");
				System.out.print("Nickname: ");
				String nickname = br.readLine();
				addWinner(nickname);		
			}else {
				System.out.println("Esperando salto de linea para continuar...");
				playGame(br.readLine());
			}
		}else if(input.equals(MENU)) {
			startProgram();
		}else if(input.equals(SIMULATION)) {
			startSimulation();
		}else if(input.equals(SHOW_ORIGINAL_BOARD)) {
			System.out.println(game.boardToString());
			System.out.println("Esperando salto de linea para continuar...");
			playGame(br.readLine());
		}else {
			System.out.println("Ingrese un comando válido");
			System.out.println("Esperando salto de linea para continuar...");
			playGame(br.readLine());
		}
	}
	
	public void startSimulation () throws InterruptedException {
		int diceValue = (int) (Math.random() * 6 + 1);
		game.moveByTurn(diceValue);
		System.out.println("El jugador " + game.getTurn().getSymbol() + " ha lanzado el dado y obtuvo el puntaje " + diceValue + "\n");
		System.out.println(game.gameToString());
		if (game.hasWinner()) {
			System.out.println("El jugador " + game.getTurn().getSymbol() + " ha ganado el juego, con " + game.getTurn().getCont() + " movimientos");
			System.out.print("Nickname: ");
			String nickname = "";
			try {
				nickname = br.readLine();
				if (nickname == "") {
					nickname = "Computadora";					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addWinner(nickname);	
		}else {
			Thread.sleep(2000);
			startSimulation();
		}
	}
	
	public void addWinner (String nickname) {
		int numberOfPlayers = game.getSymbols().length();
		rt.addWinner(nickname, game.getTurn().getScore(), game.getColumns(), game.getRows(), game.getSnakes(), game.getLadders(), numberOfPlayers, game.getSymbols());
	}
	
	public void doOperation(int choice) throws InterruptedException {
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
					System.out.println(rt.toString());
				break;
			case EXIT:
				System.out.println("¡Adios!");
				break;
			default:
				System.out.println("Opcion invalida, repita nuevamente");
		}
	}
	
	public void startProgram() throws NumberFormatException, IOException, InterruptedException  {
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
			try {
				doOperation(option);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startProgram(option);
		}
	}
}
