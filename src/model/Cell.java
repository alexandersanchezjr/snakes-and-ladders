package model;

public class Cell {
	private int number;
	private char snake;
	private int ladder;
	private Player player;
	
	private Cell right;
	private Cell left;
	private Cell up;	
	private Cell down;
	
	public Cell (int number, char snake, int ladder) {
		this.number = number;
		this.snake = snake;
		this.ladder = ladder;
	}
	/**
	 * @return the number
	*/
	public int getNumber() {
		return number;
	}


	
//	public char getNameCol() {
//		return (char)('A'+column);
//	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Cell getRight() {
		return right;
	}

	public Cell getLeft() {
		return left;
	}

	public Cell getUp() {
		return up;
	}

	public Cell getDown() {
		return down;
	}

	public void setPrev(Cell left) {
		this.left = left;
	}

	public void setRight(Cell right) {
		this.right = right;
	}

	public void setUp(Cell u) {
		up = u;
	}

	public void setDown(Cell d) {
		down = d;
	}
	
	public String cellToString() {
		if (snake != 0) 
			return "[" + number + snake + "]";
		else if (ladder != 0)
			return "[" + number + ladder + "]";
		else 
			return "[" + number + "]";
		
	}
	
	public String gameToString () {
		if (snake != 0) 
			return "[" + snake + player.toString(player) + "]";
		else if (ladder != 0)
			return "[" + ladder + player.toString(player) + "]";
		else if (player != null)
			return "[" + player.toString(player) + "]";
		else
			return "[\t]";
	}
}
