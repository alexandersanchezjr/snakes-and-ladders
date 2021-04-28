package model;

import java.util.Random;

public class Game {
	//private final String SEPARATOR = " ";
	
	private final static char ASTERISK = '*';
	private final static char EXCLAMATION = '!';
	private final static char UPPER_CASE_O = 'O';
	private final static char UPPER_CASE_X = 'X';
	private final static char PERCENTAGE = '%';
	private final static char DOLLAR = '$';
	private final static char HASH = '#';
	private final static char PLUS = '+';
	private final static char AMPERSAND = '&';
		
	private Cell first;
	private int columns;
	private int rows;
	private Player players;
	
	private RankingTree rt;
	
	Random random = new Random();

	
	public Game (int rows, int columns, int snakes, int ladders, int players) {
		this.columns = columns;
		this.rows = rows;
		rt = new RankingTree();
		createGame(snakes, ladders);
		assignRandomSymbols(players);
		first.setPlayer(this.players);
	}
	
	public Game (int rows, int columns, int snakes, int ladders, String players) {
		this.columns = columns;
		this.rows = rows;
		rt = new RankingTree();
		createGame(snakes, ladders);
		assignSymbols(players, players.length());
		first.setPlayer(this.players);
	}

	private void assignSymbols(String playersSymbols, int length){
		if (length > 0) {
			assignSymbols(playersSymbols, length--);
			Player newPlayer = new Player (playersSymbols.charAt(length));
			addPlayer(newPlayer, players);
		}
	}

	private void createGame(int snakes, int ladders) {
		first = new Cell ();
		createRow(0,0,first);
		numberCells();
		System.out.println(boardToString());
		includeSnakes(snakes, false);
		includeLadders(ladders, false);
	}
	
	private void addPlayer(Player newPlayer, Player current) {
		 if (players == null) {
			 players = newPlayer;
		 }else if (current.getRight() == null){
			 current.setRight(newPlayer);
		 }else {
			 addPlayer(newPlayer, current.getRight());
		 }

	}
	
	private void assignRandomSymbols(int numberOfPlayers) {
		if(numberOfPlayers != 0) {
			char symbol = generateRandomSymbol();
			Player newPlayer = new Player (symbol);
			if (!searchPlayer(newPlayer, players)) {
				addPlayer(newPlayer, players);
			}
			assignRandomSymbols(numberOfPlayers--);
		}
	}
	
	private boolean searchPlayer (Player player, Player current) {
		boolean found = false;
		if (current != null) {
			if (player.getSymbol() == current.getSymbol()) {
				found = true;
			}else {
				found = searchPlayer (player, current.getRight());
			}
		}
		return found;
	}
	
	private char generateRandomSymbol() {
		char symbol = 0;
		int symbolNumber = random.ints(1, 9).findFirst().getAsInt();
		switch(symbolNumber) {
		case 1:
			symbol = ASTERISK;
			break;
		case 2:
			symbol = EXCLAMATION;
			break;
		case 3:
			symbol = UPPER_CASE_O;
			break;
		case 4:
			symbol = UPPER_CASE_X;
			break;
		case 5:
			symbol = PERCENTAGE;
			break;
		case 6:
			symbol = DOLLAR;
			break;
		case 7:
			symbol = HASH;
			break;
		case 8:
			symbol = PLUS;
			break;
		case 9:
			symbol = AMPERSAND;
			break;
		}
		return symbol;
	}
	

	private void createRow(int i, int j, Cell currentFirstRow) {
		createColumn(i,j+1,currentFirstRow,currentFirstRow.getDown());
		if(i+1 < rows) {
			Cell downFirstRow = new Cell();
			downFirstRow.setUp(currentFirstRow);
			currentFirstRow.setDown(downFirstRow);
			createRow(i+1,j,downFirstRow);
		}
	}

	private void createColumn(int i, int j, Cell previous, Cell previuousRow) {	
		if(j < columns) {
			Cell current = new Cell ();
			current.setLeft(previous);
			previous.setRight(current);
			
			if(previuousRow!=null) {
				previuousRow = previuousRow.getRight();
				current.setUp(previuousRow);
				previuousRow.setDown(current);
			}
			
			createColumn(i,j+1,current,previuousRow);
		}
	}
	
	private void numberCells () {
		Cell firstBoardCell = searchFirstBoardCell(first);
		numberCellsToRight (2, columns + 1 , firstBoardCell);
	}
	
	private void numberCellsToRight (int i, int j, Cell current) {
		if (current != null) {
			numberCellsToRight(i, j + 1, current.getRight());
			current.setNumber(j - columns);
			if (current.getUp() != null && current.getLeft() == null) {
				Cell currentUp = current.getUp();
				numberCellsToLeft(i + 1, j - 1, currentUp);
			}
		}
	}
	
	private void numberCellsToLeft (int i, int j, Cell current) {
		if (current != null)  {
			numberCellsToLeft(i, j - 1, current.getRight());
			current.setNumber(j + columns);
			if (current.getUp() != null && current.getLeft() == null){
				Cell currentUp = current.getUp();
				numberCellsToRight (i + 1, (i*columns) + 1, currentUp);
			}
		}
		
	}
	
	private Cell searchFirstBoardCell (Cell current) {
		Cell firstBoardCell = null;
		if (current.getDown() == null) {
			firstBoardCell = current;
		}else {
			firstBoardCell = searchFirstBoardCell(current.getDown());
		}
		return firstBoardCell;
	}
	
	private void includeLadders(int ladders, boolean exit) {
		if (ladders > 0 && exit != true) {
			includeLadders(ladders-1, exit);
			int firstNumberToSearch = (int) (Math.random() * rows * columns) + 1;//random.ints(2, ((rows * columns)+1)).findFirst().getAsInt();
			System.out.println(firstNumberToSearch);
			Cell firstCell = searchCell(firstNumberToSearch);
			System.out.println("Escalera: " + firstCell);
			if (!firstCell.hasSnakeOrLadder() && !rowHasLadder(firstCell)) {
				firstCell.setLadder(0 + ladders);
				int secondNumberToSearch = random.ints(2, ((rows * columns)+1)).findFirst().getAsInt();
				Cell secondCell = searchCell(secondNumberToSearch);
				if (!secondCell.hasSnakeOrLadder() && !rowHasLadder(secondCell)) {
					secondCell.setLadder(0 + ladders);
					exit = true;
				}else {
					includeLadders(ladders, exit);
				}
			}else {
				includeLadders(ladders, exit);
			}
		}
	}
	
	private boolean rowHasLadder(Cell current) {
		boolean ladder = false;
		boolean ladderInLeft = false;
		boolean ladderInRight = false;
		if (current.getLeft() != null) {
			ladderInLeft = leftRowHasLadder (current.getLeft());		
		}
		if (current.getRight() != null) {
			ladderInRight = rightRowHasLadder (current.getRight());			
		}
		if (ladderInLeft == true || ladderInRight == true)
			ladder = true;
		return ladder;
	}
	
	private boolean leftRowHasLadder (Cell leftCell) {
		boolean ladderInLeft = false;
		boolean exit = false;
		if (leftCell != null) {
			ladderInLeft = leftCell.hasSnakeOrLadder();	
			if (ladderInLeft == true)
				exit = true;
			else 
				exit = leftRowHasLadder (leftCell.getLeft());
		}else
			exit = false; 

		return exit;
	}
	
	private boolean rightRowHasLadder (Cell rightCell) {
		boolean ladderInRight = false;
		boolean exit = false;
		if (rightCell != null) {
			ladderInRight = rightCell.hasSnakeOrLadder();	
			if (ladderInRight == true)
				exit = true;
			else 
				exit = rightRowHasLadder (rightCell.getRight());
		}else
			exit = false; 

		return exit;
		
	}
	
	private void includeSnakes(int snakes, boolean exit) {
		if (snakes > 0 && exit != true) {
			includeSnakes(snakes-1, exit);
			int firstNumberToSearch = (int) (Math.random() * ((rows * columns) - 1)) + 1;
			System.out.println(firstNumberToSearch);
			Cell firstCell = searchCell(firstNumberToSearch);
			System.out.println("Serpiente: " + firstCell);
			if (!firstCell.hasSnakeOrLadder() && !rowHasSnake(firstCell)) {
				firstCell.setSnake((char)(64 + snakes));
				int secondNumberToSearch = random.ints(1, (rows * columns)).findFirst().getAsInt();
				Cell secondCell = searchCell(secondNumberToSearch);
				if (!secondCell.hasSnakeOrLadder() && !rowHasSnake(secondCell)) {
					secondCell.setSnake((char)(64 + snakes));
					exit = true;
				}else {
					includeLadders(snakes, exit);
				}
			}else {
				includeSnakes(snakes, exit);
			}
		}
	}
	
	private boolean rowHasSnake(Cell current) {
		boolean snake = false;
		boolean snakeInLeft = false;
		boolean snakeInRight = false;
		if (current.getLeft() != null) {
			snakeInLeft = leftRowHasSnake (current.getLeft());		
		}
		if (current.getRight() != null) {
			snakeInRight = rightRowHasSnake (current.getRight());			
		}
		if (snakeInLeft == true || snakeInRight == true)
			snake = true;
		return snake;
	}
	
	private boolean leftRowHasSnake (Cell leftCell) {
		boolean snakesInLeft = false;
		boolean exit = false;
		if (leftCell != null) {
			snakesInLeft = leftCell.hasSnakeOrLadder();	
			if (snakesInLeft == true)
				exit = true;
			else 
				exit = leftRowHasSnake (leftCell.getLeft());
		}else
			exit = false; 

		return exit;
	}
	
	private boolean rightRowHasSnake (Cell rightCell) {
		boolean snakesInRight = false;
		boolean exit = false;
		if (rightCell != null) {
			snakesInRight = rightCell.hasSnakeOrLadder();	
			if (snakesInRight == true)
				exit = true;
			else 
				exit = rightRowHasSnake (rightCell.getRight());
		}else
			exit = false; 

		return exit;
		
	}

	private Cell searchCell (int cellNumber) {		
		return searchCellInRow(0, 0, cellNumber, first);
	}

	private Cell searchCellInRow (int i, int j, int cellNumber, Cell currentFirstRow) {
		Cell cell = null;
		if (i < rows) {
			if (currentFirstRow.getNumber() == cellNumber)
				cell = currentFirstRow;
			else {
				cell = searchCellInColumn(i, j+1, cellNumber, currentFirstRow.getRight());
				if(i+1 < rows && cell == null) {				
					cell = searchCellInRow(i+1, j, cellNumber, currentFirstRow.getDown());
				}
			}	
		}
		return cell;
	}

	private Cell searchCellInColumn(int i, int j, int cellNumber, Cell currentColumn) {
		Cell cell = null;
		if (j < columns) {
			if (currentColumn.getNumber() == cellNumber)
				cell = currentColumn;
			else if(j < columns && currentColumn.getRight() !=null) {
				cell = searchCellInColumn(i, j+1, cellNumber, currentColumn.getRight());
			}
		}
		return cell;
		
	}
	
	public String showRankingTree() {
		return rt.toString();
	}
	
	public void movePlayer () {
		
	}
	
	public String boardToString() {
		String msg;
		msg = boardRowToString(first);
		return msg;
	}

	private String boardRowToString (Cell firstRow) {
		String msg = "";
		if(firstRow != null) {
			msg = boardColumnToString(firstRow) + "\n";
			msg += boardRowToString(firstRow.getDown());
		}
		return msg;
	}

	private String boardColumnToString(Cell current) {
		String msg = "";
		if(current != null) {
			msg = current.cellToString();
			msg += boardColumnToString(current.getRight());
		}
		return msg;
	}
	
	public String gameToString() {
		String msg;
		msg = gameRowToString(first);
		return msg;
	}

	private String gameRowToString (Cell firstRow) {
		String msg = "";
		if(firstRow != null) {
			msg = gameColumnToString(firstRow) + "\n";
			msg += gameRowToString(firstRow.getDown());
		}
		return msg;
	}

	private String gameColumnToString(Cell current) {
		String msg = "";
		if(current != null) {
			msg = current.gameToString();
			msg += gameColumnToString(current.getRight());
		}
		return msg;
	}

}
