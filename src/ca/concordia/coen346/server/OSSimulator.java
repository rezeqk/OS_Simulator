package ca.concordia.coen346.server;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class OSSimulator extends Thread{

    private final static int MAX_PROCESSES = 10;
    public final static int quantum = 10000;


    private volatile Queue<Process> readyQueue = new LinkedList<>(); //ready queue for process

    private final Buffer buffer = new Buffer();



    private final PIDManager pidmanager;

    private final Scheduler scheduler;

    public OSSimulator () throws Exception {
        super();
        // Set the thread's name
        setName("OS simulator");
        pidmanager = new PIDManager();

        scheduler = new Scheduler(MAX_PROCESSES, 2, readyQueue);
        scheduler.start();
    }


    public int createProcess(Socket clientEndPoint) throws Exception {
        if(readyQueue.size() >= MAX_PROCESSES) return -1;
        int pid = pidmanager.allocatePid();
        Process process = new Process(pid, clientEndPoint, buffer);
        readyQueue.add(process);
        return 0;
    }


    public void run(){
        while(true){
        //remove process to be terminated
        for (Process process: readyQueue) {
            if(process.toBeTerminated()){remove(process);}
        }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }
    public void remove(Process process){
        try {
            int pid = process.getPID();

            //remove the process from the ready queue
            readyQueue.remove(process);
            //release the pid
            pidmanager.releasePid(pid);
            process.sendMessage("The process that is terminated " +process.getPID()+ " the position is also "+ process.getPID());
            Thread.sleep(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
