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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.anotherWorld.coreBiome.AlpineBiome;
import org.terasology.anotherWorld.coreBiome.CliffBiome;
import org.terasology.anotherWorld.coreBiome.DesertBiome;
import org.terasology.anotherWorld.coreBiome.ForestBiome;
import org.terasology.anotherWorld.coreBiome.PlainsBiome;
import org.terasology.anotherWorld.coreBiome.TaigaBiome;
import org.terasology.anotherWorld.coreBiome.TundraBiome;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.registry.CoreRegistry;
import org.terasology.registry.Share;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Share(BiomeRegistry.class)
@RegisterSystem
public class BiomeRegistryImpl extends BaseComponentSystem implements BiomeRegistry {
    private static final Logger logger = LoggerFactory.getLogger(BiomeRegistryImpl.class);

    private Map<String, Biome> biomes = new HashMap<>();


    public BiomeRegistryImpl() {
        initializeCoreBiomes();
    }

    @Override
    public void initialise() {
        super.initialise();
        loadBiomes();
    }

    private void initializeCoreBiomes() {
        Biome desert = new DesertBiome();
        biomes.put(desert.getBiomeId(), desert);
        Biome forest = new ForestBiome();
        biomes.put(forest.getBiomeId(), forest);
        Biome plains = new PlainsBiome();
        biomes.put(plains.getBiomeId(), plains);
        Biome tundra = new TundraBiome();
        biomes.put(tundra.getBiomeId(), tundra);
        Biome taiga = new TaigaBiome();
        biomes.put(taiga.getBiomeId(), taiga);
        Biome alpine = new AlpineBiome();
        biomes.put(alpine.getBiomeId(), alpine);
        Biome cliff = new CliffBiome();
        biomes.put(cliff.getBiomeId(), cliff);
    }

    private void loadBiomes() {
        WorldGeneratorPluginLibrary pluginLibrary = CoreRegistry.get(WorldGeneratorPluginLibrary.class);
        List<Biome> loadedBiomes = pluginLibrary.instantiateAllOfType(Biome.class);
        for (Biome biome : loadedBiomes) {
            try {
                validateBiome(biome);
                biomes.put(biome.getBiomeId(), biome);
            } catch (IllegalArgumentException exp) {
                logger.error("Biome has invalid definition of a sweet-spot");
            }
        }
    }

    private void validateBiome(Biome biome) {
        Biome.SweetSpot sweetSpot = biome.getSweetSpot();
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

    public Biome getBiomeById(String biomeId) {
        return biomes.get(biomeId);
    }

    public Iterable<Biome> getBiomes() {
        return biomes.values();
    }
}
