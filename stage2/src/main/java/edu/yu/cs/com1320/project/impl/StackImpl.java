package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Stack;

public class StackImpl<Object> implements Stack<Object>{
    public StackImpl(){}
    private stackInnards stackHead = new stackInnards(null, null);
    private int size = 0;
    public void push(Object element) {
        stackHead.next = new stackInnards(element, stackHead.next);
    }

    public Object pop() {
        stackInnards holder = stackHead.next;
        stackHead.next = stackHead.next.next;
        //noinspection ConstantConditions
        if(holder != null) {
            size--;
            return holder.command;
        }
        return null;
    }

    public Object peek() {
        return stackHead.next.command;
    }

    public int size() {
        return size;
    }
    private class stackInnards{
        protected Object command;
        protected stackInnards next;
        private stackInnards(Object command, stackInnards next){
            this.command = command;
            this.next = next;
        }
    }
}