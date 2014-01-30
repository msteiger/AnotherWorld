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

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class GenerationParameters {
    private LandscapeProvider landscapeProvider;
    private TerrainShapeProvider terrainShapeProvider;
    private BiomeProvider biomeProvider;
    private int seaLevel;
    private int maxLevel;

    public GenerationParameters(LandscapeProvider landscapeProvider, TerrainShapeProvider terrainShapeProvider, BiomeProvider biomeProvider, int seaLevel, int maxLevel) {
        this.landscapeProvider = landscapeProvider;
        this.terrainShapeProvider = terrainShapeProvider;
        this.biomeProvider = biomeProvider;
        this.seaLevel = seaLevel;
        this.maxLevel = maxLevel;
    }

    public LandscapeProvider getLandscapeProvider() {
        return landscapeProvider;
    }

    public TerrainShapeProvider getTerrainShapeProvider() {
        return terrainShapeProvider;
    }

    public BiomeProvider getBiomeProvider() {
        return biomeProvider;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
