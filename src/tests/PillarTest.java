package tests;

import maze.Maze;

/**
 * Tests the pillar class.
 *
 * @author Shaun Howard
 */
public class PillarTest {

    Maze testMazeSmall;
    Maze testMazeLarge;

    public void setUp(){
        testMazeSmall = new Maze(3, 3);
        testMazeLarge = new Maze(5, 5);
    }
}
