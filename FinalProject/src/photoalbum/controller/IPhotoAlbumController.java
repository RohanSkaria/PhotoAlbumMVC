package photoalbum.controller;

/**
 * Interface for the Photo Album controller.
 */
public interface IPhotoAlbumController {

  /**
   * Process the input file.
   *
   * @param input inputs being processed.
   */
  void processFiles(String input);

  /**
   * Initialize and start the view.
   */
  void run();
}
