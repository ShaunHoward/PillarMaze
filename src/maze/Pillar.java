package maze;

import java.util.*;

/**
 * A node represents a pillar in a pillar grid.
 * The node consists of the previously visited node p,
 * the distance from the beginning node b,
 * the heuristic distance to the ending node e,
 * the cost of this node which is the sum of b and e,
 * the number of planks left at this node,
 * the x and y coordinates of this node in the maze grid,
 * whether the node is the beginning or end, and a map of neighbors with
 * neighbors as key and a connected Boolean as the value.
 *
 * @author Shaun Howard
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

    /* nMap, map of neighbor nodes with key as neighbor and connected boolean as value */
    Map<Pillar, Boolean> neighbors = new HashMap<>();

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
    public Pillar getPrevious() {
        return p;
    }

    /**
     * Sets the previously visited pillar.
     *
     * @param p - the previously visited pillar
     */
    public void setPrevious(Pillar p) {
        this.p = p;
    }

    /**
     * Gets the distance from the beginning pillar.
     *
     * @return the distance from the beginning pillar
     */
    public float getDistanceFromBegin() {
        return b;
    }

    /**
     * Sets the distance from the beginning pillar.
     *
     * @param b - the distance from the beginning pillar
     */
    public void setDistanceFromBegin(float b) {
        this.b = b;
    }

    /**
     * Gets the heuristic distance to the end pillar.
     *
     * @return the heuristic distance to the end pillar
     */
    public float getHeuristic() {
        return e;
    }

    /**
     * Sets the heuristic distance to the end pillar.
     *
     * @param e - the heuristic distance to the end pillar
     */
    public void setHeuristic(float e) {
        this.e = e;
    }

    /**
     * Gets the cost of this pillar.
     *
     * @return the cost of this pillar
     */
    public float getCost() {
        return c;
    }

    /**
     * Sets the cost of this pillar.
     *
     * @param c - the cost of this pillar
     */
    public void setCost(float c) {
        this.c = c;
    }

    /**
     * Gets the planks left at this pillar.
     *
     * @return the planks left at this pillar
     */
    public int getPlanksLeft() {
        return n;
    }

    /**
     * Sets the planks left at this pillar.
     *
     * @param n - the planks left at this pillar
     */
    public void setPlanksLeft(int n) {
        this.n = n;
    }

    /**
     * Gets the x-coordinate of this pillar.
     *
     * @return the x-coordinate of this pillar
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this pillar.
     *
     * @return the y-coordinate of this pillar
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the neighbor pillars of this pillar.
     *
     * @return the neighbor pillars of this pillar
     */
    public Map<Pillar, Boolean> getNeighbors() {
        return neighbors;
    }

    /**
     * Gets a list of neighbors, which are either
     * connected or disconnected as determined by the
     * entered boolean value.
     *
     * @param connected - whether to get connected neighbors
     * @return connected or disconnected neighbors of this pillar
     */
    public List<Pillar> getNeighborList(boolean connected) {
        // The list of neighbors of this pillar.
        List<Pillar> neighborList = new ArrayList<>(neighbors.keySet());
        Iterator<Pillar> listItr = neighborList.iterator();
        Pillar curr = null;

        // Remove any neighbors not matching connected boolean in map.
        while (listItr.hasNext()) {
            curr = listItr.next();
            if (neighbors.containsKey(curr) && neighbors.get(curr).booleanValue() != connected) {
                listItr.remove();
            }
        }
        return neighborList;
    }

    /**
     * Adds a neighbor pillar to this pillar.
     *
     * @param neighbor    - the neighbor pillar to add
     * @param isConnected - whether the neighbor is connected
     */
    public void addNeighbor(Pillar neighbor, boolean isConnected) {
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
     * Gets the coordinates of this pillar as a string.
     *
     * @return - the coordinates <x,y></x,y> of this pillar as a string
     */
    public String getCoordinateString() {
        return "<" + getX() + ", " + getY() + ">";
    }

    /**
     * Determines whether two pillars are the same based on their costs,
     * x, and y coordinates.
     *
     * @param pillar - the pillar to compare with this pillar
     * @return whether the the pillars are the same
     */
    public boolean samePillars(Pillar pillar) {
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
    public boolean equals(Object o) {

        if (!MazeUtilities.isNull(o) && o instanceof Pillar) {
            Pillar p = (Pillar) o;
            if (samePillars(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Hash code for this pillar. Generated based on
     * x, y coordinates and cost of this pillar.
     *
     * @return the hash code of this pillar
     */
    @Override
    public int hashCode() {
        int result = (c != +0.0f ? Float.floatToIntBits(c) : 0);
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }
}
