package tests;

import maze.Maze;
import maze.MazeSolver;
import maze.Pillar;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the pillar maze solver class.
 *
 * @author Shaun Howard
 */
public class MazeSolverTest {

    MazeSolver solver;

    Maze zeroPillarMaze;
    Maze onePillarMaze;
    Maze smallMaze;
    Maze largeMaze;
    Maze rectangleMaze;

    Method initSearch;
    Method attemptLinks;

    List<Pillar> shortestPath, shortestPath2;

    @Before
    public void setUp() {
        solver = new MazeSolver();
        zeroPillarMaze = new Maze(0, 0);
        onePillarMaze = new Maze(1, 1);
        smallMaze = new Maze(3, 3);
        largeMaze = new Maze(5, 5);
        rectangleMaze = new Maze(4, 6);

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
            attemptLinks = solver.getClass().getDeclaredMethod("attemptLinks",Pillar.class, Pillar.class,
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

    @Test(expected=Exception.class)
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
            e.printStackTrace();
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
            e.printStackTrace();
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }

    @Test
    public void testPathAlongBorder(){
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
    public void testInitializeSearch() {
        //invoke initSearch

    }

    @Test
    public void testAttemptLinks() {
        //invoke attemptLinks

    }
}
