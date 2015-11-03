package main;

public class Bullet extends BoardPiece{

	// Value from 1 through 8 - does not change, as the bullet does not change direction
	private int direction;
	// Tracks who fired the bullet, so that the kills can be tracked
	private String owner;
	
	public Bullet(int direction, String owner){
		setName("bullet");
		this.direction = direction;
		this.owner = owner;
	}
	
	public int move(BoardPiece[][] board) {
		return direction;
	}

	public String getOwner() {
		return owner;
	}
}
