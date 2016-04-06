package kmeans;
/**
 * Result class for KMeans cluster method. DO NOT MODIFY. 
 *
 */

public class KMeansResult {
	// the position of the centroids after your clustering is complete
	double[][] centroids;
	// the sequence of distortions recorded on each iteration of the clustering algorithm 
	double[] distortionIterations;
	// the index of the centroid assigned to each instance in the final clustering
	int[] clusterAssignment;
	// the diameter of each cluster in final clustering 
	double[] clusterDiameter;
}
