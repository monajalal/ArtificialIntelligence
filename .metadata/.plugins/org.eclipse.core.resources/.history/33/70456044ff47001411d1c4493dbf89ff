import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fill in the implementation details of the class DecisionTree using this file.
 * Any methods or secondary classes that you want are fine but we will only
 * interact with those methods in the DecisionTree framework.
 * 
 * You must add code for the 5 methods specified below.
 * 
 * See DecisionTree for a description of default methods.
 */
public class DecisionTreeImpl extends DecisionTree {
	private DecTreeNode root;
	private List<String> labels; // ordered list of class labels
	private List<String> attributes; // ordered list of attributes
	private Map<String, List<String>> attributeValues; // map to ordered
														// discrete values taken
														// by attributes

	/**
	 * Answers static questions about decision trees.
	 */
	DecisionTreeImpl() {
		// no code necessary
		// this is void purposefully
	}

	/**
	 * Build a decision tree given only a training set.
	 * 
	 * @param train: the training set
	 */
	DecisionTreeImpl(DataSet train) {

		this.labels = train.labels;
		this.attributes = train.attributes;
		this.attributeValues = train.attributeValues;
		// TODO: add code here
	}

	/**
	 * Build a decision tree given a training set then prune it using a tuning
	 * set.
	 * 
	 * @param train: the training set
	 * @param tune: the tuning set
	 */
	DecisionTreeImpl(DataSet train, DataSet tune) {

		this.labels = train.labels;
		this.attributes = train.attributes;
		this.attributeValues = train.attributeValues;
		// TODO: add code here
	}

	@Override
	public String classify(Instance instance) {

		// TODO: add code here
	}

	@Override
	/**
	 * Print the decision tree in the specified format
	 */
	public void print() {

		printTreeNode(root, null, 0);
	}
	
	/**
	 * Prints the subtree of the node
	 * with each line prefixed by 4 * k spaces.
	 */
	public void printTreeNode(DecTreeNode p, DecTreeNode parent, int k) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < k; i++) {
			sb.append("    ");
		}
		String value;
		if (parent == null) {
			value = "ROOT";
		} else{
			String parentAttribute = attributes.get(parent.attribute);
			value = attributeValues.get(parentAttribute).get(p.parentAttributeValue);
		}
		sb.append(value);
		if (p.terminal) {
			sb.append(" (" + labels.get(p.label) + ")");
			System.out.println(sb.toString());
		} else {
			sb.append(" {" + attributes.get(p.attribute) + "?}");
			System.out.println(sb.toString());
			for(DecTreeNode child: p.children) {
				printTreeNode(child, p, k+1);
			}
		}
	}

	@Override
	public void rootInfoGain(DataSet train) {

		
		this.labels = train.labels;
		this.attributes = train.attributes;
		this.attributeValues = train.attributeValues;
		// TODO: add code here
	
}
