package main;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Sanchit extends BoardPiece {
	
	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public Sanchit (int pieceSize)
	{
		setName("Sanchit");
		setOwner(getName());
		try 
		{
			setImage(ImageIO.read(getClass().getResourceAsStream("/Images/"+getName()+".png")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		setImage(resizeImage(getImage(), pieceSize));
	}
	
	 /*This is your main method
	   The current board will be passed down to your class (locations of the other things on the board)
	   The board is a 2D array of 'BoardPieces'
	   Objects on the board will be:
	    - null(nothing there)
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
	 	14 15 16*/
	   

	public int move(BoardPiece[][] board) 
	{
		int myX = 0; int myY = 0;
	
			boolean found = false;
			for(int x = 0; x< board.length; x++)
			{					//Finding my x and y
				for(int y=0; y < board.length; y++)
				{
					if(board[x][y]!= null && board[x][y].getName().equals("Sanchit"))
					{
						myX = x;
						myY = y;
						found = true;
						break;
						
					}
				}
				if(found == true)
					break;
			}
			
			int go = 0;
			
			//if(board[26][0] !=  null)
			
			boolean threat1 = false;
			boolean threat2 = false;
			boolean threat3 = false;
			boolean threat4 = false;
			boolean threat5 = false;
			boolean threat6 = false;
			boolean threat7 = false;
			boolean threat8 = false;
			
			for(int x = myX; x < (myX+3); x++)
			{
				for(int y = myY; y < myY; y++)
				{
					if( x < 25 && x > 1 && y < 25 && y > 1)
					{
						if(board[myX+1][myY+1] != null || board[myX+2][myY+2] != null)
							go = 16;
						else if(board[myX-1][myY-1] != null || board[myX-2][myY-2] != null)
							go = 9;
						else if(board[myX][myY+1] != null || board[myX][myY+2] != null)
							go = 15;
						else if(board[myX][myY-1] != null || board[myX][myY-2] != null)
							go = 10;
						else if(board[myX+1][myY] != null || board[myX+2][myY] != null)
							go = 13;
						else if( board[myX-1][myY] != null || board[myX-2][myY] != null)
							go = 12;
						else if(board[myX+1][myY-1] != null || board [myX+2][myY-2] != null)
							go = 11;
						else if(board[myX-1][myY+1] != null || board[myX-2][myY+2] != null)
							go = 14;
						else 
							break;
					
					}
				if(board[x][y] != null && board[x][y].getName().equals("Bullet")) 
						{
					//BULLET APPROACHING IN 4 GENERAL DIRECTIONS:
					//The bullet is above my position in the same column and is moving	towards me (downwards)
					if(x == myX && y > myY && board[x][y].getvY() < 0)
						threat2 = true;
					//The bullet is below my position in the same column and is moving towards me (upwards)
					if(x == myX && y < myY && board[x][y].getvY() > 0)
						threat7 = true;
					//The bullet is to the right of my position in the same row and is moving towards me (left direction)
					if(y == myY && x > myX && board[x][y].getvX() < 0)
						threat5 = true;
					//The bullet is to the left of my position in the same row and is moving towards me (right direction)
					if(y == myY && x < myX && board[x][y].getvX() > 0)
						threat4 = true;
					
					//BULLET APPROACHING IN 4 DIAGONAL DIRECTIONS:
					
					//To verify the spots that are diagonal to my position
					int checkX = Math.abs(myX - x);
					int checkY = Math.abs(myY - y);
					boolean verify = false;
					
					if (checkX == checkY)
						verify = true;
					
					//The bullet is diagonal coming towards me from the top left
					if(verify == true && myX-x > 0 && myY-y > 0 && board[x][y].getvX() > 0 && board[x][y].getvY() > 0)
						threat1 = true;
					//The bullet is diagonal coming towards me from the bottom left
					if(verify == true && myX-x > 0 && myY-y < 0 && board[x][y].getvX() > 0 && board[x][y].getvY() < 0)
						threat6 = true;
					//The bullet is diagonal coming towards me from the top right
					if(verify == true && myX-x < 0 && myY-y > 0 && board[x][y].getvX() < 0 && board[x][y].getvY() > 0)
						threat3 = true;
					//The bullet is diagonal coming towards me from the bottom right
					if(verify == true && myX-x < 0 && myY-y < 0 && board[x][y].getvX() < 0 && board[x][y].getvY() < 0)
						threat8 = true;

					}
				break;
				}
				break;
			}
			
			//Only moving (a.k.a. acting) if the bullet is a threat to my current position
			if(threat2 == true || threat7 == true) //up or down
			{
				if (myX < 13)
					go = 5;
				else
					go = 4;
			}
			else if(threat4 == true || threat5 == true) //left or right
			{
				if (myY < 13)
					go = 7;
				else
					go = 2;
			}
			else if (threat6 == true) //bottom left
				go = 14;
			else if (threat1 == true) //top left
				go = 9;
			else if (threat8 == true) //bottom right
				go = 16;
			else if (threat3 == true) //top right
				go = 11;

			else if( myX < 25 && myX > 1 && myY < 25 && myY > 1)
					{
						if(board[myX+1][myY+1] != null || board[myX+2][myY+2] != null)
							go = 16;
						else if(board[myX-1][myY-1] != null || board[myX-2][myY-2] != null)
							go = 9;
						else if(board[myX][myY+1] != null || board[myX][myY+2] != null)
							go = 15;
						else if(board[myX][myY-1] != null || board[myX][myY-2] != null)
							go = 10;
						else if(board[myX+1][myY] != null || board[myX+2][myY] != null)
							go = 13;
						else if( board[myX-1][myY] != null || board[myX-2][myY] != null)
							go = 12;
						else if(board[myX+1][myY-1] != null || board [myX+2][myY-2] != null)
							go = 11;
						else if(board[myX-1][myY+1] != null || board[myX-2][myY+2] != null)
							go = 14;
						else
							go = rand (9,16);
					}
			else 
				go = rand(0,16);
			
			return go;

	}
}
