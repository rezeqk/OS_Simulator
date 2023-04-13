package ca.concordia.coen346.server;

import java.net.Socket;
import java.util.ArrayList;

public class OSSimulator extends Thread{

    private final static int MAX_PROCESSES = 20;
    private final static int quantum = 5000;


    private ArrayList<Process> queue = new ArrayList<>(MAX_PROCESSES); //ready queue for process

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
        scheduler = new Scheduler(MAX_PROCESSES);
        executor = new RoundRobinExecutor(4);
    }


    public int createProcess(Socket clientEndPoint) throws Exception {

        int pid = pidmanager.allocatePid();
        Process process = new Process(pid, clientEndPoint, buffer);
        process.setQuantum(quantum);
        // Return -1 if there is not more space and 0 if
        return scheduler.addProcessToQueue(process);
    }



    public Process schedule(){
        return  scheduler.PickNextTask();

    }

    public void run(){
        while(true){
            /*
            Calling a blocking I/O operation: A thread can go into waiting mode when it makes
            a blocking I/O call such as read() or write() on a socket.
            * */

            //Schedule
            Process client = schedule();
            System.out.println(client);
            if(client == null){
                System.out.println("No process to schedule for now");
            }else{
                System.out.println("Process scheduled: " + client.getPID() + " on thread " + Thread.currentThread().getName());
                executor.execute(client);
                // removes process
                if (client.toBeTerminated()) remove(client);
            }

            //wait a bit
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void remove(Process process){
        try {
            int pid = process.getPID();
            //remove the process from the ready queue
            scheduler.removeProcess(pid);
            //release the pid
            pidmanager.releasePid(pid);

            process.sendMessage("The process that is terminated " +process.getPID()+ " the position is also "+ process.getPID());
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Some error ocurred");
            throw new RuntimeException(e);
        }

    }
}
