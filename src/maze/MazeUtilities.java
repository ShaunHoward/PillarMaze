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
}
