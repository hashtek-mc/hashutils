package fr.hashtek.hashutils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

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

    public static Player getAimedPlayer(Player player, int range)
    {
        final ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
        final ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, range);
        final ArrayList<Location> sight = new ArrayList<Location>();

        for (Entity e : entities)
            if (!(e instanceof Player))
                entities.remove(e);

        for (int i = 0; i < sightBlock.size(); i++)
            sight.add(sightBlock.get(i).getLocation());

        for (int i = 0; i < sight.size(); i++)
            for (int k = 0 ; k < entities.size() ;k++)
                if (Math.abs(entities.get(k).getLocation().getX() - sight.get(i).getX()) < 1.3)
                    if (Math.abs(entities.get(k).getLocation().getY() - sight.get(i).getY()) < 1.5)
                        if (Math.abs(entities.get(k).getLocation().getZ() - sight.get(i).getZ()) < 1.3)
                            return (Player) entities.get(k);

        return null;
    }

}
