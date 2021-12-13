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

    private WeightedGraph ourGraph;

    public algo() {
        this.ourGraph = new WeightedGraph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.ourGraph = (WeightedGraph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.ourGraph;
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
                copy.addNode(new Node(g.getNodesMap().get(src)));
            }
        }
        //copy edges
        if (g.getEdgeMap().size() > 0) {
            for (Point2D me : g.getEdgeMap().keySet()) {
                Edge edgeToCopy = (Edge) g.getEdgeMap().get(me);
                Edge edge = new Edge((Node) copy.getNode((int) me.getX()),(Node)copy.getNode((int) me.getY()), edgeToCopy.getWeight(), edgeToCopy.getInfo(),edgeToCopy.getTag());
                //add to edges
                Point2D p = new Point((int) me.getX(),(int)me.getY());
                copy.getEdgeMap().put(p, edge);
                //add to adgesS
                if (copy.getEdgeMapS().containsKey((int) me.getX())) {
                    copy.getEdgeMapS().get((int) me.getX()).put((int) me.getY(), edge);
                } else {
                    HashMap<Integer, EdgeData> innerMap = new HashMap<>();
                    copy.getEdgeMapS().put((int) me.getX(), innerMap);
                    innerMap.put((int) me.getY(), edge);
                }
                //add to adgesD
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
        //adjacency list for graph g = getEdgeMapS()
        // Step 1: Mark all the vertices as not visited (For first BFS)
        HashMap<Integer, Boolean> visited = new HashMap<>();
        int keyStart = 0;
        for (Integer key : g.getNodesMap().keySet()) {
            visited.put(key, false);
            keyStart = key;
        }
        // Step 2: Do BFS traversal starting from first vertex.
        BFS(keyStart, visited);
        // If BFS traversal doesn't visit all vertices, then return false.
        for (Integer i : visited.keySet()) {
            if (!visited.get(i)) {
                return false;
            }
        }
        // Step 3: Create a reversed graph
        algo gT = new algo();
        WeightedGraph graphT = (WeightedGraph) this.copy();
        gT.init(graphT);
        gT.getTranspose();
        // Step 4: Mark all the vertices as not visited (For second BFS)
        for (Integer i : visited.keySet()) {
            visited.replace(i, false);
        }
        // Step 5: Do BFS for reversed graph starting from first vertex. Starting Vertex must be same starting point of first BFS
        gT.BFS(keyStart, visited);
        // If all vertices are not visited in second BFS, then return false
        for (Integer i : visited.keySet()) {
            if (!visited.get(i)) {
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

    private void BFS(int nodeStart ,HashMap<Integer, Boolean> visited) {
        WeightedGraph g = (WeightedGraph) getGraph();
        LinkedList<Integer> queue = new LinkedList<>();
        visited.replace(nodeStart,true);
        queue.add(nodeStart);
        while (queue.size() != 0) {
            nodeStart = queue.poll();
            if(g.getEdgeMapS().get(nodeStart)!= null) {
                for (Integer n : g.getEdgeMapS().get(nodeStart).keySet()) {
                    if (!visited.get(n)) {
                        visited.replace(n, true);
                        queue.add(n);
                    }
                }
            }
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (ourGraph.getNode(src) == null || ourGraph.getNode(dest) == null) {
            return -1;
        }
        if(src == dest){
            ourGraph.getNode(dest).setTag(src);
            return 0;
        }
        int V = ourGraph.nodeSize();
        Set<Node> settled = new HashSet<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(V, new NodeComperator());
        Node srcNode = (Node) ourGraph.getNode(src);
        srcNode.setWeight(0);
        pq.add(srcNode);
        Iterator<NodeData> nIterator = ourGraph.nodeIter();
        while (nIterator.hasNext()) {
            Node n = (Node) nIterator.next();
            if (n.getKey() != srcNode.getKey()) {
                n.setWeight(Double.MAX_VALUE);
                settled.add(n);
            }
        }
        while (!settled.isEmpty()) {
            if(pq.peek() != null && pq.peek() == ourGraph.getNode(dest)){
                return pq.peek().getWeight();
            }
            if(pq.peek() != null && pq.peek().getWeight() == Double.MAX_VALUE){
                return -1;
            }
            Node node = pq.poll();
            if(node == null ||ourGraph.getEdgeMapS().get(node.getKey())== null) {return -1;}
            Iterator<EdgeData> eIterator = ourGraph.edgeIter(node.getKey());
            while (eIterator.hasNext()) {
                EdgeData neighborEdge = eIterator.next();
                int nKey = neighborEdge.getDest();
                Node sunOfNode = (Node) ourGraph.getNode(nKey);
                if (sunOfNode.getWeight() > (node.getWeight() + neighborEdge.getWeight())) {
                    sunOfNode.setTag(node.getKey());
                    sunOfNode.setWeight(node.getWeight() + neighborEdge.getWeight());
                    pq.add(sunOfNode);
                }
            }
        }
        return ourGraph.getNode(dest).getWeight();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> list = new ArrayList<>() ;
        if(src == dest){
            list.add(ourGraph.getNode(dest));
            return list;
        }
        if(shortestPathDist(src,dest) == -1){return null;};
        Node n= (Node) ourGraph.getNode(dest);
        Node nSrc= (Node) ourGraph.getNode(src);
        while(n.getTag()!= nSrc.getKey()){
            list.add(0,n);
            n= (Node) ourGraph.getNode(n.getTag());
        }
        list.add(0,n);
        list.add(0,nSrc);
        return list;
    }

    @Override
    public NodeData center() {
        if (!this.isConnected()) {
            return null;
        }
        double max;
        double min = Double.MAX_VALUE;
        NodeData centerOfGraph = null;
        for (Integer me : ourGraph.getNodesMap().keySet()) {
            Node node = (Node) ourGraph.getNode(me);
            max = Double.MIN_VALUE;
            for (Integer me1 : ourGraph.getNodesMap().keySet()) {
                Node node1 = (Node) ourGraph.getNode(me1);
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
        if(cities.size() == 0) {return null;}
        double weight;
        double min, dis;
        Node minNode = null;
        LinkedList<NodeData> path = new LinkedList<>();
        path.add(cities.get(0));
        while (cities.size() > 0) {
            min =Integer.MAX_VALUE;
            weight = Integer.MAX_VALUE;
            Node node = (Node) path.get(path.size()-1);
            if(cities.contains(node)) {
                cities.remove(node);
            }
            for (int i = 0; i < cities.size(); i++) {
                Point2D p = new Point(node.getKey(), cities.get(i).getKey());
                if(ourGraph.getEdgeMap().containsKey(p)) {
                    weight = ourGraph.getEdgeMap().get(p).getWeight();
                } else {
                    dis = shortestPathDist(node.getKey(), cities.get(i).getKey());
                    if(dis != -1) {
                        weight = dis;
                    }
                }
                if (weight < min) {
                    min = weight;
                    minNode = (Node) cities.get(i);
                }
            }
            if (weight == Integer.MAX_VALUE) {return null;} //?
            Point2D p = new Point(path.get(path.size()-1).getKey(), minNode.getKey());
            if (ourGraph.getEdgeMap().containsKey(p)) {
                path.add(minNode);
            } else {
                int x=path.get(path.size()-1).getKey();
                int y=minNode.getKey();
                List<NodeData> toMerge = shortestPath(x, y);
                while (toMerge.size() != 0) {
                    if (cities.contains(this.getGraph().getNode(toMerge.get(0).getKey()))) {
                        cities.remove(this.getGraph().getNode(toMerge.get(0).getKey()));
                    }
                    if(path.get(path.size()-1) != toMerge.get(0)) {
                        path.add(toMerge.get(0));
                    }
                    toMerge.remove(0);
                }
            }
            if (cities.contains(minNode)){cities.remove(minNode);}
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
        this.ourGraph = new WeightedGraph();
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