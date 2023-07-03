package alexthw.starbunclemania.client;

import com.hollingsworth.arsnouveau.client.renderer.entity.StarbuncleRenderer;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ResizedStarbRender extends StarbuncleRenderer {
    public ResizedStarbRender(EntityRendererProvider.Context manager) {
        super(manager);
    }


    @Override
    public void render(Starbuncle entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(3, 3, 3);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
