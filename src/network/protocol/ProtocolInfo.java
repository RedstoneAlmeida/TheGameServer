package network.protocol;

/**
 * Created by ASUS on 13/01/2019.
 */
public interface ProtocolInfo {

    byte HANDLER_PACKET = 0x01;
    byte GAME_START_PACKET = 0x02;
    byte RUNTIME_PLAYER_PACKET = 0x03;
    byte ADD_ENTITY_PACKET = 0x04;
    byte MOVE_PLAYER_PACKET = 0x05;
    byte ANIMATION_PACKET = 0x06;
    byte TEXT_PACKET = 0x07;

    byte GENERATE_ENTITY_PACKET = 0x12;

}
