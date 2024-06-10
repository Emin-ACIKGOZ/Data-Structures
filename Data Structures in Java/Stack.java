// Made by Emin Salih Açıkgöz. Student ID: 22050111032
/**
 * Represents a stack.
 *
 * @param <E> the type of elements in the stack
 */
public class Stack<E> {

    /**
     * Represents a node within the stack.
     *
     * @param <E> the type of element stored in the node
     */
    private static class Node<E> {
        private final E val; // The payload of the node
        private Node<E> next; // A reference to the next node

        /**
         * Constructs a new node with the given value of val.
         *
         * @param val the value to be stored in the node
         */
        private Node(E val) {
            this.val = val;
        }

        /**
         * Retrieves the next node.
         *
         * @return the next node in the stack
         */
        private Node<E> getNext() {
            return this.next;
        }

        /**
         * Sets the next node.
         *
         * @param next the node to be set as the next node
         */
        private void setNext(Node<E> next) {
            this.next = next;
        }

        /**
         * Retrieves the value stored in the node.
         *
         * @return the value stored in the node
         */
        private E getVal() {
            return this.val;
        }
    }

    private Node<E> top; // The top node of the stack
    private int size; // The size of the stack

    /**
     * Constructs an empty stack.
     */
    public Stack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the size of the stack
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Pushes an element onto the top of the stack.
     *
     * @param val the value to be pushed onto the stack
     */
    public void push(E val) {
        if (val != null) {
            Node<E> temp = new Node<>(val);
            temp.setNext(this.top);
            this.top = temp;
            this.size++;
        }
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the element removed from the top of the stack, or null if the stack is empty
     */
    public E pop() {
        if (isEmpty()) {
            return null;
        } else {
            Node<E> topNode = top;
            top = top.getNext();
            this.size--;
            return topNode.getVal();
        }
    }

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * @return the element at the top of the stack, or null if the stack is empty
     */
    public E peek() {
        if (isEmpty()) {
            return null;
        } else {
            return top.getVal();
        }
    }
}

