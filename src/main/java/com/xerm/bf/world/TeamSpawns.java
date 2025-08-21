package com.xerm.bf.world;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TeamSpawns {
    private static final Map<String, TeamSpawn> spawns = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Path file = Path.of("config", "bf_spawns.json");

    public static void setSpawn(String team, TeamSpawn spawn) {
        spawns.put(team, spawn);
        save();
    }

    public static TeamSpawn getSpawn(String team) {
        return spawns.get(team);
    }

    public static void load() {
        if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file)) {
                JsonObject obj = gson.fromJson(reader, JsonObject.class);
                for (String key : obj.keySet()) {
                    JsonObject t = obj.getAsJsonObject(key);
                    TeamSpawn spawn = TeamSpawn.fromJson(t);
                    spawns.put(key, spawn);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        JsonObject obj = new JsonObject();
        for (Map.Entry<String, TeamSpawn> entry : spawns.entrySet()) {
            obj.add(entry.getKey(), entry.getValue().toJson());
        }
        try {
            Files.createDirectories(file.getParent());
            try (Writer writer = Files.newBufferedWriter(file)) {
                gson.toJson(obj, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
