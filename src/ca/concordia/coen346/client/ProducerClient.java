package ca.concordia.coen346.client;

import ca.concordia.coen346.server.Process;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ProducerClient {
    private static String instructionFromUser = null;
    private static boolean responseHasNotArrived = true;
    private static boolean run = true;

    public static void main(String[] args){
        int id;
        try(Socket socket = new Socket("localhost", 8000)){
            System.out.println("Client connected");

            Scanner scanner =new Scanner(System.in);
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));


            //get the pid assigned from the server
            String fromServer = reader.readLine();
            id = Integer.parseInt(fromServer);
            System.out.println("PID: " + id);

            while(run){
                // preempt the user for instruction
                String num =  getMessageFromUser(scanner);
               //if the server send a signal to run
                if(reader.readLine().equals("RUN")){
                    sendInstructionToServer(writer,reader,num);

                    // reset
                    instructionFromUser = null;
                    responseHasNotArrived = true;
                }


                //wait for the response
                while(responseHasNotArrived){
                    String response = reader.readLine();
                    if(response.equals("RUN"))continue;
                    System.out.println(response);
                    responseHasNotArrived = false;
                    break;
                }

            }


        }catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }

    }

    private static void sendInstructionToServer(PrintWriter writer, BufferedReader reader, String num) {

        switch (instructionFromUser) {
            case "add" -> {
                writer.println(Process.PRODUCE);
                writer.println(num);
            }
            case "terminate" -> {
                writer.println(Process.TERMINATE);
                run = false;
                break;
            }
        }
    }

    // prompt the user for an instruction and ask for the number
    public static String getMessageFromUser(Scanner scanner){

        String number = null;
        //if there is already a message do nothing
        if(instructionFromUser != null) return null;

        // else get the message
        System.out.print("Please select the instruction you would like to send ");
        instructionFromUser = scanner.nextLine();

        System.out.print("What number would you like to add : ");
        if(instructionFromUser.equals("add")) number = scanner.nextLine();

        return number;
    }

}
