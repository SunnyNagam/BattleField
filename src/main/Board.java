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
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	// Dimensions
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;

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
	private String log;

	// Game vars
	static int boardSize = 27;
	static int numRandBots = 27*26;
	boolean drawGrid = false;
	boolean gameover = false;
	static int pieceSize = HEIGHT/boardSize;
	private static BoardPiece[][] board = new BoardPiece[boardSize][boardSize];
	long waitTime = 100;			// pause between turns
	private int noKillTurns = 0;

	// Store the defeated players
	LinkedList<BoardPiece> graveyard = new LinkedList<BoardPiece>();

	// Store the coordinates of all the players 
	static ArrayList<Point> playerCoor = new ArrayList<Point>();
	// Store the coordinates of all the bullets
	static ArrayList<Point> bulletCoor = new ArrayList<Point>();

	// Leaderboard variables
	int killBoardCount = 10;
	// The highest kills
	int[] topKills = new int[killBoardCount];
	// The players who got those kills
	String[] topKillers = new String[killBoardCount];

	static int a =0;
	static int b=0;
	public void init(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		board = new BoardPiece[boardSize][boardSize];

		// Creating info log
		textArea = new TextArea("Battle is beginning...", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea.setBounds(HEIGHT, 0, WIDTH-HEIGHT, HEIGHT/2);
		add(textArea);										// Add it to the screen
		setLayout(new BorderLayout());						// Required
		textArea.setEditable(false);						// Preventing the box from being editable
		textArea.setFont(new Font("Serif", Font.PLAIN, 19));// Setting font
		log ="";
		// Clearing kill board
		for(int x=0; x<killBoardCount; x++){
			topKills[x] = 0;
			topKillers[x] = "";
		}

		// Load and place players
		loadPlayers();
		draw();
	}
	public static void sC(){
		a = rand(0,boardSize-1);
		b = rand(0,boardSize-1);
		while(board[a][b]!=null){
			a = rand(0,boardSize-1);
			b = rand(0,boardSize-1);
		}
	}
	// Load and place players
	private static void loadPlayers(){		
		playerCoor.clear();
		
		sC();
		board[a][b] = new Charlie(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new Ali(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new Ammar(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new Amnah(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new Claire(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new DaFangsta(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new davis(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
//		board[a][b] = new HosunLee(pieceSize);		//Replace "Wesley" With the name of your class
//		playerCoor.add(new Point(a,b));
//		sC();
		board[a][b] = new JWang(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new Maggie(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new Remo(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new Sam(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new Sanchit(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		sC();
		board[a][b] = new Yaamz(pieceSize);		//Replace "Wesley" With the name of your class
		playerCoor.add(new Point(a,b));
		

		for(int x=0; x<numRandBots; x++){	// Loading numBoard more random players just to stress test, comment out as needed 
			a = rand(0,boardSize-1);
			b = rand(0,boardSize-1);
			while(board[a][b]!=null){
				a = rand(0,boardSize-1);
				b = rand(0,boardSize-1);
			}
			// Players are added here
			board[a][b] =new Wesley(pieceSize);
			playerCoor.add(new Point(a,b));
			board[a][b].setName(String.valueOf("Jacob "+x));
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
			// Wait
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	private void draw() {					// Constantly draws board
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);

		if(drawGrid){
			for(int x=0; x<=boardSize; x++)			// draw grid
				g.drawLine(pieceSize*x,0,pieceSize*x,HEIGHT);

			for(int y=0; y<=boardSize; y++)			// draw grid
				g.drawLine(0,pieceSize*y,pieceSize*boardSize,pieceSize*y);
		}

		// Players
		for (int a = 0; a < playerCoor.size(); a++){
			if(board[playerCoor.get(a).x][playerCoor.get(a).y]!=null)
				g.drawImage(board[playerCoor.get(a).x][playerCoor.get(a).y].getImage(), playerCoor.get(a).x*pieceSize, playerCoor.get(a).y*pieceSize, null);
		}
		// Bullets
		for (int a = 0; a < bulletCoor.size(); a++){
			if(board[bulletCoor.get(a).x][bulletCoor.get(a).y]!=null)
				g.drawImage(board[bulletCoor.get(a).x][bulletCoor.get(a).y].getImage(), bulletCoor.get(a).x*pieceSize, bulletCoor.get(a).y*pieceSize, null);
		}


		// Drawing leaderboard
		g.drawString("LEADERBOARD", HEIGHT+20, HEIGHT/2+20);
		for(int x=0; x<killBoardCount; x++){
			g.drawString(new String((x+1)+".)    "+topKillers[x]+"    Kills: "+topKills[x]), HEIGHT+25, (HEIGHT/2+20)+((x+1)*20));
		}
		
		g.drawString("Turns since last kill: " + noKillTurns, HEIGHT, HEIGHT-HEIGHT/6);
	}

	private void makeMove(int move, int curX, int curY, int coorInd){
		/* Given the move, current x and y, the board, and the index in playerCoor:
		 * either moves the player or shoots. 'speedX' is the players x velocity, 'speedY' is the
		 * players y velocity. For example if player entered 4 as thier move according to this control scheme:
		 *   1  2  3
		 *   4  0  5
		 *	 6  7  8
		 * the player's speedX would be -1 (move to the left) and speedY would be 0 (only moving to the left)
		 */

		int speedX = 0, speedY=0;
		// To track if the player is shooting or moving
		boolean shooting = false;
		if (move >= 9){
			shooting = true;
			move -= 8 ;						//Simplifies shooting process
		}
		if(move == 1){			// If and else statements set the player's/bullet's velocity (direction) based on move's value
			speedX = -1;
			speedY = -1;
		}
		else if(move == 2){
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
		else{			// Player loses a turn if move is outside the domain 0 <= move <= 16
			return;
		}
		if (curX + speedX >= boardSize || curX + speedX < 0 || curY + speedY >= boardSize || curY + speedY < 0){   // If player tries to move outside the board (arrayOutOfBounds) they lose a turn
			return;
		}
		//Executing player actions
		if (!shooting){	// Executing  movement
			// Movement
			if(board[curX+speedX][curY+speedY]!=null && board[curX+speedX][curY+speedY].getName().equals("Bullet")){
				addToLog((board[curX][curY].getName()+" walked into " + board[curX+speedX][curY+speedY].getOwner() +  "'s bullet and died!"));
				//System.out.println("with a move of "+move+" from ("+curX+", "+curY+") walked into a bullet at ("+(curX+speedX)+","+(curY+speedY)+")");
				killPiece(curX,curY,board[curX+speedX][curY+speedY].getOwner());	//kills player
				// Delete the bullet you ran into
				board[curX+speedX][curY+speedY] = null;
				board[curX][curY]=null;
			}
			else if(board[curX+speedX][curY+speedY]!=null){
				// Do nothing, player tried to walk into another player
				addToLog(board[curX][curY].getName()+" tried to walk into " + board[curX+speedX][curY+speedY].getName());
			}
			else{		// Legitimate move, moving to new coordinates
				//System.out.println(board[curX][curY].getName()+" made a move of "+move+" from ("+curX+", "+curY+") to ("
				//+(curX+speedX)+", "+(curY+speedY)+")");
				board[curX+speedX][curY+speedY] = board[curX][curY];
				playerCoor.set(coorInd, new Point(curX+speedX,curY+speedY));
				board[curX][curY] = null;
			}
			return;
		}
		else {	// Executing shooting (move>=9)
			// Checking to see if the space is occupied.
			if (board[curX+speedX][curY+speedY] != null){
				if (board[curX+speedX][curY+speedY].getName().equals("Bullet"))		// If two bullets collide, both bullets annihilate each other
					board[curX+speedX][curY+speedY] = null;

				else {
					addToLog(board[curX+speedX][curY+speedY].getName() + " has been killed by " + board[curX][curY].getName() + " at point blank!");
					killPiece(curX+speedX,curY+speedY,board[curX][curY].getName());	// Kills player
					board[curX+speedX][curY+speedY]=null;
				}
			}

			else{
				board[curX+speedX][curY+speedY] = new Bullet(speedX, speedY, board[playerCoor.get(coorInd).x][playerCoor.get(coorInd).y].getName(),coorInd, pieceSize);	// Creates a bullet in the proper location,
				bulletCoor.add(new Point(curX+speedX,curY+speedY));
			}
		}
	}

	private void makeMoveBullet(int speedX, int speedY, int curX, int curY, int coorInd){
		if (curX + speedX >= boardSize || curX + speedX < 0 || curY + speedY >= boardSize || curY + speedY < 0){   // If bullet tries to move outside the board (arrayOutOfBounds) destroy bullet
			board[curX][curY] = null;
			bulletCoor.remove(coorInd);
			return;
		}
		// Executing bullet actions	
		if (board[curX+speedX][curY+speedY] != null){
			if (board[curX+speedX][curY+speedY].getName().equals("Bullet")){	// If two bullets collide, both bullets annihilate each other
				board[curX+speedX][curY+speedY] = null;
				board[curX][curY] = null;
				bulletCoor.remove(coorInd);
			}
			else {
				addToLog(board[curX+speedX][curY+speedY].getName() + " has been killed by " + board[curX][curY].getOwner() + "!");	// Kill a player if the bullet hits him
				killPiece(curX+speedX, curY+speedY, board[curX][curY].getOwner());
				board[curX][curY] = null;
				bulletCoor.remove(coorInd);
			}
			return;
		}
		else{				// Moving the bullet
			//System.out.println(board[curX][curY].getName()+" made a move from ("+curX+", "+curY+") to ("
			//	+(curX+speedX)+", "+(curY+speedY)+")");
			board[curX+speedX][curY+speedY] = board[curX][curY];
			bulletCoor.set(coorInd, new Point(curX+speedX,curY+speedY));
			board[curX][curY] = null;
			return;
		}
	}

	private void killPiece(int x, int y, String killer){
		if (board[x][y] != null && board[x][y].getName() != "Bullet")		// Don't add nothings and bullets to the graveyard
			graveyard.add(board[x][y]);										// Graveyard for post game statistics maybe

		noKillTurns = 0;
		
		int kills=0;
		for(int px=0; px<playerCoor.size(); px++){
			if(board[playerCoor.get(px).x][playerCoor.get(px).y]!=null&&board[playerCoor.get(px).x][playerCoor.get(px).y].getName().equals(killer)){
				board[playerCoor.get(px).x][playerCoor.get(px).y].incrementKills();
				kills = board[playerCoor.get(px).x][playerCoor.get(px).y].getKills();
				break;
			}
		}
		board[x][y] = null;				// Remove killed player
		
		if (killer!= null && killer.equals(""))return;	// Don't change leaderboard if player removed by crashing
		
		for(int n=0; n<killBoardCount; n++){
			if(topKillers[n].equals(killer)){
				topKills[n]++;
				for(int ind = n-1; ind !=- 1 && topKills[ind] <= topKills[ind+1]; ind--){		// changing killer's position on leaderboard if necessary
					String tname = topKillers[ind+1];
					topKillers[ind+1] = topKillers[ind];
					topKillers[ind] = tname;
					int tkill = topKills[ind+1];
					topKills[ind+1] = topKills[ind];
					topKills[ind] = tkill;
				}
				return;
			}
		}
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
		BoardPiece[][] boardCopyOrig = new BoardPiece[boardSize][boardSize];
		for (int a = 0; a < boardSize; a++)			
			for (int b = 0; b < boardSize; b++){
				boardCopyOrig[a][b] = board[a][b];
				boardCopy[a][b] = boardCopyOrig[a][b];
			}
		// Make player moves		
		for (int p = 0; p < playerCoor.size(); p++){
			int x = playerCoor.get(p).x;		// players x and y
			int y = playerCoor.get(p).y;
			if(board[x][y]==null){
				playerCoor.remove(p);
				p--;
				if(playerCoor.size()==1){           // Only one guy left
					log+= "\n\n\n\n";
					log+= board[playerCoor.get(0).x][playerCoor.get(0).y].getName()+" WON!!!!";
					log+= "\n\n\n\n";
					textArea.setText(log);
					textArea.setCaretPosition(textArea.getText().length()); // Auto scroll to bottom
					//gameover = true;
				}
				continue;
			}
			boolean tampered = false;			// Security to prevent people from tampering with the board they are provided
			for (int a = 0; a < boardSize; a++)		
				for (int b = 0; b < boardSize; b++)
					if(boardCopy[a][b] != boardCopyOrig[a][b]){
						tampered = true;
						break;
					}
			if(tampered)
				for (int a = 0; a < boardSize; a++)			
					for (int b = 0; b < boardSize; b++)
						boardCopy[a][b] = boardCopyOrig[a][b];
			try{
				makeMove(board[x][y].move(boardCopy),x,y,p);
			}catch(Exception |StackOverflowError e){
				addToLog(board[x][y].getName()+" CRASHED and being naughty in Mr. Chow's sight, shall snuff it.");
				killPiece(x,y,"");
				//e.printStackTrace();
				//System.out.println(board[x][y].getName()+" did nothing due to code crashing.");
			}
			noKillTurns++;
			
		}

		// Move Bullets
		for (int times = 0; times < 2; times++)		// Speed of bullets (distance covered per turn, currently set at two tiles per turn)
			for (int b = 0; b < bulletCoor.size(); b++){
				int x = bulletCoor.get(b).x;
				int y = bulletCoor.get(b).y;
				if(board[x][y] == null){
					bulletCoor.remove(b);
					b--;
					continue;
				}
				makeMoveBullet(board[x][y].getvX(), board[x][y].getvY(), x, y, b);
			}
	}

	private void addToLog(String add){
		log+=add+"\n";
		textArea.setText(log);
		textArea.setCaretPosition(textArea.getText().length()); // Auto scroll to bottom
	}

	// Draws game with formating
	private void drawToScreen() {						
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
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