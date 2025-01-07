package data.ineerDB;

import java.util.ArrayList;

public class Arrays<T>{

    ArrayList<T> list;

    public Arrays() {
        list = new ArrayList<T>();
    }

    public T get(int i){
        try {
            return list.get(i);
        }
        catch (Exception e){
            return null;
        }
    }

    public void set(int i, T t){
        list.set(i, t);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public T getLast(){
        return list.getLast();
    }

    public void add(T t){
        list.add(t);
    }

    public int size(){
        return list.size();
    }

}
