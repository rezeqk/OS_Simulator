package ca.concordia.coen346.client;

import ca.concordia.coen346.server.Process;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketImpl;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ProducerClient {

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

            boolean run = true;

            while(run){
                System.out.println(reader.readLine());
                System.out.println("Please Enter The instruction you would like to write");
                String instructionfromUser = scanner.nextLine();

                switch (instructionfromUser) {
                    case "getNumitems" -> {
                        writer.println(Process.NUM_ITEMS);
                        System.out.println(reader.readLine());
                    }
                    case "getItemInPos" -> {
                        writer.println(Process.GET_ITEM);
                        System.out.println(reader.readLine());
                    }
                    case "getNextItemPos" -> {
                        writer.println(Process.GET_ITEM);
                        System.out.println(reader.readLine());
                    }
                    case "terminate" -> {
                        writer.println(Process.TERMINATE);
                        run = false;
                    }
                }

            }



        }catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }

    }

//    public void setItem(){
//
//    }
}
