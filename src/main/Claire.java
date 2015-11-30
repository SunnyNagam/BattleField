/*	
	PLAYER AI TEMPLATE CLASS
	
	This is the template class for creating your AI for the battlefield game!
	Please include a png file (of any size) preferably, a 30x30 pixel image.
	Note: Your image must be a square, if it is not it will be squished.
	
	RULES AND INFO:
	 - Wherever you see 'Claire', replace with your first name (control f and REPLACE)
	 - If your code returns/encounters an error, your player will be automatically removed from the game
	 - Do not attempt to code anything that does not involve your player
	 - You are allowed to add as much code inside and outside of your main method
	 
	 If you need help, or troubleshooting, consult Sunny or Wesley
*/
package main;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Claire extends BoardPiece {
	
	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public Claire(int pieceSize){
		setName("Claire");
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
	    
	    //when done, send to sunnynagam1@gmail.com
	    
	    MOVE
	 	1  2  3
	 	4  0  5
	 	6  7  8
	 	
	 	FIRE
	 	9  10 11
	 	12    13
	 	14 15 16
	  
	 */
	
	/*
		public static int x=0, y=0;
		public static int locX = 0, locY = 0;
		
		public static int minX = locX - 2;
		public static int maxX = locX + 2;
		public static int minY = locY - 2;
		public static int maxY = locY + 2;
		public static boolean danger = false;
		public static int threats = 0;
	 */
	
	//how do I get it to remember which squares are safe and move to them?
	
	public static int x=0, y=0;
	public static int locX = 0, locY = 0;
	public static boolean emptySpot = true;
	public static int bulletX = 0;
	public static int bulletY = 0;
	public static int count = 0;
	public static int go = 0;

	public int move(BoardPiece[][] board) {
	
		//int x=0, y=0;
		//int locX = 0, locY = 0;

			//find my location on the board
			for(x=0; x<board.length; x++)
				for(y=0; y<board[x].length; y++)
				{
					if(board[x][y]!=null && board[x][y].getName().equals("Claire"))
					{
						locX = x;
						locY = y;
					}
				}
			
			//checking for a bullet that is near me (2 squares):
			int minX = locX - 2;
			int maxX = locX + 2;
			int minY = locY - 2;
			int maxY = locY + 2;
			boolean danger1 = false;
			boolean danger2 = false;
			boolean danger3 = false;
			boolean danger4 = false;
			boolean danger5 = false;
			boolean danger6 = false;
			boolean danger7 = false;
			boolean danger8 = false;
			boolean opponent1 = false;
			boolean opponent2 = false;
			boolean opponent3 = false;
			boolean opponent4 = false;
			boolean opponent5 = false;
			boolean opponent6 = false;
			boolean opponent7 = false;
			boolean opponent8 = false;

			int threats = 0;
			 
			//board length = 26
			if(minX  <= 0)
				minX = 2;
			if(maxX >= 26) // might have to square root this length
				maxX = 24;
			if(minY  <= 0)
				minY = 2;
			if(maxY >= 26)// might have to square root this length
				maxY = 24;
			
			/*
			 if(minX  < 0)
				minX = 0;
			 if(maxX > board.length) // might have to square root this length
				maxX = board.length-1;
			if(minY  < 0)
				minY = 0;
			if(maxY > board.length)// might have to square root this length
				maxY = board.length-1;
			*/
			
			
			//Looking for opponents
			for(x=0; x<26; x++)
				for(y=0; y<26;  y++)
				{				
					if(board[x][y] != null && !board[x][y].getName().equals("Bullet") && !board[x][y].getName().equals("Claire") )
					{
						//detects person
						if(x < locX && y == locY)
						{
							opponent1 = true;
							
						}
						//detects person
						else if(x > locX && y == locY)
						{
							opponent2 = true;
						}
						//detects person
						else if(y > locY && x == locX)
						{
							opponent3 = true;
						}
						//detects person
						else if(y < locY && x == locX)
						{
							opponent4 = true;
						}
						//detects person
						else if(x < locX && locX-x == y-locY)
						{
							opponent5 = true;
						}
						//detects person
						else if(x > locX && locX-x == y-locY)
						{
							opponent6 = true;
						}
						//detects person
						else if(x < locX && locX-x == y-locY)
						{
							opponent7 = true;
						}
						//detects person
						else if(x > locX && locX-x == y-locY)
						{
							opponent8 = true;
						}
					}
				}
			
			
			
			
			
			//Looking for bullets
			 for(x=minX; x<maxX; x++)
				for(y=minY; y<maxY;  y++)
				{				
					if(board[x][y] != null && board[x][y].getName().equals("Bullet"))
					{
						//this detects a bullet moving directly towards me from the left
						if(x < locX && board[x][y].getvX() > 0 && y == locY)
						{
							danger1 = true;
							threats++;
							bulletX = x;
							bulletY = y;
						}
						//this detects a bullet moving directly towards me from the right
						else if(x > locX && board[x][y].getvX() < 0 && y == locY)
						{
							danger2 = true;
							threats++;
							bulletX = x;
							bulletY = y;
						}
						//this detects a bullet moving directly towards me from the top
						else if(y > locY && board[x][y].getvY() < 0 && x == locX)
						{
							danger3 = true;
							threats++;
							bulletX = x;
							bulletY = y;
						}
						//this detects a bullet moving directly towards me from the bottom
						else if(y < locY && board[x][y].getvY() > 0 && x == locX)
						{
							danger4 = true;
							threats++;
							bulletX = x;
							bulletY = y;
						}
						//this detects a bullet moving directly towards me diagonally from the upper left
						else if(x < locX && board[x][y].getvX() > 0 && locX-x == y-locY && board[x][y].getvY() < 0)
						{
							danger5 = true;
							threats++;
							bulletX = x;
							bulletY = y;
						}
						//this detects a bullet moving directly towards me diagonally from the upper right
						else if(x > locX && board[x][y].getvX() < 0 && locX-x == y-locY && board[x][y].getvY() < 0)
						{
							danger6 = true;
							threats++;
							bulletX = x;
							bulletY = y;
						}
						//this detects a bullet moving directly towards me diagonally from the lower left
						else if(x < locX && board[x][y].getvX() > 0 && locX-x == y-locY && board[x][y].getvY() > 0)
						{
							danger7 = true;
							threats++;
							bulletX = x;
							bulletY = y;
						}
						//this detects a bullet moving directly towards me diagonally from the lower right
						else if(x > locX && board[x][y].getvX() < 0 && locX-x == y-locY && board[x][y].getvY() > 0)
						{
							danger8 = true;
							threats++;
							bulletX = x;
							bulletY = y;
						}
					}
				}		
				 
				//move
				// if(bulletX < locX && bulletY == locY && danger1 == true)
			 	if(danger1 == true)
					{
						go = 7;
					}
				 else if(danger2 == true)
					{
						go = 7;
					}
				 else if(danger3 == true)
					{
						go = 4;
					}
				 else if(danger4 == true)
					{
						go = 4;
					}
				 else if(danger5 == true)
					{
						go = 6;
					}
				 else if(danger6 == true)
					{
						go = 4;
					}
				 else if(danger7 == true)
					{
					 	go = 1;
					}
				 else if(danger8 == true)
					{
						go = 6;
					}
				 //else 
					// {
					//	int minRand = 9, maxRand = 16;
					//	go = (int)(Math.random()*(maxRand-minRand+1)+minRand);	
					// }
			 	//not working right now
			 	
			 	  if (opponent1 == true)
					{
						go = 12;
						// int minRand = 9, maxRand = 16;
						// go = (int)(Math.random()*(maxRand-minRand+1)+minRand);	
					}	
				else if (opponent2 == true)
					{
						go = 13;
					}	
				else if (opponent3 == true)
					{
						go = 10;
					}	
				else if (opponent4 == true)
					{
						go = 15;
					}	
				else if (opponent5 == true)
					{
						go = 9;
					}	
				else if (opponent6 == true)
					{
						go = 11;
					}	
				else if (opponent7 == true)
					{
						go = 14;
					}	
				else if (opponent8 == true)
					{
						go = 16;
					}
			 	 
			 		 
			 	 
			 	
			 	/*
			 	 * 				 else if
				 {
					int minRand = 9, maxRand = 16;
					go = (int)(Math.random()*(maxRand-minRand+1)+minRand);	
				 }
			 	 */
			 	//keeps on shooting in one direction
			 	
				 
				 return go;
		}
		
	
	
	
	
	
	/*
	 * 		 System.out.println("Bullet X " + (x));
			 System.out.println("Bullet Y " + (y)); 
	 */
	
		/*
		 for(x=minX; x<maxX; x++)
				for(y=minY; y<maxY;  y++)
				{
					if(board[x][y] == null)
					{
						return 14;
					}
				}
		*/
		 
		 //if(bulletX < locX && bulletY == locY)
		 //{
		 //	return 7;
		 //}
		 
		/*
	
		//if there is an imminent threat
		if(threats > 0)
		{
			//have to move
			//check each potential spot to move to for danger
			
			//need it to check one square around from the potential location 
			minX = locX - 1;
			maxX = locX + 1;
			minY = locY - 1;
			maxY = locY + 1;
			threats = 0;
			
			if(minX  < 0)
				minX = 0;
			if(maxX > 26) // might have to square root this length
				maxX = 26;
			if(minY  < 0)
				minY = 0;
			if(maxY > 26)// might have to square root this length
				maxY = 26;

			/*
			 * if(minX  < 0)
				minX = 0;
			if(maxX > board.length) // might have to square root this length
				maxX = board.length-1;
			if(minY  < 0)
				minY = 0;
			if(maxY > board.length)// might have to square root this length
				maxY = board.length-1;
			 */
			
		/*
		 * 	//scan all possible spots to move into
			for(x=minX; x<maxX; x++)
				for(y=minY; y<maxY;  y++)
				{
					if(board[x][y] != null)
					{						
						emptySpot = false;
					}
					if(board[x][y] == null)
					{
						//scan for bullets
						checkBullet(board);
						
						if(threats == 0)
						{
							return (int)(Math.random()*(8)+1);
							//maybe later check which spot is the furthest from anyone
							//or continuously try to stay in the middle or corner
						}
						if(threats > 0)
						{
							//move to one without a threat
						}
					}
				}
		}
		 
		 */
			
		
		/*
		 * 
		//no imminent threat 
		if(threats == 0)
		{
		
			//scan and look for someone close to shoot at
			//if no one is close, scan the whole board and find someone to shoot at
			//bullet or person in left spot
			
			minX = locX - 2;
			maxX = locX + 2;
			minY = locY - 2;
			maxY = locY + 2;
			 
			if(minX  < 0)
				minX = 0;
			if(maxX > board.length) // might have to square root this length
				maxX = board.length-1;
			if(minY  < 0)
				minY = 0;
			if(maxY > board.length)// might have to square root this length
				maxY = board.length-1;
			
			for(x=minX; x<maxX; x++)
				for(y=minY; y<maxY;  y++)
				{
					if(board[x][y] != null)
					{
						if(x < locX && y == locY)
						{
							emptySpot = false;
						}
						//bullet or person in right spot
						if(x > locX && y == locY)
						{
							emptySpot = false;
						}
						//bullet or person above
						if(y > locY && x == locX)
						{
							emptySpot = false;
						}
						//bullet or person below
						if(y < locY && x == locX)
						{
							emptySpot = false;
						}
						//bullet or person in top left
						if(x < locX && locX-x == y-locY && y > locY)
						{
							emptySpot = false;
						}
						//bullet or person in top right
						if(x > locX && locX-x == y-locY && y > locY)
						{
							emptySpot = false;
						}
						//bullet or person in bottom left
						if(x < locX && locX-x == y-locY && y < locY)
						{
							emptySpot = false;
						}
						//bullet or person in bottom right
						if(x > locX && locX-x == y-locY && y < locY)
						{
							emptySpot = false;
						} 			
					}
				}
		}
		 */
		
		/*
			if(emptySpot == false) // && the spot doesn't equal "Bullet"
			{
				//shoot towards the person 
			}
		 */	

	
	//not sure if method works
	public static void checkBullet(BoardPiece[][] board)
	{		
		int minX = locX - 2;
		int maxX = locX + 2;
		int minY = locY - 2;
		int maxY = locY + 2;
		boolean danger = false;
		int threats = 0;
		 
		if(minX  < 0)
			minX = 0;
		if(maxX > board.length) // might have to square root this length
			maxX = board.length-1;
		if(minY  < 0)
			minY = 0;
		if(maxY > board.length)// might have to square root this length
			maxY = board.length-1;
		
		for(x=minX; x<maxX; x++)
			for(y=minY; y<maxY;  y++)
			{
				//checkBullet(board);
				//TODO are all the velocities correct?
				
				 if(board[x][y].getName().equals("Bullet"))
				{
					//this detects a bullet moving directly towards me from the left
					if(x < locX && board[x][y].getvX() > 0 && y == locY)
					{
						danger = true;
						threats++;
					}
					//this detects a bullet moving directly towards me from the right
					if(x > locX && board[x][y].getvX() < 0 && y == locY)
					{
						danger = true;
						threats++;
					}
					//this detects a bullet moving directly towards me from the top
					if(y > locY && board[x][y].getvY() < 0 && x == locX)
					{
						danger = true;
						threats++;
					}
					//this detects a bullet moving directly towards me from the bottom
					if(y < locY && board[x][y].getvY() > 0 && x == locX)
					{
						danger = true;
						threats++;
					}
					//this detects a bullet moving directly towards me diagonally from the upper left
					if(x < locX && board[x][y].getvX() > 0 && locX-x == y-locY && board[x][y].getvY() < 0)
					{
						danger = true;
						threats++;
					}
					//this detects a bullet moving directly towards me diagonally from the upper right
					if(x > locX && board[x][y].getvX() < 0 && locX-x == y-locY && board[x][y].getvY() < 0)
					{
						danger = true;
						threats++;
					}
					//this detects a bullet moving directly towards me diagonally from the lower left
					if(x < locX && board[x][y].getvX() > 0 && locX-x == y-locY && board[x][y].getvY() > 0)
					{
						danger = true;
						threats++;
					}
					//this detects a bullet moving directly towards me diagonally from the lower right
					if(x > locX && board[x][y].getvX() < 0 && locX-x == y-locY && board[x][y].getvY() > 0)
					{
						danger = true;
						threats++;
					}
				}
			}
	}
}
	
	
	/*
	//do I need all of these if statements? or can I just see that it's not empty?
	//bullet or person in left spot
	if(x < locX && y == locY)
	{
		emptySpot = false;
		threats++;
	}
	//bullet or person in right spot
	if(x > locX && y == locY)
	{
		emptySpot = false;
		threats++;
	}
	//bullet or person above
	if(y > locY && x == locX)
	{
		emptySpot = false;
		threats++;
	}
	//bullet or person below
	if(y < locY && x == locX)
	{
		emptySpot = false;
		threats++;
	}
	//bullet or person in top left
	if(x < locX && locX-x == y-locY && y > locY)
	{
		emptySpot = false;
		threats++;
	}
	//bullet or person in top right
	if(x > locX && locX-x == y-locY && y > locY)
	{
		emptySpot = false;
		threats++;
	}
	//bullet or person in bottom left
	if(x < locX && locX-x == y-locY && y < locY)
	{
		emptySpot = false;
		threats++;
	}
	//bullet or person in bottom right
	if(x > locX && locX-x == y-locY && y < locY)
	{
		emptySpot = false;
		threats++;
	} 
	 */


