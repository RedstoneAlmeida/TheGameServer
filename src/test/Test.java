package test;

import network.protocol.GameStartPacket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Test {

    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost", 9500);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            GameStartPacket pk = new GameStartPacket();
            pk.name = "Teste";
            pk.encode();
            out.writeByte(pk.pid());
            byte[] buffer = pk.getBuffer();
            out.writeInt(buffer.length);
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
