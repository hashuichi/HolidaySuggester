import java.io.*;

public class TreeNode {
    private String value;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(String value) {
        this.value = value;
    }

    public TreeNode(String value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public TreeNode getLeft() { return left; }
    public TreeNode getRight() { return right; }

    public boolean isLeaf() {
        return left == null && right == null ? true : false;
    }

    public String toString() {
        return value;
    }

    //Recursive alogorithm that uses preorder to find and replace the nodes
    public void addNode(TreeNode newNode) {
        if (left == newNode.right) {
            left = newNode;
        } else if (left != null) {
            left.addNode(newNode);
        }
        if (right == newNode.right) {
            right = newNode;
        } else if (right != null) {
            right.addNode(newNode);
        }
    }

    public void saveTree(BufferedWriter out) {
        try {
            if (isLeaf()) {
                out.write("sug "+value);
                out.newLine();
            } else {
                out.write(value);
                out.newLine();
            }
        } catch(IOException e) {
            System.out.println(e);
        }

       if (left != null) {
            left.saveTree(out);
        }
        if (right != null) {
            right.saveTree(out);
        }
    }
}