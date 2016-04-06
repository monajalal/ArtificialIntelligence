/*Mona Jalal*/
import java.util.HashMap;
import java.util.PriorityQueue;



public class AStarSearchImpl implements AStarSearch {

	//Priority queue for nodes to be explored (chosen from)
	PriorityQueue<State> openPQ = new PriorityQueue<State>(1,State.comparator);
	//Priority queue for nodes already explored
	PriorityQueue<State> closedPQ= new PriorityQueue<State>(1,State.comparator);
    //HashMap for nodes to be explored (chosen from)
	//HashMap<String, Integer> openHM= new HashMap<String, Integer>();
	HashMap<String, State> openHM= new HashMap<String, State>();
	//HashMap for nodes already explored (already chosen)
	HashMap<String, State> closedHM= new HashMap<String, State>();
	//an Enum for the operations
	public enum Operation{
		A,B,C,D,E,F,G,H
	}

	
	
	@Override	
	public SearchResult search(String initConfig, int modeFlag) {
		//number of popped states from the OPEN priority queue
		int numPoppedStates = 0; 
		//initializing the curState values
		State curState=new State(initConfig,0,getHeuristicCost(initConfig, modeFlag), "");
		openPQ.add(curState);
		openHM.put(curState.config, curState);
		//continue as long as there's an item in open priority queue
		while (!openPQ.isEmpty()){
			//assumption is that .poll removes the state with min f
			State curPolled=openPQ.poll();
			//increase the number of popped states from OPEN
			numPoppedStates++;
			//place it in the CLOSED
			closedHM.put(curPolled.config, curPolled);
			//place it in closed hashmap
			closedPQ.add(curPolled);
			//check to see if curPolled is a goal state
			if (checkGoal(curPolled.config)){
				//return an instantiation of class SearchResult with proper values
                return new SearchResult(curPolled.config, curPolled.opSequence, numPoppedStates); 
            }
			//saving all the neighbor states in an State array
			State[] neighborStates=neighborStates(curPolled,modeFlag);
			//iterating over all of the neighbors
			for (int i=0; i<8; i++){
				//if the current neighbor is not in OPEN or CLOSED hashmaps
				if (openHM.get(neighborStates[i].config)==null &&
				    closedHM.get(neighborStates[i].config)==null){ 
					//Algorithm 1 -- line 11
					openPQ.add(neighborStates[i]);
					openHM.put(neighborStates[i].config,neighborStates[i]);
				}
				else{
					if (openHM.get(neighborStates[i].config)!=null){//there's a duplicate in OPEN
						//checking to see if the new state is better
						if (neighborStates[i].realCost<openHM.get(neighborStates[i].config).realCost){
							//remove it from the OPEN priority queue
							openPQ.remove(openHM.get(neighborStates[i].config));
							openPQ.add(neighborStates[i]);
							//add the new state to OPEN hashmap
							openHM.put(neighborStates[i].config, neighborStates[i]);
						}
					}
					//checking to see if there's a duplicate in closed hashmap
					else if (closedHM.get(neighborStates[i].config)!=null){
						//checking to see if the new state is better
						if (neighborStates[i].realCost<closedHM.get(neighborStates[i].config).realCost){
						//remove it from the CLOSE priority queue
						closedPQ.remove(closedHM.get(neighborStates[i].config));
					    closedPQ.add(neighborStates[i]);
						//add the better state to the CLOSED hashmap
						closedHM.put(neighborStates[i].config, neighborStates[i]);
						}
					}
				}
				
			}
		}		
		return null;
	}

	//checking to see if we have met the goal state
	@Override
	public boolean checkGoal(String config) {
		String centralSquare=centralSquare(config);
		if (count('1' , centralSquare)==8 ||
			count('2' , centralSquare)==8 ||
			count('3' , centralSquare)==8)
			return true;
		else
			return false;
		
		
	}

	//finding all the neighbor states and set their related configs
	public State[] neighborStates(State parent, int modeFlag){
	    State[] neighborStates = new State[8];
	    int i = 0;
	    
	    for (Operation o: Operation.values()) {
	        String s = move(parent.config, o.name().charAt(0));
	        neighborStates[i++] = new State(s, parent.realCost+1, getHeuristicCost(s, modeFlag), parent.opSequence+o.name());
	    }
	    
	    return neighborStates;
	}
	
	

	//find all the neighbor states of a given state configuration
	public String[] neighbors (String config){
		String[] neighborsArray=new String[8];
		int i=0;
		for (Operation o : Operation.values()){
			neighborsArray[i]=move(config,o.name().charAt(0));
			i++;
		}		
		
		return neighborsArray;
	}
	
	@Override
	public String move(String config, char op) {
		StringBuilder str = new StringBuilder(config);
		//change the configuration based on a specific given operation
		if (op=='A'){
			
			str.setCharAt(0,config.charAt(2)); 
			str.setCharAt(2,config.charAt(6)); 
			str.setCharAt(6,config.charAt(11)); 
			str.setCharAt(11,config.charAt(15)); 
			str.setCharAt(15,config.charAt(20)); 
			str.setCharAt(20,config.charAt(22)); 
			str.setCharAt(22,config.charAt(0)); 
		
		}
		else if (op=='B'){
			
			str.setCharAt(1,config.charAt(3)); 
			str.setCharAt(3,config.charAt(8)); 
			str.setCharAt(8,config.charAt(12)); 
			str.setCharAt(12,config.charAt(17)); 
			str.setCharAt(17,config.charAt(21)); 
			str.setCharAt(21,config.charAt(23)); 
			str.setCharAt(23,config.charAt(1)); 
			
		}
		else if (op=='C'){
			str.setCharAt(10,config.charAt(9)); 
			str.setCharAt(9,config.charAt(8)); 
			str.setCharAt(8,config.charAt(7)); 
			str.setCharAt(7,config.charAt(6)); 
			str.setCharAt(6,config.charAt(5)); 
			str.setCharAt(5,config.charAt(4)); 
			str.setCharAt(4,config.charAt(10)); 
		
		}
		else if (op=='D'){
			
			
			str.setCharAt(19,config.charAt(18)); 
			str.setCharAt(18,config.charAt(17)); 
			str.setCharAt(17,config.charAt(16)); 
			str.setCharAt(16,config.charAt(15)); 
			str.setCharAt(15,config.charAt(14)); 
			str.setCharAt(14,config.charAt(13)); 
			str.setCharAt(13,config.charAt(19)); 
			
		}
		else if (op=='E'){
			
			str.setCharAt(23,config.charAt(21)); 
			str.setCharAt(21,config.charAt(17)); 
			str.setCharAt(17,config.charAt(12)); 
			str.setCharAt(12,config.charAt(8)); 
			str.setCharAt(8,config.charAt(3)); 
			str.setCharAt(3,config.charAt(1)); 
			str.setCharAt(1,config.charAt(23)); 

		}
		else if (op=='F'){
			
			str.setCharAt(22,config.charAt(20)); 
			str.setCharAt(20,config.charAt(15)); 
			str.setCharAt(15,config.charAt(11)); 
			str.setCharAt(11,config.charAt(6)); 
			str.setCharAt(6,config.charAt(2)); 
			str.setCharAt(2,config.charAt(0)); 
			str.setCharAt(0,config.charAt(22)); 
			
		}
		else if (op=='G'){
			
			str.setCharAt(13,config.charAt(14)); 
			str.setCharAt(14,config.charAt(15)); 
			str.setCharAt(15,config.charAt(16)); 
			str.setCharAt(16,config.charAt(17)); 
			str.setCharAt(17,config.charAt(18)); 
			str.setCharAt(18,config.charAt(19)); 
			str.setCharAt(19,config.charAt(13)); 
			
		}
		else if (op=='H'){
			
			str.setCharAt(4,config.charAt(5)); 
			str.setCharAt(5,config.charAt(6)); 
			str.setCharAt(6,config.charAt(7)); 
			str.setCharAt(7,config.charAt(8)); 
			str.setCharAt(8,config.charAt(9)); 
			str.setCharAt(9,config.charAt(10)); 
			str.setCharAt(10,config.charAt(4)); 

		}
	
		//convert the StringBuilder type to String and return it
		return str.toString(); 
	}

	//count the number of occurrences of an item in a string
	public int count(char num, String searchStr) {
		int count=0;
		for (int i=0; i<searchStr.length();i++)
			if (searchStr.charAt(i)==num)
				count++;
		return count;
	}
	
	//extract the central square string from the config string
	public String centralSquare(String config){
		return config.substring(6,9)+config.substring(11,13)+config.substring(15,18);
	}
	
	@Override
	public int getHeuristicCost(String config, int modeFlag) {		
		String centralSqure=centralSquare(config);	
		if (modeFlag==1){
			int count1=count('1',centralSqure);
			int count2=count('2',centralSqure);
			int count3=count('3', centralSqure);
			return 8-Math.max(count1,Math.max(count2,count3));	
		}
			
		if (modeFlag==2)
			return 0;
		else if (modeFlag==3)
		{
			int count1=count('1',centralSqure);
			int count2=count('2',centralSqure);
			int count3=count('3', centralSqure);
			int h= 8-Math.max(count1,Math.max(count2,count3));	
			Double tmp=h*Math.random();
			return tmp.intValue();
		}
		return 0;
	}
	

}
