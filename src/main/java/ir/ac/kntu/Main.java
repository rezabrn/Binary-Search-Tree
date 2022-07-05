package ir.ac.kntu;

import java.util.Scanner;

public class Main {
    static BST bst = new BST();

    public static void main(String[] args) {
        Node root = startTree();
        String command = new String();
        listOfCommands();
        Scanner in = new Scanner(System.in);
        while (!command.equals("Exit")) {
            System.out.println("write a command");
            command = in.nextLine();
            bst.command(command, root);
        }
        in.close();
    }

    static Node startTree() {
        Scanner in = new Scanner(System.in);
        Node root = new Node(0);
        System.out.println("write an amount for root node");
        root.setKey(Integer.parseInt(in.nextLine()));
        int key;
        System.out
                .println("enter keys of nodes; if you wanna finish that, you can enter any characters except number.");
        while (true) {
            try {
                key = Integer.parseInt(in.nextLine());
                bst.add(root, key);
            } catch (NumberFormatException e) {
                break;
            }
        }
        bst.printTree(root);
        return root;
    }

    static void listOfCommands() {
        System.out.println("------------------------");
        System.out.println("*commands*\n");
        System.out.println("Show");
        System.out.println("Add");
        System.out.println("Remove");
        System.out.println("Update");
        System.out.println("Search");
        System.out.println("Equal");
        System.out.println("Depth");
        System.out.println("Exit");
        System.out.println("------------------------");
    }
}