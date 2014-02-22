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
import org.terasology.anotherWorld.util.alpha.IdentityAlphaFunction;
import org.terasology.engine.SimpleUri;
import org.terasology.math.TeraMath;
import org.terasology.math.Vector3i;
import org.terasology.monitoring.PerformanceMonitor;
import org.terasology.world.ChunkView;
import org.terasology.world.chunks.Chunk;
import org.terasology.world.generator.WorldGenerator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public abstract class PluggableWorldGenerator implements WorldGenerator {
    private static final Logger logger = LoggerFactory.getLogger(PluggableWorldGenerator.class);

    private Vector3i chunkSize = new Vector3i(16, 256, 16);

    private List<ChunkDecorator> chunkDecorators = new LinkedList<>();
    private List<FeatureGenerator> featureGenerators = new LinkedList<>();

    private BiomeProvider biomeProvider;
    private int seaLevel = 32;
    private int maxLevel = 220;

    private LandscapeProvider landscapeProvider;
    private SimpleUri uri;
    private float biomeDiversity = 0.5f;

    private Function<Float, Float> temperatureFunction = IdentityAlphaFunction.singleton();
    private Function<Float, Float> humidityFunction = IdentityAlphaFunction.singleton();

    private TerrainShapeProvider terrainShapeProvider;

    public PluggableWorldGenerator(SimpleUri uri) {
        this.uri = uri;
    }

    public void setLandscapeProvider(LandscapeProvider landscapeProvider) {
        this.landscapeProvider = landscapeProvider;
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

    @Override
    public final void initialize() {
    }

    @Override
    public void setWorldSeed(String seed) {
        setupGenerator();

        landscapeProvider.initialize(seed, seaLevel, maxLevel);

        terrainShapeProvider = new LookupTerrainShapeProvider(landscapeProvider);

        biomeProvider = new BiomeProviderImpl(seed, seaLevel, maxLevel,
                biomeDiversity, temperatureFunction, humidityFunction, terrainShapeProvider);

        for (ChunkDecorator chunkDecorator : chunkDecorators) {
            chunkDecorator.initializeWithSeed(seed);
        }

        for (FeatureGenerator featureGenerator : featureGenerators) {
            featureGenerator.initializeWithSeed(seed);
        }
    }

    protected abstract void setupGenerator();

    @Override
    public void applySecondPass(Vector3i chunkPos, ChunkView view) {
        PerformanceMonitor.startActivity("AnotherWorld - chunk second pass");
        try {
            GenerationParameters generationParameters = new GenerationParameters(landscapeProvider, terrainShapeProvider, biomeProvider, seaLevel, maxLevel);

            for (FeatureGenerator featureGenerator : featureGenerators) {
                featureGenerator.generateInChunk(chunkPos, view, generationParameters);
            }
        } finally {
            PerformanceMonitor.endActivity();
        }
    }

    @Override
    public SimpleUri getUri() {
        return uri;
    }

    @Override
    public void createChunk(Chunk chunk) {
        PerformanceMonitor.startActivity("AnotherWorld - chunk generation");
        try {
            GenerationParameters generationParameters = new GenerationParameters(landscapeProvider, terrainShapeProvider, biomeProvider, seaLevel, maxLevel);

            for (ChunkDecorator chunkDecorator : chunkDecorators) {
                chunkDecorator.generateInChunk(chunk, generationParameters);
            }
        } finally {
            PerformanceMonitor.endActivity();
        }
    }

    @Override
    public float getFog(float x, float y, float z) {
        return biomeProvider.getBiomeAt(TeraMath.floorToInt(x + 0.5f), TeraMath.floorToInt(y + 0.5f), TeraMath.floorToInt(z + 0.5f)).getFog();
    }

    @Override
    public float getTemperature(float x, float y, float z) {
        return biomeProvider.getTemperature(TeraMath.floorToInt(x + 0.5f), TeraMath.floorToInt(y + 0.5f), TeraMath.floorToInt(z + 0.5f));
    }

    @Override
    public float getHumidity(float x, float y, float z) {
        return biomeProvider.getHumidity(TeraMath.floorToInt(x + 0.5f), TeraMath.floorToInt(y + 0.5f), TeraMath.floorToInt(z + 0.5f));
    }
}
