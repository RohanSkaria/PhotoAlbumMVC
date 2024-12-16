package photoalbum.model;

/**
 * Interface to create Shape objects for the photo album.
 */
public interface IShape {

  /**
   * Method to get the name of the shape.
   *
   * @return a String name.
   */
  String getName();

  /**
   * Method to get the color of the shape.
   *
   * @return the Color tuple.
   */
  Color getColor();

  /**
   * Method to set the Color to the passed in Color object.
   *
   * @param color of object Color.
   */
  void setColor(Color color);

  /**
   * Method to get the x and y coordinates of the shape.
   *
   * @return the x and y coordinates as a tuple object Coordinate.
   */
  Coordinate getPosition();

  /**
   * Method to set the coordinates of a shape object.
   *
   * @param position the new Coordinates for the Shape object.
   */
  void setPosition(Coordinate position);

  /**
   * Method to get the type of the shape.
   *
   * @return the String for the type of shape.
   */
  String getType();

  /**
   * Method to return an Immutable copy of the shape.
   *
   * @return an IShape object.
   */
  IShape copy();

  /**
   * Methods to change the 2-d parameters for the 2d shape.
   *
   * @param dx the new double for the first horizontal parameter.
   * @param dy the new double for the second vertical parameter.
   */
  void resize(double dx, double dy);

  /**
   * Method to get the first dimension of the shape.
   *
   * @return a double for the first-dimension.
   */
  double getFirstDimension();

  /**
   * Method to get the second dimension of the shape.
   *
   * @return a double for the second-dimension.
   */
  double getSecondDimension();
}
