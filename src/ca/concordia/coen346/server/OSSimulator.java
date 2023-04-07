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

    private Scheduler scheduler;

    public OSSimulator () throws Exception {
        super();
        // Set the thread's name
        setName("OS simulator");
        pidmanager = new PIDManager();
        scheduler = new Scheduler(MAX_PROCESSES);
    }


    public int createProcess(Socket clientEndPoint) throws Exception {

        int pid = pidmanager.allocatePid();
        Process process = new Process(pid, clientEndPoint, buffer);

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
            if(client == null){
                System.out.println("No process to schedule for now");
            }else{
                System.out.println("Process scheduled: " + client.getPID());
                int result = client.run(1);

                // TODO : refactor this as in make a function because tmi
                if (result == -1) {
                    //release PID
                    try {

                        // get the pid of the process to remove
                        int pid = client.getPID();
                        //remove the process from the ready queue
                        scheduler.removeProcess(pid);
                        //release the pid
                        pidmanager.releasePid(client.getPID());

                        client.messager("The process that is terminated " +client.getPID()+ " the position is also "+ client.getPID());
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        System.out.println("Some error ocurred");
                        throw new RuntimeException(e);
                    }
                }
            }

            //wait a bit
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
