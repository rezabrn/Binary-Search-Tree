package ir.ac.kntu;

import java.net.PortUnreachableException;
import java.util.ArrayList;

public class BST {
    private Node root;

    private ArrayList<Node> nodes;

    public BST(int key) {
        root = new Node(key);
        nodes = new ArrayList<>();
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void command(String input) {
        // if (input.contains("Show")) {
        // show(root);
        /*} else*/ if (input.contains("Add")) {
            int key = Integer.parseInt(input.substring(input.indexOf("[") + 1,
            input.indexOf("]")));
            System.out.println(add(root, key));
        } else if (input.contains("Remove")) {
            Node node = new Node(Integer.parseInt(input.substring(input.indexOf("[") + 1, input.indexOf("]"))));
            int key = Integer.parseInt(input.substring(input.indexOf("["), input.indexOf("]")));
            System.out.println(remove(node, key));
        } /*
           * else if (input.contains("Update")) {
           * int key1 = Integer.parseInt(input.substring(input.indexOf("e[") + 1,
           * input.indexOf("] ")));
           * input = input.substring(0, input.indexOf("] ")) +
           * input.substring(input.indexOf("] " + 1));
           * int key2 = Integer.parseInt(input.substring(input.indexOf("o [") + 1,
           * input.indexOf("]")));
           * update(key1, key2);
           * }
           */ else if (input.contains("Serach")) {
            int key = Integer.parseInt(input.substring(input.indexOf("[" + 1, input.indexOf("]"))));
            System.out.println(search(root, key));
        } else if (input.contains("Equal")) {
            Node node1 = new Node(Integer.parseInt(input.substring(input.indexOf("[") + 1,
                    input.indexOf("-"))));
            Node node2 = new Node(Integer.parseInt(input.substring(input.indexOf("-") + 1,
                    input.indexOf("]"))));
            System.out.println(isEqual(node1, node2));
        } else if (input.contains("Depth")) {
            System.out.println(depth(root));
        }
    }

    public void show(Node node) {
        System.out.println(node.getKey());
        if (node.getRight() == null && node.getLeft() == null) {
            return;
        } else {
            if (node.getKey() == root.getKey()) {
                System.out.println("root = \n" + node);
            } else {
                System.out.println("node = \n" + node.getKey());
            }
            if (node.getLeft() != null) {
                show(node.getLeft());
            }
            if (node.getRight() != null) {
                show(node.getRight());
            }
        }
    }

    Node add(Node root, int key) {
        /*
         * If the tree is empty,
         * return a new node
         */
        if (root == null) {
            root = new Node(key);
            return root;
        }

        /* Otherwise, recur down the tree */
        if (key < root.getKey())
            root.setLeft(add(root.getLeft(), key));
        else if (key > root.getKey())
            root.setRight(add(root.getRight(), key));

        /* return the (unchanged) node pointer */
        return root;
    }

    Node remove(Node root, int key) {
        /* Base Case: If the tree is empty */
        if (root == null)
            return root;

        /* Otherwise, recur down the tree */
        if (key < root.getKey()) {
            root.setLeft(remove(root.getLeft(), key));
        } else if (key > root.getKey()) {
            root.setRight(remove(root.getRight(), key));
        }

        // if key is same as root's
        // key, then This is the
        // node to be deleted
        else {
            // node with only one child or no child
            if (root.getLeft() == null)
                return root.getRight();
            else if (root.getRight() == null)
                return root.getLeft();

            // node with two children: Get the inorder
            // successor (smallest in the right subtree)
            root.setKey(minValue(root.getRight()));

            // Delete the inorder successor
            root.setRight(remove(root.getRight(), root.getKey()));
        }
        return root;
    }

    int minValue(Node root) {
        int minv = root.getKey();
        while (root.getLeft() != null) {
            minv = root.getLeft().getKey();
            root = root.getLeft();
        }
        return minv;
    }

    // public void update(int key1, int key2) {
    // for (Node node : nodes) {
    // if (node.getKey() == key1) {
    // if (node.getRight().getKey() > key2 && node.getLeft().getKey() < key2) {
    // node.setKey(key2);
    // } else {
    // System.out.println("the operation is illegal");
    // }
    // }
    // }
    // }

    // public void update(Node node, int key1, int key2) {
    // if (node.getKey() == key1) {

    // }
    // }

    public boolean search(Node node, int key) {
        if (node == null)
            return false;

        if (node.getKey() == key)
            return true;

        // then recur on left subtree /
        boolean res1 = search(node.getLeft(), key);

        // node found, no need to look further
        if (res1)
            return true;

        // node is not found in left,
        // so recur on right subtree /
        boolean res2 = search(node.getRight(), key);

        return res2;
    }

    public boolean isEqual(Node root1, Node root2) {
        // Check if both the trees are empty
        if (root1 == null && root2 == null) {
            return true;
        }
        // If any one of the tree is non-empty
        // and other is empty, return false
        else if (root1 != null && root2 == null)
            return false;
        else if (root1 == null && root2 != null)
            return false;
        else {
            // Check if current data of both trees equal
            // and recursively check for left and right subtrees
            if (root1.getKey() == root2.getKey() && isEqual(root1.getLeft(), root2.getLeft()) == true
                    && isEqual(root1.getRight(), root2.getRight()) == true)
                return true;
            else
                return false;
        }
    }

    int depth(Node node) {
        if (node == null) {
            return -1;
        } else {
            int lDepth = depth(node.getLeft());
            int rDepth = depth(node.getRight());

            if (lDepth > rDepth)
                return (lDepth + 1);
            else
                return (rDepth + 1);
        }
    }
}