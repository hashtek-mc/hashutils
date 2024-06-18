package fr.hashtek.hashutils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reflection
{

    /**
     * Sends a packet to a player.
     *
     * @param   player  Player
     * @param   packet  Packet
     */
    public void sendPacket(Player player, Object packet)
        throws Exception
    {
        if (!player.isOnline())
            throw new Exception("Player is not online.");

        final Object playerConnection = this.getPlayerConnection(player);

        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"))
            .invoke(playerConnection, packet);
    }

    /**
     * @param   player      Player
     * @return  Player's PlayerConnection as an abstract object.
     * @throws  Exception   Either if player is not online or class exception.
     */
    public Object getPlayerConnection(Player player)
        throws Exception
    {
        if (!player.isOnline())
            throw new Exception("Player is not online.");

        final Object handle = player.getClass().getMethod("getHandle").invoke(player);

        return handle.getClass().getField("playerConnection").get(handle);
    }

    /**
     * @param   name    NMS name
     * @return  NMS class
     */
    public Class<?> getNMSClass(String name)
        throws ClassNotFoundException
    {
        return Class.forName(
            "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name
        );
    }

}
