package org.terasology.anotherWorld.generation;
/*
 * Copyright 2015 MovingBlocks
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


import org.terasology.anotherWorld.AnotherWorldBiome;
import org.terasology.anotherWorld.AnotherWorldBiomes;
import org.terasology.rendering.nui.Color;
import org.terasology.world.viewer.layers.NominalFacetLayer;
import org.terasology.world.viewer.layers.Renders;
import org.terasology.world.viewer.layers.ZOrder;

import com.google.common.collect.ImmutableMap;

/**
 * Maps {@link AnotherWorldBiome} facet values to corresponding colors.
 */
@Renders(value = BiomeFacet.class, order = ZOrder.BIOME)
public class BiomeFacetLayer extends NominalFacetLayer<AnotherWorldBiome> {

    public BiomeFacetLayer() {
        super(BiomeFacet.class, ImmutableMap.<AnotherWorldBiome, Color>builder()
                .put(AnotherWorldBiomes.ALPINE, new Color(0xffffffff))
                .put(AnotherWorldBiomes.TUNDRA, new Color(0xa0a099ff))
                .put(AnotherWorldBiomes.CLIFF, new Color(0x888888ff))
                .put(AnotherWorldBiomes.TAIGA, new Color(0x99aa77ff))
                .put(AnotherWorldBiomes.DESERT, new Color(0xc9d29bff))
                .put(AnotherWorldBiomes.FOREST, new Color(0x679459ff))
                .put(AnotherWorldBiomes.PLAINS, new Color(0x88aa55ff))
                .build()::get);
    }
}
