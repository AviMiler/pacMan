package services.DB;

import java.util.ArrayList;

public class Arrays<T>{

    ArrayList<T> list;

    public Arrays() {
        list = new ArrayList<T>();
    }

    public T get(int i){
        return list.get(i);
    }

    public void set(int i, T t){
        list.set(i, t);
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
