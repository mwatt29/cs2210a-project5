import java.util.ArrayList;

public class UndirectedGraph implements UndirectedGraphADT {
    
    private ArrayList<Node> vertices;
    private ArrayList<ArrayList<Edge>> adjList;

    // Constructor: creates an undirected graph with n nodes and no edges
    public UndirectedGraph(int n) {
        vertices = new ArrayList<Node>(n);
        adjList = new ArrayList<ArrayList<Edge>>(n);
        
        // Initialize nodes and empty adjacency lists
        for (int i = 0; i < n; i++) {
            vertices.add(new Node(i));
            adjList.add(new ArrayList<Edge>());
        }
    }

    // Adds an edge to the graph
    public void insertEdge(Node u, Node v, int type) throws GraphException {
        if (!isValidNode(u) || !isValidNode(v)) {
            throw new GraphException("Node does not exist");
        }

        // Check if edge already exists to prevent duplicates
        if (areAdjacent(u, v)) {
            throw new GraphException("Edge already exists");
        }

        Edge newEdge = new Edge(u, v, type);
        
        // Since it is undirected, we add the SAME edge object to both lists.
        // This ensures if we change the type later, it updates for both nodes.
        adjList.get(u.getName()).add(newEdge);
        adjList.get(v.getName()).add(newEdge);
    }

    // Returns the node with the specified name
    public Node getNode(int name) throws GraphException {
        if (name < 0 || name >= vertices.size()) {
            throw new GraphException("Node name out of bounds");
        }
        return vertices.get(name);
    }

    // Returns a list storing all the edges incident on node u
    public ArrayList<Edge> incidentEdges(Node u) throws GraphException {
        if (!isValidNode(u)) {
            throw new GraphException("Node does not exist");
        }
        
        ArrayList<Edge> edges = adjList.get(u.getName());
        // Spec says return null if no edges
        if (edges.isEmpty()) return null;
        
        return edges;
    }

    // Returns the edge connecting nodes u and v
    public Edge getEdge(Node u, Node v) throws GraphException {
        if (!isValidNode(u) || !isValidNode(v)) {
            throw new GraphException("Node does not exist");
        }

        ArrayList<Edge> edges = adjList.get(u.getName());
        for (Edge e : edges) {
            // Check if this edge connects to v (either as first or second endpoint)
            if (e.firstEndpoint().equals(v) || e.secondEndpoint().equals(v)) {
                return e;
            }
        }
        
        throw new GraphException("No edge between these nodes");
    }

    // Returns true if nodes u and v are adjacent
    public boolean areAdjacent(Node u, Node v) throws GraphException {
        if (!isValidNode(u) || !isValidNode(v)) {
            throw new GraphException("Node does not exist");
        }

        ArrayList<Edge> edges = adjList.get(u.getName());
        for (Edge e : edges) {
            if (e.firstEndpoint().equals(v) || e.secondEndpoint().equals(v)) {
                return true;
            }
        }
        return false;
    }

    // Helper method to validate if a node exists in this graph
    private boolean isValidNode(Node n) {
        if (n == null) return false;
        int name = n.getName();
        return name >= 0 && name < vertices.size();
    }
}