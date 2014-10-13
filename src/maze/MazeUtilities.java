package maze;

/**
 * Utilities for the pillar maze.
 *
 * @author Shaun Howard
 */
public class MazeUtilities {

    /**
     * Throws an exception when any of the input objects is null.
     *
     * @param objs - the objects to check for null
     * @throws Exception - thrown when any object is null
     */
    public static void throwExceptionWhenNull(Object... objs) throws Exception {
        for (Object obj : objs) {
            if (obj == null) {
                throw new Exception("An object is null.");
            }
        }
    }

    /**
     * Returns true when any of the input objects is null.
     *
     * @param objs - the objects to check for null
     * @return true when any given objects are null
     */
    public static boolean isNull(Object... objs){
        for (Object obj : objs) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the actual size is different from the expected size.
     * If they differ, an exception is thrown with a message that the
     * maze size was unequal to the expected size.
     *
     * @param actual - the actual maze size
     * @param expected - the expected maze size
     * @throws Exception - thrown when the actual maze size is unequal to
     * the expected maze size
     */
    public static void throwExceptionWhenDifferent(int actual, int expected) throws Exception {
        /* if the size of maze is not equal to size */
        if (actual != expected) {
            /* throw an invalid size exception */
            throw new Exception("Maze size was unequal to expected size.");
        }
    }
}
