package com.raphydaphy.arena.mixin;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.network.packet.ChatMessageS2CPacket;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Shadow
	public ServerPlayNetworkHandler networkHandler;

	@Inject(at = @At("RETURN"), method = "onDeath")
	private void onDeath(DamageSource source, CallbackInfo info) {
		PlayerEntity player = ((ServerPlayerEntity)(Object)this);
		Text text = new LiteralText(ChatFormatting.YELLOW + "You died at " + (int)player.x + ", " + (int)player.y + ", " + (int)player.z + "!");
		this.networkHandler.sendPacket((new ChatMessageS2CPacket(text, MessageType.SYSTEM)));
	}
}
