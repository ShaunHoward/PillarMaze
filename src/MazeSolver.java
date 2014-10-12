/**
 * Created by shaun on 10/12/14.
 */
public class MazeSolver {
/*
    //P* algorithm
    call input graph 'maze'
    call input length 'size'
            if the size of maze is not equal to size
    throw an invalid size exception
    end if
    initialize the set of expanded nodes 'E'
    initialize a priority queue of nodes 'N' sorted in non-decreasing order by cost, c,
    the sum of heuristic and number of times moved
    if maze size is greater than 0
    add the beginning node of maze to E
    add the beginning node to N with c=0, n=1, e=distance to end from beginning
    end if
            while N is not empty
    extract lowest cost node 'v' from N
    if v is the end node of maze
    return shortest path to v
    end if
    find the connected neighbor nodes of v
    for each connected node 'c'
            if c does not exist in E
    c.p . v
    c.b . v.b + 1
    c.e . distance to end from c
    c.n . v.n
    c.c . c.b + c.e
    add c to E
    add c to N
    end if
    end

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
    return nil

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

    // connected neighbors of node
    call the current node 'c'
    initialize a list of connected neighbors
    for each node in c.nMap
    if node has true value in nMap
    add node to list of connected neighbors
    end if
    end
    return list of connected neighbors

    // unconnected neighbors of node
    call the current node 'c'
    initialize a list of unconnected neighbors
    for each node in c.nMap
    if node has false value in nMap
    add node to list of unconnected neighbors
    end if
    end
    return list of unconnected neighbors */
}
