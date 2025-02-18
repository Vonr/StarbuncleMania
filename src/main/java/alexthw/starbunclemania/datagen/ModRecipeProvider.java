package alexthw.starbunclemania.datagen;

import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static alexthw.starbunclemania.StarbuncleMania.prefix;
import static alexthw.starbunclemania.registry.ModRegistry.*;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator pGenerator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(pGenerator.getPackOutput(), lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {

        //items
        shaped(STARBATTERY)
                .define('R', Items.REDSTONE)
                .define('C', Items.COPPER_INGOT)
                .define('N', Items.GOLD_NUGGET)
                .pattern(" N ")
                .pattern("CRC")
                .pattern("CRC")
                .save(consumer);

        shaped(STARTRASH)
                .define('I', Items.IRON_INGOT)
                .define('N', Items.IRON_NUGGET)
                .pattern(" N ")
                .pattern("I I")
                .pattern("NIN")
                .save(consumer);

        shaped(STARHAT)
                .pattern(" F ")
                .pattern("FWF")
                .pattern(" F ")
                .define('W', Items.BLACK_WOOL)
                .define('F', ItemsRegistry.MAGE_FIBER)
                .save(consumer);

        shaped(PROFHAT)
                .pattern("CCC")
                .pattern(" W ")
                .pattern("   ")
                .define('W', Items.BLACK_WOOL)
                .define('C', Items.BLACK_CARPET)
                .save(consumer);

        //blocks
        shapedB(FLUID_JAR)
                .define('G', Items.GLASS)
                .define('C', BlockRegistry.CASCADING_LOG.asItem())
                .define('W', ItemsRegistry.WATER_ESSENCE)
                .define('I', Items.GOLD_INGOT)
                .pattern(" I ")
                .pattern("GWG")
                .pattern("CCC")
                .save(consumer);
        shapedB(FLUID_SOURCELINK)
                .pattern(" S ")
                .pattern("GBG")
                .pattern(" S ")
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('S', ItemsRegistry.SOURCE_GEM)
                .define('B', SOURCE_FLUID_BUCKET.get())
                .save(consumer);
        shapelessBuilder(SOURCE_CONDENSER.get())
                .requires(FLUID_JAR.get())
                .requires(BlockRegistry.RELAY)
                .save(consumer);

        shaped(STARMINE)
                .pattern("III")
                .pattern("ILI")
                .pattern("GGG")
                .define('I', Items.IRON_INGOT)
                .define('L', Items.LEATHER_HELMET)
                .define('G', Items.GOLD_NUGGET)
                .save(consumer);

        shaped(STARBUILD)
                .pattern("GGG")
                .pattern("GDG")
                .define('G', Items.GOLD_NUGGET)
                .define('D', Items.DISPENSER)
                .save(consumer);

        shapelessBuilder(DIRECTION_SCROLL.get()).requires(ItemsRegistry.BLANK_PARCHMENT).requires(Items.COMPASS).save(consumer);
        shapelessBuilder(FLUID_SCROLL_ALLOW.get()).requires(ItemsRegistry.BLANK_PARCHMENT).requires(ItemsRegistry.WATER_ESSENCE).save(consumer);
        shapelessBuilder(FLUID_SCROLL_DENY.get()).requires(ItemsRegistry.BLANK_PARCHMENT).requires(ItemsRegistry.WATER_ESSENCE).requires(Ingredient.of(Tags.Items.COBBLESTONES)).save(consumer);
        shapelessBuilder(FLUID_SCROLL_ALLOW.get()).requires(FLUID_SCROLL_ALLOW.get()).save(consumer, prefix("clear_fluid_allow"));
        shapelessBuilder(FLUID_SCROLL_DENY.get()).requires(FLUID_SCROLL_DENY.get()).save(consumer, prefix("clear_fluid_deny"));

    }

    public ShapedRecipeBuilder shaped(DeferredHolder<Item, Item> result) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get()).unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.WORN_NOTEBOOK));
    }

    public ShapedRecipeBuilder shapedB(DeferredHolder<Block, Block> result) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get()).unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.WORN_NOTEBOOK));
    }

    public ShapelessRecipeBuilder shapelessBuilder(ItemLike result) {
        return shapelessBuilder(result, 1);
    }

    public ShapelessRecipeBuilder shapelessBuilder(ItemLike result, int resultCount) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, resultCount).unlockedBy("has_journal", InventoryChangeTrigger.TriggerInstance.hasItems(ItemsRegistry.WORN_NOTEBOOK));
    }

}
