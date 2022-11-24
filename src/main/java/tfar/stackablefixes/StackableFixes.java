package tfar.stackablefixes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.items.ItemHandlerHelper;
import tfar.stackablefixes.mixin.ItemAccess;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(StackableFixes.MODID)
public class StackableFixes {
    public static final String MODID = "stackablefixes";

    public StackableFixes() {
        // Register the setup method for modloading
        if (!FMLEnvironment.production) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
            makeStackable(Items.LAVA_BUCKET, Items.WATER_BUCKET, Items.MILK_BUCKET, Items.MUSHROOM_STEW,Items.MINECART);
    }

    static void makeStackable(Item... items) {
        for (Item item : items) {
            ((ItemAccess) item).setMaxStackSize(16);
        }
    }

    //return empty to let vanilla handle
    public static ItemStack handleBucket(ItemStack bucketStack, Player player) {
        BucketItem item = (BucketItem) bucketStack.getItem();
        boolean creative = player.getAbilities().instabuild;
        if (creative)return ItemStack.EMPTY;

        if (item.getFluid() != Fluids.EMPTY) {
            //decrease by one and give to player
            bucketStack.shrink(1);
            ItemHandlerHelper.giveItemToPlayer(player,new ItemStack(Items.BUCKET));
            return bucketStack;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack handleMushroomStew(ItemStack bowlFoodItemStack, LivingEntity livingEntity) {
        boolean creative = livingEntity instanceof Player player && player.getAbilities().instabuild;
        if (creative)return ItemStack.EMPTY;
        //decrease by one and give to livingEntity
        bowlFoodItemStack.shrink(1);
        if (livingEntity instanceof Player player) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(Items.BOWL));
        }
        return bowlFoodItemStack;
    }
}
