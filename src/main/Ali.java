/*	
	PLAYER AI TEMPLATE CLASS
	
	This is the template class for creating your AI for the battlefield game!
	Please include a png file (of any size) preferably, a 30x30 pixel image.
	Note: Your image must be a square, if it is not it will be squished.
	
	RULES AND INFO:
	 - Wherever you see 'BoardPieceTemplate', replace with your first name (control f and REPLACE)
	 - If your code returns/encounters an error, your player will be automatically removed from the game
	 - Do not attempt to code anything that does not involve your player
	 - You are allowed to add as much code inside and outside of your main method
	 
	 If you need help, or troubleshooting, consult Sunny or Wesley
*/
package main;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Ali extends BoardPiece {
	
	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public Ali(int pieceSize){
		setName("Ali");
		setOwner(getName());
		try {
			setImage(ImageIO.read(getClass().getResourceAsStream("/Images/"+getName()+".png")));
		}
		catch (IOException e){
			e.printStackTrace();
		}
		setImage(resizeImage(getImage(), pieceSize));
	}
	
	/* This is your main method
	   The current board will be passed down to your class (locations of the other things on the board)
	   The board is a 2D array of 'BoardPieces'
	   Objects on the board will be:
	    - null (nothing there)
	    - "bullet"
	    - other players represented by their first names (ex. "Bob")
	    	For example if Bob is standing in coordinates (2,5) then board[2][5].getName() will give you the name "Bob"
	    	if nothing is in coordinates (2,5) then board[2][5] will be null
	    	if a bullet is in coordinates (2,5) then board[2][5].getName() will give you the name "Bullet"
	    Your method must return an integer value from 0 to 16, inclusive.
	    1 through 8 indicate movement direction, 9 through 16 indicate firing direction, 0 does nothing
	    
	    MOVE
	 	1  2  3
	 	4  0  5
	 	6  7  8
	 	
	 	FIRE
	 	9  10 11
	 	12    13
	 	14 15 16
	  
	 */
	public int move(BoardPiece[][] board) {
		
		int myX = 0; 
		int myY = 0; 
		
		//Finding ourselves
		for(int x = 0; x <board.length; x++)
			for(int y = 0 ; y < board.length; y++)
			{
				if(board[x][y] != null && board[x][y].getName().equals("Ali"))
				{
					myX = x;
					myY = y;
				}
			}

			
			
			
			
		// TODO put codez in here
		//Finding the nearest bullet
		int nearestBulletX, nearestBulletY; 
		double nearestBulletDistance = 1000; //just a large number to start
		boolean threat1 = false;
		boolean threat2 = false;
		boolean threat3 = false;
		boolean threat4 = false;
		
	

		int turn = 0;
		for(int x = 0; x <board.length; x++)
			for(int y = 0 ; y < board.length; y++)
			{
				if(board[x][y] != null && board[x][y].getName().equals("Bullet"))
				{//Found a bullet
					
					//deciding if the bullet is threat
					if(x == myX && y > myY && board[x][y].getvY() < 0)//bullet is above you and moving down
						threat1 = true;
					if(x == myX && y < myY && board[x][y].getvY() > 0)//bullet is below you and moving up
						threat2 = true;
					if(y == myY && x > myX && board[x][y].getvX() < 0)//bullet is to your right
						threat3 = true;
					if(y == myY && x < myX && board[x][y].getvX() > 0)//bullet is to your left
						threat4 = true;
					
					//look for bullet approaching you diagonally
//					if(Math.abs(myX - x) == Math.abs(myY - y) && myX-x > 0 && myY-y < 0 && board[x][y].getvX() > 0 && board[x][y].getvY() < 0)//find bullets diagonal coming towards you from top left corner
//						threat5 = true;
//					if(Math.abs(myX - x) == Math.abs(myY - y) && myX-x > 0 && myY-y > 0 && board[x][y].getvX() > 0 && board[x][y].getvY() > 0)//find bullets diagonal coming towards you
///						threat6 = true;
//					if(Math.abs(myX - x) == Math.abs(myY - y) && myX-x < 0 && myY-y < 0 && board[x][y].getvX() < 0 && board[x][y].getvY() < 0)//find bullets diagonal coming towards you
//						threat7 = true;
//					if(Math.abs(myX - x) == Math.abs(myY - y) && myX-x < 0 && myY-y > 0 && board[x][y].getvX() < 0 && board[x][y].getvY() > 0)//find bullets diagonal coming towards you
//						threat8 = true;
					
				
					
					if(threat1 == true) // only recording the bullet if it is a threat to you
					{
						turn = 4;
						
						
//						int base = myX-x;
//						int height = myY - y;
//						double distance = Math.sqrt(base*base + height*height);//pythagoren theorem
//						if(nearestBulletDistance > distance)
//						{
//							nearestBulletDistance = distance;
//							nearestBulletX = x;
//							nearestBulletY = y;
//						}
					}
					else if (threat2 == true)//if there is a bullet approching you from below
					{
						turn = 4;
					}
					else if (threat3 == true)//if there is a bullet approching from right
					{
						turn = 2;
					}
					else if (threat4 == true)//if there is a bullet to your left
					{
						turn = 2;
					}
					else
						turn = rand(9,16);//if there is no threat, shoot randomly diagonal and right 
				}
			}
			
			
			
			
		return turn;			// return number in between 0 and 16
	}
	
	public static void findBullet(int [][] board)
	{
		 boolean user = false;
			{
				
			}
	}

}