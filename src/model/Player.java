package model;

public class Player {
	
	private char symbol;
	private int cont;
	private int score;
	private int cellNumber;
	private Player right;
	private Player left;
	
	
	/**
	 * @param symbol
	 * @param cont
	 */
	public Player(char symbol) {
		this.setSymbol(symbol);
		cont = 0;
		setScore(0);
		cellNumber = 1;
	}

	/**
	 * @return the symbol
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the cont
	 */
	public int getCont() {
		return cont;
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
	public void setScore(int size) {
		score = cont*size;
	}

	/**
	 * @return the cellNumber
	 */
	public int getCellNumber() {
		return cellNumber;
	}

	/**
	 * @param cellNumber the cellNumber to set
	 */
	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
	}

	/**
	 * @return the right
	 */
	public Player getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(Player right) {
		this.right = right;
	}

	/**
	 * @return the left
	 */
	public Player getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(Player left) {
		this.left = left;
	}

	public void increaseCont() {
		cont++;
	}

}
