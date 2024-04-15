package fr.hashtek.hashutils;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class Title extends Reflection
{

    /**
     * Sends out a title to a player.
     *
     * @param   player      Player to send the title to
     * @param   fadeInTime  Fade in time
     * @param   showTime    Show time
     * @param   fadeOutTime Fade out time
     * @param   title       Title
     * @param   subtitle    Subtitle
     */
    public void send(
        Player player,
        int fadeInTime,
        int showTime,
        int fadeOutTime,
        String title,
        String subtitle
    ) throws Exception
    {
        final Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
            .invoke(null, "{\"text\": \"" + title + "\"}");
        final Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
            getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
            int.class, int.class, int.class);
        final Object packet = titleConstructor.newInstance(
            getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
            fadeInTime, showTime, fadeOutTime);

        final Object chatsTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
            .invoke(null, "{\"text\": \"" + subtitle + "\"}");
        final Constructor<?> stitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
            getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
            int.class, int.class, int.class);
        final Object spacket = stitleConstructor.newInstance(
            getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
            fadeInTime, showTime, fadeOutTime);

        sendPacket(player, packet);
        sendPacket(player, spacket);
    }

}
