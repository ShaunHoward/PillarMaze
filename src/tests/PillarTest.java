package tests;

import maze.Maze;
import maze.Pillar;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the pillar class.
 *
 * @author Shaun Howard
 */
public class PillarTest {

    Pillar pillar1, pillar2, pillar3, pillar4;

    @Before
    public void setUp(){
        pillar1 = new Pillar(0,0);
        pillar2 = new Pillar(1,0);
        pillar3 = new Pillar(0,1);
        pillar4 = new Pillar(1,1);
    }

    //test addNeighbor
    @Test
    public void testAddNeighbor(){
        Map<Pillar, Boolean> neighbors;

        pillar3.addNeighbor(pillar2, true);
        pillar3.addNeighbor(pillar1, true);
        neighbors = pillar3.getNeighbors();
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.containsKey(pillar2));
        assertTrue(neighbors.containsKey(pillar1));
        assertEquals(true, neighbors.get(pillar1));
        assertEquals(true, neighbors.get(pillar2));

        pillar2.addNeighbor(pillar3, true);
        pillar2.addNeighbor(pillar1, false);
        pillar2.addNeighbor(pillar4, true);
        neighbors = pillar2.getNeighbors();
        assertEquals(3, neighbors.size());
        assertTrue(neighbors.containsKey(pillar3));
        assertTrue(neighbors.containsKey(pillar1));
        assertEquals(false, neighbors.get(pillar1));
        assertEquals(true, neighbors.get(pillar3));
        assertEquals(true, neighbors.get(pillar4));

        pillar1.addNeighbor(pillar2, true);
        pillar1.addNeighbor(pillar3, false);
        neighbors = pillar1.getNeighbors();
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.containsKey(pillar2));
        assertTrue(neighbors.containsKey(pillar3));
        assertEquals(true, neighbors.get(pillar2));
        assertEquals(false, neighbors.get(pillar3));
    }

    //test getNeighbors
    @Test
    public void testGetNeighbors(){
        Map<Pillar, Boolean> neighbors;

        pillar1.addNeighbor(pillar2, true);
        pillar1.addNeighbor(pillar3, true);
        neighbors = pillar1.getNeighbors();
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.containsKey(pillar2));
        assertTrue(neighbors.containsKey(pillar3));

        pillar2.addNeighbor(pillar3, true);
        pillar2.addNeighbor(pillar1, true);
        neighbors = pillar2.getNeighbors();
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.containsKey(pillar3));
        assertTrue(neighbors.containsKey(pillar1));

        pillar3.addNeighbor(pillar2, true);
        pillar3.addNeighbor(pillar1, true);
        neighbors = pillar3.getNeighbors();
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.containsKey(pillar2));
        assertTrue(neighbors.containsKey(pillar1));
    }

    //test getNeighborList
    @Test
    public void testGetNeighborList(){

        Map<Pillar, Boolean> neighbors;
        List<Pillar> neighborList;

        pillar1.addNeighbor(pillar2, true);
        pillar1.addNeighbor(pillar3, true);
        neighbors = pillar1.getNeighbors();
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.containsKey(pillar2));
        assertTrue(neighbors.containsKey(pillar3));

        //connected neighbors
        neighborList = pillar1.getNeighborList(true);
        assertEquals(2, neighborList.size());

        //unconnected neighbors
        neighborList = pillar1.getNeighborList(false);
        assertEquals(0, neighborList.size());

        pillar3.addNeighbor(pillar2, true);
        pillar3.addNeighbor(pillar1, false);
        neighbors = pillar3.getNeighbors();
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.containsKey(pillar2));
        assertTrue(neighbors.containsKey(pillar1));

        //connected neighbors
        neighborList = pillar3.getNeighborList(true);
        assertEquals(1, neighborList.size());

        //unconnected neighbors
        neighborList = pillar3.getNeighborList(false);
        assertEquals(1, neighborList.size());

        pillar2.addNeighbor(pillar3, false);
        pillar2.addNeighbor(pillar1, false);
        neighbors = pillar2.getNeighbors();
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.containsKey(pillar3));
        assertTrue(neighbors.containsKey(pillar1));

        //connected neighbors
        neighborList = pillar2.getNeighborList(true);
        assertEquals(0, neighborList.size());

        //unconnected neighbors
        neighborList = pillar2.getNeighborList(false);
        assertEquals(2, neighborList.size());

        pillar2.addNeighbor(pillar3, true);
        pillar2.addNeighbor(pillar1, false);
        pillar2.addNeighbor(pillar4, true);
        neighbors = pillar2.getNeighbors();
        assertEquals(3, neighbors.size());
        assertTrue(neighbors.containsKey(pillar3));
        assertTrue(neighbors.containsKey(pillar1));

        //connected neighbors
        neighborList = pillar2.getNeighborList(true);
        assertEquals(2, neighborList.size());

        //unconnected neighbors
        neighborList = pillar2.getNeighborList(false);
        assertEquals(1, neighborList.size());
    }

    //test compareTo
    @Test
    public void testCompareTo(){

        //equal
        pillar1.setCost(10.0f);
        pillar2.setCost(10.0f);
        assertEquals(0, pillar1.compareTo(pillar2));

        //left less than right
        pillar1.setCost(8.0f);
        pillar2.setCost(10.0f);
        assertEquals(-1, pillar1.compareTo(pillar2));

        //left greater than right
        pillar1.setCost(12.0f);
        pillar2.setCost(10.0f);
        assertEquals(1, pillar1.compareTo(pillar2));
    }

    //test equals
    @Test
    public void testEquals(){
        //equals
        assertTrue(pillar1.equals(pillar1));
        assertTrue(pillar2.equals(pillar2));
        assertTrue(pillar3.equals(pillar3));

        pillar1.setCost(10.0f);
        Pillar testPillar1 = new Pillar(0,0);
        testPillar1.setCost(10.0f);

        pillar2.setCost(20.0f);
        Pillar testPillar2 = new Pillar(1,0);
        testPillar2.setCost(20.0f);

        pillar3.setCost(30.0f);
        Pillar testPillar3 = new Pillar(0,1);
        testPillar3.setCost(30.0f);

        //equals
        assertTrue(pillar1.equals(testPillar1));
        assertTrue(pillar2.equals(testPillar2));
        assertTrue(pillar3.equals(testPillar3));
    }

    //test not equals
    @Test
    public void testNotEquals(){
        //unequals
        assertFalse(pillar1.equals(pillar2));
        assertFalse(pillar2.equals(pillar3));
        assertFalse(pillar1.equals(pillar3));


        pillar1.setCost(10.0f);
        Pillar testPillar1 = new Pillar(1,0);
        testPillar1.setCost(10.0f);

        pillar2.setCost(20.0f);
        Pillar testPillar2 = new Pillar(0,1);
        testPillar2.setCost(20.0f);

        pillar3.setCost(30.0f);
        Pillar testPillar3 = new Pillar(5,5);
        testPillar3.setCost(40.0f);

        //equals
        assertFalse(pillar1.equals(testPillar1));
        assertFalse(pillar2.equals(testPillar2));
        assertFalse(pillar3.equals(testPillar3));
    }

    //test null equals
    @Test
    public void testNullEquals(){
        //unequals
        assertFalse(pillar1.equals(null));
        assertFalse(pillar2.equals(null));
        assertFalse(pillar3.equals(null));
    }

}
