/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
