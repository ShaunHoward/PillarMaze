package maze;

import java.util.HashMap;
import java.util.Map;

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

    /* the length of the maze by rows. */
    int length = 0;

    /* the width of the maze by columns. */
    int width = 0;

    /* the size of the maze by length times width. */
    int size = 0;

    /* the beginning pillar of the maze. */
    Pillar begin = null;

    /* the ending pillar of the maze. */
    Pillar end = null;

    /* a map of pillars keyed by their positions. */
    Map<Position, Pillar> pillars = new HashMap<>();

    /**
     * Constructs an empty maze of the length and width
     * then calculates the maze size based on these
     * parameters.
     *
     * @param length - the length of the maze to construct
     * @param width  - the width of the maze to construct
     */
    public Maze(int length, int width) {
        this.length = length;
        this.width = width;
        this.size = length * width;
        constructPillars();
        setNeighbors();
    }

    /**
     * Constructs the pillars for the maze
     * across the grid of size length x width.
     * Each pillar has it's own grid coordinates
     * as if it was on an inverted 2D graph.
     *
     * The origin of the maze is at the bottom right
     * corner while the farthest distance is at the
     * top left corner.
     *
     * From right to left, x coordinate increases.
     * From bottom to top, y coordinate increases.
     */
    void constructPillars() {
        Pillar pillar;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                pillar = new Pillar(j, i);
                pillars.put(new Position(j, i), pillar);
            }
        }
    }

    /**
     * Sets the neighbor pillars of each pillar in the
     * maze. At this point, the maze does not determine
     * if the pillars are connected by planks. This will
     * be set by the user with the linkPillars() method.
     */
    void setNeighbors() {
        Pillar pillar;

        /*
         * Find all the neighbors of a selected pillar
         * at the given position in the maze and add
         * them to the map of neighbors for that pillar.
         * Null neighbors will not be added to the map.
         */
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                pillar = pillars.get(new Position(j, i));
                pillar.addNeighbor(getBelowPillar(pillar), false);
                pillar.addNeighbor(getAbovePillar(pillar), false);
                pillar.addNeighbor(getLeftPillar(pillar), false);
                pillar.addNeighbor(getRightPillar(pillar), false);
            }
        }
    }

    /**
     * Gets the pillar below the given pillar, otherwise
     * returns null.
     *
     * @param pillar - the pillar to find the pillar below
     * @return the pillar below the given pillar
     */
    Pillar getBelowPillar(Pillar pillar) {
        int x = pillar.getX();
        int y = pillar.getY();
        if (y > 0) {
            return pillars.get(new Position(x, y - 1));
        }
        return null;
    }

    /**
     * Gets the pillar above the given pillar, otherwise
     * returns null.
     *
     * @param pillar - the pillar to find the pillar above
     * @return the pillar above the given pillar
     */
    Pillar getAbovePillar(Pillar pillar) {
        int x = pillar.getX();
        int y = pillar.getY();
        if (y < width) {
            return pillars.get(new Position(x, y + 1));
        }
        return null;
    }

    /**
     * Gets the pillar right of the given pillar, otherwise
     * returns null.
     *
     * @param pillar - the pillar to find the pillar right of
     * @return the pillar right of the given pillar
     */
    Pillar getRightPillar(Pillar pillar) {
        int x = pillar.getX();
        int y = pillar.getY();
        if (x > 0) {
            return pillars.get(new Position(x - 1, y));
        }
        return null;
    }

    /**
     * Gets the pillar left of the given pillar, otherwise
     * returns null.
     *
     * @param pillar - the pillar to find the pillar left of
     * @return the pillar left of the given pillar
     */
    Pillar getLeftPillar(Pillar pillar) {
        int x = pillar.getX();
        int y = pillar.getY();
        if (x < length) {
            return pillars.get(new Position(x + 1, y));
        }
        return null;
    }

    /**
     * Links the start pillar to the end pillar
     * with a unidirectional plank.
     *
     * @param startPos - the position of the pillar to start the plank
     * @param endPos   - the position of the pillar to end the plank
     * @throws Exception - when the pillars at either position are null
     */
    public void linkPillars(Position startPos, Position endPos) throws Exception {
        Pillar start = pillars.get(startPos);
        Pillar end = pillars.get(endPos);

        MazeUtilities.throwExceptionWhenNull(start, end);

        Map<Pillar, Boolean> neighbors = start.getNeighbors();
        if (neighbors.containsKey(end)) {
            neighbors.put(end, true);
        }
    }

//    /**
//     * Adds the given pillar to the set of pillars.
//     *
//     * @param pillar - the pillar to add to the set
//     */
//    void addPillar(Pillar pillar) {
//        pillars.pu(pillar);
//        if (pillar.isBegin()) {
//            begin = pillar;
//        } else if (pillar.isEnd()) {
//            end = pillar;
//        }
//    }

    /**
     * Gets the beginning pillar of this maze.
     *
     * @return the beginning pillar of this maze
     */
    public Pillar getBegin() {
        return begin;
    }

    /**
     * Sets the beginning pillar of this maze.
     *
     * @param begin - the beginning position of this maze
     */
    public void setBegin(Position begin){
        if (pillars.containsKey(begin)) {
            this.begin = pillars.get(begin);
        }
    }

    /**
     * Gets the ending pillar of this maze.
     *
     * @return the ending pillar of this maze
     */
    public Pillar getEnd() {
        return end;
    }

    /**
     * Sets the ending pillar of this maze.
     *
     * @param end - the ending position of this maze
     */
    public void setEnd(Position end){
        if (pillars.containsKey(end)) {
            this.end = pillars.get(end);
        }
    }

    /**
     * Gets the pillars of this maze.
     *
     * @return the pillars of this maze
     */
    public Map<Position, Pillar> getPillars() {
        return pillars;
    }

    /**
     * Gets the size of this maze.
     *
     * @return the size of this maze
     */
    public int size(){
        return this.size;
    }

    /**
     * Gets the length of the maze.
     *
     * @return the length of the maze
     */
    public int length(){
        return this.length;
    }

    /**
     * Gets the width of the maze.
     *
     * @return the width of the maze
     */
    public int width(){
        return this.width;
    }

    /**
     * Makes a new position in this maze.
     *
     * @param x - the x position
     * @param y - the y position
     * @return a new position in this maze
     */
    public static Position position(int x, int y){
        return new Position(x, y);
    }

    /**
     * Holds the position of pillars in the maze.
     */
    public static class Position {

        /* the x-coordinate of a pillar. */
        int x = 0;

        /* the y-coordinate of a pillar. */
        int y = 0;

        /**
         * Constructs a position from x and y coordinates.
         *
         * @param x - the x-coordinate in the maze
         * @param y - the y-coordinate in the maze
         */
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Compares this position with another object.
         * The two positions are equal if their
         * x and y coordinates are equivalent.
         *
         * @param o - the object to compare with this position
         * @return whether the two objects are equivalent
         */
        @Override
        public boolean equals(Object o) {
            if (!MazeUtilities.isNull(o) && o instanceof Position) {
                Position p = (Position) o;
                if (this.x == p.x && this.y == p.y) {
                    return true;
                }
            }
            return false;
        }

        /**
         * The hash code for this position is based on
         * x and y coordinates.
         *
         * @return the hash code for this position
         */
        @Override
        public int hashCode() {
            return (x * 23 + y * 55) % 23;
        }
    }

}
