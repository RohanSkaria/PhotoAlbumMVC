package photoalbum.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import photoalbum.model.Color;
import photoalbum.model.Coordinate;
import photoalbum.model.IPhotoalbum;
import photoalbum.model.Oval;
import photoalbum.model.Rectangle;

/**
 * The class responsible for reading and processing the input commands that define all the
 * attributes of the shapes created.
 */
public class InputProcessor {
  private final IPhotoalbum photoAlbum;

  /**
   * Constructor for the InputProcessor Class.
   *
   * @param photoAlbum the photo album being used.
   */
  public InputProcessor(IPhotoalbum photoAlbum) {
    this.photoAlbum = photoAlbum;
  }

  /**
   * Method to read the input file.
   *
   * @param file the file path as a String.
   */
  public void readDoc(String file) {
    try (Scanner scanner = new Scanner(new File(file))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        processInput(line);
      }
    } catch (FileNotFoundException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }
  }

  /**
   * Method to process the input line.
   *
   * @param input the input line.
   */
  public void processInput(String input) {
    Scanner scanner = new Scanner(input);
    String command = scanner.next().toLowerCase();

    try {
      switch (command) {
        case "shape":
          processShapeCommand(scanner);
          break;
        case "move":
          processMoveCommand(scanner);
          break;
        case "color":
          processColorCommand(scanner);
          break;
        case "resize":
          processResizeCommand(scanner);
          break;
        case "remove":
          processShapeRemove(scanner);
          break;
        case "snapshot":
          processSnapshot(scanner);
          break;
        default:
          System.out.println("Unknown command: " + command);
      }
    } catch (IllegalArgumentException e) {
      System.err.println("Error processing command: " + e.getMessage());
    }
  }

  /**
   * Method to create a shape.
   *
   * @param scanner the instance used to read the file.
   * @throws IllegalArgumentException An exception thrown if the shape is not there.
   */
  private void processShapeCommand(Scanner scanner) throws IllegalArgumentException {
    String id = scanner.next().trim();
    String type = scanner.next().toLowerCase().trim();

    double x = scanner.nextDouble();
    double y = scanner.nextDouble();
    Coordinate coordinate = new Coordinate(x, y);

    double width = scanner.nextDouble();
    double height = scanner.nextDouble();

    int r = scanner.nextInt();
    int g = scanner.nextInt();
    int b = scanner.nextInt();
    Color color = new Color(r, g, b);

    switch (type) {
      case "rectangle" -> photoAlbum.addShape(new Rectangle(id, coordinate, width, height, color));
      case "oval" -> photoAlbum.addShape(new Oval(id, coordinate, color, width, height));
      default -> throw new IllegalArgumentException("Unsupported shape type: " + type);
    }

    System.out.println("Successfully created " + type + " with ID: " + id);
  }

  /**
   * Method to move a shape.
   *
   * @param scanner the instance used to read the file.
   */
  private void processMoveCommand(Scanner scanner) {
    String id = scanner.next();
    double x = scanner.nextDouble();
    double y = scanner.nextDouble();
    photoAlbum.moveShape(id, new Coordinate(x, y));
  }

  /**
   * Method to change the color of a shape.
   *
   * @param scanner the instance used to read the file.
   */
  private void processColorCommand(Scanner scanner) {
    String id = scanner.next();
    int r = scanner.nextInt();
    int g = scanner.nextInt();
    int b = scanner.nextInt();
    Color color = new Color(r, g, b);
    photoAlbum.setColor(id, color);
  }

  /**
   * Method to resize the shape.
   *
   * @param scanner the instance used to read the file.
   */
  private void processResizeCommand(Scanner scanner) {
    String id = scanner.next();
    int width = scanner.nextInt();
    int height = scanner.nextInt();
    photoAlbum.resizeShape(id, width, height);
  }

  /**
   * Method to remove a shape.
   *
   * @param scanner the instance for reading the next line.
   */
  private void processShapeRemove(Scanner scanner) {
    String id = scanner.next();
    photoAlbum.removeShape(photoAlbum.getShape(id));
  }

  /**
   * Method to take a snapshot and view it.
   *
   * @param scanner the instance used to read the file.
   */
  private void processSnapshot(Scanner scanner) {
    StringBuilder description = new StringBuilder();
    while (scanner.hasNext()) {
      description.append(scanner.next()).append(" ");
    }
    photoAlbum.takeSnapshot(description.toString().trim());
  }
}