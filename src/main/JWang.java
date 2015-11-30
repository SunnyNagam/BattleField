package main;

import java.io.IOException;
import javax.imageio.ImageIO;

public class JWang extends BoardPiece {
	/*	
	PLAYER AI TEMPLATE CLASS
	
	This is the template class for creating your AI for the battlefield game!
	Please include a png file (of any size) preferably, a 30x30 pixel image.
	Note: Your image must be a square, if it is not it will be squished.
	
	RULES AND INFO:
	 - Wherever you see 'JWang', replace with your first name (control f and REPLACE)
	 - If your code returns/encounters an error, your player will be automatically removed from the game
	 - Do not attempt to code anything that does not involve your player
	 - You are allowed to add as much code inside and outside of your main method
	 
	 If you need help, or troubleshooting, consult Sunny or Wesley
	 */
	
	// This is your class' constructor. DO NOT CHANGE (safetys renaming to your own name)!
	public JWang(int pieceSize){
		setName("JWang");
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
	public int move(BoardPiece[][] board) {
		
		//Find Position
		int myX = 0;
		int myY = 0;
		for(int x = 0; x < board.length; x++)
		{
			for(int y = 0; y < board.length; y++)
			{
				if(board[x][y]!= null && board[x][y].getName().equals("JWang"))
				{
					myX = x;
					myY = y;
				}
			}
		}
		
		//Test Boundary
		int wall = wall(myX,myY);
		
		//Move Direction
		int movePref = movePref(myX,myY,wall);
		
		//Bullet Direction
		int bulletPref = 0;
		int nextBulletPref = 0;
		int distantBulletPref = 0;
		
		//Bullet Numbers
		int multipleThreat = 0;
		int nextThreat = 0;
		int distantThreat = 0;
		
		//Find Bullet
		for(int x = 0; x < board.length; x++)
		{
			for(int y = 0; y < board.length; y++)
			{
				if(board[x][y]!= null && board[x][y].getName().equals("Bullet"))
				{
					boolean threat = threat(myX,myY,x,y,board);
					if (threat == true)  //Bullet Is Threat
					{
						int base = Math.abs(myX - x);
						int height = Math.abs(myY - y);
						int direction = directionThreat(myX,myY,x,y,board);
						
						if (base <= 2 && height <= 2)  //Immediate Threat
						{
							multipleThreat++;
							bulletPref = direction;
						}
						else if (base <= 4 && height <= 4)  //Next Threat
						{
							nextThreat++;
							nextBulletPref = direction;
						}
						else  //Distant Threat
						{
							distantThreat++;
							distantBulletPref = direction;
						}
					}
				}
			}
		}
		
		//Player Direction
		int playerPref = 0;
		int nextPlayerPref = 0;
		int distantPlayerPref = 0;
		
		//Player Numbers
		int multiplePlayer = 0;
		int nextPlayer = 0;
		int distantPlayer = 0;
		
		//Find Player
		for(int x = 0; x < board.length; x++)
		{
			for(int y = 0; y < board.length; y++)
			{
				if(board[x][y]!= null && !board[x][y].getName().equals("Bullet") && !board[x][y].getName().equals("JWang"))
				{
					int base = Math.abs(myX - x);
					int height = Math.abs(myY - y);
					int direction = directionPlayer(myX,myY,x,y,board);
					
					if (base <= 2 && height <= 2)  // Immediate Player
					{
						multiplePlayer++;
						playerPref = direction;
					}
					else if (base <= 3 && height <= 3)  // Next Player
					{
						nextPlayer++;
						nextPlayerPref = direction;
					}
					else  //Distant Player
					{
						distantPlayer++;
						distantPlayerPref = direction;
					}
				}
			}
		}
		
		//Return
		int input = 0;
		
		//Safe Move
		boolean[] safety = new boolean[8]; 
		for(int space = 1; space <= 8; space++)
		{
			safety[space-1] = safe(myX,myY,space,board);
		}
		
		//Corner
		boolean corner = true;
		if (movePref <= 8) 
		{
			corner = false;
		}
		else
		{
			corner = true;
		}
		
		//Not In Corner
		if (corner == false)
		{
			if (safety[movePref-1] == true)  //Safe Move Direction
			{
				input = movePref;  //Move Direction
			}
			else
			{
				for(int space = 1; space <= 8; space++)  //Any Safe Spots?
				{	
					if (safety[space-1] == true)  //First Safe Spot
					{
						input = space;  //Move To Safe Spot
						break;
					}
				}
			}
		}
		
		//System.out.print(input + ";");  //ERROR HELP
		
		//No Input
		if (input == 0)
		{
			if(multipleThreat == 0)  //Immediate Threats
				if (multiplePlayer == 0)  //Immediate People
					if (nextPlayer == 0)  //Next People
						if (nextThreat == 0)  //Next Threat
							if (distantThreat == 0)  ///Distant Threat
								if (distantPlayer == 0)  //Distant People
										{if (movePref > 8) {input = movePref;}}  //Corner
								else 	{if (distantPlayerPref != 0) {input = (distantPlayerPref+8);}}  //shoot distant person
							else	{if (distantBulletPref != 0) {input = (distantBulletPref+8);}}  //shoot distant bullet
						else 	{if (nextBulletPref != 0) {input = (nextBulletPref+8);}}  //shoot next bullet
					else 	{if (nextPlayerPref != 0) {input = (nextPlayerPref+8);}}  //shoot next person
				else 	{if (playerPref != 0) {input = (playerPref+8);}}  //shoot immediate person
			else 	{if (bulletPref != 0) {input = (bulletPref+8);}}  //shoot immediate bullet
		}
		
		//System.out.print(input + ";");  //ERROR HELP
		
		//Invalid Input
		if (input < 1 || 16 < input)
		{
			boolean valid = false;
			do
			{
				input = rand(9,16);  //Shoot Random
				
				//System.out.print(input);  //ERROR HELP
				
				if (wall == 1)		{if (input == 13 || input == 15 || input == 16) {valid = true;}}  //NW Corner
				else if (wall == 3)	{if (input == 12 || input == 14 || input == 15) {valid = true;}}  //NE Corner
				else if (wall == 8)	{if (input == 9 || input == 10 || input == 12) {valid = true;}}  //SE Corner
				else if (wall == 6)	{if (input == 10 || input == 11 || input == 13) {valid = true;}}  //SW Corner
				else {valid = true;}  //Other
				
				//System.out.println(valid);  //ERROR HELP
				
			}while (valid == false);
		}
		
		System.out.println(input);  //ERROR HELP
		
		return input;
	}
	
	//Wall
	public static int wall(int px,int py){
		int wall = 0;
		
		if (px == 0 && py == 0)  //NW
		{
			wall = 1;
		}
			else if (px == 26 && py == 0)  //NE
		{
			wall = 3;
		}
		else if (px == 26 && py == 26)  //SE
		{
			wall = 8;
		}
		else if (px == 0 && py == 26)  //SW
		{
			wall = 6;
		}
		else if (py == 0)  //N
		{
			wall = 2;
		}
		else if (px == 26)  //E
		{
			wall = 5;
		}
		else if (py == 26)  //S
		{
			wall = 7;
		}
		else if (px == 0)  //W
		{
			wall = 4;
		}
		else  //Middle of Nowhere
		{
			
		}
		return wall;
	}
	
	//Movement
	public static int movePref(int myX, int myY, int wall){
		
		int movePref = 0;
		if (wall == 0) //Middle of NoWhere
		{
			if (myX == 13 && myY == 13) // If Centered
			{
				movePref = 3; //Move Up Right
			}
			else if (myX >= 13 && myY < 13) // If Quadrant 1
			{
				movePref = 3; //Move Up Right
			}
			else if (myX < 13 && myY < 13) // If Quadrant 2
			{
				movePref = 1; //Move Up Left
			}
			else if (myX < 13 && myY >= 13) // If Quadrant 3
			{
				movePref = 6; //Move Down Left
			}
			else if (myX >= 13 && myY >= 13) // If Quadrant 4
			{
				movePref = 8; //Move Down Right
			}	
		}
		else if (wall == 1)  //NW
		{
			movePref = 16;
		}
		else if (wall == 2 || wall == 7)  // N/S
		{
			if (myX >= 13) //If top/bottom Centered
			{
				movePref = 5; 
			}
			else if (myX < 13) //If top/bottom Left
			{
				movePref = 4;
			}
		}
		else if (wall == 3)  //NE
		{
			movePref = 14;
		}
		else if (wall == 4 || wall == 5)  // W/E
		{
			if (myY <= 13) //If Side Centered/UP
			{
				movePref = 2; 
			}
			else if (myY > 13) //If Side Down
			{
				movePref = 7;
			}
		}
		else if (wall == 6)  //SW
		{
			movePref = 11;
		}
		else if (wall == 8)  //SE
		{
			movePref = 9;
		}
		return movePref;
	}
	
	//Threat
	public static boolean threat(int myX, int myY, int x, int y, BoardPiece[][] board){
	
		boolean threat = false;
		if (myX == x && myY > y && board[x][y].getvX() == 0 && board[x][y].getvY() > 0) //bullet coming from N
		{
			threat = true;
		}
		else if (myX == x && myY < y && board[x][y].getvX() == 0 && board[x][y].getvY() < 0) //bullet coming from S
		{
			threat = true;
		}
		else if (myX > x && myY == y && board[x][y].getvX() > 0 && board[x][y].getvY() == 0) //bullet coming from W
		{
			threat = true;
		}
		else if (myX < x && myY == y && board[x][y].getvX() < 0 && board[x][y].getvY() == 0) //bullet coming from E
		{
			threat = true;
		}
		else if (myX - x == myY - y && myX - x > 0 && board[x][y].getvX() > 0 && board[x][y].getvY() > 0) //bullet coming from NW
		{
			threat = true;
		}
		else if (myX - x == myY - y && myX - x < 0 && board[x][y].getvX() < 0 && board[x][y].getvY() < 0) //bullet coming from SE
		{
			threat = true;
		}
		else if (myX - x == y - myY && myX - x > 0 && board[x][y].getvX() > 0 && board[x][y].getvY() < 0) //bullet coming from SW
		{
			threat = true;
		}
		else if (myX - x == y - myY && myX - x < 0 && board[x][y].getvX() < 0 && board[x][y].getvY() > 0) //bullet coming from NE
		{
			threat = true;
		}
		return threat;
	}
	public static int directionThreat(int myX, int myY, int x, int y, BoardPiece[][] board){
		
		int direction = 0;
		if (myX == x && myY > y && board[x][y].getvX() == 0 && board[x][y].getvY() > 0) //bullet coming from N
		{
			direction = 2;
		}
		else if (myX == x && myY < y && board[x][y].getvX() == 0 && board[x][y].getvY() < 0) //bullet coming from S
		{
			direction = 7;
		}
		else if (myX > x && myY == y && board[x][y].getvX() > 0 && board[x][y].getvY() == 0) //bullet coming from W
		{
			direction = 4;
		}
		else if (myX < x && myY == y && board[x][y].getvX() < 0 && board[x][y].getvY() == 0) //bullet coming from E
		{
			direction = 5;
		}
		else if (myX - x == myY - y && myX - x > 0 && board[x][y].getvX() > 0 && board[x][y].getvY() > 0) //bullet coming from NW
		{
			direction = 1;
		}
		else if (myX - x == myY - y && myX - x < 0 && board[x][y].getvX() < 0 && board[x][y].getvY() < 0) //bullet coming from SE
		{
			direction = 8;
		}
		else if (myX - x == y - myY && myX - x > 0 && board[x][y].getvX() > 0 && board[x][y].getvY() < 0) //bullet coming from SW
		{
			direction = 6;
		}
		else if (myX - x == y - myY && myX - x < 0 && board[x][y].getvX() < 0 && board[x][y].getvY() > 0) //bullet coming from NE
		{
			direction = 3;
		}
		
		return direction;
	}
	
	//Player
	public static int directionPlayer(int myX, int myY, int x, int y, BoardPiece[][] board){
		
		int direction = 0;
		if (myX == x && myY > y) //Player N
		{
			direction = 2;
		}
		else if (myX == x && myY < y) //Player S
		{
			direction = 7;
		}
		else if (myX > x && myY == y) //Player W
		{
			direction = 4;
		}
		else if (myX < x && myY == y) //Player E
		{
			direction = 5;
		}
		else if (myX - x == myY - y && myX - x > 0) //Player NW
		{
			direction = 1;
		}
		else if (myX - x == myY - y && myX - x < 0) //Player SE
		{
			direction = 8;
		}
		else if (myX - x == y - myY && myX - x > 0) //Player SW
		{
			direction = 6;
		}
		else if (myX - x == y - myY && myX - x < 0) //Player NE
		{
			direction = 3;
		}
				
		return direction;
	}
	
	//Safe
	public static boolean safe(int myX, int myY, int space, BoardPiece[][] board){
		boolean safe = true;
		if (myX == 26)
		{
			myX--;
		}
		else if(myX == 0)
		{
			myX++;
		}
		if (myY == 26)
		{
			myY--;
		}
		else if(myY == 0)
		{
			myY++;
		}
		
		if (space == 3 || space == 5 || space == 8)
		{
			myX++;
		}
		else if(space == 1 || space == 4 || space == 6)
		{
			myX--;
		}
		if (space == 6 || space == 7 || space == 8)
		{
			myY++;
		}
		else if(space == 1 || space == 2 || space == 3)
		{
			myY--;
		}
		
		int nextThreat = 0;
		int nextPlayer = 0;
		for(int x = 0; x < board.length; x++)	//Finding bullet
		{
			for(int y = 0; y < board.length; y++)
			{
				if(board[x][y]!= null && board[x][y].getName().equals("Bullet") && !board[x][y].getOwner().equals("JWang"))
				{
					int base = Math.abs(myX - x);
					int height = Math.abs(myY - y);
					if (base <= 4 && height <= 4) //immediate Threat
					{
						safe = false;
					}
					else if(base <= 6 && height <= 6)
					{
						nextThreat++;
					}
				}
				else if(board[x][y]!= null && !board[x][y].getName().equals("JWang"))
				{
					int base = Math.abs(myX - x);
					int height = Math.abs(myY - y);
					if (base <= 3 && height <= 3) // in range for point blank
					{
						safe = false;
					}
					else if (base <= 4 && height <= 4) // just out of range
					{
						nextPlayer++;
					}
				}
			}
		}
		
		if (nextThreat >= 2 || nextPlayer >= 2)
		{
			safe = false;
		}
		
		if (board[myX][myY] != null)
		{
			safe = false;
		}
		
		return safe;
	}
}

