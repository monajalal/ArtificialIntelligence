import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Do not modify.
 * 
 * Direct questions to nixon@cs.wisc.edu
 */
public class HW2 {

	/**
	 * Runs the tests for HW2
	 */
	public static void main(String[] args) {
		 if (args.length < 4) {
			 System.out.println("usage: java HW2 <modeFlag> <trainFilename> " +
			 		"<tuneFilename> <testFilename>");
			 System.exit(-1);
		 }
		 
		  /*
		  * mode 0 : output the mutual information of each attribute at the root node
		  *      1 : create a decision tree from a training set, output the tree
		  *      2 : create a decision tree from a training set, output the classifications 
		  *          of a test set
		  *      3 : create a decision tree from a training set then tune, output the tree
		  *      4 : create a decision tree from a training set then tune, output the
		  *          classifications of a test set
		  */
		 int mode = Integer.parseInt(args[0]);
		 if (0 > mode || mode >= 5) {
			 System.out.println("mode must be between 0 and 4");
			 System.exit(-1);
		 }

		 if (mode == 0) {
			 (new DecisionTreeImpl()).rootInfoGain(createDataSet(args[1]));
			 return;
		 }
		 
		 // Turn text into array
		 // Only create the sets that we intend to use
		 // Verify that our attributes and labels are consistent in ordering across sets
		 DataSet trainSet = createDataSet(args[1]);
		 DataSet tuneSet = null;
		 if (mode > 2) {
			 tuneSet = createDataSet(args[2]);
			 if (!trainSet.sameMetaValues(tuneSet)) {
				 System.out.println("bad meta-values in tune set");
				 System.exit(-1);
			 }
		 }
		 DataSet testSet = null;
		 if (mode % 2 == 0) {
			 testSet = createDataSet(args[3]);
			 if (!trainSet.sameMetaValues(testSet)) {
				 System.out.println("bad meta-values in test set");
				 System.exit(-1);
			 }
		 }
		 
		 // Create decision tree
		 DecisionTree tree = null;
		 if (mode <= 2) {
			 tree = new DecisionTreeImpl(trainSet);
		 } else {
			 tree = new DecisionTreeImpl(trainSet, tuneSet);
		 }
		 
		 // Run test
		 if (mode % 2 == 1) {
			 tree.print();
		 } else {
			 for (Instance instance : testSet.instances) {
				 System.out.println(tree.classify(instance));
			 }
		 }
	}

	/**
	 * Converts from text file format to DataSet format.
	 * From the homework spec:
	 * All data files (training, tuning, test) will contain a list of classes 
	 * and attribute values, followed by the actual data.  Attributes and 
	 * classes are always discretely valued.  A line that begins with a double 
	 * slash // is a comment and should be ignored.  In other lines, elements 
	 * will be comma-separated.  For a line beginning with %%, it contains two 
	 * possible classes.  Each line that begins with ## specifies the name and 
	 * all possible discrete values of one attribute.  The order of successive 
	 * attributes is important as this is the same order used in each of the 
	 * examples in the file.
	 */
	private static DataSet createDataSet(String file) {
		DataSet set = new DataSet();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			while (in.ready()) { 
				String line = in.readLine(); 
				String prefix = line.substring(0, 2);
				if (prefix.equals("//")) {
				} else if (prefix.equals("%%")) {
					set.addLabels(line);
				} else if (prefix.equals("##")) {
					set.addAttribute(line);
				} else {
					set.addInstance(line);
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} 
		
		
		return set;
	}
}
