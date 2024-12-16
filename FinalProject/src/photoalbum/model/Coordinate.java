package photoalbum.model;

/**
 * Tuple for the position of a object.
 *
 * @param x the double x coordinate for an object.
 * @param y the double y coordinate for an object.
 */
public record Coordinate(double x, double y) {

  @Override
  public String toString() {
    return String.format("(%.1f,%.1f)", x, y);
  }
}
