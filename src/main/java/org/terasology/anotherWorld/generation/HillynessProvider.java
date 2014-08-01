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
package org.terasology.anotherWorld.generation;

import org.terasology.math.TeraMath;
import org.terasology.math.Vector2i;
import org.terasology.math.Vector3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetBorder;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

@Produces(HillynessFacet.class)
@Requires(@Facet(value = SurfaceHeightFacet.class, border = @FacetBorder(sides = HillynessProvider.RANGE)))
public class HillynessProvider implements FacetProvider {
    public static final int RANGE = 5;

    public HillynessProvider() {
    }

    @Override
    public void setSeed(long seed) {

    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(HillynessFacet.class);
        HillynessFacet facet = new HillynessFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        for (Vector3i position : region.getRegion()) {
            float baseHeight = surfaceHeightFacet.getWorld(position.x, position.z);
            int count = 0;
            int diffSum = 0;
            for (int testX = position.x - RANGE; testX < position.x + RANGE; testX++) {
                int zRange = (int) Math.sqrt(RANGE * RANGE - (testX - position.x) * (testX - position.x));
                for (int testZ = position.z - zRange; testZ < position.z + zRange; testZ++) {
                    count++;
                    diffSum += Math.abs(surfaceHeightFacet.getWorld(new Vector2i(testX, testZ)) - baseHeight);
                }
            }
            float diffAverage = 1f * diffSum / count;

            facet.setWorld(position.x, position.z, TeraMath.clamp(diffAverage / 2));
        }

        region.setRegionFacet(HillynessFacet.class, facet);
    }
}
