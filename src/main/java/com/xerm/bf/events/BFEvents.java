package com.xerm.bf.events;

import com.xerm.bf.world.TeamSpawn;
import com.xerm.bf.world.TeamSpawns;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.xerm.bf.team.Kits;
import com.xerm.bf.team.Team;


@Mod.EventBusSubscriber(modid = "bf")
public class BFEvents {

    @SubscribeEvent
    public static void onCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("ruspawn").requires(cs -> cs.hasPermission(2))
                        .executes(ctx -> setSpawn(ctx, "ru"))
        );

        event.getDispatcher().register(
                Commands.literal("usaspawn").requires(cs -> cs.hasPermission(2))
                        .executes(ctx -> setSpawn(ctx, "usa"))
        );

        event.getDispatcher().register(
                Commands.literal("rujoin").executes(ctx -> joinTeam(ctx, "ru"))
        );

        event.getDispatcher().register(
                Commands.literal("usajoin").executes(ctx -> joinTeam(ctx, "usa"))
        );
    }

    private static int setSpawn(CommandContext<CommandSourceStack> ctx, String team) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        ServerLevel level = player.serverLevel();
        TeamSpawn spawn = new TeamSpawn(level.dimension(), player.blockPosition(), player.getYRot(), player.getXRot());
        TeamSpawns.setSpawn(team, spawn);
        ctx.getSource().sendSuccess(() -> net.minecraft.network.chat.Component.literal(team + " spawn set!"), true);
        return 1;
    }

    private static int joinTeam(CommandContext<CommandSourceStack> ctx, String team) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        MinecraftServer server = player.getServer();
        TeamSpawn spawn = TeamSpawns.getSpawn(team);

        if (spawn == null) {
            ctx.getSource().sendFailure(net.minecraft.network.chat.Component.literal("No spawn set for team " + team));
            return 0;
        }

        ServerLevel targetLevel = server.getLevel(spawn.dimension());
        if (targetLevel == null) {
            ctx.getSource().sendFailure(net.minecraft.network.chat.Component.literal("Invalid dimension for team " + team));
            return 0;
        }

        player.teleportTo(targetLevel, spawn.pos().getX() + 0.5, spawn.pos().getY(), spawn.pos().getZ() + 0.5, spawn.yaw(), spawn.pitch());

        // Kit verme örneği
        // Burada doğrudan Kits.java kullanıyoruz
        if (team.equals("ru")) {
            Kits.giveTeamKit(player, Team.RU);
        } else if (team.equals("usa")) {
            Kits.giveTeamKit(player, Team.USA);
        } else {
            return 0; // geçersiz takım olursa
        }

        return 1;

    }   }
