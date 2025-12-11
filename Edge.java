public class Edge {
    private Node u;
    private Node v;
    private int type;

    // Constructor: Creates an edge of the given type connecting nodes u and v
    public Edge(Node u, Node v, int type) {
        this.u = u;
        this.v = v;
        this.type = type;
    }

    // Returns the first endpoint
    public Node firstEndpoint() {
        return this.u;
    }

    // Returns the second endpoint
    public Node secondEndpoint() {
        return this.v;
    }

    // Returns the type of the edge
    public int getType() {
        return this.type;
    }

    // Sets the type of the edge
    public void setType(int newType) {
        this.type = newType;
    }

    // Returns true if this Edge object connects the same two nodes as otherEdge
    public boolean equals(Edge otherEdge) {
        if (otherEdge == null) return false;
        
        Node otherU = otherEdge.firstEndpoint();
        Node otherV = otherEdge.secondEndpoint();

        // Check if endpoints match (order doesn't matter for undirected edge)
        boolean sameOrder = this.u.equals(otherU) && this.v.equals(otherV);
        boolean reverseOrder = this.u.equals(otherV) && this.v.equals(otherU);

        return sameOrder || reverseOrder;
    }
}