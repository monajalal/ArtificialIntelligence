
public class SearchResult {
	SearchResult(String config, String opSequence, int numPoppedStates){
		this.config = config;
		this.opSequence = opSequence;
		this.numPoppedStates = numPoppedStates;
	}
	String config;				// the board configuration
	String opSequence;			// operation sequence to each this.config 
	int numPoppedStates;		// the number of states popped from the queue 
	
	/**
	 * Print the number of states popped from priority queue.
	 * If initial state is already the goal state, then it should be 1.
	 */
	public void printNumPoppedStates(){
		System.out.println(this.numPoppedStates);
	}
	/**
	 * Print the operation sequence. If initial state is already the goal state, then 
	 * print "no moves needed"
	 */
	public void printOpSeq(){
		if (this.opSequence != ""){
			System.out.println(this.opSequence);
		}else {
			System.out.println("No moves needed");
		}
	}
	/**
	 * Print the configuration
	 */
	public void printConfig(){
		int n = 0;
		for (int i = 0; i < 7; ++i){
			if (i != 2 && i != 4){
				System.out.println(String.format("  %c %c  ", config.charAt(n), config.charAt(n+1)));
				n += 2;
			} else{
				for (int j = 0; j < 7; ++j)
					System.out.print(config.charAt(n+j));
				System.out.println();
				n += 7;
			}
		}
		
	}
}
