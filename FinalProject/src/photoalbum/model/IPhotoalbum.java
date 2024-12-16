package photoalbum.model;

import java.util.List;

/**
 * An interface for photo album Objects.
 */
public interface IPhotoalbum {
  /**
   * Add a Shape object to a list of shapes.
   *
   * @param shape a shape object that is being added.
   */
  void addShape(IShape shape);

  /**
   * Removes the shape in the list.
   *
   * @param shape the shape object being removed.
   */
  void removeShape(IShape shape);

  /**
   *  Get a shape in the list.
   *
   * @param name a String name to represent the shape being looked for.
   * @return a Shape that has the same name.
   */
  IShape getShape(String name);

  /**
   * Method to return an unmodifiable list of shapes in the album.
   *
   * @return an unmodifiable List of all shapes.
   */
  List<IShape> getAllShapes();

  /**
   * To move a shape in the album to new coordinates.
   *
   * @param name the name of the shape.
   * @param pos the new coordinates of the shape.
   */
  void moveShape(String name, Coordinate pos);

  /**
   * Method to set a new color to the shape.
   *
   * @param name the name of the shape.
   * @param color the new color for the shape.
   */
  void setColor(String name, Color color);

  /**
   * A current snapshot of the shapes in the list.
   *
   * @param s the message for the current snapshot.
   */
  void takeSnapshot(String s);

  /**
   * Method to return an arraylist of the snapshots.
   *
   * @return a List with Snapshot objects.
   */
  List<Snapshot> getSnapshots();

  /**
   * Method to resize the dimensions of the Shape.
   *
   * @param name the name of the shape being resized.
   * @param x the double for the horizontal resize.
   * @param y the double for the vertical resize.
   */
  void resizeShape(String name, double x, double y);

  /**
   * Method to clear the shapes map.
   */
  void reset();

}
