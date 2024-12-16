package photoalbum.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A concrete class Snapshot that acts as a freeze-frame of the model state of the album.
 */
public class Snapshot implements Comparable<Snapshot> {
  private final String id;
  private final String timestamp;
  private final String description;
  private final List<IShape> shapes;

  /**
   * A constructor for a snapshot of the album.
   *
   * @param description a description for the current snapshot.
   * @param shapes the list of shapes inside this snapshot.
   */
  public Snapshot(String description, List<IShape> shapes) {
    this.id = java.time.Instant.now().toString();
    this.timestamp = java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    this.description = description;
    this.shapes = shapes.stream().map(IShape::copy).collect(Collectors.toList());
  }

  /**
   * Method to get the ID of the snapshot.
   *
   * @return a String representing the ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Method to get the timestamp of the snapshot.
   *
   * @return a String for the Timestamp.
   */
  public String getTimestamp() {
    return timestamp;
  }

  /**
   * Method to get a description for the snapshot.
   *
   * @return a String for the snapshot description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * A method to get the list of shapes in the snapshot.
   *
   * @return a List of shapes that is unmodifiable.
   */
  public List<IShape> getShapes() {
    return Collections.unmodifiableList(shapes);
  }

  @Override
  public int compareTo(Snapshot o) {
    return id.compareTo(o.id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Snapshot snapshot)) {
      return false;
    }
    return id.equals(snapshot.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Printing Snapshots\n");
    sb.append("Snapshot ID: ").append(id).append("\n");
    sb.append("Timestamp: ").append(timestamp).append("\n");
    sb.append("Description: ").append(description).append("\n");
    sb.append("Shape Information:\n");

    for (IShape shape : shapes) {
      sb.append(shape.toString()).append("\n\n");
    }

    return sb.toString();
  }
}

