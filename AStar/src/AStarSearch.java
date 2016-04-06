/**
 * Interface class for A* search algorithm.
 * For an explanation of methods, see AStarSearchImpl. 
 * 
 * DO NOT MODIFY
 */
public interface AStarSearch {
	/**
	 * Given the initial configuration, return the search result
	 * @param initConfig: the initial configuration of the board
	 * @param modeFlag: specify which heuristic your should use
	 */
	public SearchResult search(String initConfig, int modeFlag);
	/**
	 * check the configuration is a goal 
	 */
	public boolean checkGoal(String config);
	/**
	 * move the current configation with specific operation
	 */
	public String move(String config, char op);	
	/**
	 * compute the heuristic cost for current configuration
	 * @param modeFlag TODO
	 */
	public int getHeuristicCost(String config, int modeFlag);
	
}
