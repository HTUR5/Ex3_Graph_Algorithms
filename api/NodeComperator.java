package api;

import java.util.Comparator;



public class NodeComperator implements Comparator<Node> {


    @Override
    public int compare(Node o1, Node o2) {

        if (o1.getWeight()<o2.getWeight())
        {
            return -1;
        }
        if (o1.getWeight()>o2.getWeight())
        {
            return 1;
        }
        return 0;
    }
}