package main;

public class Bullet extends BoardPiece{
	
	public Bullet(int direction, String owner){
		setName("Bullet");
		setDirection(direction);
		setOwner(owner);
	}

	public int move(BoardPiece[][] board) {
		return getDirection();
	}

}
