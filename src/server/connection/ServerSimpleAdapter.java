package server.connection;

import network.protocol.DataPacket;
import server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ASUS on 13/01/2019.
 */
public class ServerSimpleAdapter {

    private PlayerSession session;

    public ServerSimpleAdapter(PlayerSession session){
        this.session = session;
    }

    public void write(byte pid, byte[] buffer, int length) throws IOException {
        try {
            DataOutputStream output = new DataOutputStream(session.getSocket().getOutputStream());
            output.writeByte(pid);
            output.writeInt(length);
            output.write(buffer);
        } catch (Exception e) {
            if(e.getMessage().contains("Broken")){
                session.getServer().getOnlinePlayers().remove(session.getPlayer().getRuntimeId());
                session.getServer().getEntities().remove(session.getPlayer().getRuntimeId());
                session.getSocket().close();
            }
        }
    }

    public void read() throws IOException{
        if(session.getSocket() == null || session.getSocket().getInputStream() == null)
            return;
        DataInputStream entrada = new DataInputStream(session.getSocket().getInputStream());
        byte id = entrada.readByte();
        int lenght = entrada.readInt();
        byte[] buffer = new byte[lenght];
        entrada.readFully(buffer);

        DataPacket packet = session.getServer().getNetwork().getPacket(id);
        packet.setBuffer(buffer);
        packet.decode();

        this.session.putPoolPacket(packet);
    }

}
