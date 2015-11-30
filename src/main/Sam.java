/*     
        PLAYER AI TEMPLATE CLASS

        This is the template class for creating your AI for the battlefield game!
        Please include a png file (of any size) preferably, a 30x30 pixel image.
        Note: Your image must be a square, if it is not it will be squished.
        RULES AND INFO:
         - Wherever you see 'BoardPieceTemplate', replace with your first name (control f and REPLACE)
         - If your code returns/encounters an error, your player will be automatically removed from the game
         - Do not attempt to code anything that does not involve your player
         - You are allowed to add as much code inside and outside of your main method

  If you need help, or troubleshooting, consult Sunny or Wesley
 */
package main;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sam extends BoardPiece {

	// This is your class' constructor. DO NOT CHANGE (besides renaming to your own name)!
	public Sam(int pieceSize){
		setName("Sam");
		setOwner(getName());
		try {
			setImage(ImageIO.read(getClass().getResourceAsStream("/Images/"+getName()+".png")));
		}
		catch (IOException e){
			e.printStackTrace();
		}
		setImage(resizeImage(getImage(), pieceSize));
	}

	/* This is your main method
           The current board will be passed down to your class (locations of the other things on the board)
           The board is a 2D array of 'BoardPieces'
           Objects on the board will be:
            - null (nothing there)
            - "bullet"
            - other players represented by their first names (ex. "Bob")
                For example if Bob is standing in coordinates (2,5) then board[2][5].getName() will give you the name "Bob"
                if nothing is in coordinates (2,5) then board[2][5] will be null
                if a bullet is in coordinates (2,5) then board[2][5].getName() will give you the name "Bullet"
            Your method must return an integer value from 0 to 16, inclusive.
            1 through 8 indicate movement direction, 9 through 16 indicate firing direction, 0 does nothing

            MOVE
                1  2  3
                4  0  5
                6  7  8

                FIRE
                9  10 11
                12    13
                14 15 16
	 */
	public int move(BoardPiece[][] board) {
		// TODO put codez in here
		final int SIZE = board.length;
		boolean person = false;
		int pY = 0;
		int pX = 0;
		int plocationMeX = 0;
		int plocationMeY = 0;
		int blocationMeX = 0;
		int blocationMeY = 0;
		int myX = 0;
		int myY = 0;
		boolean myPos = false;
		int bX = 0;
		int bY = 0;
		boolean threat = false;
		boolean safe = false;
		boolean safe1 = false;
		boolean quadA = false;
		boolean quadB = false;
		boolean quadC = false;
		boolean quadD = false;



		for(int x = 0; x < SIZE; x++)//find me
		{
			for(int y = 0; y < SIZE; y++)
			{
				if(board[x][y] != null && board[x][y].getName() == "Sam")
				{
					myX = x;
					myY = y;
					myPos = true;
					System.out.println("My location: " + myX + ", " + myY);
					break;
				}	
			}

			if((myX <= 13 && myX >= 0) && (myY < 13 && myY > 0))
				quadA = true;
			else if((myX > 13 && myX < 26) && (myY <= 13 && myY >= 0))
				quadB = true;
			else if((myX <= 13 && myX >= 0) && (myY >= 13 && myY <= 26))
				quadC = true;
			else if((myX > 13 && myX < 26) && (myY > 13 && myY < 26))
				quadD = true;

		}

		for(int x = 0; x < SIZE; x++)//bullet threat
		{
			for(int y = 0; y < SIZE; y++)
			{
				if(board[x][y] != null && board[x][y].getName() == "Bullet" && board[x][y].getOwner() != "Sam")
				{
					bX = x;
					bY = y;

					blocationMeX = myX-bX;
					blocationMeY = myY-bY;

				}
				if((blocationMeX <= 3 && blocationMeX >= -3) || (blocationMeY <= 3 && blocationMeY >= -3))
				{

					System.out.println("Bullet Threat!");
					if((blocationMeX == -2 && blocationMeY == -2) || (blocationMeX == -1 && blocationMeY == -1)  || (blocationMeX == -3 && blocationMeY == -3) ){
						return 9;
					}
					else if((blocationMeX == 0 && blocationMeY == -2) || (blocationMeX == 0 && blocationMeY == -1) || (blocationMeX == 0 && blocationMeY == -3) ){
						return 10;
					}
					else if((blocationMeX == 2 && blocationMeY == -2) || (blocationMeX == 1 && blocationMeY == -1) || (blocationMeX == 3 && blocationMeY == -3) ){
						return 11;
					}
					else if((blocationMeX == -2 && blocationMeY == 0) || (blocationMeX == -1 && blocationMeY == 0) || (blocationMeX == -3 && blocationMeY == 0) ){
						return 12;
					}
					else if((blocationMeX == 2 && blocationMeY == 0) || (blocationMeX == 1 && blocationMeY == 0) || (blocationMeX == 3 && blocationMeY == 0) ){
						return 13;
					} 
					else if((blocationMeX == -2 && blocationMeY == 2) || (blocationMeX == -1 && blocationMeY == 1) || (blocationMeX == -3 && blocationMeY == 3) ){
						return 14;
					}
					else if((blocationMeX == 0 && blocationMeY == 2) || (blocationMeX == 0 && blocationMeY == 1) || (blocationMeX == 0 && blocationMeY == 3) ){
						return 15;
					}
					else if((blocationMeX == 2 && blocationMeY == 2) || (blocationMeX == 1 && blocationMeY == 1) || (blocationMeX == 3 && blocationMeY == 3) ){
						return 16;
					}
				}
				else{
					safe1 = true;
					System.out.println("Safe Bullet Threat");
				}

			}
		}


		for(int x = 0; x < SIZE; x++)//player threat
		{
			for(int y = 0; y < SIZE; y++)
			{
				if(board[x][y] != null && board[x][y].getName() != "Bullet" && board[x][y].getName() != "Sam")
				{
					pX = x;
					pY = y;

					plocationMeX = myX-pX;
					plocationMeY = myY-pY;

				}
				if((plocationMeX <= 3 && plocationMeX >= -3) || (plocationMeY <= 3 && plocationMeY >= -3))
				{

					System.out.println("Person Threat!");
					if((plocationMeX == -2 && plocationMeY == -2) || (plocationMeX == -1 && plocationMeY == -1)  || (plocationMeX == -3 && plocationMeY == -3) ){
						return 9;
					}
					else if((plocationMeX == 0 && plocationMeY == -2) || (plocationMeX == 0 && plocationMeY == -1) || (plocationMeX == 0 && plocationMeY == -3) ){
						return 10;
					}
					else if((plocationMeX == 2 && plocationMeY == -2) || (plocationMeX == 1 && plocationMeY == -1) || (plocationMeX == 3 && plocationMeY == -3) ){
						return 11;
					}
					else if((plocationMeX == -2 && plocationMeY == 0) || (plocationMeX == -1 && plocationMeY == 0) || (plocationMeX == -3 && plocationMeY == 0) ){
						return 12;
					}
					else if((plocationMeX == 2 && plocationMeY == 0) || (plocationMeX == 1 && plocationMeY == 0) || (plocationMeX == 3 && plocationMeY == 0) ){
						return 13;
					} 
					else if((plocationMeX == -2 && plocationMeY == 2) || (plocationMeX == -1 && plocationMeY == 1) || (plocationMeX == -3 && plocationMeY == 3) ){
						return 14;
					}
					else if((plocationMeX == 0 && plocationMeY == 2) || (plocationMeX == 0 && plocationMeY == 1) || (plocationMeX == 0 && plocationMeY == 3) ){
						return 15;
					}
					else if((plocationMeX == 2 && plocationMeY == 2) || (plocationMeX == 1 && plocationMeY == 1) || (plocationMeX == 3 && plocationMeY == 3) ){
						return 16;
					}
				}
				else{
					safe1 = true;
					System.out.println("Safe Person Threat");
				}

			}
		}


		if(safe == true && safe1 == true)
		{

			if(quadA == true)
			{

				if(myX != 0 && myY != 0){
					return 1;
				}
				else if(myX != 0){
					return 4;
				}
				else if(myY != 0){
					return 2;
				}
				else if(myX == 0 && myY == 0){
					return 16;
				}

			}
			else if(quadB == true)
			{

				if(myX != SIZE && myY != 0){
					return 3;
				}
				else if(myX != SIZE){
					return 5;
				}
				else if(myY != 0){
					return 2;
				}
				else if(myX == SIZE && myY == 0){
					return 14;
				}

			}
			else if(quadC == true)
			{

				if(myX != 0 && myY != SIZE){
					return 6;
				}
				else if(myX != 0){
					return 4;
				}
				else if(myY != SIZE){
					return 7;
				}		
				else if(myX == 0 && myY == SIZE){
					return 11;
				}

			}
			else if(quadD == true)
			{

				if(myX != SIZE && myY != SIZE){
					return 8;
				}
				else if(myX != SIZE){
					return 5;
				}
				else if(myY != SIZE){
					return 7;
				}
				else if(myX == SIZE && myY == SIZE){
					return 9;
				}							

			}


		}


		System.out.println("ERROR");
		return rand(9,16);


	}

}

