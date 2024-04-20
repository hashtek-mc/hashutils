package fr.hashtek.hashutils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar
{

    private final PacketPlayOutChat packet;


    /**
     * Creates a new instance of ActionBar.
     *
     * @param    text   Text to send
     */
    public ActionBar(String text)
    {
        this.packet = new PacketPlayOutChat(
            IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"),
            (byte) 2
        );
    }


    /**
     * Sends the action bar message to a player
     *
     * @param   player  Player
     */
    public void send(Player player)
    {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(this.packet);
    }

    /**
     * Broadcasts the action bar message to all players.
     */
    public void broadcast()
    {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            this.send(player);
    }

}
