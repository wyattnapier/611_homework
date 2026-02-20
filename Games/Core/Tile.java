package Games.Core;

/**
 * this interface is a blueprint of basic methods that tiles need
 */
public interface Tile {
    /**
     * Check if the tile is in its correct final position
     * 
     * @return true if in correct position/complete, false otherwise
     */
    public abstract boolean isComplete();

    /**
     * @return display value of tile
     */
    public abstract String toString();
}