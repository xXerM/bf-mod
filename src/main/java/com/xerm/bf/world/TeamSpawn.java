package com.xerm.bf.world;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.google.gson.JsonObject;

public class TeamSpawn {
    private final ResourceKey<Level> dimension;
    private final BlockPos pos;
    private final float yaw;
    private final float pitch;

    public TeamSpawn(ResourceKey<Level> dimension, BlockPos pos, float yaw, float pitch) {
        this.dimension = dimension;
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public ResourceKey<Level> dimension() {
        return dimension;
    }

    public BlockPos pos() {
        return pos;
    }

    public float yaw() {
        return yaw;
    }

    public float pitch() {
        return pitch;
    }

    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("dim", dimension.location().toString());
        obj.addProperty("x", pos.getX());
        obj.addProperty("y", pos.getY());
        obj.addProperty("z", pos.getZ());
        obj.addProperty("yaw", yaw);
        obj.addProperty("pitch", pitch);
        return obj;
    }

    public static TeamSpawn fromJson(JsonObject t) {
        ResourceLocation rl = ResourceLocation.tryParse(t.get("dim").getAsString());
        ResourceKey<Level> dim = ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION, rl);

        BlockPos pos = new BlockPos(t.get("x").getAsInt(), t.get("y").getAsInt(), t.get("z").getAsInt());
        float yaw = t.get("yaw").getAsFloat();
        float pitch = t.get("pitch").getAsFloat();

        return new TeamSpawn(dim, pos, yaw, pitch);
    }
}
