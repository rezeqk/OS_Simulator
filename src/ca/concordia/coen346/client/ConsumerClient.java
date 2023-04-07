package ca.concordia.coen346.client;

import ca.concordia.coen346.server.Process;

import javax.imageio.IIOException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//TODO :  Add event listener
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
                System.out.println("New process created with ID: " + ID);
                System.out.println(reader.readLine());

                //

                System.out.println("Please select the instruction you would like to send ");
                String instructionfromUser = scanner.nextLine();

                switch (instructionfromUser){
                    case "terminate" :
                        writer.println(Process.TERMINATE);
                        //read the answer
                        System.out.println(reader.readLine());
                        break;
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
                    default:
                        writer.println("none");
                }

            } catch(UnknownHostException exception){
                System.out.printf("Server not found: %s%n", exception.getMessage());
            } catch (IOException e){
                System.out.printf("I/O ERROR: %s%n", e.getMessage());
            }
        }
}
