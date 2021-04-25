package model;

public class Game {
	private Cell first;
	private int columns;
	private int rows;
	
	public Game (int columns, int rows, int snakes, int ladders, int players) {
		this.columns = columns;
		this.rows = rows;
		createGame();
	}
	
	public Game (int columns, int rows, int snakes, int ladders, String players) {
		this.columns = columns;
		this.rows = rows;
		createGame();
	}

	private void createGame() {
		first = new Cell (1);
		createRow(1,1,first);
	}
	
	private void createRow(int i, int j, Cell currentFirstRow) {
		
		if (i % 2 != 0) {
			createColumn(i,j++,currentFirstRow,currentFirstRow.getDown());
			if(i+1 < rows) {
				Cell upFirstRow = new Cell((i + 1) * j);
				upFirstRow.setDown(currentFirstRow);
				currentFirstRow.setUp(upFirstRow);
				createRow(i+1,j,upFirstRow);
			}
		}else {
			createColumn(i,j--,currentFirstRow,currentFirstRow.getDown());
			if(i+1 < rows) {
				Cell downFirstRow = new Cell((i + 1) * j);
				downFirstRow.setUp(currentFirstRow);
				currentFirstRow.setDown(downFirstRow);
				createRow(i++,j * columns,downFirstRow);
			}
		}
			
	}

	private void createColumn(int i, int j, Cell previous, Cell previuousRow) {
		if (i % 2 != 0) {
			if(j < columns) {
				Cell current = new Cell (i * j);
				current.setLeft(previous);
				previous.setRight(current);
				
				if(previuousRow!=null) {
					previuousRow = previuousRow.getRight();
					current.setUp(previuousRow);
					previuousRow.setDown(current);
				}
				
				createColumn(i,j++,current,previuousRow);
			}
		}else {
			if(j < columns) {
				Cell current = new Cell (i * j);
				current.setLeft(previous);
				previous.setRight(current);
				
				if(previuousRow!=null) {
					previuousRow = previuousRow.getRight();
					current.setUp(previuousRow);
					previuousRow.setDown(current);
				}
				
				createColumn(i,j--,current,previuousRow);
			}
		}

	}
//	
//	public String toString() {
//		String msg;
//		msg = toStringRow(first);
//		return msg;
//	}
//
//	private String toStringRow(Node firstRow) {
//		String msg = "";
//		if(firstRow!=null) {
//			msg = toStringCol(firstRow) + "\n";
//			msg += toStringRow(firstRow.getDown());
//		}
//		return msg;
//	}
//
//	private String toStringCol(Node current) {
//		String msg = "";
//		if(current!=null) {
//			msg = current.toString();
//			msg += toStringCol(current.getNext());
//		}
//		return msg;
//	}
//	
//	public String toString2() {
//		String msg = "";
//		
//		return msg;
//	}
}
