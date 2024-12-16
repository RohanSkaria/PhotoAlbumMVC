package photoalbum.views;

import photoalbum.model.Snapshot;

/**
 * A interface for the graphical view of the photo album.
 */
public interface IGraphicalView extends IPhotoAlbumView {

  /**
   * A method to go to the next snapshot or return a message otherwise.
   */
  void goToNextSnapshot();

  /**
   * A method to go to the previous snapshot or return a method otherwise.
   */
  void goToPreviousSnapshot();

  /**
   * A method to go to the specific snapshot or return a message otherwise.
   *
   * @param snapshotId the snapshot that is trying to be reached.
   */
  void jumpToSnapshot(String snapshotId);

  /**
   * A method to display the information of a specific snapshot or return a message otherwise.
   *
   * @param snapshot the snapshot of which its information is being displayed.
   */
  void showSnapshot(Snapshot snapshot);
}


