package fr.hashtek.hashutils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HashUtils
{

    public static class World {

        private final org.bukkit.World world;


        public World(org.bukkit.World world)
        {
            this.world = world;
        }


        public void clearItems()
        {
            final List<Entity> entityList = this.world.getEntities();

            for (Entity current : entityList)
                if (current.getType() == EntityType.DROPPED_ITEM)
                    current.remove();
        }

    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value)
    {
        for (Map.Entry<T, E> entry : map.entrySet())
            if (Objects.equals(value, entry.getValue()))
                return entry.getKey();
        return null;
    }

    public static Player getAimedPlayer(Player player)
    {
        final Location playerEye = player.getEyeLocation();

        for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
            if (!(entity instanceof Player))
                continue;

            final Player p = (Player) entity;
            final Vector vector = p.getEyeLocation().toVector().subtract(playerEye.toVector());
            final double dot = vector.normalize().dot(playerEye.getDirection());

            if (dot > 0.99d)
                return p;
        }

        return null;
    }

}
