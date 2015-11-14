package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class BoardPiece {
	private String name = "piece";
	// The picture of the boardpiece
	private BufferedImage image;
	
	// Bullet specific vars
	// Tracks who fired the bullet, so that the kills can be tracked
	private String owner;
	
	// The number of kills scored by this class (if player)
	private int kills = 0;
	// Value from 1 through 8 - does not change, as the bullet does not change direction
	private int vX = 0, vY = 0;		//Bullet's velocity in x and y direction
	// This method is called by the Board to execute the piece's move, with a value from 1 through 16.
	// 1 through 8 indicate movement direction, 9 through 16 indicate firing direction
	// 1  2  3
	// 4     5
	// 6  7  8
	/// non cannon
	// -2  0  2
	// -1     1
	// -2  0  2
	public abstract int move(BoardPiece[][] board);
	
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public String getOwner() {
		return owner;
	}
	public int rand(double d, double e){
		return (int) (d + (int)(Math.random()*((e-d)+1)));
	}
	
	public int getvX() {
		return vX;
	}
	public void setvX(int vX) {
		this.vX = vX;
	}
	public int getvY() {
		return vY;
	}
	public void setvY(int vY) {
		this.vY = vY;
	}
	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void incrementKills(){
		kills++;
	}
	// Resizing images to certain dimensions
	public static BufferedImage resizeImage(BufferedImage img, int size) { 
	    Image tmp = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
}