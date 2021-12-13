package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class geo_locationTest {

    static geo_location g1 = new geo_location(1,1,1);
    static geo_location g2 = new geo_location(0,0,0);

    @Test
    void x() {
        assertEquals(g1.x(),1);
        assertEquals(g2.x(),0);
    }

    @Test
    void y() {
        assertEquals(g1.y(),1);
        assertEquals(g2.y(),0);
    }

    @Test
    void z() {
        assertEquals(g1.z(),1);
        assertEquals(g2.z(),0);
    }

    @Test
    void setX() {
        g1.setX(0);
        g2.setX(1);
        assertEquals(g1.x(),0);
        assertEquals(g2.x(),1);
    }

    @Test
    void setY() {
        g1.setY(0);
        g2.setY(1);
        assertEquals(g1.y(),0);
        assertEquals(g2.y(),1);
    }

    @Test
    void setZ() {
        g1.setZ(0);
        g2.setZ(1);
        assertEquals(g1.z(),0);
        assertEquals(g2.z(),1);
    }

    @Test
    void distance() {
        assertEquals(g1.distance(g2), 1.732,0.1);
        assertEquals(g1.distance(new geo_location(2,3,4)), 5.385,0.1);

    }
}