/**
 * An SMS instance.
 * 
 * DO NOT MODIFY.
 */
public class ClassifyResult {
	/** 
	 * Spam or ham
	 */
	public Label label; 	
	/**
	 * The log probability that SMS message is spam
	 * Does not have to be normalized
	 */
	public double log_prob_spam;
	/**
	 * The log probability that SMS message is ham
	 * Does not have to be normalized
	 */
	public double log_prob_ham;
}
