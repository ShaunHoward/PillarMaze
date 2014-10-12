package maze;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A maze is a grid of pillar nodes that may be linked by planks.
 * The maze is constructed by incrementally adding nodes to specific
 * x,y coordinates which are not already occupied and linking them by
 * putting a node as a key into another nodes map of neighbors and setting
 * its value to true, otherwise the value should be false and these nodes
 * are simply unconnected neighbors. The end node, the exit of the maze,
 * will be signified by a Boolean 'end' which should be set to true, otherwise,
 * the node is a step to the end and so this value is false.
 * The other values of the nodes should initially be set to 0 or null.
 *
 * @author Shaun Howard
 */
public class Maze {

    int size = 0;
    Pillar begin = null;
    Pillar end = null;
    Set<Pillar> pillars = new HashSet<>();

    /**
     * Constructs an empty maze of the given size.
     *
     * @param size - the size of the maze to construct
     */
    public Maze(int size) {
        this.size = size;
    }

    /**
     * Adds the given pillar to the set of pillars.
     *
     * @param pillar - the pillar to add to the set
     */
    void addPillar(Pillar pillar) {
        pillars.add(pillar);
        if (pillar.isBegin()) {
            begin = pillar;
        } else if (pillar.isEnd()) {
            end = pillar;
        }
    }




}
