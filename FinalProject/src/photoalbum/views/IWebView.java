package photoalbum.views;

/**
 * A interface to specify the web view of the photo album.
 */
public interface IWebView extends IPhotoAlbumView {

  /**
   * Creates the output file with the html code.
   *
   * @param outputFilePath the output file path as a String.
   */
  void generateWebPage(String outputFilePath);
}
