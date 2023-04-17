package ca.concordia.coen346.server;


import java.io.*;
import java.net.Socket;

public class Process implements Runnable{

    public final static String NUM_ITEMS = "getNumItems";
    public final static String GET_ITEM = "getItemInPos";
    public final static String NEXT_ITEM_POS = "getNextItemPos";
    public final static String TERMINATE = "terminate";

    private final int processId;

    private final Buffer buffer;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean toBeTerminated;

    private long startTime;
    private long executionTime;


    public Process(int id, Socket socket, Buffer buffer) throws Exception{
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.buffer = buffer;
        this.processId = id;
        this.toBeTerminated = false;

        socket.setSoTimeout(OSSimulator.quantum);
        // writing the PID to the client
        writer.println(processId);

//        System.out.println("Process " + processId + " is created");
    }

    // TODO: 2023-04-06 read and write to buffer


    // todo : implement the getNextItem Position 
//    public void writingtoBuffer(){
//
//    }
//
//    public String readingfromBuffer(){
//        return null;
//    }

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
                case TERMINATE -> {
                    toBeTerminated = true;
//                    writer.println("Process will be terminated");
                }
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
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
