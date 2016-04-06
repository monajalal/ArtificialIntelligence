import java.util.LinkedList;

///////////////////////////////////////////////////////////////////////////////
//
//Title:            KMeans.java
//Files:            KMeans.java, KMeansResult.java, HW1.java
//Semester:         Fall 2012
//
//Author:           Matthew Klebenow - klebenow@wisc.edu
//CS Login:         klebenow
//Lecturer's Name:  Jerry Zhu
//
///////////////////////////////////////////////////////////////////////////////

/**
 * Class KMeans contains the K-Means clustering algorithm 'cluster' that returns
 * a KMeansResult object. Additional private utility methods for the clustering
 * algorithm also reside here.
 *
 * @author Matthew Klebenow
 *
 */
public class KMeans {
    /**
     * An implementation of the k-means clustering algorithm.
     *
     * @param centroids
     *            2D double array of centroid locations
     * @param instances
     *            2D double array of instance locations
     * @param stoppingPoint
     *            Double that represents the difference between successive
     *            iteration distortions that must be reached for the algorithm
     *            to terminate
     * @return A KMeansResult object
     */
    public KMeansResult cluster(double[][] centroids, double[][] instances,
            double stoppingPoint) {
        int iteration = -1;

        // Initialize the result
        KMeansResult res = new KMeansResult();
        LinkedList<Double> distortionList = new LinkedList<Double>();
        res.clusterAssignment = new int[instances.length];

        // Boolean variable for completion
        boolean done = false;

        // Loop until done
        while (!done) {
            // Increment iteration
            iteration++;

            // Assign instances to centroids
            assignInstances(centroids, instances, res);

            // Check for orphaned centroids
            boolean throughOrphanage = false;
            while (!throughOrphanage) {
                int orphanNum = -1;
                orphanNum = checkOrphanage(centroids, res);
                if (orphanNum >= 0) {
                    adoptOrphan(orphanNum, instances, centroids, res);
                } else {
                    throughOrphanage = true;
                }
            }

            // Update Centroids
            updateCentroids(centroids, instances, res);

            // Calculate new distortion
            double distortion = calculateDistortion(centroids, instances, res);
            distortionList.add(distortion);

            // Check if distortion difference is less than stoppingPoint
            // if(iteration > 0 && Math.abs(res.distortionIterations[iteration]
            // - res.distortionIterations[iteration-1]) < stoppingPoint){
            if (iteration > 0
                    && Math.abs(distortionList.get(iteration)
                            - distortionList.get(iteration - 1)) < stoppingPoint) {
                done = true;
            }

        }// Iterations completed

        // Set KMeansResult final centroids array
        res.centroids = centroids;

        // Initialize result's distortion array
        res.distortionIterations = new double[distortionList.size()];

        // Copy LinkedList distortionList to the result's distortion array
        for (int i = 0; i < res.distortionIterations.length; i++) {
            res.distortionIterations[i] = distortionList.get(i);
        }

        // Return KMeansResult res
        return res;
    }

    /**
     * This method assigns all instances to the centroid which is closest to
     * them. In this implementation, distance is measured with the L2 norm.
     *
     * @param centroids
     *            2D double array of centroid locations
     * @param instances
     *            2D double array of instance locations
     * @param res
     *            The KMeansResult object
     */
    private static void assignInstances(double[][] centroids,
            double[][] instances, KMeansResult res) {
        // Loop over all instances to update centroids
        for (int i = 0; i < instances.length; i++) {
            // For each instance, look for new centroid with minimum distance
            int centroidID = -1;
            double minDist = Double.MAX_VALUE;

            // Loop over all centroids j
            for (int j = 0; j < centroids.length; j++) {
                double currDist = 0;

                // Determine distance to centroid j for instance i
                // Loop over dimensions n
                for (int n = 0; n < centroids[j].length; n++) {
                    currDist += Math.pow(instances[i][n] - centroids[j][n], 2);
                }// End summation loop

                // Apply square root to current distance summation
                currDist = Math.sqrt(currDist);

                // Compare current distance to minimum thus far
                if (currDist < minDist) {
                    // Update new minimum distance
                    minDist = currDist;

                    // Update instance's new centroid
                    centroidID = j;
                }
            }// End centroids loop

            // Update instance information
            res.clusterAssignment[i] = centroidID;

        }// End instances loop
         // All instances have been assigned
    }

    /**
     * This method checks to see if any centroid has lost all of its instances.
     *
     * @param centroids
     *            2D double array of centroid locations
     * @param res
     *            The KMeansResult object
     * @return The number of the centroid which has been orphaned
     */
    private static int checkOrphanage(double[][] centroids, KMeansResult res) {
        // int containing centroid who is orphaned (-1 if no orphan)
        int orphanNum = -1;

        // LinkedList containing all centroid assignments
        LinkedList<Integer> assignments = new LinkedList<Integer>();

        // Add all assignments to searchable LinkedList
        for (int clusterNumber : res.clusterAssignment) {
            assignments.add(clusterNumber);
        }

        // Check if each centroid i has at least one assignment
        for (int i = 0; i < centroids.length; i++) {
            if (!assignments.contains(new Integer(i))) {
                // No assignment: record orphan number
                orphanNum = i;
                break;
            }
        }
        // Return index of centroid that is orphaned
        return orphanNum;
    }

    /**
     * Selects the instance that is farthest from it's assigned centroid and
     * changes its assignment to the orphaned centroid
     *
     * @param orphanNum
     *            Integer corresponding to the centroid that is orphaned
     * @param instances
     *            2D double array of instance locations
     * @param centroids
     *            2D double array of centroid locations
     * @param res
     *            The KMeansResult object
     */
    private static void adoptOrphan(int orphanNum, double[][] instances,
            double[][] centroids, KMeansResult res) {
        // Initialize maximum distance and respective instance
        double maxDist = -1;
        int maxInst = -1;

        // Loop over all instances
        for (int i = 0; i < instances.length; i++) {
            // Initialize current distance
            double currDist = 0;

            // Find instance assignment
            int instClus = res.clusterAssignment[i];

            // Loop over all dimensions n
            for (int n = 0; n < instances[i].length; n++) {
                // Calculate distance to centroid instClus
                currDist += Math.pow(instances[i][n] - centroids[instClus][n],
                        2);
            }

            // Apply square root
            currDist = Math.sqrt(currDist);

            // Test if current distance is greater than maximum distance
            if (currDist > maxDist) {
                // Update maximum distance and instance
                maxDist = currDist;
                maxInst = i;
            }
        }

        // Assign maximum distance instance to the previously orphaned centroid
        res.clusterAssignment[maxInst] = orphanNum;
    }

    /**
     * This method updates all centroids to be located at the mean location of
     * all instances that belong to that centroid.
     *
     * @param centroids
     *            2D double array of centroid locations
     * @param instances
     *            2D double array of instance locations
     * @param res
     *            The KMeansResult object
     */
    private static void updateCentroids(double[][] centroids,
            double[][] instances, KMeansResult res) {
        // Loop over all centroids
        for (int i = 0; i < centroids.length; i++) {
            // Make list to track instances of centroid i
            LinkedList<Integer> containedInstances = new LinkedList<Integer>();

            // Search all cluster assignments j for centroid i
            for (int j = 0; j < res.clusterAssignment.length; j++) {
                if (res.clusterAssignment[j] == i) {
                    containedInstances.add(j);
                }
            }// End cluster assignment search

            // Loop over all dimensions n of centroid i and update
            for (int n = 0; n < centroids[i].length; n++) {
                // Average contained instances in dimension j
                double tot = 0;
                for (int inst : containedInstances) {
                    tot += instances[inst][n];
                }
                // Divide by total number of contained instances and assign to
                // centroid i, dimension j
                centroids[i][n] = tot / containedInstances.size();
            }// End centroid update loop

        }// End centroids loop
    }

    /**
     * This method calculates the distortion of all centroids to their
     * instances.
     *
     * @param centroids
     *            2D double array of centroid locations
     * @param instances
     *            2D double array of instance locations
     * @param res
     *            The KMeansResult object
     * @return The current distortion of the clusters
     */
    private static double calculateDistortion(double[][] centroids,
            double[][] instances, KMeansResult res) {
        double distortion = 0;

        // Loop over centroids i
        for (int i = 0; i < centroids.length; i++) {

            // Search over instances j for assignment to cluster i
            for (int j = 0; j < res.clusterAssignment.length; j++) {

                // If instance j is assigned to cluster i...
                if (res.clusterAssignment[j] == i) {

                    // Loop over dimensions n of instance j
                    for (int n = 0; n < instances[j].length; n++) {
                        // Update distortion summation with each dimension
                        distortion += Math.pow(instances[j][n]
                                - centroids[i][n], 2);
                    }
                }
            }
        }// End centroid loop
        return distortion;
    }
}
