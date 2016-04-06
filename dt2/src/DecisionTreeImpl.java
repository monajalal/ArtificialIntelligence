import java.util.ArrayList;
import java.util.HashMap;
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
	public List<Instance> instances;
	


	/**
	 * Answers static questions about decision trees.
	 */
	DecisionTreeImpl() {
		// no code necessary
		// this is void purposefully
	}
	/** 
	 * creating log2 using log base 10
	 * @param x
	 * @return
	 */
	private  double log2( double x ){
		return Math.log( x ) / Math.log( 2 );
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
		ArrayList<Integer> attributeIndices = new ArrayList<Integer>();
		for (int i = 0; i < attributes.size(); i++) {
	
			attributeIndices.add(i);
		}
		
		
		this.root = buildTree(train.instances, train.attributes, pluralityValue(train.instances), -1);
	
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
		
		return null;
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
		this.instances=train.instances;
		int numLabels=train.labels.size();
		// TODO: add code here
		double entropy = calcEntropy(instances, numLabels );
		double mutualInformation = 0;
		for (int i = 0; i < attributes.size(); i++) { 
			double condEntropy=calcConditionalEntropy(i,
					                                  train.instances,
					                                  train.attributeValues.get(train.attributes.get(i)).size(),
					                                  train.labels.size());
			mutualInformation = entropy - condEntropy;
			System.out.printf("%s %.5f\n", attributes.get(i) + "", mutualInformation);
		}
    }
	

	
	private double calcEntropy(List<Instance> instances,int numLabels){
		
		int[] labelsRecord = new int[numLabels];
		int numInstances = instances.size();
		for (Instance instance : instances) {
			labelsRecord[instance.label]++;
		}
		double entropy = 0;
	
		for (int i = 0; i < labelsRecord.length; i++) {
			if (numInstances!=0) //to avoid division by zero
			  if (labelsRecord[i]!=0){ //log2(0) is undefined
				double P = (double)(labelsRecord[i])/numInstances; 
				entropy += (-P * log2(P));	    
		    }
		}
		return entropy;
		
		 
	}
	
	
	private double calcConditionalEntropy(int attrIndex,
			                              List<Instance> instances,
			                              int numAttributes,
			                              int numLabels){

		
		int[] attr= new int[numAttributes];
		ArrayList<ArrayList<Instance>> labelInstances = new ArrayList<ArrayList<Instance>>();
	
		for (int i = 0; i < numAttributes; i++) {
			labelInstances.add(new ArrayList<Instance>());
		}
		int totalInstances = instances.size();
		for (Instance instance : instances) {
			attr[instance.attributes.get(attrIndex)]++;
			labelInstances.get(instance.attributes.get(attrIndex)).add(instance);
		}
		double conditionalEntropy = 0;
		for (int i = 0; i < attr.length; i++) {
			if (totalInstances != 0 
			    && attr[i] != 0) {
				double P = (double)(attr[i])/totalInstances; 
				double partialCondEntropy = calcEntropy(labelInstances.get(i), numLabels);
				conditionalEntropy += P * partialCondEntropy;
			}
		}
		
		return conditionalEntropy;

	}
	
	
	private boolean isPureNode(List<Instance> instances){
		int currLabel = Integer.MIN_VALUE;
		for (Instance instance : instances) {
			if (currLabel == Integer.MIN_VALUE){
				currLabel = instance.label;
				continue;
			}
			if (currLabel != instance.label){
				return false;
			}
		}	
		return true;
	}
	
	

	private Integer pluralityValue(List<Instance> instances){
	    int[] labelsCount = new int[this.labels.size()];
	    for(Instance instance : instances){
	        labelsCount[instance.label]++;
	    }
	    int maxLabelCount = -1;
	    int maxLabelIndex = -1;
	    for(int i = 0; i < labelsCount.length; i++){
	        if(labelsCount[i] > maxLabelCount){
	            maxLabelCount = labelsCount[i];
	            maxLabelIndex = i;
	        }
	    }
	    return maxLabelIndex;
	}
	
	
	
	
	
	 
	
	
	private DecTreeNode buildTree(List<Instance> instances,
			               List<String> attributes,
			               Integer defLab,
			               Integer parVal){
		if (instances.isEmpty()){
		    return (new DecTreeNode(defLab, null, parVal, true));
		}
		if(isPureNode(instances)){
	        return (new DecTreeNode(instances.get(0).label, null, parVal, true));
	    }
		
		defLab = pluralityValue(instances);
		
		if (attributes.isEmpty()) {
			return (new DecTreeNode(defLab, null, parVal, true));
		}
         
		//BestQuestion
	    double entropy = calcEntropy(instances, labels.size());
		int largestEntropyIndex = Integer.MIN_VALUE;
		double largestMutualInfomation = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < attributes.size(); i++) {
			String attrName=this.attributes.get(i);
			double conditionalEntropy = calcConditionalEntropy(i,
					                                           instances,
					                                           this.attributeValues.get(attrName).size(),
					                                           this.labels.size());
               
			double mutualInformation = entropy - conditionalEntropy;
			if (mutualInformation > largestMutualInfomation){
				largestMutualInfomation = mutualInformation;
				largestEntropyIndex = i;
			}
		}
		DecTreeNode currentNode;
		String largestEntropyAttr = attributes.get(largestEntropyIndex);
		currentNode = new DecTreeNode(null, this.attributes.indexOf(largestEntropyAttr), parVal, false);
		int numBranches = attributeValues.get(largestEntropyAttr).size();
		
		ArrayList<ArrayList<Instance>> labelInstances = new ArrayList<ArrayList<Instance>>();
		for (int i = 0; i < numBranches; i++) {
			labelInstances.add(new ArrayList<Instance>());
		}
		for (Instance instance : instances) {
			labelInstances.get(instance.attributes.get(this.attributes.indexOf(largestEntropyAttr))).add(instance);
		}
		List<String> remainingAttributes = new ArrayList<String>(attributes);
		remainingAttributes.remove(largestEntropyAttr);
		for (int i = 0; i < numBranches; i++) {
			currentNode.addChild(buildTree(labelInstances.get(i), remainingAttributes, defLab, i));
		}
		return currentNode;
	}
}

