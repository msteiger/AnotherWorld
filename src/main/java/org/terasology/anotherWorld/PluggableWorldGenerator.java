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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.anotherWorld.generation.BiomeProvider;
import org.terasology.anotherWorld.generation.HillynessProvider;
import org.terasology.anotherWorld.generation.MaxLevelProvider;
import org.terasology.anotherWorld.generation.PerlinSurfaceHeightProvider;
import org.terasology.anotherWorld.generation.SeaLevelHumidityProvider;
import org.terasology.anotherWorld.generation.SeaLevelTemperatureProvider;
import org.terasology.anotherWorld.generation.SeedProvider;
import org.terasology.anotherWorld.generation.SurfaceHumidityProvider;
import org.terasology.anotherWorld.generation.SurfaceTemperatureProvider;
import org.terasology.anotherWorld.generation.TerrainVariationProvider;
import org.terasology.anotherWorld.util.alpha.IdentityAlphaFunction;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.core.world.generator.facetProviders.SurfaceToDensityProvider;
import org.terasology.engine.SimpleUri;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.World;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.WorldGenerator;

import java.util.LinkedList;
import java.util.List;

public abstract class PluggableWorldGenerator implements WorldGenerator {
    private static final Logger logger = LoggerFactory.getLogger(PluggableWorldGenerator.class);

    private World world;
    private List<ChunkDecorator> chunkDecorators = new LinkedList<>();
    private List<FeatureGenerator> featureGenerators = new LinkedList<>();

    private int seaLevel = 32;
    private int maxLevel = 220;
    private float biomeDiversity = 0.5f;

    private SimpleUri uri;
    private String worldSeed;

    private Function<Float, Float> temperatureFunction = IdentityAlphaFunction.singleton();
    private Function<Float, Float> humidityFunction = IdentityAlphaFunction.singleton();

    private PerlinSurfaceHeightProvider surfaceHeightProvider;

    private TerrainShapeProvider terrainShapeProvider;

    public PluggableWorldGenerator(SimpleUri uri) {
        this.uri = uri;
    }

    public void addChunkDecorator(ChunkDecorator chunkGenerator) {
        chunkDecorators.add(chunkGenerator);
    }

    public void addFeatureGenerator(FeatureGenerator featureGenerator) {
        featureGenerators.add(featureGenerator);
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * 0=changing slowly, 1=changing frequently
     *
     * @param biomeDiversity
     */
    public void setBiomeDiversity(float biomeDiversity) {
        this.biomeDiversity = biomeDiversity;
    }

    public void setTemperatureFunction(Function<Float, Float> temperatureFunction) {
        this.temperatureFunction = temperatureFunction;
    }

    public void setHumidityFunction(Function<Float, Float> humidityFunction) {
        this.humidityFunction = humidityFunction;
    }


    public void setLandscapeOptions(float seaFrequency, float terrainDiversity, Function<Float, Float> generalTerrainFunction,
                                    Function<Float, Float> heightBelowSeaLevelFunction,
                                    Function<Float, Float> heightAboveSeaLevelFunction,
                                    float hillinessDiversity, Function<Float, Float> hillynessFunction) {
        surfaceHeightProvider = new PerlinSurfaceHeightProvider(seaFrequency, terrainDiversity, generalTerrainFunction,
                heightBelowSeaLevelFunction,
                heightAboveSeaLevelFunction,
                hillinessDiversity, hillynessFunction);
    }

    @Override
    public void initialize() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        WorldBuilder worldBuilder = new WorldBuilder(worldSeed.hashCode())
                .addProvider(new SeaLevelProvider(seaLevel))
                .addProvider(new MaxLevelProvider(maxLevel))
                .addProvider(new BiomeProvider())
                .addProvider(new HillynessProvider())
                .addProvider(new MaxLevelProvider(maxLevel))
                .addProvider(surfaceHeightProvider)
                .addProvider(new SurfaceToDensityProvider())
                .addProvider(new SeaLevelHumidityProvider(biomeDiversity, humidityFunction))
                .addProvider(new SurfaceHumidityProvider())
                .addProvider(new SeaLevelTemperatureProvider(biomeDiversity, temperatureFunction))
                .addProvider(new SurfaceTemperatureProvider())
                .addProvider(new SeedProvider())
                .addProvider(new TerrainVariationProvider());

        for (ChunkDecorator chunkDecorator : chunkDecorators) {
            worldBuilder.addRasterizer(chunkDecorator);
        }
        for (FeatureGenerator featureGenerator : featureGenerators) {
            worldBuilder.addRasterizer(featureGenerator);
        }

        world = worldBuilder.build();
    }

    @Override
    public void setWorldSeed(String seed) {
        worldSeed = seed;
        setupGenerator();
    }

    protected abstract void setupGenerator();


    @Override
    public void createChunk(CoreChunk chunk) {
        world.rasterizeChunk(chunk);
    }

    @Override
    public SimpleUri getUri() {
        return uri;
    }

    @Override
    public float getFog(float x, float y, float z) {
        return 0.5f;
    }

    @Override
    public float getTemperature(float x, float y, float z) {
        return 0.5f;
    }

    @Override
    public float getHumidity(float x, float y, float z) {
        return 0.5f;
    }
}
