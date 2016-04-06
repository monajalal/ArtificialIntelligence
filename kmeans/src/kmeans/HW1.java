package kmeans;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class that acts as a launcher for your clustering algorithm. DO NOT MODIFY.
 *
 */

public class HW1 {
	/*
	 * Reads the command-line arguments, performs type conversion, calls clustering
	 * algorithm and prints results to the console.
	 */
	public static void main(String[] args) throws IOException {
		// Read instances
		String filename = args[0];
		double[][] instances = toDoubles(read(new File(filename)));

		// Read initial centroids
		filename = args[1];
		double[][] centroids = toDoubles(read(new File(filename)));

		// Read threshold value
		double threshold = Double.parseDouble(args[2]);

		// Read output flag
		int flag = Integer.parseInt(args[3]);

		// Initialize clustering algorithm
		KMeans kMeans = new KMeans();
		KMeansResult res = kMeans.cluster(centroids, instances, threshold);
		
		// Check null result
		if(res == null) {
			System.out.println("Please implement the cluster method to return a non-null result.");
			return;
		}

		// Print results
		if (flag == 1) {
			for (double[] c : res.centroids) {
				print(c);
			}
		} else if (flag == 2 ) {
			for (int c : res.clusterAssignment) {
				System.out.println(c);
			}
		} else if (flag == 3) {
			String format = "%." + 3 + "f";
			for (double d : res.distortionIterations) {
				System.out.println(String.format(format, d));
			}	
		} else if (flag == 4) {
			String format = "%." + 3 + "f";
			for (double d : res.clusterDiameter) {
				System.out.println(String.format(format, d));
			}	
		}
	}

	// Formats a double array
	private static void print(double[] c) {
		String format = "%." + 3 + "f";
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(format, c[0]));
		for (int j = 1; j < c.length; j++) {
			sb.append(",");
			sb.append(String.format(format, c[j]));
		}
		System.out.println(sb.toString());
	}

	// Splits a string into an array of doubles
	private static double[] splitToDouble(String s, String sep) {
		String[] ss = s.split(sep);
		double[] is = new double[ss.length];
		for (int i = 0; i < is.length; i++)
			is[i] = Double.parseDouble(ss[i]);
		return is;
	}

	// Converts an array of string to an array of doubles
	private static double[][] toDoubles(String[][] data) {
		double[][] ds = new double[data.length][];
		for (int i = 0; i < ds.length; i++) {
			int n = data[i].length;
			ds[i] = new double[n];
			for (int j = 0; j < n; j++) {
				ds[i][j] = Double.parseDouble(data[i][j]);
			}
		}
		return ds;
	}

	// Reads a file into an array of strings, comma-separated
	private static String[][] read(File f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		String s;
		List<String[]> data = new ArrayList<String[]>();
		while ((s = br.readLine()) != null) {
			String[] fields = s.split(",");
			data.add(fields);
		}
		br.close();
		String[][] results = new String[data.size()][];
		for (int i = 0; i < data.size(); i++)
			results[i] = data.get(i);
		return results;
	}

}
