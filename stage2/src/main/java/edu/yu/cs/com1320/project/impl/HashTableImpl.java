package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.HashTable;

public class HashTableImpl<Key, Value> implements HashTable<Key, Value> {
    public HashTableImpl(){}
    private Inputs[] table = new Inputs[5];
    public Value get(Key k){
        int index = this.hashing(k.hashCode());
        Inputs<Key, Value> holder = table[index];
        if (holder != null) {
            int one = k.hashCode();
            int two = holder.key.hashCode();
            while (one != two) {
                holder = holder.next;
            }
            return holder.value;
        }
        return null;
    }
    public Value put(Key k, Value v){
        int index = this.hashing(k.hashCode());
        Inputs<Key, Value> old = table[index];
        Inputs<Key, Value> newInput = new Inputs<Key, Value>(k, v);
        if(old == null){
            table[index] = newInput;
            return null;
        }
        Inputs<Key, Value> holder = old;
        Inputs<Key, Value> previous = null;
        int counter = 0;
        //check array slot until we find a matching URI.hashCode()
        while (holder.key != k) {
            if(counter == 4){
                return reHash(newInput, table.length);
            }
            //if we run to the end
            if (holder.next == null) {
                // this is a new input
                if (v != null) {
                    holder.next = newInput;
                    //this was a waste of time and was meant to just delete
                }  else{
                    previous.next = null;
                }
                return null;
            }
            previous = holder;
            holder = holder.next;
            counter++;
        }
        //deletes input if value is null
        Inputs<Key, Value> next = holder.next;
        if (v == null) {
            previous.next = next;
            return holder.value;
        }
        holder = newInput;
        newInput.next = next;
        previous.next = newInput;
        return old.value;
    }
    private Value reHash(Inputs<Key, Value> newInput, int tableSize){
        Inputs[] newTable = new Inputs[(int) Math.ceil(tableSize*1.5)];
        for (int i = 0; i < table.length; i++) {
            Inputs<Key, Value> placeHolder = table[i];
            if(placeHolder != null){
                reHashRecursion(newTable, placeHolder);
            }
        }
        table = newTable;
        return null;
    }
    private void reHashRecursion(Inputs[] newTable, Inputs<Key, Value> placeHolder){
        if (placeHolder.next == null){
            int index = hashing(placeHolder.key.hashCode());
            if(newTable[index] == null){
                newTable[index] = placeHolder;
            } else{
                puttingInLink(newTable, placeHolder);
            }
        } else{
            reHashRecursion(newTable, placeHolder.next);
            puttingInLink(newTable, placeHolder);
        }
    }
    private void puttingInLink(Inputs[] newTable, Inputs<Key, Value> placeHolder){
        int index = hashing(placeHolder.key.hashCode());
        Inputs<Key, Value> holder = newTable[index];
        Inputs<Key, Value> previous = null;
        while (holder.key != null){
            previous = holder;
            holder = holder.next;
        }
        holder.next = placeHolder;
    }

    private int hashing(int k){
        return (k & 0x7fffffff) % this.table.length;
    }
    private class Inputs<Key, Value> {
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