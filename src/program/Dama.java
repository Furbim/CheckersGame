package program;

import java.util.Scanner;

import entities.Tabuleiro;

public class Dama {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Tabuleiro tab = new Tabuleiro();

		tab.createTable();

		tab.positioPieces();

		System.out.println("White plays first!");

		tab.showTable();

		boolean move = false;

		do {

			System.out.println("Please insert the column and then the line from 1 to 8:");

			int piecex = sc.nextInt();
			int piecey = sc.nextInt();
			int movex = sc.nextInt();
			int movey = sc.nextInt();

			move = tab.checkMovement(piecex, piecey, movex, movey);

			tab.checkWhoseRound();
			if (tab.getP() == 'W') {

				System.out.println("White's turn!");

			} else {

				System.out.println("Black's turn!");

			}

			tab.showTable();

		} while (tab.checkVictory() == false || move == false);

		sc.close();
	}

}
