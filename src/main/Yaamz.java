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

public class Yaamz extends BoardPiece {
	
	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public Yaamz(int pieceSize){
		setName("Yaamz");
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
	public int move(BoardPiece[][] board) 
	{
			int x =0, y=0;
			int move = 0;
			
			boolean found = false;
			for(; x<board.length; x++)
			{					//Finding my x and y
				for(y=0; y<board[x].length; y++)
				{
					if(board[x][y]!= null && board[x][y].getName()=="Yaamz")
					{
						found = true;
						break;
					}
				}
				if(found == true)
					break;
			}
			for(int checkx = x; checkx < (x+3); checkx++)
				for(int checky = y; checky < (y+3); checky++)
				{
					try{
						
						//
						if (board [checkx+1][checky-1] != null && board[checkx+1][checky-1].getName()=="Bullet")
							
						{
							move = 5;
						}
						
						else if (board [checkx+1][checky+1] != null && board[checkx+1][checky+1].getName()=="Bullet")
						{
							move = 5;
							
						}
					
						else if (board [checkx-1][checky+1] != null && board[checkx-1][checky+1].getName()=="Bullet")
						{
							move = 2;
						}
						
						else if (board [checkx-1][checky-1] != null && board[checkx-1][checky-1].getName()=="Bullet")
						{
							move = 2;
						}
						
						else if (board [checkx-1][checky] != null  &&board[checkx-1][checky].getName()!="Yaamz")
						{
							move = 12;
						}
							
						else if (board [checkx+1][checky] != null && board[checkx+1][checky].getName()!="Yaamz")
						{
							move = 13;
							
						}
						
						else if (board [checkx][checky-1] != null && board[checkx][checky-1].getName()!="Yaamz")
						{
							//System.out.println(board [checkx][checky-1].getName());
							move = 10;
						} 
						else if (board [checkx][checky+1] != null && board[checkx][checky+1].getName()!="Yaamz")
						{
							move = 15;
						}
						//
						else if (board [checkx+2][checky-2] != null && board[checkx+2][checky-2].getName()=="Bullet")
							
						{
							move = 5;
						}
						
						else if (board [checkx+2][checky+2] != null && board[checkx+2][checky+2].getName()=="Bullet")
						{
							move = 5;
							
						}
					
						else if (board [checkx-2][checky+2] != null && board[checkx-2][checky+2].getName()=="Bullet")
						{
							move = 2;
						}
						
						else if (board [checkx-2][checky-2] != null && board[checkx-2][checky-2].getName()=="Bullet")
						{
							move = 2;
						}
						
						else if (board [checkx-2][checky] != null  &&board[checkx-2][checky].getName()!="Yaamz")
						{
							move = 12;
						}
							
						else if (board [checkx+2][checky] != null && board[checkx+2][checky].getName()!="Yaamz")
						{
							move = 13;
							
						}
						
						else if (board [checkx][checky-2] != null && board[checkx][checky-2].getName()!="Yaamz")
						{
							//System.out.println(board [checkx][checky-2].getName());
							move = 10;
						} 
						else if (board [checkx][checky+2] != null && board[checkx][checky+2].getName()!="Yaamz")
						{
							move = 15;
						}
//
						else if (board [checkx-3][checky] != null  &&board[checkx-3][checky].getName()!="Yaamz")
						{
							move = 12;
						}
							
						else if (board [checkx+3][checky] != null && board[checkx+3][checky].getName()!="Yaamz")
						{
							move = 13;
							
						}
						
						else if (board [checkx][checky-3] != null && board[checkx][checky-3].getName()!="Yaamz")
						{
							//System.out.println(board [checkx][checky-3].getName());
							move = 10;
						} 
						else if (board [checkx][checky+3] != null && board[checkx][checky+3].getName()!="Yaamz")
						{
							move = 15;
						}
						//
						
						else if (board [checkx-4][checky] != null  &&board[checkx-4][checky].getName()!="Yaamz")
						{
							move = 12;
						}
							
						else if (board [checkx+4][checky] != null && board[checkx+4][checky].getName()!="Yaamz")
						{
							move = 13;
							
						}
						
						else if (board [checkx][checky-4] != null && board[checkx][checky-4].getName()!="Yaamz")
						{
							//System.out.println(board [checkx][checky-4].getName());
							move = 10;
						} 
						else if (board [checkx][checky+4] != null && board[checkx][checky+4].getName()!="Yaamz")
						{
							move = 15;
						}
						//
						
						
						else if (board [checkx-5][checky] != null  &&board[checkx-5][checky].getName()!="Yaamz")
						{
							move = 12;
						}
							
						else if (board [checkx+5][checky] != null && board[checkx+5][checky].getName()!="Yaamz")
						{
							move = 13;
							
						}
						
						else if (board [checkx][checky-5] != null && board[checkx][checky-5].getName()!="Yaamz")
						{
							//System.out.println(board [checkx][checky-5].getName());
							move = 10;
						} 
						else if (board [checkx][checky+5] != null && board[checkx][checky+5].getName()!="Yaamz")
						{
							move = 15;
						}
						//
						
						else if (board [checkx-6][checky] != null  &&board[checkx-6][checky].getName()!="Yaamz")
						{
							move = 12;
						}
							
						else if (board [checkx+6][checky] != null && board[checkx+6][checky].getName()!="Yaamz")
						{
							move = 13;
							
						}
						
						else if (board [checkx][checky-6] != null && board[checkx][checky-6].getName()!="Yaamz")
						{
							//System.out.println(board [checkx][checky-6].getName());
							move = 10;
						} 
						else if (board [checkx][checky+6] != null && board[checkx][checky+6].getName()!="Yaamz")
						{
							move = 15;
						}
						else 
							move = rand (9,16);
					}catch (Exception e)
					{
						
					}
				}
			return move;
	}
}