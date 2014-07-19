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

import org.terasology.math.Vector2i;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.facets.SeaLevelFacet;
import org.terasology.world.generation.facets.SurfaceHeightFacet;
import org.terasology.world.generation.facets.SurfaceHumidityFacet;

/**
 * @author Immortius
 */
@Produces(SurfaceHumidityFacet.class)
@Requires({@Facet(SurfaceHeightFacet.class), @Facet(SeaLevelHumidityFacet.class), @Facet(SeaLevelFacet.class)})
public class SurfaceHumidityProvider implements FacetProvider {

    @Override
    public void setSeed(long seed) {
    }

    @Override
    public void process(GeneratingRegion region) {
        SurfaceHumidityFacet facet = new SurfaceHumidityFacet(region.getRegion(), region.getBorderForFacet(SurfaceHumidityFacet.class));
        SeaLevelHumidityFacet seaLevelHumidityFacet = region.getRegionFacet(SeaLevelHumidityFacet.class);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);
        SeaLevelFacet seaLevelFacet = region.getRegionFacet(SeaLevelFacet.class);
        int seaLevel = seaLevelFacet.getSeaLevel();
        MaxLevelFacet maxLevelFacet = region.getRegionFacet(MaxLevelFacet.class);
        int maxLevel = maxLevelFacet.getMaxLevel();

        for (Vector2i position : facet.getWorldRegion()) {
            float humidityBase = seaLevelHumidityFacet.getWorld(position);
            float height = surfaceHeightFacet.getWorld(position);

            if (height <= seaLevel) {
                facet.setWorld(position, humidityBase);
            } else if (height >= maxLevel) {
                facet.setWorld(position, 0);
            } else {
                // The higher above see level - the less humid
                facet.setWorld(position, humidityBase * (1f * (maxLevel - height) / (maxLevel - seaLevel)));
            }
        }

        region.setRegionFacet(SurfaceHumidityFacet.class, facet);
    }
}
