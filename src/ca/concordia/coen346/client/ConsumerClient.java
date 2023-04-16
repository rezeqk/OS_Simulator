package ca.concordia.coen346.client;

<<<<<<< Updated upstream
import ca.concordia.coen346.server.Process;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
=======
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
>>>>>>> Stashed changes
import java.util.Scanner;
import java.util.Set;

public class ConsumerClient {
<<<<<<< Updated upstream

    ConsumerClient(){

    }
        public static void main (String []args){
            try(Socket socket = new Socket("localhost",8000)){
                System.out.println("Client connected");
                Scanner scanner = new Scanner(System.in);

                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter (output,true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                // reads ID
                String ID = reader.readLine();
                System.out.println("New process created with ID: " + ID);
                boolean run = true;

                while(run){
                    System.out.println(reader.readLine());
                    System.out.println("Please select the instruction you would like to send ");
                    String instructionfromUser = scanner.nextLine();

                    switch (instructionfromUser){
                        case "getNumItems" :
                            writer.println(Process.NUM_ITEMS);
                            System.out.println(reader.readLine());
                            break;
                        case "next item":
                            writer.println(Process.GET_ITEM);
                            System.out.println(reader.readLine());

                        case "next item position":
                            writer.println(Process.NEXT_ITEM_POS);
                            System.out.println(reader.readLine());
                        case "terminate":
                            writer.println(Process.TERMINATE);
                            //read the answer
                            System.out.println(reader.readLine());
                            run= false;
                            break;
                        case "add":
                           reader.readLine();
                            System.out.println(reader.readLine());
                        default:
                            writer.println("none");
                    }
                }




            } catch(UnknownHostException exception){
                System.out.printf("Server not found: %s%n", exception.getMessage());
            } catch (IOException e){
                System.out.printf("I/O ERROR: %s%n", e.getMessage());
            }
        }
=======

    private static String instructionFromUser = null;
    static Scanner scanner = new Scanner(System.in);

    private static boolean run = true;

    public static void main(String[] args) throws IOException {
        try (SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8000))) {
            System.out.println("Client connected");

            channel.configureBlocking(false);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder responseBuilder = new StringBuilder();

            while (run) {
                getMessageFromUser();

                if (instructionFromUser != null) {
                    buffer.clear();
                    buffer.put(instructionFromUser.getBytes());
                    buffer.flip();
                    channel.write(buffer);
                    buffer.clear();
                    instructionFromUser = null;
                }

                int readyChannels = selector.selectNow();
                if (readyChannels == 0) {
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();

                for (SelectionKey key : selectedKeys) {
                    if (key.isReadable()) {
                        responseBuilder.setLength(0);

                        while (true) {
                            buffer.clear();
                            int bytesRead = channel.read(buffer);
                            if (bytesRead <= 0) {
                                break;
                            }
                            buffer.flip();
                            responseBuilder.append(new String(buffer.array(), 0, bytesRead));
                        }

                        String fromServer = responseBuilder.toString();
                        if (fromServer.equals("RUN")) {
                            sendInstructionToServer(channel);
                        }
                    }
                }

                selectedKeys.clear();
            }

            System.out.println("Process terminated");

        }
    }

    private static void getMessageFromUser() {
        if (instructionFromUser == null) {
            System.out.print("Enter instruction: ");
            instructionFromUser = scanner.nextLine();
        }
    }

    private static void sendInstructionToServer(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        getMessageFromUser();
        buffer.put(instructionFromUser.getBytes());
        buffer.flip();
        channel.write(buffer);
        buffer.clear();
        instructionFromUser = null;
    }
>>>>>>> Stashed changes
}
