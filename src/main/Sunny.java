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
		for(int a = x-1; a<x+2; a++)
			for(int b=y-1;b<y+2;b++){
				try{
					if(board[a][b]!=null){
						int c = a-x,d=b-y;
						if(c==-1&&d==-1){
							return 1+8;
						}
						else if(c==-1&&d==0){
							return 4+8;
						}
						else if(c==-1&&d==1){
							return 6+8;
						}
						else if(c==0&&d==-1){
							return 2+8;
						}
						else if(c==0&&d==1){
							return 7+8;
						}
						else if(c==1&&d==-1){
							return 3+8;
						}
						else if(c==1&&d==0){
							return 5+8;
						}
						else if(c==1&&d==1){
							return 8+8;
						}
					}
				}catch(Exception e){
					continue;
				}
			}
		return rand(0,16);
	}

}
