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
	
//	public Cell (int number, char snake, int ladder, Player player) {
//		this.number = number;
//		this.snake = snake;
//		this.ladder = ladder;
//		this.player = player;
//	}
	
	public Cell () {
		
	}
	/**
	 * @return the number
	*/
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	/**
	 * @return the snake
	 */
	public char getSnake() {
		return snake;
	}
	/**
	 * @return the ladder
	 */
	public int getLadder() {
		return ladder;
	}
	/**
	 * @param snake the snake to set
	 */
	public void setSnake(char snake) {
		this.snake = snake;
	}
	/**
	 * @param ladder the ladder to set
	 */
	public void setLadder(int ladder) {
		this.ladder = ladder;
	}
	
	public boolean hasSnakeOrLadder() {
		if (snake == 0 && ladder == 0)
			return false;
		else 
			return true;
	}
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
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

	public void setLeft(Cell left) {
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
	
	public Player removePlayer (Player playerToRemove, Player current) {
		Player removedPlayer = null;
		if (playerToRemove == player) {
			removedPlayer = player;
			player = removedPlayer.getRight();
			if (removedPlayer.getRight() != null) {
				removedPlayer.getRight().setLeft(null);
			}			
			removedPlayer.setRight(null);
			removedPlayer.setLeft(null);
		}else if (current != null) {
			if(playerToRemove == current) {
				removedPlayer = current;
				current.setRight(removedPlayer.getRight());
				if (removedPlayer.getRight() != null) {
					removedPlayer.getRight().setLeft(current);
				}			
				removedPlayer.setRight(null);
				removedPlayer.setLeft(null);
			}else {
				removedPlayer = removePlayer (playerToRemove, current.getRight());
			}
		}
		return removedPlayer;
	}
	
	public void addPlayer (Player newPlayer, Player current) {
		 if (current == null) {
			 current = newPlayer;
		 }else if (current.getRight() == null){
			 current.setRight(newPlayer);
			 newPlayer.setLeft(current);
		 }else {
			 addPlayer(newPlayer, current.getRight());
		 }
	}
	
	public String cellToString() {
		if (snake != 0) 
			return "[" + number + snake + "\t]";
		else if (ladder != 0)
			return "[" + number + ladder + "\t]";
		else 
			return "[" + number + "\t]";
		
	}
	
	public String gameToString () {
		if (snake != 0) 
			return "[" + snake + playersToString () + "\t]";
		else if (ladder != 0)
			return "[" + ladder + playersToString () + "\t]";
		else if (player != null)
			return "[" + playersToString () + "\t]";
		else
			return "[\t]";
	}
	
	private String playersToString () {
		String playersString = "";
		if (player != null) {
			playersString = player.toString(player);
		}
		return playersString;
	}
}
