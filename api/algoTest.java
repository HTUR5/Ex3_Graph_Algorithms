package api;

import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class algoTest {

    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
        algo gr1 = new algo();
        Node n0 = new Node(0, geo_locationTest.g1, 3,"",0);
        Node n1 = new Node(1, geo_locationTest.g2, 3,"",0);
        Node n2 = new Node(2,new geo_location(5,1,8), 0, "", 0);
        Node n3 = new Node(3,new geo_location(5,6,6), 0, "", 0);
        gr1.getGraph().addNode(n0);
        gr1.getGraph().addNode(n1);
        gr1.getGraph().addNode(n2);
        gr1.getGraph().addNode(n3);
        gr1.getGraph().connect(n0.getKey(),n1.getKey(),4);
        gr1.getGraph().connect(n1.getKey(),n2.getKey(),3);
        gr1.getGraph().connect(n2.getKey(),n3.getKey(),6);
        gr1.getGraph().connect(n3.getKey(),n0.getKey(),6);
        WeightedGraph gr2 = (WeightedGraph) gr1.copy();
        gr1.getGraph().equals(gr2);

    }

    @Test
    void isConnected() {
        algo gr2 = new algo();
        gr2.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G1.json");
        long startTime = System.currentTimeMillis();
        assertEquals(gr2.isConnected(),true);
        long endTime = System.currentTimeMillis();
//        System.out.println("That took " + (endTime - startTime) + " milliseconds");
//        algo gr1 = new algo();
//        Node n0 = new Node(0, geo_locationTest.g1, 3,"",0);
//        Node n1 = new Node(1, geo_locationTest.g2, 3,"",0);
//        Node n2 = new Node(2,new geo_location(5,1,8), 0, "", 0);
//        Node n3 = new Node(3,new geo_location(5,6,6), 0, "", 0);
//        gr1.getGraph().addNode(n0);
//        gr1.getGraph().addNode(n1);
//        gr1.getGraph().addNode(n2);
//        gr1.getGraph().addNode(n3);
//        gr1.getGraph().connect(n0.getKey(),n1.getKey(),4);
//        gr1.getGraph().connect(n1.getKey(),n2.getKey(),3);
//        gr1.getGraph().connect(n2.getKey(),n3.getKey(),6);
//        gr1.getGraph().connect(n3.getKey(),n0.getKey(),6);
//        assertEquals(gr1.isConnected(),true);
//        gr1.getGraph().removeEdge(2,3);
//        assertEquals(gr1.isConnected(),false);
    }

    @Test
    void shortestPathDist() {
        algo gr1 = new algo();
        gr1.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G3.json");
        long startTime = System.currentTimeMillis();
        gr1.shortestPathDist(0,4);
        long endTime = System.currentTimeMillis();
//        System.out.println("That took " + (endTime - startTime) + " milliseconds");
//        double dis= gr1.shortestPathDist(0,4);
//        assertEquals(dis,10.92,0.1);
    }

    @Test
    void shortestPath() {
        List<NodeData> list02 = new ArrayList<NodeData>();
        List<NodeData> list2 = new ArrayList<NodeData>();
        algo gr2 = new algo();
        gr2.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G3.json");
        Node nn1 = (Node) gr2.getGraph().getNode(4);
        Node nn2 = (Node) gr2.getGraph().getNode(13);
        Node nn3 = (Node) gr2.getGraph().getNode(14);
        Node nn4 = (Node) gr2.getGraph().getNode(28);
        list02.add(nn1);
        list02.add(nn2);
        list02.add(nn3);
        list02.add(nn4);
        list02 = gr2.shortestPath(4,28);
        //assertEquals(list02.get(2).getKey() ,gr2.shortestPath(4,10).get(2).getKey());
        list2 = gr2.shortestPath(4,28);
        list02.equals(gr2.shortestPath(4,28));
        List<NodeData> list = new ArrayList<NodeData>();
        List<NodeData> list1 = new ArrayList<NodeData>();
        algo gr1 = new algo();
        gr1.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G1.json");
        Node n1 = new Node(1, geo_locationTest.g1, 3,"",0);
        Node n2 = new Node(2, geo_locationTest.g2, 3,"",0);
        Node n3 = new Node(6,new geo_location(5,1,8), 0, "", 0);
        Node n4 = new Node(7,new geo_location(5,6,6), 0, "", 0);
        Node n5 = new Node(8,new geo_location(5,6,6), 0, "", 0);
        Node n6 = new Node(9,new geo_location(5,6,6), 0, "", 0);
        Node n7 = new Node(10,new geo_location(5,6,6), 0, "", 0);
        list.add(n1);
        list.add(n2);
        list.add(n3);
        list.add(n4);
        list.add(n5);
        list.add(n6);
        list.add(n7);
        list1 = gr1.shortestPath(1,10);
        int x=list.get(2).getKey();
        int y=gr1.shortestPath(1,10).get(2).getKey();
        assertEquals(list.get(2).getKey() ,gr1.shortestPath(1,10).get(2).getKey());
        list.equals(gr1.shortestPath(1,10));
        list1 = gr1.shortestPath(3,9);
//        algo gr3 = new algo();
//        gr3.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\100000.json");
//        long startTime = System.currentTimeMillis();
//        gr3.shortestPath(4,15);
//        long endTime = System.currentTimeMillis();
//        System.out.println("That took " + (endTime - startTime) + " milliseconds");
    }

    @Test
    void center() {
        algo gr1 = new algo();
        gr1.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G3.json");
        long startTime = System.currentTimeMillis();
        gr1.center();
        long endTime = System.currentTimeMillis();
        //System.out.println("That took " + (endTime - startTime) + " milliseconds");
        assertEquals(gr1.center().getKey(),40);

    } //G1,G2,G3 = 8,0,40

    @Test
    void tsp() {
        algo gr1 = new algo();
        gr1.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G3.json");
        LinkedList<NodeData> cities = new LinkedList<>();
        LinkedList<NodeData> cities1 = new LinkedList<>();
        cities.add(gr1.getGraph().getNode(1));
        cities.add(gr1.getGraph().getNode(2));
        cities.add(gr1.getGraph().getNode(6));
        cities.add(gr1.getGraph().getNode(7));
        cities.add(gr1.getGraph().getNode(8));
        cities.add(gr1.getGraph().getNode(9));
        cities.add(gr1.getGraph().getNode(10));
        cities1.add(gr1.getGraph().getNode(1));
        cities1.add(gr1.getGraph().getNode(6));
        cities1.add(gr1.getGraph().getNode(2));
        cities1.add(gr1.getGraph().getNode(0));
        cities1.add(gr1.getGraph().getNode(9));
        cities1.add(gr1.getGraph().getNode(14));
        cities1.add(gr1.getGraph().getNode(3));
        //int x= cities1.size();
        cities.get(2).equals(gr1.tsp(cities1));
        //cities.equals(gr1.tsp(cities1));
        //long startTime = System.currentTimeMillis();
        //gr1.tsp(cities);
        //long endTime = System.currentTimeMillis();
        //System.out.println("That took " + (endTime - startTime) + " milliseconds");
    }

    @Test
    void save() {
    }

    @Test
    void load() {
       algo G= new algo ();
       G.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G3.json");
    }
}