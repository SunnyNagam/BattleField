package main;

public class Sunny extends BoardPiece{
	public Sunny(){
		setName("Sunny");
	}
	
	public int move(BoardPiece[][] board) {
		int x =0, y=0;
		
		for(; x<board.length; x++)					//Finding my x and y
			for(; y<board[x].length; y++)
				if(board[x][y]!= null &&board[x][y].getName()==getName())
					break;
		System.out.println(x);
		if(x<board.length)
			return 5;
		else
			return 0;
	}

}
