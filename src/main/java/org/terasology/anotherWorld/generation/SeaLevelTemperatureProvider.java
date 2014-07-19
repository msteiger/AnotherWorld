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
import org.terasology.math.TeraMath;
import org.terasology.math.Vector2i;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

@Produces(SeaLevelTemperatureFacet.class)
public class SeaLevelTemperatureProvider implements FacetProvider {
    private final float minMultiplier = 0.0005f;
    private final float maxMultiplier = 0.01f;

    private Noise2D temperatureNoise;
    private float noiseMultiplier;
    private Function<Float, Float> temperatureFunction;

    public SeaLevelTemperatureProvider(float conditionsDiversity, Function<Float, Float> temperatureFunction) {
        this.temperatureFunction = temperatureFunction;
        noiseMultiplier = minMultiplier + (maxMultiplier - minMultiplier) * conditionsDiversity;
    }

    @Override
    public void setSeed(long seed) {
        temperatureNoise = new SimplexNoise(seed + 582374);
    }

    @Override
    public void process(GeneratingRegion region) {
        SeaLevelTemperatureFacet facet = new SeaLevelTemperatureFacet(region.getRegion(), region.getBorderForFacet(SeaLevelTemperatureFacet.class));

        for (Vector2i position : facet.getWorldRegion()) {
            double result = temperatureNoise.noise(position.x * noiseMultiplier, position.y * noiseMultiplier);
            facet.setWorld(position, temperatureFunction.apply((float) TeraMath.clamp((result + 1.0f) / 2.0f)));
        }

        region.setRegionFacet(SeaLevelTemperatureFacet.class, facet);
    }
}
