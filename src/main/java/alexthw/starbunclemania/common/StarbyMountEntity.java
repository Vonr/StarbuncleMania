package alexthw.starbunclemania.common;

import alexthw.starbunclemania.registry.ModRegistry;
import com.hollingsworth.arsnouveau.api.entity.ChangeableBehavior;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.goal.carbuncle.StarbyTransportBehavior;
import com.hollingsworth.arsnouveau.common.items.data.StarbuncleCharmData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StarbyMountEntity extends Starbuncle implements PlayerRideableJumping {

    private float playerJumpPendingScale;
    private boolean isJumping;

    public StarbyMountEntity(Level world) {
        super(ModRegistry.STARBY_MOUNT.get(), world);
        this.setTamed(true);
    }

    public StarbyMountEntity(Level world, StarbuncleCharmData.Mutable data) {
        this(world);
        this.data = data;
        restoreFromTag();
    }

    public StarbyMountEntity(EntityType<StarbyMountEntity> entityType, Level world) {
        super(entityType, world);
        this.setTamed(true);
    }

    @Override
    public void setBehavior(ChangeableBehavior behavior) {
    }

    @Override
    public void getTooltip(List<Component> tooltip) {
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), ModRegistry.STARSADDLE.get().getDefaultInstance()));
    }

    @Override
    public EntityType<?> getType() {
        return ModRegistry.STARBY_MOUNT.get();
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    public void setIsJumping(boolean pJumping) {
        this.isJumping = pJumping;
    }

    public void travel(@NotNull Vec3 pTravelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.getControllingPassenger() instanceof Player livingentity) {
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float strafe = livingentity.xxa * 0.5F;
                float forward = livingentity.zza * 2;
                if (forward <= 0.0F) {
                    forward *= 0.25F;
                }

                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround()) {
                    double d0 = this.getJumpPower() * 2 * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
                    double d1 = d0 + this.getJumpBoostPower();
                    Vec3 vec3 = this.getDeltaMovement();
                    this.setDeltaMovement(vec3.x, d1, vec3.z);
                    this.setIsJumping(true);
                    this.hasImpulse = true;
                    CommonHooks.onLivingJump(this);
                    if (forward > 0.0F) {
                        float f2 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
                        this.setDeltaMovement(this.getDeltaMovement().add(-0.4F * f2 * this.playerJumpPendingScale, 0.0D, 0.4F * f3 * this.playerJumpPendingScale));
                    }

                    this.playerJumpPendingScale = 0.0F;
                }


                //this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3(strafe, pTravelVector.y, forward));
                } else {
                    this.setDeltaMovement(Vec3.ZERO);
                }
                if (this.onGround()) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }
            } else {
                //this.flyingSpeed = 0.02F;
                super.travel(pTravelVector);
            }
        }
    }

    @Override
    public void onWanded(Player playerEntity) {
        Starbuncle carbuncle = new Starbuncle(playerEntity.level(), true);
        StarbuncleCharmData.Mutable data = this.data;
        carbuncle.setPos(getX() + 0.5, getY() + 1, getZ() + 0.5);
        carbuncle.data = data;
        carbuncle.restoreFromTag();
        playerEntity.level().addFreshEntity(carbuncle);
        playerEntity.level().addFreshEntity(new ItemEntity(playerEntity.level(), getX(), getY(), getZ(), ModRegistry.STARSADDLE.get().getDefaultInstance()));
        carbuncle.onWanded(playerEntity);
        carbuncle.setBehavior(new StarbyTransportBehavior(carbuncle, new CompoundTag()));
        this.discard();
    }

    @Override
    public void positionRider(@NotNull Entity passenger, @NotNull MoveFunction callback) {
        super.positionRider(passenger, callback);
        if (passenger instanceof Mob mob && this.getControllingPassenger() == passenger && mob.zza > 0) {
            this.yBodyRot = mob.yBodyRot;
        }
        if (this.hasPassenger(passenger) && passenger instanceof Player) {
            float f1 = Mth.sin(this.yBodyRot * (0.017453292f));
            float f = Mth.cos(this.yBodyRot * (0.017453292f));
            Vec3 vec3 = this.getPassengerRidingPosition(passenger);
            Vec3 vec31 = passenger.getVehicleAttachmentPoint(this);
            callback.accept(passenger, vec3.x - vec31.x + f1 * 0.8, vec3.y - vec31.y, vec3.z - vec31.z - f * 0.8);
        }
    }

    @Override
    protected double getDefaultGravity() {
        if (getCosmeticItem().getItem() == ModRegistry.STARBALLON.get()) {
            return super.getDefaultGravity() * 0.5D;
        }
        return super.getDefaultGravity();
    }

    @Override
    public boolean dismountsUnderwater() {
        return false;
    }

    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    @Override
    protected boolean canRide(@NotNull Entity pEntity) {
        return pEntity instanceof Player;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource) {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (player.level().isClientSide()) return InteractionResult.PASS;

        if (player.getMainHandItem().isEmpty() && !player.isShiftKeyDown()) {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void onPlayerJump(int pJumpPower) {
        if (pJumpPower < 0) {
            pJumpPower = 0;
        }

        if (pJumpPower >= 90) {
            this.playerJumpPendingScale = 1.0F;
        } else {
            this.playerJumpPendingScale = 0.4F + 0.4F * (float) pJumpPower / 90.0F;
        }

    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void handleStartJump(int pJumpPower) {

    }

    @Override
    public void handleStopJump() {

    }

}
