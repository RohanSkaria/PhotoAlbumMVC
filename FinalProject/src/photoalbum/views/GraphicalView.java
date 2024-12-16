package photoalbum.views;

import photoalbum.model.IShape;
import photoalbum.model.Snapshot;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A graphical view class that has interactive buttons to display the snapshots passed in.
 */
public class GraphicalView extends JFrame implements IGraphicalView {
  private List<Snapshot> snapshotList;
  private int index;

  private JPanel mainPanel;
  private JPanel canvasPanel;
  private JLabel snapshotInfoLabel;
  private JButton nextButton;
  private JButton prevButton;
  private JButton infoButton;
  private JComboBox<String> snapshotSelector;

  /**
   * Constructor for the graphical view of the snapshots.
   *
   * @param snapshotList the list of snapshots being displayed.
   */
  public GraphicalView(List<Snapshot> snapshotList) {
    this.snapshotList = snapshotList;
    this.index = 0;

    initComponents();
    setup();
    setupListeners();
  }

  /**
   * Initializing the graphical components.
   */
  private void initComponents() {
    setTitle("WELCOME TO PHOTO ALBUM");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(800, 800));

    mainPanel = new JPanel(new BorderLayout());
    canvasPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCurrentSnapshot(g);
      }
    };

    canvasPanel.setPreferredSize(new Dimension(800, 800));
    canvasPanel.setBackground(Color.WHITE);

    snapshotInfoLabel = new JLabel("Please choose a Snapshot:)");

    nextButton = new JButton("NEXT");
    prevButton = new JButton("PREV");
    infoButton = new JButton("INFO");

    String[] snapshotIds = snapshotList.stream().map(Snapshot::getId).toArray(String[]::new);
    snapshotSelector = new JComboBox<>(snapshotIds);
  }

  /**
   * Generic setup for the graphical interface.
   */
  private void setup() {
    JPanel controlPanel = new JPanel();
    controlPanel.add(prevButton);
    controlPanel.add(nextButton);  // Move next to prev button
    controlPanel.add(new JLabel("Go to:"));
    controlPanel.add(snapshotSelector);
    controlPanel.add(infoButton);  // Add the info button to control panel

    this.mainPanel.add(this.canvasPanel, BorderLayout.CENTER);
    this.mainPanel.add(controlPanel, BorderLayout.SOUTH);

    // Only add the info label once, at the top
    this.mainPanel.add(snapshotInfoLabel, BorderLayout.NORTH);

    add(mainPanel);
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Creates the current snapshot. Insures proper order when creating the objects in the snap.
   *
   * @param g the object type Graphics where the snapshot is being created.
   */
  private void drawCurrentSnapshot(Graphics g) {
    if (snapshotList.isEmpty()) {
      return;
    }

    Snapshot snapshot = snapshotList.get(index);
    List<IShape> shapes = snapshot.getShapes();

    // First pass: Draw any shape marked as background
    shapes.stream()
            .filter(s -> s.getName().toLowerCase().contains("background") ||
                    s.getName().toLowerCase().contains("rect1"))  // Including RECT1 as it serves as background
            .forEach(shape -> drawShape(g, shape));

    // Second pass: Draw all other shapes in order
    shapes.stream()
            .filter(s -> !s.getName().toLowerCase().contains("background") &&
                    !s.getName().toLowerCase().contains("rect1"))
            .forEach(shape -> drawShape(g, shape));
  }

  /**
   * Generic method for a graphical display to add the buttons needed.
   */
  private void setupListeners() {
    nextButton.addActionListener(e -> {
      goToNextSnapshot();
    });
    prevButton.addActionListener(e -> {
      goToPreviousSnapshot();
    });

    infoButton.addActionListener(e -> {
      if (!snapshotList.isEmpty()) {
        showSnapshot(snapshotList.get(index));
      }
    });

    snapshotSelector.addActionListener(e -> {
      String id = (String) snapshotSelector.getSelectedItem();
      jumpToSnapshot(id);
    });
  }

  @Override
  public void displaySnapshot(Snapshot snapshot) {
    if (snapshot == null) {
      JOptionPane.showMessageDialog(this, "No snapshots");
      return;
    }

    this.index = snapshotList.indexOf(snapshot);
    snapshotSelector.setSelectedIndex(index);

    String description = snapshot.getDescription();
    if (description != null && !description.trim().isEmpty()) {
      snapshotInfoLabel.setText("Snapshot: " + snapshot.getId() + " - " + description);
    } else {
      snapshotInfoLabel.setText("Snapshot: " + snapshot.getId());
    }

    canvasPanel.repaint();
  }

  @Override
  public List<Snapshot> getSnapshots() {
    return snapshotList;
  }

  @Override
  public void goToNextSnapshot() {
    if (snapshotList.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No snapshots available");
      return;
    }

    if (this.index < snapshotList.size() - 1) {
      this.index++;
      displaySnapshot(snapshotList.get(this.index));
    } else {
      JOptionPane.showMessageDialog(this, "Already at the last snapshot");
    }
  }

  @Override
  public void goToPreviousSnapshot() {
    if (snapshotList.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No snapshots available");
      return;
    }

    if (this.index > 0) {
      this.index--;
      displaySnapshot(snapshotList.get(this.index));
    } else {
      JOptionPane.showMessageDialog(this, "Already at the first snapshot");
    }
  }

  @Override
  public void jumpToSnapshot(String snapshotId) {
    for (Snapshot snapshot : snapshotList) {
      if (snapshot.getId().equals(snapshotId)) {
        this.index = snapshotList.indexOf(snapshot);
        displaySnapshot(snapshot);
        return;
      }
    }
  }

  @Override
  public void showSnapshot(Snapshot snapshot) {
    if (snapshot == null) {
      JOptionPane.showMessageDialog(this, "No snapshot picked");
      return;
    }

    String description = snapshot.getDescription();
    String info = String.format(
            "Snapshot Details:\n"
                    + "ID: %s\n"
                    + "Timestamp: %s\n"
                    + "Description: %s\n"
                    + "Number of Shapes: %d",
            snapshot.getId(),
            snapshot.getTimestamp(),
            description.isEmpty() ? "(No description)" : description,
            snapshot.getShapes().size()
    );

    JOptionPane.showMessageDialog(this, info, "Snapshot Information",
            JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Helper method to draw a single shape with proper color conversion.
   * This reduces code duplication and centralizes shape drawing logic.
   *
   * @param g     The graphics context to draw on
   * @param shape The shape to draw
   */
  private void drawShape(Graphics g, IShape shape) {
    // Convert the color values from our model (0-255) to AWT Color (0.0-1.0)
    Color shapeColor = new Color(
            (float) (shape.getColor().r() / 255.0),
            (float) (shape.getColor().g() / 255.0),
            (float) (shape.getColor().b() / 255.0)
    );
    g.setColor(shapeColor);

    switch (shape.getType().toLowerCase()) {
      case "rectangle":
        g.fillRect(
                (int) shape.getPosition().x(),
                (int) shape.getPosition().y(),
                (int) shape.getFirstDimension(),
                (int) shape.getSecondDimension()
        );
        break;
      case "oval":
        g.fillOval(
                (int) shape.getPosition().x(),
                (int) shape.getPosition().y(),
                (int) shape.getFirstDimension(),
                (int) shape.getSecondDimension()
        );
        break;
    }
  }
}
