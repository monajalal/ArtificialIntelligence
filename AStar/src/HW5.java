import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This is the main method that will load the application.
 * 
 * DO NOT MODIFY
 */

public class HW5 {
	/**
	 * Creates a fresh instance of the search alogirthm.
	 * 
	 * @return	a A* search class instance
	 */
	private static AStarSearchImpl getNewAStarSearch() {
		return new AStarSearchImpl();		
	}

	/**
	 * Main method reads command-line flags and outputs the goal results
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("usage: java HW5 <inputFileName> <modeFlag>");
		}

		String[] initConfigs = readlines(args[0]);
		int modeFlag = Integer.valueOf(args[1]);
		//System.out.print(Arrays.deepToString(initConfigs));
		SearchResult res = getNewAStarSearch().search(initConfigs[0], modeFlag);
		res.printConfig();			// print the goal configuration
		res.printOpSeq();			// print the operation sequence
		res.printNumPoppedStates();	// print the No. of goal configuration to pop out from the queue.	
	}
	
	/**
	 * Read lines from the file
	 */
	public static String[] readlines(String fileName) throws IOException {
		File f = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(f));		
		List<String> data = new ArrayList<String>();
		String s;
		while ((s = br.readLine()) != null && ! s.isEmpty()) {
			data.add(s);
		}
		br.close();
		return data.toArray(new String[data.size()]);		
	}
	
}
