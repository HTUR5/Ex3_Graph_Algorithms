package api;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class WeightedGraphTest {

    WeightedGraph gr1 = new WeightedGraph();
    Node n1 = new Node(0, geo_locationTest.g1, 3,"",0);
    Node n2 = new Node(1, geo_locationTest.g2, 3,"",0);
    Node n3 = new Node(2,new geo_location(5,1,8), 0, "", 0);
    Node n4 = new Node(3,new geo_location(5,6,6), 0, "", 0);


    @Test
    void getNode() {
        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3); //what is weight of node
        gr1.addNode(n4);
        (gr1.getNode(2)).equals(new Node(2,new geo_location(5,1,8), 0, "", 0));
        (gr1.getNode(0)).equals(NodeTest.n1);
        assertNull(gr1.getNode(5));
    }

    @Test
    void getEdge() {
        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3);
        gr1.addNode(n4);
        Edge e1a = new Edge(n1, n2, 4, "",0);
        Edge e2a = new Edge(n4, n3, 7, "",0);
        gr1.connect(n1.getKey(),n2.getKey(),4);
        gr1.connect(n2.getKey(),n3.getKey(),3);
        gr1.connect(n4.getKey(),n3.getKey(),6);
        Edge e1b = (Edge) gr1.getEdge(n1.getKey(),n2.getKey());
        Edge e2b = (Edge) gr1.getEdge(n4.getKey(),n3.getKey());
        e1b.equals(e1a);
        e2b.equals(e2a);
        assertNull(gr1.getEdge(5,3));
    }

    @Test
    void addNode() {
        gr1.addNode(n4);
        assertTrue(gr1.getNodesMap().containsKey(3));
        assertTrue(gr1.getNodesMap().containsValue(n4));
    }

    @Test
    void connect() {
        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3);
        gr1.addNode(n4);
        gr1.connect(n1.getKey(),n2.getKey(),4);
        Point2D p = new Point(n1.getKey(),n2.getKey());
        assertTrue(gr1.getEdgeMap().containsKey(p));
        assertTrue(gr1.getEdgeMapS().get(n1.getKey()).containsKey(n2.getKey()));
        assertTrue(gr1.getEdgeMapD().get(n2.getKey()).containsKey(n1.getKey()));
    }

    @Test
    void nodeIter() {
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void edgeIterD() {
    }

    @Test
    void removeNode() {
        WeightedGraph gr2 = new WeightedGraph();
        gr2.addNode(n1);
        gr2.addNode(n2);
        gr2.addNode(n3);
        gr2.addNode(n4);
        gr2.connect(n1.getKey(),n2.getKey(),4);
        gr2.connect(n2.getKey(),n3.getKey(),3);
        gr2.connect(n4.getKey(),n3.getKey(),6);
        gr2.removeNode(2);
        assertTrue(!gr2.getNodesMap().containsKey(2));
        for (Point2D me : gr2.getEdgeMap().keySet()) {
            assertTrue(me.getX() != 2 && me.getY() != 2);
        }
        assertTrue(!gr2.getEdgeMapS().containsKey(2));
        assertTrue(!gr2.getEdgeMapD().containsKey(2));
    }

    @Test
    void removeEdge() {
        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3);
        gr1.addNode(n4);
        gr1.connect(n1.getKey(),n2.getKey(),4);
        gr1.connect(n2.getKey(),n3.getKey(),3);
        gr1.connect(n4.getKey(),n3.getKey(),6);
        gr1.removeEdge(0,1);
    }

    @Test
    void nodeSize() {
        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3);
        gr1.addNode(n4);
        assertEquals(gr1.nodeSize(),4);
    }

    @Test
    void edgeSize() {
        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3);
        gr1.addNode(n4);
        gr1.connect(n1.getKey(),n2.getKey(),4);
        gr1.connect(n2.getKey(),n3.getKey(),3);
        gr1.connect(n4.getKey(),n3.getKey(),6);
        //assertEquals(gr1.edgeSize(),3);
    }

    @Test
    void getMC() {
        WeightedGraph gr2 = new WeightedGraph();
        //assertEquals(gr2.getMC(),0);
    }
}