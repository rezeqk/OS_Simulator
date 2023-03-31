package ca.concordia.coen346.client;

import ca.concordia.coen346.server.Process;

import javax.imageio.IIOException;
import java.io.*;
import java.net.Socket;

public class ConsumerClient {

    ConsumerClient(){

    }
        public static void main (String []args){
            try{

                Socket socket = new Socket("localhost ",8000);
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter (output,true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                // reads ID
                String ID = reader.readLine();

                //get signal instruction from the server
                 String instructionFromServer = reader.readLine();

                 if(instructionFromServer.equals("RUN")){
                     writer.println(Process.TERMINATE);
                 }

            }catch (IOException e){

            }
        }
}
