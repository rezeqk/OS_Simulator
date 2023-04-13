package ca.concordia.coen346.server;

public class Worker extends Thread{
    private Object lock;
    private Runnable process;

    public Worker(){
        this.lock = new Object();
        this.process = null;
    }
    @Override
    public void run() {
        while(true){
            synchronized (lock){
                // if the process is not submitted, then it is null, if it is null then the lock is on wait
                while (process == null){
                    try{
                        lock.wait();
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                        System.out.println(e.getMessage());
                        return;
                    }
                }
                //if the process is not null turn
                process.run();
                // reset
                process = null;

            }
        }
    }

    public void submit(Runnable process) {
        synchronized (lock){
            this.process = process;
            this.lock.notify();
        }
    }
}
