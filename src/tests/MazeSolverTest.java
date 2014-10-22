package tests;

import maze.Maze;
import maze.MazeSolver;
import maze.Pillar;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the pillar maze solver class.
 *
 * @author Shaun Howard
 */
public class MazeSolverTest {

    Map<Maze.Position, Pillar> testPillars;
    Set<Pillar> testESet;
    PriorityQueue<Pillar> testNQueue;
    Pillar testPillar;

    MazeSolver solver;

    Maze zeroPillarMaze;
    Maze onePillarMaze;
    Maze smallMaze;
    Maze largeMaze;
    Maze extraLargeRectMaze;

    Method initSearch;
    Method attemptLinks;

    List<Pillar> shortestPath, shortestPath2;

    @Before
    public void setUp() {
        testESet = new HashSet<>();
        testNQueue = new PriorityQueue<>();
        solver = new MazeSolver();
        zeroPillarMaze = new Maze(0, 0);
        onePillarMaze = new Maze(1, 1);
        smallMaze = new Maze(3, 3);
        largeMaze = new Maze(5, 5);
        extraLargeRectMaze = new Maze(9, 10);

        // Gather initializeSearch() method
        try {
            initSearch = solver.getClass().getDeclaredMethod("initializeSearch", Maze.class, Set.class,
                    PriorityQueue.class);
            initSearch.setAccessible(true);
        } catch (NoSuchMethodException e) {
            fail("Unable to find initializeSearch() method declaration.");
        }

        // Gather attemptLinks() method
        try {
            attemptLinks = solver.getClass().getDeclaredMethod("attemptLinks", Pillar.class, Pillar.class,
                    Set.class, PriorityQueue.class);
            attemptLinks.setAccessible(true);
        } catch (NoSuchMethodException e) {
            fail("Unable to find attemptLinks() method declaration.");
        }
    }

    @Test(expected = Exception.class)
    public void testZeroPillarMaze() throws Exception {
        shortestPath = MazeSolver.pStar(zeroPillarMaze, 0);
    }

    @Test(expected = Exception.class)
    public void testOnePillarMaze() throws Exception {
        shortestPath = MazeSolver.pStar(onePillarMaze, 1);
    }

    @Test(expected = Exception.class)
    public void testMazeSizeMismatch() throws Exception {
        shortestPath = MazeSolver.pStar(smallMaze, 20);
    }

    @Test(expected = Exception.class)
    public void testMazeWithoutBeginning() throws Exception {
        //link row 1
        smallMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
        smallMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
        smallMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

        //link row 2
        smallMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));

        //link row 3
        smallMaze.linkPillars(Maze.position(0, 2), Maze.position(1, 2));
        smallMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

        //set end
        smallMaze.setEnd(Maze.position(2, 2));
        shortestPath = MazeSolver.pStar(smallMaze, 9);
    }

    @Test
    public void testMazeWithoutEnd() {
        try {
            //link row 1
            smallMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 2
            smallMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));

            //link row 3
            smallMaze.linkPillars(Maze.position(0, 2), Maze.position(1, 2));
            smallMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //set beginning and end
            smallMaze.setBegin(Maze.position(1, 1));
            shortestPath = MazeSolver.pStar(smallMaze, 9);

            assertEquals(null, shortestPath);

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testExtraLargeRectangleMaze() {
        try {
            assertEquals(90, extraLargeRectMaze.size());

            //link pillars
            extraLargeRectMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            extraLargeRectMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));
            extraLargeRectMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));
            extraLargeRectMaze.linkPillars(Maze.position(2, 1), Maze.position(2, 2));
            extraLargeRectMaze.linkPillars(Maze.position(2, 2), Maze.position(3, 2));
            extraLargeRectMaze.linkPillars(Maze.position(3, 2), Maze.position(3, 3));
            extraLargeRectMaze.linkPillars(Maze.position(3, 3), Maze.position(4, 3));
            extraLargeRectMaze.linkPillars(Maze.position(4, 3), Maze.position(4, 4));
            extraLargeRectMaze.linkPillars(Maze.position(4, 4), Maze.position(5, 4));
            extraLargeRectMaze.linkPillars(Maze.position(5, 4), Maze.position(5, 5));
            extraLargeRectMaze.linkPillars(Maze.position(5, 5), Maze.position(5, 6));
            extraLargeRectMaze.linkPillars(Maze.position(5, 6), Maze.position(6, 6));
            extraLargeRectMaze.linkPillars(Maze.position(6, 6), Maze.position(6, 7));
            extraLargeRectMaze.linkPillars(Maze.position(6, 7), Maze.position(7, 7));
            extraLargeRectMaze.linkPillars(Maze.position(7, 7), Maze.position(7, 8));
            extraLargeRectMaze.linkPillars(Maze.position(7, 8), Maze.position(7, 9));

            //set beginning and end
            extraLargeRectMaze.setBegin(Maze.position(0, 0));
            extraLargeRectMaze.setEnd(Maze.position(8, 9));
            shortestPath = MazeSolver.pStar(extraLargeRectMaze, 90);

            assertEquals("<0, 0>", shortestPath.get(0).getCoordinateString());
            assertEquals("<1, 0>", shortestPath.get(1).getCoordinateString());
            assertEquals("<1, 1>", shortestPath.get(2).getCoordinateString());
            assertEquals("<2, 1>", shortestPath.get(3).getCoordinateString());
            assertEquals("<2, 2>", shortestPath.get(4).getCoordinateString());
            assertEquals("<3, 2>", shortestPath.get(5).getCoordinateString());
            assertEquals("<3, 3>", shortestPath.get(6).getCoordinateString());
            assertEquals("<4, 3>", shortestPath.get(7).getCoordinateString());
            assertEquals("<4, 4>", shortestPath.get(8).getCoordinateString());
            assertEquals("<5, 4>", shortestPath.get(9).getCoordinateString());
            assertEquals("<5, 5>", shortestPath.get(10).getCoordinateString());
            assertEquals("<5, 6>", shortestPath.get(11).getCoordinateString());
            assertEquals("<6, 6>", shortestPath.get(12).getCoordinateString());
            assertEquals("<6, 7>", shortestPath.get(13).getCoordinateString());
            assertEquals("<7, 7>", shortestPath.get(14).getCoordinateString());
            assertEquals("<7, 8>", shortestPath.get(15).getCoordinateString());
            assertEquals("<7, 9>", shortestPath.get(16).getCoordinateString());
            assertEquals("<8, 9>", shortestPath.get(17).getCoordinateString());

            assertEquals(18, shortestPath.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testGivenMaze() {
        try {
            //link row 1
            largeMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            largeMaze.linkPillars(Maze.position(2, 0), Maze.position(3, 0));
            largeMaze.linkPillars(Maze.position(3, 0), Maze.position(4, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 2
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(1, 2));
            largeMaze.linkPillars(Maze.position(3, 1), Maze.position(4, 1));

            //link row 3
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(1, 3));
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //link row 4
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(0, 3));
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(2, 3));
            largeMaze.linkPillars(Maze.position(3, 3), Maze.position(4, 3));
            largeMaze.linkPillars(Maze.position(0, 3), Maze.position(0, 4));

            //link row 5
            largeMaze.linkPillars(Maze.position(0, 4), Maze.position(1, 4));
            largeMaze.linkPillars(Maze.position(1, 4), Maze.position(2, 4));
            largeMaze.linkPillars(Maze.position(2, 4), Maze.position(3, 4));
            largeMaze.linkPillars(Maze.position(3, 4), Maze.position(4, 4));

            //set beginning and end
            largeMaze.setBegin(Maze.position(0, 0));
            largeMaze.setEnd(Maze.position(4, 4));
            shortestPath = MazeSolver.pStar(largeMaze, 25);

            assertEquals("<0, 0>", shortestPath.get(0).getCoordinateString());
            assertEquals("<1, 0>", shortestPath.get(1).getCoordinateString());
            assertEquals("<1, 1>", shortestPath.get(2).getCoordinateString());
            assertEquals("<1, 2>", shortestPath.get(3).getCoordinateString());
            assertEquals("<1, 3>", shortestPath.get(4).getCoordinateString());
            assertEquals("<1, 4>", shortestPath.get(5).getCoordinateString());
            assertEquals("<2, 4>", shortestPath.get(6).getCoordinateString());
            assertEquals("<3, 4>", shortestPath.get(7).getCoordinateString());
            assertEquals("<4, 4>", shortestPath.get(8).getCoordinateString());

            assertEquals(9, shortestPath.size());

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testSolveIrregularBeginAndEndMaze(){
        try {
            //link row 1
            largeMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            largeMaze.linkPillars(Maze.position(2, 0), Maze.position(3, 0));
            largeMaze.linkPillars(Maze.position(3, 0), Maze.position(4, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 2
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(1, 2));
            largeMaze.linkPillars(Maze.position(3, 1), Maze.position(4, 1));

            //link row 3
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(1, 3));
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //link row 4
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(0, 3));
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(2, 3));
            largeMaze.linkPillars(Maze.position(3, 3), Maze.position(4, 3));
            largeMaze.linkPillars(Maze.position(0, 3), Maze.position(0, 4));

            //link row 5
            largeMaze.linkPillars(Maze.position(0, 4), Maze.position(1, 4));
            largeMaze.linkPillars(Maze.position(1, 4), Maze.position(2, 4));
            largeMaze.linkPillars(Maze.position(2, 4), Maze.position(3, 4));
            largeMaze.linkPillars(Maze.position(3, 4), Maze.position(4, 4));

            //set beginning and end
            largeMaze.setBegin(Maze.position(1, 0));
            largeMaze.setEnd(Maze.position(3, 4));
            shortestPath = MazeSolver.pStar(largeMaze, 25);

            assertEquals("<1, 0>", shortestPath.get(0).getCoordinateString());
            assertEquals("<1, 1>", shortestPath.get(1).getCoordinateString());
            assertEquals("<1, 2>", shortestPath.get(2).getCoordinateString());
            assertEquals("<1, 3>", shortestPath.get(3).getCoordinateString());
            assertEquals("<2, 3>", shortestPath.get(4).getCoordinateString());
            assertEquals("<2, 4>", shortestPath.get(5).getCoordinateString());
            assertEquals("<3, 4>", shortestPath.get(6).getCoordinateString());

            assertEquals(7, shortestPath.size());

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testSolveBackwardMaze(){
        try {
            //link row 1
            largeMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            largeMaze.linkPillars(Maze.position(2, 0), Maze.position(3, 0));
            largeMaze.linkPillars(Maze.position(3, 0), Maze.position(4, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 2
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(1, 2));
            largeMaze.linkPillars(Maze.position(3, 1), Maze.position(4, 1));

            //link row 3
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(1, 3));
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //link row 4
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(0, 3));
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(2, 3));
            largeMaze.linkPillars(Maze.position(3, 3), Maze.position(4, 3));
            largeMaze.linkPillars(Maze.position(0, 3), Maze.position(0, 4));

            //link row 5
            largeMaze.linkPillars(Maze.position(0, 4), Maze.position(1, 4));
            largeMaze.linkPillars(Maze.position(1, 4), Maze.position(2, 4));
            largeMaze.linkPillars(Maze.position(2, 4), Maze.position(3, 4));
            largeMaze.linkPillars(Maze.position(3, 4), Maze.position(4, 4));

            //set beginning and end
            largeMaze.setBegin(Maze.position(3, 4));
            largeMaze.setEnd(Maze.position(1, 0));
            shortestPath = MazeSolver.pStar(largeMaze, 25);

            assertEquals(null, shortestPath);

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testSolveGivenMazeTwice() {
        try {
            //link row 1
            largeMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            largeMaze.linkPillars(Maze.position(2, 0), Maze.position(3, 0));
            largeMaze.linkPillars(Maze.position(3, 0), Maze.position(4, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 2
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(1, 2));
            largeMaze.linkPillars(Maze.position(3, 1), Maze.position(4, 1));

            //link row 3
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(1, 3));
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //link row 4
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(0, 3));
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(2, 3));
            largeMaze.linkPillars(Maze.position(3, 3), Maze.position(4, 3));
            largeMaze.linkPillars(Maze.position(0, 3), Maze.position(0, 4));

            //link row 5
            largeMaze.linkPillars(Maze.position(0, 4), Maze.position(1, 4));
            largeMaze.linkPillars(Maze.position(1, 4), Maze.position(2, 4));
            largeMaze.linkPillars(Maze.position(2, 4), Maze.position(3, 4));
            largeMaze.linkPillars(Maze.position(3, 4), Maze.position(4, 4));

            //set beginning and end
            largeMaze.setBegin(Maze.position(0, 0));
            largeMaze.setEnd(Maze.position(4, 4));
            shortestPath = MazeSolver.pStar(largeMaze, 25);
            shortestPath2 = MazeSolver.pStar(largeMaze, 25);

            assertEquals(9, shortestPath.size());

            assertEquals(shortestPath.size(), shortestPath2.size());

            assertEquals("<0, 0>", shortestPath.get(0).getCoordinateString());
            assertEquals("<1, 0>", shortestPath.get(1).getCoordinateString());
            assertEquals("<1, 1>", shortestPath.get(2).getCoordinateString());
            assertEquals("<1, 2>", shortestPath.get(3).getCoordinateString());
            assertEquals("<1, 3>", shortestPath.get(4).getCoordinateString());
            assertEquals("<1, 4>", shortestPath.get(5).getCoordinateString());
            assertEquals("<2, 4>", shortestPath.get(6).getCoordinateString());
            assertEquals("<3, 4>", shortestPath.get(7).getCoordinateString());
            assertEquals("<4, 4>", shortestPath.get(8).getCoordinateString());

            assertEquals("<0, 0>", shortestPath2.get(0).getCoordinateString());
            assertEquals("<0, 1>", shortestPath2.get(1).getCoordinateString());
            assertEquals("<0, 2>", shortestPath2.get(2).getCoordinateString());
            assertEquals("<0, 3>", shortestPath2.get(3).getCoordinateString());
            assertEquals("<0, 4>", shortestPath2.get(4).getCoordinateString());
            assertEquals("<1, 4>", shortestPath2.get(5).getCoordinateString());
            assertEquals("<2, 4>", shortestPath2.get(6).getCoordinateString());
            assertEquals("<3, 4>", shortestPath2.get(7).getCoordinateString());
            assertEquals("<4, 4>", shortestPath2.get(8).getCoordinateString());

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testPathAlongBorder() {
        try {
            //link row 1
            largeMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            largeMaze.linkPillars(Maze.position(2, 0), Maze.position(3, 0));
            largeMaze.linkPillars(Maze.position(3, 0), Maze.position(4, 0));
            largeMaze.linkPillars(Maze.position(4, 0), Maze.position(4, 1));

            //link row 2
            largeMaze.linkPillars(Maze.position(4, 1), Maze.position(4, 2));

            //link row 3
            largeMaze.linkPillars(Maze.position(4, 2), Maze.position(4, 3));

            //link row 4
            largeMaze.linkPillars(Maze.position(4, 3), Maze.position(4, 4));

            //set beginning and end
            largeMaze.setBegin(Maze.position(0, 0));
            largeMaze.setEnd(Maze.position(4, 4));
            shortestPath = MazeSolver.pStar(largeMaze, 25);

            assertEquals("<0, 0>", shortestPath.get(0).getCoordinateString());
            assertEquals("<1, 0>", shortestPath.get(1).getCoordinateString());
            assertEquals("<2, 0>", shortestPath.get(2).getCoordinateString());
            assertEquals("<3, 0>", shortestPath.get(3).getCoordinateString());
            assertEquals("<4, 0>", shortestPath.get(4).getCoordinateString());
            assertEquals("<4, 1>", shortestPath.get(5).getCoordinateString());
            assertEquals("<4, 2>", shortestPath.get(6).getCoordinateString());
            assertEquals("<4, 3>", shortestPath.get(7).getCoordinateString());
            assertEquals("<4, 4>", shortestPath.get(8).getCoordinateString());

            assertEquals(9, shortestPath.size());

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testTraverseOnlyWithPlankMaze() {
        try {
            //link row 1
            smallMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 2
            smallMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));

            //link row 3
            smallMaze.linkPillars(Maze.position(0, 2), Maze.position(1, 2));
            smallMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //set beginning and end
            smallMaze.setBegin(Maze.position(0, 0));
            smallMaze.setEnd(Maze.position(2, 2));
            shortestPath = MazeSolver.pStar(smallMaze, 9);

            assertEquals(5, shortestPath.size());

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testImpossibleTraversalMaze() {
        try {
            //link row 1
            smallMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 3
            smallMaze.linkPillars(Maze.position(0, 2), Maze.position(1, 2));

            //set beginning and end
            smallMaze.setBegin(Maze.position(0, 0));
            smallMaze.setEnd(Maze.position(2, 2));
            shortestPath = MazeSolver.pStar(smallMaze, 9);

            assertEquals(null, shortestPath);

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testNoBenefitFromPlankMaze() {
        try {
            //link row 1
            smallMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            smallMaze.linkPillars(Maze.position(0, 0), Maze.position(0, 1));
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 2
            smallMaze.linkPillars(Maze.position(0, 1), Maze.position(0, 2));
            smallMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));

            //link row 3
            smallMaze.linkPillars(Maze.position(0, 2), Maze.position(1, 2));
            smallMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //set beginning and end
            smallMaze.setBegin(Maze.position(0, 0));
            smallMaze.setEnd(Maze.position(2, 2));
            shortestPath = MazeSolver.pStar(smallMaze, 9);

            assertEquals(5, shortestPath.size());

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testMultiplePlankPositionsMaze() {
        try {
            //link row 1
            smallMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            smallMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));

            //link row 2
            smallMaze.linkPillars(Maze.position(0, 1), Maze.position(0, 2));
            smallMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));
            smallMaze.linkPillars(Maze.position(1, 1), Maze.position(1, 2));

            //link row 3
            smallMaze.linkPillars(Maze.position(0, 2), Maze.position(1, 2));
            smallMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //set beginning and end
            smallMaze.setBegin(Maze.position(0, 0));
            smallMaze.setEnd(Maze.position(2, 2));
            shortestPath = MazeSolver.pStar(smallMaze, 9);

            assertEquals(5, shortestPath.size());

        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testShortestPath() {
        Pillar p = new Pillar(0, 0);
        Pillar q = new Pillar(0, 1);
        q.setPrevious(p);
        List<Pillar> path = MazeSolver.shortestPath(q);
        assertEquals(2, path.size());
        assertEquals(p, path.get(0));
        assertEquals(q, path.get(1));
    }

    @Test
    public void testDistanceToEnd() {
        //set beginning and end
        smallMaze.setBegin(Maze.position(0, 0));
        smallMaze.setEnd(Maze.position(2, 2));
        Map<Maze.Position, Pillar> smallMap = smallMaze.getPillars();
        try {
            assertEquals(4, MazeSolver.distanceToEnd(smallMap.get(Maze.position(0, 0)), smallMap.get(Maze.position(2, 2))), .01);
        } catch (Exception e) {
            fail("Unexpectedly failed to get the distance to the end of the maze.");
        }
    }

    @Test
    public void testNullDistanceToEnd() {
        //set beginning and end
        smallMaze.setBegin(Maze.position(0, 0));
        smallMaze.setEnd(Maze.position(2, 2));
        Map<Maze.Position, Pillar> smallMap = smallMaze.getPillars();
        try {
            assertEquals(Float.POSITIVE_INFINITY, MazeSolver.distanceToEnd(null, smallMap.get(Maze.position(2, 2))), .01);
        } catch (Exception e) {
            fail("Unexpectedly failed to get the distance to the end of the maze.");
        }
    }

    @Test
    public void testInitializeSearch() {
        try {
            smallMaze.setBegin(Maze.position(1, 1));
            initSearch.invoke(solver.getClass(), smallMaze, testESet, testNQueue);
        } catch (Exception e) {
            fail("Unexpectedly could not invoke initializeSearch().");
        }

        assertEquals(1, testESet.size());
        assertEquals(1, testNQueue.size());
    }

    @Test(expected = Exception.class)
    public void testInvalidSizeInitializeSearch() throws Exception {
        zeroPillarMaze.setBegin(Maze.position(0, 0));
        initSearch.invoke(solver.getClass(), zeroPillarMaze, testESet, testNQueue);
    }

    @Test
    public void testAttemptLinks() {
        try {
            //link row 1
            largeMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            largeMaze.linkPillars(Maze.position(2, 0), Maze.position(3, 0));
            largeMaze.linkPillars(Maze.position(3, 0), Maze.position(4, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 2
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(1, 2));
            largeMaze.linkPillars(Maze.position(3, 1), Maze.position(4, 1));

            //link row 3
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(1, 3));
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //link row 4
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(0, 3));
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(2, 3));
            largeMaze.linkPillars(Maze.position(3, 3), Maze.position(4, 3));
            largeMaze.linkPillars(Maze.position(0, 3), Maze.position(0, 4));

            //link row 5
            largeMaze.linkPillars(Maze.position(0, 4), Maze.position(1, 4));
            largeMaze.linkPillars(Maze.position(1, 4), Maze.position(2, 4));
            largeMaze.linkPillars(Maze.position(2, 4), Maze.position(3, 4));
            largeMaze.linkPillars(Maze.position(3, 4), Maze.position(4, 4));

            //set beginning and end
            largeMaze.setBegin(Maze.position(0, 0));
            largeMaze.setEnd(Maze.position(4, 4));
        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }

        //invoke attemptLinks
        try {
            testPillars = largeMaze.getPillars();
            testPillar = testPillars.get(Maze.position(3,3));
            testPillar.setPlanksLeft(1);
            attemptLinks.invoke(solver.getClass(), testPillar, largeMaze.getEnd(), testESet, testNQueue);
        } catch (Exception e) {
            fail("Unexpectedly could not invoke attemptLinks().");
        }

        assertEquals(3, testESet.size());
        assertEquals(3, testNQueue.size());
    }

    @Test
    public void testInvalidAttemptLinks() {
        try {
            //link row 1
            largeMaze.linkPillars(Maze.position(0, 0), Maze.position(1, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(2, 0));
            largeMaze.linkPillars(Maze.position(2, 0), Maze.position(3, 0));
            largeMaze.linkPillars(Maze.position(3, 0), Maze.position(4, 0));
            largeMaze.linkPillars(Maze.position(1, 0), Maze.position(1, 1));

            //link row 2
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(2, 1));
            largeMaze.linkPillars(Maze.position(1, 1), Maze.position(1, 2));
            largeMaze.linkPillars(Maze.position(3, 1), Maze.position(4, 1));

            //link row 3
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(1, 3));
            largeMaze.linkPillars(Maze.position(1, 2), Maze.position(2, 2));

            //link row 4
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(0, 3));
            largeMaze.linkPillars(Maze.position(1, 3), Maze.position(2, 3));
            largeMaze.linkPillars(Maze.position(3, 3), Maze.position(4, 3));
            largeMaze.linkPillars(Maze.position(0, 3), Maze.position(0, 4));

            //link row 5
            largeMaze.linkPillars(Maze.position(0, 4), Maze.position(1, 4));
            largeMaze.linkPillars(Maze.position(1, 4), Maze.position(2, 4));
            largeMaze.linkPillars(Maze.position(2, 4), Maze.position(3, 4));
            largeMaze.linkPillars(Maze.position(3, 4), Maze.position(4, 4));

            //set beginning and end
            largeMaze.setBegin(Maze.position(0, 0));
            largeMaze.setEnd(Maze.position(4, 4));
        } catch (Exception e) {
            fail("Unexpected exception was thrown while linking pillars.");
        }

        //invoke attemptLinks
        try {
            testPillars = largeMaze.getPillars();
            testPillar = testPillars.get(Maze.position(3,3));
            testPillar.setPlanksLeft(0);
            attemptLinks.invoke(solver.getClass(), testPillar, largeMaze.getEnd(), testESet, testNQueue);
        } catch (Exception e) {
            fail("Unexpectedly could not invoke attemptLinks().");
        }

        assertEquals(0, testESet.size());
        assertEquals(0, testNQueue.size());
    }
}
