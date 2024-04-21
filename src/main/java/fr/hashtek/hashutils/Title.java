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
        final Object chatTitle = super.getNMSClass("IChatBaseComponent")
            .getDeclaredClasses()[0]
            .getMethod("a", String.class)
            .invoke(null, "{\"text\": \"" + title + "\"}");
        final Constructor<?> titleConstructor = super.getNMSClass("PacketPlayOutTitle")
            .getConstructor(
                super.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                super.getNMSClass("IChatBaseComponent"),
                int.class, int.class, int.class
            );
        final Object packet = titleConstructor.newInstance(
            super.getNMSClass("PacketPlayOutTitle")
                .getDeclaredClasses()[0]
                .getField("TITLE")
                .get(null),
            chatTitle, fadeInTime, showTime, fadeOutTime
        );

        final Object chatsTitle = super.getNMSClass("IChatBaseComponent")
            .getDeclaredClasses()[0]
            .getMethod("a", String.class)
            .invoke(null, "{\"text\": \"" + subtitle + "\"}");
        final Constructor<?> stitleConstructor = super.getNMSClass("PacketPlayOutTitle")
            .getConstructor(
                super.getNMSClass("PacketPlayOutTitle")
                .getDeclaredClasses()[0],
                super.getNMSClass("IChatBaseComponent"),
                int.class, int.class, int.class
            );
        final Object spacket = stitleConstructor.newInstance(
            super.getNMSClass("PacketPlayOutTitle")
                .getDeclaredClasses()[0]
                .getField("SUBTITLE")
                .get(null),
            chatsTitle, fadeInTime, showTime, fadeOutTime
        );

        super.sendPacket(player, packet);
        super.sendPacket(player, spacket);
    }

}
