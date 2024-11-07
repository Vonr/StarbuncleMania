package alexthw.starbunclemania.common.block.wixie_stations;

import alexthw.starbunclemania.registry.FarmerDelightCompat;
import alexthw.starbunclemania.wixie.FarmerDelightRecipeWrappers;
import com.hollingsworth.arsnouveau.api.recipe.MultiRecipeWrapper;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FarmerPotWixieCauldronTile extends WixieCauldronTile implements GeoBlockEntity {
    private boolean isMixing;

    public FarmerPotWixieCauldronTile(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return FarmerDelightCompat.COOKING_POT_WIXIE_CAULDRON_TILE.get();
    }

    @Override
    public MultiRecipeWrapper getRecipesForStack(ItemStack stack) {
        return FarmerDelightRecipeWrappers.PotRecipeWrapper.fromStack(stack, level);
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.saveAdditional(compound, pRegistries);
        compound.putBoolean("isMixing", this.isMixing);
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.loadAdditional(compound, pRegistries);
        this.isMixing = compound.getBoolean("isMixing");
    }

    @Override
    public void onCraftStart() {
        this.isMixing = true;
        updateBlock();
        super.onCraftStart();
    }

    @Override
    public void onCraftingComplete() {
        this.isMixing = false;
        setChanged();
        super.onCraftingComplete();
    }

    public static final RawAnimation stir = RawAnimation.begin().thenLoop("animation.pot_stirrer.stir");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar animatableManager) {
        animatableManager.add(new AnimationController<>(this, "rotate_controller", 40,
                e -> isMixing ? e.setAndContinue(stir) : PlayState.STOP)
        );
    }

    private final AnimatableInstanceCache manager = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return manager;
    }

}
