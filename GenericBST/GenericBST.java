// Sean Szumlanski
// COP 3503, Fall 2020

// ====================
// GenericBST: BST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provide for you to modify as part of
// Programming Assignment #2.

// Edited By: Cameron Bernard
// COP 3503, Fall 2020


import java.io.*;
import java.util.*;


// A generic Node class that contains some data and a connection to up to 2 other nodes
class Node < T > {
    T data;
    Node < T > left,
    right;

    Node(T data) {
        this.data = data;
    }
}

public class GenericBST < T extends Comparable < T >> {
    private Node < T > root;

    // Overloaded method to insert a Node into the BST
    // @param Any data of type t
    // @return void
    public void insert(T data) {
        root = insert(root, data);
    }

    // Inserts a Node into the BST
    // @param a node root and any data t
    // @return Node root
    private Node < T > insert(Node < T > root, T data) {
        // Instantiates a new Node using data if the current root hasn't already been instantiated
        if (root == null) {
            return new Node < T > (data);
        }
        // Traverses the left node of the current root if the data is less than the value of the current root Node
        // Logically equivalent to data < root.data
        else if (root.data.compareTo(data) > 0) {
            root.left = insert(root.left, data);
        }
        // Traverses the right node of the current root if the data is greater than the value of the current root Node
        // Logically equivalent to data > root.data
        else if (root.data.compareTo(data) < 0) {
            root.right = insert(root.right, data);
        }

        return root;
    }

    // Overloaded method to remove a Node from the BST
    // @param Any data of type t
    // @return void
    public void delete(T data) {
        root = delete(root, data);
    }

    // Removes a node from the BST
    // @param any Node and any data of type T
    private Node < T > delete(Node < T > root, T data) {
        // If the root node is null, there is nothing to delete or the traversal has ended.
        if (root == null) {
            return null;
        }
        // Traverses the left node of the current root if the data is less than the value of the current root Node
        else if (root.data.compareTo(data) > 0) {
            root.left = delete(root.left, data);
        }
        // Traverses the right node of the current root if the data is greater than the value of the current root Node
        else if (root.data.compareTo(data) < 0) {
            root.right = delete(root.right, data);
        }
        // This else statement means that we have found the node we want to delete
        else {
            // Return null if the root has no children, or one child if it only has one
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            // If the node has two children, the node's data is set to the largest element in the left subtree
            // then the left child's data is set to be what is returned when deleting the new root data from that subtree
            else {
                root.data = findMax(root.left);
                root.left = delete(root.left, root.data);
            }
        }

        return root;
    }

    // This method assumes root is non-null, since this is only called by
    // delete() on the left subtree, and only when that subtree is not empty.
    private T findMax(Node < T > root) {
        // Continues traversal to the right until it reaches a null node
        // This node will always be the largest element
        while (root.right != null) {
            root = root.right;
        }

        return root.data;
    }

    // Overloaded method to tell if data is in a BST
    // @param data of type T that is being searched for
    // @return boolean result
    public boolean contains(T data) {
        return contains(root, data);
    }

    // Finds if the value given is in the BST
    // @param data of type T that is being searched for
    // @return boolean result
    private boolean contains(Node < T > root, T data) {
        // If the root is null, the value is not found and as such will return false
        if (root == null) {
            return false;
        }
        // Traverses the left node of the current root if the data is less than the value of the current root Node
        else if (root.data.compareTo(data) > 0) {
            return contains(root.left, data);
        }
        // Traverses the right node of the current root if the data is greater than the value of the current root Node
        else if (root.data.compareTo(data) < 0) {
            return contains(root.right, data);
        }
        // The data being searched for is equal to the data here, then it was found and will return true
        else {
            return true;
        }
    }

    // Prints an in-order traversal of the BST
    // @param void
    // @return void
    public void inorder() {
        System.out.print("In-order Traversal:");
        inorder(root);
        System.out.println();
    }

    // Prints the far left Node, then the far right Node
    // @param any Node
    // @return void
    private void inorder(Node < T > root) {
        if (root == null)
            return;

        inorder(root.left);
        System.out.print(" " + root.data);
        inorder(root.right);
    }
    // Prints a pre-order traversal of the BST
    // @param void
    // @return void
    public void preorder() {
        System.out.print("Pre-order Traversal:");
        preorder(root);
        System.out.println();
    }

    // Prints the root Node, then prints the far left Nodes, then the far right Nodes
    // @param any Node
    // @return void
    private void preorder(Node < T > root) {
        if (root == null)
            return;

        System.out.print(" " + root.data);
        preorder(root.left);
        preorder(root.right);
    }

    // Prints a post-order traversal of the BST
    // @param void
    // @return void
    public void postorder() {
        System.out.print("Post-order Traversal:");
        postorder(root);
        System.out.println();
    }

    //Prints the far left Nodes, then the far right Nodes, then the root Node
    // @param any Node
    // @return void
    private void postorder(Node < T > root) {
        if (root == null)
            return;

        postorder(root.left);
        postorder(root.right);
        System.out.print(" " + root.data);
    }

    public static void main(String[] args) {
        GenericBST < Integer > myTree = new GenericBST < Integer > ();

        for (int i = 0; i < 5; i++) {
            int r = (int)(Math.random() * 150) + 1;
            System.out.println("Inserting " + r + "...");
            myTree.insert(r);
        }

        myTree.inorder();
        myTree.preorder();
        myTree.postorder();
    }

    // Returns the difficulty of the assignment from 1.0 to 5.0
    // @param void
    // @return the difficulty rating as a double
    public static double difficultyRating() {
        return 1.8;
    }

    // Returns the hours spent working on the assignment (minimum 0)
    // @param void
    // @return the hours spend on the assignment as a double.
    public static double hoursSpent() {
        return 2.0;
    }
}
