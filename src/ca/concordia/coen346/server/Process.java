package ca.concordia.coen346.server;


import java.io.*;
import java.net.Socket;

public class Process implements Runnable{

    public final static String NUM_ITEMS = "getNumItems";
    public final static String GET_ITEM = "getItemInPos";
    public final static String NEXT_ITEM_POS = "getNextItemPos";
    public final static String TERMINATE = "terminate";

    private final int processId;

    private Buffer buffer;
    private BufferedReader reader;
    private PrintWriter writer;
    private int quantum;
    private boolean toBeTerminated;


    public Process(int id, Socket socket, Buffer buffer) throws Exception{
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.buffer = buffer;
        this.processId = id;
        this.toBeTerminated = false;
        // writing the PID to the client
        writer.println(processId);

        System.out.println("Process " + processId + " is created");
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

    public void insertItem(int item, int pos){buffer.insertItem(item, pos);}
    public static final void printInstruction(){
        System.out.println("Please select one of the following instruction");
        System.out.println("1 : " +
                "\n");
    }

    // function to write messages to client
    public void sendMessage(String message){
        writer.println(message);
    }


    @Override
    public void run() {
        System.out.println("Process " + getPID() + " is scheduled");

        long startTime = System.currentTimeMillis();
        long endTime = startTime + this.quantum;

        // run the bloc for the time specified by quantum
        while(System.currentTimeMillis() < endTime){
            try{
                //msg to send to the client
                String msg = "Process with ID: " + processId + " is scheduled to run on thread " + Thread.currentThread().getName();
                sendMessage(msg);

                String instruction = reader.readLine();
                System.out.println(instruction);

                if(instruction.equals(NUM_ITEMS)){
                    int numItems = buffer.size();
                    writer.println(numItems);
                }
                else if(instruction.equals(GET_ITEM)){
                    int item = buffer.getNextItem();
                    writer.println(item);
                }
                else if(instruction.equals(NEXT_ITEM_POS)){
                    int index = buffer.getNextPosition();
                    writer.println(index);
                }
                else if(instruction.equals(TERMINATE)){
                }
            }catch(IOException e){
            }
        }

    }


    public void setQuantum(int quantum){
        this.quantum = quantum;
    }
    public boolean toBeTerminated(){
        return toBeTerminated();
    }
}
