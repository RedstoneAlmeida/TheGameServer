package server.scheduler;

import server.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by ASUS on 27/01/2019.
 */
public class ServerScheduler {

    private Server server;
    private Map<Integer, Task> handlers = new HashMap<>();
    private Map<Task, Integer> schedulers = new HashMap<>();

    private int id = 0;

    public ServerScheduler(Server server){
        this.server = server;
    }

    public int addScheduler(Task task, int delay){
        handlers.put(id++, task);
        schedulers.put(task, delay);

        return id;
    }

    public void cancelTask(int id){
        if(!handlers.containsKey(id))
            return;
        Task task = handlers.get(id);
        if(schedulers.containsKey(task))
            schedulers.remove(task);
        handlers.remove(id);
    }

    public void reTask(int id){
        if(!handlers.containsKey(id))
            return;
        Task task = handlers.get(id);
        if(schedulers.containsKey(task))
            schedulers.replace(task, task.getDelay());
        handlers.remove(id);
    }

    public void tick(){
        for(int i : handlers.keySet()){
            Task task = handlers.get(i);
            if(!schedulers.containsKey(task)) continue;
            int delay = schedulers.get(task);
            if(delay <= 0){
                task.onRun();
                task.onCompletion(server);
                if(!task.isLoop())
                    this.cancelTask(i);
                else
                    this.reTask(i);
            } else {
                schedulers.replace(task, delay - 1);
            }
        }
    }

}
