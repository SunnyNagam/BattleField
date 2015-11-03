package main;

public abstract class BoardPiece {
	private String name = "piece";
	
	
	// This method is called by the Board to execute the piece's move, with a value from 1 through 16.
	// 1 through 8 indicate movement direction, 9 through 16 indicate firing direction
	// 1  2  3
	// 4     5
	// 6  7  8
	public abstract int move(BoardPiece[][] board);

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}