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
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.terasology.anotherWorld.util.alpha.IdentityAlphaFunction;
import org.terasology.math.TeraMath;
import org.terasology.math.Vector2i;
import org.terasology.utilities.procedural.BrownianNoise2D;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.SimplexNoise;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class PerlinLandscapeGenerator implements LandscapeProvider {
    public static final int CACHE_SIZE = 10000;
    private Cache<Vector2i, Integer> heightCache = CacheBuilder.<Vector2i, Integer>newBuilder()
            .concurrencyLevel(5).maximumSize(CACHE_SIZE).build();

    private Noise2D noise;
    private double noiseScale;

    private float seaFrequency;
    private float terrainNoiseMultiplier;
    private Function<Float, Float> generalHeightFunction;
    private Function<Float, Float> heightBelowSeaLevelFunction;
    private Function<Float, Float> heightAboveSeaLevelFunction;
    private float hillynessDiversity;
    private Function<Float, Float> hillynessFunction;
    private TerrainDeformation terrainDeformation;
    private int seaLevel;
    private int maxLevel;

    private final float minMultiplier = 0.0005f;
    private final float maxMultiplier = 0.01f;

    @Deprecated
    public PerlinLandscapeGenerator(float seaFrequency, Function<Float, Float> heightAboveSeaLevelFunction,
                                    float hillynessDiversity, Function<Float, Float> hillynessFunction) {
        this(seaFrequency, 0.4216f, IdentityAlphaFunction.singleton(), IdentityAlphaFunction.singleton(),
                heightAboveSeaLevelFunction, hillynessDiversity, hillynessFunction);
    }

    public PerlinLandscapeGenerator(float seaFrequency, float terrainDiversity, Function<Float, Float> generalTerrainFunction,
                                    Function<Float, Float> heightBelowSeaLevelFunction,
                                    Function<Float, Float> heightAboveSeaLevelFunction,
                                    float hillinessDiversity, Function<Float, Float> hillynessFunction) {
        this.seaFrequency = seaFrequency;
        this.terrainNoiseMultiplier = minMultiplier + terrainDiversity * (maxMultiplier - minMultiplier);
        this.generalHeightFunction = generalTerrainFunction;
        this.heightBelowSeaLevelFunction = heightBelowSeaLevelFunction;
        this.heightAboveSeaLevelFunction = heightAboveSeaLevelFunction;
        this.hillynessDiversity = hillinessDiversity;
        this.hillynessFunction = hillynessFunction;
    }

    @Override
    public void initialize(String seed, int sea, int max) {
        seaLevel = sea;
        maxLevel = max;
        BrownianNoise2D brownianNoise = new BrownianNoise2D(new SimplexNoise(seed.hashCode()), 6);
        noise = brownianNoise;
        noiseScale = brownianNoise.getScale();
        terrainDeformation = new TerrainDeformation(seed, hillynessDiversity, hillynessFunction);
    }

    @Override
    public int getHeight(Vector2i position) {
        Integer height = heightCache.getIfPresent(position);
        if (height != null) {
            return height;
        }

        float hillyness = terrainDeformation.getHillyness(position.x, position.y);
        float noiseValue = getNoiseInWorld(hillyness, position.x, position.y);
        if (noiseValue < seaFrequency) {
            float alphaBelowSeaLevel = (noiseValue / seaFrequency);
            float resultAlpha = heightBelowSeaLevelFunction.apply(alphaBelowSeaLevel);
            height = (int) (seaLevel * resultAlpha);
        } else {
            // Number in range 0<=alpha<1
            float alphaAboveSeaLevel = (noiseValue - seaFrequency) / (1 - seaFrequency);
            float resultAlpha = heightAboveSeaLevelFunction.apply(alphaAboveSeaLevel);
            height = (int) (seaLevel + resultAlpha * (maxLevel - seaLevel));
        }

        heightCache.put(position, height);

        return height;
    }

    private float getNoiseInWorld(float hillyness, int worldX, int worldZ) {
        double noiseValue = 0;
        int scanArea = (int) ((1 - hillyness) * 50);
        int divider = 0;
        // Scan and average heights in the circle of blocks with diameter of "scanArea" (based on hillyness)
        for (int x = worldX - scanArea; x <= worldX + scanArea; x++) {
            int zScan = (int) Math.sqrt(scanArea * scanArea - (x - worldX) * (x - worldX));
            for (int z = worldZ - zScan; z <= worldZ + zScan; z++) {
                noiseValue += noise.noise(terrainNoiseMultiplier * x, terrainNoiseMultiplier * z) / noiseScale;
                divider++;
            }
        }
        noiseValue /= divider;
        return generalHeightFunction.apply((float) TeraMath.clamp((noiseValue + 1.0) / 2));
    }
}
