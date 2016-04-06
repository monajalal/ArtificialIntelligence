import java.util.*;

public class GameState {
	public boolean leaf = false;// true if this state is a leaf
	int[] crossedList;// the crossedlist for this state
	int lastMove; // the last move which led to this game state
	int bestMove;// best possible next move

	public GameState(int[] crossedList, int lastMove) {
		this.crossedList = crossedList.clone();
		this.lastMove = lastMove;
	}

}