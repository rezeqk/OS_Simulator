package ca.concordia.coen346.server;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final static int BUFFER_SIZE = 10;
    private ArrayList <Integer> buffer;
    private int out;
    private int count;

    private int index =0;

    private final Lock lock = new ReentrantLock();

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
        lock.lock();
        try{
            int temp =0;
            int pos = buffer.size() -1;
            temp = buffer.get(pos);
            buffer.remove(pos);
            return temp;
        } finally {
            lock.unlock();
        }
    }

    public void setIn(int pos){
        this.index = pos;
    }

    public int getNextPosition(){
        return index;
    }


    public void insertItem(int item){
        lock.lock();
        try{
            if(buffer.size() < BUFFER_SIZE){
                buffer.add(item);
                index++;
            }
        }finally{
            lock.unlock();
        }

    }

    public void removeItem(int pos)
    {
        lock.lock();
        try
        {
            buffer.remove(pos);
        }
        finally
        {
            lock.unlock();
        }
    }


}