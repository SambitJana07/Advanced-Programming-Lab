import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BSTAssignment {

   
    static class Node {
        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    static class BST {
        Node root;

       
        void insert(int key) {
            root = insert(root, key);
        }

        Node insert(Node n, int key) {
            if (n == null) return new Node(key);

            if (key < n.key)
                n.left = insert(n.left, key);
            else if (key > n.key)
                n.right = insert(n.right, key);

            return n;
        }

        
        boolean search(int key) {
            Node n = root;

            while (n != null) {
                if (n.key == key) return true;
                n = key < n.key ? n.left : n.right;
            }

            return false;
        }

       
        void inorder(Node n) {
            if (n == null) return;
            inorder(n.left);
            System.out.print(n.key + " ");
            inorder(n.right);
        }

        void preorder(Node n) {
            if (n == null) return;
            System.out.print(n.key + " ");
            preorder(n.left);
            preorder(n.right);
        }

        void postorder(Node n) {
            if (n == null) return;
            postorder(n.left);
            postorder(n.right);
            System.out.print(n.key + " ");
        }

       
        void delete(int key) {
            root = delete(root, key);
        }

        Node delete(Node n, int key) {
            if (n == null) return null;

            if (key < n.key)
                n.left = delete(n.left, key);
            else if (key > n.key)
                n.right = delete(n.right, key);
            else {
                if (n.left == null) return n.right;
                if (n.right == null) return n.left;

                Node temp = min(n.right);
                n.key = temp.key;
                n.right = delete(n.right, temp.key);
            }
            return n;
        }

        Node min(Node n) {
            while (n.left != null)
                n = n.left;
            return n;
        }

       
        int height(Node n) {
            if (n == null) return 0;
            return 1 + Math.max(height(n.left), height(n.right));
        }

        int height() {
            return height(root);
        }
    }

   
    static List<Integer> input(int n, int type) {
        List<Integer> a = new ArrayList<>();

        for (int i = 1; i <= n; i++)
            a.add(i);

        if (type == 0)
            Collections.shuffle(a);

        if (type == 2)
            Collections.reverse(a);

        return a;
    }

    
    static void test(List<Integer> a, String type, PrintWriter file) {

        BST tree = new BST();

        long start = System.nanoTime();

        for (int x : a)
            tree.insert(x);

        double build = (System.nanoTime() - start) / 1e6;

        int h = tree.height();

        start = System.nanoTime();

        for (int i = 0; i < 1000; i++)
            tree.search((i % a.size()) + 1);

        double search = (System.nanoTime() - start) / 1e3;

        start = System.nanoTime();

        for (int i = 0; i < Math.min(500, a.size()); i++)
            tree.delete(a.get(i));

        double del = (System.nanoTime() - start) / 1e3;

        System.out.printf(
                "%-7d %-16s %-12.3f %-8d %-12.3f %-12.3f%n",
                a.size(), type, build, h, search, del
        );

        file.printf(
                Locale.US,
                "%d,%s,%.3f,%d,%.3f,%.3f%n",
                a.size(), type, build, h, search, del
        );
    }

    public static void main(String[] args) throws Exception {

        
        BST tree = new BST();

        int[] keys = {50, 30, 70, 20, 40, 60, 80, 10};

        for (int x : keys)
            tree.insert(x);

        System.out.print("Inorder: ");
        tree.inorder(tree.root);

        System.out.print("\nPreorder: ");
        tree.preorder(tree.root);

        System.out.print("\nPostorder: ");
        tree.postorder(tree.root);

        System.out.println("\n\nSearch 40: " + tree.search(40));
        System.out.println("Search 90: " + tree.search(90));

       
        tree.delete(10);
        tree.delete(30);

        System.out.print("\nAfter deletion: ");
        tree.inorder(tree.root);

       
        System.out.println("\n\nBST PERFORMANCE");
        System.out.printf(
                "%-7s %-16s %-12s %-8s %-12s %-12s%n",
                "N", "Input", "Build(ms)",
                "Height", "Search(us)", "Delete(us)"
        );

        PrintWriter file = new PrintWriter("bst_results.csv");

        file.println(
                "N,Input,Build_ms,Height,Search_us,Delete_us"
        );

        int[] sizes = {1000, 5000, 10000};

        for (int n : sizes) {
            test(input(n, 0), "Random", file);
            test(input(n, 1), "Sorted", file);
            test(input(n, 2), "Reverse", file);
        }

        file.close();

        System.out.println("\nResults saved to bst_results.csv");
    }
}