package ca.concordia.coen346.server;

import java.util.ArrayList;

public class Scheduler {
    int positon;

    int MaxProcesses;
    private ArrayList <Process> queue;

    public Scheduler(int MaxProcesses){
        positon = 0;
        this.queue= new ArrayList<>();
        this.MaxProcesses = MaxProcesses;

    }


    public Process PickNextTask(){
        // check if queue is empty
        if (queue.isEmpty()) return null;
        // check position of if out of bounds
        if (positon >= queue.size()) positon=0;
        //picking next task
        return queue.get(positon++);
    }

    public int addProcess (Process process){
        if (queue.size() == MaxProcesses)  return -1;
        //add a process
        queue.add(process);
        return 0;
    }

    public int removeProcess (int pid){
        Process temp;
        //check if queue is empty
        if (queue.isEmpty()) return -1;
        // go through queue and get the process with the PID
        for (int i= 0; i< queue.size();i++){
            //retrive process at current location
            temp = queue.get(i);
            // check if the pid of temp matches the one that should be deleted
            if(pid == temp.getPID()) queue.remove(i);
            break;
        }
        return 0;
    }


}

