public class RedblackTreeInsertion {
    public class Main {

    static final boolean RED = true;
    static final boolean BLACK = false;

    static class Node {
        int data;
        boolean color;
        Node left, right, parent;

        Node(int data) {
            this.data = data;
            this.color = RED;
        }
    }

    static Node root = null;

    static void leftRotate(Node x) {
        Node y = x.right;

        x.right = y.left;

        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    static void rightRotate(Node x) {
        Node y = x.left;

        x.left = y.right;

        if (y.right != null)
            y.right.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;
        else if (x == x.parent.right)
            x.parent.right = y;
        else
            x.parent.left = y;

        y.right = x;
        x.parent = y;
    }

    static void insert(int data) {

        Node newNode = new Node(data);

        if (root == null) {
            root = newNode;
            root.color = BLACK;
            return;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            parent = current;

            if (data < current.data)
                current = current.left;
            else if (data > current.data)
                current = current.right;
            else
                return;
        }

        newNode.parent = parent;

        if (data < parent.data)
            parent.left = newNode;
        else
            parent.right = newNode;

          
    }
}

   
    
}
