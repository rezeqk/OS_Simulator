package ca.concordia.coen346.server;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scheduler extends Thread {
    private final int MaxProcesses;
    int pIndex; // index to keep track of the process
    int wIndex; // index to keep track of the workers
    boolean run;

    private List<Worker> workers;
    private Queue<Process> queue;

    public Scheduler(int maxProcesses, int numThreads, Queue<Process> queue){
        this.pIndex = this.wIndex = 0;
        this.MaxProcesses = maxProcesses;
        this.queue = queue;
        this.run = true;
        this.workers = new ArrayList<>(numThreads);
        for (int i = 0; i < numThreads; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
        }

    }

    public void run(){
    while (run){
        //Loop through the list of workers and preempt
        for (Worker worker: workers) {
            Process process = (Process) worker.getCurrProcess();
            if(worker.finishedTask()) {
                freeWorker(worker, process);

            }else if(process == null){
                continue;
            } else if(process.executionTime() > OSSimulator.quantum) {
                try{
                    worker.join(500);
                } catch(InterruptedException e){
                    freeWorker(worker,process);
                    System.out.println("Exec time exceeeds maximum "+ process.executionTime());
                }
            }
        }


        //Schedule
        // loop through the list of workers and schedule tasks
        for (Worker worker: workers) {
            // if a worker is available and the next task is not null, schedule it
            if(worker.isAvailable()){
                Process task = pickNextTask();
                if(task !=null) worker.submit(task);
            }
        }

        }
    }


    public void freeWorker(Worker worker, Process process){
        //save the process being run
        process = (Process) worker.getPrevProcess();

        // free the worker to take on other tasks
        worker.setAvailable();
        queue.add(process);

    }
    public Process pickNextTask(){
        // check if queue is empty
        if(queue.peek() == null ) return null;
        return queue.remove();
    }

    public int addProcessToQueue(Process process){
        // if queue full
        if (queue.size() == MaxProcesses)  return -1;
        //add a process
        queue.add(process);
        return 0;
    }

    public int removeProcess (int pid){
        //check if queue is empty
        if (queue.isEmpty()) return -1;
        // go through queue and get the process with the PID to remover
        for (Process temp : queue) {
                if(pid == temp.getPID()) queue.remove(temp);
                break;
        }
        return 0;
    }

    public void printQueue(){
        for (Process process : queue
        ) {
            System.out.println(process.getPID());
        }
    }

}

