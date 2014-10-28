/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.anotherWorld.generation;

import com.google.common.base.Predicate;
import org.terasology.math.Region3i;
import org.terasology.world.block.Block;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseObjectFacet3D;

public class OreBlockFacet extends BaseObjectFacet3D<Block> {
    private Predicate<Block> replacePredicate;

    public OreBlockFacet(Region3i targetRegion, Border3D border, Class<Block> objectType, Predicate<Block> replacePredicate) {
        super(targetRegion, border, objectType);
        this.replacePredicate = replacePredicate;
    }

    public Predicate<Block> getReplacePredicate() {
        return replacePredicate;
    }
}
