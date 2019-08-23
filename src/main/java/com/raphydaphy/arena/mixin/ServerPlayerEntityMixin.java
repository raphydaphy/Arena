package com.raphydaphy.arena.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.packet.ChatMessageS2CPacket;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	@Shadow
	public ServerPlayNetworkHandler networkHandler;

	public ServerPlayerEntityMixin(World world, GameProfile profile) {
		super(world, profile);
	}

	@Inject(at = @At("RETURN"), method = "onDeath")
	private void onDeath(DamageSource source, CallbackInfo info) {
		Text text = new LiteralText(Formatting.YELLOW.toString() + "You died at " + (int)this.x + ", " + (int)this.y + ", " + (int)this.z + "!");
		this.networkHandler.sendPacket((new ChatMessageS2CPacket(text, MessageType.SYSTEM)));
	}
}
