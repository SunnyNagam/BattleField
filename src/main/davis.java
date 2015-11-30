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

public class davis extends BoardPiece {
	
	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public davis(int pieceSize){
		setName("davis");
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
		// TODO put codez in here
		int x = 0;
		int y = 0;
		int [][]badspace = new int [27][27];
		int urY = 0;
		int urX = 0;
		int move = 0;
		for (x = 0; x < 26; x++)
		{			
			for (y = 0; y < 26; y++)
			{	
					if ( board[x][y].getName().equals("davis"))
					{
						urY = y;
						urX = x;
					}
			}
		}
		for (x = 0; x < 26; x++)
		{			
			for (y = 0; y < 26; y++){	
				{
					
					if (board[x][y].getName() != null && board[x][y].getName().equals("Bullet"))
					{
						badspace[x][y] = -1;
						
					}	
				} // brute force coding let's get it
			}
		}
			while(move == 0){
			if ( badspace[(urX - 1)][(urY-2)] != -1 && badspace[(urX + 2)][(urY+2)] != -1 && badspace[(urX - 1)][(urY-2)] != -1 && badspace[(urX - 1)][(urY+2)] != -1 && badspace[(urX - 2)][urY] != -1 && badspace[urX][urY+1] != -1 && badspace[urX][(urY-1)] != -1 && badspace[(urX + 1)][(urY-1)] != -1 && badspace[(urX + 1)][(urY+1)] != -1 && badspace[(urX + 3)][urY] != -1 && badspace[(urX + 1)][(urY+2)] != -1 && badspace[(urX + 1)][(urY - 2)] != -1 && badspace[(urX + 2)][urY] != -1 && badspace[(urX + 1)][(urY - 1)] != -1)  // if moving right does not equal bullet move right
			{
				move = 5;
			}
			else if (badspace[(urX)][(urY + 3)] != -1 && badspace[(urX + 2)][(urY + 2)] != -1 && badspace[(urX - 2)][(urY + 2)] != -1 && badspace[(urX + 2)][(urY + 1)] != -1 && badspace[(urX + 1)][(urY + 1)] != -1 && badspace[(urX + 2)][(urY + 1)] != -1 && badspace[(urX - 1)][(urY + 1)] != -1 && badspace[(urX + 2)][(urY)] != -1 && badspace[(urX - 2)][(urY)] != -1 && badspace[(urX + 1)][(urY)] != -1 && badspace[(urX - 1)][(urY)] != -1 && badspace[(urX)][(urY-2)] != -1 && badspace[(urX)][(urY-3)] != -1 && badspace[(urX)][(urY+2)] != -1 && badspace[(urX - 1)][(urY)] != -1 && badspace[(urX + 1)][(urY-2)] != -1 )
			{
			 move = 2;
			}
			else if( badspace[(urX + 1)][(urY-2)] != -1 && badspace[(urX - 2)][(urY+2)] != -1 && badspace[(urX + 1)][(urY-2)] != -1 && badspace[(urX + 1)][(urY+2)] != -1 && badspace[(urX + 2)][urY] != -1 && badspace[urX][urY+1] != -1 && badspace[urX][(urY-1)] != -1 && badspace[(urX - 1)][(urY-1)] != -1 && badspace[(urX + 1)][(urY+1)] != -1 && badspace[(urX + 3)][urY] != -1 && badspace[(urX + 1)][(urY+2)] != -1 && badspace[(urX + 1)][(urY - 2)] != -1 && badspace[(urX + 2)][urY] != -1 && badspace[(urX + 1)][(urY - 1)] != -1)
			{
			 move = 4;
			}
			else if (badspace[(urX)][(urY - 3)] != -1 && badspace[(urX + 2)][(urY - 2)] != -1 && badspace[(urX - 2)][(urY - 2)] != -1 && badspace[(urX + 2)][(urY - 1)] != -1 && badspace[(urX + 1)][(urY - 1)] != -1 && badspace[(urX - 2)][(urY - 1)] != -1 && badspace[(urX - 1)][(urY - 1)] != -1 && badspace[(urX + 2)][(urY)] != -1 && badspace[(urX - 2)][(urY)] != -1 && badspace[(urX + 1)][(urY)] != -1 && badspace[(urX - 1)][(urY)] != -1 && badspace[(urX)][(urY+2)] != -1 && badspace[(urX)][(urY+3)] != -1 && badspace[(urX)][(urY+2)] != -1 && badspace[(urX - 1)][(urY)] != -1 && badspace[(urX + 1)][(urY+2)] != -1 )
			{
				move = 7;
			}
			else{ 
				move = ((int)((Math.random()*16+9)));
				}
			}
			
return move;
}
}