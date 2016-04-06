import java.util.Comparator;

/**
 * class for search state.
 * 
 * DO NOT MODIFY.
 */
public class State {
	public State(String config, int realCost, int heuristicCost, String opSequence){
		this.config = config;
		this.realCost = realCost;
		this.heuristicCost = heuristicCost;
		this.opSequence = opSequence;
	}

	/**
	 * Comparator for priority queue
	 */
	public static Comparator<State> comparator = new Comparator<State>() {
		@Override
		public int compare(State s1, State s2) {
			
			if (s1.realCost + s1.heuristicCost - s2.realCost - s2.heuristicCost == 0)
				return s1.opSequence.compareTo(s2.opSequence);
			else 
				return (s1.realCost + s1.heuristicCost - s2.realCost - s2.heuristicCost);
		}
	};
	
	/**
	 * Override the equals function 
	 */
	public boolean equals(Object other){
		if (other == null){		   
		    return false;
		}else if (this.getClass() != other.getClass()){
			return false;
		}else {
			return this.config.equals(((State)other).config);
		}
	};
	
	public String config;			//configuation
	public int realCost;			//equivalent to g 
	public int heuristicCost;		//equivalent to h
	public String opSequence;		//operation sequence
}