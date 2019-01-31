package server;

import java.net.InetSocketAddress;

/**
 * Created by ASUS on 12/01/2019.
 */
public class OpenServer {

    public final static String PATH = System.getProperty("user.dir") + "/";
    public final static String DATA_PATH = System.getProperty("user.dir") + "/";
    public final static String PLUGIN_PATH = DATA_PATH + "plugins/";

    public static void main(String[] args){
        // 145.239.222.240
        try {
            Server server = new Server(new InetSocketAddress("0.0.0.0", 9500));
            server.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
