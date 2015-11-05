package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Board extends JPanel implements Runnable{
	//dimensions
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;
	//game thread
	private Thread thread;
	private boolean running; 
	private int FPS = 60;
	private long targetTime = 1000/FPS; 

	//image
	private BufferedImage image;
	private Graphics2D g;

	//game vars:
	static int boardSize = 15;
	int pieceSize = HEIGHT/boardSize;
	private static BoardPiece[][] board = new BoardPiece[boardSize][boardSize];
	long waitTime = 0;
	LinkedList<BoardPiece> graveyard = new LinkedList<BoardPiece>();
	static ArrayList<Point> playerCoor = new ArrayList<Point>();

	public void init(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		board = new BoardPiece[boardSize][boardSize];
		// load and place players
		loadPlayers();
		draw();
	}

	private static void loadPlayers(){		// load and place players
		playerCoor.clear();
		
		board[0][0] = new Sunny();
		playerCoor.add(new Point(0,0));
		board[boardSize-1][0] = new Wesley();
		playerCoor.add(new Point(boardSize-1,0));
		
		for(int x=0; x<20; x++){	// loading t20 more random players just to stress test, comment out as needed 
			int a = rand(0,boardSize-1), b = rand(0,boardSize-1);
			board[a][b] = new Sunny();
			playerCoor.add(new Point(a,b));
			board[a][b].setName(String.valueOf(x));

		}
	}

	public void run(){								// runs game
		init();

		long start, elapsed, wait;					//Vars to keep track of game's run times
		while(running){
			start = System.nanoTime();
			draw();

			update();
			drawToScreen();

			elapsed = System.nanoTime() - start;
			wait = targetTime - (elapsed/=1000000);
			if(wait <0) wait = 0;
			try{
				Thread.sleep(wait);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//wait
			try {
				thread.sleep(waitTime);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	private void draw() {					// constantly draw the sprites and board
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);

		for(int x=0; x<=boardSize; x++)			// draw grid
			g.drawLine(pieceSize*x,0,pieceSize*x,HEIGHT);

		for(int y=0; y<=boardSize; y++)			// draw grid
			g.drawLine(0,pieceSize*y,pieceSize*boardSize,pieceSize*y);

		for(int x=0; x<boardSize; x++)			// draw players
			for(int y=0; y<boardSize; y++){
				g.drawString(board[x][y]==null?"Empty":board[x][y].getName(), x*pieceSize+5, y*pieceSize + 15);
				if(board[x][y]!=null){		//fills the square blue if it's a player or bullet (easier to see until we get sprites)
					g.setColor(Color.blue);
					g.fillRect(x*pieceSize,y*pieceSize,pieceSize,pieceSize);
				}
			}

	}

	private void makeMove(int move, int curX, int curY, BoardPiece[][] field, int coorInd){
		/* Given the move, current x and y, the board, and the index in playerCoor:
		 * either moves the player or shoots. 'speedX' is the players x velocity, 'speedY' is the
		 * players y velocity. For example if player entered 4 as thier move according to this control scheme:
		 *   1  2  3
		 *   4  0  5
		 *	 6  7  8
		 * the player's speedX would be -1 (move to the left) and speedY would be 0 (only moving to the left)
		 */
		int speedX = 0, speedY=0;
		if(move==1){			//these if and else statments set the player's velocity based on move 
			speedX = -1;
			speedY = -1;
		}
		else if(move ==0){
			speedX = 0;
			speedY = 0;
		}
		else if(move ==2){
			speedX = 0;
			speedY = -1;
		}
		else if(move ==3){
			speedX = 1;
			speedY = -1;
		}
		else if(move ==4){
			speedX = -1;
			speedY = 0;
		}
		else if(move ==5){
			speedX = 1;
			speedY = 0;
		}
		else if(move ==6){
			speedX = -1;
			speedY = 1;
		}
		else if(move ==7){
			speedX = 0;
			speedY = 1;
		}
		else if(move ==8){
			speedX = 1;
			speedY = 1;
		}
		else if(move ==9){	//shooting TODO

		}
		else if(move ==10){

		}
		else if(move ==11){

		}
		else if(move ==12){

		}
		else if(move ==13){

		}
		else if(move ==14){

		}
		else if(move ==15){

		}
		else if(move ==16){

		}
		else{			//player loses a turn if move is outside the domain 0<=move<=16
			return;
		}
		
		if(curX+speedX>=boardSize||curX+speedX<0||curY+speedY>=boardSize||curY+speedY<0)   // if player tries to move outside the board (arrayOutOfBounds) they lose a turn
			return;
		// movement
		if(field[curX+speedX][curY+speedY]!=null && field[curX+speedX][curY+speedY].getName()=="Bullet"){
			System.out.println(board[curY][curY].getName()+" walked into " + field[curX+speedX][curY+speedY].getOwner() +  "'s Bullet and died!");
			killPiece(curX,curY,coorInd);
		}
		else if(field[curX+speedX][curY+speedY]!=null){
			// do nothing
		}
		else if(field[curX+speedX][curY+speedY]==null){		// legitimate move, moving to new coordinates
			board[curX+speedX][curY+speedY] = board[curX][curY];
			//System.out.print(playerCoor.get(coorInd).x+" to -> ");
			playerCoor.set(coorInd, new Point(curX+speedX,curY+speedY));
			//System.out.println(playerCoor.get(coorInd).x+" //"+board[curX][curY].getName());
			board[curX][curY] = null;
		}
		else{
			//player is trying to move into a place where another player is 
			//do nothing i guess (they lose a turn) (may change this?)
		}
		//shooting
		//TODO
	}

	private void killPiece(int x, int y,int p){
		if(board[x][y]!=null&& board[x][y].getName()!="Bullet")		//don't add nothings and bullets to the graveyard
			graveyard.add(board[x][y]);
		board[x][y] = null;
		playerCoor.remove(p);
	}

	private void update() {								// updates current game state

		// Copy of board
		BoardPiece[][] boardCopy =new BoardPiece[boardSize][boardSize];
		for(int x=0; x<boardSize; x++)			
			for(int y=0; y<boardSize; y++){
				boardCopy[x][y] = board[x][y];
				//boardCopy[x][y].setName(board[x][y].getName());
			}
		//get moves		
		for(int p=0; p<playerCoor.size(); p++){
			int x = playerCoor.get(p).x;
			int y = playerCoor.get(p).y;
			String focus = board[x][y].getName();
			try{
				makeMove(board[x][y].move(boardCopy)
						,x,y,board,p);
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(x+","+y);
				for(int a=0; a<playerCoor.size(); a++)
					System.out.println("player at "+playerCoor.get(a).x+", "+playerCoor.get(a).y);
				System.out.println(focus+" died due to illegal output. Or code crashing.");
				killPiece(x,y,p);
			}
		}
		//do constant computation
	}

	private void drawToScreen() {						// scales and draws game with formating
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH ,  HEIGHT , null);
		g2.dispose();
	}
	
	// Check to see if a bullet collides with something
	public void checkCollides(BoardPiece[][] field, int curX, int curY){
		// Checking to see if the space is occupied.
		if (field[curX-1][curY-1] != null){
			if (field[curX-1][curY-1].getName() == "Bullet")
				field[curX-1][curY-1] = null;
			else {
				System.out.println(field[curX-1][curY-1].getName() + " has been killed by ")
				field[curX-1][curY-1].getName
			}
				
		}
			
		field[curX-1][curY-1] = new Bullet(1, field[curX][curY].getName());	// Creates a bullet in the proper location, with the 
	}
	
	public static int rand(double d, double e){
		return (int) (d + (int)(Math.random()*((e-d)+1)));
	}

	public Board(){			// Game constructor
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify(){				// declares parent status and adds listeners
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
}
