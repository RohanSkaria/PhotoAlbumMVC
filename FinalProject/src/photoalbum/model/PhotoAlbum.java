package photoalbum.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * The Concrete class PhotoAlbum that implements an album system.
 */
public class PhotoAlbum implements IPhotoalbum {
  private final Map<String, IShape> shapes;
  private final NavigableSet<Snapshot> snapshotList;

  /**
   * A constructor for the photo album class.
   */
  public PhotoAlbum() {
    shapes = new HashMap<>();
    snapshotList = new TreeSet<>();
  }

  @Override
  public void removeShape(IShape shape) throws IllegalArgumentException {
    if (!shapes.containsKey(shape.getName())) {
      throw new IllegalArgumentException("Shape is not in the Album");
    }
    shapes.remove(shape.getName());
  }

  @Override
  public void addShape(IShape shape) throws IllegalArgumentException {
    if (shapes.containsKey(shape.getName())) {
      throw new IllegalArgumentException("This shape exists");
    }
    shapes.put(shape.getName(), shape);
  }


  @Override
  public IShape getShape(String name) throws IllegalArgumentException {
    if (!shapes.containsKey(name)) {
      throw new IllegalArgumentException("Getter: Shape does not exist");
    }
    return shapes.get(name);
  }

  @Override
  public List<IShape> getAllShapes() {
    return new ArrayList<>(shapes.values());
  }

  @Override
  public void moveShape(String name, Coordinate move) {
    if (!shapes.containsKey(name)) {
      throw new IllegalArgumentException("Transpose: Shape does not exist");
    }
    getShape(name).setPosition(move);
  }

  @Override
  public void resizeShape(String name, double dx, double dy) {
    if (!shapes.containsKey(name)) {
      throw new IllegalArgumentException("Transform: Shape does not exist");
    }
    getShape(name).resize(dx, dy);
  }

  @Override
  public void setColor(String name, Color newColor) {
    if (!shapes.containsKey(name)) {
      throw new IllegalArgumentException("Color: Shape does not exist");
    }
    getShape(name).setColor(newColor);
  }

  @Override
  public void takeSnapshot(String name) {
    snapshotList.add(new Snapshot(name, getAllShapes()));
  }

  @Override
  public List<Snapshot> getSnapshots() {
    return new ArrayList<>(snapshotList);
  }

  @Override
  public void reset() {
    shapes.clear();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Printing Snapshots\n");
    for (Snapshot snapshot : snapshotList) {
      sb.append(snapshot.toString()).append("\n");
    }

    return sb.toString();
  }
}
