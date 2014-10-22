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

import com.google.common.base.Function;
import org.terasology.world.generation.*;
import org.terasology.world.generation.facets.SeaLevelFacet;

/**
 * Created by Marcin on 2014-10-20.
 */
@Produces(HumidityFacet.class)
@Requires({@Facet(SeaLevelFacet.class), @Facet(MaxLevelFacet.class)})
public class HumidityProvider implements FacetProvider {
    private final float minMultiplier = 0.0005f;
    private final float maxMultiplier = 0.01f;

    private long humiditySeed;
    private float noiseMultiplier;
    private Function<Float, Float> humidityFunction;

    public HumidityProvider(float conditionsDiversity, Function<Float, Float> humidityFunction) {
        this.humidityFunction = humidityFunction;
        noiseMultiplier = minMultiplier + (maxMultiplier - minMultiplier) * conditionsDiversity;
    }

    @Override
    public void setSeed(long seed) {
        humiditySeed = seed + 129534;
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(HumidityFacet.class);
        SeaLevelFacet seaLevelFacet = region.getRegionFacet(SeaLevelFacet.class);
        int seaLevel = seaLevelFacet.getSeaLevel();
        MaxLevelFacet maxLevelFacet = region.getRegionFacet(MaxLevelFacet.class);
        int maxLevel = maxLevelFacet.getMaxLevel();

        HumidityFacet facet = new HumidityFacet(region.getRegion(), border, seaLevel, maxLevel, noiseMultiplier, humidityFunction, humiditySeed);
        region.setRegionFacet(HumidityFacet.class, facet);
    }
}
