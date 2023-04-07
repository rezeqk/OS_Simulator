package ca.concordia.coen346.server;

public class Buffer {
    private final static int BUFFER_SIZE = 10;

    private int[] buffer = new int[BUFFER_SIZE];
    private int in;
    private int out;
    private int count;


    public Buffer(){
        buffer[0] = 1;buffer[1] = 1;buffer[2] = 1;buffer[3] = 1;buffer[4] = 1;buffer[5] = 1;buffer[6] = 1;

    }

    public int readCount(){
        return count;
    }

    public int getNextItem(int pos){
        int item = buffer[pos];
        return item;
    }

    public void setIn(int pos){
        this.in = pos;
    }

    public int getNextPosition(){
        return in;
    }

    public void insertItem(int item, int pos){
        buffer[pos] = item;
    }


}
