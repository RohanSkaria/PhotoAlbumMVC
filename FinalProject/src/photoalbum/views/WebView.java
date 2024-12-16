package photoalbum.views;

import photoalbum.model.IShape;
import photoalbum.model.Snapshot;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

/**
 * Constructor for the HTML view of the photo album. Has the list of snapshots that will be shown.
 */
public class WebView implements IWebView {
  private final List<Snapshot> snapshots;
  private static final int SVG_WIDTH = 800;
  private static final int SVG_HEIGHT = 800;

  /**
   * Constructor for creating a html view of the photo album.
   *
   * @param snapshots the list of snapshots that are being compiled and depicted into an output file.
   */
  public WebView(List<Snapshot> snapshots) {
    this.snapshots = snapshots;
  }

  @Override
  public void generateWebPage(String output) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(output))) {
      writeHtmlHeader(writer);
      for (Snapshot snapshot : snapshots) {
        writeSnapshotSection(writer, snapshot);
      }
      writeHtmlFooter(writer);

    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Method to create a header for the snapshots.
   *
   * @param writer the output writer.
   */
  private void writeHtmlHeader(PrintWriter writer) {
    writer.println("<!DOCTYPE html>");
    writer.println("<html lang=\"en\">");
    writer.println("<head>");
    writer.println("    <meta charset=\"UTF-8\">");
    writer.println("    <title>Photo Album Snapshots</title>");
    writer.println("    <style>");
    writer.println("        body { font-family: Arial, sans-serif; max-width: 1000px; margin: 0 auto; }");
    writer.println("        .snapshot { margin-bottom: 20px; border-bottom: 1px solid #ccc; padding-bottom: 20px; }");
    writer.println("        .snapshot-info { background-color: #f4f4f4; padding: 10px; margin-bottom: 10px; }");
    writer.println("        .snapshot-info h2 { color: #333; margin-top: 0; }");
    writer.println("        .snapshot-info p { margin: 5px 0; }");
    writer.println("        .snapshot-info .description { color: #666; font-style: italic; margin-top: 10px; }");
    writer.println("    </style>");
    writer.println("</head>");
    writer.println("<body>");
    writer.println("    <h1>Photo Album Snapshots</h1>");
  }

  /**
   * Method to create the delimited snapshot sections in the output.
   *
   * @param writer the output writer
   * @param snapshot the snapshot being processed.
   */
  private void writeSnapshotSection(PrintWriter writer, Snapshot snapshot) {
    writer.println("    <div class=\"snapshot\">");


    writer.println("        <div class=\"snapshot-info\">");
    writer.println("            <h2>Snapshot: " + escapeHtml(snapshot.getId()) + "</h2>");
    writer.println("            <p>Timestamp: " + escapeHtml(snapshot.getTimestamp().toString()) + "</p>");

    String description = snapshot.getDescription();
    if (description != null && !description.trim().isEmpty()) {
      writer.println("            <p class=\"description\">Description: " + escapeHtml(description) + "</p>");
    }
    writer.println("        </div>");


    writer.println("        <svg width=\"800\" height=\"800\" xmlns=\"http://www.w3.org/2000/svg\">");

    List<IShape> shapes = snapshot.getShapes();

    shapes.stream()
            .filter(s -> s.getName().toLowerCase().contains("background") ||
                    s.getName().toLowerCase().contains("rect1"))
            .forEach(shape -> writeSvgShape(writer, shape));


    shapes.stream()
            .filter(s -> !s.getName().toLowerCase().contains("background") &&
                    !s.getName().toLowerCase().contains("rect1"))
            .forEach(shape -> writeSvgShape(writer, shape));

    writer.println("        </svg>");
    writer.println("    </div>");
  }


  /**
   * Method to create the shape in the out.
   *
   * @param writer the output file.
   * @param shape the object Shape being created.
   */
  private void writeSvgShape(PrintWriter writer, IShape shape) {
    String shapeType = shape.getType().toLowerCase();
    double x = shape.getPosition().x();
    double y = shape.getPosition().y();
    double width = shape.getFirstDimension();
    double height = shape.getSecondDimension();

    String colorString = String.format("rgb(%d,%d,%d)",
            (int)shape.getColor().r(),
            (int)shape.getColor().g(),
            (int)shape.getColor().b());

    switch (shapeType) {
      case "rectangle":
        writer.println(String.format(
                "            <rect x=\"%.1f\" y=\"%.1f\" width=\"%.1f\" height=\"%.1f\" fill=\"%s\" />",
                x, y, width, height, colorString));
        break;
      case "oval":
        // For the moon, we need to center it properly
        double cx = x + width/2;  // Center x
        double cy = y + height/2; // Center y
        double rx = width/2;      // x radius
        double ry = height/2;     // y radius
        writer.println(String.format(
                "            <ellipse cx=\"%.1f\" cy=\"%.1f\" rx=\"%.1f\" ry=\"%.1f\" fill=\"%s\" />",
                cx, cy, rx, ry, colorString));
        break;
    }
  }

  /**
   * Method to create a Generic footer.
   *
   * @param writer the write class for the output.
   */
  private void writeHtmlFooter(PrintWriter writer) {
    writer.println("</body>");
    writer.println("</html>");
  }

  /**
   * Method to handle html special characters.
   */
  private String escapeHtml(String input) {
    if (input == null) return "";
    return input
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;");
  }

  @Override
  public List<Snapshot> getSnapshots() {
    return Collections.unmodifiableList(snapshots);
  }

  @Override
  public void displaySnapshot(Snapshot snapshot) {}

}
