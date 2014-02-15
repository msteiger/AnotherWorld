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
package org.terasology.anotherWorld;

import com.google.common.base.Function;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.SimplexNoise;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class ConditionsBaseProvider {
    private final float minMultiplier = 0.0005f;
    private final float maxMultiplier = 0.01f;

    private final Noise2D temperatureNoise;
    private final Noise2D humidityNoise;

    private float noiseMultiplier;
    private Function<Float, Float> temperatureFunction;
    private Function<Float, Float> humidityFunction;

    public ConditionsBaseProvider(String worldSeed, float conditionsDiversity, Function<Float, Float> temperatureFunction, Function<Float, Float> humidityFunction) {
        this.temperatureFunction = temperatureFunction;
        this.humidityFunction = humidityFunction;
        temperatureNoise = new SimplexNoise(worldSeed.hashCode() + 582374);
        humidityNoise = new SimplexNoise(worldSeed.hashCode() + 129534);
        noiseMultiplier = minMultiplier + (maxMultiplier - minMultiplier) * conditionsDiversity;
    }

    public float getBaseTemperature(int x, int z) {
        double result = temperatureNoise.noise(x * noiseMultiplier, z * noiseMultiplier);
        return temperatureFunction.apply((float) TeraMath.clamp((result + 1.0f) / 2.0f));
    }

    public float getBaseHumidity(int x, int z) {
        double result = humidityNoise.noise(x * noiseMultiplier, z * noiseMultiplier);
        return humidityFunction.apply((float) TeraMath.clamp((result + 1.0f) / 2.0f));
    }
}
