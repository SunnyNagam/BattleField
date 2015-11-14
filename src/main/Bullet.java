package main;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends BoardPiece{
	
	public Bullet(int x, int y, String owner, int ownInd, int pieceSize){
		setName("Bullet");
		setvX(x);
		setvY(y);
		setOwner(owner);
		try {
			setImage(ImageIO.read(getClass().getResourceAsStream("/Images/bullet.png")));	// Setting the boardpiece's image
		}
		catch (IOException e){
			e.printStackTrace();
		}
		setImage(resizeImage(getImage(), pieceSize));				// Resizing to fit dimensions
	}

	public int move(BoardPiece[][] board) {
		return 0;
	}

}
