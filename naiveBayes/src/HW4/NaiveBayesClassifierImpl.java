/**
 * HW4 SPAM detection using NaiveBayes Classification
 * Mona Jalal--CS540
 * Professor Jerry Zhu
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Your implementation of a naive bayes classifier. Please implement all four methods.
 */

public class NaiveBayesClassifierImpl implements NaiveBayesClassifier {
	
	//size of vocabulary
	Integer vocabularySize = 0;
	//SPAM count
	Integer SPAMcnt = 0;
	//HAM count
	Integer HAMcnt = 0;
	//SPAM instance count
	Integer SPAMdocuments = 0;
	//HAM instance count
	Integer HAMdocuments= 0;
	//defining constant delta::smoothing parameter
	private final static double delta = 0.00001;
	//creating a hashMap for SPAM and HAM frequency map
	private Map<String, Integer> SPAMfrequency=
			new HashMap<String, Integer>(vocabularySize);
	private Map<String, Integer> HAMfrequency=
			new HashMap<String, Integer>(vocabularySize);

	
	/**
	 * Trains the classifier with the provided training data and vocabulary size
	 */
	@Override
	public void train(Instance[] trainingData, int v) {
		Integer hashIdx;
		vocabularySize = v;
		//iterating over all the instances in the training data
		//to see which of them has the label equal to SPAM
		//and increasing the count of SPAM documents otherwise
		//increase the count of HAM documents
		for (Instance instance : trainingData) {
			if (instance.label == Label.HAM){
				HAMdocuments++;
			} else
				SPAMdocuments++;
			//for each word in each of the documents with label 
			//SPAM, increase the count of the SPAMcnt
			for (String word : instance.words) {
				if (instance.label == Label.HAM){
					HAMcnt++;
					//seeing if word already exists in hashMap
					hashIdx = HAMfrequency.get(word);
					if(hashIdx == null)
						//if it doesn't, assign it to 1
						HAMfrequency.put(word, 1);
					else 
						//else assign it to index+1
						HAMfrequency.put(word, hashIdx + 1);
				}
				else {
					SPAMcnt++;
					hashIdx = SPAMfrequency.get(word);
					if(hashIdx == null)
						SPAMfrequency.put(word, 1);
					else 
						SPAMfrequency.put(word, hashIdx + 1);
				}
			}
		}
		
	}

	/**
	 * Returns the prior probability of the label parameter, i.e. P(SPAM) or P(HAM)
	 */
	@Override
	public double p_l(Label label) {
		int allDocuments=SPAMdocuments + HAMdocuments;
		//if the label equals SPAM
		if (label == Label.SPAM)
			return (double)(SPAMdocuments)/allDocuments;
		//if the label equals HAM
		else
			return (double)HAMdocuments/allDocuments;
	}

	/**
	 * Returns the smoothed conditional probability of the word given the label,
	 * i.e. P(word|SPAM) or P(word|HAM)
	 */
	@Override
	public double p_w_given_l(String word, Label label) {
        
		Integer numberOfLabel;
		Integer size;
		//check to see if the word is in the SPAMfrequency hashMap
		if (label == Label.SPAM){
			numberOfLabel=SPAMfrequency.get(word);
			size=SPAMcnt;
		//or in the HAMfrequency hashMap
		} 
		else {
			numberOfLabel=HAMfrequency.get(word);
			size=HAMcnt;
		}
		if (numberOfLabel == null) 
			numberOfLabel=0; 
	

		return (double)(numberOfLabel+delta)/((vocabularySize*delta) + size);
	}
	
	/**
	 * Classifies an array of words as either SPAM or HAM. 
	 */
	@Override
	public ClassifyResult classify(String[] words) {
		
		ClassifyResult classifyRes=new ClassifyResult();
		Label SPAMorHAM;
	    //find the log probability of label equal to HAM
		double p_l_HAM=Math.log(p_l(Label.HAM));
		 //find the log probability of label equal to SPAM
		double p_l_SPAM=Math.log(p_l(Label.SPAM));
		
		double logProbSPAM=0;
		double logProbHAM=0;
		
		for(int i = 0; i < words.length; i++)
			logProbHAM+= Math.log(p_w_given_l(words[i], Label.HAM));
		for(int i = 0; i < words.length; i++)
			logProbSPAM+= Math.log(p_w_given_l(words[i], Label.SPAM));
		
		logProbHAM+=p_l_HAM;
	    logProbSPAM+=p_l_SPAM;
	    
		//depending on the weight of the sum assign HAM or SPAM 
	    //to the final label in the classifying results
		if(logProbSPAM > logProbHAM){
			SPAMorHAM = Label.SPAM;
		}
		else{
			SPAMorHAM = Label.HAM;
		}
		//create the classifier
        classifyRes.label=SPAMorHAM;
		classifyRes.log_prob_ham=logProbHAM;
		classifyRes.log_prob_spam=logProbSPAM;
		
		return classifyRes;
	}
}