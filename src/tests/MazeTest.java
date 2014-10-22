package tests;

import maze.Maze;
import maze.Pillar;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void testSmallSize(){
        assertEquals(9, smallMaze.size());
    }

    @Test
    public void testLargeSize(){
        assertEquals(25, largeMaze.size());
    }

    @Test
    public void testSmallPillarConstruction(){
        for (int i = 0; i < smallMaze.length(); i++) {
            for (int j = 0; j < smallMaze.width(); j++) {
                assertEquals(new Pillar(j, i), smallPillarMap.get(Maze.position(j, i)));
            }
        }

    }

    @Test
    public void testLargePillarConstruction(){
        for (int i = 0; i < largeMaze.length(); i++) {
            for (int j = 0; j < largeMaze.width(); j++) {
                assertEquals(new Pillar(j, i), largePillarMap.get(Maze.position(j, i)));
            }
        }
    }

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


    @Test
    (expected=Exception.class)
    public void testExceptionLinkPillars() throws Exception{
        smallMaze.linkPillars(null, null);
    }

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

    @Test
    public void testPositionEquals(){
        assertEquals(Maze.position(0,0), Maze.position(0,0));
        assertThat(Maze.position(1, 0), not(Maze.position(0, 0)));
        assertThat(Maze.position(0,0), not(Maze.position(0,1)));
    }

    @Test
    public void testSetBeginNotInMap(){
        smallMaze.setBegin(Maze.position(26, 23));
    }

    @Test
    public void testSetEndNotInMap(){
        smallMaze.setEnd(Maze.position(0, 23));
    }

    @Test
    public void testNullPositionEquals(){
        assertFalse(Maze.position(0,0).equals(null));
    }

    @Test
    public void testNotPositionInstanceEquals(){
        Pillar p = new Pillar(0,0);
        assertFalse(Maze.position(0,0).equals(p));
    }

    @Test
    public void testPositionHashCode(){
        pos = Maze.position(90, 23423);
        assertEquals(26213, pos.hashCode());
    }

    @Test
    public void testPosition(){
        pos = Maze.position(25, 28);
        assertEquals(Maze.Position.class, pos.getClass());
        assertEquals(pos, Maze.position(25, 28));
    }

    @Test
    public void testConstructPillars(){
       //invoke constructPillars()
    }

    @Test
    public void testSetNeighbors(){
        //invoke setNeighbors()
    }

}
