package ca.concordia.coen346.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class RoundRobinExecutor implements Executor {
    private List<Worker> workers;
    private int index;
    public RoundRobinExecutor(int numThreads) {
        workers = new ArrayList<>(numThreads);
        this.index = 0;
        for (int i = 0; i < numThreads; i++) {
           Worker worker = new Worker();
           workers.add(worker);
           worker.start();
        }
    }

    @Override
    public void execute(Runnable processToRun) {
        if(this.index >= workers.size()) this.index = 0;
        Worker worker = workers.get(index);
        // Submit the process to the worker;
        worker.submit(processToRun);
        this.index++;
    }
}
