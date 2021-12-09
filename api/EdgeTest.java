package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    static Edge e1 = new Edge(new Node(NodeTest.n1), new Node(NodeTest.n2), 5, "",0);

    @Test
    void getSrc() {
        assertEquals(e1.getSrc(),0);
    }

    @Test
    void getDest() {
        assertEquals(e1.getDest(),1);
    }

    @Test
    void getWeight() {
        assertEquals(e1.getWeight(),5);
    }

    @Test
    void getInfo() {
    }

    @Test
    void setInfo() {
    }

    @Test
    void getTag() {
    }

    @Test
    void setTag() {
    }
}