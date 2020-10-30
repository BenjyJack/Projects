package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.MinHeap;
import edu.yu.cs.com1320.project.stage4.impl.DocumentImpl;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class MinHeapImpl<E extends Comparable> extends MinHeap {
    public MinHeapImpl() {
        this.elements = new Comparable[4];
        this.elementsToArrayIndex = new HashMap(4);
    }

    @Override
    public void insert(Comparable x) {
        super.insert(x);
        this.elementsToArrayIndex.put(x, this.count);
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        E min = (E) this.elements[1];
        //swap root with last, decrement count
        this.swap(1, this.count--);
        //move new root down as needed
        this.downHeap(1);
        this.elements[this.count + 1] = null; //null it to prepare for GC
        this.elementsToArrayIndex.remove(min);
        return min;
    }

    public void reHeapify(Comparable element) {
        int index = getArrayIndex(element);
        while (index > 1 && this.isGreater(index / 2, index)) {
            Comparable moved = elements[index / 2];
            this.swap(index, index / 2);
            elementsToArrayIndex.put(moved, index);
            elementsToArrayIndex.put(element, index / 2);
            index = index / 2;
        }
        while (elements.length > index * 2 && elements[index *2] != null && this.isGreater(index, index * 2)) {
            Comparable moved = elements[index * 2];
            this.swap(index, index * 2);
            elementsToArrayIndex.put(moved, index);
            elementsToArrayIndex.put(element, index * 2);
            index = index * 2;
        }
    }

    protected int getArrayIndex(Comparable element) {
        return (int) this.elementsToArrayIndex.get(element);
    }

    protected void doubleArraySize() {
        E[] reSized = (E[]) new Comparable[elements.length * 2];
        for (int i = 0; i < elements.length; i++) {
            reSized[i] = (E) elements[i];
        }
        elements = reSized;
    }
}
