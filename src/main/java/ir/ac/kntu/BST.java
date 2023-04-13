package ir.ac.kntu;

import java.net.PortUnreachableException;
import java.util.ArrayList;

import javax.management.relation.Role;

public class BST {

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
        } else {
            command2(input, root);
        }
    }

    public void command2(String input, Node root) {
        if (input.contains("Search")) {
            int key = Integer.parseInt(input.substring(input.indexOf("[") + 1, input.indexOf("]")));
            if (search(root, key)) {
                System.out.println("the node exits in tree");
            } else {
                System.out.println("the node dosent exit in tree");
            }
        } else if (input.contains("Equal")) {
            System.out.println("make a new tree to compare");
            if (isEqual(root, Main.startTree())) {
                System.out.println("they are the same");
            } else {
                System.out.println("they are different");
            }
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
        if (root == null) {
            return root;
        }
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
            if (key2 > node.getLeft().getKey() && key2 < node.getRight().getKey() && key2 < minValue(node.getRight())
                    && key2 > maxValue(node.getLeft())) {
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
        if (node == null) {
            return false;
        }
        if (node.getKey() == key) {
            return true;
        }
        boolean res1 = search(node.getLeft(), key);
        if (res1) {
            return true;
        }
        boolean res2 = search(node.getRight(), key);
        return res2;
    }

    public boolean isEqual(Node root1, Node root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 != null && root2 == null) {
            return false;
        } else if (root1 == null && root2 != null) {
            return false;
        } else {
            if (root1.getKey() == root2.getKey() && isEqual(root1.getLeft(), root2.getLeft()) == true
                    && isEqual(root1.getRight(), root2.getRight()) == true) {
                return true;
            } else {
                return false;
            }
        }
    }

    int depth(Node node) {
        if (node == null) {
            return -1;
        } else {
            int leftDepth = depth(node.getLeft());
            int rightDepth = depth(node.getRight());
            if (leftDepth > rightDepth) {
                return (leftDepth + 1);
            } else {
                return (rightDepth + 1);
            }
        }
    }

    public void printTree(Node node) {
        if (node.getRight() != null) {
            printTree(node.getRight(), true, "");
        }
        printNodeValue(node);
        if (node.getLeft() != null) {
            printTree(node.getLeft(), false, "");
        }
    }

    public void printNodeValue(Node node) {
        System.out.print(node.getKey());
        System.out.println();
    }

    public void printTree(Node node, boolean isgetRight, String indent) {
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