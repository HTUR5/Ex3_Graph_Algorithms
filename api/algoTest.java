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
        assertEquals(gr1.isConnected(),true);
        gr1.getGraph().removeEdge(2,3);
        assertEquals(gr1.isConnected(),false);
        algo gr2 = new algo();
        algo gr3 = new algo();
        algo gr4 = new algo();
        algo gr5 = new algo();
        //algo gr6 = new algo();
        gr2.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G1.json");
        gr3.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G2.json");
        gr4.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G3.json");
        gr5.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\10000Nodes.json");
        //gr6.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\100000.json");
        assertEquals(gr2.isConnected(),true);
        assertEquals(gr3.isConnected(),true);
        assertEquals(gr4.isConnected(),true);
        assertEquals(gr5.isConnected(),true);
        //assertEquals(gr6.isConnected(),true);
    }

    @Test
    void shortestPathDist() {
        algo gr1 = new algo();
//        Node n1 = new Node(1, geo_locationTest.g1, 3,"",0);
//        Node n2 = new Node(2, geo_locationTest.g2, 3,"",0);
//        Node n3 = new Node(3,new geo_location(5,1,8), 0, "", 0);
//        Node n4 = new Node(4,new geo_location(5,6,6), 0, "", 0);
//        Node n5 = new Node(5,new geo_location(5,6,6), 0, "", 0);
//        Node n6 = new Node(6,new geo_location(5,6,6), 0, "", 0);
//        gr1.getGraph().addNode(n1);
//        gr1.getGraph().addNode(n2);
//        gr1.getGraph().addNode(n3);
//        gr1.getGraph().addNode(n4);
//        gr1.getGraph().addNode(n5);
//        gr1.getGraph().addNode(n6);
//        gr1.getGraph().connect(n1.getKey(),n2.getKey(),7);
//        gr1.getGraph().connect(n1.getKey(),n3.getKey(),9);
//        gr1.getGraph().connect(n1.getKey(),n6.getKey(),14);
//        //gr1.getGraph().connect(n2.getKey(),n1.getKey(),1);
//        gr1.getGraph().connect(n2.getKey(),n3.getKey(),10);
//        gr1.getGraph().connect(n2.getKey(),n4.getKey(),15);
//        gr1.getGraph().connect(n3.getKey(),n6.getKey(),2);
//        gr1.getGraph().connect(n3.getKey(),n4.getKey(),11);
//        //gr1.getGraph().connect(n4.getKey(),n3.getKey(),11);
//        gr1.getGraph().connect(n4.getKey(),n5.getKey(),6);
//        //gr1.getGraph().connect(n5.getKey(),n6.getKey(),9);
//        //gr1.getGraph().connect(n5.getKey(),n4.getKey(),6);
//        gr1.getGraph().connect(n6.getKey(),n5.getKey(),9);
        gr1.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G1.json");
        double dis= gr1.shortestPathDist(1,10);
        assertEquals(dis,9.02,0.1);
    }

    @Test
    void shortestPath() {
        List<NodeData> list = new ArrayList<NodeData>();
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
        list.equals(gr1.shortestPath(1,10));

    }

    @Test
    void center() {
        algo gr1 = new algo();
        gr1.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G1.json");
        assertEquals(gr1.center().getKey(),8);
    } //G1,G2,G3 = 8,0,40

    @Test
    void tsp() {
        algo gr1 = new algo();
        gr1.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G1.json");
        LinkedList<NodeData> cities = new LinkedList<>();
        cities.add(gr1.getGraph().getNode(1));
        cities.add(gr1.getGraph().getNode(2));
        cities.add(gr1.getGraph().getNode(6));
        cities.add(gr1.getGraph().getNode(7));
        cities.add(gr1.getGraph().getNode(8));
        cities.add(gr1.getGraph().getNode(9));
        cities.add(gr1.getGraph().getNode(10));
        cities.equals(gr1.tsp(cities));
        //gr1.tsp(cities);
    }

    @Test
    void save() {
    }

    @Test
    void load() {
       algo G= new algo ();
       G.load("C:\\Users\\hoday\\IdeaProjects\\Ex2\\src\\api\\G1.json");
    }
}