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
	
	
	public Game (int rows, int columns, int snakes, int ladders, int players) {
		this.columns = columns;
		this.rows = rows;
		this.snakes = snakes;
		this.ladders = ladders;
		createGame(snakes, ladders);
		assignRandomSymbols(players);
		symbols = playersToString(this.players);
		searchFirstBoardCell(first).setInfo(getSymbols());
	}
	
	public Game (int rows, int columns, int snakes, int ladders, String players) {
		this.columns = columns;
		this.rows = rows;
		createGame(snakes, ladders);
		assignSymbols(players, players.length() - 1);
		symbols = playersToString(this.players);
		System.out.println(getSymbols());
		searchFirstBoardCell(first).setInfo(getSymbols());
	}

	/**
	 * @return the turn
	 */
	public Player getTurn() {
		return turn;
	}

	/**
	 * @return the symbols
	 */
	public String getSymbols() {
		return symbols;
	}

	/**
	 * @return the columns
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return the snakes
	 */
	public int getSnakes() {
		return snakes;
	}

	/**
	 * @return the ladders
	 */
	public int getLadders() {
		return ladders;
	}
	
	/**
	* Assign the symbols chosen by the user.<br>
	* <b>pre:</b> The playerSymbols is initialized (is not empty). <br>
	* <b>pre:</b> The length is initialized (is not zero). <br>
	* <b>post:</b> Has added the new player(s) given its symbols. 
	* @param playersSymbols The symbols the user chooses. playersSymbols != null, playersSymbols != "".
	* @param length Length of the playerSymbols string. length &gt;= 0.
	*/
	private void assignSymbols(String playersSymbols, int length){
		if (length >= 0) {
			assignSymbols(playersSymbols, length-1);
			Player newPlayer = new Player (playersSymbols.charAt(length));
			addPlayer(newPlayer, players);
		}
	}

	/**
	* Create a new game.<br>
	* <b>pre:</b> The snakes is initialized (is not zero). <br>
	* <b>pre:</b> The ladders is initialized (is not zero). <br>
	* <b>post:</b> Has created the board and player(s). 
	* @param snakes The number of snakes the user chooses. snakes &gt;= 0.
	* @param ladders The number of ladders the user chooses. ladders &gt;= 0.
	*/
	private void createGame(int snakes, int ladders) {
		first = new Cell ();
		createRow(0,0,first);
		numberCells();
		includeSnakes(snakes);
		includeLadders(ladders);
	}
	
	/**
	* Add a new player to the linked list of players recursively.<br>
	* <b>post:</b> Has added a new player to the game if and only if the new player did not exist previously. 
	* @param newPlayer The new player to be added. current != null.
	* @param current The linked list players which will be traverse.
	*/	
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
	
	/**
	* Assign random symbols to the players which will be created.<br>
	* <b>pre:</b> The numberOfPlayers is initialized (is not zero). <br>
	* <b>post:</b> Has created player(s) with random symbols. 
	* @param numberOfPlayers The number of players the user chooses will be created. numberOfPlayers &gt;= 0.
	*/
	private void assignRandomSymbols(int numberOfPlayers) {
		if(numberOfPlayers > 0) {
			createRandomPlayer (numberOfPlayers);
			assignRandomSymbols(numberOfPlayers-1);
		}
	}
	
	/**
	* Create a player with a random symbol recursively just when the resultant symbol is not used previously.<br>
	* <b>pre:</b> The numberOfPlayers is not zero. <br>
	* <b>pre:</b> The symbol variable is one of the nine (9) predefined symbols. <br>
	* <b>post:</b> Has created player with a unique symbol. 
	* @param numberOfPlayers The number of the player in order to be created. numberOfPlayers &gt;= 0.
	*/
	private void createRandomPlayer (int numberOfPlayers) {
		char symbol = generateRandomSymbol();
		Player newPlayer = new Player (symbol);
		if (searchPlayer(newPlayer, players) == null) {
			addPlayer(newPlayer, players);
		}else {
			createRandomPlayer (numberOfPlayers);
		}
	}
	
	/**
	* Search a player in the game and returns it.<br>
	* <b>pre:</b> The player to be searched exists in the linked list (is not null). <br>
	* <b>post:</b> Has found or not the desired player in the game. 
	* @param player The player to be searched. player != null.
	* @param current The linked list players which will be traverse.
	* @return p The found player or null if was not found.
	*/
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
	
	/**
	* Generate a random symbol.<br>
	* <b>post:</b> Has returned a character of the nine (9) previously defined.
	* @return symbol The symbol generated randomly.
	*/
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
	
	/**
	* Create a complete row for the board and, therefore, the whole board.<br>
	* <b>pre:</b> The currentFirstRow is not null. <br>
	* <b>pre:</b> The i param is equal to 0. <br>
	* <b>pre:</b> The j param is equal to 0. <br>
	* <b>post:</b> Has created the board. 
	* @param i The iterator for the rows. i == 0.
	* @param j The iterator for the columns. j == 0.
	* @param currentFirstRow The current row to be created since the first cell in row. currentFirstRow != null.
	*/
	private void createRow(int i, int j, Cell currentFirstRow) {
		createColumn(i,j+1,currentFirstRow,currentFirstRow.getDown());
		if(i+1 < getRows()) {
			Cell downFirstRow = new Cell();
			downFirstRow.setUp(currentFirstRow);
			currentFirstRow.setDown(downFirstRow);
			createRow(i+1,j,downFirstRow);
		}
	}

	/**
	* Create a cell for the respective column in a row.<br>
	* <b>pre:</b> The previous is not null. <br>
	* <b>post:</b> Has created the row, column by column. 
	* @param i The iterator for the rows. i == 0.
	* @param j The iterator for the columns. j == 0.
	* @param previous The first cell in the row. previous != null.
	* @param previous The first cell in the row. previous != null.
	*/
	private void createColumn(int i, int j, Cell previous, Cell previuousRow) {	
		if(j < getColumns()) {
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
	
	/**
	* Number the cells once created.<br>
	* <b>pre:</b> The board exists. <br>
	* <b>post:</b> Has numbered the cells in a predefined format. 
	*/
	private void numberCells () {
		Cell firstBoardCell = searchFirstBoardCell(first);
		numberCellsToRight (2, getColumns() + 1 , firstBoardCell);
	}
	
	/**
	* Number the cells which number of row is odd.<br>
	* <b>pre:</b> The current is not null. <br>
	* <b>post:</b> Has numbered this row in ascendant order from left to right. 
	* @param i The number of the row.
	* @param j The number of columns.
	* @param current The current cell as parameter. current != null.
	*/
	private void numberCellsToRight (int i, int j, Cell current) {
		if (current != null) {
			numberCellsToRight(i, j + 1, current.getRight());
			current.setNumber(j - getColumns());
			if (current.getUp() != null && current.getLeft() == null) {
				Cell currentUp = current.getUp();
				numberCellsToLeft(i + 1, j - 1, currentUp);
			}
		}
	}
	
	/**
	* Number the cells which number of row is even.<br>
	* <b>pre:</b> The current is not null. <br>
	* <b>post:</b> Has numbered this row in descendant order from left to right. 
	* @param i The number of the row.
	* @param j The number of columns.
	* @param current The current cell as parameter. current != null.
	*/
	private void numberCellsToLeft (int i, int j, Cell current) {
		if (current != null)  {
			numberCellsToLeft(i, j - 1, current.getRight());
			current.setNumber(j + getColumns());
			if (current.getUp() != null && current.getLeft() == null){
				Cell currentUp = current.getUp();
				numberCellsToRight (i + 1, (i*getColumns()) + 1, currentUp);
			}
		}
		
	}
	
	/**
	* Search the cell with the number of cell equals to 1 and returns it.<br>
	* <b>pre:</b> The current cell is not to null. <br>
	* <b>post:</b> Has returned the the first cell. 
	* @param current The first cell which Game class has the association with and the cell under this cell. current != null.
	* @return firstBoardCell The cell with the cell number equals to 1.
	*/
	private Cell searchFirstBoardCell (Cell current) {
		Cell firstBoardCell = null;
		if (current.getDown() == null) {
			firstBoardCell = current;
		}else {
			firstBoardCell = searchFirstBoardCell(current.getDown());
		}
		return firstBoardCell;
	}
	
	/**
	* Include the number of ladders on the board.<br>
	* <b>pre:</b> The ladders parameter is not zero. <br>
	* <b>post:</b> Has include the whole desired ladders. 
	* @param ladders The number of ladders to be added to the board.
	*/
	private void includeLadders(int ladders) {
		if (ladders > 0) {
			createFirstLadder(false, ladders);
			createSecondLadder (false, ladders);
			includeLadders(ladders-1);
		}
	}
	
	/**
	* Create the start or end (first part) of a ladder in a cell.<br>
	* <b>pre:</b> The stop parameter is false when the method is invoked. Then is prone to change. <br>
	* <b>pre:</b> The ladders parameter is equal to the number in ladders in the method which invoked this one. <br>
	* <b>post:</b> Has created the first part of the ladder. 
	* @param stop The sentinel parameter which will finish the method.
	* @param ladders The number of the ladder which will be created. 
	*/
	private void createFirstLadder (boolean stop, int ladders) {
		if (!stop) {
			firstLadder = (int) ((Math.random() * ((getRows()*getColumns())-2)) + 2);
			Cell firstCell = searchCell(firstLadder);
			if (!firstCell.hasSnakeOrLadder() && !rowHasSameLadder(firstCell, ladders)) {
				firstCell.setLadder(ladders);
				stop = true;
			}
			createFirstLadder (stop, ladders);
		}
		
	}
	
	/**
	* Create the start or end (second part) of a ladder in a cell.<br>
	* <b>pre:</b> The stop parameter is false when the method is invoked. Then is prone to change. <br>
	* <b>pre:</b> The ladders parameter is equal to the number in ladders in the method which invoked this one. <br>
	* <b>post:</b> Has created the second part of the ladders . 
	* @param stop The sentinel parameter which will finish the method.
	* @param ladders The number of the ladder which will be created. 
	*/
	private void createSecondLadder (boolean stop, int ladders) {

		if (!stop) {
			secondLadder = (int) ((Math.random() * ((getRows()*getColumns())-2)) + 2);
			Cell secondCell = searchCell(secondLadder);
			if (!secondCell.hasSnakeOrLadder() && !rowHasSameLadder(secondCell, ladders)) {
				secondCell.setLadder(ladders);
				stop = true;
			}
			createSecondLadder (stop, ladders);
		}
	}
	
	/**
	* Verifies if the current cell row has the same ladder number.<br>
	* <b>pre:</b> The current cell is not to null. <br>
	* <b>pre:</b> The cellLadder is not to zero. <br>
	* <b>post:</b> Has returned true if has found the same ladder or false if not. 
	* @param current The cell required to create the ladder in. current != null.
	* @param cellLadder The number of the ladder which will be compared.
	* @return ladder True if same ladder is found or false if not.
	*/
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
	
	/**
	* Include the number of snakes on the board.<br>
	* <b>pre:</b> The snakes parameter is not zero. <br>
	* <b>post:</b> Has include the whole desired snakes. 
	* @param snakes The number of snakes to be added to the board.
	*/
	private void includeSnakes(int snakes) {
		if (snakes > 0) {
			createFirstSnake(false, snakes);
			createSecondSnake(false, snakes);
			includeSnakes(snakes-1);
		}
	}
	
	/**
	* Create the start or end (first part) of a snake in a cell.<br>
	* <b>pre:</b> The stop parameter is false when the method is invoked. Then is prone to change. <br>
	* <b>pre:</b> The snakes parameter is equal to the number in snakes in the method which invoked this one. <br>
	* <b>post:</b> Has created the first part of the snake . 
	* @param stop The sentinel parameter which will finish the method.
	* @param ladders The number of the snake which will be casted in a char type, then will be created. 
	*/
	private void createFirstSnake (boolean stop, int snakes) {
		if (stop != true) {
			firstSnake = (int) ((Math.random() * ((getRows()*getColumns())-1)) + 1);
			Cell firstCell = searchCell(firstSnake);
			char snakeLetter = (char)(64 + snakes);
			if (!firstCell.hasSnakeOrLadder() && !rowHasSameSnake(firstCell, snakeLetter)) {
				firstCell.setSnake(snakeLetter);
				stop = true;
			}
			createFirstSnake (stop, snakes);
		}
	}
	
	/**
	* Create the start or end (second part) of a snake in a cell.<br>
	* <b>pre:</b> The stop parameter is false when the method is invoked. Then is prone to change. <br>
	* <b>pre:</b> The snakes parameter is equal to the number in snakes in the method which invoked this one. <br>
	* <b>post:</b> Has created the second part of the snakes . 
	* @param stop The sentinel parameter which will finish the method.
	* @param ladders The number of the snake which will be casted in a char type, then will be created. 
	*/
	private void createSecondSnake (boolean stop, int snakes) {
		if (stop != true) {
			secondSnake = (int) ((Math.random() * ((getRows()*getColumns())-1)) + 1);
			Cell secondCell = searchCell(secondSnake);
			char snakeLetter = (char)(64 + snakes);
			if (!secondCell.hasSnakeOrLadder() && !rowHasSameSnake(secondCell, snakeLetter)) {
				secondCell.setSnake(snakeLetter);
				stop = true;
			}
			createSecondSnake (stop, snakes);
		}
			
	}
	
	/**
	* Verifies if the current cell row has the same snake character value.<br>
	* <b>pre:</b> The current cell is not to null. <br>
	* <b>pre:</b> The cellSnake value is not below of 64. <br>
	* <b>post:</b> Has returned true if has found the same snake or false if not. 
	* @param current The cell required to create the snake in. current != null.
	* @param cellSnake The character of the snake which will be compared.
	* @return snake True if same snake is found or false if not.
	*/
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

	/**
	* Search the cell given a cell number.<br>
	* <b>pre:</b> The cellNumber is not zero. <br>
	* <b>pre:</b> The the cell exists. <br>
	* <b>post:</b> Has returned the cell if found or null if not. 
	* @param cellNumber The number of the cell which will be searched. cellNumber != 0.
	* @return Cell A cell if found of null if not.
	*/
	private Cell searchCell (int cellNumber) {		
		return searchCellInRow(0, 0, cellNumber, first);
	}

	private Cell searchCellInRow (int i, int j, int cellNumber, Cell currentFirstRow) {
		Cell cell = null;
		if (i < getRows()) {
			if (currentFirstRow.getNumber() == cellNumber)
				cell = currentFirstRow;
			else {
				cell = searchCellInColumn(i, j+1, cellNumber, currentFirstRow.getRight());
				if(i+1 < getRows() && cell == null) {				
					cell = searchCellInRow(i+1, j, cellNumber, currentFirstRow.getDown());
				}
			}	
		}
		return cell;
	}

	private Cell searchCellInColumn(int i, int j, int cellNumber, Cell currentColumn) {
		Cell cell = null;
		if (j < getColumns()) {
			if (currentColumn.getNumber() == cellNumber)
				cell = currentColumn;
			else if(j < getColumns() && currentColumn.getRight() !=null) {
				cell = searchCellInColumn(i, j+1, cellNumber, currentColumn.getRight());
			}
		}
		return cell;
		
	}
	
	/**
	* Converts the linked list of players in a symbols string.<br>
	* <b>pre:</b> The current is not null. <br>
	* <b>post:</b> Has returned the string of players. 
	* @param current The first player in the linked list. current != nul.
	* @return message A string containing the players symbols.
	*/
	private String playersToString (Player current) {
		String message = "";
		if (current != null) {
			message += current.getSymbol();
			message += playersToString (current.getRight());
		}
		return message;
	}

	/**
	* Move a player depending of the turn of each one.<br>
	* <b>pre:</b> The diceValue a number in a range. <br>
	* <b>post:</b> Has move the player by its turn respectively. 
	* @param diceValue The dice value. diceValue &gt;= 1 and &lt;=6.
	*/
	public void moveByTurn (int diceValue) {
		if (turn == null) {
			turn = players;
			movePlayer (turn, diceValue);
		}else if (turn.getRight() != null){
			turn = turn.getRight();
			movePlayer (turn, diceValue);
		}else {
			turn = turn.getRight();
			moveByTurn (diceValue);
		}
	}
	
	/**
	* Move a player to a cell given the dice value or in a row above or below if is moved to a ladder or snake, respectively.<br>
	* <b>pre:</b> The diceValue a number in a range. <br>
	* <b>pre:</b> The p player is not null. <br>
	* <b>post:</b> Has move the player to the correspondent cell. 
	* @param diceValue The dice value. diceValue &gt;= 1 and &lt;=6.
	* @param p The p player to be moved. p != null.
	*/
	private void movePlayer (Player p, int diceValue) {
		Cell cellToRemovePlayer = searchCell (p.getCellNumber());
		int newCellNumber = p.getCellNumber() + diceValue;
		Cell cellToAddPlayer = newCellNumber > getRows() * getColumns() ? searchCell(getRows() * getColumns()) : searchCell(newCellNumber);
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
		p.setScore(getRows() * getColumns());
	}

	public boolean hasWinner() {
		if (searchCell(getRows() * getColumns()).getInfo() != "") {
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
		if(number <= (getRows() * getColumns())) {
			Cell current = searchCell(number);
			if(current.getLadder() == ladder) {
				currentNumber = current.getNumber();
			}else {
				currentNumber = searchLadder(ladder, number + 1);
			}
		}
		return currentNumber;
	}

	/**
	* Converts the game board in a cell string with just numbers and its snakes and ladders.<br>
	* <b>pre:</b> The current is not null. <br>
	* <b>post:</b> Has returned the string of players. 
	* @param current The first player in the linked list. current != null.
	* @return message A string containing the players symbols.
	*/
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
	
	/**
	* Converts the game board in a cell string with snakes, ladders and players .<br>
	* <b>pre:</b> The current is not null. <br>
	* <b>post:</b> Has returned the string of players. 
	* @param current The first player in the linked list. current != null.
	* @return message A string containing the players symbols.
	*/
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
