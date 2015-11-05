package main;

public class Bullet extends BoardPiece{
	
	public Bullet(int direction, String owner){
		setName("bullet");
		setDirection(direction);
		setOwner(owner);
	}

	public int move(BoardPiece[][] board) {
		return 0;
	}

}
