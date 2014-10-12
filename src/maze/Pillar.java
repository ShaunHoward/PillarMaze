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
 *  whether the node is the end, and a map of neighbors with
 *  neighbors as key and a connected Boolean as the value.
 *
 *  @author Shaun Howard
 */
public class Pillar {

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


    Pillar getPrevious() {
        return p;
    }

    void setPrevious(Pillar p) {
        this.p = p;
    }

    float getDistanceFromBegin() {
        return b;
    }

    void setDistanceFromBegin(float b) {
        this.b = b;
    }

    float getHeuristic() {
        return e;
    }

    void setHeuristic(float e) {
        this.e = e;
    }

    float getCost() {
        return c;
    }

    void setCost(float c) {
        this.c = c;
    }

    int getPlanksLeft() {
        return n;
    }

    void setPlanksLeft(int n) {
        this.n = n;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    boolean isEnd() {
        return end;
    }

    void setEnd(boolean end) {
        this.end = end;
    }

    Map<Pillar, Boolean> getNeighbors() {
        return neighbors;
    }

    void setNeighbors(Map<Pillar, Boolean> neighbors) {
        this.neighbors = neighbors;
    }

    void addNeighbor(Pillar neighbor, boolean isConnected) {
        this.neighbors.put(neighbor, isConnected);
    }
}
