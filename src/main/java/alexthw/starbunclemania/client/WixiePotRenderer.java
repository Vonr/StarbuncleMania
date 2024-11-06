package alexthw.starbunclemania.client;

import alexthw.starbunclemania.common.block.wixie_stations.FarmerPotWixieCauldronTile;
import com.hollingsworth.arsnouveau.client.renderer.tile.ArsGeoBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class WixiePotRenderer extends ArsGeoBlockRenderer<FarmerPotWixieCauldronTile> {

    public WixiePotRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new WixieCookingPotModel());
    }

}
