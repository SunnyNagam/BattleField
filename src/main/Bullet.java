package main;

public class Bullet extends BoardPiece{
	
	public Bullet(int x, int y, String owner, int ownInd){
		setName("Bullet");
		setvX(x);
		setvY(y);
		setOwner(owner);
	}

	public int move(BoardPiece[][] board) {
		return 0;
	}

}
