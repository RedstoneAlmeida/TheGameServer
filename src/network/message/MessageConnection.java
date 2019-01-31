package network.message;

import network.protocol.TextPacket;
import server.Player;
import server.Server;

import java.util.Collection;

/**
 * Created by ASUS on 16/01/2019.
 */
public class MessageConnection {

    public static String HIGH_MESSAGE_ERROR = "Você enviou uma mensagem muito grande!";

    public static void sendText(Player player, String text, int type){
        TextPacket packet = new TextPacket();
        packet.eid = player.getRuntimeId();
        packet.name = player.getUsername();
        packet.message = text;
        packet.type = type;

        MessageConnection.sendTextPacket(packet, player.getSession().getServer().getOnlinePlayers().values());
    }

    public static void sendTextPacket(TextPacket packet, Collection<Player> players){
        for(Player player : players){
            if(player.getRuntimeId() == packet.eid) continue;
            player.dataPacket(packet);
        }
    }

    public static void sendConsoleMessage(TextPacket packet){
        System.out.println(String.format("(%s) %s: %s", packet.eid, packet.name, packet.message));
    }

}
