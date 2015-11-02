package main;

public class Bullet extends BoardPiece{

	// Value from 1 through 8 - does not change, as the bullet does not change direction
	private int direction;
	
	public Bullet(int direction){
		this.direction = direction;
	}
	
	public int move(BoardPiece[][] board) {
		return direction;
	}
}
