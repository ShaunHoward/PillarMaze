package maze;

import java.util.HashMap;
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

    int length = 0;
    int width = 0;
    int size = 0;
    Pillar begin = null;
    Pillar end = null;
    Map<Pillar, Position> pillars = new HashMap<>();

    /**
     * Constructs an empty maze of the length and width
     * then calculates the maze size based on these
     * parameters.
     *
     * @param length - the length of the maze to construct
     * @param width - the width of the maze to construct
     */
    public Maze(int length, int width) {
        this.length = length;
        this.width = width;
        this.size = length * width;
        constructPillars();
    }

    void constructPillars(){
        for (int i = 0; i < length; i++){
            for (int j = 0; j < width; j++){
                Pillar pillar = new Pillar(j, i);
                pillars.put(pillar, new Position(j,i));
            }
        }
    }

    /**
     * Links the start pillar to the end pillar
     * with a unidirectional link.
     *
     * @param start - the pillar to start the link
     * @param end - the pillar to end the link
     */
    void linkPillars(Pillar start, Pillar end){
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
    Pillar getBegin(){
        return begin;
    }

    /**
     * Gets the ending pillar of this maze.
     *
     * @return the ending pillar of this maze
     */
    Pillar getEnd(){
        return end;
    }

    /**
     * Gets the pillars of this maze.
     *
     * @return the pillars of this maze
     */
    Map<Pillar, Position> getPillars(){
        return pillars;
    }

    class Position{
        int x = 0;
        int y = 0;

        Position(int x, int y){
            this.x = x;
            this.y = y;
        }

        void setCoordinates(int x, int y){
            this.x = x;
            this.y = y;
        }

        int getX(){
            return x;
        }

        int getY(){
            return y;
        }
    }

}
