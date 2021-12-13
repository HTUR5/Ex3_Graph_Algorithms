package api;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class WeightedGraph implements DirectedWeightedGraph{
    private HashMap<Integer,Node> nodesMap;
    private HashMap<Point2D, EdgeData> edgeMap; //point.x = edge.src.key, point.y = edge.dest.key
    private HashMap<Integer, HashMap<Integer, EdgeData>> edgeMapS; //key = edge.src.key and its value is all the edge which go out of this node (the dest)
    private HashMap<Integer, HashMap<Integer, EdgeData>> edgeMapD; //key = edge.dest.key and its value is all the edge which go in of this node (the src)
    private int count;

    public WeightedGraph(){
        this.nodesMap = new HashMap<>();
        this.edgeMap = new HashMap<>();
        this.edgeMapS = new HashMap<>();
        this.edgeMapD = new HashMap<>();
        this.count = 0;
    }

    public HashMap<Integer, Node> getNodesMap() {
        return nodesMap;
    }

    public HashMap<Point2D, EdgeData> getEdgeMap() {
        return edgeMap;
    }

    public HashMap<Integer, HashMap<Integer, EdgeData>> getEdgeMapS() {
        return edgeMapS;
    }

    public HashMap<Integer, HashMap<Integer, EdgeData>> getEdgeMapD() {
        return edgeMapD;
    }

    @Override
    public NodeData getNode(int key) {
        if(!nodesMap.containsKey(key)) {
            return null;
        }
        return nodesMap.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        Point2D p =new Point(src,dest);
        if(!this.edgeMap.containsKey(p)) {
            return null;
        }
        return edgeMap.get(p);
    }

    @Override
    public void addNode(NodeData n) {
        nodesMap.put(n.getKey(), (Node)n);
        count++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (this.nodesMap.containsKey(src) && this.nodesMap.containsKey(dest)) {
            Node n1 = (Node) this.getNode(src);
            Node n2 = (Node) this.getNode(dest);
            Edge edge = new Edge(n1, n2, w, "", 0);
            //for edgeMapS
            if (this.edgeMapS.get(src) == null) {
                HashMap<Integer, EdgeData> innerMap = new HashMap<>();
                this.edgeMapS.put(src, innerMap);
                innerMap.put(dest, edge);
            } else {
                this.edgeMapS.get(src).put(dest, edge);
            }
            //for edgeMapD
            if (this.edgeMapD.get(dest) == null) {
                HashMap<Integer, EdgeData> innerMap = new HashMap<>();
                this.edgeMapD.put(dest, innerMap);
                innerMap.put(src, edge);
            } else {
                this.edgeMapD.get(dest).put(src, edge);
            }
            //for edgeMap
            Point2D p2 = new Point(src, dest);
            this.edgeMap.put(p2, edge);

            count++;
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        HashMap<Integer, NodeData> copyMap =(HashMap<Integer, NodeData>)this.nodesMap.clone();
        return new Iterator<NodeData>() {
            Iterator<NodeData> iter = copyMap.values().iterator();
            private int finalMC = getMC();
            @Override
            public boolean hasNext() {
                if(finalMC != getMC()){
                    throw new NoSuchElementException();
                }
                return iter.hasNext();
            }

            @Override
            public NodeData next() {
                if(finalMC != getMC()){
                    throw new NoSuchElementException();
                }
                return iter.next();
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<EdgeData>() {
            Iterator<EdgeData> iter = edgeMap.values().iterator();
            private int finalMC = getMC();
            @Override
            public boolean hasNext() {
                if(finalMC != getMC()){
                    throw new NoSuchElementException();
                }
                return iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if(finalMC != getMC()){
                    throw new NoSuchElementException();
                }
                return iter.next();
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterator<EdgeData>() {
            Iterator<EdgeData> it_edge = edgeMapS.get(node_id).values().iterator();
            private int finalMC = getMC();
            @Override
            public boolean hasNext() {
                if(finalMC != getMC()){
                    throw new NoSuchElementException();
                }
                return it_edge.hasNext();
            }

            @Override
            public EdgeData next() {
                if(finalMC != getMC()){
                    throw new NoSuchElementException();
                }
                return it_edge.next();
            }
        };
    }

    @Override
    public NodeData removeNode(int key) {
        if(!this.nodesMap.containsKey(key)) {return null;}
        NodeData node = this.getNode(key);
        //remove all the edge from edgeMapD when key is src from edgeMapS
        if(this.edgeMapS.get(key) != null) {
            for (Integer me : this.edgeMapS.get(key).keySet()) {
                int dest = me;
                this.edgeMapD.get(dest).remove(key);
                //remove from edgeMap
                Point2D p = new Point(key, dest);
                this.edgeMap.remove(p);
                count++;
                if(this.edgeMapD.get(dest).isEmpty()) {
                    this.edgeMapD.remove(dest);
                }
            }
            this.edgeMapS.remove(key);
        }
        //remove all the edge from edgeMapS when key is dest from edgeMapD
        if(this.edgeMapD.get(key) != null) {
            for(Integer me : this.edgeMapD.get(key).keySet()) {
                int src = me;
                this.edgeMapS.get(src).remove(key);
                //remove from edgeMap
                Point2D p = new Point(src, key);
                this.edgeMap.remove(p);
                count++;
                if(this.edgeMapS.get(src).isEmpty()) {
                    this.edgeMapS.remove(src);
                }
            }
            this.edgeMapD.remove(key);
        }
        //remove node from nodesMap
        this.nodesMap.remove(key);
        count++;
        return node;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if(this.getEdge(src, dest) == null) {return null;}
        Edge edge = (Edge) this.getEdge(src, dest);
        this.edgeMapD.get(dest).remove(src);
        if(this.edgeMapD.get(dest).isEmpty()) {
            this.edgeMapD.remove(dest);
        }
        this.edgeMapS.get(src).remove(dest);
        if(this.edgeMapS.get(src).isEmpty()) {
            this.edgeMapS.remove(src);
        }
        Point2D p = new Point(src,dest);
        edgeMap.remove(p);
        count++;
        return  edge;
    }

    @Override
    public int nodeSize() {
        return nodesMap.size();
    }

    @Override
    public int edgeSize() {
        return edgeMap.size();
    }

    public void setMC(int count) {
        this.count = count;
    }

    @Override
    public int getMC() {
        return count;
    }

    public String toString(){
        Iterator itNodes = this.nodeIter();
        Iterator itEdges = this.edgeIter();

        String answer = "{\n" + "\"Edges\": [\n";

        while(itEdges.hasNext()){
            answer = answer+itEdges.next().toString();
            if(itEdges.hasNext()){
                answer = answer + ",\n";
            }
            else{
                answer = answer + "\n";
            }
        }

        answer = answer +"],\n" + "\"Nodes\":[";
        while(itNodes.hasNext()){
            answer = answer+itNodes.next().toString();
            if(itNodes.hasNext()){
                answer = answer + ",\n";
            }
            else{
                answer = answer + "\n";
            }
        }
        answer = answer + "]\n" + '}';
        return answer;
    }
}
