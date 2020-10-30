package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Trie;

import java.util.*;

public class TrieImpl<Value> implements Trie<Value> {
    private static final int alphabetSize = 256; // extended ASCII
    private Node<Value> root = new Node<>(); // root of trie
    public TrieImpl(){}
    public void put(String key, Value val) {
        //deleteAll the value from this key
        if (val == null)
        {
            this.deleteAll(key);
        }
        else
        {
            String[] splitKey = key.toLowerCase().split("\\W+");
            for (String word : splitKey) {
                this.root = put(this.root, word, val, 0);
            }
        }
    }
    private Node<Value> put(Node<Value> x, String key, Value val, int d) {
        //create a new node
        if (x == null)
        {
            x = new Node<Value>();
        }
        //we've reached the last node in the key,
        //set the value for the key and return the node
        if (d == key.length())
        {
            if(x.val == null){
                x.val = new ArrayList<>(1);
            }
            if(x.val.contains(val)){
                return x;
            }
            x.val.add(val);
            return x;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        x.links[c] = this.put(x.links[c], key, val, d + 1);
        return x;
    }

    public List<Value> getAllSorted(String key, Comparator<Value> comparator) {
        List<Value> results = new ArrayList<>();
        Node<Value> wordNode = getAllSorted(root, key, 0);
        if(wordNode == null || wordNode.val == null){
            return null;
        }
        results = wordNode.val;
        results.sort(comparator);
        return results;
    }

    private Node<Value> getAllSorted(Node<Value> x, String key, int d){
        if (x == null)
        {
            return null;
        }
        //we've reached the last node in the key,
        //return the node
        if (d == key.length())
        {
            return x;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        return this.getAllSorted(x.links[c], key, d + 1);
    }

    public List<Value> getAllWithPrefixSorted(String prefix, Comparator comparator) {
        List<Value> results = new ArrayList();
        getAllWithPrefixSorted(root, prefix, 0, results);
        if(results == null){
            return null;
        }
        results.sort(comparator);
        return results;
    }
    private void getAllWithPrefixSorted(Node<Value> x, String key, int d, List<Value> results){
        if (x == null)
        {
            return;
        }
        //we've reached the last node in the key,
        //return the node
        if (d == key.length())
        {
            plugAndChug(x, results);
            return;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        this.getAllWithPrefixSorted(x.links[c], key, d + 1, results);
    }

    private void plugAndChug(Node<Value> x, List<Value> results){
        if(x == null){
            return;
        }
        if(x.val != null) {
            for (Value val : x.val) {
                if (!results.contains(val)) {
                    results.add(val);
                }
            }
        }
        for (Node<Value> node: x.links) {
            plugAndChug(node, results);
        }
    }

    public Set<Value> deleteAllWithPrefix(String prefix) {
        Set<Value> results = new HashSet<>();
        deleteAllWithPrefix(root, prefix, 0, results);
        if(results == null){
            return null;
        }
        return results;
    }
    private void deleteAllWithPrefix(Node<Value> x, String key, int d, Set<Value> results){
        if (x == null)
        {
            return;
        }
        //we've reached the last node in the key,
        //return the node
        if (d == key.length())
        {
            plugAndChug(x, results);
            x = null;
            return;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        this.deleteAllWithPrefix(x.links[c], key, d + 1, results);
    }
    private void plugAndChug(Node<Value> x, Set<Value> results){
        if(x == null){
            return;
        }
        if(x.val != null) {
            for (Value val : x.val) {
                if (!results.contains(val)) {
                    results.add(val);
                }
            }
            x.val = null;
        }
        for (Node<Value> node: x.links) {
            plugAndChug(node, results);
        }
    }

    public Set<Value> deleteAll(String key) {
        return deleteAll(root, key, 0);
    }

    private Set<Value> deleteAll(Node x, String key, int d)
    {
        //link was null - return null, indicating a miss
        if (x == null)
        {
            return null;
        }
        //we've reached the last node in the key,
        //return the node
        if (d == key.length())
        {
            Set<Value> set = convertToSet(x.val);
            x.val = null;
            return set;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        return (Set) this.deleteAll(x.links[c], key, d + 1);
    }
    private Set<Value> convertToSet(List<Value> list){
        Set<Value> set = new HashSet();
        set.addAll(list);
        return set;
    }

    public Value delete(String key, Value val) {
        return delete(root, key, 0, val);
    }
    private Value delete(Node<Value> x, String key, int d, Value val)
    {
        //link was null - return null, indicating a miss
        if (x == null)
        {
            return null;
        }
        //we've reached the last node in the key,
        //return the node
        if (d == key.length())
        {
            if(nullCheck(x.val, val)){
                x.val.remove(val);
                return val;
            }
            return null;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        return (Value) delete(x.links[c], key, d + 1, val);
    }
    private boolean nullCheck(List list, Value val){
        return list.contains(val);
    }

    private static class Node<Value> {
        protected List<Value> val= new ArrayList<>();
        protected Node[] links = new Node[TrieImpl.alphabetSize];
    }
}
