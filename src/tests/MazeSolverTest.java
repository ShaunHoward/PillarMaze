package tests;

import maze.Maze;
import maze.MazeSolver;
import maze.Pillar;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the pillar maze solver class.
 *
 * @author Shaun Howard
 */
public class MazeSolverTest {

    Maze smallMaze;
    Maze largeMaze;
    List<Pillar> shortestPath;

    @Before
    public void setUp(){
        smallMaze = new Maze(3, 3);
        largeMaze = new Maze(5, 5);
    }

    @Test
    public void givenMaze(){
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
//
//            assertEquals(8, shortestPath.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception was thrown while linking pillars.");
        }
    }
}
