/**
 * A k-means clustering algorithm implementation.
 *
 */

import java.util.ArrayList;

public class KMeans {
    public KMeansResult cluster(double[][] centroids, double[][] instances, double stoppingPoint) {
    	int noOfDimensions = centroids[0].length;
    	int[] clusterAssignment = new int[instances.length];
    	ArrayList<Double> distortionIterations = new ArrayList<Double>();

    	while(true){
    		//Classify
    		for (int i = 0; i < instances.length; i++) {
    			double[] dataPoint = instances[i];
    			//For each datapoint, get the distance to each centroid
    			double bestDistance = -1;
    			int bestCentroid = -1;
    			for (int j = 0; j < centroids.length; j++) {
    				double[] centroid = centroids[j];
    				//Get distance
    				Double distance = getEDistance(centroid, dataPoint);
    				if(distance == null){
    					System.out.println("Got a null distance");
    					return null; //Error
    				} else if(bestDistance < 0 || distance <= bestDistance){
    					bestDistance = distance;
    					bestCentroid = j;
    				}
    			}
    			if(bestCentroid == -1){
    				System.out.println("Can't find an owner");
    				return null; //Error due to not able to find an owner
    			}
    			clusterAssignment[i] = bestCentroid;
    		}

    		//Check if there are orphaned centroids
    		boolean[] hasChildren = new boolean[centroids.length];
    		for (int i = 0; i < clusterAssignment.length; i++) {
    			hasChildren[clusterAssignment[i]] = true;
			}
    		for (int i = 0; i < hasChildren.length; i++) {
    			if(hasChildren[i] == false){
    				//System.out.println("I see a orphaned centroid");
    				double bestDistance = -1;
    				int bestInstance = -1;
    				for (int j = 0; j < instances.length; j++) {
    					Double distance = getEDistance(instances[j], centroids[clusterAssignment[j]]);
    					if(distance == null){
    						System.out.println("Got a null distance");
    						return null; //Error
    					} else if(bestDistance < 0 || distance > bestDistance){
    						bestDistance = distance;
    						bestInstance = j;
    					}
    				}
    				if(bestInstance == -1){
    					System.out.println("Can't find a max distance");
    					return null; //Error due to not able to find an owner
    				}
    				clusterAssignment[bestInstance] = i;
    			}
			}


    		//Set new Centroids
    		for (int k = 0; k < centroids.length; k++) {
    			double[] totalSums = new double[noOfDimensions];
    			int totalElements = 0;
    			for (int l = 0; l < clusterAssignment.length; l++) {
    				if(clusterAssignment[l] == k){ //Belong in the same group
    					for (int m = 0; m < instances[l].length; m++) {
    						totalSums[m] += instances[l][m];
    					}
    					totalElements++;
    				}
    			}
    			//Set new centroid
    			for (int l = 0; l < totalSums.length; l++) {
    				centroids[k][l] = totalSums[l] / totalElements;
    			}
    		}

    		//Calculate distortion
    		//The sum of the squares of the e distance
    		double currDistortion = 0;
    		for (int j = 0; j < instances.length; j++) {
    			currDistortion += Math.pow(getEDistance(instances[j], centroids[clusterAssignment[j]]), 2);
    		}

    		distortionIterations.add(currDistortion);
    		//Check if need to continue
    		if(distortionIterations.size() > 1 && distortionIterations.get(distortionIterations.size() - 2) - currDistortion < stoppingPoint){
    			break;
    		} else {
    			continue;
    		}
    	}
    	KMeansResult result = new KMeansResult();
    	result.centroids = centroids;
    	result.clusterAssignment = clusterAssignment;
    	result.distortionIterations = new double[distortionIterations.size()];
    	for (int j = 0; j < result.distortionIterations.length; j++) {
    		result.distortionIterations[j] = distortionIterations.get(j);
		}

        return result;
    }

    public Double getEDistance(double[] pointA, double[] pointB){
    	if(pointA.length != pointB.length){
    		return null;
    	}
    	//Calculate E distance
    	double totalSum = 0;
    	for (int i = 0; i < pointA.length; i++) {
    		totalSum += (pointA[i] - pointB[i]) * (pointA[i] - pointB[i]);
		}
		return Double.valueOf(Math.sqrt(totalSum));
    }

}
