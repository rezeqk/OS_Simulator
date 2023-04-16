package ca.concordia.coen346.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;

public class ProcessServer {

    private static OSSimulator simulator;
    public static void main(String[] args) throws Exception {
        simulator = new OSSimulator();
        simulator.start();
        try {
//            ServerSocket socket = new ServerSocket(8000);
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(8000));

            //set blocking to false to avoid scheduling issues
            serverSocket.configureBlocking(false);

            while(true) {
                System.out.println("Thread : " +Thread.currentThread().getName() + " -- Server waiting for connections");

<<<<<<< Updated upstream
                printListOfThreads();
//                System.out.println("Server waiting for connections");
                Socket client = socket.accept();
=======
                SocketChannel client = serverSocket.accept();
                if (client == null)  continue;
>>>>>>> Stashed changes
                if (simulator.createProcess(client) == -1) {
                    client.socket().sendUrgentData(-1);
                    client.close();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // print all the threads and relevant information about them
    static void printListOfThreads(){
        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
        for (Thread thread : threads.keySet()) {
            if(thread.getName().equals("OS simulator") || thread.getName().equals("main")){
                System.out.println("Thread name: " + thread.getName());
                System.out.println("Thread ID: " + thread.getId());
                System.out.println("Thread state: " + thread.getState());
                System.out.println("Thread stack trace: ");
                StackTraceElement[] stackTrace = threads.get(thread);
                for (StackTraceElement element : stackTrace) {
                    System.out.println("\t" + element);
                }
                System.out.println();
            }
        }

    }
}
