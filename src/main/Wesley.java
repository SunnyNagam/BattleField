package main;

import java.util.ArrayList;

public class Wesley extends BoardPiece{
	ArrayList<Integer> ints = new ArrayList<Integer>();
	int count =0;
	public Wesley(){
		setName("Wesley");
	}
	@Override
	public int move(BoardPiece[][] board) {
		ints.add(count++);
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
//		if(x>0||!found){
////			if(board[x-1][y]==null)
////				return 4;
////			else if(x<board.length-1)
////				return 5;
//			return 4;
//		}
//		return 0;
		return rand(6,16);
	}
}
