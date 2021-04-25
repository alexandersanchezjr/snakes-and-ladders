package model;

import java.util.Random;

public class Game {
	private Cell first;
	private int columns;
	private int rows;
	
	Random random = new Random();

	
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
		includeSnakes(snakes);
		includeLadders(ladders);
	}
	

	private void createRow(int i, int j, Cell currentFirstRow) {
		
		if (i % 2 != 0) {
			createColumn(i,j++,currentFirstRow,currentFirstRow.getDown());
			if(i+1 < rows) {
				Cell upFirstRow = new Cell((i + 1) * j);
				upFirstRow.setDown(currentFirstRow);
				currentFirstRow.setUp(upFirstRow);
				createRow(i++,j,upFirstRow);
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
	
	private void includeSnakes(int snakes) {
		if (snakes == 1) {
			int numberToSearch = random.ints(1, ((rows * columns)+1)).findFirst().getAsInt();
			Cell cell = searchCell(numberToSearch);
			if (!cell.hasSnakeOrLadder()) {
				
			}else
				includeSnakes(snakes);
		}
			
	}
	
	private Cell searchCell (int cellNumber) {		
		return searchCellInRow(1, 1, cellNumber, first);
	}

	private Cell searchCellInRow (int i, int j, int cellNumber, Cell currentFirstRow) {
		Cell cell = null;
		if (currentFirstRow.getNumber() == cellNumber)
			cell = currentFirstRow;
		else {
			cell = searchCellInColumn(i, j++, cellNumber, currentFirstRow.getRight());
			if(i++ < rows && cell == null) {				
				cell = searchCellInRow(i++, j, cellNumber, currentFirstRow.getUp());
			}
		}
		
		return cell;
	}

	private Cell searchCellInColumn(int i, int j, int cellNumber, Cell currentColumn) {
		Cell cell = null;
		if (currentColumn != null) {
			if (currentColumn.getNumber() == cellNumber)
				cell = currentColumn;
			else if(j < columns && currentColumn.getRight() !=null) {
				cell = searchCellInColumn(i, j++, cellNumber, currentColumn.getRight());
			}
		}
		return cell;
		
	}
	
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
