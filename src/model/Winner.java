package model;

public class Winner {
	
	private String nickname;
	private int score;
	private int moves;
	private int columns;
	private int rows;
	private int snakes;
	private int ladders;
	private int players;
	private String symbols;
	
	private Winner parent;
	private Winner left;
	private Winner right;
	
	public Winner(String nickname, int score, int columns, int rows, int snakes, int ladders, int players, String symbols, int moves) {
		this.nickname = nickname;
		this.score = score;
		this.columns = columns;
		this.rows = rows;
		this.snakes = snakes;
		this.ladders = ladders;
		this.players = players;
		this.symbols = symbols;
		this.moves = moves;
		parent = null;
		left = null;
		right = null;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the columns
	 */
	public int getColumns() {
		return columns;
	}
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}
	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	/**
	 * @return the snakes
	 */
	public int getSnakes() {
		return snakes;
	}
	/**
	 * @param snakes the snakes to set
	 */
	public void setSnakes(int snakes) {
		this.snakes = snakes;
	}
	/**
	 * @return the ladders
	 */
	public int getLadders() {
		return ladders;
	}
	/**
	 * @param ladders the ladders to set
	 */
	public void setLadders(int ladders) {
		this.ladders = ladders;
	}
	/**
	 * @return the players
	 */
	public int getPlayers() {
		return players;
	}
	/**
	 * @param players the players to set
	 */
	public void setPlayers(int players) {
		this.players = players;
	}
	/**
	 * @return the symbols
	 */
	public String getSymbols() {
		return symbols;
	}
	/**
	 * @param symbols the symbols to set
	 */
	public void setSymbols(String symbols) {
		this.symbols = symbols;
	}
	/**
	 * @return the parent
	 */
	public Winner getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Winner parent) {
		this.parent = parent;
	}
	/**
	 * @return the left
	 */
	public Winner getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(Winner left) {
		this.left = left;
	}
	/**
	 * @return the right
	 */
	public Winner getRight() {
		return right;
	}
	/**
	 * @param rigth the right to set
	 */
	public void setRight(Winner right) {
		this.right = right;
	}
	
	/**
	* Returns the winner data in a string form.<br>
	* <b>post:</b> Has returned the winner data. 
	* @return a message containing the winner info.
	*/
	public String showData() {
		String msg = "";
	    msg += "=================================================\n";
	    msg += "\nNombre: " + nickname + 
	    		"\nPuntaje: " +  score +
	    		"\nMovimientos: " + moves +
	    		"\nDimensión tablero: " + columns + "x" + rows + 
	    		"\nSerpientes: " + snakes + 
	    		"\nEscaleras: " + ladders + 
	    		"\nNumero de jugadores: " + players + 
	    		"\nSimbolos de jugadores: " + symbols + "\n\n";
	    msg += "=================================================\n\n";
	    return msg;
	}
}
