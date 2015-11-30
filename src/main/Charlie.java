
package main;

import java.io.IOException;
import javax.imageio.ImageIO;

import com.sun.prism.paint.Stop;

public class Charlie extends BoardPiece {
	static final int PLAYERSIZE = 26;

	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
    public Charlie(int pieceSize){
            setName("Charlie");
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
    static int bSize = 0;
    static String name = "";
    public int move(BoardPiece[][] board)
    {
    	name = getName();
    	bSize = board.length-1;
    	int x = move1(board);
    	//System.out.println("Action:" + x);
    	return x;
    }
    public int move1(BoardPiece[][] board) {
            // TODO put codez in here
	//Kill Players too close
    //Works
		int[] selfPos = selfLocation(board);
		int posX = selfPos[0], posY = selfPos[1];
		////System.out.println("Pos: " + posX + ", " + posY);
		////System.out.println("2nd Step");
		int[][] bulletLocation = findBullets(board);
		if(dangerClose(board, posX, posY))
		{ 
					int closePlayers = escapeClosePlayers(board, bulletLocation, posX, posY);
					//System.out.println("Escape Close Players: " + closePlayers);
					if (closePlayers != 0)
						return closePlayers;
					closePlayers = ShootClosePlayers(board, posX, posY);
					//System.out.println("Shoot Close Players: " + closePlayers);
					if (closePlayers != 0)
						return closePlayers;
					
		}

    	
    	
	//Self Defense
	//Works

		////System.out.println("f: " + bulletLocation[0][0] + ", " + bulletLocation[0][1]);
		////System.out.println("1st Step");

	//Find the quadrant with the least players 

		int[][] dangerBullet = dangerBullets(board, bulletLocation, posX, posY);
		//System.out.println("DangerBullet: " + dangerBullet[0][1]);
		int moveQuad = moveQuadLeast(board, bulletLocation, posX, posY);
		if (moveQuad != 0)
		{
			//System.out.println("MoveQuadLeast: " + moveQuad);
			return moveQuad;
		}else if (dangerBullet[0][1] != 200)
		{

				//System.out.println("3th bullet" + dangerBullet[3][1]);
				if(dangerBullet[3][1]!=0)
					return escapeClosePlayers(board, bulletLocation, posX, posY);
				else
					return shootBullet(board, bulletLocation, dangerBullet); 
		}else
		{
			int cornerShoot = inCornerShoot(board, posX, posY);
			if (cornerShoot != 0)
				return cornerShoot;
			else
				return(rand(0, 16));
		}

		
		////System.out.println("Quadrant " + quad[0][1] + " has the least players");
		
		
		//return 9;// return number in between 0 and 16
    }
    
    public int moveQuadLeast(BoardPiece[][] board, int [][] bulletLocation, int posX, int posY)
    {
		if((posX == 0 && posY == bSize)||(posX == 0 && posY == 0)||(posX == bSize && posY == bSize)||(posX == bSize && posY == 0))
		{
			//System.out.println("I am in a corner");
			return 0;

		}else
		{
			////System.out.println("NOT in a corner");
			int [][] quad = quadLeastPlayers(board, posX, posY);
			int targetX = 0;
			int targetY = 0;
			if (quad[0][1] == 0)
			{
				targetX = -1;
				targetY = -1;
			}else if (quad[0][1] == 1)
			{
				targetX = 1;
				targetY = -1;
			}else if (quad[0][1]==2)
			{
				targetX = 1;
				targetY = 1;
			}else
			{
				targetX = -1;
				targetY = 1;
			}
			////System.out.println("Step 1: " + targetX + ", " + targetY);
			////System.out.println("SelfPos " + posX + ", " + posY);
			//targetX = -1;
			//targetY = -1;		
			if(posX == bSize || posX == 0)
			{
				targetX = 0;
				if (posY < 13)
				{
					targetY = -1;
				}else
					targetY = 1;
			}
			if(posY == bSize || posY == 0)
			{
				targetY = 0;
				if (posX < 13)
				{
					targetX = -1;
				}else
					targetX = 1;
			}
			
		/*	//System.out.println("Step 2: " + targetX + ", " + targetY);
		*/

			

			int x = 0;
			int targetPosX = 0;
			if( targetX == 1)
				targetPosX = bSize;
			int targetPosY = 0;
			if(targetY == 1)
				targetPosY = bSize;
			while (x < 3)
			{

				
				if (x == 1)
				{
					if (Math.abs(targetPosX-posX) < Math.abs(targetPosY-posY))
					{
						targetY = 0;
					}else
						targetX = 0;
				}else if (x == 2)
				{
					if (Math.abs(targetPosX-posX) > Math.abs(targetPosY-posY))
					{
						targetY = 0;
					}else
						targetX = 0;
				}
	/*			if (safeMove(board, bulletLocation, posX+targetX, posY+targetY))
				{
					//System.out.println("Movement is safe");
				}else
					//System.out.println("Movement is not safe");*/
				
				if (safeMove(board, bulletLocation, posX+targetX, posY+targetY))
				{
					//System.out.println("X:" + targetX + "Y: " +targetY);
					if (targetX == -1)
					{
						if (targetY == -1)
							return 1;
						else if (targetY == 0)
							return 4;
						else 
							return 6;
					}else if (targetX == 0)
					{
						if (targetY == -1)
							return 2;
						else if (targetY == 1)
							return 7;
						else
							return 0;
					}else 
					{
						if (targetY == -1)
							return 3;
						else if (targetY == 0)
							return 5;
						else 
							return 8;
					}
				}else
					x++;
			}
			return 0;
	    }
	}
    public int shootBullet(BoardPiece[][] board, int[][]bulletLocation, int[][] dangerBullet)
    {
    	int distance = 0, bullet = 0;
    	for (int x = 0; x < 18; x++)
    	{
    		if ( distance < dangerBullet[x][1])
    		{
    			distance = dangerBullet[x][1];
    			bullet = dangerBullet[x][0];
    		}
    	}
		int bulletvX = board[bulletLocation[bullet][0]][bulletLocation[bullet][1]].getvX();
		int bulletvY = board[bulletLocation[bullet][0]][bulletLocation[bullet][1]].getvY();
		////System.out.println("Shot");
		if (bulletvX == 0)
    	{
    		if(bulletvY > 0)
    			return 10;
    		else
    			return 15;
    	}else if (bulletvX < 0)
    	{
    		if(bulletvY == 0)
    			return 13;
    		else if(bulletvY < 0)
    			return 16;
    		else 
    			return 11;
    	}else
    	{
    		if(bulletvY == 0)
    			return 12;
    		else if(bulletvY < 0)
    			return 14;
    		else 
    			return 9;
    	}
    }
    
    public int[][] findBullets(BoardPiece[][] board){
    	
    	int[][]bulletLocation = new int[200][2];
    	int bullet = 0;
    	for(int y = 0; y <= bSize; y++)
    	{
			for(int x = 0; x <= bSize; x++){
				if (board[x][y] != null)
				{
					if (board[x][y].getName() == "Bullet")
					{
						bulletLocation[bullet][0]=x;
						bulletLocation[bullet][1]=y;
						bullet++;
					}
				}
			}
    	}
    	return bulletLocation;
    }
    
	public int[][] dangerBullets(BoardPiece[][] board, int[][]bulletLocation, int posX, int posY)
	    {
	    	int number = 0;
	    	int [][]  dangerBullet = new int [18][2];
	    	dangerBullet[0][1]=200;
	    	for(int x = 0; x<200; x++)
	    	{
	    		if(bulletLocation[x][0] != 0 || bulletLocation[x][1] != 0)    		   		
	    		{
	    			int bulletvX = board[bulletLocation[x][0]][bulletLocation[x][1]].getvX();
	    			int bulletvY = board[bulletLocation[x][0]][bulletLocation[x][1]].getvY();
	    			////System.out.println("dBullet: " + bulletvX + ", " + bulletvY);
	    			int temp = 0;
	    			while ((bulletLocation[x][0]+bulletvX*temp != posX || bulletLocation[x][1]+bulletvY*temp != posY) && temp < 9)//Bullets are only dangerous if they are going to hit me in 3 turns
	    			{
	    				temp++;
	    			}
	    			////System.out.println(temp);
					if (bulletLocation[x][0]+bulletvX*temp == posX && bulletLocation[x][1]+bulletvY*temp == posY)
					{
						dangerBullet[number][0]=x;//which bullet it is in 'bulletLocation'
						dangerBullet[number][1]=temp;//the distance
						number++;
					}
					if(number == 18)
					{
						break;
					}
					//TODO if there are more than 3 "dangerous" bullets, I need to try and escape
	    		}
	    			
	    		/*if(bulletLocation[x][0] != 0 || bulletLocation[x][1] != 0)    		   		
	    		{
	    			int bulletvX = board[bulletLocation[x][0]][bulletLocation[x][1]].getvX();
	    			int bulletvY = board[bulletLocation[x][0]][bulletLocation[x][1]].getvY();
	    			int temp = 0;
	    			while (bulletLocation[x][0]+bulletvX*temp <= selfpos[0] && bulletLocation[x][1]+bulletvY*temp <= selfpos[1])
	    			{
	    				temp++;
	    			}
					if (bulletLocation[x][0]+bulletvX*temp == selfpos[0] && bulletLocation[x][1]+bulletvY*temp == selfpos[1])
					{
						dangerBullet[number][0]=x;//which bullet it is in 'bulletLocation'
						dangerBullet[number][1]=temp;//the distance
						number++;
					}
	    		}
	    			*/
	    	}
	    	if(dangerBullet[0][1] != 200)
	    	while (!checkAscendBullets(dangerBullet, 18))
	    	{
	    		for (int x = 0; x < 18-1; x++)
	    		{
	    			if (dangerBullet[x][1] > dangerBullet[x+1][1] && dangerBullet[x][1] != 0 && dangerBullet[x+1][1] != 0)
	    				
					{
	    				int temp = dangerBullet [x][1];
	    				dangerBullet[x][1] = dangerBullet[x+1][1];
	    				dangerBullet[x+1][1] = temp;
	    				temp = dangerBullet[x][0];
	    				dangerBullet[x][0] = dangerBullet[x+1][0];
	    				dangerBullet[x+1][0] = temp;
					}
	    		}
	    	}
	    	return dangerBullet;
	    }
	
	public boolean checkAscendBullets(int[][]dangerBullet, int arraySize)
	    {
	    for (int x = 0; x < arraySize-1; x++)
			if (dangerBullet[x][1] > dangerBullet[x+1][1] && dangerBullet[x][1] != 0 && dangerBullet[x+1][1] != 0)
			{
				return false;
			}
		return true;
	    
	    }

    public int[] selfLocation(BoardPiece[][] board)
    {
    	int[] selfpos = new int [2];
    	for(int y = 0; y <= bSize; y++)
    	{
			for(int x = 0; x <= bSize; x++){
				if (board[x][y] != null)
				{
					if (board[x][y].getName() == name)
					{
						selfpos[0]=x;
						selfpos[1]=y;
						////System.out.println(board[x][y]);
						x=bSize;
						y=bSize;

					}
				}
			}
    	}
    	return selfpos;
    }
    
    public int escapeClosePlayers(BoardPiece[][]board, int [][] bulletLocation, int posX, int posY)
    {
		int moveQuad = moveQuadLeast(board, bulletLocation, posX, posY);
		if (moveQuad != 0)
		{
			return moveQuad;
		}else
		{
			for(int targetY = -1; targetY <=1; targetY++)
				for(int targetX = -1; targetX <= 1; targetX ++)
				{
					try {
						BoardPiece x = board[posX+targetX][ posY+targetY];
						if (safeMove(board, bulletLocation, posX+targetX, posY+targetY))
						{
							if (targetX == -1)
							{
								if (targetY == -1)
									return 1;
								else if (targetY == 0)
									return 4;
								else 
									return 6;
							}else if (targetX == 0)
							{
								if (targetY == -1)
									return 2;
								else 
									return 7;
							}else 
							{
								if (targetY == -1)
									return 3;
								else if (targetY == 0)
									return 5;
								else 
									return 8;
							}
						}
					
					
					}catch (Exception e)
					{
						
					}
				}

		}
		return 0;
    }

    public boolean dangerClose (BoardPiece[][]board, int posX, int posY)
    {
    	if (closePlayers(-1, 0, posX, posY, board, 3) || closePlayers(-1, -1, posX, posY, board, 3) || closePlayers(0, -1, posX, posY, board, 3) || closePlayers(1, -1, posX, posY, board, 3) || closePlayers(1, 0, posX, posY, board, 3) || closePlayers(1, 1, posX, posY, board, 3) || closePlayers(0, 1, posX, posY, board, 3) || closePlayers(-1, 1, posX, posY, board, 3))
    		return true;
    	else
    		return false;
    }
    
    public int ShootClosePlayers(BoardPiece[][]board, int posX, int posY)
    {
    	if (closePlayers(-1, 0, posX, posY, board, 3))
    		return 12;
    	else if (closePlayers(-1, -1, posX, posY, board, 3))
    		return 9;
    	else if (closePlayers(0, -1, posX, posY, board, 3))
    		return 10;
    	else if (closePlayers(1, -1, posX, posY, board, 3))
    		return 11;
    	else if (closePlayers(1, 0, posX, posY, board, 3))
    		return 13;
    	else if (closePlayers(1, 1, posX, posY, board, 3))
    		return 16;
    	else if (closePlayers(0, 1, posX, posY, board, 3))
    		return 15;
    	else if (closePlayers(-1, 1, posX, posY, board, 3))
    		return 14;
    	else
    		return 0;
    	/*if ((board[selfPos[0]-1][selfPos[1]] != null && board[selfPos[0]-1][selfPos[1]].getName() != "Bullet" )||(board[selfPos[0]-2][selfPos[1]] != null && board[selfPos[0]-2][selfPos[1]].getName() != "Bullet" ))
    		return 12;
    	else if ((board[selfPos[0]-1][selfPos[1]-1] != null && board[selfPos[0]-1][selfPos[1]-1].getName() != "Bullet" )||(board[selfPos[0]-2][selfPos[1]-2] != null && board[selfPos[0]-2][selfPos[1]-2].getName() != "Bullet" )||(board[selfPos[0]-3][selfPos[1]-3] != null && board[selfPos[0]-3][selfPos[1]-3].getName() != "Bullet" ))
    		return 9;
    	else if ((board[selfPos[0]][selfPos[1]-1] != null && board[selfPos[0]][selfPos[1]-1].getName() != "Bullet" )||(board[selfPos[0]][selfPos[1]-2] != null && board[selfPos[0]][selfPos[1]-2].getName() != "Bullet" ))
    		return 10;
    	else if ((board[selfPos[0]+1][selfPos[1]-1] != null && board[selfPos[0]+1][selfPos[1]-1].getName() != "Bullet" )||(board[selfPos[0]+2][selfPos[1]-2] != null && board[selfPos[0]+2][selfPos[1]-2].getName() != "Bullet" ))
    		return 11;
    	else if ((board[selfPos[0]+1][selfPos[1]] != null && board[selfPos[0]+1][selfPos[1]].getName() != "Bullet" )||(board[selfPos[0]+2][selfPos[1]] != null && board[selfPos[0]+2][selfPos[1]].getName() != "Bullet" ))
    		return 13;
    	else if ((board[selfPos[0]+1][selfPos[1]+1] != null && board[selfPos[0]+1][selfPos[1]+1].getName() != "Bullet" )||(board[selfPos[0]+2][selfPos[1]+2] != null && board[selfPos[0]+2][selfPos[1]+2].getName() != "Bullet" ))
    		return 16;
    	else if ((board[selfPos[0]][selfPos[1]+1] != null && board[selfPos[0]][selfPos[1]+1].getName() != "Bullet" )||(board[selfPos[0]][selfPos[1]+2] != null && board[selfPos[0]][selfPos[1]+2].getName() != "Bullet" ))
    		return 15;
    	else if ((board[selfPos[0]-1][selfPos[1]+1] != null && board[selfPos[0]-1][selfPos[1]+1].getName() != "Bullet" )||(board[selfPos[0]-2][selfPos[1]+2] != null && board[selfPos[0]-2][selfPos[1]+2].getName() != "Bullet" ))
    		return 14;
    	else
    		return -1;
    		*/
    
    }
    
    public boolean closePlayers(int x, int y, int posX, int posY, BoardPiece[][]board, int distance)
    {
    	for( int a = 0; a <= distance; a++)
    	{
    		try{
    			if (board[posX+x*a][posY+y*a] != null && board[posX+x*a][posY+y*a].getName() != "Bullet" && board[posX+x*a][posY+y*a].getName() != name )
    		
    			{
    				return true;
    			}
    		}catch (Exception e){
    			return false;
    		}
    	}
    	return false;
    }

    public boolean safeMove (BoardPiece[][]board, int[][] bulletLocation, int MposX, int MposY)//Problematic
    {
		for (int y = -1; y <= 1; y++)
			for (int x = -1; x<= 1; x++)
    			if (closePlayers(x, y, MposX, MposY, board, 7))				
    			{
    				//System.out.println("Movement Not Safe");	
    				return false;
    			}
    	if (dangerBullets(board, bulletLocation, MposX, MposY)[0][1]!=200)//This part is problematic
		{
			//System.out.println("Movement Not Safe");	
			return false;
		}
    	else 
    	{
    		//System.out.println("Movement is Safe");
    		return true;
    	}
    }
    
    public int[][]  quadLeastPlayers(BoardPiece[][]board, int posX, int posY)
    {
    	// 0 | 1
    	// 3 | 2
    	int [][] quad = new int [4][2];
    	for (int x = 0; x < 4; x++)
		{
			quad[x][0] = quadrantPlayers (board, posX, posY, x);
			quad[x][1] = x;
		}
    	while (!(quad[0][0] <= quad[1][0] && quad[1][0] <= quad[2][0] &&quad[2][0] <= quad[3][0]))
    	{
    		for (int x = 0; x < 3; x++)
    		{
    			if (quad[x][0] > quad[x+1][0])
    				
				{
    				int temp = quad [x][0];
    				quad[x][0] = quad[x+1][0];
    				quad[x+1][0] = temp;
    				temp = quad [x][1];
    				quad[x][1] = quad[x+1][1];
    				quad[x+1][1] = temp;
				}
    		}
    	}
    	return quad;
    }
    
    public int quadrantPlayers (BoardPiece[][]board, int posX, int posY, int quad)
    {
		int iniX = 0;
		int iniY = 0;
		int finalX = 0;
		int finalY = 0;
		int players = 0;
    	if (quad == 0)
    	{
    		iniX = 0;
    		iniY = 0;
    		finalX = posX;
    		finalY = posY;
    	}else if (quad == 1)
    	{
    		iniX = posX;
    		iniY = 0;
    		finalX = bSize;
    		finalY = posY;
    	}else if (quad == 2)
    	{
    		iniX = posX;
    		iniY = posY;
    		finalX = bSize;
    		finalY = bSize;
    	}else 
    	{
    		iniX = 0;
    		iniY = posY;
    		finalX = posX;
    		finalY = bSize;
    	}
    	for (int y = iniY; y <= finalY; y++)
    		for (int x = iniX; x <= finalX; x++)
    		{
    			if(board[x][y] != null && board[x][y].getName() != "Bullet" &&  board[x][y].getName() != name)
    				players++;
    		}
    	return players;
    }
    

    public int inCornerShoot (BoardPiece[][] board, int posX, int posY)
    {
    	//Look for someone to shoot;
    	for(int x = 0; x <= bSize; x++)
    	{
        	if (closePlayers(-1, 0, posX, posY, board, x))
        		return 12;
        	else if (closePlayers(-1, -1, posX, posY, board, x))
        		return 9;
        	else if (closePlayers(0, -1, posX, posY, board, x))
        		return 10;
        	else if (closePlayers(1, -1, posX, posY, board, x))
        		return 11;
        	else if (closePlayers(1, 0, posX, posY, board, x))
        		return 13;
        	else if (closePlayers(1, 1, posX, posY, board, x))
        		return 16;
        	else if (closePlayers(0, 1, posX, posY, board, x))
        		return 15;
        	else if (closePlayers(-1, 1, posX, posY, board, x))
        		return 14;
    	}
    	if (posX == 0)
    	{
    		if(posY == bSize)
    		{
    			int x = rand(0,2);
    			if(x == 0)
    				return 10;
    			else if(x == 1)
    				return 11;
    			else 
    				return 13;
    		}else if(posY == 0)
    		{
    			int x = rand(0,2);
    			if(x == 0)
    				return 13;
    			else if(x == 1)
    				return 15;
    			else 
    				return 16;
    		}
    	}else if (posX == bSize)
    	{
    		if(posY == bSize)
    		{
    			int x = rand(0,2);
    			if(x == 0)
    				return 10;
    			else if(x == 1)
    				return 9;
    			else 
    				return 12;
    		}else if(posY == 0)
    		{
    			int x = rand(0,2);
    			if(x == 0)
    				return 12;
    			else if(x == 1)
    				return 15;
    			else 
    				return 14;
    		}
    	}
    		return 0;
    }
}

