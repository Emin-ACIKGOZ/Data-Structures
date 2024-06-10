import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class BST {
    /**
     * This class represents a node in a binary search tree for storing contact info.
     */
    private static class Node {
        private String firstName; // First name of the contact
        private String lastName; // Last name of the contact
        private String phoneNumber; // Phone number of the contact
        private String emailAddress; // Email address of the contact
        private Node left; // Reference to the left child node
        private Node right; // Reference to the right child node

        /**
         * Constructs a new node with the given contact info.
         * @param firstName The first name of the contact.
         * @param lastName The last name of the contact.
         * @param phoneNumber The phone number of the contact.
         * @param emailAddress The email address of the contact.
         */
        public Node(String firstName, String lastName, String phoneNumber, String emailAddress) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.emailAddress = emailAddress;
            this.left = null;
            this.right = null;
        }

        // Getters and setters

        /**
         * Gets the first name of the contact.
         * @return The first name of the contact.
         */
        String getFirstName() {
            return firstName;
        }

        /**
         * Gets the last name of the contact.
         * @return The last name of the contact.
         */
        String getLastName() {
            return lastName;
        }

        /**
         * Gets the phone number of the contact.
         * @return The phone number of the contact.
         */
        String getPhoneNumber() {
            return phoneNumber;
        }

        /**
         * Gets the email address of the contact.
         * @return The email address of the contact.
         */
        String getEmailAddress() {
            return emailAddress;
        }

        /**
         * Gets the left child node.
         * @return The left child node.
         */
        Node getLeft() {
            return left;
        }

        /**
         * Sets the left child node.
         * @param left The left child node to set.
         */
        void setLeft(Node left) {
            this.left = left;
        }

        /**
         * Gets the right child node.
         * @return The right child node.
         */
        Node getRight() {
            return right;
        }

        /**
         * Sets the right child node.
         * @param right The right child node to set.
         */
        void setRight(Node right) {
            this.right = right;
        }

        /**
         * Sets the contact information of the node.
         * @param node The node whose contact information will be set to this node.
         */
        void setData(Node node) {
            this.firstName = node.getFirstName();
            this.lastName = node.getLastName();
            this.phoneNumber = node.getPhoneNumber();
            this.emailAddress = node.getEmailAddress();
        }

        /**
         * Returns a string representation of the node, containing contact information.
         * @return A string representation of the node.
         */
        @Override
        public String toString() {
            return firstName + "," + lastName + "," + phoneNumber + "," + emailAddress;
        }
    }


    private Node root; // The root node of the BST

    /**
     * Constructs an empty BST.
     */
    public BST() {
        this.root = null;
    }

    /**
     * Inserts a new node with the given contact information into the BST.
     * @param firstName The first name of the contact.
     * @param lastName The last name of the contact.
     * @param phoneNumber The phone number of the contact.
     * @param emailAddress The email address of the contact.
     */
    public void insert(String firstName, String lastName, String phoneNumber, String emailAddress) {
        if (!isValidInput(firstName, lastName, phoneNumber, emailAddress)) {
            System.err.println("Invalid input data.");
            return;
        }
        Node newNode = new Node(firstName, lastName, phoneNumber, emailAddress);
        if (root == null) {
            root = newNode;
        } else {
            insertNode(root, newNode);
        }
    }

    /**
     * Recursively inserts a new node into the BST rooted at the given root node.
     * @param root The root node of the subtree.
     * @param newNode The node to insert.
     */
    private void insertNode(Node root, Node newNode) {
        if (newNode.getLastName().compareToIgnoreCase(root.getLastName()) < 0) {
            if (root.getLeft() == null) {
                root.setLeft(newNode);
            } else {
                insertNode(root.getLeft(), newNode);
            }
        } else {
            if (root.getRight() == null) {
                root.setRight(newNode);
            } else {
                insertNode(root.getRight(), newNode);
            }
        }
    }


    /**
     * Deletes a node with the given last name from the BST.
     * @param lastName The last name of the contact to delete.
     */
    public void delete(String lastName) {
        boolean[] deleted = {false}; // Flag to track if deletion occurs
        root = deleteNode(root, lastName, deleted);
        if (deleted[0]) {
            System.out.println("Contact deleted successfully!");
        } else {
            System.err.println("Contact with last name '" + lastName + "' does not exist.");
        }
    }

    /**
     * Deletes a node with the given last name from the BST rooted at the given root node.
     * @param root The root node of the subtree.
     * @param lastName The last name of the contact to delete.
     * @param deleted A boolean flag to track if deletion occurs.
     * @return The root of the modified subtree after deletion.
     */
    private Node deleteNode(Node root, String lastName, boolean[] deleted) {
        if (root == null) {
            return null;
        }

        if (lastName.compareToIgnoreCase(root.getLastName()) < 0) {
            root.setLeft(deleteNode(root.getLeft(), lastName, deleted));
        } else if (lastName.compareToIgnoreCase(root.getLastName()) > 0) {
            root.setRight(deleteNode(root.getRight(), lastName, deleted));
        } else {
            deleted[0] = true; // Set the flag to true when deletion occurs
            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            } else {
                root.setData(getInOrderSuccessor(root.getRight()));
                root.setRight(deleteNode(root.getRight(), root.getLastName(), deleted));
            }
        }
        return root;
    }

    /**
     * Finds and returns the in-order successor (leftmost node) of the given node in the BST.
     * @param node The node for which to find the in-order successor.
     * @return The in-order successor node.
     */
    private Node getInOrderSuccessor(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }


    /**
     * Checks if the input data for contact info is valid.
     * @param firstName The first name of the contact.
     * @param lastName The last name of the contact.
     * @param phoneNumber The phone number of the contact.
     * @param emailAddress The email address of the contact.
     * @return True if the input data is valid, false otherwise.
     */
    private boolean isValidInput(String firstName, String lastName, String phoneNumber, String emailAddress) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String nameRegex = "^[a-zA-Z]+$";
        String phoneRegex = "^\\d{10}$";
        return Pattern.matches(emailRegex, emailAddress) &&
                Pattern.matches(nameRegex, firstName) &&
                Pattern.matches(nameRegex, lastName) &&
                Pattern.matches(phoneRegex, phoneNumber);
    }

    /**
     * Prints the contacts in the BST in in-order traversal.
     */
    public void printInOrder() {
        printInOrder(root);
    }

    /**
     * Prints the contacts in the subtree rooted at the given node in in-order traversal.
     * @param node The root node of the subtree.
     */
    private void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.getLeft());
            System.out.println(node);
            printInOrder(node.getRight());
        }
    }

    /**
     * Prints the contacts in the BST in pre-order traversal.
     */
    public void printPreOrder() {
        printPreOrder(root);
    }

    /**
     * Prints the contacts in the subtree rooted at the given node in pre-order traversal.
     * @param node The root node of the subtree.
     */
    private void printPreOrder(Node node) {
        if (node != null) {
            System.out.println(node);
            printPreOrder(node.getLeft());
            printPreOrder(node.getRight());
        }
    }

    /**
     * Prints the contacts in the BST in post-order traversal.
     */
    public void printPostOrder() {
        printPostOrder(root);
    }

    /**
     * Prints the contacts in the subtree rooted at the given node in post-order traversal.
     * @param node The root node of the subtree.
     */
    private void printPostOrder(Node node) {
        if (node != null) {
            printPostOrder(node.getLeft());
            printPostOrder(node.getRight());
            System.out.println(node);
        }
    }

    /**
     * Searches for a node with a specified last name in the binary search tree.
     *
     * @param lastName the last name to search for
     */
    public void search(String lastName) {
        Node foundNode = search(root, lastName);
        if (foundNode != null) {
            System.out.println("Contact found: ");
            System.out.println(foundNode);
        } else {
            System.err.println("Node with last name '" + lastName + "' not found.");
        }
    }

    /**
     * A recursive helper function to search for a node with a specified last name in BST.
     *
     * @param node the current node being examined
     * @param lastName the last name to search for
     * @return the node with the specified last name, or null if not found
     */
    private Node search(Node node, String lastName) {
        if (node == null || node.getLastName().equalsIgnoreCase(lastName)) {
            return node;
        }

        if (lastName.compareToIgnoreCase(node.getLastName()) < 0) {
            return search(node.getLeft(), lastName);
        } else {
            return search(node.getRight(), lastName);
        }
    }

    /**
     * Converts the binary search tree to a list of strings.
     *
     * @return a list containing string representations of the nodes in the tree
     */
    public List<String> toList() {
        List<String> values = new LinkedList<>();
        toList(root, values);
        return values;
    }

    /**
     * A recursive helper function to convert the binary search tree to a list of strings.
     *
     * @param node the current node being examined
     * @param values the list to which string representations of nodes are added
     */
    private void toList(Node node, List<String> values) {
        if (node != null) {
            values.add(node.toString());
            toList(node.getLeft(), values);
            toList(node.getRight(), values);
        }
    }

}

