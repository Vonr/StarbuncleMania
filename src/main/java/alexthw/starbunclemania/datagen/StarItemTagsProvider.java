package alexthw.starbunclemania.datagen;

import alexthw.starbunclemania.StarbuncleMania;
import alexthw.starbunclemania.registry.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class StarItemTagsProvider extends IntrinsicHolderTagsProvider<Item> {

    public StarItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(output, Registries.ITEM, future, item -> item.builtInRegistryHolder().key(), StarbuncleMania.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "head"))).add(
                ModRegistry.SEA_BUNNY.get(),
                ModRegistry.STARBY_EARS.get(),
                ModRegistry.ALAK_HAT.get(),
                ModRegistry.DRYGMY_HORNS.get(),
                ModRegistry.WHIRLI_PROP.get()
        );
    }
}
