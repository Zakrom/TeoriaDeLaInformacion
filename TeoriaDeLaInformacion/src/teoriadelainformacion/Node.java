package teoriadelainformacion;

public class Node extends Tree {

    Tree left;
    Tree right;

    public Node(Tree left, Tree right) {
        super(left.frequency + right.frequency);
        this.left = left;
        this.right = right;
    }
}
