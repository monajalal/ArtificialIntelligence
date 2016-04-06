import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This is the main method that will load the application.
 * 
 * DO NOT MODIFY
 */

public class HW4 {
	/**
	 * Creates a fresh instance of the classifier.
	 * 
	 * @return	a classifier
	 */
	private static NaiveBayesClassifier getNewClassifier() {
		NaiveBayesClassifier nbc = new NaiveBayesClassifierImpl();
		return nbc;
	}

	/**
	 * Main method reads command-line flags and outputs either the classifications
	 * of the test file or uses cross-validation to compute a mean accuracy
	 * of the classifier.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println("usage: java HW3 <trainingFilename> <testFilename>");
		}

		// Output classifications on test data
		File trainingFile = new File(args[0]);
		File testFile = new File(args[1]);

		Instance[] trainingData = createInstances(trainingFile);
		Instance[] testData= createInstances(testFile);

		NaiveBayesClassifier nbc = getNewClassifier();
		nbc.train(trainingData, vocabularySize(trainingData, testData));

		double acc = 0.0;
		for (Instance i : testData) {
			ClassifyResult cr = nbc.classify(i.words);
			//if (cr.label == i.label) continue;
			if (cr.label == i.label) acc += 1;
			System.out.println(String.format("%s %s", cr.label, i.label));
			System.out.println(String.format("Log probability of spam: %f", cr.log_prob_spam));
			System.out.println(String.format("Log probability of ham: %f", cr.log_prob_ham));
		}
		acc /= testData.length;
		System.out.println(String.format("The classification accuracy on testing set is: %f\n", acc));
	}
	
	private static int vocabularySize(Instance[]... data) {
		Set<String> all = new HashSet<String>();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				for (int k = 0; k < data[i][j].words.length; k++) {
					all.add(data[i][j].words[k]);
				}
			}
		}
		return all.size();
	}

	/**
	 * Returns an array of the accuracy of the classifiers parameter over the array of folds.
	 * 
	 * @param classifiers	an array of classifiers	
	 * @param folds			an array of arrays of test data 
	 * @return				the accuracy of each classifier on its respective test data
	 */
	private static double[] accuracy(NaiveBayesClassifier[] classifiers,
			Instance[][] folds) {
		double[] ds = new double[classifiers.length];
		for (int i = 0; i < classifiers.length; i++) {
			double correct = 0.0;
			for (int j = 0; j < folds[i].length; j++) {
				if(folds[i][j].label == classifiers[i].classify(folds[i][j].words).label)
					correct++;
			}
			ds[i] = correct / folds[i].length;
		}
		return ds;
	}

	/**
	 * Uses a group of folds to return an array of classifiers. In cross validation,
	 * each folds[j] will be used to train classifiers[i] where i != j. The matching
	 * fold is left out as testing data.
	 * 
	 * @param folds		an array of arrays of data
	 * @return			an array of classifiers
	 */
	private static NaiveBayesClassifier[] train(Instance[][] folds) {
		NaiveBayesClassifier[] classifiers = new NaiveBayesClassifier[folds.length];
		for (int i = 0; i < classifiers.length; i++) {
			List<Instance> trainingData = new ArrayList<Instance>();
			for (int j = 0; j < folds.length; j++) {
				if(i != j) {
					for(int k = 0; k < folds[i].length; k++) {
						trainingData.add(folds[j][k]);
					}
				}
			}
			classifiers[i] = getNewClassifier();
			Instance[] d = trainingData.toArray(new Instance[trainingData.size()]);
			classifiers[i].train(d, vocabularySize(d));
		}
		return classifiers;
	}
	
	/**
	 * Reads the lines of the inputted file, treats the first token as the label
	 * and cleanses the remainder, returning an array of instances.
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 */
	private static Instance[] createInstances(File f) throws IOException {
		String[] ls = lines(f);
		Instance[] is = new Instance[ls.length];
		for (int i = 0; i < ls.length; i++) {
			String[] ws = cleanse(ls[i]).split("\\s");
			is[i] = new Instance();
			is[i].words = drop(ws, 1);
			is[i].label = Label.valueOf(ws[0].toUpperCase());
		}
		return is;
	}

	/**
	 * Some cleansing helps "thicken" the densities of the data model.
	 * 
	 * @param s
	 * @return	the string with punctuation removed and uniform case
	 */
	private static String cleanse(String s) {
		s = s.replace("?", " ");
		s = s.replace(".", " ");
		s = s.replace(",", " ");
		s = s.replace("/", " ");
		s = s.replace("!", " ");
		return s.toLowerCase();
	}

	/**
	 * Divides the data into folds.
	 * 
	 * @param foldSize	the size of each fold
	 * @param data		an array of data to divide into folds
	 * @return			an array of array of data
	 */
	private static Instance[][] fold(int foldSize, Instance[] data) {
		int k = data.length / foldSize;		
		Instance[][] es = new Instance[k][foldSize];
		for(int i = 0; i < k; i++) {
			for(int j = 0; j < foldSize; j++) {
				es[i][j] = data[i * foldSize + j];
			}
		}
		return es;
	}

	public static String[] lines(File f) throws IOException {
		FileReader fr = new FileReader(f);
		String[] l = lines(fr);
		fr.close();
		return l;
	}
	
	public static String[] lines(Reader r) throws IOException {
		BufferedReader br = new BufferedReader(r);
		String s;
		List<String> data = new ArrayList<String>();
		while ((s = br.readLine()) != null && !s.isEmpty()) {
			data.add(s);
		}
		br.close();
		return data.toArray(new String[data.size()]);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] drop(T[] xs, int i) {
		T[] ys = (T[]) Array.newInstance(xs[0].getClass(), xs.length - i);
		System.arraycopy(xs, i, ys, 0, xs.length - 1);
		return ys;		
	}
	
	public static double mean(double[] ds) {
		double sum = 0.0;
		for (int i = 0; i < ds.length; i++) {
			sum += ds[i];
		}
		return sum / ds.length;
	}
}
