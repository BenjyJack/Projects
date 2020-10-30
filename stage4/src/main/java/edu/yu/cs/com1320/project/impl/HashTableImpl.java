package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.*;

public class HashTableImpl<Key, Value> implements HashTable<Key, Value> {
    private Inputs[] table = new Inputs[5];
    public HashTableImpl() {}

    public Value get(Key k) {
        return (Value) iterateThrough(table[keyToTable(k, table)], k);
    }

    public Value put(Key k, Value v) {
        return (Value) iterateThrough(table[keyToTable(k, table)], k, v);
    }

    private Value iterateThrough(Inputs<Key, Value> placement, Key k) {
        Inputs<Key, Value> position = placement;
        while (position != null) {
            if(position.key == k){
                break;
            }
            position = position.next;
        }
        if (position == null) {
            return null;
        }
        return position.value;
    }

    private Value iterateThrough(Inputs<Key, Value> position, Key k, Value v) {
        int counter = 0;
        Inputs<Key, Value> previous = null;
        Inputs<Key, Value> insert = new Inputs<>(k, v);
        if(table[keyToTable(k, table)] == null){
            addNew(insert, table);
            return null;
        }
        while (position != null) {
            if(position.key == k){
                break;
            }
            previous = position;
            position = position.next;
            counter++;
        }
        if (position == null && counter != 3) {
            addNew(insert, table);
            return null;
        }
        if (counter == 3) {
            rehash();
            addNew(insert, table);
            return null;
        }
        if(v == null){
            Value returner = position.value;
            if(previous == null){
                firstInTableDelete(position, k);
            }else{
                notFirstInTableDelete(position, previous);
            }
            return returner;
        }
        if(previous == null){
            table[keyToTable(k, table)] = insert;
        }else {
            previous.next = insert;
        }
        insert.next = position.next;
        return position.value;
    }
    private void firstInTableDelete(Inputs<Key, Value> position, Key k){
        if(position.next == null) {
            table[keyToTable(k, table)] = null;
        }else{
            table[keyToTable(k, table)] = position.next;
        }
    }
    private void notFirstInTableDelete(Inputs<Key, Value> position, Inputs<Key, Value> previous){
        if(position.next == null){
            previous.next = null;
        }else{
            previous.next = position.next;
        }
    }

    private void rehash() {
        Inputs[] rehashedTable = new Inputs[(int) Math.ceil(table.length * 1.5)];
        for (int i = 0; i < table.length; i++) {
            Inputs rehashing = table[i];
            rehash(rehashing, rehashedTable);
        }
        table = rehashedTable;
    }
    private void rehash(Inputs<Key, Value> input, Inputs[] rehashedTable){
        if(input == null){
            return;
        }
        rehash(input.next, rehashedTable);
        addNew(input, rehashedTable);
    }

    private void addNew(Inputs position, Inputs[] givenTable) {
        position.next = givenTable[keyToTable((Key) position.key, givenTable)];
        givenTable[keyToTable((Key) position.key, givenTable)] = position;
    }

    private int keyToTable(Key k, Inputs[] givenTable) {
        return (k.hashCode() & 0x7fffffff) % givenTable.length;
    }

    private class Inputs<Key, Value> {
        Key key;
        Value value;
        Inputs<Key, Value> next;

        private Inputs(Key k, Value v) {
            key = k;
            value = v;
            next = null;
        }
    }
}
