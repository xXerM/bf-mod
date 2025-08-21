package com.xerm.bf.team;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class Kits {

    public static void giveTeamKit(ServerPlayer sp, Team team) {
        switch (team) {
            case RU -> giveRuKit(sp);
            case USA -> giveUsaKit(sp);
        }
    }

    private static void giveRuKit(ServerPlayer sp) {
        sp.getInventory().clearContent();

        give(sp, new ItemStack(getModItem("superbwarfare", "ak_47")));
        give(sp, new ItemStack(getModItem("superbwarfare", "awm")));
        give(sp, new ItemStack(getModItem("superbwarfare", "mp_443")));
        give(sp, new ItemStack(getModItem("superbwarfare", "ru_chest_6b43")));
        give(sp, new ItemStack(getModItem("superbwarfare", "ru_helmet_6b47")));
        give(sp, new ItemStack(getModItem("superbwarfare", "dog_tag")));
        give(sp, new ItemStack(getModItem("superbwarfare", "armor_plate"), 5));
        give(sp, new ItemStack(getModItem("superbwarfare", "medical_kit"), 5));
        give(sp, new ItemStack(getModItem("superbwarfare", "creative_ammo_box")));
    }

    private static void giveUsaKit(ServerPlayer sp) {
        sp.getInventory().clearContent();

        give(sp, new ItemStack(getModItem("superbwarfare", "m_4")));
        give(sp, new ItemStack(getModItem("superbwarfare", "awm")));
        give(sp, new ItemStack(getModItem("superbwarfare", "glock_17")));
        give(sp, new ItemStack(getModItem("superbwarfare", "us_chest_iotv")));
        give(sp, new ItemStack(getModItem("superbwarfare", "us_helmet_pastg")));
        give(sp, new ItemStack(getModItem("superbwarfare", "dog_tag")));
        give(sp, new ItemStack(getModItem("superbwarfare", "armor_plate"), 5));
        give(sp, new ItemStack(getModItem("superbwarfare", "medical_kit"), 5));
        give(sp, new ItemStack(getModItem("superbwarfare", "creative_ammo_box")));
    }

    private static void give(ServerPlayer sp, ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            if (!sp.getInventory().add(stack)) {
                sp.drop(stack, false);
            }
        }
    }

    private static Item getModItem(String modid, String name) {
        Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.fromNamespaceAndPath(modid, name));
        if (item == null) {
            System.out.println("[BF Mod] Item bulunamadı: " + modid + ":" + name);
            return net.minecraft.world.item.Items.AIR; // boş item döndür
        }
        return item;
    }
}
