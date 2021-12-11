package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTest {

    static Node n1 = new Node(0, geo_locationTest.g1, 3,"",0);
    static Node n2 = new Node(1, geo_locationTest.g2, 3,"",0);
    static Node n3 = new Node(1, null, 3,"",0);

    @Test
    void getKey() {
        assertEquals(n1.getKey(),0);
        assertEquals(n2.getKey(),1);
    }

    @Test
    void getLocation() {
       assertNull(n3.getLocation());
       (n1.getLocation()).equals(geo_locationTest.g1);
    }

    @Test
    void setLocation() {
        geo_location g2 = new geo_location(0,0,0);
        n1.setLocation(g2);
        (n1.getLocation()).equals(g2);
    }

    @Test
    void getWeight() {
        assertEquals(n1.getWeight(),3);
    }

    @Test
    void setWeight() {
        n2.setWeight(5);
        assertEquals(n2.getWeight(),5);
    }

    @Test
    void getInfo() {
        String s = "";
        assertEquals(n1.getInfo(),s);
    }

    @Test
    void setInfo() {
        n1.setInfo("node");
        n1.getInfo().equals("node");
    }

    @Test
    void getTag() {
        assertEquals(n1.getTag(),0);
    }

    @Test
    void setTag() {
        n1.setTag(2);
        assertEquals(2,n1.getTag());
    }
}