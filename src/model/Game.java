package model;

//import java.util.Random;

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
	
	private int firstLadder;
	private int secondLadder;
	private int firstSnake;
	private int secondSnake;
	
	private RankingTree rt;
	
	public Game (int rows, int columns, int snakes, int ladders, int players) {
		this.columns = columns;
		this.rows = rows;
		rt = new RankingTree();
		createGame(snakes, ladders);
		assignRandomSymbols(players);
		searchFirstBoardCell(first).setPlayer(this.players);
	}
	
	public Game (int rows, int columns, int snakes, int ladders, String players) {
		this.columns = columns;
		this.rows = rows;
		rt = new RankingTree();
		createGame(snakes, ladders);
		assignSymbols(players, players.length());
		searchFirstBoardCell(first).setPlayer(this.players);
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
		includeSnakes(snakes);
		includeLadders(ladders);
	}
	
	private void addPlayer(Player newPlayer, Player current) {
		 if (players == null) {
			 players = newPlayer;
		 }else if (current.getRight() == null){
			 current.setRight(newPlayer);
			 newPlayer.setLeft(current);
		 }else {
			 addPlayer(newPlayer, current.getRight());
		 }
	}
	
	private void assignRandomSymbols(int numberOfPlayers) {
		if(numberOfPlayers > 0) {
			createRandomPlayer (numberOfPlayers);
			assignRandomSymbols(numberOfPlayers-1);
		}
	}
	
	private void createRandomPlayer (int numberOfPlayers) {
		char symbol = generateRandomSymbol();
		Player newPlayer = new Player (symbol);
		if (!searchPlayer(newPlayer, players)) {
			addPlayer(newPlayer, players);
		}else {
			createRandomPlayer (numberOfPlayers);
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
		int symbolNumber = (int) ((Math.random() * (9-1)) +1);
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
	
	private void includeLadders(int ladders) {
		if (ladders > 0) {
			createFirstLadder(false, ladders);
			includeLadders(ladders-1);
		}
	}
	
	private void createFirstLadder (boolean stop, int ladders) {
		if (!stop) {
			firstLadder = (int) ((Math.random() * ((rows*columns)-2)) + 2);
			Cell firstCell = searchCell(firstLadder);
			if (!firstCell.hasSnakeOrLadder() && rowHaSameLadder(firstCell, firstCell.getLadder())) {
				firstCell.setLadder(ladders);
				stop = true;
				createSecondLadder (false, ladders);
			}
			createFirstLadder (stop, ladders);
		}
		
	}
	
	private void createSecondLadder (boolean stop, int ladders) {

		if (!stop) {
			secondLadder = (int) ((Math.random() * ((rows*columns)-2)) + 2);
			Cell secondCell = searchCell(secondLadder);
			if (!secondCell.hasSnakeOrLadder() && rowHaSameLadder(secondCell, secondCell.getLadder())) {
				secondCell.setLadder(ladders);
				stop = true;
			}
			createSecondLadder (stop, ladders);
		}
	}
	
	private boolean rowHaSameLadder(Cell current, int cellLadder) {	
		boolean ladder = false;
		boolean ladderInLeft = false;
		boolean ladderInRight = false;
		if (current.getLeft() != null) {
			ladderInLeft = leftRowHasSameLadder (current.getLeft(), cellLadder);		
		}
		if (current.getRight() != null) {
			ladderInRight = rightRowHasSameLadder (current.getRight(), cellLadder);			
		}
		if (ladderInLeft == true || ladderInRight == true)
			ladder = true;
		return ladder;
	}
	
	private boolean leftRowHasSameLadder (Cell leftCell, int cellLadder) {
		boolean sameLadderInLeft = false;
		boolean exit = false;
		if (leftCell != null) {
			if (leftCell.getLadder() == cellLadder) {
				sameLadderInLeft = true;	
			}
			if (sameLadderInLeft == true)
				exit = true;
			else 
				exit = leftRowHasSameLadder (leftCell.getLeft(), cellLadder);
		}else
			exit = false; 

		return exit;
	}
	
	private boolean rightRowHasSameLadder (Cell rightCell, int cellLadder) {
		boolean sameLadderInRight = false;
		boolean exit = false;
		if (rightCell != null) {
			if (rightCell.getLadder() == cellLadder) {
				sameLadderInRight = true;	
			}			
			if (sameLadderInRight == true)
				exit = true;
			else 
				exit = rightRowHasSameLadder (rightCell.getRight(), cellLadder);
		}else
			exit = false; 

		return exit;
		
	}
	
	private void includeSnakes(int snakes) {
		if (snakes > 0) {
			createFirstSnake(false, snakes);
			createSecondSnake(false, snakes);
			includeSnakes(snakes-1);
		}
	}
	
	private void createFirstSnake (boolean stop, int snakes) {
		if (stop != true) {
			firstSnake = (int) ((Math.random() * ((rows*columns)-1)) + 1);
			Cell firstCell = searchCell(firstSnake);
			if (!firstCell.hasSnakeOrLadder() && rowHasSameSnake(firstCell, firstCell.getSnake())) {
				firstCell.setSnake((char)(64 + snakes));
				stop = true;
			}
			createFirstSnake (stop, snakes);
		}
	}
	
	private void createSecondSnake (boolean stop, int snakes) {
		if (stop != true) {
			secondSnake = (int) ((Math.random() * ((rows*columns)-1)) + 1);
			Cell secondCell = searchCell(secondSnake);
			if (!secondCell.hasSnakeOrLadder()) {
				secondCell.setSnake((char)(64 + snakes));
				stop = true;
			}
			createSecondSnake (stop, snakes);
		}
			
	}
	
	private boolean rowHasSameSnake(Cell current, char cellSnake) {
		boolean snake = false;
		boolean snakeInLeft = false;
		boolean snakeInRight = false;
		if (current.getLeft() != null) {
			snakeInLeft = leftRowHasSameSnake (current.getLeft(), cellSnake);		
		}
		if (current.getRight() != null) {
			snakeInRight = rightRowHasSameSnake (current.getRight(), cellSnake);			
		}
		if (snakeInLeft == true || snakeInRight == true)
			snake = true;
		return snake;
	}
	
	private boolean leftRowHasSameSnake (Cell leftCell, char cellSnake) {
		boolean sameSnakeInLeft = false;
		boolean exit = false;
		if (leftCell != null) {
			if (cellSnake == leftCell.getSnake()){
				sameSnakeInLeft = true;	
			}
			if (sameSnakeInLeft == true)
				exit = true;
			else 
				exit = leftRowHasSameSnake (leftCell.getLeft(), cellSnake);
		}else
			exit = false; 

		return exit;
	}
	
	private boolean rightRowHasSameSnake (Cell rightCell, char cellSnake) {
		boolean sameSnakeInRight = false;
		boolean exit = false;
		if (rightCell != null) {
			if (cellSnake == rightCell.getSnake()){
				sameSnakeInRight = true;	
			}
			if (sameSnakeInRight == true)
				exit = true;
			else 
				exit = rightRowHasSameSnake (rightCell.getRight(), cellSnake);
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
	
	public void movePlayer (Player p, int diceValue) {
		Cell c = searchCell(p.getCellNumber()+diceValue);
		if(c.hasSnakeOrLadder()) {
			if(c.getSnake() != 0) {
				int cellNumber = searchSnake(c.getSnake(), c.getNumber()-1, false);
				searchCell(cellNumber).setPlayer(p);
			}else {
				
			}
			p.increaseCont();
		}
	}
	
	private int searchSnake(char snake, int number, boolean found) {
		Cell current = searchCell(number);
		int currentNumber = 0;
		if(!found) {
			if(current.getSnake() == snake) {
				currentNumber = current.getNumber();
				found = true;
			}else {
				currentNumber = searchSnake(snake, number-1, found);
			}
		}
		return currentNumber;
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
