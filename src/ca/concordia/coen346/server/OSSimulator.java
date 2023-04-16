package ca.concordia.coen346.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OSSimulator extends Thread{

    private final static int MAX_PROCESSES = 20;
    public final static int quantum = 10000;


    private Queue<Process> readyQueue; //ready queue for process

    private Buffer buffer = new Buffer();
    private int numProcesses = 0;


    private int currClient = 0;

    private PIDManager pidmanager;

    private Scheduler scheduler;
    private RoundRobinExecutor executor;

    public OSSimulator () throws Exception {
        super();
        // Set the thread's name
        setName("OS simulator");
        pidmanager = new PIDManager();
        readyQueue = new LinkedList<>();
        scheduler = new Scheduler(MAX_PROCESSES, 4, readyQueue);
    }


    public int createProcess(Socket clientEndPoint) throws Exception {
        if(readyQueue.size() >= MAX_PROCESSES) return -1;
        int pid = pidmanager.allocatePid();
        Process process = new Process(pid, clientEndPoint, buffer);
        readyQueue.add(process);

//        for (int i = 0; i < MAX_PROCESSES; i++) {
//            Process p = new Process(pidmanager.allocatePid(), clientEndPoint, buffer);
//            queue.add(p);
//        }
        return 0;
    }



    public Process schedule(){
        return  scheduler.pickNextTask();

    }

    public void run(){
        scheduler.start();
//        System.out.println("OS started");

//        while(true){
            /*
            Calling a blocking I/O operation: A thread can go into waiting mode when it makes
            a blocking I/O call such as read() or write() on a socket.
            * */

            //garabage collector
//            for (int i = 0; i < readyQueue.size(); i++) {
//                Process process = readyQueue.get(i);
//                if(process.toBeTerminated() == true) remove(process);
//            }
            //Schedule
//            Process client = schedule();
//            System.out.println(client);
//            if(client == null){
//                System.out.println("No process to schedule for now");
//            }else{
//                System.out.println("Process scheduled: " + client.getPID() + " on thread " + Thread.currentThread().getName());
//                executor.execute(client);
//                // removes process
//                if (client.toBeTerminated()) remove(client);
//            }
//
//            //wait a bit
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
    public void remove(Process process){
        try {
            int pid = process.getPID();
            //remove the process from the ready queue
            readyQueue.remove(pid);
            //release the pid
            pidmanager.releasePid(pid);

            process.sendMessage("The process that is terminated " +process.getPID()+ " the position is also "+ process.getPID());
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Some error occurred");
            throw new RuntimeException(e);
        }

    }
}
