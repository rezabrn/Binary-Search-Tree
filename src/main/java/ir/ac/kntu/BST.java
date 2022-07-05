package ir.ac.kntu;

import java.net.PortUnreachableException;
import java.util.ArrayList;

import javax.management.relation.Role;

public class BST {
    // private Node root;

    private ArrayList<Node> nodes;

    public BST() {
    }

    public void command(String input, Node root) {
        if (input.contains("Show")) {
            printTree(root);
        } else if (input.contains("Add")) {
            int key = Integer.parseInt(input.substring(input.indexOf("[") + 1,
                    input.indexOf("]")));
            printTree(add(root, key));
        } else if (input.contains("Remove")) {
            int key = Integer.parseInt(input.substring(input.indexOf("[") + 1, input.indexOf("]")));
            printTree(remove(root, key));
        } else if (input.contains("Update")) {
            int key1 = Integer.parseInt(input.substring(input.indexOf("e[") + 2, input.indexOf("] ")));
            input = input.substring(input.indexOf("] ") + 1);
            int key2 = Integer.parseInt(input.substring(input.indexOf("o [") + 3, input.indexOf("]")));
            printTree(update(root, key1, key2));
        } else if (input.contains("Serach")) {
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

    public Node add(Node node, int key) {
        if (node == null) {
            node = new Node(key);
            return node;
        }
        if (key < node.getKey()) {
            node.setLeft(add(node.getLeft(), key));
            node.getLeft().setParent(node);
        } else if (key > node.getKey()) {
            node.setRight(add(node.getRight(), key));
            node.getRight().setParent(node);
        }
        return node;
    }

    public Node remove(Node root, int key) {
        if (root == null)
            return root;
        if (key < root.getKey()) {
            root.setLeft(remove(root.getLeft(), key));
        } else if (key > root.getKey()) {
            root.setRight(remove(root.getRight(), key));
        } else {
            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            }
            root.setKey(minValue(root.getRight()));
            root.setRight(remove(root.getRight(), root.getKey()));
        }
        return root;
    }

    public int minValue(Node root) {
        int minv = root.getKey();
        while (root.getLeft() != null) {
            minv = root.getLeft().getKey();
            root = root.getLeft();
        }
        return minv;
    }

    public int maxValue(Node root) {
        int maxv = root.getKey();
        while (root.getRight() != null) {
            maxv = root.getRight().getKey();
            root = root.getRight();
        }
        return maxv;
    }

    public Node update(Node node, int key1, int key2) {
        if (key1 > node.getKey()) {
            node.setRight(update(node.getRight(), key1, key2));
        } else if (key1 < node.getKey()) {
            node.setLeft(update(node.getLeft(), key1, key2));
        } else {
            if (key2 > node.getLeft().getKey() && key2 < node.getRight().getKey() && key2 < minValue(node.getRight()) && key2 > maxValue(node.getLeft())) {
                node.setKey(key2);
            } else {
                remove(rootFinder(node), node.getKey());
                add(rootFinder(node), key2);
            }
        }

        return node;
    }

    public Node rootFinder(Node node) {
        if (node.getParent() == null) {
            return node;
        } else {
            return node.getParent();
        }
    }

    public boolean search(Node node, int key) {
        if (node == null)
            return false;

        if (node.getKey() == key)
            return true;
        boolean res1 = search(node.getLeft(), key);
        if (res1)
            return true;
        boolean res2 = search(node.getRight(), key);

        return res2;
    }

    public boolean isEqual(Node root1, Node root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 != null && root2 == null)
            return false;
        else if (root1 == null && root2 != null)
            return false;
        else {
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

    static void printTreeInOrder(Node entry) {
        if (entry != null) {
            printTreeInOrder(entry.getLeft());
            if (entry != null) {
                System.out.println(entry.getKey());
            }
            printTreeInOrder(entry.getRight());
        }
    }

    private void printTreePreOrder(Node entry) {
        if (entry != null) {
            if (entry.getKey() != 0) {
                System.out.println(entry.getKey());
            }
            printTreeInOrder(entry.getLeft());
            printTreeInOrder(entry.getRight());
        }
    }

    private void printTreePostOrder(Node entry) {
        if (entry != null) {
            printTreeInOrder(entry.getLeft());
            printTreeInOrder(entry.getRight());
            if (entry.getKey() != 0) {
                System.out.println(entry.getKey());
            }
        }
    }

    protected Node getMinimum(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    protected Node getMaximum(Node node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    protected Node getSuccessor(Node node) {
        // if there is getRight() branch, then successor is getLeft()most node of that
        // subtree
        if (node.getRight() != null) {
            return getMinimum(node.getRight());
        } else { // otherwise it is a lowest ancestor whose getLeft() child is also
            // ancestor of node
            Node currentNode = node;
            Node parentNode = node.getParent();
            while (parentNode != null && currentNode == parentNode.getRight()) {
                // go up until we find parent that currentNode is not in getRight()
                // subtree.
                currentNode = parentNode;
                parentNode = parentNode.getParent();
            }
            return parentNode;
        }
    }

    // -------------------------------- TREE PRINTING
    // ------------------------------------

    public void printTree(Node root) {
        printSubtree(root);
    }

    public void printSubtree(Node node) {
        if (node.getRight() != null) {
            printTree(node.getRight(), true, "");
        }
        printNodeValue(node);
        if (node.getLeft() != null) {
            printTree(node.getLeft(), false, "");
        }
    }

    private void printNodeValue(Node node) {
        if (node.getKey() == 0) {
            System.out.print("<null>");
        } else {
            System.out.print(node.getKey());
        }
        System.out.println();
    }

    private void printTree(Node node, boolean isgetRight, String indent) {
        if (node.getRight() != null) {
            printTree(node.getRight(), true, indent + (isgetRight ? "        " : " |      "));
        }
        System.out.print(indent);
        if (isgetRight) {
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("----- ");
        printNodeValue(node);
        if (node.getLeft() != null) {
            printTree(node.getLeft(), false, indent + (isgetRight ? " |      " : "        "));
        }
    }
}