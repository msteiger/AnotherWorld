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
package org.terasology.anotherWorld;

import org.terasology.world.biomes.BiomeRegistrator;
import org.terasology.world.biomes.BiomeRegistry;

public class AnotherWorldBiomes implements BiomeRegistrator {

    public static final AnotherWorldBiome DESERT = new AnotherWorldBiomeImpl(
            "AnotherWorld:Desert",
            "Desert",
            0.4f,
            0.1f,
            // Hot and dry, and preferably flat
            new DefaultSweetSpot(0f, 0.4f, 1f, 0.4f, 0f, 0.2f, 0f, 0f)
    );

    public static final AnotherWorldBiome FOREST = new AnotherWorldBiomeImpl(
            "AnotherWorld:Forest",
            "Forest",
            1f,
            0.6f,
            // Reasonably humid, and average temperature
            new DefaultSweetSpot(0.7f, 0.5f, 0.5f, 0.5f, 0f, 0f, 0f, 0f)
    );

    public static final AnotherWorldBiome PLAINS = new AnotherWorldBiomeImpl(
            "AnotherWorld:Plains",
            "Plains",
            1f,
            0.3f,
            // Rather dry and average temperature, flat preferred
            new DefaultSweetSpot(0.3f, 0.3f, 0.5f, 0.3f, 0f, 0.4f, 0f, 0f)
    );

    public static final AnotherWorldBiome TUNDRA = new AnotherWorldBiomeImpl(
            "AnotherWorld:Tundra",
            "Tundra",
            0.8f,
            0.1f,
            // Cold and dry
            new DefaultSweetSpot(0f, 0.2f, 0f, 0.2f, 0f, 0f, 0.6f, 0.6f)
    );

    public static final AnotherWorldBiome TAIGA = new AnotherWorldBiomeImpl(
            "AnotherWorld:Taiga",
            "Taiga",
            0.8f,
            0.1f,
            // Cold, but reasonably humid, usually on high levels
            new DefaultSweetSpot(0.5f, 0.2f, 0f, 0.2f, 0f, 0f, 0.6f, 0.6f)
    );

    public static final AnotherWorldBiome ALPINE = new AnotherWorldBiomeImpl(
            "AnotherWorld:Alpine",
            "Alpine",
            0.9f,
            0,
            // Occurs in very high Y-level, and cold
            new DefaultSweetSpot(0f, 0f, 0f, 0f, 0f, 0f, 1f, 1f)
    );

    public static final AnotherWorldBiome CLIFF = new AnotherWorldBiomeImpl(
            "AnotherWorld:Cliff",
            "Cliff",
            0.8f,
            0.1f,
            // Cold, but reasonably humid, usually on high levels
            new DefaultSweetSpot(0f, 0f, 0f, 0f, 1f, 1f, 0f, 0f)
    );

    private void validateBiome(AnotherWorldBiome biome) {
        AnotherWorldBiome.SweetSpot sweetSpot = biome.getSweetSpot();
        validateValue(sweetSpot.getAboveSeaLevel());
        validateValue(sweetSpot.getAboveSeaLevelWeight());
        validateValue(sweetSpot.getHumidity());
        validateValue(sweetSpot.getHumidityWeight());
        validateValue(sweetSpot.getTemperature());
        validateValue(sweetSpot.getTemperatureWeight());
        validateValue(sweetSpot.getTerrain());
        validateValue(sweetSpot.getTerrainWeight());

        float weightTotal = sweetSpot.getAboveSeaLevelWeight() + sweetSpot.getHumidityWeight()
                + sweetSpot.getTemperatureWeight() + sweetSpot.getTerrainWeight();

        if (weightTotal > 1.0001 || weightTotal < 0.0009) {
            throw new IllegalArgumentException();
        }
    }

    private void validateValue(float value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void registerBiomes(BiomeRegistry registry) {
        registry.registerBiome(DESERT);
        registry.registerBiome(FOREST);
        registry.registerBiome(PLAINS);
        registry.registerBiome(TUNDRA);
        registry.registerBiome(TAIGA);
        registry.registerBiome(ALPINE);
        registry.registerBiome(CLIFF);
    }
}
