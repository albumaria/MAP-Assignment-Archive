package model.adt.stack;

import model.adt.ADTException;

import java.util.Stack;

public class ADTStack<T> implements IADTStack<T> {
    private Stack<T> stack;

    public ADTStack() {
        this.stack = new Stack<>();
    }

    public T pop() throws ADTException {
        if (this.stack.empty()) {
            throw new ADTException("No element to pop");
        }

        T element = this.stack.peek();
        this.stack.pop();

        return element;
    }

    public T peek() throws ADTException {
        if (this.stack.empty()) {
            throw new ADTException("No element to peek");
        }

        return this.stack.peek();
    }

    public void push(T element) {
        this.stack.push(element);
    }

    public boolean isEmpty() {
        return this.stack.empty();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = this.stack.size() - 1; i >= 0; i--) {
            s.append(this.stack.get(i)).append('\n');
        }

        return s.toString();

    }
}
