package ca.concordia.coen346.server;

public class Worker extends Thread{
    private final Object lock;
    private Runnable currProcess;
    private Runnable prevProcess;
    private boolean isAvailable;
    private boolean finishedTask;

    public Worker(){
        this.lock = new Object();
        this.currProcess = null;
        this.prevProcess = null;
        this.isAvailable = true;
        this.finishedTask = false;
    }
    @Override
    public void run() {
        while(true){
            synchronized (lock){
                // if the process is not submitted, then it is null, if it is null then the lock is on wait
                while (this.currProcess == null){
                    try{
                        lock.wait();
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                        System.out.println(e.getMessage());
                        return;
                    }
                }

                // this worker cannot take other task while executing this
                this.isAvailable = false;

                //save the process;
                this.prevProcess = currProcess;
                //run the process
                this.currProcess.run();

//                printExecTime();
                //if task finishes before being preempted
                this.finishedTask = true;
                this.prevProcess = currProcess;
                this.currProcess = null;
            }
        }
    }

    public void submit(Runnable process) {
        synchronized (lock){
            this.currProcess = process;
            this.lock.notify();
        }
    }


    public Runnable getPrevProcess(){return this.prevProcess;}
    public Runnable getCurrProcess(){return this.currProcess;}

    public void setAvailable(){
        this.isAvailable = true;
        this.finishedTask = false;
        this.prevProcess = null;
        this.currProcess = null;
    }

    public boolean isAvailable(){return isAvailable;}

    public boolean finishedTask() {return finishedTask;}
    public void printExecTime(){
        Process p = (Process) currProcess;
        System.out.println("Exec Time: " + p.executionTime());
    }
}
