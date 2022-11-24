package tfar.stackablefixes.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.stackablefixes.StackableFixes;

@Mixin(BowlFoodItem.class)
public class BowlFoodItemMixin {

    @Inject(method = "finishUsingItem",at = @At("RETURN"),cancellable = true)
    private static void handleEmptyBucket(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack handle = StackableFixes.handleMushroomStew(pStack,pEntityLiving);
        if (!handle.isEmpty()) {
            cir.setReturnValue(handle);
        }
    }
}
