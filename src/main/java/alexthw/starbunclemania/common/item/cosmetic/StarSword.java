package alexthw.starbunclemania.common.item.cosmetic;

import alexthw.starbunclemania.starbuncle.sword.StarbyFighterBehavior;
import com.hollingsworth.arsnouveau.api.entity.IDecoratable;
import com.hollingsworth.arsnouveau.api.item.ICosmeticItem;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarStarbuncle;
import com.hollingsworth.arsnouveau.common.items.EnchantersSword;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class StarSword extends EnchantersSword implements ICosmeticItem {

    public StarSword(Properties pProperties) {
        super(Tiers.GOLD, 1, 1, pProperties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pInteractionTarget, @NotNull InteractionHand pUsedHand) {

        if (pInteractionTarget instanceof IDecoratable deco && canWear(pInteractionTarget)){
            deco.setCosmeticItem(pStack.split(1));
            if (deco instanceof Starbuncle starbuncle && !pPlayer.isShiftKeyDown()){
                CompoundTag tag = new CompoundTag();
                tag.putUUID("master", pPlayer.getUUID());
                starbuncle.setBehavior(new StarbyFighterBehavior(starbuncle, tag));
                PortUtil.sendMessage(pPlayer, Component.translatable("ars_nouveau.starbuncle.sword_behavior_set"));
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public Vec3 getTranslations() {
        return new Vec3(0,0,0);
    }

    @Override
    public Vec3 getScaling() {
        return new Vec3(1,1,1);
    }

    /**
     * @param entity check if is compatible with the cosmetic item
     */
    @Override
    public boolean canWear(LivingEntity entity) {
        return entity instanceof Starbuncle || entity instanceof FamiliarStarbuncle;
    }

}
