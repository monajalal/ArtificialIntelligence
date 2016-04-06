/**
 * An ordered collection of items, where items are both added and removed 
 * exclusively from the front.
 * @author
 */
public interface StackADT<E> {
    /**
     * Checks if the stack is empty.
     * @return true if stack is empty; otherwise false
     */
    boolean isEmpty();

    /**
     * Returns (but does not remove) the top item of the stack.
     * @return the top item of the stack
     * @throws EmptyStackException if the stack is empty
     */
    E peek() throws EmptyStackException;

    /**
     * Pops the top item off the stack and returns it. 
     * @return the top item of the stack
     * @throws EmptyStackException if the stack is empty
     */
    E pop() throws EmptyStackException;


    /**
     * Pushes the given item onto the top of the stack.
     * @param item the item to push onto the stack
     * @throws IllegalArgumentException if item is null.
     */
    void push(E item);

    /**
     * Returns the size of the stack.
     * @return the size of the stack
     */
    int size();

    /**
     * Returns a string representation of the stack (for printing).
     * @return a string representation of the stack
     */
    String toString(); 
}
