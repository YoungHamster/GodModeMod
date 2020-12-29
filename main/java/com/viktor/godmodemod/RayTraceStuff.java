package com.viktor.godmodemod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class RayTraceStuff {
    private static Vector3d getStartVec(PlayerEntity player){
        double startX = player.getPositionVec().x;
        double startY = player.getPositionVec().y + player.getEyeHeight();
        double startZ = player.getPositionVec().z;
        return new Vector3d(startX, startY, startZ);
    }

    private static Vector3d getEndVec(PlayerEntity player, Vector3d startVec, double range){
        double endX = startVec.x + player.getLookVec().x*range;
        double endY = startVec.y + player.getLookVec().y*range;
        double endZ = startVec.z + player.getLookVec().z*range;
        return new Vector3d(endX, endY, endZ);
    }

    public static BlockRayTraceResult rayTraceBlockFromPlayer(World world, PlayerEntity player, double range){
        Vector3d startVec = getStartVec(player);
        Vector3d endVec = getEndVec(player, startVec, range);
        return world.rayTraceBlocks(new RayTraceContext(startVec, endVec, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, null));
    }

    public static Entity rayTraceEntityFromPlayer(PlayerEntity player, World world, double range){
        Vector3d startVec = getStartVec(player);
        Vector3d endVec = getEndVec(player, startVec, range);
        AxisAlignedBB entitiesBB = new AxisAlignedBB(
                player.getPosX() - range, player.getPosY() - range, player.getPosZ() - range,
                player.getPosX() + range, player.getPosY() + range, player.getPosZ() + range);
        List<Entity> entities = world.getEntitiesInAABBexcluding(player, entitiesBB, null);
        for(Entity entity: entities){
            Optional<Vector3d> collision = entity.getBoundingBox().rayTrace(startVec, endVec);
            if(collision.isPresent())
                return entity;
        }
        return null;
    }
}
