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
	
	public void addWinner(String nickname, int score, int columns, int rows, int snakes, int ladders, int players, String symbols) {
		Winner w = new Winner(nickname, score, columns, rows, snakes, ladders, players, symbols);
		if(root == null) {
			root = w;
			System.out.println("Primer Ganador agregado ");
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
				System.out.println(newWinner.getNickname() + " ha sido agregado al arbol por derecha de ganadores");
			}
		} 
		else {
			if(current.getLeft() != null) {
				addWinner(current.getLeft(), newWinner);
			}
			else {
				current.setLeft(newWinner);
				newWinner.setParent(current);
				System.out.println(newWinner.getNickname() + " ha sido agregado al arbol por izquierda de ganadores");

			}
		}
	}
	
	public String toString() {
		String msg = "";
		if(root == null) {
			msg = "No hay jugadores ganadores aún";
		}
		else {
			System.out.println("Raiz: " + root);
			msg = root.toString(root);
		}
		return msg;
	}
	
}
