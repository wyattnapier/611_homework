package Games.Core;

public interface Tile {
    /**
     * Check if the tile is in its correct final position
     * 
     * @return true if in correct position, false otherwise
     */
    public abstract boolean isComplete();

    /**
     * @return display value of tile
     */
    public abstract String toString();
}