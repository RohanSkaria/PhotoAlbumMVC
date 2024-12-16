package photoalbum.controller;

import java.util.Scanner;
import javax.swing.*;
import photoalbum.model.IPhotoalbum;
import photoalbum.model.PhotoAlbum;
import photoalbum.views.GraphicalView;
import photoalbum.views.IPhotoAlbumView;
import photoalbum.views.WebView;

/**
 * A class for the constructor of the photo album.
 * Coordinates the actions between the model and the view.
 */
public class PhotoAlbumController implements IPhotoAlbumController {
  private final IPhotoalbum photoAlbum;
  private InputProcessor inputProcessor;
  private IPhotoAlbumView graphicalView;

  /**
   * Constructor for a controller, has the input file, viewType and output file.
   *
   * @param input the input file.
   * @param viewType the viewType for the display.
   * @param output the output file.
   */
  public PhotoAlbumController(String input, ViewType viewType, String output) {
    this.photoAlbum = new PhotoAlbum();
    this.inputProcessor = new InputProcessor(photoAlbum);


    processFiles(input);

    try {
      switch (viewType) {
        case GRAPHICAL:
          System.out.println("Initializing graphical view...");
          initGraphicalView();
          break;

        case WEB:
          System.out.println("Initializing web view...");
          initWebView(output);
          break;

        case BOTH:
          System.out.println("Initializing both views...");
          initGraphicalView();
          initWebView(output);
          break;

        default:
          throw new IllegalArgumentException("Unsupported view type: " + viewType);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Failed to initialize view: " + e.getMessage(), e);
    }
  }

  /**
   * Creates a controller instance.
   *
   * @param args the command line arguments.
   * @return a new controller instance or null.
   */
  public static PhotoAlbumController createController(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      if (args.length != 3) {
        System.out.println("Usage: java -jar photoalbum.jar <input-file> "
                + "<view-type> <output-file>");
        System.out.println("View Types: GRAPHICAL, WEB, BOTH");
        return null;
      }

      String inputFile = args[0];
      ViewType viewType = ViewType.valueOf(args[1].toUpperCase());
      String outputFile = args[2];

      return new PhotoAlbumController(inputFile, viewType, outputFile);
    } catch (IllegalArgumentException e) {
      System.err.println("Error: " + e.getMessage());
      System.err.println("Valid view types are: GRAPHICAL, WEB, BOTH");
    } catch (Exception e) {
      System.err.println("Error processing photo album: " + e.getMessage());
    }

    return null;
  }

  /**
   * Method to run an instance of the controller.
   */
  public void run() {
    if (graphicalView instanceof JFrame) {
      SwingUtilities.invokeLater(() -> {
        ((JFrame) graphicalView).toFront();
        ((JFrame) graphicalView).repaint();
      });
    }
  }

  @Override
  public void processFiles(String input) {
    if (this.inputProcessor == null) {
      this.inputProcessor = new InputProcessor(photoAlbum);
    }

    this.inputProcessor.readDoc(input);
  }

  /**
   * Method to generate a graphical view.
   */
  private void initGraphicalView() {
    SwingUtilities.invokeLater(() -> {
      graphicalView = new GraphicalView(photoAlbum.getSnapshots());
      ((JFrame) graphicalView).setVisible(true);
    });
  }

  /**
   * Method to initialize the webview.
   *
   * @param output the output file path.
   */
  private void initWebView(String output) {
    IPhotoAlbumView webView = new WebView(photoAlbum.getSnapshots());
    ((WebView) webView).generateWebPage(output);
    System.out.println("Web view generated at: " + output);
  }

  /**
   * Generic file reader.
   *
   * @param args the specifications for the controller as a list of Strings.
   */
  public static void main(String[] args) {
    PhotoAlbumController controller = createController(args);
    if (controller != null) {
      controller.run();
    }
  }
}