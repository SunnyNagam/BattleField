package main;

public class Sunny extends BoardPiece{
	public Sunny(){
		setName("Sunny");
	}
	@Override
	public int move(BoardPiece[][] board) {
		int x =0, y=0;
		
		for(x=0; x<board.length; x++)					//Finding my x and y
			for(y=0; y<board[x].length; y++)
				if(board[x][y].getName()=="Sunny")
					break;
		
		return 5;
	}

}
