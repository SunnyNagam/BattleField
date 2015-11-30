package main;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Remo extends BoardPiece 
{

	public Remo(int pieceSize)
	{
		setName("Remo");
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


	public int move(BoardPiece[][] board)
	{		
		
		int x = 0, y=0;
		int random = (int)(Math.random()*8+1);

		int found = 0;
		for(y=0; y<board.length; y++)
		{
			for(x=0; x<board.length; x++)
			{
				if(board[y][x].getName().equals("Remo"))
				{
					found = 1;
					break;
				}
			}
			if(found==1)
				break;
		}
	
		if(board[y-1][x-1].getName()!=null|| board[y-1][x-1].getName().equals("Bullet")||board[y-1][x+1].getName()!=null||board[y-1][x+1].getName().equals("Bullet")||board[y+1][x-1].getName()!=null||board[y+1][x-1].getName().equals("Bullet")||board[y+1][x+1].getName()!=null||board[y+1][x+1].getName().equals("Bullet"))
		{
			int shoot = 0;

			if(board[y-1][x-1].getName()!=null|| board[y-1][x-1].getName().equals("Bullet"))
				shoot=9;
			else if(board[y-1][x+1].getName()!=null||board[y-1][x+1].getName().equals("Bullet"))
				shoot=11;
			else if(board[y+1][x-1].getName()!=null||board[y+1][x-1].getName().equals("Bullet"))
				shoot=14;
			else if(board[y+1][x+1].getName()!=null||board[y+1][x+1].getName().equals("Bullet"))
				shoot=16;

			return shoot;
		}
		
		else if(random==2 && board[y-2][x].getName()!=null || random == 4 && board[y][x-2].getName()!=null|| random == 5 && board[y][x+2].getName()!=null|| random==7 && board[y+2][x].getName()!=null)
		{
			int step=0;
			
			if(board[y-1][x].getName()==null)
				step=2;
			else if(board[y][x-1].getName()==null)
				step=4;
			else if(board[y][x+1].getName()==null)
				step=5;
			else if(board[y+1][x].getName()==null)
				step=7;
			
			else if(board[y-1][x-1].getName()==null)
				step=1;
			else if(board[y-1][x+1].getName()==null)
				step=3;
			else if(board[y+1][x-1].getName()==null)
				step=6;
			else if(board[y+1][x+1].getName()==null) 
				step=8;
			
			return step;
		}


		return random;

	}
}