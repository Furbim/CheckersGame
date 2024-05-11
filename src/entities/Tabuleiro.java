package entities;

public class Tabuleiro {

	private Integer rodada = 0;
	private Integer rodadabkp = 0;
	private String table = "";
	private char[][] matriz = new char[8][8];
	private char P = ' ';
	private char OP = ' ';

	// Get the
	public char getP() {
		return P;
	}

	// Table constructor
	public Tabuleiro() {
	}

	// Creating the board matrix
	public void createTable() {

		for (int i = 0; i < this.matriz.length; ++i) {
			for (int j = 0; j < this.matriz.length; ++j) {
				this.matriz[i][j] = '-';
			}
		}

	}

	// Function to format the board
	public void showTable() {

		String msg = "";
		String columns = "   ";

		int i;
		for (i = 0; i < this.matriz.length; ++i) {
			columns = columns + i + " ";
		}

		columns = columns + "<- Y";

		for (i = 0; i < this.matriz.length; ++i) {
			msg = msg + " " + i + " ";

			for (int j = 0; j < this.matriz.length; ++j) {
				msg = msg + this.matriz[i][j] + " ";
			}

			msg = msg + "\n";
		}

		msg = msg + " ^\n |\n X";
		this.table = columns + "\n" + msg;
		System.out.println(this.table);
	}

	// Function to position the pieces over the board
	public void positioPieces() {

		for (int i = 0; i < this.matriz.length; ++i) {
			int w;
			if (i != 0 && i != this.matriz.length - 2) {
				if (i != this.matriz.length - 1 && i != 1) {
					for (w = 0; w < this.matriz.length; ++w) {
						this.matriz[i][w] = '-';
					}
				} else {
					for (w = 1; w < this.matriz.length; w += 2) {
						if (i == this.matriz.length - 1) {
							this.matriz[i][w] = 'w';
						} else {
							this.matriz[i][w] = 'b';
						}
					}
				}
			} else {
				for (w = 0; w < this.matriz.length; w += 2) {
					if (i == this.matriz.length - 2) {
						this.matriz[i][w] = 'w';
					} else {
						this.matriz[i][w] = 'b';
					}
				}
			}
		}

		this.showTable();
	}

	// Function to check if it's black or white's turn with pair or odd system
	public void checkWhoseRound() {

		if (this.rodada % 2 == 0) {
			this.P = 'w';
			this.OP = 'b';
		} else {
			this.P = 'b';
			this.OP = 'w';
		}
	}

	// Function to change the pieces in matrix
	public void doMovement(int piecex, int piecey, int movex, int movey) {

		checkWhoseRound();

		// Replacing the piece house to empty and then replacing the destiny to the
		// piece
		this.matriz[piecex][piecey] = '-';
		this.matriz[movex][movey] = this.P;

		// Assigning +1 to the round to change the player
		this.rodada = this.rodada + 1;
		rodadabkp = rodada;

	}

	// Function to check if the movement is possible or not
	public boolean checkMovement(int piecex, int piecey, int movex, int movey) {

		checkWhoseRound();

		// Checking if the movement is within the limits of the matrix
		if (movex >= 0 && movex <= 7 && movey >= 0 && movey <= 7) {
			if (this.matriz[piecex][piecey] != this.P) {
				System.out.println("1");
				System.out.println("Impossible move!Insert Again:");
				return false;

				// Checking if the move house is empty
			} else if (this.matriz[movex][movey] != '-') {

				System.out.println("2");
				System.out.println("Impossible move!Insert Again:");
				return false;

			}

			return true;

		} else {

			return false;
		}

	}

	public boolean simpleMove(int piecex, int piecey, int movex, int movey) {

		if (this.P != 'w') {
			System.out.println("3");

			// Checking if it is a diagonal movement
			if (Math.abs(movex - piecex) == 1 && Math.abs(movey - piecey) == 1) {

				// Checking if the piece is moving forward
				if (piecex - movex < 0) {

					// Calling the function to do the movement
					return true;

				} else {

					return false;

				}

			} else {

				System.out.println("Impossible move! Insert Again:");
				return false;
			}

			// Checking if it is a diagonal movement
		} else if (Math.abs(movex - piecex) == 1 && Math.abs(movey - piecey) == 1) {

			// Checking if the piece is moving forward
			if (piecex - movex > 0) {

				// Calling the function to do the movement
				return true;

			} else {

				System.out.println("10");
				System.out.println("Impossible move! Insert Again:");
				return false;

			}

		} else {
			return false;
		}
	}

	// Function to check if it's a eating movement
	public boolean checkToEat(int movex, int movey, int piecex, int piecey) {

		checkWhoseRound();

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i != 0 || j != 0) {
					try {

						// Here I used try because I can't find a way to check if the movement
						if (this.matriz[piecex + i][piecey + j] == this.OP && piecex + i + i == movex
								&& piecey + j + j == movey) {

							return true;
						}

					} catch (Exception e) {

					}

				}

			}
		}

		System.out.println("8");
		return false;
	}

	public boolean doEatMovement(int piecex, int piecey, int movex, int movey) {

		int i = 0, j = 0;

		if (piecex - movex > 0) {

			i = -1;

		} else {

			i = 1;

		}

		if (piecey - movey > 0) {

			j = -1;

		} else {

			j = 1;

		}

		this.matriz[piecex][piecey] = '-';
		this.matriz[piecex + i][piecey + j] = '-';
		this.matriz[movex][movey] = this.P;

		// Checking if the piece have more options of pieces to eat after a eating
		// movement
		if (this.eatAgain(movex, movey) == true) {

			return true;
		} else {

			// Assigning +1 to the round to change the player
			this.rodada = this.rodada + 1;
			rodadabkp = rodada;
			return true;
		}

	}

	// Function to check Victory
	public boolean checkVictory() {

		checkWhoseRound();

		int contw = 0;
		int contb = 0;
		int contbm = 0;
		int contwm = 0;

		int i;
		int j;

		// Counting the pieces to check if it don't have any more
		for (i = 0; i < this.matriz.length; ++i) {
			for (j = 0; j < this.matriz.length; ++j) {
				if (this.matriz[i][j] == 'w') {
					++contw;
				} else if (this.matriz[i][j] == 'b') {
					++contb;
				}
			}
		}

		if (contw == 0) {

			System.out.println("White wins!");
			return true;

		} else if (contb == 0) {
			System.out.println("Black wins!");
			return true;

		}

		// Checking if the black or white pieces don't have more possible movements
		for (i = 0; i < this.matriz.length; ++i) {
			for (j = 0; j < this.matriz.length; ++j) {

				for (int wi = -2; wi < 3; ++wi) {
					for (int wj = -2; wj < 3; ++wj) {

						if (this.matriz[i][j] == 'w') {

							if (this.rodada % 2 != 0) {

								rodada++;

							}

							if (checkMovement(i, j, wi, wj)) {

								if (checkToEat(wi, wj, i, j)) {

									contwm++;

									// Checking whose turn it is
								} else {

									if (simpleMove(i, j, wi, wj) == true) {

										contwm++;

									}

								}
							}

						} else {

							if (this.rodada % 2 == 0) {

								rodada++;

							}

							if (checkMovement(i, j, wi, wj)) {

								if (checkToEat(wi, wj, i, j)) {

									contbm++;

									// Checking whose turn it is
								} else {

									if (simpleMove(i, j, wi, wj) == true) {

										contbm++;

									}

								}
							}

						}
					}
				}
			}
		}
		
		resetRound();
		
		if (contwm == 0) {

			System.out.println("Black Wins");
			return true;
		} else if (contwm == 0) {
			System.out.println("White Wins");
			return true;
		}

		return false;

	}

	// Function to check if the piece have more options of pieces to eat after a
	// eating movement
	public boolean eatAgain(int movex, int movey) {

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i != 0 || j != 0) {
					if (movex + i >= 1 || movey + i >= 1 || movex + i <= 6 || movey + i <= 6) {
						try {
							if (this.matriz[movex + i][movey + j] == this.OP
									&& this.matriz[movex + i + i][movey + j + j] == '-') {
								return true;
							}
						} catch (Exception e) {

						}
					}

				}

			}
		}

		return false;
	}

	
	public void resetRound() {
		
		rodada = rodadabkp;
		
	}
	
	public boolean itsKing(int piecex, int piecey) {

		checkWhoseRound();

		if (this.P == 'w') {
			if (matriz[piecex][piecey] == 'W') {

				return true;
			}
		} else {
			if (matriz[piecex][piecey] == 'B') {

				return true;
			}

		}

		return false;
	}

}
