package tests;

import maze.Maze;
import maze.Pillar;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * Tests the pillar maze class.
 *
 * @author Shaun Howard
 */
public class MazeTest {

    Maze testMaze;
    Maze.Test mazeTestObject;
    Class[] emptyParams = new Class[0];
    Maze maze;
    Maze.Position pos;
    Pillar pillar;
    Maze smallMaze;
    Maze largeMaze;
    Map<Maze.Position, Pillar> smallPillarMap;
    Map<Maze.Position, Pillar> largePillarMap;
    Map<Pillar, Boolean> pillarNeighbors;

    Method constructPillars;
    Method setNeighbors;

    @Before
    public void setUp(){
        testMaze = new Maze();
        mazeTestObject = testMaze.new Test();
        maze = new Maze(5,5);
        pos = null;
        pillar = null;
        smallMaze = new Maze(3, 3);
        largeMaze = new Maze(5, 5);
        smallPillarMap = smallMaze.getPillars();
        largePillarMap = largeMaze.getPillars();

        // Gather constructPillars() method
        try {
            constructPillars = maze.getClass().getDeclaredMethod("constructPillars",emptyParams);
            constructPillars.setAccessible(true);
        } catch (NoSuchMethodException e) {
            fail("Unable to find constructPillars() method declaration.");
        }

        // Gather setNeighbors() method
        try {
            setNeighbors = maze.getClass().getDeclaredMethod("setNeighbors", emptyParams);
            setNeighbors.setAccessible(true);
        } catch (NoSuchMethodException e) {
            fail("Unable to find setNeighbors() method declaration.");
        }

    }

    //Small case, Structured Basis, Data-flow, good data
    @Test
    public void testSmallSize(){
        assertEquals(9, smallMaze.size());
    }

    //Nominal case, Structured Basis, Data-flow, good data
    @Test
    public void testLargeSize(){
        assertEquals(25, largeMaze.size());
    }

    //Small case, Structured Basis, Data-flow, good data, compound boundaries
    @Test
    public void testSmallPillarConstruction(){
        for (int i = 0; i < smallMaze.length(); i++) {
            for (int j = 0; j < smallMaze.width(); j++) {
                assertEquals(new Pillar(j, i), smallPillarMap.get(Maze.position(j, i)));
            }
        }

    }

    //Nominal case, Structured Basis, Data-flow, good data
    @Test
    public void testLargePillarConstruction(){
        for (int i = 0; i < largeMaze.length(); i++) {
            for (int j = 0; j < largeMaze.width(); j++) {
                assertEquals(new Pillar(j, i), largePillarMap.get(Maze.position(j, i)));
            }
        }
    }

    //Small case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testSmallPillarNeighbors(){
        //test pillar at (0,0)
        pillar = smallPillarMap.get(Maze.position(0,0));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(2, pillarNeighbors.size());
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 0)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(0, 1)));

        //test pillar at (1,0)
        pillar = smallPillarMap.get(Maze.position(1,0));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(3, pillarNeighbors.size());
        assertTrue(pillarNeighbors.containsKey(new Pillar(2, 0)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(0, 0)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 1)));

        //test pillar at (2,0)
        pillar = smallPillarMap.get(Maze.position(2,0));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(2, pillarNeighbors.size());
        assertTrue(pillarNeighbors.containsKey(new Pillar(2, 1)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 0)));

        //test pillar at (0,1)
        pillar = smallPillarMap.get(Maze.position(0,1));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(3, pillarNeighbors.size());
        assertTrue(pillarNeighbors.containsKey(new Pillar(0, 0)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 1)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(0, 2)));

        //test pillar at (1,1)
        pillar = smallPillarMap.get(Maze.position(1,1));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(4, pillarNeighbors.size());
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 2)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 0)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(2, 1)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(0, 1)));

        //test pillar at (2,1)
        pillar = smallPillarMap.get(Maze.position(2,1));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(3, pillarNeighbors.size());
        assertTrue(pillarNeighbors.containsKey(new Pillar(2, 2)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 1)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(2, 0)));

        //test pillar at (0,2)
        pillar = smallPillarMap.get(Maze.position(0,2));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(2, pillarNeighbors.size());
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 2)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(0, 1)));

        //test pillar at (1,2)
        pillar = smallPillarMap.get(Maze.position(1,2));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(3, pillarNeighbors.size());
        assertTrue(pillarNeighbors.containsKey(new Pillar(2, 2)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(0, 2)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 1)));

        //test pillar at (2,2)
        pillar = smallPillarMap.get(Maze.position(2,2));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(2, pillarNeighbors.size());
        assertTrue(pillarNeighbors.containsKey(new Pillar(1, 2)));
        assertTrue(pillarNeighbors.containsKey(new Pillar(2, 1)));
    }

    //Small case, Structured Basis, Data-flow, bad data, compound boundaries, boundary
    @Test
    (expected=Exception.class)
    public void testExceptionLinkPillars() throws Exception{
        smallMaze.linkPillars(null, null);
    }

    //Small case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testLinkPillars(){
        try {
            smallMaze.linkPillars(Maze.position(0, 0), Maze.position(1,0));
        } catch (Exception e) {
            fail("Unexpected exception was thrown.");
        }

        pillar = smallPillarMap.get(Maze.position(0,0));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(true, pillarNeighbors.get(new Pillar(1, 0)));

        try {
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(1,1));
        } catch (Exception e) {
            fail("Unexpected exception was thrown.");
        }

        pillar = smallPillarMap.get(Maze.position(1,0));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(true, pillarNeighbors.get(new Pillar(1, 1)));

        try {
            smallMaze.linkPillars(Maze.position(1, 2), Maze.position(1,1));
        } catch (Exception e) {
            fail("Unexpected exception was thrown.");
        }

        pillar = smallPillarMap.get(Maze.position(1,2));
        pillarNeighbors = pillar.getNeighbors();
        assertEquals(true, pillarNeighbors.get(new Pillar(1, 1)));
    }

    //Small case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testNoLinkPillars(){
        try {
            smallMaze.linkPillars(Maze.position(0, 0), Maze.position(2,0));
        } catch (Exception e) {
            fail("Unexpected exception was thrown.");
        }

        pillar = smallPillarMap.get(Maze.position(0,0));
        pillarNeighbors = pillar.getNeighbors();
        assertFalse(pillarNeighbors.containsKey(new Pillar(2, 0)));

        try {
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(2,2));
        } catch (Exception e) {
            fail("Unexpected exception was thrown.");
        }

        pillar = smallPillarMap.get(Maze.position(1,0));
        pillarNeighbors = pillar.getNeighbors();
        assertFalse(pillarNeighbors.containsKey(new Pillar(2, 2)));

        try {
            smallMaze.linkPillars(Maze.position(1, 2), Maze.position(0,0));
        } catch (Exception e) {
            fail("Unexpected exception was thrown.");
        }

        pillar = smallPillarMap.get(Maze.position(1,2));
        pillarNeighbors = pillar.getNeighbors();
        assertFalse(pillarNeighbors.containsKey(new Pillar(0, 0)));
    }

    //Nominal case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testPositionEquals(){
        assertEquals(Maze.position(0,0), Maze.position(0,0));
        assertThat(Maze.position(1, 0), not(Maze.position(0, 0)));
        assertThat(Maze.position(0,0), not(Maze.position(0,1)));
    }

    //Nominal case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testSetBeginNotInMap(){
        smallMaze.setBegin(Maze.position(26, 23));
    }

    //Nominal case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testSetEndNotInMap(){
        smallMaze.setEnd(Maze.position(0, 23));
    }

    //Nominal case, Structured Basis, Data-flow, bad data, compound boundaries, boundary
    @Test
    public void testNullPositionEquals(){
        assertFalse(Maze.position(0,0).equals(null));
    }

    //Nominal case, Structured Basis, Data-flow, bad data, compound boundaries, boundary
    @Test
    public void testNotPositionInstanceEquals(){
        Pillar p = new Pillar(0,0);
        assertFalse(Maze.position(0,0).equals(p));
    }

    //Nominal case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testPositionHashCode(){
        pos = Maze.position(90, 23423);
        assertEquals(26213, pos.hashCode());
    }

    //Nominal case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testPosition(){
        pos = Maze.position(25, 28);
        assertEquals(Maze.Position.class, pos.getClass());
        assertEquals(pos, Maze.position(25, 28));
    }

    //Nominal case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testGetHorizontalLeftPillar(){
        Pillar cornerPillar = largePillarMap.get(Maze.position(0,0));
        Pillar edgePillar = largePillarMap.get(Maze.position(4,3));
        Pillar neighbor = largeMaze.getHorizontalPillar(cornerPillar, true);
        assertEquals(1, neighbor.getX());
        assertEquals(0, neighbor.getY());

        neighbor = largeMaze.getHorizontalPillar(edgePillar, true);
        assertEquals(null, neighbor);
    }

    //Nominal case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testGetHorizontalRightPillar(){
        Pillar cornerPillar = largePillarMap.get(Maze.position(0,0));
        Pillar surroundedPillar = largePillarMap.get(Maze.position(3,3));
        Pillar neighbor = largeMaze.getHorizontalPillar(cornerPillar, false);
        assertEquals(null, neighbor);

        neighbor = largeMaze.getHorizontalPillar(surroundedPillar, false);
        assertEquals(2, neighbor.getX());
        assertEquals(3, neighbor.getY());
    }

    //Nominal case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testGetVerticalPillarAbove(){
        Pillar pillar = largePillarMap.get(Maze.position(0,0));
        Pillar edgePillar = largePillarMap.get(Maze.position(4,4));
        Pillar neighbor = largeMaze.getVerticalPillar(pillar, true);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());

        neighbor = largeMaze.getVerticalPillar(edgePillar, true);
        assertEquals(null, neighbor);
    }

    //Nominal case, Structured Basis, Data-flow, good data, compound boundaries, boundary
    @Test
    public void testGetVerticalPillarBelow(){
        Pillar pillar = largePillarMap.get(Maze.position(0,0));
        Pillar edgePillar = largePillarMap.get(Maze.position(4,4));
        Pillar neighbor = largeMaze.getVerticalPillar(pillar, false);
        assertEquals(null, neighbor);

        neighbor = largeMaze.getVerticalPillar(edgePillar, false);
        assertEquals(4, neighbor.getX());
        assertEquals(3, neighbor.getY());
    }

//    @Test
//    public void testZeroConstructPillars(){
//        assertEquals(0,mazeTestObject.testConstructPillars(testMaze,0,0).size());
//    }
//
//    @Test
//    public void testOneConstructPillars(){
//        assertEquals(1,mazeTestObject.testConstructPillars(testMaze,1,1).size());
//    }
//
//    @Test
//    public void testNominalConstructPillars(){
//        assertEquals(25,mazeTestObject.testConstructPillars(testMaze,5,5).size());
//    }
//
//    @Test
//    public void testLargeConstructPillars(){
//        assertEquals(100,mazeTestObject.testConstructPillars(testMaze,10,10).size());
//    }
//
//    @Test
//    public void testSetNeighborsAtEdge(){
//        largePillarMap = mazeTestObject.testSetNeighbors(testMaze,10, 10);
//        pillarNeighbors = largePillarMap.get(Maze.position(0,0)).getNeighbors();
//        assertEquals(2, pillarNeighbors.size());
//        pillarNeighbors = largePillarMap.get(Maze.position(0,9)).getNeighbors();
//        assertEquals(2, pillarNeighbors.size());
//        pillarNeighbors = largePillarMap.get(Maze.position(9,9)).getNeighbors();
//        assertEquals(2, pillarNeighbors.size());
//        pillarNeighbors = largePillarMap.get(Maze.position(9,0)).getNeighbors();
//        assertEquals(2, pillarNeighbors.size());
//    }
//
//    @Test
//    public void testSetNeighborsInMiddle() {
//        mazeTestObject.testSetNeighbors(testMaze,10, 10);
//        largePillarMap = testMaze.getPillars();
//        pillarNeighbors = largePillarMap.get(Maze.position(3, 4)).getNeighbors();
//        assertEquals(4, pillarNeighbors.size());
//    }

    //Nominal case, Structured Basis, Data-flow, good data
    @Test
    public void testGetAndSetBegin(){
        largeMaze.setBegin(Maze.position(0,0));
        assertEquals(0, largeMaze.getBegin().getX());
        assertEquals(0, largeMaze.getBegin().getY());
    }

    //Nominal case, Structured Basis, Data-flow, good data
    @Test
    public void testGetPillars(){
        largePillarMap = largeMaze.getPillars();
        assertEquals(25, largePillarMap.size());
    }

    //Nominal case, Structured Basis, Data-flow, good data
    @Test
    public void testLength(){
        assertEquals(5, largeMaze.length());
    }

    //Nominal case, Structured Basis, Data-flow, good data
    @Test
    public void testWidth(){
        assertEquals(5, largeMaze.width());
    }

    //Nominal case, Structured Basis, Data-flow, good data
    @Test
    public void testSize(){
        assertEquals(25, largeMaze.size());
    }

    //Nominal case, Structured Basis, Data-flow, good data
    @Test
    public void testGetAndSetEnd(){
        largeMaze.setBegin(Maze.position(4,4));
        assertEquals(4, largeMaze.getBegin().getX());
        assertEquals(4, largeMaze.getBegin().getY());
    }
}
