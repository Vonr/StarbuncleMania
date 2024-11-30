package alexthw.starbunclemania.common.item.cosmetic;

import com.hollingsworth.arsnouveau.api.item.ArsNouveauCurio;

public class PlayerCurioCosmetic extends ArsNouveauCurio {
    public PlayerCurioCosmetic(Properties properties) {
        super(properties);
    }

    @Override
    public PlayerCurioCosmetic withTooltip(String tip) {
        return (PlayerCurioCosmetic) super.withTooltip(tip);
    }
}
