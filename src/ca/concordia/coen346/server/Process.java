package ca.concordia.coen346.server;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Process implements Runnable{

    public final static String NUM_ITEMS = "getNumItems";
    public final static String GET_ITEM = "getItemInPos";
    public final static String NEXT_ITEM_POS = "getNextItemPos";
    public final static String TERMINATE = "terminate";

    private final Integer processId;
    private final SocketChannel clientEndpoint;
    private final ByteBuffer readBuffer;
    private final ByteBuffer writeBuffer;


    // TODO: 2023-04-15 Delete this section, it's no longer needed
    private Buffer buffer;
<<<<<<< Updated upstream
    private BufferedReader reader;
    private PrintWriter writer;
    private int quantum;
=======
>>>>>>> Stashed changes
    private boolean toBeTerminated;


    public Process(int id, SocketChannel socket, Buffer buffer) throws Exception{
        int bufferSize = 1024;

        this.clientEndpoint = socket;
        System.out.println(clientEndpoint);
        this.readBuffer = ByteBuffer.allocate(bufferSize);
        this.writeBuffer = ByteBuffer.allocate(bufferSize);

        this.buffer = buffer;
        this.processId = id;
        this.toBeTerminated = false;

<<<<<<< Updated upstream
        System.out.println("Process " + processId + " is created");
=======
        // writing the PID to the client
        writeToClient(processId.toString());

>>>>>>> Stashed changes
    }

    // TODO: 2023-04-06 read and write to buffer


    // todo : implement the getNextItem Position 
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes

    public  int getPID(){
        return processId;
    }

<<<<<<< Updated upstream
    public void insertItem(int item, int pos){buffer.insertItem(item);}
    public static final void printInstruction(){
        System.out.println("Please select one of the following instruction");
        System.out.println("1 : " +
                "\n");
    }
=======
>>>>>>> Stashed changes

    // function to write messages to client


    @Override
    public void run() {
        System.out.println("Process " + getPID() + " is scheduled");

        long startTime = System.currentTimeMillis();
        long endTime = startTime + this.quantum;

<<<<<<< Updated upstream
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
                    writer.println("Item added to buffer is: " + item);
                }
                else if(instruction.equals(NEXT_ITEM_POS)){
                    int index = buffer.getNextPosition();
                    writer.println(index);
                }
                else if(instruction.equals("add")){
                    buffer.insertItem(Integer.parseInt(reader.readLine()));
                   writer.println("added to buffer");
                    System.out.println("added to buffer ");
                }
                else if(instruction.equals(TERMINATE)){
                }
            }catch(IOException e){
            }
        }

=======
        try {
            writeToClient("RUN");
            String instruction = null;

            instruction = readFromClient();
        switch (instruction) {
            case NUM_ITEMS -> {
                Integer numItems = buffer.size();
                writeToClient(numItems.toString());
                break;
            }
//            case GET_ITEM -> {
//                int item = buffer.getNextItem();
//                writer.println(item);
//            }
//            case NEXT_ITEM_POS -> {
//                int index = buffer.getNextPosition();
//                writer.println(index);
//            }
            case TERMINATE -> toBeTerminated = true;
            default -> System.out.println("Nothing happened");
        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
>>>>>>> Stashed changes
    }

    public String readFromClient() throws IOException{
        int bytesRead = clientEndpoint.read((readBuffer));
        if(bytesRead > 0){
            readBuffer.flip();

            //read from buffer
            byte[] data = new byte[readBuffer.remaining()];
            readBuffer.get(data);

            //clear
            readBuffer.clear();

            return new String(data);
        }
        return null;
    }

    public void writeToClient(String message) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes());
        clientEndpoint.write(writeBuffer);
    }



    public void setQuantum(int quantum){
        this.quantum = quantum;
    }
    public boolean toBeTerminated(){
        return toBeTerminated();
    }
}
