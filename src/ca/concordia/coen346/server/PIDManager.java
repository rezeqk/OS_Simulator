package ca.concordia.coen346.server;

import java.util.BitSet;

public class PIDManager {

    private static final int MIN_PID = 0;
    private static final int MAX_PID = 200;
    BitSet pidMap;

    /*Constructor */

    public PIDManager() throws  Exception{
        allocateMap();
    }
    /*Tracks*/
    void allocateMap() throws Exception{
        pidMap = new BitSet(MAX_PID);
    }

    public int allocatePid() throws Exception{
        int index;
        for(index = 0;index <=200 ;index++){
            if(pidMap.get(index)==false){ // if false --> there is some space
                break;
            }
        }
        int pid = index;
        if(pid > MAX_PID || pid < MIN_PID){ // no space for new pids
            throw new Exception("Unable to allocate pid, all PID have been allocated");
        }
        pidMap.set(index, true);
        return pid;
    }
    void releasePid(int pid) throws Exception {
        int index = pid - MIN_PID;
        if(pid > MAX_PID || pid < MIN_PID|| pidMap.get(index) == false){
            throw new Exception("Pid does not exist");
        }
        pidMap.set(index, false);
    }
}