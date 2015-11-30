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

public class DaFangsta extends BoardPiece 
	{
	
	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public DaFangsta(int pieceSize){
		setName("DaFangsta");
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
	    - "Bullet"
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
	/*public int self(BoardPiece[][] board)
	{
		boolean found = false;
		for (int x=0; x<board.length; x++)
			for (int y=0; y<board.length; y++)
			{
				if(board[x][y]!= null && board[x][y].getName()==getName())
				{
					found = true;
					break;
				}
			}
			
				
	}*/
	public int move(BoardPiece[][] board)
	{
		//return rand(9,16);
		int x=0;
		int y=0;
		int shoot = 0;
		boolean found = false;
		for (x=0; x<board.length; x++)
		{
			for (y=0; y<board.length; y++)
			{
				if(board[x][y]!= null && board[x][y].getName().equals("DaFangsta"))
				{
					found = true;
					break;
				}
			}
			if (found == true)break;
		}
		
		
		//if(x < 24 && x > 2 && y < 24 && y > 2)
		
			for (int checkX = x; checkX < (x+3); checkX++)
			{
				for (int checkY = y; checkY < (y+3); checkY++)
				{
					try
					{
						if (board[checkX-1][checkY]!= null && board[checkX-1][checkY].getName()!="DaFangsta")
						{
							shoot = 12;
						}
						else if (board[checkX-1][checkY+1] != null && board[checkX-1][checkY+1].getName()!="DaFangsta")
						{
							shoot = 14;
						}
						else if (board[checkX-1][checkY-1] != null && board[checkX-1][checkY-1].getName()!="DaFangsta")
						{
							shoot = 9;
						}
						else if (board[checkX][checkY+1] != null && board[checkX][checkY+1].getName()!="DaFangsta")
						{
							shoot = 15;
						}
						else if (board[checkX][checkY-1] != null && board[checkX][checkY-1].getName()!="DaFangsta")
						{
							shoot = 10;
						}
						else if (board[checkX+1][checkY] != null && board[checkX+1][checkY].getName()!="DaFangsta")
						{
							shoot = 13;
						}
						else if (board[checkX+1][checkY+1] != null && board[checkX+1][checkY+1].getName()!="DaFangsta")
						{
							shoot = 16;
						}
						else if (board[checkX+1][checkY-1] != null && board[checkX+1][checkY-1].getName()!="DaFangsta")
						{
							shoot = 11;
						}
						if (board[checkX-2][checkY] != null && board[checkX-2][checkY].getName()!="DaFangsta")
						{
							shoot = 12;
						}
						else if (board[checkX-2][checkY+2] != null && board[checkX-2][checkY+2].getName()!="DaFangsta")
						{
							shoot = 14;
						}
						else if (board[checkX-2][checkY-2] != null && board[checkX-2][checkY-2].getName()!="DaFangsta")
						{
							shoot = 9;
						}
						else if (board[checkX][checkY+2] != null && board[checkX][checkY+2].getName()!="DaFangsta")
						{
							shoot =  15;
						}
						else if (board[checkX][checkY-2] != null && board[checkX][checkY-2].getName()!="DaFangsta")
						{
							shoot = 10;
						}
						else if (board[checkX+2][checkY] != null && board[checkX+2][checkY].getName()!="DaFangsta")
						{
							shoot = 13;
						}
						else if (board[checkX+2][checkY+2] != null && board[checkX+2][checkY+2].getName()!="DaFangsta")
						{
							shoot = 16;
						}
						else if (board[checkX+2][checkY-2] != null && board[checkX+2][checkY-2].getName()!="DaFangsta")
						{
							shoot = 11;
						}
						else
							shoot = rand(9,16);
					}
					catch (Exception e)
					{
						
					}
				}
			}
			
		
		
			//shoot = 0;
				
		
		return shoot;
				
	}
	
	
	
	
	
	
	
	
	
	
	
	

}