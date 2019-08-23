package com.raphydaphy.arena;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class Arena implements ModInitializer {
	@Override
	public void onInitialize() {
	}

	public static ServerPlayerEntity getNearestPlayer(MinecraftServer server, ServerPlayerEntity player) {
		int closestSquared = Integer.MAX_VALUE;
		ServerPlayerEntity closest = player;
		for (ServerPlayerEntity player2 : server.getPlayerManager().getPlayerList()) {
			if (player != player2 && player.dimension == player2.dimension) {
				BlockPos dist = player.getBlockPos().subtract(player2.getBlockPos());
				int distSq = dist.getX() * dist.getX() + dist.getY() * dist.getY() + dist.getZ() * dist.getZ();
				if (distSq < closestSquared) {
					closestSquared = distSq;
					closest = player2;
				}
			}
		}
		return closest;
	}
}
