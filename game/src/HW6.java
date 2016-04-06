import java.util.*;

public class HW6 {

	public static void main(String args[]) {
		int n = Integer.parseInt(args[0]);
		int mode = Integer.parseInt(args[1]);

		if (n < 5) {
			System.out.println("Value of n is too small to play the game");
			return;
		}

		if (mode != 1 && mode != 2 && mode != 3) {
			System.out.println("Invalid mode");
			return;
		}

		// Creating the board with n elements
		Board board = new Board(n);

		// Creating two players: 1 and 2
		PlayerImpl player1 = new PlayerImpl(1, n);
		PlayerImpl player2 = new PlayerImpl(2, n);

		Scanner scn = new Scanner(System.in);

		// Variable that determines the winner
		int winner = 0;

		// The loop where the game is played

		while (true)
		// for(int z=0;z<5;z++)

		{
			if (mode == 1 || mode == 3) {
				// Player 1's move
				int lastMove = board.getLastMove();
				int[] crossedList = board.getCrossedListCopy();
				int mvPl1 = player1.move(lastMove, crossedList);
				// Player 1 looses
				if (mvPl1 == -1) {
					winner = 2;
					break;
				}
				System.out.println("Player 1: " + mvPl1);
				// Player 1's move on the board

				board.move(mvPl1, 1);
			}

			if (mode == 2) {
				// Player 2's move
				int lastMove = board.getLastMove();
				int[] crossedList = board.getCrossedListCopy();
				ArrayList<Integer> succ = player1.generateSuccessors(lastMove,
						crossedList);
				if (succ.isEmpty()) {
					winner = 2;
					break;
				}

				System.out.print("Player 1: ");
				int mvPl1 = scn.nextInt();

				while (succ.indexOf(mvPl1) == -1) {
					System.out.println("Invalid move");
					System.out.print("Player 1: ");
					mvPl1 = scn.nextInt();
				}

				// Player 1's move on the board
				board.move(mvPl1, 1);

			}

			if (mode == 3) {
				// Player 2's move
				int lastMove = board.getLastMove();
				int[] crossedList = board.getCrossedListCopy();
				ArrayList<Integer> succ = player2.generateSuccessors(lastMove,
						crossedList);
				if (succ.isEmpty()) {
					winner = 1;
					break;
				}

				System.out.print("Player 2: ");
				int mvPl2 = scn.nextInt();

				while (succ.indexOf(mvPl2) == -1) {
					System.out.println("Invalid move");
					System.out.print("Player 1: ");
					mvPl2 = scn.nextInt();
				}

				// Player 1's move on the board
				board.move(mvPl2, 2);

			}

			if (mode == 1 || mode == 2) {
				// Player 2's move
				int lastMove = board.getLastMove();
				int[] crossedList = board.getCrossedListCopy();
				int mvPl2 = player2.move(lastMove, crossedList);
				// Player 2 looses
				if (mvPl2 == -1) {
					winner = 1;
					break;
				}
				System.out.println("Player 2: " + mvPl2);

				// Player 2's move on the board
				board.move(mvPl2, 2);
			}

		}

		System.out.println("Winner: Player " + winner);
	}
}