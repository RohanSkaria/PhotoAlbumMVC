package photoalbum.model;

/**
 * Concrete class Oval that is-a AbstractShape.
 */
public class Oval extends AbstractShape {


  /**
   * A Constructor for Oval. Has the parameters below.
   *
   * @param name String for the name of the Oval.
   * @param position Coordinate object for the Oval.
   * @param firstDimension the double x-radius.
   * @param secondDimension the double y-radius.
   * @param color the Object Color for the Oval.
   */
  public Oval(String name, Coordinate position, Color color, double firstDimension, double secondDimension)
          throws IllegalArgumentException {
    super(name, position, color, firstDimension, secondDimension);
  }

  /**
   * Method to get the type of shape.
   *
   * @return A String to show the type of shape this Object is.
   */
  public String getType() {
    return "oval";
  }

  /**
   * Method to return a copy of this Oval that is also immutable.
   *
   * @return a IShape Object of type Oval.
   */
  public IShape copy() {
    return new Oval(name, position, color, firstDimension, secondDimension);
  }


  @Override
  public String toString() {
    return String.format("""
            Name: %s
            Type: %s
            Center: %s, X radius: %.1f, Y radius: %.1f, Color: %s""",
            name,
            getType(),
            position.toString(),
            firstDimension,
            secondDimension,
            color.toString());
  }

}
