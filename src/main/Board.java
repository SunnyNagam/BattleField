package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Board extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	// Dimensions
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;

	// Game thread
	private Thread thread;
	private boolean running; 
	private int FPS = 60;
	private long targetTime = 1000/FPS; 

	// Image vars
	private BufferedImage image;
	private Graphics2D g;

	//Information Log Text Box
	private TextArea textArea;
	// Size of the text log
	private int log_length = 100;   // 100 characters per line
	private String log;

	// Game vars
	static int boardSize = 100;
	static int numRandBots = 100;
	boolean drawGrid = false;
	boolean gameover = false;
	int pieceSize = HEIGHT/boardSize;
	private static BoardPiece[][] board = new BoardPiece[boardSize][boardSize];
	long waitTime = 0;

	// Store the defeated players
	LinkedList<BoardPiece> graveyard = new LinkedList<BoardPiece>();

	// Store the coordinates of all the players 
	static ArrayList<Point> playerCoor = new ArrayList<Point>();
	// Store the coordinates of all the bullets
	static ArrayList<Point> bulletCoor = new ArrayList<Point>();

	//miscellaneous variables
	int killBoardCount = 5;
	int[] topKills = new int[killBoardCount];
	String[] topKillers = new String[killBoardCount];


	public void init(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		board = new BoardPiece[boardSize][boardSize];


		// Creating info log
		textArea = new TextArea("Battle has begun.", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea.setBounds(HEIGHT, 0, WIDTH-HEIGHT, HEIGHT/2);
		add(textArea);										// Add it to the screen
		setLayout(new BorderLayout());						// Required
		textArea.setEditable(false);						// Preventing the box from being editable
		textArea.setFont(new Font("Serif", Font.PLAIN, 19));// Setting font
		log ="";

		//clearing kill board
		for(int x=0; x<killBoardCount; x++){
			topKills[x] = 0;
			topKillers[x] = "";
		}

		// load and place players
		loadPlayers();
		draw();
	}

	private static void loadPlayers(){		// Load and place players
		playerCoor.clear();

		board[0][0] = new Sunny();
		playerCoor.add(new Point(0,0));
		board[boardSize-1][0] = new Wesley();
		playerCoor.add(new Point(boardSize-1,0));

		for(int x=0; x<numRandBots; x++){	// Loading 20 more random players just to stress test, comment out as needed 
			int a = rand(0,boardSize-1), b = rand(0,boardSize-1);
			board[a][b] = new Sunny();
			playerCoor.add(new Point(a,b));
			board[a][b].setName(String.valueOf("Player "+x));

		}
	}

	// Runs game
	public void run(){
		init();

		// Vars to keep track of game's run times
		long start, elapsed, wait;					
		while(running){
			start = System.nanoTime();
			draw();
			if(!gameover)
				update();
			drawToScreen();

			elapsed = System.nanoTime() - start;
			wait = targetTime - (elapsed/=1000000);
			if(wait < 0) wait = 0;
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

		if(drawGrid){
			for(int x=0; x<=boardSize; x++)			// draw grid
				g.drawLine(pieceSize*x,0,pieceSize*x,HEIGHT);

			for(int y=0; y<=boardSize; y++)			// draw grid
				g.drawLine(0,pieceSize*y,pieceSize*boardSize,pieceSize*y);
		}

		for(int x=0; x<boardSize; x++)			// draw players
			for(int y=0; y<boardSize; y++){
				//g.drawString(board[x][y]==null?"Empty":board[x][y].getName(), x*pieceSize+5, y*pieceSize + 15);
				if(board[x][y]!=null){		//fills the square blue if it's a player or bullet (easier to see until we get sprites)
					if(board[x][y].getName()=="Bullet"){
						g.setColor(Color.red);
					}
					else
						g.setColor(Color.blue);
					g.fillRect(x*pieceSize,y*pieceSize,pieceSize,pieceSize);
					g.setColor(Color.black);
				}
			}
		
		//drawing leaderboard
		g.drawString("LEADERBOARD", HEIGHT+20, HEIGHT/2+20);
		for(int x=0; x<killBoardCount; x++){
			g.drawString(new String((x+1)+".)    "+topKillers[x]+"    Kills: "+topKills[x]), HEIGHT+25, (HEIGHT/2+20)+((x+1)*20));
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
		if(move == 1 || move == 9){			// If and else statements set the player's/bullet's velocity (direction) based on move's value
			speedX = -1;
			speedY = -1;
		}
		else if(move == 0){
			speedX = 0;
			speedY = 0;
		}
		else if(move == 2 || move == 10){
			speedX = 0;
			speedY = -1;
		}
		else if(move == 3 || move == 11){
			speedX = 1;
			speedY = -1;
		}
		else if(move == 4 || move == 12){
			speedX = -1;
			speedY = 0;
		}
		else if(move == 5 || move == 13){
			speedX = 1;
			speedY = 0;
		}
		else if(move == 6 || move == 14){
			speedX = -1;
			speedY = 1;
		}
		else if(move == 7 || move == 15){
			speedX = 0;
			speedY = 1;
		}
		else if(move == 8 || move == 16){
			speedX = 1;
			speedY = 1;
		}
		else{			// Player loses a turn if move is outside the domain 0 <= move <= 16
			return;
		}
		if (curX + speedX >= boardSize || curX + speedX < 0 || curY + speedY >= boardSize || curY + speedY < 0){   // If player tries to move outside the board (arrayOutOfBounds) they lose a turn
			return;
		}
		//Executing player actions
		if (move < 9){	// Executing  movement
			// Movement
			if(board[curX+speedX][curY+speedY]!=null && board[curX+speedX][curY+speedY].getName()=="Bullet"){
				log+=new String(align((board[curX][curY].getName()+" walked into " + board[curX+speedX][curY+speedY].getOwner() +  "'s bullet and died!")));
				log+="\n";
				//somehow increase player kill
				killPiece(curX,curY,coorInd,board[curX+speedX][curY+speedY].getOwner());	//kills player
				//delete the bullet you ran into
				board[curX+speedX][curY+speedY] = null;
				return;
			}
			else if(board[curX+speedX][curY+speedY]!=null){
				// Do nothing, player tried to walk into another player
				return;
			}
			else{		// Legitimate move, moving to new coordinates
				board[curX+speedX][curY+speedY] = board[curX][curY];
				//System.out.print(playerCoor.get(coorInd).x+" to -> ");
				playerCoor.set(coorInd, new Point(curX+speedX,curY+speedY));
				//System.out.println(playerCoor.get(coorInd).x+" //"+board[curX][curY].getName());
				board[curX][curY] = null;
				return;
			}
		}
		else {	// Executing shooting (move>=9)
			// Checking to see if the space is occupied.
			if (board[curX+speedX][curY+speedY] != null){
				if (board[curX+speedX][curY+speedY].getName() == "Bullet"){	// If two bullets collide, both bullets annihilate each other
					board[curX+speedX][curY+speedY] = null;
				}
				else {
					log+=new String(board[curX+speedX][curY+speedY].getName() + " has been killed by " + field[curX][curY].getName() + " at point blank!");
					log+="\n";
					killPiece(curX+speedX,curY+speedY,coorInd,board[curX][curY].getName());	//kills player
				}
			}

			else{
				board[curX+speedX][curY+speedY] = new Bullet(move-8, field[curX][curY].getName(),coorInd);	// Creates a bullet in the proper location,
				bulletCoor.add(new Point(curX+speedX,curY+speedY));
			}
		}
	}
	private void makeMoveBullet(int move, int curX, int curY, BoardPiece[][] field, int coorInd){
		/* Given the move, current x and y, the board, and the index in playerCoor:
		 * either moves the player or shoots. 'speedX' is the players x velocity, 'speedY' is the
		 * players y velocity. For example if player entered 4 as thier move according to this control scheme:
		 *   1  2  3
		 *   4  0  5
		 *	 6  7  8
		 * the player's speedX would be -1 (move to the left) and speedY would be 0 (only moving to the left)
		 */
		int speedX = 0, speedY=0;
		if(move == 1 || move == 9){			// If and else statements set the player's/bullet's velocity (direction) based on move's value
			speedX = -1;
			speedY = -1;
		}
		else if(move == 0){
			speedX = 0;
			speedY = 0;
		}
		else if(move == 2 ){
			speedX = 0;
			speedY = -1;
		}
		else if(move == 3){
			speedX = 1;
			speedY = -1;
		}
		else if(move == 4){
			speedX = -1;
			speedY = 0;
		}
		else if(move == 5){
			speedX = 1;
			speedY = 0;
		}
		else if(move == 6){
			speedX = -1;
			speedY = 1;
		}
		else if(move == 7){
			speedX = 0;
			speedY = 1;
		}
		else if(move == 8){
			speedX = 1;
			speedY = 1;
		}
		else{			// Player loses a turn if move is outside the domain 0 <= move <= 8
			return;
		}
		if (curX + speedX >= boardSize || curX + speedX < 0 || curY + speedY >= boardSize || curY + speedY < 0){   // If bullet tries to move outside the board (arrayOutOfBounds) destroy bullet
			if(board[curX][curY].getName()=="Bullet"){
				board[curX][curY] = null;
				bulletCoor.remove(coorInd);
			}
			return;
		}
		// Executing bullet actions		
		if (board[curX+speedX][curY+speedY] != null){
			if (board[curX+speedX][curY+speedY].getName() == "Bullet"){	// If two bullets collide, both bullets annihilate each other
				board[curX+speedX][curY+speedY] = null;
				board[curX][curY] = null;
				bulletCoor.remove(coorInd);
				return;
			}
			else {
				log+=new String(board[curX+speedX][curY+speedY].getName() + " has been killed by " + board[curX][curY].getOwner() + "!");
				log+="\n";
				killPiece(curX+speedX,curY+speedY,coorInd,board[curX][curY].getOwner());	//kills player
				board[curX][curY] = null;
				bulletCoor.remove(coorInd);
				return;
			}
		}
		else{	// Moving the bullet
			board[curX+speedX][curY+speedY] = board[curX][curY];
			bulletCoor.set(coorInd, new Point(curX+speedX,curY+speedY));
			board[curX][curY] = null;
			return;
		}


	}

	private void killPiece(int x, int y, int p, String killer){
		if (board[x][y] != null && board[x][y].getName() != "Bullet")		// Don't add nothings and bullets to the graveyard
			graveyard.add(board[x][y]);					//graveyard for post game statistics maybe

		int kills=0;
		for(int bx=0; bx<boardSize; bx++)						// award kill to killer
			for(int by=0; by<boardSize; by++)
				if(board[bx][by]!=null&&board[bx][by].getName().equals(killer)){
					board[bx][by].incrementKills();
					kills = board[bx][by].getKills();
				}

		board[x][y] = null;				// remove killed player

		//recalculate kill board (leaderboard)
		String curKiller = killer;
		for(int pos=0; pos<killBoardCount; pos++){
			if(kills>topKills[pos]){
				int temp = kills;
				kills = topKills[pos];
				topKills[pos] = temp;
				String tem = curKiller;
				curKiller = topKillers[pos];
				topKillers[pos] = tem;
			}
		}
	}

	private void update() {								// Updates current game state
		// Copy of board
		BoardPiece[][] boardCopy = new BoardPiece[boardSize][boardSize];
		for (int x = 0; x < boardSize; x++)			
			for (int y = 0; y < boardSize; y++){
				boardCopy[x][y] = board[x][y];
			}
		// Get moves		
		for (int p = 0; p < playerCoor.size(); p++){
			int x = playerCoor.get(p).x;
			int y = playerCoor.get(p).y;
			String focus ="";
			try{
				focus = board[x][y].getName();
			}catch(Exception e){
				playerCoor.remove(p);
				p--;
				if(playerCoor.size()==1){
					//only one guy left
					log = "";
					for(int n=0; n<4; n++)
						log+="\n";
					log+=board[playerCoor.get(0).x][playerCoor.get(0).y].getName()+" WON!!!!";
					for(int n=0; n<5; n++)
						log+="\n";
					textArea.setText(log);
					gameover = true;
					break;
				}
				continue;
			}
			try{
				makeMove(board[x][y].move(boardCopy)
						,x,y,board,p);
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(x + "," + y);
				System.out.println(focus + " died due to illegal output. Or code crashing.");
				killPiece(x,y,p,"IllegalOutput");
			}
		}

		// Move Bullets
		for (int times = 0; times <1; times++)		// Speed of bullets (distance covered per turn, currently set at two tiles per turn)
			for (int b = 0; b < bulletCoor.size(); b++){
				int x = bulletCoor.get(b).x;
				int y = bulletCoor.get(b).y;
				try{
					board[x][y].getName();
				}catch(Exception e){
					bulletCoor.remove(b);
					continue;
				}
				makeMoveBullet(board[x][y].move(board), x, y, board, b);
				
			}

		//do constant computation
		textArea.setText(log);
		textArea.setCaretPosition(textArea.getText().length()); //auto scrool to bottom
	}

	private void drawToScreen() {						// scales and draws game with formating
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH,  HEIGHT, null);
		g2.dispose();
	}

	public String align(String input){							 	//Aligns text
		char[] text = input.toCharArray(); 		 	//Converts the content of scene into char array
		for (int i = 0, count = 0, word = 0; i < text.length; i++){
			count++;											 	//i means the point in the array, count tracks how far into each line the text is
			word++;												 	//word tracks how long the current word is
			if (input.charAt(i) == ' ')	 		 	//If a space is found, the length of the word is reset
				word = 0;
			else if (input.charAt(i) == (char)10)
				count = 0;											//Resetting the count on a new line
			if (count >= log_length){									 	//If the length of the text exceeds max,
				i-=word;										 	//Move the 'place' back to before the current word
				count = 0;										 	//Change it to a new line and reset values
				word = 0;
				text[i] = (char)10;
			}

		}
		return(String.valueOf(text));
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