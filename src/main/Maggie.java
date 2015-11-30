package main;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Maggie extends BoardPiece {
	
	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public Maggie (int pieceSize)
	{
		setName("Maggie");
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
	
	/* This is your main method
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
	 	14 15 16
	  
	 */
	
	public int shooting(BoardPiece[][] board, int myX, int myY, int act)
	{
		if((board[myX+1][myY+1] != null && !board[myX+1][myY+1].getName().equals("Bullet")) || (board[myX+2][myY+2] != null && !board[myX+2][myY+2].getName().equals("Bullet"))) // || board[myX+3][myY+3] != null)
			act = 16;
		else if((board[myX-1][myY-1] != null && !board[myX-1][myY-1].getName().equals("Bullet")) || (board[myX-2][myY-2] != null && !board[myX-2][myY-2].getName().equals("Bullet")))// || board[myX-3][myY-3] != null)
			act = 9;
		else if((board[myX][myY+1] != null && !board[myX][myY+1].getName().equals("Bullet")) || (board[myX][myY+2] != null && !board[myX][myY+2].getName().equals("Bullet")))// || board[myX][myY+3] != null)
			act = 15;
		else if((board[myX][myY-1] != null && !board[myX][myY-1].getName().equals("Bullet"))|| (board[myX][myY-2] != null && !board[myX][myY-2].getName().equals("Bullet")))// || board[myX][myY-3] != null)
			act = 10;
		else if((board[myX+1][myY] != null && !board[myX+1][myY].getName().equals("Bullet")) || (board[myX+2][myY] != null && !board[myX+2][myY].getName().equals("Bullet")))// || board[myX+3][myY] != null)
			act = 13;
		else if((board[myX-1][myY] != null && !board[myX-1][myY].getName().equals("Bullet")) || (board[myX-2][myY] != null && !board[myX-2][myY].getName().equals("Bullet")))// || board[myX-3][myY] != null)
			act = 12;
		else if((board[myX+1][myY-1] != null && !board[myX+1][myY-1].getName().equals("Bullet")) || (board [myX+2][myY-2] != null && !board [myX+2][myY-2].getName().equals("Bullet")))// || board[myX+3][myY-3] != null)
			act = 11;
		else if((board[myX-1][myY+1] != null && !board[myX-1][myY+1].getName().equals("Bullet")) || (board[myX-2][myY+2] != null && !board[myX-2][myY+2].getName().equals("Bullet")))// || board[myX-3][myY+3] != null)
			act = 14;
		else
			act = rand(9, 16);
		
		return act;
	}

	public int move(BoardPiece[][] board) 
	{
		//Variables to record my position
				int myX = 0; 
				int myY = 0;
				boolean found = false;

				//To find my x and y position on the board
				for(int x = 0; x< board.length; x++)
				{					//Finding my x and y
					for(int y=0; y < board.length; y++)
					{
						if(board[x][y]!= null && board[x][y].getName().equals("Maggie"))
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
				
				//Variables to record possible threats in all spots around my position
				boolean threat1 = false;
				boolean threat2 = false;
				boolean threat3 = false;
				boolean threat4 = false;
				boolean threat5 = false;
				boolean threat6 = false;
				boolean threat7 = false;
				boolean threat8 = false;
				
				//Variables to record possible threats in spots close to my position (1-2 spots away)
				boolean mainThreat1 = false;
				boolean mainThreat2 = false;
				boolean mainThreat3 = false;
				boolean mainThreat4 = false;
				boolean mainThreat5 = false;
				boolean mainThreat6 = false;
				boolean mainThreat7 = false;
				boolean mainThreat8 = false;
				
				//Variable to record what to do
				int act = 0;
				
				//Sending to a method to shoot if something in the region of my position
				//if(myX < 24 && myX > 2 && myY < 24 && myY > 2)
					//act = shooting(board, myX, myY, act);
				
				//Checking for threats (bullets in the region of my position)
				for(int x = 0; x <board.length; x++)
				{
					for(int y = 0; y < board.length; y++)
					{
						//Finding the bullet
						if(board[x][y] != null && board[x][y].getName().equals("Bullet")) 
						{
							//THIS IS ALL TO DETERMINE IF THE BULLET IS A THREAT TO ME, BASED ON MY CURRENT POSITION
							
							//BULLET APPROACHING IN 4 GENERAL DIRECTIONS:
							//The bullet is above my position in the same column and is moving	towards me (downwards)
							if(x == myX && y > myY && board[x][y].getvY() < 0)
							{
								threat2 = true;
								
								if((x < (myX+3) || x > (myX-3)) && (y < (myY+3) || y > (myY-3)) && board[x][y].getvY() < 0)
									mainThreat2 = true;
							}

							//The bullet is below my position in the same column and is moving towards me (upwards)
							if(x == myX && y < myY && board[x][y].getvY() > 0)
							{
								threat7 = true;
								
								if((x < (myX+3) || x > (myX-3)) && (y < (myY+3) || y > (myY-3)) && board[x][y].getvY() > 0)
									mainThreat7 = true;
							}
							//The bullet is to the right of my position in the same row and is moving towards me (left direction)
							if(y == myY && x > myX && board[x][y].getvX() < 0)
							{
								threat5 = true;
								
								if((x < (myX+3) || x > (myX-3)) && (y < (myY+3) || y > (myY-3)) && board[x][y].getvX() < 0)
									mainThreat5 = true;

							}
							//The bullet is to the left of my position in the same row and is moving towards me (right direction)
							if(y == myY && x < myX && board[x][y].getvX() > 0)
							{
								threat4 = true;
								
								if((x < (myX+3) || x > (myX-3)) && (y < (myY+3) || y > (myY-3)) && board[x][y].getvX() > 0)
									mainThreat4 = true;
							}
								
							//BULLET APPROACHING IN 4 DIAGONAL DIRECTIONS:
							
							//To verify the spots that are diagonal to my position
							int checkX = Math.abs(myX - x);
							int checkY = Math.abs(myY - y);
							boolean verify = false;
							
							if (checkX == checkY)
								verify = true;
							
							//The bullet is diagonal coming towards me from the top left
							if(verify == true && myX-x > 0 && myY-y > 0 && board[x][y].getvX() > 0 && board[x][y].getvY() > 0)
							{
								threat1 = true;
								
								if((x < (myX+3) || x > (myX-3)) && (y < (myY+3) || y > (myY-3)) && board[x][y].getvY() > 0)
									mainThreat1 = true;
							}
							//The bullet is diagonal coming towards me from the bottom left
							if(verify == true && myX-x > 0 && myY-y < 0 && board[x][y].getvX() > 0 && board[x][y].getvY() < 0)
							{
								threat6 = true;		
								
								if((x < (myX+3) || x > (myX-3)) && (y < (myY+3) || y > (myY-3)) && board[x][y].getvY() < 0)
									mainThreat6 = true;
							}
							//The bullet is diagonal coming towards me from the top right
							if(verify == true && myX-x < 0 && myY-y > 0 && board[x][y].getvX() < 0 && board[x][y].getvY() > 0)
							{
								threat3 = true;		
								
								if((x < (myX+3) || x > (myX-3)) && (y < (myY+3) || y > (myY-3)) && board[x][y].getvY() > 0)
									mainThreat3 = true;
							}
							//The bullet is diagonal coming towards me from the bottom right
							if(verify == true && myX-x < 0 && myY-y < 0 && board[x][y].getvX() < 0 && board[x][y].getvY() < 0)
							{
								threat8 = true;			
								
								if((x < (myX+3) || x > (myX-3)) && (y < (myY+3) || y > (myY-3)) && board[x][y].getvY() < 0)
									mainThreat8 = true;
							}

						}
					}
				}
				
				//Only moving (a.k.a. acting) if the bullet is a threat to my current position
				
				if(mainThreat2 == true || mainThreat7 == true) //main threat (1-2 away) up or down
				{
					if (mainThreat2 == true)
						act = 10;
					else
						act = 15;
					
					/*if (myX < 13)
						act = 5;
					else
						act = 4;*/
				}
				else if(mainThreat4 == true || mainThreat5 == true) // main threat (1-2 away) up or down
				{
					if (mainThreat4 == true)
						act = 12;
					else
						act = 13;
					
					/*if (myY < 13)
						act = 7;
					else
						act = 2;*/
				}
				else if(mainThreat6 == true) // main threat (1-2 away) bottom left
					act = 14;
					//act = 4;//14;
				else if(mainThreat1 == true) // main threat (1-2 away) top left
					act = 9;
					//act = 2;//9;
				else if(mainThreat8 == true) // main threat (1-2 away) bottom right
					act = 16;
					//act = 7;//16;
				else if(mainThreat3 == true) // main threat (1-2 away) top right
					act = 11;
					//act = 5;//11;
				else if(threat2 == true || threat7 == true) //up or down
				{
					if (myX < 13)
						act = 5;
					else
						act = 4;
				}
				else if(threat4 == true || threat5 == true) //left or right
				{
					if (myY < 13)
						act = 7;
					else
						act = 2;
				}
				else if (threat6 == true) //bottom left
					act = 4;//14;
				else if (threat1 == true) //top left
					act = 2;//9;
				else if (threat8 == true) //bottom right
					act = 7;//16;
				else if (threat3 == true) //top right
					act = 5;//11;
				else
				{
					if( myX < 24 && myX > 2 &&  myY < 24 && myY > 2)
						act = shooting(board, myX, myY, act); //otherwise send to method - shooting wherever there is a spot not equal to null
					else
						act = rand (9,16); //if in the corner then just shoot randomly :)
						
				}
				
				return act;

///////////////////
		/*int myX = 0; int myY = 0;
	
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
			
			return go;*/
///////////////////

	}
}


		








////////////////////////////////////////
/*package main;

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
	
	 This is your main method
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
	 	14 15 16
	  
	 

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
//					if( x < 25 && x > 1 && y < 25 && y > 1)
//					{
//						if(board[myX+1][myY+1] != null || board[myX+2][myY+2] != null)
//							go = 16;
//						else if(board[myX-1][myY-1] != null || board[myX-2][myY-2] != null)
//							go = 9;
//						else if(board[myX][myY+1] != null || board[myX][myY+2] != null)
//							go = 15;
//						else if(board[myX][myY-1] != null || board[myX][myY-2] != null)
//							go = 10;
//						else if(board[myX+1][myY] != null || board[myX+2][myY] != null)
//							go = 13;
//						else if( board[myX-1][myY] != null || board[myX-2][myY] != null)
//							go = 12;
//						else if(board[myX+1][myY-1] != null || board [myX+2][myY-2] != null)
//							go = 11;
//						else if(board[myX-1][myY+1] != null || board[myX-2][myY+2] != null)
//							go = 14;
//						else 
//							break;
//					
//					}
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


		

*/