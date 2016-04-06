/**
 * This class provides a framework for accessing a decision tree.
 * Do not modify or place code here, instead create an
 * implementation in a file DecisionTreeImpl. 
 * 
 * Direct questions to nixon@cs.wisc.edu
 */
abstract class DecisionTree {
	/**
	 * Evaluates the learned decision tree on a single instance.
	 * @return the classification of the instance
	 */
	abstract public String classify(Instance instance);
	
	/**
	 * Prints the tree in specified format. 
	 */
	abstract public void print();
	
	/**
	 *  Print the information gain of each attribute as computed
	 *  from creating the root node for the given DataSet.
	 *  
	 *  Print the Attribute name then a space then the information gain
	 *  Use precision of precisely 5 decimal places in output.
	 *  
	 *  Example:
	 *  PTYPE 0.12345
     *  AGE 0.45678
     *  SEX 0.24890
	 */
	abstract public void rootInfoGain(DataSet train);
}
