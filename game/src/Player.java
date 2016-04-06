import java.util.*;

public interface Player {
	public int move(int lastMove, int[] crossedList);

	public ArrayList<Integer> generateSuccessors(int lastMove, int[] crossedList);

	public int max_value(GameState s);

	public int min_value(GameState s);

}