package fr.hashtek.hashutils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class ActionBar extends Reflection
{

    private final Constructor<?> chatComponentTextConstructor;
    private final Constructor<?> packetPlayOutChatConstructor;
    private final String text;


    /**
     * Creates a new instance of ActionBar.
     *
     * @param    text   Text to send
     */
    public ActionBar(String text)
        throws ClassNotFoundException, NoSuchMethodException
    {
        final Class<?> chatComponentTextClass = super.getNMSClass("ChatComponentText");
        final Class<?> packetPlayOutChatClass = super.getNMSClass("PacketPlayOutChat");

        this.chatComponentTextConstructor = chatComponentTextClass.getDeclaredConstructor(String.class);
        this.packetPlayOutChatConstructor = packetPlayOutChatClass.getDeclaredConstructor(
            super.getNMSClass("IChatBaseComponent"),
            byte.class
        );
        this.text = text;
    }


    /**
     * Sends the action bar message to a player
     *
     * @param   player      Player
     * @throws  Exception   Packet manipulation fail
     */
    public void send(Player player) throws Exception
    {
        final Object chatComponentText = chatComponentTextConstructor.newInstance(this.text);
        final Object packetPlayOutChat = packetPlayOutChatConstructor.newInstance(chatComponentText, (byte) 2);

        final Object craftPlayer = player.getClass()
            .getMethod("getHandle").invoke(player);
        final Object playerConnection = craftPlayer.getClass()
            .getField("playerConnection").get(craftPlayer);

        playerConnection.getClass().getMethod("sendPacket", super.getNMSClass("Packet"))
            .invoke(playerConnection, packetPlayOutChat);
    }

    /**
     * Broadcasts the action bar message to all players.
     *
     * @throws  Exception   Packet manipulation fail
     */
    public void broadcast() throws Exception
    {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            this.send(player);
    }

}
