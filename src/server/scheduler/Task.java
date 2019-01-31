package server.scheduler;

import server.Server;

/**
 * Created by ASUS on 27/01/2019.
 */
public abstract class Task {

    public abstract void onRun();

    public void onCompletion(Server server){

    }

    public boolean isLoop(){
        return false;
    }

    public int getDelay(){
        return 0;
    }

}
