package main;

public class Sunny extends BoardPiece{
	public Sunny(){
		setName("Sunny");
	}
	
	public int move(BoardPiece[][] board) {
		int x =0, y=0;
		boolean found = false;
		for(; x<board.length; x++){					//Finding my x and y
			for(y=0; y<board[x].length; y++){
				if(board[x][y]!= null && board[x][y].getName()==getName()){
					found = true;
					break;
				}
			}
			if(found) break;
		}
//		if(x<board.length-1||!found){
//			if(board[x+1][y]==null)
//				return 5;
//			else if(x>0)
//				return 4;
//		}
//		return 0;
		return rand(0,16);
	}

}
