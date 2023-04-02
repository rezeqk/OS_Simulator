package ca.concordia.coen346.client;

import ca.concordia.coen346.server.Process;

import javax.imageio.IIOException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ConsumerClient {

    ConsumerClient(){

    }
        public static void main (String []args){
            try(Socket socket = new Socket("localhost",8000)){
                System.out.println("Cliend connected");
                Scanner scanner = new Scanner(System.in);

                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter (output,true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                // reads ID
                String ID = reader.readLine();

                System.out.println("THE ID is " + ID);
                System.out.println("New process created with ID: " + ID);
                //get signal instruction from the server
//                 String instructionFromServer = reader.readLine();


//                     if(instructionfromUser.equals("terminate"))
//                     {
//                     writer.println(instructionfromUser);
//                     System.out.println(reader.readLine());
//                         System.out.println(reader.readLine());
//                     }
                System.out.println(reader.readLine());

                    System.out.println("Please select the instruction you would like to send ");
                    String instructionfromUser = scanner.nextLine();

                    if (instructionfromUser.equals("getNumItems"))
                    {
                        System.out.println(reader.readLine());
                        writer.println(Process.NUM_ITEMS);
                        System.out.println(reader.readLine());
                    }


            } catch(UnknownHostException exception){
                System.out.printf("Server not found: %s%n", exception.getMessage());
            } catch (IOException e){
                System.out.printf("I/O ERROR: %s%n", e.getMessage());
            }
        }
}
