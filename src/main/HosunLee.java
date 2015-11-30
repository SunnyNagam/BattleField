package main;

import java.io.IOException;
import javax.imageio.ImageIO;

public class HosunLee extends BoardPiece {
		
// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public HosunLee(int pieceSize){
		setName("HosunLee");
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
			
	/*	This is your main method
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
			//variables
			int nx = 0;
			int ny = 0;
			boolean threat = false;//are there any bullet threats?
			boolean corner = false;//am I in the corner?
			int cornercase = 0;//which corner am I in?
			double bulletdistancex;//bullet distance in x-axis
			double bulletdistancey;//bullet distance in y-axis
			double minbulletdistance = 3;//minimum bullet distance between myself and bullet allowed
			double bulletdistance = Math.sqrt(bulletdistancex*bulletdistancex + bulletdistancey*bulletdistancey);//bullet distance calculated by pythagorean theorem
			int situation = 0;//in which direction is the bullet coming from?
			//defining corners
			if(nx == 27 && ny == 27)
			{
				corner = true;
				cornercase = 1;
			}
			if(nx == 0 && ny == 27)
			{
				corner = true;
				cornercase = 2;
			}
			if(nx == 0 && ny == 0)
			{
				corner = true;
				cornercase = 3;
			}
			if(nx == 27 && ny == 0)
			{
				corner = true;
				cornercase = 4;
			}
			//locating myself
			for(int x = 0; x < 27; x++)
			{
				for(int y = 0; y < 27; y++)
				{
					if(board[x][y] != null && board [x][y] == "Hosun")
					{
						nx = x;
						ny = y;
					}
				}
			}
			//locating the bullet
			for(int x = 0; x < 27; x++)
			{
				for(int y = 0; y < 27; y++)
				{
					if(board[x][y] != null && board [x][y] == "Bullet")//bullet found
					{//decide if the bullet is a threat
						if(x == nx && y > ny && board[x][y] < 0)//bullet is above you and moving down
						{
							threat = true;
							situation = 1;
						}
						if(x == nx && y < ny && board[x][y] < 0)//bullet is below you and moving up
						{
							threat = true;
							situation = 2;
						}
						if(y == ny && x > nx && board[x][y] < 0)//bullet is to your right
						{
							threat = true;
							situation = 3;
						}
						if(y == ny && x < nx && board[x][y] > 0)//bullet is to your left
						{
							threat = true;
							situation = 4;
						}
						
					}
				}
		//if a bullet is recognized as a threat
				if(threat == true && situation == 1)//if the bullet is above you and moving down
				{
					if(corner == false)//if not in the corners
					{
						if(board[nx + 1][ny + 1] != "Bullet" && board[nx + 1][ny - 1] != "Bullet" && board [nx + 1][ny] != "Bullet" && board[nx + 2][ny] != "Bullet")
							return 5;
						else if(board[nx - 1][ny + 1] != "Bullet" && board[nx - 1][ny - 1] != "Bullet" && board[nx - 1][ny] != "Bullet" && board[nx - 2][ny] != "Bullet")
							return 4;
						else
							return 10;
					}
						if(cornercase == 3)//if in corner 3
						{
							if(board[nx + 1][ny] == "Bullet")
								return 13;
							else if(board[nx + 1][ny + 1] == "Bullet")
								return 11;
							else
								return 10;
						}
						if(cornercase == 4)//if in corner 4
						{
							if(board[nx - 1][ny] == "Bullet")
								return 12;
							else if(board[nx - 1][ny + 1] == "Bullet")
								return 9;
							else
								return 10;
						}
				}
				else if(threat == true && situation == 2)//if the bullet is below you and moving up
				{
					if(corner == false)//if not in the corners
					{
						if(board[nx + 1][ny + 1] != "Bullet" && board[nx + 1][ny - 1] && board[nx + 1][ny] != "Bullet" && board[nx + 2][ny] != "Bullet")
							return 5;
						else if(board[nx - 1][ny + 1] != "Bullet" && board[nx - 1][ny - 1] && board[nx - 1][ny] != "Bullet" && board[nx - 2][ny] != "Bullet")
							return 4;
						else
							return 15;
					}
					if(cornercase == 1)//if in corner 1
					{
						if(board[nx - 1][ny] == "Bullet")
							return 12;
						else if(board[nx - 1][ny - 1] == "Bullet")
							return 14;
						else
							return 15;
					}
					if(cornercase == 2)//if in corner 2
					{
						if(board[nx + 1][ny] == "Bullet")
							return 13;
						else if(board[nx + 1][ny - 1] == "Bullet")
							return 16;
						else
							return 15;
					}
				}
				else if(threat == true && situation == 3)//if the bullet is to your right
				{
					if(corner == false)//if not in the corners
					{
						if(board[nx][ny + 1] != "Bullet" && board [nx][ny + 2] != "Bullet" && board[nx - 1][ny + 1] && board[nx + 1][ny + 1] != "Bullet")
							return 2;
						else if(board[nx][ny - 1] != "Bullet" && board[nx][ny - 2] != "Bullet" && board[nx + 1][ny - 1] && board[nx - 1][ny - 1] != "Bullet")
							return 7;
						else
							return 13;
					}
					if(cornercase == 2)//if in corner 2
					{
						if(board[nx][ny - 1] == "Bullet")
							return 15;
						else if(board[nx + 1][ny - 1] == "Bullet")
							return 16;
						else
							return 13;
					}
					if(cornercase == 3)//if in corner 3
					{
						if(board[nx][ny + 1] == "Bullet")
							return 10;
						else if(board[nx + 1][ny + 1] == "Bullet")
							return 11;
						else
							return 13;
					}
				}
				else if(threat == true && situation == 4)//if the bullet is to your left
				{
					if(corner == false)//if not in the corners
					{
						if(board[nx][ny + 1] != "Bullet" && board[nx][ny + 2] != "Bullet" && board[nx - 1][ny + 1] && board[nx + 1][ny + 1] != "Bullet")
							return 2;
						else if(board[nx][ny - 1] != "Bullet" && board[nx][ny - 2] != "Bullet" && board[nx + 1][ny - 1] && board[nx - 1][ny - 1] != "Bullet")
							return 7;
						else
							return 12;
					}
					if(cornercase == 1)//if in corner 1
					{
						if(board[nx][ny - 1] == "Bullet")
							return 15;
						else if(board[nx - 1][ny - 1] == "Bullet")
							return 14;
						else
							return 12;
					}
					if(cornercase == 4)//if in corner 4
					{
						if(board[nx][ny + 1] == "Bullet")
							return 10;
						else if(board[nx - 1][ny + 1] == "Bullet")
							return 9;
						else
							return 12;
					}
				}
				/*MOVE
				1  2  3
				4  0  5
				6  7  8
						 	
				FIRE
				9  10 11
				12    13
				14 15 16
				*/
			}
			// TODO put codes in here
			return 0;// return number in between 0 and 16
	}
}
