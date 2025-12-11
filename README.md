# CS2210A Project 5: Labyrinth Solver

## Overview

This project implements a labyrinth (maze) solver using depth-first search (DFS) with backtracking on an undirected graph representation. The program finds a path from the entrance to the exit of a labyrinth while navigating various types of obstacles using a limited number of bombs.

The labyrinth contains different types of walls that can be traversed or destroyed:
- **Corridors** (`-`, `|`): Free passages requiring no bombs
- **Brick Walls** (`b`, `B`): Can be destroyed with 1 blast bomb
- **Rock Walls** (`r`, `R`): Can be destroyed with 2 blast bombs
- **Metal Walls** (`m`, `M`): Can be destroyed with 1 melt bomb
- **Solid Stone** (`*`): Impassable walls that cannot be destroyed

## Project Structure

### Core Classes

- **`Solve.java`**: Main algorithm class that parses the labyrinth file and implements the DFS path-finding algorithm
- **`Lab.java`**: Entry point of the application with GUI visualization
- **`UndirectedGraph.java`**: Implementation of an undirected graph using adjacency lists
- **`Node.java`**: Represents a room/cell in the labyrinth
- **`Edge.java`**: Represents a connection between two rooms with a specific type (wall type)
- **`Board.java`**: GUI component for drawing the labyrinth
- **`DrawLab.java`**: Handles visualization of the labyrinth and solution path

### Support Classes

- **`UndirectedGraphADT.java`**: Interface defining the graph ADT operations
- **`GraphException.java`**: Custom exception for graph-related errors
- **`LabyrinthException.java`**: Custom exception for labyrinth parsing errors
- **`TestGraph.java`**: Test suite for verifying graph implementation

## Compilation

To compile all Java files:

```bash
javac *.java
```

## Usage

### Running the Labyrinth Solver

```bash
java Lab <labyrinth_file> [delay]
```

**Parameters:**
- `<labyrinth_file>`: Path to the labyrinth input file (required)
- `[delay]`: Animation delay in milliseconds (optional, default: 0)

**Examples:**

```bash
# Run with default animation speed
java Lab lab1

# Run with 100ms delay between steps
java Lab lab5 100

# Run a complex labyrinth
java Lab lab8 50
```

### Running Tests

To test the graph implementation:

```bash
java TestGraph
```

## Input File Format

The labyrinth file format consists of:

1. **Scale factor** (line 1): Display scale for visualization
2. **Width** (line 2): Number of columns in the labyrinth
3. **Length** (line 3): Number of rows in the labyrinth
4. **Blast bombs** (line 4): Number of bombs available to destroy brick/rock walls
5. **Melt bombs** (line 5): Number of bombs available to destroy metal walls
6. **Labyrinth layout**: ASCII representation of the maze

### Example Input File (lab1):

```
60
5
3
0
0
e-o-o-o-o
|*|*R*M*B
o-oro-o-o
B*|*|*|*M
o-o-obx-o
```

**File Format Details:**
- Lines 1-5: Scale, width, length, blast bombs, melt bombs
- Remaining lines: Alternating room rows and wall rows
  - Room rows: Show rooms (e, x, o) with horizontal walls between them
  - Wall rows: Show vertical walls below each room row

**Legend:**
- `e`: Entrance (start position)
- `x`: Exit (destination)
- `o`: Regular room
- `-`, `|`: Corridors (free passage)
- `b`, `B`: Brick walls (requires 1 blast bomb)
- `r`, `R`: Rock walls (requires 2 blast bombs)
- `m`, `M`: Metal walls (requires 1 melt bomb)
- `*`: Solid stone (impassable)

## Algorithm

The solver uses **Depth-First Search (DFS) with backtracking**:

1. Start at the entrance node (`e`)
2. Mark the current node as visited
3. Check if the current node is the exit
4. For each adjacent node:
   - Check if the edge can be traversed (corridor or enough bombs available)
   - If passable, recursively explore that path with updated bomb counts
   - If the exit is found, return true
5. If no path leads to the exit, backtrack by unmarking the node
6. Continue until a path is found or all possibilities are exhausted

### Edge Types and Bomb Requirements

| Edge Type | Type ID | Symbol | Bomb Requirement |
|-----------|---------|--------|------------------|
| Corridor  | 1       | `-`, `|` | 0 (free passage) |
| Brick     | 2       | `b`, `B` | 1 blast bomb |
| Rock      | 3       | `r`, `R` | 2 blast bombs |
| Metal     | 4       | `m`, `M` | 1 melt bomb |
| Solid     | -1      | `*`    | Impassable |

## Features

- **Graph-based representation**: Converts 2D labyrinth into an undirected graph
- **DFS pathfinding**: Finds a solution path if one exists
- **Resource management**: Tracks and manages bomb usage throughout the search
- **Backtracking**: Efficiently explores alternative paths when dead ends are encountered
- **Visual feedback**: Displays the labyrinth and animates the solution path
- **Multiple test cases**: Includes 8 sample labyrinths of varying complexity (lab1-lab8)

## Sample Labyrinths

The project includes 8 pre-made labyrinth files:
- **lab1-lab4**: Simple mazes for basic testing
- **lab5-lab6**: Medium complexity mazes
- **lab7-lab8**: Complex mazes with multiple obstacle types

## Implementation Notes

### Graph Construction
- Each room in the labyrinth is represented as a node
- Node IDs are calculated as: `nodeID = row * width + column`
- Edges are added between adjacent rooms based on the wall type
- Solid stone walls (`*`) do not create edges

### DFS State Management
- Nodes are marked during exploration to prevent cycles
- The algorithm maintains bomb counts throughout recursion
- Backtracking unmarks nodes to allow exploration from different paths
- The solution path is stored in a stack and returned as an iterator

## Academic Context

This project is part of CS2210A (Data Structures and Algorithms) and demonstrates:
- Graph data structures and algorithms
- Depth-first search and backtracking
- Object-oriented design principles
- File I/O and parsing
- GUI programming with Java Swing
- Exception handling

## License

Academic project for Western University CS2210A course.
