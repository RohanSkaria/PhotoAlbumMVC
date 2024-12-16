package photoalbum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import photoalbum.model.Color;
import photoalbum.model.Coordinate;
import photoalbum.model.IShape;
import photoalbum.model.Oval;
import photoalbum.model.PhotoAlbum;
import photoalbum.model.Rectangle;
import photoalbum.model.Snapshot;


/**
 * A Test class for the photo album system.
 */
public class PhotoalbumTest {
  private PhotoAlbum model;
  private Rectangle rectangle;
  private Oval oval;
  private Snapshot snapshot;
  private List<IShape> shapes;

  /**
   * Set up for the tests.
   */
  @Before
  public void setUp() {
    model = new PhotoAlbum();
    rectangle = new Rectangle("R1", new Coordinate(100.0, 200.0),
            50.0, 75.0, new Color(1.0, 0.0, 0.0));
    oval = new Oval("O1", new Coordinate(300.0, 400.0), new Color(0.0, 0.0, 1.0),
            30.0, 40.0);
    shapes = new ArrayList<>();
    shapes.add(new Rectangle("R1", new Coordinate(100.0, 200.0),
            50.0, 75.0, new Color(1.0, 0.0, 0.0)));
    shapes.add(new Oval("O1", new Coordinate(300.0, 400.0), new Color(0.0, 0.0, 1.0),
            30.0, 40.0));
    snapshot = new Snapshot("Test snapshot", shapes);
  }

  /**
   * Record Color
   * Tests a bad color for the color record.
   */
  @Test
  public void testBadColor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Color(-0.1, 1.0, .5);
    });
  }

  /**
   * Coordinate Record
   * Testing toString in Coordinate Record.
   */
  @Test
  public void testCoordinate() {
    Coordinate n = new Coordinate(100.0, 200.0);
    assertEquals("(100.0,200.0)", n.toString());
  }

  /**
   * Shape Class
   * Testing shape setPos.
   */
  @Test
  public void testShapePos() {
    Coordinate newPosition = new Coordinate(150.0, 250.0);
    rectangle.setPosition(newPosition);
    assertEquals(newPosition, rectangle.getPosition());
  }

  /**
   * Shape Class
   * Testing shape setPos.
   */
  @Test
  public void testShapeColor() {
    Color newColor = new Color(0.0, 1.0, 0.0);
    rectangle.setColor(newColor);
    assertEquals(newColor, rectangle.getColor());
  }


  /**
   * Shape Class
   * Testing shape resize.
   */
  @Test
  public void testShapeResize() {
    rectangle.resize(1.0, 1.0);
    assertEquals("Name: R1\n"
            + "Type: rectangle\n"
            + "Min corner: (100.0,200.0), Width: 1.0, Height: 1.0, "
            + "Color: (1.0,0.0,0.0)", rectangle.toString());
  }

  /**
   * Shape Class
   * Testing bad shape resize.
   */
  @Test
  public void testBadShapeResize() {
    assertThrows(IllegalArgumentException.class, () -> {
      rectangle.resize(-1.0, 1.0);
    });
  }


  /**
   * PhotoAlbum Class
   * Testing adding shape to album.
   */
  @Test
  public void testAddShape() {
    model.addShape(rectangle);
    assertEquals(rectangle, model.getShape("R1"));
  }


  /**
   * PhotoAlbum Class
   * Testing bad duplicate add.
   */
  @Test
  public void testAddDuplicateShape() {
    model.addShape(rectangle);
    assertThrows(IllegalArgumentException.class, () -> {
      model.addShape(rectangle);
    });
  }


  /**
   * PhotoAlbum Class
   * Testing shape transpose.
   */
  @Test
  public void testMoveShape() {
    model.addShape(rectangle);
    Coordinate newPosition = new Coordinate(150.0, 250.0);
    model.moveShape("R1", newPosition);
    assertEquals(newPosition, model.getShape("R1").getPosition());
  }


  /**
   * PhotoAlbum Class
   * Testing shape color change.
   */
  @Test
  public void testChangeColor() {
    model.addShape(rectangle);
    Color newColor = new Color(0.0, 1.0, 0.0);
    model.setColor("R1", newColor);
    assertEquals(newColor, model.getShape("R1").getColor());
  }


  /**
   * PhotoAlbum Class
   * Testing takeSnapshot method.
   */
  @Test
  public void testTakeSnapshot() {
    model.addShape(rectangle);
    model.addShape(oval);
    model.takeSnapshot("Test snapshot");
    assertEquals(1, model.getSnapshots().size());

    Snapshot snapshot = model.getSnapshots().get(0);
    assertEquals("Test snapshot", snapshot.getDescription());
    assertEquals(2, snapshot.getShapes().size());
  }

  /**
   * PhotoAlbum Class
   * Testing Ordering of Snapshots.
   */
  @Test
  public void testSnapshotOrdering() {
    model.addShape(rectangle);
    model.takeSnapshot("First");
    model.addShape(oval);
    model.takeSnapshot("Second");

    List<Snapshot> snapshots = model.getSnapshots();
    assertEquals(2, snapshots.size());
    assertTrue(snapshots.get(0).getId().compareTo(snapshots.get(1).getId()) < 0);
  }

  /**
   * PhotoAlbum Class
   * Resetting shapes in list.
   */
  @Test
  public void testReset() {
    model.addShape(rectangle);
    model.addShape(oval);
    model.takeSnapshot("Before reset");
    model.reset();

    assertEquals(0, model.getAllShapes().size());
    assertEquals(1, model.getSnapshots().size());
  }

  /**
   * Snapshot Class
   * Test Comparable methods.
   */
  @Test
  public void testSnapshotComparison() {
    Snapshot laterSnapshot = new Snapshot("Later snapshot", shapes);
    assertTrue(snapshot.compareTo(laterSnapshot) < 0);
    assertTrue(laterSnapshot.compareTo(snapshot) > 0);
  }


  /**
   * SnapShot Class
   * Testing deep copy for snapshots.
   */
  @Test
  public void testShapeCopies() {
    List<IShape> snapshotShapes = snapshot.getShapes();
    shapes.clear();

    assertEquals(2, snapshotShapes.size());
  }


  /**
   * Oval Class
   * Testing oval type.
   */
  @Test
  public void testTypeOval() {
    assertEquals("oval", oval.getType());
  }

  /**
   * Oval Class
   * Testing badOval resize.
   */
  @Test
  public void testBadOvalResize() {
    assertThrows(IllegalArgumentException.class, () -> {
      oval.resize(-1.0, 1.0);
    });
  }

  /**
   * Oval Class
   * Testing oval toString.
   */
  @Test
  public void testOvalPrint() {
    assertEquals("Name: O1\n"
            + "Type: oval\n"
            + "Center: (300.0,400.0), X radius: 30.0, Y radius: 40.0,"
            + " Color: (0.0,0.0,1.0)", oval.toString());
  }

  /**
   * PhotoAlbum Class
   * Testing bad setColor method.
   */
  @Test
  public void testBadColorSet() {
    assertThrows(IllegalArgumentException.class, () -> {
      model.setColor("hi", new Color(1,1,1));
    });
  }

  /**
   * PhotoAlbum Class
   * Testing bad resizeShape method.
   */
  @Test
  public void testBadResizeShape() {
    assertThrows(IllegalArgumentException.class, () -> {
      model.resizeShape("hi", 1,1);
    });
  }
}