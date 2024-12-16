package photoalbum;

import org.junit.Before;
import org.junit.Test;


import java.util.List;

import photoalbum.controller.InputProcessor;
import photoalbum.model.Color;
import photoalbum.model.Coordinate;
import photoalbum.model.IPhotoalbum;
import photoalbum.model.IShape;
import photoalbum.model.PhotoAlbum;
import photoalbum.model.Snapshot;
import static org.junit.Assert.*;

/**
 * Tests for the Input processor - this will also test the classes in controller.
 */
public class InputProcessorTest {
  private IPhotoalbum album;
  private InputProcessor processor;


  /**
   * General set up for all the tests.
   */
  @Before
  public void setUp() {
    album = new PhotoAlbum();
    processor = new InputProcessor(album);
  }

  /**
   * Tests the shape creation.
   */
  @Test
  public void testShapeCommandCaseInsensitivity() {
    processor.processInput("SHAPE rect1 RECTANGLE 0 0 100 50 255 0 0");
    assertNotNull(album.getShape("rect1"));
  }


  /**
   * Tests creating a snapshot with a description.
   */
  @Test
  public void testSnapshotWithDescription() {
    processor.processInput("shape rect1 rectangle 0 0 100 50 255 0 0");
    processor.processInput("snapshot Test with multiple words description");

    List<Snapshot> snapshots = album.getSnapshots();
    assertEquals("Test with multiple words description",
            snapshots.get(0).getDescription());
  }


  /**
   * Tests multiple shape creations at once.
   */
  @Test
  public void testProcessMultipleCommands() {
    processor.processInput("shape rect1 rectangle 0 0 100 50 255 0 0");
    processor.processInput("move rect1 10 10");
    processor.processInput("color rect1 0 255 0");
    processor.processInput("resize rect1 200 100");

    IShape shape = album.getShape("rect1");
    assertEquals(new Coordinate(10, 10), shape.getPosition());
    assertEquals(new Color(0, 255, 0), shape.getColor());
    assertEquals(200, shape.getFirstDimension(), 0.001);
    assertEquals(100, shape.getSecondDimension(), 0.001);
  }

  /**
   * Tests shape creation and proper coordinate and position.
   */
  @Test
  public void testProcessShapeCommand() {
    String input = "shape rect1 rectangle 0 0 100 50 255 0 0";
    processor.processInput(input);

    IShape shape = album.getShape("rect1");
    assertNotNull("Shape should be created", shape);
    assertEquals("Shape type should match", "rectangle", shape.getType());
    assertEquals("Shape position should match",
            new Coordinate(0, 0),
            shape.getPosition());
  }

  @Test
  public void testProcessMoveCommand() {
    // First create a shape
    processor.processInput("shape rect1 rectangle 0 0 100 50 255 0 0");
    // Then move it
    processor.processInput("move rect1 100 100");

    IShape shape = album.getShape("rect1");
    assertEquals("Shape should be moved",
            new Coordinate(100, 100),
            shape.getPosition());
  }

  /**
   * Tests the color during creation.
   */
  @Test
  public void testProcessColorCommand() {
    processor.processInput("shape rect1 rectangle 0 0 100 50 255 0 0");
    processor.processInput("color rect1 0 255 0");

    IShape shape = album.getShape("rect1");
    assertEquals("Color should be updated",
            new Color(0, 255, 0),
            shape.getColor());
  }

  /**
   * Tests the creation of a snapshot object.
   */
  @Test
  public void testProcessSnapshotCommand() {
    processor.processInput("shape rect1 rectangle 0 0 100 50 255 0 0");
    processor.processInput("snapshot First snapshot");

    List<Snapshot> snapshots = album.getSnapshots();
    assertEquals("Should create a snapshot", 1, snapshots.size());
    assertEquals("Snapshot description should match",
            "First snapshot",
            snapshots.get(0).getDescription());
  }

  /**
   * Tests the creation of an oval.
   */
  @Test
  public void testProcessOvalShape() {
    processor.processInput("shape oval1 oval 50 50 100 100 0 255 0");

    IShape shape = album.getShape("oval1");
    assertNotNull("Oval shape should be created", shape);
    assertEquals("Shape type should be oval", "oval", shape.getType());
    assertEquals("Oval position should match", new Coordinate(50, 50), shape.getPosition());
    assertEquals("Oval color should match", new Color(0, 255, 0), shape.getColor());
  }

  /**
   * Tests multiple snapshots.
   */
  @Test
  public void testMultipleShapesSnapshot() {
    processor.processInput("shape rect1 rectangle 0 0 100 50 255 0 0");
    processor.processInput("shape oval1 oval 50 50 100 100 0 255 0");
    processor.processInput("snapshot Two shapes");

    List<Snapshot> snapshots = album.getSnapshots();
    assertEquals(1, snapshots.size());
    assertEquals(2, snapshots.get(0).getShapes().size());
  }


  /**
   * Tests resizing a shape.
   */
  @Test
  public void testResizeShape() {
    processor.processInput("shape rect1 rectangle 0 0 100 50 255 0 0");
    processor.processInput("resize rect1 200 75");

    IShape shape = album.getShape("rect1");
    assertEquals(200, shape.getFirstDimension(), 0.001);
    assertEquals(75, shape.getSecondDimension(), 0.001);
  }

  /**
   * Test multiple snapshots.
   */
  @Test
  public void testMultipleSnapshots() {
    processor.processInput("shape rect1 rectangle 0 0 100 50 255 0 0");
    processor.processInput("snapshot First");
    processor.processInput("move rect1 50 50");
    processor.processInput("snapshot After move");
    processor.processInput("color rect1 0 0 255");
    processor.processInput("snapshot After color");

    List<Snapshot> snapshots = album.getSnapshots();
    assertEquals(3, snapshots.size());
    assertEquals("First", snapshots.get(0).getDescription());
    assertEquals("After move", snapshots.get(1).getDescription());
    assertEquals("After color", snapshots.get(2).getDescription());
  }


  /**
   * Tests overlapping shapes.
   */
  @Test
  public void testShapeOverlap() {
    processor.processInput("shape rect1 rectangle 0 0 100 50 255 0 0");
    processor.processInput("shape rect2 rectangle 50 25 100 50 0 255 0");

    IShape shape1 = album.getShape("rect1");
    IShape shape2 = album.getShape("rect2");

    assertTrue("Shapes should exist", shape1 != null && shape2 != null);
  }


  /**
   * Tests an empty snapshot.
   */
  @Test
  public void testEmptySnapshot() {
    processor.processInput("snapshot Empty album");

    List<Snapshot> snapshots = album.getSnapshots();
    assertEquals(1, snapshots.size());
    assertEquals(0, snapshots.get(0).getShapes().size());
  }
}