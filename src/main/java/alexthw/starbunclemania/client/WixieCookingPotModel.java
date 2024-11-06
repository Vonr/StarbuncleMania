package alexthw.starbunclemania.client;

import alexthw.starbunclemania.StarbuncleMania;
import alexthw.starbunclemania.common.block.wixie_stations.FarmerPotWixieCauldronTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WixieCookingPotModel extends GeoModel<FarmerPotWixieCauldronTile> {

    public static final ResourceLocation model = StarbuncleMania.prefix( "geo/pot_stirrer.geo.json");
    public static final ResourceLocation tex = StarbuncleMania.prefix( "textures/block/wixie_cooking_pot_spoon.png");
    public static final ResourceLocation animation = StarbuncleMania.prefix( "animations/pot_stirrer.animation.json");

    @Override
    public ResourceLocation getModelResource(FarmerPotWixieCauldronTile animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(FarmerPotWixieCauldronTile animatable) {
        return tex;
    }

    @Override
    public ResourceLocation getAnimationResource(FarmerPotWixieCauldronTile animatable) {
        return animation;
    }
}
