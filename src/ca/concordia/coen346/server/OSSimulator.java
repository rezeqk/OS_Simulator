package ca.concordia.coen346.server;

import java.net.Socket;
import java.util.ArrayList;

public class OSSimulator extends Thread{

    private final static int MAX_PROCESSES = 20;


    private ArrayList<Process> queue = new ArrayList<>(MAX_PROCESSES); //ready queue for process

    private Buffer buffer = new Buffer();
    private int numProcesses = 0;


    private int currClient = 0;

    private PIDManager pidmanager;

    public OSSimulator () throws Exception {
        pidmanager = new PIDManager();

    }

    public int createProcess(Socket clientSocket) throws Exception {
        if(numProcesses == MAX_PROCESSES){
            return -1;
        }
        int pid = pidmanager.allocatePid();
        //get id and assign to that position
        Process process = new Process(pid, clientSocket, buffer);
        queue.add(process);
        numProcesses++;

        //add to queue
        System.out.println("Process created" + numProcesses);
        return 0;
    }



    public Process schedule(){
        //Select next process
        if(queue.size()==0) return null;
        if(currClient >= queue.size()) currClient =0;

        return queue.get(currClient);
    }

    public void run(){
        while(true){
            //Schedule
            System.out.println("Scheduling");
            Process client = schedule();
            System.out.println(client);
            if(client != null) {
                int result = client.run(1);
                if (result == -1) {
                    //delete process from queue and processes
                    //release PID
                    try {
                        pidmanager.releasePid(client.getPID());
                        queue.remove(client.getPID());
                        client.messager("The process that is terminated " +client.getPID()+ " the position is also "+ client.getPID());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    System.out.println("Result: "+ result);
                }
            }
        }
    }
}
