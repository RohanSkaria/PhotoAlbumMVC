# Photo Album Application

## Overview
The system represents a digital photo album that manages shapes with various properties and can capture snapshots of the album's state at different points in time. The design follows object-oriented principles.

## Changes 
1. Pushed the dimensions of each shape to its abstract class. I initially thought they should be reserved for the shapes to account for multi-dimensional shapes, but for this project I think its better to keep it simplified to 2 dimensions for all shapes.
2. Getter methods for the dimensions were implemented. 

## Overview
This application implements a photo album system that manages and displays shapes through both graphical and web-based interfaces. Using a command-based input system, users can create, modify, and capture snapshots of their work while maintaining flexibility in how they view their creations.

## Features
This system leverages the Model-View-Controller (MVC) architectural pattern to provide a robust and extensible shape management solution. Key features include:

### Shape Management
- Create rectangles and ovals with custom dimensions and colors
- Position shapes using precise x-y coordinates
- Modify existing shapes through moving, resizing, and color changes
- Remove shapes from the canvas

### View Options
- Graphical interface built with Java Swing for interactive viewing
- Web-based output using HTML/SVG for static viewing
- Support for simultaneous display in both formats

### Snapshot System
- Capture the state of all shapes at any moment
- Add optional descriptions to snapshots
- Navigate between snapshots in the graphical view
- Generate comprehensive HTML pages showing all snapshots

## Running the Application

### Command Line Format
```bash
-in "name-of-command-file" -view "type-of-view" [-out "where-output-should-go"] [xmax] [ymax]
```

### Examples
For web view:
```bash
java -jar photoalbum.jar -in buildings.txt -out myWeb.html -v web
```

For graphical view:
```bash
java -jar photoalbum.jar -in buildings.txt -v graphical 800 800
```

### Command Line Arguments
- `-in`: Input file path (required)
- `-view` or `-v`: View type - web, graphical, or both (required)
- `-out`: Output file path (required for web view)
- `xmax ymax`: Optional window dimensions (default: 1000x1000)

## Input File Format

### Shape Creation
```
shape name type x-coordinate y-coordinate width height red green blue
```

### Shape Manipulation
```
move shapeName newX newY
color shapeName red green blue
resize shapeName newWidth newHeight
remove shapeName
```

### Snapshots
```
snapshot [optional description]
```

## Project Structure

### Model Components
- `IPhotoAlbum`: Primary interface for album operations
- `PhotoAlbum`: Core implementation managing shapes
- `IShape`: Shape interface
- `Rectangle`, `Oval`: Shape implementations
- `Snapshot`: State preservation system

### View Components
- `IPhotoAlbumView`: Base view interface
- `GraphicalView`: Swing implementation
- `WebView`: HTML/SVG generator

### Controller Components
- `PhotoAlbumController`: Primary controller
- `InputProcessor`: Command processor
- `IPhotoAlbumController`: Controller interface

## Testing
The project includes JUnit4 tests for core functionality:

### Model Testing
- Shape creation and modification
- Snapshot management
- State preservation

### Controller Testing
- Command processing
- Input validation
- Error handling

### View Testing
- Shape rendering
- Snapshot display
- Output generation

## Implementation Details

### Key Classes
- `Record` classes for Color and Coordinate
- Stream processing for shape operations
- Robust error handling system

