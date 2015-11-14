package main;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Sunny extends BoardPiece{
	public Sunny(int pieceSize){
		setName("Sunny");
		try {
			setImage(ImageIO.read(getClass().getResourceAsStream("/Images/"+getName()+".png")));
		}
		catch (IOException e){
			e.printStackTrace();
		}
		//setImage(resizeImage(getImage(), pieceSize));	// doesnt work
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
		BoardPiece[][] around = new BoardPiece[3][3];
		int[][] buldir = new int[3][3];
		boolean[][] dangerflag = new boolean[3][3];
		for(int a = x-1, bx=0; a<x+2; a++,bx++)								//getting 3 by 3 map and bullet directions around me
			for(int b=y-1, by=0;b<y+2&&a>=0&&a<=board.length;b++,by++){
				if(b>=0&&b<=board.length&&board[a][b]!=null){
					around[bx][by] = board[a][b];
					if(board[a][b].getName().equals("Bullet")){
						buldir[bx][by] = board[a][b].getvX();
					}
				}
			}
//		for(int ax=0; ax<3; ax++)
//			for(int ay=0; ay<3; ay++){
//				BoardPiece b = around[ax][ay];
//				if(b!=null){
//					if(!b.getName().equals("Bullet")){
//						dangerflag[ax][ay] = true;
//						continue;
//					}
//					if(ax == 0 && ay==0){
//						
//					}
//					else if(ax == 0 && ay==1){
//
//					}
//					else if(ax == 0 && ay==2){
//
//					}
//					else if(ax == 1 && ay==0){
//
//					}
//					else if(ax == 1 && ay==1){
//
//					}
//					else if(ax == 1 && ay==2){
//
//					}
//					else if(ax == 2 && ay==0){
//
//					}
//					else if(ax == 2 && ay==1){
//
//					}
//					else if(ax == 2 && ay==2){
//
//					}
//				}
//			}
//				for(int a = x-1; a<x+2; a++)
//					for(int b=y-1;b<y+2;b++){
//						try{
//							if(board[a][b]!=null){
//								int c = a-x,d=b-y;
//								if(c==-1&&d==-1){
//									return 1+8;
//								}
//								else if(c==-1&&d==0){
//									return 4+8;
//								}
//								else if(c==-1&&d==1){
//									return 6+8;
//								}
//								else if(c==0&&d==-1){
//									return 2+8;
//								}
//								else if(c==0&&d==1){
//									return 7+8;
//								}
//								else if(c==1&&d==-1){
//									return 3+8;
//								}
//								else if(c==1&&d==0){
//									return 5+8;
//								}
//								else if(c==1&&d==1){
//									return 8+8;
//								}
//							}
//						}catch(Exception e){
//							continue;
//						}
//					}
		return rand(0,16);
	}

}
