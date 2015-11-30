
package main;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Ammar extends BoardPiece {

	public Ammar(int pieceSize) {
		setName("Ammar");
		setOwner(getName());
		try {
			setImage(ImageIO.read(getClass().getResourceAsStream("/Images/" + getName() + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setImage(resizeImage(getImage(), pieceSize));
	}

	public int move(BoardPiece[][] board) {

		final int SIZE = board.length;

		int myX = 0, myY = 0;

		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (board[x][y] != null && board[x][y].getName() == "Ammar") {
					myX = x;
					myY = y;
				}
			}
		}
		int r = 0;
		if (myX == 26 && myY == 0) {
			r = random(1,3);
			if (r == 1)
				return 12;
			else if (r == 2)
				return 14;
			else
				return 15;
		}
		else if (myX == 26 && myY == 26) {
			r = random(1,3);
			if (r == 1)
				return 10;
			else if (r == 2)
				return 12;
			else
				return 9;
		}
		else if (myX == 0 && myY == 26) {
			r = random(1,3);
			if (r == 1)
				return 10;
			else if (r == 2)
				return 11;
			else
				return 13;
		}
		else if (myX == 0 && myY == 0) {
			r = random(1,3);
			if (r == 1)
				return 13;
			else if (r == 2)
				return 15;
			else
				return 16;
		}
			

		int bulletCount = 0, personCount = 0; // threatLevel = 0;
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {

				if (board[x][y] != null && board[x][y].getName() == "Bullet") {

					if (y == myY && Math.abs(myX - x) < 4) {

						if (myX > x && board[x][y].getvX() == 1) {
							if (myX == 26) {
								if (myY == 0) {
									for (int w = 3; w > 0; w--) {
										if (board[26 - w][0 + w] != null && board[26 - w][0 + w].getName() == "Bullet"
												&& board[26 - w][0 + w].getvX() == 1
												&& board[26 - w][0 + w].getvY() == -1) {
											if (board[25][1].getName() == null)
												return 7;
										} else if (board[26][w] != null && board[26][w].getName() == "Bullet"
												&& board[26][w].getvY() == -1) {
											return 6;
										} else {
											return 12;
										}

									}

								} else if (myY == 26) {
									if (board[23][23] != null && board[23][23].getName() == "Bullet"
											&& board[23][23].getvX() == 1 && board[23][23].getvY() == 1) {
										if (board[26][25].getName() == null)
											return 2;
									} else
										return 12;
								} else {
									if (board[23][myY + 3] != null && board[23][myY + 3].getName() == "Bullet"
											&& board[23][myY + 3].getvX() == 1 && board[23][myY + 3].getvY() == 1)
										return 7;

									else if (board[23][myY - 3] != null && board[23][myY - 3].getName() == "Bullet"
											&& board[23][myY - 3].getvX() == 1 && board[23][myY - 3].getvY() == -1)
										return 7;

									else
										return 2;
								}
							} else {
								if (board[x][myY + 1] == null)
									return 2;
								else if (board[x][myY - 1] == null)
									return 7;
								else
									return 3;

							}

						} else if (myX < x && board[x][y].getvX() == -1) {
							if (myX == 0) {
								if (myY == 0) {
									if (board[3][3].getName() == "Bullet" && board[3][3].getvX() == -1
											&& board[23][3].getvY() == -1)
										return 7;
									else
										return 8;
								}

								else if (myX == 0) {
									if (board[3][23].getName() == "Bullet" && board[3][23].getvX() == -1
											&& board[3][23].getvY() == 1)
										return 2;
									else
										return 3;
								}
							}
						}
					}

					else if (x == myX && Math.abs(myY - y) < 4) {
						if (myY > y && board[x][y].getvY() == 1) {
							return 10; // remember to change if time permits
						} else if (myY < y && board[x][y].getvY() == -1) {
							return 15; // remember to change if time permits
						}
					}

					else if (Math.abs(myX - x) == Math.abs(myY - y) && Math.abs(myY - y) < 3) {
						if (myX > x && myY > y && board[x][y].getvX() == 1) {
							return 7; // remember to change if time permits
						} else if (myX > x && myY < y && board[x][y].getvX() == 1) {
							return 2; // remember to change if time permits
						} else if (myX < x && myY > y && board[x][y].getvX() == -1) {
							return 7; // remember to change if time permits
						} else if (myX < x && myY < y && board[x][y].getvX() == -1) {
							return 2; // remember to change if time permits
						}
					}

				}
			}
		}

		if (myX - 2 >= 0 && myY - 2 >= 0 && myX + 3 <= SIZE && myY + 3 <= SIZE) {
			for (int x = myX - 2; x < myX + 3; x++) {
				for (int y = myY - 2; y < myY + 3; y++) {
					if (board[x][y] != null && board[x][y].getName() == "Bullet") {
						bulletCount++;
					} else if (board[x][y] != null && board[x][y].getName() != "Bullet"
							&& (Math.abs(myX - x) == Math.abs(myY - y) || y == myY || x == myX)) {
						personCount++;
					}
				}
			}
			if (personCount < 1) {
				for (int x = myX - 2; x < myX + 3; x++) {
					for (int y = myY - 2; y < myY + 3; y++) {
						if (board[x][y] != null && board[x][y].getName() == "Bullet") {
							if (y == myY) {
								if (x > myX && board[x][y].getvX() == -1) {
									return 13;
								} else if (x < myX && board[x][y].getvX() == 1) {
									return 12;
								}
							} else if (x == myX) {
								if (y < myY && board[x][y].getvY() == 1) {
									return 10;
								} else if (y > myY && board[x][y].getvY() == -1) {
									return 15;
								}
							} else if (Math.abs(myX - x) == Math.abs(myY - y)) {
								if (y > myY && x > myX && board[x][y].getvY() == -1 && board[x][y].getvX() == -1) {
									return 16;
								} else
									if (y > myY && x < myX && board[x][y].getvY() == -1 && board[x][y].getvX() == 1) {
									return 14;
								} else if (y < myY && x > myX && board[x][y].getvY() == 1
										&& board[x][y].getvX() == -1) {
									return 11;
								} else if (y < myY && x < myX && board[x][y].getvY() == 1 && board[x][y].getvX() == 1) {
									return 9;
								}
							}
						}
					}
				}
			}
		}

		if (personCount < 3) {

			return random(9, 16);
		}

		return random(9, 16);

	}

	public static int random(int min, int max) {
		return (int) (Math.random() * max + min);
	}

}
