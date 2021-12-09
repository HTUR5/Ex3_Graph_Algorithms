package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class algo implements DirectedWeightedGraphAlgorithms {

    private WeightedGraph g;

    public algo() {
        this.g = new WeightedGraph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = (WeightedGraph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        WeightedGraph g = (WeightedGraph) this.getGraph();
        WeightedGraph copy = new WeightedGraph();
        //copy count
        copy.setMC(this.getGraph().getMC());
        //copy nodes
        if (g.getNodesMap().size() > 0) {
            for (Integer me : g.getNodesMap().keySet()) {
                int src = me;
                copy.addNode(g.getNodesMap().get(src));
            }
        }
        //copy edges
        if (g.getEdgeMap().size() > 0) {
            for (Point2D me : g.getEdgeMap().keySet()) {
                Edge edge = (Edge) g.getEdgeMap().get(me);
                //add to edges
                copy.getEdgeMap().put(me, edge);
                //add to adgesS
                if (copy.getEdgeMapS().containsKey((int) me.getX())) {
                    copy.getEdgeMapS().get((int) me.getX()).put((int) me.getY(), edge);
                } else {
                    HashMap<Integer, EdgeData> innerMap = new HashMap<>();
                    copy.getEdgeMapS().put((int) me.getX(), innerMap);
                    innerMap.put((int) me.getY(), edge);
                }
                //add to adgesS
                if (copy.getEdgeMapD().containsKey((int) me.getY())) {
                    copy.getEdgeMapD().get((int) me.getY()).put((int) me.getX(), edge);
                } else {
                    HashMap<Integer, EdgeData> innerMap = new HashMap<>();
                    copy.getEdgeMapD().put((int) me.getY(), innerMap);
                    innerMap.put((int) me.getX(), edge);
                }
            }
        }
        return copy;
    }

    @Override
    public boolean isConnected() {
        WeightedGraph g = (WeightedGraph) this.getGraph();
        //make an adjacency list for graph g
        //adjacency list = getEdgeMapS()
        // Step 1: Mark all the vertices as not visited (For first DFS)
        HashMap<Integer, Boolean> visited = new HashMap<>();
        int k = 0;
        for (Integer key : g.getNodesMap().keySet()) {
            visited.put(key, false);
            k = key;
        }
        // Step 2: Do BFS traversal starting from first vertex.
        BFS(k, visited);
        // If BFS traversal doesn't visit all vertices, then return false.
        for (Integer i : visited.keySet()) {
            if (visited.get(i) == false) {
                return false;
            }
        }
        // Step 3: Create a reversed graph
        algo gT = new algo();
        WeightedGraph graphT = (WeightedGraph) this.copy();
        gT.init(graphT);
        gT.getTranspose();
        // Step 4: Mark all the vertices as not visited (For second DFS)
        for (Integer i : visited.keySet()) {
            //visited.remove(i);
            visited.replace(i, false);
        }
        // Step 5: Do BFS for reversed graph starting from first vertex.
        // Starting Vertex must be same starting point of first BFS
        gT.BFS(k, visited);
        // If all vertices are not visited in second BFS, then return false
        for (Integer i : visited.keySet()) {
            if (visited.get(i) == false) {
                return false;
            }
        }
        return true;
    }

    private void getTranspose() {
        // Function that returns transpose of this graph
        WeightedGraph gT = (WeightedGraph) this.getGraph();
        gT.getEdgeMapS().clear();
        for(Integer me: gT.getEdgeMapD().keySet()) {  //me = src of transposed graph
            for (Integer me1: gT.getEdgeMapD().get(me).keySet()) { //me1 = dest of transposed graph
                //Point2D p = new Point(me,me1);
                Edge edge = new Edge((Node)gT.getNode(me), (Node)gT.getNode(me1),gT.getEdge(me1,me).getWeight(),"",0);
                if(gT.getEdgeMapS().get(me) == null) {
                    HashMap<Integer, EdgeData> innerMap = new HashMap<>();
                    gT.getEdgeMapS().put(me,innerMap);
                    innerMap.put(me1,edge);
                } else {
                    gT.getEdgeMapS().get(me).put(me1,edge);
                }
            }
        }
    }

    private void BFS(int nodeKey ,HashMap<Integer, Boolean> visited) {
        WeightedGraph g = (WeightedGraph) getGraph();
        LinkedList<Integer> queue = new LinkedList<>();
        visited.remove(nodeKey);
        visited.put(nodeKey,true);
        queue.add(nodeKey);
        while (queue.size() != 0) {
            nodeKey = queue.poll();
            if(g.getEdgeMapS().get(nodeKey)!= null) {
                for (Integer n : g.getEdgeMapS().get(nodeKey).keySet()) {
                    if (!visited.get(n)) {
                        visited.remove(n);
                        visited.put(n, true);
                        queue.add(n);
                    }
                }
            }
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(src == dest){
            return 0;
        }
        int V=g.nodeSize();
        Set<Node> settled = new HashSet<Node>();
        PriorityQueue<Node> pq = new PriorityQueue<Node>(V, new NodeComperator());
        Node srcNode = (Node) g.getNode(src);
        srcNode.setWeight(0); //  O(1)
        pq.offer(srcNode); //O(logV)
        Iterator<NodeData> nIterator = g.nodeIter(); //O(1)
        while (nIterator.hasNext()) { //O(V)
            Node n = (Node) nIterator.next();
            if (n.getKey() != srcNode.getKey()) {
                n.setWeight(Double.MAX_VALUE);
                //n.setPrev(null);
                settled.add(n); //O(logV)
            }
        }
        //O(V+E) * O(logV) = O(ElogV)
        while (!settled.isEmpty()) {
            if(pq.peek() != null && pq.peek() == g.getNode(dest)){
                return pq.peek().getWeight();
            }
            if(pq.peek() != null && pq.peek().getWeight() == Double.MAX_VALUE){
                return -1;
            }
            Node node = pq.poll();
            //O(logV)
            Iterator<EdgeData> eIterator = g.edgeIter(node.getKey());
            while (eIterator.hasNext()) {
                EdgeData neighborEdge = eIterator.next();
                int neighborKey = neighborEdge.getDest();
                Node neighbor = (Node) g.getNode(neighborKey);
                if (neighbor.getWeight() > (node.getWeight() + neighborEdge.getWeight())) {
                    neighbor.setWeight(node.getWeight() + neighborEdge.getWeight());
                    //neighbor.setPrev(curr);
                    pq.offer(neighbor); //O(logv)
                }
            }
        }

        return g.getNode(dest).getWeight();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> list = new ArrayList<NodeData>() ;
        if(src == dest){
            list.add(g.getNode(src));
            return list ;
        }
        int V=g.nodeSize();
        Set<Node> settled = new HashSet<Node>();
        PriorityQueue<Node> pq = new PriorityQueue<Node>(V, new NodeComperator());
        Node srcNode = (Node) g.getNode(src);
        //  O(1)
        pq.offer(srcNode); //O(logV)
        Iterator<NodeData> nIterator = g.nodeIter(); //O(1)
        while (nIterator.hasNext()) { //O(V)
            Node n = (Node) nIterator.next();
            if (n.getKey() != srcNode.getKey()) {
                n.setWeight(Double.MAX_VALUE);
                //n.setPrev(null);
                settled.add(n); //O(logV)
            }
        }

        while (!settled.isEmpty()) {

            if(pq.peek() != null && pq.peek() == g.getNode(dest)){
                list.add(g.getNode(dest));
                return list;
            }
            if(pq.peek() != null && pq.peek().getWeight() == Double.MAX_VALUE){
                return null;
            }
            Node node = pq.poll();
            list.add(node);
            settled.remove(node);
            Iterator<EdgeData> eIterator = g.edgeIter(node.getKey());
            while (eIterator.hasNext()) {
                EdgeData nNode = eIterator.next();
                int nKey = nNode.getDest();
                Node neighbor = (Node) g.getNode(nKey);
                if (neighbor.getWeight() > (node.getWeight() + nNode.getWeight())) {
                    neighbor.setWeight(node.getWeight() + nNode.getWeight());
                    //neighbor.setPrev(curr);
                    pq.add(neighbor);
                }
            }
        }

        return null;
    }

    @Override
    public NodeData center() {
        if (!this.isConnected()) {
            return null;
        }
        double max;
        double min = Double.MAX_VALUE;
        NodeData centerOfGraph = null;
        for (Integer me : g.getNodesMap().keySet()) {
            Node node = (Node) g.getNode(me);
            max = Double.MIN_VALUE;
            for (Integer me1 : g.getNodesMap().keySet()) {
                Node node1 = (Node) g.getNode(me1);
                if (node1.getKey() != node.getKey()) {
                    double temp = shortestPathDist(node.getKey(),node1.getKey());
                    if (temp > max) {
                        max = temp;
                    }
                }
            }
            if (max < min) {
                min = max;
                centerOfGraph = node;
            }
        }
        return centerOfGraph;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        double weight;
        double min = Integer.MAX_VALUE;
        Node minNode = null;
        LinkedList<NodeData> path = new LinkedList<>();
        path.add(cities.get(0));
        while (cities.size() != 0) {
            min =Integer.MAX_VALUE;
            Node node = (Node) path.get(path.size()-1);// new Node((Node)path.get(path.size()-1));
            boolean b = cities.contains(node);
            //size or (size-1)
            if(cities.contains(node)) {
                cities.remove(node);}
            for (int i = 0; i < cities.size(); i++) {
                Point2D p = new Point(node.getKey(), cities.get(i).getKey());
                if(g.getEdgeMap().containsKey(p)) {
                    weight = g.getEdgeMap().get(p).getWeight();
                } else {
                    weight = shortestPathDist(node.getKey(), cities.get(i).getKey());
                }
                if (weight < min) {
                    min = weight;
                    minNode = (Node) cities.get(i);
                }
            }
            Point2D p = new Point(path.get(path.size()-1).getKey(), minNode.getKey());
            if (g.getEdgeMap().containsKey(p)) {
                path.add(minNode);
            } else {
                int x=path.get(path.size()-1).getKey();
                int y=minNode.getKey();
                List<NodeData> toMerge = shortestPath(x, y);
                while (toMerge.size() != 0) {
                    path.add(toMerge.get(0));
                    toMerge.remove(0);
                    if(cities.contains(this.getGraph().getNode(toMerge.get(0).getKey()))) {
                        cities.remove(this.getGraph().getNode(toMerge.get(0).getKey()));
                    }
                }
            }
            cities.remove(minNode);
        }
        return path;
    }

    @Override
    public boolean save(String file) {
        try {
            FileWriter json_file = new FileWriter(file);
            BufferedWriter b = new BufferedWriter(json_file);
            b.write(this.getGraph().toString());
            b.close();
            json_file.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        WeightedGraph graph = (WeightedGraph) this.getGraph();
        try {
            Object ob = new JSONParser().parse(new FileReader(file));
            if (ob == null)
                throw new Exception("Error");
            JSONObject js = (JSONObject) ob;
            JSONArray edgesArr = (JSONArray) js.get("Edges");
            JSONArray nodesArr = (JSONArray) js.get("Nodes");
            Map mapNode;
            for (int i = 0; i < nodesArr.size(); i++) {
                mapNode = (Map) nodesArr.get(i);
                int id = Integer.parseInt(Objects.toString(mapNode.get("id")));
                String[] pos = ((String) mapNode.get("pos")).split(",");
                double x = Double.parseDouble(pos[0]);
                double y = Double.parseDouble(pos[1]);
                double z = Double.parseDouble(pos[2]);
                geo_location p1 = new geo_location(x, y, z);
                Node node = new Node(id, p1, 0, "", 0);
                graph.addNode(node);
            }
            Map mapEdge;
            for (int i = 0; i < edgesArr.size(); i++) {
                mapEdge = (Map) edgesArr.get(i);
                int src = Integer.parseInt(Objects.toString(mapEdge.get("src")));
                int dest = Integer.parseInt(Objects.toString(mapEdge.get("dest")));
                double w = Double.parseDouble(Objects.toString(mapEdge.get("w")));
                Node one = (Node) graph.getNode(src);
                Node two = (Node) graph.getNode(dest);
                graph.connect(src,dest,w);
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}