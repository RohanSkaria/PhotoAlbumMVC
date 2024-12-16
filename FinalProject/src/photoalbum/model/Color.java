package photoalbum.model;

/**
 * A tuple to show the rgb of an object.
 *
 * @param r the double value assigned for the red color.
 * @param g the double value assigned for the green color.
 * @param b the double value assigned for the blue color.
 */
public record Color(double r, double g, double b) {

  /**
   * Constructor for record to make sure the values for rgb are not negative.
   *
   * @param r the double representing red.
   * @param g the double representing green.
   * @param b the double representing blue.
   */
  public Color {
    if (r < 0.0 || r > 255.0 || g < 0.0 || g > 255.0 || b < 0.0 || b > 255.0) {
      throw new IllegalArgumentException("Invalid color value");
    }
  }

  @Override
  public String toString() {
    return String.format("(%.1f,%.1f,%.1f)", r, g, b);
  }
}
