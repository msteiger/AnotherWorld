package org.terasology.anotherWorld.decorator.layering;

import org.terasology.world.block.Block;
import org.terasology.world.liquid.LiquidType;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class LayeringConfig {
    private Block bottomBlock;
    private Block mainBlock;
    private Block seaBlock;
    private LiquidType seaLiquid;

    public LayeringConfig(Block bottomBlock, Block mainBlock, Block seaBlock, LiquidType seaLiquid) {
        this.bottomBlock = bottomBlock;
        this.mainBlock = mainBlock;
        this.seaBlock = seaBlock;
        this.seaLiquid = seaLiquid;
    }

    public Block getBottomBlock() {
        return bottomBlock;
    }

    public Block getMainBlock() {
        return mainBlock;
    }

    public Block getSeaBlock() {
        return seaBlock;
    }

    public LiquidType getSeaLiquid() {
        return seaLiquid;
    }
}
