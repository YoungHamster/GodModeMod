package com.viktor.godmodemod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;

public class StickHandler {

    public static boolean playeralreadyinvincible = false;

    private static final RayTraceStuff rayTraceStuff = new RayTraceStuff();

    @SubscribeEvent
    public void onStickUse(PlayerInteractEvent.RightClickItem event) {
        if(Thread.currentThread().getThreadGroup() != SidedThreadGroups.SERVER) return;
        if(!event.getItemStack().hasTag()) return;
        if (event.getItemStack().getTag().toString().contains("killing stick")) {
            Entity entity = rayTraceStuff.rayTraceEntityFromPlayer(event.getPlayer(), event.getWorld(), 50);
            if(entity != null) {
                event.getPlayer().sendMessage(new StringTextComponent("Woosh! It's gone"),
                        event.getPlayer().getUniqueID());
                entity.remove();
            }
        }
        if (event.getItemStack().getTag().toString().contains("spawning stick")) {
            BlockRayTraceResult result = rayTraceStuff.rayTraceBlockFromPlayer(event.getWorld(), event.getPlayer(), 20);
            double dx = 0;
            double dy = 0;
            double dz = 0;
            switch(result.getFace())
            {
                case UP: dy = 1;
                    break;
                case DOWN: dy = -1;
                    break;
                case NORTH: dz = -1;
                    break;
                case SOUTH: dz = 2;
                    break;
                case WEST: dx = -1;
                    break;
                case EAST: dx = 2;
                    break;
            }
            CowEntity entity = new CowEntity(EntityType.COW, event.getWorld());
            entity.setPosition(result.getPos().getX() + dx, result.getPos().getY() + dy, result.getPos().getZ() + dz);
            event.getWorld().addEntity(entity);
            event.getPlayer().sendMessage(new StringTextComponent("You spawned a cow"),
                    event.getPlayer().getUniqueID());
        }
        if (event.getItemStack().getTag().toString().contains("invincibility stick")) {
            playeralreadyinvincible = !playeralreadyinvincible;
            event.getPlayer().setInvulnerable(playeralreadyinvincible);
            if(playeralreadyinvincible){
                event.getPlayer().sendMessage(new StringTextComponent("You've made yourself invincible"),
                        event.getPlayer().getUniqueID());
            }
            else{
                event.getPlayer().sendMessage(new StringTextComponent("You've made yourself not invincible anymore"),
                        event.getPlayer().getUniqueID());
            }
        }
    }

    @SubscribeEvent
    public void tellPlayerTheyreAGodNow(AnvilRepairEvent event){
        if(Thread.currentThread().getThreadGroup() != SidedThreadGroups.SERVER) return;
        if(event.getItemResult().getTag().toString().contains("killing stick")){
            event.getPlayer().sendMessage(new StringTextComponent("So you're now a god of death"),
                    event.getPlayer().getUniqueID());
        }
        if(event.getItemResult().getTag().toString().contains("spawning stick")){
            event.getPlayer().sendMessage(new StringTextComponent("So you're now a god of life"),
                    event.getPlayer().getUniqueID());
        }
        if(event.getItemResult().getTag().toString().contains("invincibility stick")){
            event.getPlayer().sendMessage(new StringTextComponent("So you're now a god"),
                    event.getPlayer().getUniqueID());
        }
    }
}
