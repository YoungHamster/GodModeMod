package com.viktor.godmodemod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class RayTraceStuff {
    public static BlockRayTraceResult rayTraceBlockFromPlayer(World world, PlayerEntity player, double range){
        double startX = player.getPositionVec().x;
        double startY = player.getPositionVec().y + player.getHeight();
        double startZ = player.getPositionVec().z;
        Vector3d startVec = new Vector3d(startX, startY, startZ);
        double endX = startX + player.getLookVec().x*range;
        double endY = startY + player.getLookVec().y*range;
        double endZ = startZ + player.getLookVec().z*range;
        Vector3d endVec = new Vector3d(endX, endY, endZ);
        return world.rayTraceBlocks(new RayTraceContext(startVec, endVec, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, player));
    }
}
