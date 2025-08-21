package com.xerm.bf;

import com.xerm.bf.events.BFEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(BFMod.MOD_ID)
public class BFMod {
    public static final String MOD_ID = "bf";

    public BFMod() {
        // Forge event bus
        MinecraftForge.EVENT_BUS.register(BFEvents.class);
    }
}
