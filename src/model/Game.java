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

	
	public Game (int columns, int rows, int snakes, int ladders, int players) {
		this.columns = columns;
		this.rows = rows;
		rt = new RankingTree();
		createGame(snakes, ladders);
		assignRandomSymbols(players);
		first.setPlayer(this.players);
	}
	
	public Game (int columns, int rows, int snakes, int ladders, String players) {
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
		first = new Cell (1);
		createRow(1,1,first);
		includeSnakes(snakes);
		includeLadders(ladders);
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
		
		if (i % 2 != 0) {
			createColumn(i,j++,currentFirstRow,currentFirstRow.getDown());
			if(i+1 < rows) {
				Cell upFirstRow = new Cell((i + 1) * j);
				upFirstRow.setDown(currentFirstRow);
				currentFirstRow.setUp(upFirstRow);
				createRow(i++,j,upFirstRow);
			}
		}else {
			createColumn(i,j--,currentFirstRow,currentFirstRow.getDown());
			if(i+1 < rows) {
				Cell downFirstRow = new Cell((i + 1) * j);
				downFirstRow.setUp(currentFirstRow);
				currentFirstRow.setDown(downFirstRow);
				createRow(i++,j * columns,downFirstRow);
			}
		}
			
	}

	private void createColumn(int i, int j, Cell previous, Cell previuousRow) {
		if (i % 2 != 0) {
			if(j < columns) {
				Cell current = new Cell (i * j);
				current.setLeft(previous);
				previous.setRight(current);
				
				if(previuousRow!=null) {
					previuousRow = previuousRow.getRight();
					current.setUp(previuousRow);
					previuousRow.setDown(current);
				}
				
				createColumn(i,j++,current,previuousRow);
			}
		}else {
			if(j < columns) {
				Cell current = new Cell (i * j);
				current.setLeft(previous);
				previous.setRight(current);
				
				if(previuousRow!=null) {
					previuousRow = previuousRow.getRight();
					current.setUp(previuousRow);
					previuousRow.setDown(current);
				}
				
				createColumn(i,j--,current,previuousRow);
			}
		}

	}
	
	private void includeLadders(int ladders) {
		if (ladders > 0) {
			includeSnakes(ladders--);
			int firstNumberToSearch = random.ints(1, ((rows * columns)+1)).findFirst().getAsInt();
			Cell firstCell = searchCell(firstNumberToSearch);
			if (!firstCell.hasSnakeOrLadder() && !rowHasLadder(firstCell.getLeft(), firstCell.getRight())) {
				firstCell.setLadder(0 + ladders);
				int secondNumberToSearch = random.ints(1, ((rows * columns)+1)).findFirst().getAsInt();
				Cell secondCell = searchCell(secondNumberToSearch);
				if (!secondCell.hasSnakeOrLadder() && !rowHasLadder(secondCell.getLeft(), secondCell.getRight())) {
					secondCell.setLadder(0 + ladders);
				}
			}else {
				includeSnakes(ladders);
			}
		}
	}
	
	private boolean rowHasLadder(Cell leftCell, Cell rightCell) {
		boolean ladder = false;
		boolean ladderInLeft = leftRowHasLadder (leftCell);
		boolean ladderInRight = rightRowHasLadder (rightCell);
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
	
	private void includeSnakes(int snakes) {
		if (snakes > 0) {
			includeSnakes(snakes--);
			int firstNumberToSearch = random.ints(1, ((rows * columns)+1)).findFirst().getAsInt();
			Cell firstCell = searchCell(firstNumberToSearch);
			if (!firstCell.hasSnakeOrLadder() && !rowHasSnake(firstCell.getLeft(), firstCell.getRight())) {
				firstCell.setSnake((char)(64 + snakes));
				int secondNumberToSearch = random.ints(1, ((rows * columns)+1)).findFirst().getAsInt();
				Cell secondCell = searchCell(secondNumberToSearch);
				if (!secondCell.hasSnakeOrLadder() && !rowHasSnake(secondCell.getLeft(), secondCell.getRight())) {
					secondCell.setSnake((char)(64 + snakes));
				}
			}else {
				includeSnakes(snakes);
			}
		}
	}
	
	private boolean rowHasSnake(Cell leftCell, Cell rightCell) {
		boolean snake = false;
		boolean snakeInLeft = leftRowHasSnake (leftCell);
		boolean snakeInRight = rightRowHasSnake (rightCell);
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
		return searchCellInRow(1, 1, cellNumber, first);
	}

	private Cell searchCellInRow (int i, int j, int cellNumber, Cell currentFirstRow) {
		Cell cell = null;
		if (currentFirstRow.getNumber() == cellNumber)
			cell = currentFirstRow;
		else {
			cell = searchCellInColumn(i, j++, cellNumber, currentFirstRow.getRight());
			if(i++ < rows && cell == null) {				
				cell = searchCellInRow(i++, j, cellNumber, currentFirstRow.getUp());
			}
		}
		
		return cell;
	}

	private Cell searchCellInColumn(int i, int j, int cellNumber, Cell currentColumn) {
		Cell cell = null;
		if (currentColumn != null) {
			if (currentColumn.getNumber() == cellNumber)
				cell = currentColumn;
			else if(j < columns && currentColumn.getRight() !=null) {
				cell = searchCellInColumn(i, j++, cellNumber, currentColumn.getRight());
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
