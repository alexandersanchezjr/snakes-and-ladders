package model;

public class Player {
	
	private char symbol;
	private int cont;
	private Player right;
	
	
	/**
	 * @param symbol
	 * @param cont
	 */
	public Player(char symbol) {
		this.setSymbol(symbol);
		cont = 0;
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

	public void increaseCont() {
		cont++;
	}
	
	public String toString (Player right) {
		String message = "";
		if (right.getRight() == null)
			message += right.getSymbol();
		else 
			message = right.getSymbol() + toString(right.getRight());
		return message;
	}
}
