package program;

import java.util.Scanner;

import entities.Tabuleiro;

public class Dama {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Tabuleiro tab = new Tabuleiro();
		
		boolean move = false;

		tab.createTable();

		tab.positioPieces();
		
		System.out.println("White plays first!");
		
		do {
			
			tab.showTable();
			
			
			if (move == false) {

				System.out.println("Impossible move! Insert Again:");

			}else {
				
				System.out.println("Please insert the column and then the line from 1 to 8:");
				
			}
			

			int piecex = sc.nextInt();
			int piecey = sc.nextInt();
			int movex = sc.nextInt();
			int movey = sc.nextInt();

			if (tab.checkMovement(piecex, piecey, movex, movey)) {

				if (tab.checkToEat(movex, movey, piecex, piecey)) {

					if (tab.doEatMovement(piecex, piecey, movex, movey)) {

						move = true;

					} else {

						move = false;

					}

				} else {

					if (tab.simpleMove(piecex, piecey, movex, movey) == true) {

						tab.doMovement(piecex, piecey, movex, movey);

						move = true;

					} else {

						move = false;

					}

				}
			}

			tab.checkWhoseRound();

			if (tab.getP() == 'w') {

				System.out.println("White's turn!");

			} else {

				System.out.println("Black's turn!");

			}

			tab.showTable();

		} while (tab.checkVictory() == false || move == false);

		sc.close();
	}

}
