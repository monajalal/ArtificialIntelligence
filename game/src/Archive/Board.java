public class Board {

	private int[] crossedList = null; // an array to figure out which numbers
										// have been crossed out
	private int lastMove = -1; // the last move made

	public Board(int n) {
		crossedList = new int[n + 1];
	}

	// A move on the board made by a player
	public void move(int num, int player) {
		if (num == 0) {
			System.out.println("Wrong move");
			System.exit(0);
		}

		lastMove = num;
		crossedList[num] = player;

	}

	public int getLastMove() {
		return this.lastMove;
	}

	public int[] getCrossedListCopy() {
		return this.crossedList.clone();
	}
}