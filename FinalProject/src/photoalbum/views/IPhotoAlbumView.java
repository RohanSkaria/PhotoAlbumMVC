package photoalbum.views;

import java.util.List;

import photoalbum.model.Snapshot;

/**
 * The class for all the views for the photo album.
 */
public interface IPhotoAlbumView {

  /**
   * to display the specific snapshot to its intended view.
   *
   * @param snapshot the snapshot being displayed.
   */
  void displaySnapshot(Snapshot snapshot);

  /**
   * Getter Method for a list of snapshots.
   *
   * @return an unmodified list of snapshots.
   */
  List<Snapshot> getSnapshots();
}
