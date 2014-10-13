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
     *
     * Once P* begins to follow a shortest path, it takes that path
     * to the end of the maze.
     *
     * @param maze - the maze to find the shortest path through
     * @param size - the expected size of the maze
     */
    static List<Pillar> pStar(Maze maze, int size) throws Exception {
        /* if the size of maze is not equal to size */
        if (maze.size != size) {
            /* throw an invalid size exception */
            throw new Exception("Maze size was unequal to expected size.");
        }

        /* initialize the set of expanded nodes 'E' */
        Set<Pillar> E = new HashSet<>();

        /*
         * initialize a priority queue of nodes 'N' sorted in non-decreasing order by cost, c,
         * the sum of heuristic and number of times moved
        */
        PriorityQueue<Pillar> N = new PriorityQueue<>();

        /* if maze size is greater than 0 */
        if (size > 0) {

            /* The beginning pillar of the maze. */
            Pillar pillar = maze.getBegin();

           /* add the beginning node of maze to E */
            E.add(pillar);

           /* add the beginning node to N with c=0, n=1, e=distance to end from beginning */
            pillar.setCost(0);
            pillar.setPlanksLeft(1);
            pillar.setHeuristic(distanceToEnd(pillar));
            N.add(pillar);
        }

        /* while N is not empty */
        while (!N.isEmpty()) {

            /* extract lowest cost node 'v' from N */
            Pillar v = N.poll();

            /* if v is the end node of maze */
            if (v.equals(maze.getEnd())) {
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
                    c.setHeuristic(distanceToEnd(c));

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
        }
 /*
        if v.n > 0
        find the unconnected neighbor nodes of v
        for each unconnected node u
        if u does not exist in E
        u.p . v
        u.b . v.b + 1
        u.e . distance to end from u
        u.n . v.n - 1
        u.c . u.b + u.e
        add u to E
        add u to N
        end if
        end
        end if
        end
        return nil */
                return null;
    }

    private static List<Pillar> shortestPath(Pillar v) {
        return null;
    }

    private static float distanceToEnd(Pillar pillar) {
        return 0;
    }

    /*
    // shortest path to end
    initialize the path list
    initialize a node called 'curr' with end
    add curr to path list
    while curr.p is not nil
    curr . curr.p
    add curr to front of path list
            end
    return path list

    // distance to end from node
    call the current node 'c'
    find the end node of the grid 'e'
    add the absolute values of (e.x - c.x) and (e.y - c.y) and call this 'manhattan'
            return the manhattan distance
            */
}