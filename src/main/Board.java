package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
	int boardSize = 20, pieceSize = HEIGHT/boardSize;
	BoardPiece[][] board = new BoardPiece[boardSize][boardSize];
	
	public void init(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
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
		}
	}

	private void draw() {					// constantly draw the sprites and board
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		for(int x=0; x<boardSize; x++)
			for(int y=0; y<boardSize; y++){
				g.drawString("Piece", x*pieceSize, y*pieceSize);
			}
		
		
	}

	private void update() {								// updates current game state
		//get moves
		//make changes
		//do constant computation
	}

	private void drawToScreen() {						// scales and draws game with formating
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH ,  HEIGHT , null);
		g2.dispose();
	}
	public int rand(double d, double e){
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
