package photoalbum;

import photoalbum.controller.PhotoAlbumController;
import photoalbum.controller.ViewType;

import java.util.Scanner;

public class PhotoAlbumMain {
  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      String inputFile = null;
      String outputFile = null;
      String viewType = null;
      int xMax = 1000;
      int yMax = 1000;


      System.out.println("Please enter the following details:");


      System.out.print("Input file: ");
      inputFile = scanner.nextLine();


      System.out.print("View type (web, graphical, or both): ");
      viewType = scanner.nextLine().toUpperCase();


      if (viewType.equals("WEB") || viewType.equals("BOTH")) {
        System.out.print("Output file: ");
        outputFile = scanner.nextLine();
      }


      if (inputFile == null || viewType == null) {
        System.out.println("Error: Input file and view type are required.");
        System.out.println("Usage: -in <input-file> -view <type-of-view> [-out <output-file>] [xmax] [ymax]");
        System.out.println("View types: web, graphical, both");
        return;
      }

      ViewType view;
      try {
        view = ViewType.valueOf(viewType);
      } catch (IllegalArgumentException e) {
        System.out.println("Must adhere to keywords: web, graphical, both");
        return;
      }

      if (view == ViewType.WEB && outputFile == null) {
        System.out.println("Output File required for web view");
        return;
      }

      PhotoAlbumController controller = PhotoAlbumController.createController(
              new String[]{inputFile, viewType, outputFile != null ? outputFile : "unused.html"}
      );
      if (controller != null) {
        controller.run();
      }
    } catch (Exception e) {
      System.err.println("Error running photo album: " + e.getMessage());
      e.printStackTrace();
    }
  }
}