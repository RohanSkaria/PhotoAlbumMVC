package photoalbum.model;

/**
 * Class to represent a general abstract shape and default methods.
 */
abstract class AbstractShape implements IShape {
  protected String name;
  protected Color color;
  protected Coordinate position;
  protected double firstDimension;
  protected double secondDimension;

  /**
   * A constructor for the AbstractShape.
   *
   * @param name the String name for the shape.
   * @param color the Color Object for the shape.
   * @param position the Position Object for the shape.
   */
  public AbstractShape(String name, Coordinate position, Color color, double firstDimension, double secondDimension)
          throws IllegalArgumentException {
    if (name == null || color == null || position == null || firstDimension < 0 || secondDimension < 0) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.color = color;
    this.position = position;
    this.firstDimension = firstDimension;
    this.secondDimension = secondDimension;
  }


  @Override
  public String getName() {
    return name;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public Coordinate getPosition() {
    return position;
  }

  @Override
  public void setPosition(Coordinate position) {
    this.position = position;
  }

  @Override
  public double getFirstDimension() {
    return firstDimension;
  }

  @Override
  public double getSecondDimension() {
    return secondDimension;
  }

  @Override
  public void resize(double firstDimension, double secondDimension) throws IllegalArgumentException {
    if (firstDimension < 0 || secondDimension < 0) {
      throw new IllegalArgumentException();
    }
    this.firstDimension = firstDimension;
    this.secondDimension = secondDimension;
  }

}
