package api;

import java.util.Comparator;

/**
 * this class is override of java's comparator. we implement this class for the DirectedWeightedGraphAlgorithms.
 * so we can use it in our algorithm: find the shortest path.
 * when we use priority queue, we want it to sort the node by their tag (where we save the minimal weight)
 */

public class NodeComperator implements Comparator<Node> {


    @Override
    public int compare(Node o1, Node o2) {

        if (o1.getWeight()<o2.getWeight()) {
            return -1;
        }
        if (o1.getWeight()>o2.getWeight()) {
            return 1;
        }
        return 0;
    }
}