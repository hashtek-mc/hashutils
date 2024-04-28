package fr.hashtek.hashutils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Particle extends Reflection
{

    public void spawnParticle(
        Player player,
        String particle,
        Location location,
        float speed,
        int amount
    )
        throws Exception
    {
        this.spawnParticle(
            player,
            particle,
            location,
            0, 0, 0,
            speed,
            amount,
            new int[] {}
        );
    }

    public void spawnParticle(
        Player player,
        String particle,
        Location location,
        float offsetX,
        float offsetY,
        float offsetZ,
        float speed,
        int amount,
        int[] data
    )
        throws Exception
    {
        final Object packet = this.getParticlePacket(
            particle,
            location,
            offsetX, offsetY, offsetZ,
            speed,
            amount,
            data
        );

        super.sendPacket(player, packet);
    }

    private Object getParticlePacket(
        String particle,
        Location location,
        float offsetX,
        float offsetY,
        float offsetZ,
        float speed,
        int count,
        int[] data
    ) throws Exception
    {
        final Class<?> packetClass = super.getNMSClass("PacketPlayOutWorldParticles");
        final Object particleEnum = Enum.valueOf((Class<? extends Enum>) super.getNMSClass("EnumParticle"), particle);

        return packetClass
            .getConstructor(
                super.getNMSClass("EnumParticle"),
                boolean.class,
                float.class, float.class, float.class,
                float.class, float.class, float.class,
                float.class,
                int.class,
                int[].class
            )
            .newInstance(
                particleEnum,
                true,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                offsetX, offsetY, offsetZ,
                speed,
                count,
                data
            );
    }

}
