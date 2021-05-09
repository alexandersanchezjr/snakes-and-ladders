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
	private int snakes;
	private int ladders;
	private Player players;
	private String symbols;
	
	private Player turn;
	
	private int firstLadder;
	private int secondLadder;
	private int firstSnake;
	private int secondSnake;
	
	private RankingTree rt;
	
	public Game (int rows, int columns, int snakes, int ladders, int players) {
		this.columns = columns;
		this.rows = rows;
		this.snakes = snakes;
		this.ladders = ladders;
		rt = new RankingTree();
		createGame(snakes, ladders);
		assignRandomSymbols(players);
		symbols = playersToString(this.players);
//		Player boardPlayers = this.players;
//		searchFirstBoardCell(first).setPlayer(boardPlayers);
		searchFirstBoardCell(first).setInfo(symbols);
	}
	
	public Game (int rows, int columns, int snakes, int ladders, String players) {
		this.columns = columns;
		this.rows = rows;
		rt = new RankingTree();
		createGame(snakes, ladders);
		assignSymbols(players, players.length());
		symbols = playersToString(this.players);
//		Player boardPlayers = this.players;
//		searchFirstBoardCell(first).setPlayer(boardPlayers);	
		searchFirstBoardCell(first).setInfo(symbols);
	}

	/**
	 * @return the turn
	 */
	public Player getTurn() {
		return turn;
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
		if (searchPlayer(newPlayer, players) == null) {
			addPlayer(newPlayer, players);
		}else {
			createRandomPlayer (numberOfPlayers);
		}
	}
	
	private Player searchPlayer (Player player, Player current) {
		Player p = null;
		if (current != null) {
			if (player.getSymbol() == current.getSymbol()) {
				p = current;
			}else {
				p = searchPlayer (player, current.getRight());
			}
		}
		return p;
	}
	
	private char generateRandomSymbol() {
		char symbol = 0;
		int symbolNumber = (int) (Math.random() * 9 +1);
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
			createSecondLadder (false, ladders);
			includeLadders(ladders-1);
		}
	}
	
	private void createFirstLadder (boolean stop, int ladders) {
		if (!stop) {
			firstLadder = (int) ((Math.random() * ((rows*columns)-2)) + 2);
			Cell firstCell = searchCell(firstLadder);
			if (!firstCell.hasSnakeOrLadder() && rowHasSameLadder(firstCell, firstCell.getLadder())) {
				firstCell.setLadder(ladders);
				stop = true;
			}
			createFirstLadder (stop, ladders);
		}
		
	}
	
	private void createSecondLadder (boolean stop, int ladders) {

		if (!stop) {
			secondLadder = (int) ((Math.random() * ((rows*columns)-2)) + 2);
			Cell secondCell = searchCell(secondLadder);
			if (!secondCell.hasSnakeOrLadder() && rowHasSameLadder(secondCell, secondCell.getLadder())) {
				secondCell.setLadder(ladders);
				stop = true;
			}
			createSecondLadder (stop, ladders);
		}
	}
	
	private boolean rowHasSameLadder(Cell current, int cellLadder) {	
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
		if (leftCell != null) {
			if (leftCell.getLadder() == cellLadder) {
				sameLadderInLeft = true;	
			}else 
				sameLadderInLeft = leftRowHasSameLadder (leftCell.getLeft(), cellLadder);
		}
		return sameLadderInLeft;
	}
	
	private boolean rightRowHasSameLadder (Cell rightCell, int cellLadder) {
		boolean sameLadderInRight = false;
		if (rightCell != null) {
			if (rightCell.getLadder() == cellLadder) {
				sameLadderInRight = true;	
			}else 
				sameLadderInRight = rightRowHasSameLadder (rightCell.getRight(), cellLadder);
		}
		return sameLadderInRight;
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
		if (leftCell != null) {
			if (cellSnake == leftCell.getSnake()){
				sameSnakeInLeft = true;	
			}else 
				sameSnakeInLeft = leftRowHasSameSnake (leftCell.getLeft(), cellSnake);
		}
		return sameSnakeInLeft;
	}
	
	private boolean rightRowHasSameSnake (Cell rightCell, char cellSnake) {
		boolean sameSnakeInRight = false;
		if (rightCell != null) {
			if (cellSnake == rightCell.getSnake()){
				sameSnakeInRight = true;	
			}else 
				sameSnakeInRight = rightRowHasSameSnake (rightCell.getRight(), cellSnake);
		}
		return sameSnakeInRight;
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
	
	private String playersToString (Player current) {
		String message = "";
		if (current != null) {
			message += current.getSymbol();
			message += playersToString (current.getRight());
		}
		return message;
	}
	
	public void addWinner (String nickname) {
		int numberOfPlayers = symbols.length();
		rt.addWinner(nickname, getWinner().getScore(), columns, rows, snakes, ladders, numberOfPlayers, symbols);
	}
	
	private Player getWinner () {
		Player p = searchPlayer (searchCell(rows * columns).getPlayer(), players);
		return p;
	}
	
	public String showRankingTree() {
		return rt.toString();
	}
	
	public void moveByTurn (int diceValue) {
		if (turn == null) {
			turn = players;
		}else {
			turn = turn.getRight();
			if (turn == null) {
				moveByTurn(diceValue);
			}
		}
		movePlayer (turn, diceValue);
	}
	
	private void movePlayer (Player p, int diceValue) {
		Cell cellToRemovePlayer = searchCell (p.getCellNumber());
		int newCellNumber = p.getCellNumber() + diceValue;
		Cell cellToAddPlayer = newCellNumber > rows * columns ? searchCell(rows * columns) : searchCell(newCellNumber);
		cellToRemovePlayer.removePlayer(p.getSymbol());
		if(cellToAddPlayer.hasSnakeOrLadder()) {
			if(cellToAddPlayer.getSnake() != 0) {
				int cellNumber = searchSnake(cellToAddPlayer.getSnake(), cellToAddPlayer.getNumber() - 1);
				if (cellNumber > 0) {
					Cell foundCell = searchCell(cellNumber);
					foundCell.addPlayer(p.getSymbol());
					p.setCellNumber(cellNumber);
				}else {
					cellToAddPlayer.addPlayer(p.getSymbol());
					p.setCellNumber(cellToAddPlayer.getNumber());
				}
			}else {
				int cellNumber = searchLadder(cellToAddPlayer.getLadder(), cellToAddPlayer.getNumber() + 1);
				if (cellNumber > 0) {
					Cell foundCell = searchCell(cellNumber);
					foundCell.addPlayer(p.getSymbol());
					p.setCellNumber(cellNumber);
				}else {
					cellToAddPlayer.addPlayer(p.getSymbol());
					p.setCellNumber(cellToAddPlayer.getNumber());
				}
			}
		}else {
			cellToAddPlayer.addPlayer(p.getSymbol());
			p.setCellNumber(cellToAddPlayer.getNumber());
		}
		p.increaseCont();
		p.setScore(rows * columns);
	}

	public boolean hasWinner() {
		if (searchCell(rows * columns).getInfo() != "") {
			return true;
		}else {
			return false;
		}
	}
	
	private int searchSnake(char snake, int number) {
		int currentNumber = 0;
		if(number > 0) {
			Cell current = searchCell(number);
			if(current.getSnake() == snake) {
				currentNumber = current.getNumber();
			}else {
				currentNumber = searchSnake(snake, number - 1);
			}
		}
		return currentNumber;
	}
	
	private int searchLadder(int ladder, int number) {
		int currentNumber = 0;
		if(number <= (rows * columns)) {
			Cell current = searchCell(number);
			if(current.getLadder() == ladder) {
				currentNumber = current.getNumber();
			}else {
				currentNumber = searchLadder(ladder, number + 1);
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
