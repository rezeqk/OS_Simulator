package ca.concordia.coen346.server;


import java.io.*;
import java.net.Socket;

public class Process implements Runnable{

    public final static String NUM_ITEMS = "getNumItems";
    public final static String GET_ITEM = "getItemInPos";
    public final static String NEXT_ITEM_POS = "getNextItemPos";
    public final static String TERMINATE = "terminate";
    public final static String PRODUCE =  "add";

    private final int processId;

    private final Buffer buffer;
    private final BufferedReader reader;
    private final PrintWriter writer;
    private boolean toBeTerminated;

    private long startTime;


    public Process(int id, Socket socket, Buffer buffer) throws Exception{
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.buffer = buffer;
        this.processId = id;
        this.toBeTerminated = false;

        socket.setSoTimeout(OSSimulator.quantum);
        // writing the PID to the client
        writer.println(processId);

    }


    public  int getPID(){
        return processId;
    }

    public void insertItem(int item){buffer.insertItem(item);}

    // function to write messages to client
    public void sendMessage(String message){
        writer.println(message);
    }


    @Override
    public void run() {

        this.startTime = System.currentTimeMillis();
        System.out.println("Process " + getPID() + " is scheduled on thread " + Thread.currentThread().getName());

        writer.println("RUN");
        String instruction = null;
        try {
            instruction = reader.readLine();
            if(instruction ==null) return;

            switch (instruction) {
                case NUM_ITEMS -> {
                    int numItems = buffer.size();
                    writer.println(numItems);
                }
                case GET_ITEM -> {
                    int item = buffer.getNextItem();
                    writer.println(item);
                }
                case NEXT_ITEM_POS -> {
                    int index = buffer.getNextPosition();
                    writer.println(index);
                }
                case PRODUCE -> {
                    String num = reader.readLine();
                    if(num == null) return;

                    Integer numberToAdd = Integer.parseInt(num);
                    buffer.insertItem(numberToAdd);
                    int numItems= buffer.size();
                    writer.println("The number items is now : " + numItems);
                }
                case TERMINATE -> toBeTerminated = true;
            }
        } catch (IOException e) {
            //do nothing
        }

    }


    public boolean toBeTerminated(){
        return toBeTerminated;
    }

    //measure the execution time since the process has run
    public long executionTime(){
        long endTime = System.currentTimeMillis();
        return endTime - this.startTime;
    }
}
