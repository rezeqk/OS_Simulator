package ca.concordia.coen346.server;

import java.util.ArrayList;

public class Buffer {
    private final static int BUFFER_SIZE = 10;
    private ArrayList <Integer> buffer;
    private int in;
    private int out;
    private int count;

    private int index =0;



    public Buffer(){

        buffer =  new ArrayList<>();
        buffer.add(10);
        buffer.add(10);
        buffer.add(10);
        buffer.add(10);
        buffer.add(10);
        buffer.add(10);

    }

    //size of buffer
    public int size(){
        return buffer.size();
    }
    public int readCount(){
        return count;
    }

    public int getNextItem(){
        int temp =0;
        temp = buffer.get(index);
        buffer.remove(index);
        return temp;
    }

    public void setIn(int pos){
        this.in = pos;
    }

    public int getNextPosition(){
        return in;
    }

    //TODO: add code when it doesnt happen
    public void insertItem(int item, int pos){
        if(buffer.size() < BUFFER_SIZE){buffer.add(pos,item);}
    }

    public void removeItem(int pos){
        buffer.remove(pos);

    }


}
