package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.HashTable;

public class HashTableImpl<Key, Value> implements HashTable<Key, Value> {
    private Inputs[] table = new Inputs[5];

    //create all necessary slots to know
    public Value put(Key k, Value v) {
        int index = this.hashing(k.hashCode());
        Inputs<Key, Value> old = table[index];
        Inputs<Key, Value> newInput = new Inputs<Key, Value>(k, v);
        if(old == null){
            table[index] = newInput;
            return null;
        }
        Inputs<Key, Value> previous = null;
        //check array slot until we find a matching URI.hashCode()
        while (old.key.hashCode() != k.hashCode()) {
            //if we run to the end
            if (old.next == null) {
                // this is a new input
                if (v != null) {
                    old.next = newInput;
                    //this was a waste of time and was meant to just delete
                }  else{
                    previous.next = null;
                }
                return null;
            }
            previous = old;
            old = old.next;
        }
        //deletes input if value is null
        Inputs<Key, Value> next = old.next;
        if (v == null) {
            if(previous == null){
                table[index] = next;
            }
            else {
                previous.next = next;
            }
           return old.value;
        }
        Inputs<Key, Value> returner = old;
        old = newInput;
        newInput.next = next;
        if(previous == null){
            table[index] = newInput;
        }
        else {
            previous.next = newInput;
        }
        return returner.value;
    }

    public Value get(Key k) {
        int index = this.hashing(k.hashCode());
        Inputs<Key, Value> holder = table[index];
        if (holder != null) {
            int one = k.hashCode();
            int two = holder.key.hashCode();
            while (one != two) {
                if(holder.next == null){
                    return null;
                }
                holder = holder.next;
                two = holder.key.hashCode();
            }
            return holder.value;
        }
        return null;
    }
    private int hashing(int k){
        return (k & 0x7fffffff) % 5;
    }

    class Inputs<Key, Value> {
        Key key;
        Value value;
        Inputs<Key, Value> next;

        Inputs(Key k, Value v) {
            key = k;
            value = v;
            next = null;
        }
    }
}
