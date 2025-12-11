import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Solve {
    
    private UndirectedGraph graph;
    private Node startNode;
    private Node exitNode;
    private int blastBombs; // K1
    private int meltBombs;  // K2

    // Constructor: Parses the file and builds the graph
    public Solve(String inputFile) throws LabyrinthException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            
            // Read Headers
            br.readLine(); // Skip Scale
            int width = Integer.parseInt(br.readLine());
            int length = Integer.parseInt(br.readLine());
            blastBombs = Integer.parseInt(br.readLine());
            meltBombs = Integer.parseInt(br.readLine());

            // Initialize Graph
            graph = new UndirectedGraph(width * length);

            // Loop through the rows of the labyrinth
            for (int row = 0; row < length; row++) {
                
                // 1. Process "Room" line (contains rooms and horizontal walls)
                String roomLine = br.readLine();
                if (roomLine == null) break;

                for (int col = 0; col < width; col++) {
                    int currentNodeId = row * width + col;
                    Node u = graph.getNode(currentNodeId);

                    // Check if this is Start or Exit
                    // Characters are at indices 0, 2, 4... (col * 2)
                    char roomChar = roomLine.charAt(col * 2);
                    if (roomChar == 'e') startNode = u;
                    if (roomChar == 'x') exitNode = u;

                    // Check Horizontal Edge to the right
                    // Only if we are not at the last column
                    if (col < width - 1) {
                        char hEdgeChar = roomLine.charAt(col * 2 + 1);
                        int type = getEdgeType(hEdgeChar);
                        // If type is -1, it's a solid block, don't add edge
                        if (type != -1) {
                            Node v = graph.getNode(row * width + (col + 1));
                            graph.insertEdge(u, v, type);
                        }
                    }
                }

                // 2. Process "Vertical" line (contains vertical walls)
                // Only exists between rows, so we skip if we are at the last row
                if (row < length - 1) {
                    String vertLine = br.readLine();
                    if (vertLine != null) {
                        for (int col = 0; col < width; col++) {
                            char vEdgeChar = vertLine.charAt(col * 2);
                            int type = getEdgeType(vEdgeChar);
                            
                            if (type != -1) {
                                Node u = graph.getNode(row * width + col);
                                Node v = graph.getNode((row + 1) * width + col);
                                graph.insertEdge(u, v, type);
                            }
                        }
                    }
                }
            }
            br.close();

        } catch (Exception e) {
            throw new LabyrinthException("Error parsing file: " + e.getMessage());
        }
    }

    // Helper to map characters to edge types
    private int getEdgeType(char c) {
        switch (c) {
            case '-': case '|': return 1; // Corridor
            case 'b': case 'B': return 2; // Brick
            case 'r': case 'R': return 3; // Rock
            case 'm': case 'M': return 4; // Metal
            case '*': return -1;          // Solid Stone (No edge)
            default: return -1;
        }
    }

    public UndirectedGraph getGraph() {
        return this.graph;
    }

    public Iterator<Node> findExit() {
        Stack<Node> path = new Stack<>();
        try {
            if (dfs(startNode, blastBombs, meltBombs, path)) {
                return path.iterator();
            }
        } catch (GraphException e) {
            // Silently handle exception or print minimal error if needed
        }
        return null; // Return null if no path found
    }

    // Recursive DFS with Backtracking
    private boolean dfs(Node u, int k1, int k2, Stack<Node> path) throws GraphException {
        
        // 1. Mark current node as visited and add to path
        u.setMark(true);
        path.push(u);

        // 2. Base Case: Reached Exit
        if (u.equals(exitNode)) {
            return true;
        }

        // 3. Iterate through incident edges
        ArrayList<Edge> edges = graph.incidentEdges(u);
        if (edges != null) {
            for (Edge e : edges) {
                // Determine the neighbor node v
                Node v = (e.firstEndpoint().equals(u)) ? e.secondEndpoint() : e.firstEndpoint();

                // If v is already visited in this current path recursion, skip it
                if (v.getMark()) continue;

                int type = e.getType();
                boolean canPass = false;
                int nextK1 = k1;
                int nextK2 = k2;

                // Check passability based on edge type and available bombs
                if (type == 1) { // Corridor
                    canPass = true;
                } else if (type == 2) { // Brick Wall
                    if (k1 > 0) {
                        canPass = true;
                        nextK1--;
                    }
                } else if (type == 3) { // Rock Wall
                    if (k1 > 1) { // Needs 2 blast bombs
                        canPass = true;
                        nextK1 -= 2;
                    }
                } else if (type == 4) { // Metal Wall
                    if (k2 > 0) {
                        canPass = true;
                        nextK2--;
                    }
                }

                // Recurse if passable
                if (canPass) {
                    if (dfs(v, nextK1, nextK2, path)) {
                        return true;
                    }
                }
            }
        }

        // 4. Backtrack: Unmark node and pop from stack
        u.setMark(false);
        path.pop();
        
        return false;
    }
}