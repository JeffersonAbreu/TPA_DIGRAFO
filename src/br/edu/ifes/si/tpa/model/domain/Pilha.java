package br.edu.ifes.si.tpa.model.domain;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Pilha<Item> implements Iterable<Item> {
    private Node<Item> primeiro;     // top of stack
    private int n;                // tamanho of the stack

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> proximo;
    }

    /**
     * Initializes an empty stack.
     */
    public Pilha() {
        primeiro = null;
        n = 0;
    }

    /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return primeiro == null;
    }

    /**
     * Returns the number of items in this stack.
     *
     * @return the number of items in this stack
     */
    public int tamanho() {
        return n;
    }

    /**
     * Adds the item to this stack.
     *
     * @param  item the item to add
     */
    public void empilha(Item item) {
        Node<Item> oldfirst = primeiro;
        primeiro = new Node<Item>();
        primeiro.item = item;
        primeiro.proximo = oldfirst;
        n++;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item desempilha() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = primeiro.item;        // save item to return
        primeiro = primeiro.proximo;            // delete primeiro node
        n--;
        return item;                   // return the saved item
    }


    /**
     * Returns (but does not remove) the item most recently added to this stack.
     *
     * @return the item most recently added to this stack
     * @throws NoSuchElementException if this stack is empty
     */
    public Item primeiro() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return primeiro.item;
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in this stack in LIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
       

    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     *
     * @return an iterator to this stack that iterates through the items in LIFO order
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(primeiro);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.proximo; 
            return item;
        }
    }
}
