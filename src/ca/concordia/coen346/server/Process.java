package ca.concordia.coen346.server;

import java.io.*;
import java.net.Socket;

public class Process {

    public final static String NUM_ITEMS = "getNumItems";
    public final static String GET_ITEM = "getItemInPos";
    public final static String NEXT_ITEM_POS = "getNextItemPos";
    public final static String TERMINATE = "terminate";

    private int processId;

    private Buffer buffer;
    private BufferedReader reader;
    private PrintWriter writer;

    public Process(int id, Socket socket, Buffer buffer) throws Exception{
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.buffer = buffer;
        this.processId = id;
        // writing the PID to the client
        writer.println(processId);

        System.out.println("Process " + processId + " is created");
    }

    // TODO: 2023-04-06 read and write to buffer 
    public int run(int times){
        System.out.println("Process " + getPID() + " is schedulled");

        for(int i = 0; i < times; i++) {
            try{
                //msg to send to the client
                String msg = "Process with ID: " + processId + " is scheduled to run on thread " + Thread.currentThread().getName();
                sendMessage(msg);

                String instruction = reader.readLine();
                System.out.println(instruction);

                if(instruction.equals(NUM_ITEMS)){
                    getNumberofItems();
                }
                else if(instruction.equals(GET_ITEM)){
                    //read position from client
                    int position=0;
                    getItem(position);
                }
                else if(instruction.equals(NEXT_ITEM_POS)){
                    getNextItemPosition();
                }
                else if(instruction.equals(TERMINATE)){
                    return -1;
                }
            }catch(IOException e){
                return -1;
            }
        }
        return 0;
    }


    public void getNumberofItems() throws IOException {
        System.out.println("Number of items" + 0);
        int numItems = buffer.size();
        writer.println(numItems);
    }
    
    

    // todo : implement the getNextItem Position 
    public void getNextItemPosition(){

    }

    public  int getPID(){
        return processId;
    }

    public void getItem(int position){
        int item = buffer.getNextItem();
        //send to client
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
}
