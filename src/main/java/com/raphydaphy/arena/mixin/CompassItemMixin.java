package com.raphydaphy.arena.mixin;

import com.raphydaphy.arena.Arena;
import net.minecraft.client.network.packet.ChatMessageS2CPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CompassItem.class)
public abstract class CompassItemMixin extends Item {
    public CompassItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) return super.use(world, player, hand);
        else {
            ServerPlayerEntity nearest = Arena.getNearestPlayer(player.getServer(), (ServerPlayerEntity)player);
            Text text = new LiteralText(Formatting.BLUE + "The nearest player is " + nearest.getEntityName() + " at " + (int)nearest.x + ", " + (int)nearest.y + ", " + (int)nearest.z);
            if (nearest == player) text = new LiteralText(Formatting.RED + "There are no other players in your dimension!");
            ((ServerPlayerEntity)player).networkHandler.sendPacket((new ChatMessageS2CPacket(text, MessageType.GAME_INFO)));
            return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
        }
    }
}
