package maze;

import java.util.HashMap;
import java.util.Map;

/**
 *  A node represents a pillar in a pillar grid.
 *  The node consists of the previously visited node p,
 *  the distance from the beginning node b,
 *  the heuristic distance to the ending node e,
 *  the cost of this node which is the sum of b and e,
 *  the number of planks left at this node,
 *  the x and y coordinates of this node in the maze grid,
 *  whether the node is the beginning or end, and a map of neighbors with
 *  neighbors as key and a connected Boolean as the value.
 *
 *  @author Shaun Howard
 */
public class Pillar implements Comparable<Pillar> {

    /* p, previously visited node */
    Pillar p = null;

    /* b, distance from the beginning node */
    float b = 0;

    /* e, heuristic distance to ending node */
    float e = 0;

    /* c, cost of this node */
    float c = 0;

    /* n, number of planks left at this node */
    int n = 0;

    /* x, y, coordinates of this node in a grid */
    int x = 0, y = 0;

    /* begin, boolean to tell if this node is the beginning node */
    boolean begin = false;

    /* end, boolean to tell if this node is the end node */
    boolean end = false;

    /* nMap, map of neighbor nodes with key as neighbor and connected boolean as value */
    Map<Pillar, Boolean> neighbors = new HashMap<>();

    /**
     * Constructs a pillar from its x and y coordinates and
     * the previously visited pillar.
     *
     * @param prev - the previously visited pillar
     * @param x - the x-coordinate on the grid
     * @param y - the y-coordinate on the grid
     */
    public Pillar(Pillar prev, int x, int y) {
        this.p = prev;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a pillar from its x and y coordinates.
     *
     * @param x - the x-coordinate on the grid
     * @param y - the y-coordinate on the grid
     */
    public Pillar(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Gets the previously visited pillar.
     *
     * @return the previously visited pillar
     */
    Pillar getPrevious() {
        return p;
    }

    /**
     * Sets the previously visited pillar.
     *
     * @param p - the previously visited pillar
     */
    void setPrevious(Pillar p) {
        this.p = p;
    }

    /**
     * Gets the distance from the beginning pillar.
     *
     * @return the distance from the beginning pillar
     */
    float getDistanceFromBegin() {
        return b;
    }

    /**
     * Sets the distance from the beginning pillar.
     *
     * @param b - the distance from the beginning pillar
     */
    void setDistanceFromBegin(float b) {
        this.b = b;
    }

    /**
     * Gets the heuristic distance to the end pillar.
     *
     * @return the heuristic distance to the end pillar
     */
    float getHeuristic() {
        return e;
    }

    /**
     * Sets the heuristic distance to the end pillar.
     *
     * @param e - the heuristic distance to the end pillar
     */
    void setHeuristic(float e) {
        this.e = e;
    }

    /**
     * Gets the cost of this pillar.
     *
     * @return the cost of this pillar
     */
    float getCost() {
        return c;
    }

    /**
     * Sets the cost of this pillar.
     *
     * @param c - the cost of this pillar
     */
    void setCost(float c) {
        this.c = c;
    }

    /**
     * Gets the planks left at this pillar.
     *
     * @return the planks left at this pillar
     */
    int getPlanksLeft() {
        return n;
    }

    /**
     * Sets the planks left at this pillar.
     *
     * @param n - the planks left at this pillar
     */
    void setPlanksLeft(int n) {
        this.n = n;
    }

    /**
     * Gets the x-coordinate of this pillar.
     *
     * @return the x-coordinate of this pillar
     */
    int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of this pillar in a grid.
     *
     * @param x - the x-coordinate on the grid
     */
    void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of this pillar.
     *
     * @return the y-coordinate of this pillar
     */
    int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of this pillar in a grid.
     *
     * @param y - the y-coordinate on the grid
     */
    void setY(int y) {
        this.y = y;
    }

    /**
     * Returns whether this pillar is the begin pillar.
     *
     * @return whether this pillar is the begin pillar
     */
    public boolean isBegin() {
        return begin;
    }

    /**
     * Set whether this pillar is the beginning pillar.
     *
     * @param begin - whether this pillar is the begin pillar
     */
    public void setBegin(boolean begin) {
        this.begin = begin;
    }

    /**
     * Returns whether this pillar is the end pillar.
     *
     * @return whether this pillar is the end pillar
     */
    boolean isEnd() {
        return end;
    }

    /**
     * Set whether this pillar is the ending pillar.
     *
     * @param end - whether this pillar is the end pillar
     */
    void setEnd(boolean end) {
        this.end = end;
    }

    /**
     * Gets the neighbor pillars of this pillar.
     *
     * @return the neighbor pillars of this pillar
     */
    Map<Pillar, Boolean> getNeighbors() {
        return neighbors;
    }

    /**
     * Set the neighbor pillars of this pillar.
     *
     * @param neighbors - the neighbor pillars of this pillar
     */
    void setNeighbors(Map<Pillar, Boolean> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * Adds a neighbor pillar to this pillar.
     *
     * @param neighbor - the neighbor pillar to add
     * @param isConnected - whether the neighbor is connected
     */
    void addNeighbor(Pillar neighbor, boolean isConnected) {
        if (!MazeUtilities.isNull(neighbor)) {
            this.neighbors.put(neighbor, isConnected);
        }
    }

    /**
     * Compares pillars by their cost. Utilizes
     * the Java compareTo() for Floats.
     *
     * @param pillar - the pillar to compare to this pillar
     * @return the comparison of the pillars by cost
     */
    @Override
    final public int compareTo(Pillar pillar) {
        Float thisCost = new Float(this.getCost());
        Float pillarCost = new Float(pillar.getCost());
        return thisCost.compareTo(pillarCost);
    }

    /**
     * Determines whether two pillars are the same based on their costs,
     * x, and y coordinates.
     *
     * @param pillar - the pillar to compare with this pillar
     * @return whether the the pillars are the same
     */
    boolean samePillars(Pillar pillar){
        if (this.getX() == pillar.getX()
                && this.getY() == pillar.getY()
                && this.getCost() == pillar.getCost()) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether two pillars are equal based on their costs,
     * x, and y coordinates.
     *
     * @param o - the object to compare with this pillar
     * @return whether the input object is equivalent to this pillar
     */
    @Override
    public boolean equals(Object o){

        if (!MazeUtilities.isNull(o) && o instanceof Pillar) {
            Pillar p = (Pillar)o;
            if (samePillars(p)){
                return true;
            }
        }
        return false;
    }
}
