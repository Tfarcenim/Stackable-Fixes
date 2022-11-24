package tfar.stackablefixes.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.stackablefixes.StackableFixes;

@Mixin(BucketItem.class)
public class BucketItemMixin {

    @Inject(method = "getEmptySuccessItem",at = @At("HEAD"),cancellable = true)
    private static void handleEmptyBucket(ItemStack pBucketStack, Player pPlayer, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack handle = StackableFixes.handleBucket(pBucketStack,pPlayer);
        if (!handle.isEmpty()) {
            cir.setReturnValue(handle);
        }
    }
}
