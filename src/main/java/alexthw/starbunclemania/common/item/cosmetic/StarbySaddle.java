package alexthw.starbunclemania.common.item.cosmetic;

import alexthw.starbunclemania.common.StarbyMountEntity;
import com.hollingsworth.arsnouveau.api.item.ICosmeticItem;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.items.ModItem;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class StarbySaddle extends ModItem implements ICosmeticItem {

    public StarbySaddle(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity entity, @NotNull InteractionHand pUsedHand) {
        if (entity instanceof Starbuncle starbuncle && !(entity instanceof StarbyMountEntity)){
            starbuncle.onWanded(pPlayer);
            StarbyMountEntity mount = new StarbyMountEntity(pPlayer.level(), starbuncle.data);
            mount.setPos(starbuncle.getX(), starbuncle.getY(), starbuncle.getZ());
            pPlayer.level().addFreshEntity(mount);
            starbuncle.discard();
            pStack.shrink(1);
            PortUtil.sendMessage(pPlayer, Component.translatable("ars_nouveau.starbuncle.saddle_behavior_set"));
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(pStack, pPlayer, entity, pUsedHand);
    }


    @Override
    public Vec3 getTranslations() {
        return new Vec3(0,0,0);
    }

    @Override
    public Vec3 getScaling() {
        return new Vec3(0,0,0);
    }
}
