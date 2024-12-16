package photoalbum.model;

/**
 * An object that is-a Abstract Shape.
 */
public class Rectangle extends AbstractShape {

  /**
   * A Constructor for Rectangles. Has the parameters below.
   *
   * @param name String for the name of the Rectangle.
   * @param position Coordinate object for the Rectangle.
   * @param width the double width.
   * @param height the double height.
   * @param color the Object Color for the Rectangle.
   */
  public Rectangle(String name, Coordinate position, double width, double height, Color color)
          throws IllegalArgumentException {
    super(name, position, color, width, height);
  }


  /**
   * Method to get the type of shape.
   *
   * @return A String to show the type of shape this Object is.
   */
  public String getType() {
    return "rectangle";
  }

  /**
   * Method to return a copy of this Rectangle that is also immutable.
   *
   * @return a IShape Object of type Rectangle.
   */
  public IShape copy() {
    return new Rectangle(name, position, firstDimension, secondDimension, color);
  }


  @Override
  public String toString() {
    return String.format("""
            Name: %s
            Type: %s
            Min corner: %s, Width: %.1f, Height: %.1f, Color: %s""",
            name,
            getType(),
            position.toString(),
            firstDimension,
            secondDimension,
            color.toString());
  }
}