package kmeans;
/**
 * A k-means clustering algorithm implementation.
 * 
 */

import java.util.ArrayList;

public class KMeans{
        private int[] numInstances;

		public KMeansResult cluster(double[][] centroids,
     		                    double[][] instances, 
    		                    double threshold) {
    	
    	// Variable length distortionIterations 
    	ArrayList<Double> distortionIterations = new ArrayList<Double>(); 
    	int[] clusterAssignment = new int[instances.length];
    	//number of dimensions
    	int dims=centroids[0].length;
    	Double distance;
        //Repeat until convergence
    	//saving all the number of instances within each centroid
	    numInstances= new int[centroids.length];
	    //checking to see if there's any orphan centroid or not
	    boolean hasOrphanCentroid = false;
    	
    	while(true){
    	    do {
    	    	assignInstances(instances, centroids, clusterAssignment);
    	    	hasOrphanCentroid=false;
            	int orphanCentroid=Integer.MIN_VALUE;
	    		//finding the orphan cluster 		
	    		//check if all the centroids have some points assigned to them
	            for (int i = 0; i < centroids.length; i++)
	            	if (numInstances[i]==0)
	            	{
	            		//save the centroid number if there's no point assigned to it
	                    orphanCentroid = i;
	                    hasOrphanCentroid=true;
	                    break;
	                }
	           
	            
	            double maxDistance = Double.NEGATIVE_INFINITY;
	            int maxInstance = Integer.MIN_VALUE;
	            //if number of orphan centroids is bigger than 0, try to
	            //take them out of orphanage by assigning the instances
	            //that have the farthest distances from their assigned centroids
	            if (hasOrphanCentroid==true){ 
		            // check all the instances to see which one is farthest from its centroid
		            for (int i = 0; i < instances.length; i++) {
		            	distance = 0.0;
		            	int clusterNumber = clusterAssignment[i];
		            	distance=EuclideanDistance(instances[i],centroids[clusterNumber]);
		                //save the distance and instance if it's greater than current max distance
		                if (distance > maxDistance){
		                	maxInstance = i;
		                	maxDistance = distance;
		                    }
		                }
		               //clusterAssignment[maxInstance]= orphanCentroid;
		               //clusterAssignment[maxInstance]=orphanCentroid;
			           for(int j = 0; j < instances[maxInstance].length; j++)
			        	    centroids[orphanCentroid][j]=instances[maxInstance][j];        
		    	    }

	            } while (hasOrphanCentroid);
    	    
    		//re-calculating the centroids based on the assigned observations
    		//System.out.println(centroids.length);
    		for (int i=0; i < centroids.length; i++) {
    			double[] sum = new double[dims];
    			int clusterMembers = 0;
    			for (int j=0; j < clusterAssignment.length; j++) {
    				if(clusterAssignment[j] == i){ 
    					for (int k=0; k < instances[j].length; k++) {
    						sum[k] += instances[j][k];
    					}
    					clusterMembers++;
    				}
    			}
    			for (int j=0; j < sum.length; j++) {
    				centroids[i][j] = sum[j] / clusterMembers;
    			}
    		}
    		
    		//find the distortion
    		double distortion=0;
    		for (int j=0; j < instances.length; j++) {
    			distortion += Math.pow(EuclideanDistance(instances[j], centroids[clusterAssignment[j]]), 2);
    		}

    		distortionIterations.add(distortion);
    		//for determining when to stop iterations
    		if(distortionIterations.size() > 1 &&
    		  ( Math.abs(distortionIterations.get(distortionIterations.size() - 1)-
    				   distortionIterations.get(distortionIterations.size() - 2))/(distortionIterations.get(distortionIterations.size() - 1))
    				   < threshold)){
    			break;
    		} else {
    			continue;
    		}
    	}
    	
    	//save the results as in the KMeansResult.java
    	KMeansResult kResult = new KMeansResult();
    	kResult.centroids = centroids;
    	kResult.clusterAssignment=clusterAssignment;
    	kResult.distortionIterations = new double[distortionIterations.size()];
    	for (int j = 0; j < kResult.distortionIterations.length; j++) {
    		kResult.distortionIterations[j] = distortionIterations.get(j);
		}

        return kResult;
    }
		
    /***
     * Find the Euclidean distance of two points x and y
     * @param x
     * @param y
     * @return
     */
    public double EuclideanDistance(double[] x, double[] y)
    {
        double sum = 0;
        for(int i=0;i<x.length;i++) {
           sum = sum + Math.pow((x[i]-y[i]),2);
        }
        return Math.sqrt(sum);
    
    }
    /***
     * Assigning instances to the centroids   
     * @param instances
     * @param centroids
     * @param clusterAssignment
     */
    public void assignInstances(double[][] instances, double[][] centroids, int[] clusterAssignment ){
    	for (int i=0;i<numInstances.length;i++)
    		numInstances[i]=0;
    	for (int i=0; i < instances.length; i++) {
			double [] observation = instances[i];
			double distance;
			//find the distance of each observation to all of the centroids
		 	//assigning the smallest possible number to best distance initially
			double bestDistance = Double.POSITIVE_INFINITY;
			//assigning the smallest possible number to best centroid initially 
			int bestCentroid = Integer.MIN_VALUE;
			for (int j=0; j < centroids.length; j++) {
				double[] centroid = centroids[j];
				//find the Euclidean distance of centroid and each instance
				distance = EuclideanDistance(centroid, observation);
				if(distance < bestDistance){
					bestCentroid = j;
					bestDistance = distance;
				}
			}
		    clusterAssignment[i]=bestCentroid;
			numInstances[bestCentroid]++;
		}
    }
    

}

