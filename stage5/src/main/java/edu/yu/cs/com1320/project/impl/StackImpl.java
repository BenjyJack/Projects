package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Stack;

public class StackImpl<T> implements Stack<T> {
    public StackImpl(){}
    private stackInnards stackHead = new stackInnards(null, null);
    private int size = 0;
    public void push(T element) {
        stackHead.next = new stackInnards(element, stackHead.next);
        size++;
    }

    public T pop() {
        stackInnards holder = stackHead.next;
        if(holder != null) {
            size--;
            stackHead.next = stackHead.next.next;
            return holder.command;
        }
        return null;
    }

    public T peek() {
        if(stackHead.next == null){
            return null;
        }
        return stackHead.next.command;
    }

    public int size() {
        return size;
    }
    private class stackInnards{
        protected T command;
        protected stackInnards next;
        private stackInnards(T command, stackInnards next){
            this.command = command;
            this.next = next;
        }
    }
}
