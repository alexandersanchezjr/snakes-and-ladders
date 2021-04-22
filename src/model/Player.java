package model;

public class Player {
	
	private char symbol;
	private int cont;
	
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
	
	public void increaseCont() {
		cont++;
	}
	
	
}
