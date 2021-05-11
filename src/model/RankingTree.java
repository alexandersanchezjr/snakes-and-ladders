package model;

public class RankingTree {
	
	private Winner root;
	
	/**
	 * 
	 */
	public RankingTree() {

	}

	/**
	 * @return the root
	 */
	public Winner getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(Winner root) {
		this.root = root;
	}
	
	/**
	* Add a player which won the game.<br>
	* <b>pre:</b> The player score, columns, rows, snakes, ladders and number of players should not be zero. Also, symbols is not empty. <br>
	* <b>post:</b> Has added the player to the ranking binary tree. 
	* @param nickname The player nickname . nickname != "".
	* @param score The player score. score != 0.
	* @param columns The game columns.
	* @param rows The game rows.
	* @param snakes The game snakes. 
	* @param ladders The game ladders
	* @param players The number of players of the game.
	* @param symbols The symbols used in the game.
	*/
	public void addWinner(String nickname, int score, int columns, int rows, int snakes, int ladders, int players, String symbols) {
		Winner w = new Winner(nickname, score, columns, rows, snakes, ladders, players, symbols);
		if(root == null) {
			root = w;
		}else {
			addWinner(root, w);
		}
	}

	private void addWinner(Winner current, Winner newWinner) {
		if(newWinner.getScore() <= current.getScore()) {
			if(current.getRight() != null) {
				addWinner(current.getRight(), newWinner);
			}
			else {
				current.setRight(newWinner);
				newWinner.setParent(current);
			}
		} 
		else {
			if(current.getLeft() != null) {
				addWinner(current.getLeft(), newWinner);
			}
			else {
				current.setLeft(newWinner);
				newWinner.setParent(current);
			}
		}
	}
	
}
