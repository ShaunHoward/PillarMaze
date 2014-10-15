package maze;

import java.util.*;

/**
 * Solves the pillar maze with a P* (modified A*) algorithm.
 *
 * @author Shaun Howard
 */
public class MazeSolver {

    /**
     * P* algorithm which finds the most optimal path to from
     * the beginning pillar to the end pillar of the given maze.
     * Unlike A*, P* considers all pillars with extra planks between
     * neighbors, given that the number of planks at the current step
     * are greater than 0.
     * <p/>
     * Once P* begins to follow a shortest path, it takes that path
     * to the end of the maze.
     *
     * @param maze - the maze to find the shortest path through
     * @param size - the expected size of the maze
     * @throws Exception - thrown when the actual and expected maze sizes
     * differ
     */
    public static List<Pillar> pStar(Maze maze, int size) throws Exception {

        /* throw exception with maze size is not expected size. */
        MazeUtilities.throwExceptionWhenDifferent(maze.size, size);

        /* the end of the maze. */
        Pillar end = maze.getEnd();

        /* initialize the set of expanded nodes 'E' */
        Set<Pillar> E = new HashSet<>();

        /*
         * initialize a priority queue of nodes 'N' sorted in non-decreasing order by cost, c,
         * the sum of heuristic and number of times moved
        */
        PriorityQueue<Pillar> N = new PriorityQueue<>();

        /* initialize the P* search through the maze. */
        initializeSearch(maze, E, N);

        /* while N is not empty */
        while (!N.isEmpty()) {

            /* extract lowest cost node 'v' from N */
            Pillar v = N.poll();

            /* if v is the end node of maze */
            if (v.equals(end)) {

                /* return shortest path to v */
                return shortestPath(v);
            }

            /* find the connected neighbor nodes of v */
            List<Pillar> connectedNeighbors = v.getNeighborList(true);

            /* for each connected node 'c' */
            for (Pillar c : connectedNeighbors) {

                /* if c does not exist in E */
                if (!E.contains(c)) {

                    /* c.p = v */
                    c.setPrevious(v);

                    /* c.b = v.b + 1 */
                    c.setDistanceFromBegin(v.getDistanceFromBegin() + 1);

                    /* c.e = distance to end from c */
                    c.setHeuristic(distanceToEnd(c, end));

                    /* c.n = v.n */
                    c.setPlanksLeft(v.getPlanksLeft());

                    /* c.c = c.b + c.e */
                    c.setCost(c.getDistanceFromBegin() + c.getHeuristic());

                    /* add c to E */
                    E.add(c);

                    /* add c to N */
                    N.add(c);
                }
            }

            /* attempt to link unconnected neighbors of v if at least one plank at v. */
            attemptLinks(v, end, E, N);
        }

        /* return nil */
        return null;
    }

    /**
     * Initializes the P* search algorithm by getting the beginning pillar of the maze
     * and adding it to the set of explored pillars, then adding it the the priority queue
     * of new pillars and initializing it's values.
     *
     * @param maze - the maze to search
     * @param E - the set of explored pillars
     * @param N - the priority queue of new pillars
     */
    static void initializeSearch(Maze maze, Set<Pillar> E, PriorityQueue<Pillar> N) {

        /* if maze size is greater than 0 */
        if (maze.size > 0) {

            /* The beginning pillar of the maze. */
            Pillar pillar = maze.getBegin();

           /* add the beginning node of maze to E */
            E.add(pillar);

           /* add the beginning node to N with c=0, n=1, e=distance to end from beginning */
            pillar.setCost(0);
            pillar.setPlanksLeft(1);
            pillar.setHeuristic(distanceToEnd(pillar, maze.getEnd()));
            N.add(pillar);
        }

    }

    /**
     * Attempts to link the currently visited pillar, v, with unconnected pillars
     * that are its neighbors. These newly connected pillars are added to the set
     * of explored pillars, E, and the priority queue of new pillars, N.
     *
     * @param v - the currently visited pillar
     * @param end - the end pillar of the maze
     * @param E - the set of explored pillars
     * @param N - the priority queue of new pillars
     */
    static void attemptLinks(Pillar v, Pillar end, Set<Pillar> E, PriorityQueue<Pillar> N) {
        /* if v.n > 0 */
        if (v.getPlanksLeft() > 0) {

                /* find the unconnected neighbor nodes of v */
            List<Pillar> unconnectedNeighbors = v.getNeighborList(false);

                /* for each unconnected node u */
            for (Pillar u : unconnectedNeighbors) {

                    /* if u does not exist in E */
                if (!E.contains(u)) {
                        /* u.p = v */
                    u.setPrevious(v);

                        /* u.b = v.b + 1 */
                    u.setDistanceFromBegin(v.getDistanceFromBegin() + 1);

                        /* u.e = distance to end from u */
                    u.setHeuristic(distanceToEnd(u, end));

                        /* u.n = v.n - 1 */
                    u.setPlanksLeft(v.getPlanksLeft() - 1);

                        /* u.c = u.b + u.e */
                    u.setCost(u.getDistanceFromBegin() + u.getHeuristic());

                        /* add u to E */
                    E.add(u);

                        /* add u to N */
                    N.add(u);
                }
            }
        }
    }

    /**
     * Returns the shortest path to the given end pillar
     * from the beginning pillar of the maze.
     *
     * @param end - the pillar to get the shortest path to
     *            from the beginning pillar
     * @return the list of pillars from the beginning of the
     * maze to the end of the maze
     */
    static List<Pillar> shortestPath(Pillar end) {
        /* initialize the path list */
        List<Pillar> shortestPath = new ArrayList<>();

        /* initialize a node called 'curr' with end */
        Pillar curr = end;

        /* add curr to path list */
        shortestPath.add(curr);

        /* while curr.p is not nil */
        while (!MazeUtilities.isNull(curr.getPrevious())) {

            /* curr = curr.p */
            curr = curr.getPrevious();

            /* add curr to front of path list */
            shortestPath.add(0, curr);
        }

        /* return path list */
        return shortestPath;
    }

    /**
     * Determines and returns the distance from the
     * given pillar to the end of the maze.
     *
     * @param current - the pillar to get the distance to
     *                from the end of the maze
     * @param end     - the end pillar of the maze
     * @return the distance from the given pillar to the
     * end of the maze
     */
    static float distanceToEnd(Pillar current, Pillar end) {
        /* call the current node 'c' */
        Pillar c = current;

        /* find the end node of the grid 'e' */
        Pillar e = end;

        /* add the absolute values of (e.x - c.x) and (e.y - c.y) and call this 'manhattan' */
        int manhattan = Math.abs(e.getX() - c.getX()) + Math.abs(e.getY() - c.getY());

        /* return the manhattan distance */
        return manhattan;
    }
}