package com.viktor.godmodemod;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.launch.Phases;

import java.util.List;

public class StickHandler {

    public static boolean playeralreadyinvincible = false;

    // Event is called 2 times every time player right clicks so i use this counter to execute it only 1 time
    public static int onStickUseCounter = 0;
    private static final RayTraceStuff rayTraceStuff = new RayTraceStuff();

    @SubscribeEvent
    public void onStickUse(PlayerInteractEvent.RightClickItem event) {
        onStickUseCounter += 1;
        if(onStickUseCounter % 2 == 1) return;
        if(!event.getItemStack().hasTag()) return;
        if (event.getItemStack().getTag().toString().contains("killing stick")) {
            event.getPlayer().getLookVec();
        }
        if (event.getItemStack().getTag().toString().contains("spawning stick")) {
            BlockRayTraceResult result = rayTraceStuff.rayTraceBlockFromPlayer(event.getWorld(), event.getPlayer(), 20);
            CowEntity entity = new CowEntity(EntityType.COW, event.getWorld());
            double dx = 0;
            double dy = 0;
            double dz = 0;
            switch(result.getFace())
            {
                case UP: dy = 1;
                    break;
                case DOWN: dy = -1;
                    break;
                case NORTH: dx = -1;
                    break;
                case SOUTH: dx = 1;
                    break;
                case WEST: dz = -1;
                    break;
                case EAST: dz = 1;
                    break;
            }
            entity.setPosition(result.getPos().getX() + dx, result.getPos().getY() + dy, result.getPos().getZ() + dz);
            event.getWorld().addEntity(entity);
        }
        if (event.getItemStack().getTag().toString().contains("invincibility stick")) {
            playeralreadyinvincible = !playeralreadyinvincible;
            event.getPlayer().setInvulnerable(playeralreadyinvincible);
        }
    }
}
