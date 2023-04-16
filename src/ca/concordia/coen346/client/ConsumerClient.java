package ca.concordia.coen346.client;

import ca.concordia.coen346.server.Process;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//TODO :  Add event listener
public class ConsumerClient {
    private static String instructionFromUser = null;
    private static boolean responseHasNotArrived = true;
    static Scanner scanner = new Scanner(System.in);

    private static boolean run = true;


    ConsumerClient() {

    }

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8000)) {
            System.out.println("Client connected");

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // reads ID
            String ID = reader.readLine();
            System.out.println("New process created with ID: " + ID);

            while (run) {
                getMessageFromUser();
                if (reader.readLine().equals("RUN")) {
                    sendInstructionToServer(writer,reader);
                    instructionFromUser = null;
                }

                //wait for the response
                while(responseHasNotArrived){
                    String msg =  reader.readLine();
                    if(msg.equals("RUN"))continue;
                    System.out.println(msg);
                    responseHasNotArrived = false;
                    break;
                }

            }
            System.out.println("Process terminated");
        }
    }

    public static String getMessageFromUser(){
        if(instructionFromUser != null) return null;
        System.out.println("Please select the instruction you would like to send ");
        instructionFromUser = scanner.nextLine();
        return instructionFromUser;
    }

    public static void sendInstructionToServer(PrintWriter writer,BufferedReader reader) throws IOException{
        switch (instructionFromUser){
            case "getNumItems" :
                writer.println(Process.NUM_ITEMS);
                break;
            case "next item":
                writer.println(Process.GET_ITEM);
                System.out.println(reader.readLine());

            case "next item position":
                writer.println(Process.NEXT_ITEM_POS);
                System.out.println(reader.readLine());
            case "terminate":
                writer.println(Process.TERMINATE);
                run= false;
                break;
            default:
                writer.println("none");
        }

    }
}
