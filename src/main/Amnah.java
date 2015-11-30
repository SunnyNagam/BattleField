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
import javax.jws.soap.SOAPBinding;

public class Amnah extends BoardPiece {
	
	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public Amnah(int pieceSize){
		setName("Amnah");
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
/*	public int bestMove(
		Boardpiece[][] board, 
		boolean threatL,
		boolean threatUL,
		boolean threatDL,
		boolean threatU,
		boolean threatD,
		boolean threatR,
		boolean threatUR,
		boolean threatDR,
		int act)
	
		int worstMove = 0;
		
		if(threatL == true)
			worstMove = 12;
		if
		
		return act;
	}*/

	public int move(BoardPiece[][] board) 
	{
		// TODO put codez in here
		int selfPosX = 0;
		int selfPosY = 0;
		boolean positionFound = false;
		int act = 0;
		
		for(int x = 0; x < 26; x++)
		{
			for(int y = 0; y < 26; y++)
			{
				if(board[x][y] != null && board[x][y].getName().equals("Amnah"))
				{
					selfPosX = x;
					selfPosY = y;
		
					positionFound = true; 
				}
				if (positionFound == true)
					break;
			}
			if (positionFound == true)
				break;
		}
		
		int bulletX = 0;
		int bulletY = 0;
		
		//Regular threats
		boolean threatL = false;
		boolean threatUL = false;
		boolean threatDL = false;
		boolean threatU = false;
		boolean threatD = false;
		boolean threatR = false;
		boolean threatUR = false;
		boolean threatDR = false;
		
		//Main threats
		boolean mainThreatL = false;
		boolean mainThreatUL = false;
		boolean mainThreatDL = false;
		boolean mainThreatU = false;
		boolean mainThreatD = false;
		boolean mainThreatR = false;
		boolean mainThreatUR = false;
		boolean mainThreatDR = false;
		
		for(int x = 0; x < board.length; x++)
		{
			for(int y = 0; y < board.length; y++)
			{
				if(board[x][y] != null)
				{	
					if(board[x][y].getName().equals("Bullet"))
					{
						//if the bullet is coming from the left side
						if(x < selfPosX && y == selfPosY && board[x][y].getvX() > 0)
								threatL = true;
						
						//if bullet is coming from right side
						if(x > selfPosX && y == selfPosY && board[x][y].getvX() < 0)
								threatR = true;
						
						//if bullet is coming from above
						if(x == selfPosX && y < selfPosY && board[x][y].getvY() > 0)
								threatU = true;
						
						//if bullet is coming from below
						if(x == selfPosX && y > selfPosY && board[x][y].getvX() < 0)
								threatD = true;
						
						//if bullet is coming from the upper left
						if(x < selfPosX && y < selfPosY && (board[x][y].getvX() > 0 && board[x][y].getvY() > 0))
								threatUL = true;
						
						//if bullet is coming from down left
						if(x < selfPosX && y > selfPosX && (board[x][y].getvX() > 0 && board[x][y].getvY() < 0))
								threatDL = true;
						
						//if bullet is coming from upper right side
						if(x > selfPosX && y < selfPosY && (board[x][y].getvX() < 0 && board[x][y].getvY() > 0))
								threatUR = true;
						
						//if bullet is coming from down right side
						if(x > selfPosX && y > selfPosY && (board[x][y].getvX() < 0 && board[x][y].getvY() < 0))
								threatDR = true;
					}
					
					else
					{
						//if the person is coming from the left side
						if(x < selfPosX && y == selfPosY)
								act = 12;
						
						//if person is coming from right side
						if(x > selfPosX && y == selfPosY)
								act = 13;
						
						//if person is coming from above
						if(x == selfPosX && y < selfPosY)
								act = 10;
						
						//if person is coming from below
						if(x == selfPosX && y > selfPosY)
								act = 15;
						
						//if person is coming from the upper left
						if(x < selfPosX && y < selfPosY)
								act = 9;
						
						//if person is coming from down left
						if(x < selfPosX && y > selfPosX)
								act = 14;
						
						//if person is coming from upper right side
						if(x > selfPosX && y < selfPosY)
								act = 11;
						
						//if person is coming from down right side
						if(x > selfPosX && y > selfPosY)
								act = 16;
					}
				}
			}
		}
		
		//Main threats
		//if the bullet is coming from the left side
		if((board[selfPosX - 1][selfPosY] != null && board[selfPosX - 1][selfPosY].getvX() >= 0) || (board[selfPosX - 2][selfPosY] != null && board[selfPosX - 2][selfPosY].getvX() >= 0))
		{
			mainThreatL = true;
			act = rand(1,8);
		}
		
		//if bullet is coming from right side
		if((board[selfPosX + 1][selfPosY] != null && board[selfPosX + 1][selfPosY].getvX() <= 0) || (board[selfPosX + 2][selfPosY] != null && board[selfPosX + 2][selfPosY].getvX() <= 0))
		{
			mainThreatR = true;
			act = rand(1,8);
		}		
		//if bullet is coming from above
		if((board[selfPosX][selfPosY - 1] != null && board[selfPosX][selfPosY - 1].getvY() >= 0) || (board[selfPosX][selfPosY - 2] != null && board[selfPosX][selfPosY - 2].getvY() >= 0))
		{
			mainThreatU = true;
			act = rand(1,8);
		}		
		//if bullet is coming from below
		if((board[selfPosX][selfPosY + 1] != null && board[selfPosX][selfPosY + 1].getvY() <= 0) || (board[selfPosX][selfPosY + 2] != null && board[selfPosX][selfPosY + 2].getvY() <= 0))
		{
			mainThreatD = true;
			act = rand(1,8);
		}		
		//if bullet is coming from the upper left
		if((board[selfPosX - 1][selfPosY - 1] != null && (board[selfPosX - 1][selfPosY - 1].getvX() > 0 && board[selfPosX - 1][selfPosY - 1].getvY() > 0)) || (board[selfPosX - 2][selfPosY - 2] != null && (board[selfPosX - 2][selfPosY - 2].getvX() > 0 && board[selfPosX - 2][selfPosY - 2].getvY() > 0)))
		{
			mainThreatUL = true;
			act = rand(1,8);
		}		
		//if bullet is coming from down left
		if((board[selfPosX - 1][selfPosY + 1] != null && (board[selfPosX - 1][selfPosY + 1].getvX() > 0 && board[selfPosX - 1][selfPosY + 1].getvY() < 0)) || (board[selfPosX - 2][selfPosY + 2] != null && (board[selfPosX - 2][selfPosY + 2].getvX() > 0 && board[selfPosX - 2][selfPosY + 2].getvY() < 0)))
		{
			mainThreatDL = true;
			act = rand(1,8);
		}		
		//if bullet is coming from upper right side
		if((board[selfPosX + 1][selfPosY - 1] != null && (board[selfPosX + 1][selfPosY - 1].getvX() < 0 && board[selfPosX + 1][selfPosY - 1].getvY() > 0)) || (board[selfPosX + 2][selfPosY - 2] != null && (board[selfPosX + 2][selfPosY - 2].getvX() < 0 && board[selfPosX + 2][selfPosY - 2].getvY() > 0)))
		{
			mainThreatUR = true;
			act = rand(1,8);
		}		
		//if bullet is coming from down right side
		if((board[selfPosX + 1][selfPosY + 1] != null && (board[selfPosX + 1][selfPosY + 1].getvX() < 0 && board[selfPosX + 1][selfPosY + 1].getvY() < 0)) || (board[selfPosX + 2][selfPosY + 2] != null && (board[selfPosX + 2][selfPosY + 2].getvX() < 0 && board[selfPosX + 2][selfPosY + 2].getvY() < 0)))
		{
			mainThreatDR = true;
			act = rand(1,8);
		}					
		
		//if the person is coming from the left side
		if(threatL == true)
				act = 12;
		
		//if person is coming from right side
		if(threatR == true)
				act = 13;
		
		//if person is coming from above
		if(threatU == true)
				act = 10;
		
		//if person is coming from below
		if(threatD == true)
				act = 15;
		
		//if person is coming from the upper left
		if(threatUL == true)
				act = 9;
		
		//if person is coming from down left
		if(threatDL == true)
				act = 14;
		
		//if person is coming from upper right side
		if(threatUR == true)
				act = 11;
		
		//if person is coming from down right side
		if(threatDR == true)
				act = 16;
		
		return act; 
	}

}