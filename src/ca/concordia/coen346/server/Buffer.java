package ca.concordia.coen346.server;

import java.util.ArrayList;

public class Buffer {
    private final static int BUFFER_SIZE = 10;
    private final ArrayList <Integer> buffer;
    private int out;
    private int count;

    private int index =0;



    public Buffer(){

        buffer =  new ArrayList<>();

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
        temp = buffer.get(0);
        buffer.remove(index);
        return temp;
    }

    public void setIn(int pos){
        this.index = pos;
    }

    public int getNextPosition(){
        return index;
    }

    //TODO: add code when it doesnt happen
    public void insertItem(int item){
        if(buffer.size() < BUFFER_SIZE){
            buffer.add(item);
        }
    }


}
