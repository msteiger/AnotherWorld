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
import org.terasology.math.Region3i;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseFacet3D;

/**
 * Created by Marcin on 2014-10-20.
 */
public class TemperatureFacet extends BaseFacet3D {
    private Noise2D noiseTable;
    private int seaLevel;
    private int maxLevel;
    private float noiseMultiplier;
    private Function<Float, Float> function;

    public TemperatureFacet(Region3i targetRegion, Border3D border, int seaLevel, int maxLevel, float noiseMultiplier,
                         Function<Float, Float> function, long temperatureSeed) {
        super(targetRegion, border);
        this.seaLevel = seaLevel;
        this.maxLevel = maxLevel;
        this.noiseMultiplier = noiseMultiplier;
        this.function = function;
        noiseTable = new SimplexNoise(temperatureSeed);
    }

    public float get(int x, int y, int z) {
        float result = noiseTable.noise(x * noiseMultiplier, z * noiseMultiplier);
        float humidityBase = function.apply(TeraMath.clamp((result + 1.0f) / 2.0f));
        if (y <= seaLevel) {
            return humidityBase;
        } else if (y >= maxLevel) {
            return 0;
        } else {
            // The higher above see level - the less humid
            return humidityBase * (1f * (maxLevel - y) / (maxLevel - seaLevel));
        }
    }
}
