package Player;

/**
 * The Scorable interface defines a contract for objects that can be scored.
 * It provides methods to retrieve the name of the player or entity and
 * to get the current score or points associated with that player or entity.
 */
public interface Scorable {
    /**
     * Returns the name of the player or entity.
     *
     * @return the name of the player or entity as a String.
     */
    String getName();
    
    /**
     * Returns the current points or score of the player or entity.
     *
     * @return the points or score as an integer.
     */
    int getPoints();
}
