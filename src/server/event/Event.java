package server.event;

/**
 * Created by ASUS on 30/01/2019.
 */
public abstract class Event {

    public boolean cancelled = false;

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
