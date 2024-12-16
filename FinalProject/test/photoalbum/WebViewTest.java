package photoalbum;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import photoalbum.model.Color;
import photoalbum.model.Coordinate;
import photoalbum.model.IShape;
import photoalbum.model.Rectangle;
import photoalbum.model.Oval;
import photoalbum.model.Snapshot;
import photoalbum.views.WebView;

/**
 * Test Class for the webview.
 */
public class WebViewTest {

  /**
   * Temp folder for test file output.
   */
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  private WebView webView;
  private List<Snapshot> testSnapshots;

  /**
   * Set up for the test env.
   * Creating a Rectangle, Snapshot, Webview, and a list for the shapes.
   */
  @Before
  public void setUp() {
    IShape rect = new Rectangle("test-rect",
            new Coordinate(0, 0),
            100,
            50,
            new Color(255, 0, 0));
    List<IShape> shapes = new ArrayList<>();
    shapes.add(rect);

    Snapshot snapshot = new Snapshot("Test snapshot", shapes);
    testSnapshots = new ArrayList<>();
    testSnapshots.add(snapshot);

    webView = new WebView(testSnapshots);
  }

  /**
   * Tests the generic webpage creation.
   *
   * @throws IOException thrown if the file operation fails.
   */
  @Test
  public void testGenerateWebPage() throws IOException {
    File outputFile = tempFolder.newFile("test-output.html");

    webView.generateWebPage(outputFile.getPath());

    assertTrue("Output file should exist", outputFile.exists());

    try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
      String content = reader.lines().collect(Collectors.joining("\n"));
      assertTrue("Should contain DOCTYPE", content.contains("<!DOCTYPE html>"));
      assertTrue("Should contain snapshot ID",
              content.contains(testSnapshots.get(0).getId()));
      assertTrue("Should contain SVG content", content.contains("<svg"));
    }
  }

  /**
   * Tests the getter method for snapshots.
   */
  @Test
  public void testGetSnapshots() {
    List<Snapshot> retrievedSnapshots = webView.getSnapshots();
    assertEquals("Should return same snapshots list",
            testSnapshots,
            retrievedSnapshots);
    assertEquals("Should have correct number of snapshots",
            1,
            retrievedSnapshots.size());
  }

  /**
   * Test for the svg dimension.
   *
   * @throws IOException in case the file is corrupted.
   */
  @Test
  public void testSVGDimensions() throws IOException {
    testSnapshots.add(new Snapshot("Test", new ArrayList<>()));
    webView = new WebView(testSnapshots);

    File output = tempFolder.newFile("dimensions.html");
    webView.generateWebPage(output.getPath());

    String content = readFile(output);
    assertTrue(content.contains("<svg width=\"800\" height=\"800\""));
  }

  /**
   * Tests for proper styling.
   *
   * @throws IOException thrown if the file can't be read.
   */
  @Test
  public void testStyleContent() throws IOException {
    testSnapshots.add(new Snapshot("Test", new ArrayList<>()));
    webView = new WebView(testSnapshots);

    File output = tempFolder.newFile("style.html");
    webView.generateWebPage(output.getPath());

    String content = readFile(output);
    assertTrue(content.contains("font-family: Arial, sans-serif"));
    assertTrue(content.contains("max-width: 1000px"));
    assertTrue(content.contains("background-color: #f4f4f4"));
  }

  /**
   * Tests for multiple shapes.
   *
   * @throws IOException thrown if the file is bad.
   */
  @Test
  public void testComplexShapes() throws IOException {
    List<IShape> shapes = new ArrayList<>();
    shapes.add(new Rectangle("background", new Coordinate(0,0), 800, 800, new Color(255,255,255)));
    shapes.add(new Rectangle("rect1", new Coordinate(10,10), 100, 100, new Color(255,0,0)));
    shapes.add(new Oval("oval1", new Coordinate(200,200), new Color(0,255,0), 150, 100));
    shapes.add(new Rectangle("rect2", new Coordinate(400,400), 50, 50, new Color(0,0,255)));

    testSnapshots.add(new Snapshot("Complex Test", shapes));
    webView = new WebView(testSnapshots);

    File output = tempFolder.newFile("complex.html");
    webView.generateWebPage(output.getPath());

    String content = readFile(output);
    assertTrue(content.contains("<rect"));
    assertTrue(content.contains("<ellipse"));
    assertTrue(content.contains("rgb(255,0,0)"));
    assertTrue(content.contains("rgb(0,255,0)"));
  }

  /**
   * Tests for the snapshot info.
   *
   * @throws IOException if the file is unreadable.
   */
  @Test
  public void testSnapshotInfo() throws IOException {
    List<IShape> shapes = new ArrayList<>();
    shapes.add(new Rectangle("test", new Coordinate(0,0), 100, 100, new Color(255,0,0)));
    Snapshot snapshot = new Snapshot("Test Description", shapes);
    testSnapshots.add(snapshot);
    webView = new WebView(testSnapshots);

    File output = tempFolder.newFile("metadata.html");
    webView.generateWebPage(output.getPath());

    String content = readFile(output);
    assertTrue(content.contains(snapshot.getId()));
    assertTrue(content.contains(snapshot.getTimestamp().toString()));
    assertTrue(content.contains("Test Description"));
  }

  /**
   * Tests for special characters in input file.
   *
   * @throws IOException if the file is unreadable.
   */
  @Test
  public void testHtmlSpecialCharacters() throws IOException {
    List<IShape> shapes = new ArrayList<>();
    shapes.add(new Rectangle("test & shape", new Coordinate(0,0), 100, 100, new Color(255,0,0)));
    testSnapshots.add(new Snapshot("Test < > \" ' &", shapes));
    webView = new WebView(testSnapshots);

    File output = tempFolder.newFile("special.html");
    webView.generateWebPage(output.getPath());

    String content = readFile(output);
    assertTrue(content.contains("&amp;"));
    assertTrue(content.contains("&lt;"));
    assertTrue(content.contains("&gt;"));
    assertTrue(content.contains("&quot;"));
    assertTrue(content.contains("&#x27;"));
  }


  /**
   * Tests for the shape coordinate and size accuracy.
   *
   * @throws IOException if the file is unreadable.
   */
  @Test
  public void testShapePrecision() throws IOException {
    List<IShape> shapes = new ArrayList<>();
    shapes.add(new Rectangle("test", new Coordinate(10.123, 20.456), 100.789, 200.012, new Color(255,0,0)));
    testSnapshots.add(new Snapshot("Test", shapes));
    webView = new WebView(testSnapshots);

    File output = tempFolder.newFile("precision.html");
    webView.generateWebPage(output.getPath());

    String content = readFile(output);
    assertTrue(content.contains("10.1"));
    assertTrue(content.contains("20.5"));
    assertTrue(content.contains("100.8"));
    assertTrue(content.contains("200.0"));
  }

  /**
   * Tests for modifying snapshots.
   */
  @Test
  public void testUnmodifiableSnapshots() {
    List<IShape> shapes = new ArrayList<>();
    shapes.add(new Rectangle("test", new Coordinate(0,0), 100, 100, new Color(255,0,0)));
    testSnapshots.add(new Snapshot("Test", shapes));
    webView = new WebView(testSnapshots);

    List<Snapshot> retrievedSnapshots = webView.getSnapshots();
    try {
      retrievedSnapshots.add(new Snapshot("New", new ArrayList<>()));
      fail("List should be unmodifiable");
    } catch (UnsupportedOperationException e) {
      ;
    }
  }

  /**
   * Helper method to read a file.
   *
   * @param file tge file being read.
   * @return the String for the lines.
   * @throws IOException if the file is unreadable.
   */
  private String readFile(File file) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      return reader.lines().collect(Collectors.joining("\n"));
    }
  }
}