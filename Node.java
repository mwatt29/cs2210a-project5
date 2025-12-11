public class Node {
    private int name;
    private boolean marked;

    // Constructor: creates an unmarked node with the given name
    public Node(int name) {
        this.name = name;
        this.marked = false;
    }

    // Marks the node with the specified value
    public void setMark(boolean mark) {
        this.marked = mark;
    }

    // Returns the value with which the node has been marked
    public boolean getMark() {
        return this.marked;
    }

    // Returns the name of the node
    public int getName() {
        return this.name;
    }

    // Returns true of this node has the same name as otherNode
    public boolean equals(Node otherNode) {
        if (otherNode == null) return false;
        return this.name == otherNode.getName();
    }
}