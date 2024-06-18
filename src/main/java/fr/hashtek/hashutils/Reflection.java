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

        final Object handle = player.getClass().getMethod("getHandle").invoke(player);
        final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);

        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"))
            .invoke(playerConnection, packet);
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
