package data.ineerDB;

import java.util.ArrayList;

public class LinkedList <T>{

    java.util.LinkedList<T> list;

    public LinkedList() {
        list = new java.util.LinkedList<T>();
    }

    public T get(int i){
        return list.get(i);
    }

    public void set(int i, T t){
        list.set(i, t);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void addToHead(T t){
        list.addFirst(t);
    }

    public void add(T t){
        list.add(t);
    }

    public void addAtIndex(int i, T t){
        list.add(i,t);
    }

    public void removeHead(){
        list.removeFirst();
    }

    public int size(){
        return list.size();
    }

}
