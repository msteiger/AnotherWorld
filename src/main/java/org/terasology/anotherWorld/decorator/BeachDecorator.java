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
package org.terasology.anotherWorld.decorator;

import com.google.common.base.Predicate;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.generation.TerrainVariationFacet;
import org.terasology.anotherWorld.util.Provider;
import org.terasology.math.TeraMath;
import org.terasology.math.Vector3i;
import org.terasology.world.block.Block;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.facets.SeaLevelFacet;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class BeachDecorator implements ChunkDecorator {
    private Predicate<Block> blockFilter;
    private Provider<Block> beachBlockProvider;
    private int aboveSeaLevel;
    private int belowSeaLevel;

    public BeachDecorator(Predicate<Block> blockFilter, final Block beachBlock, int aboveSeaLevel, int belowSeaLevel) {
        this(blockFilter, new Provider<Block>() {
            @Override
            public Block provide(float randomValue) {
                return beachBlock;
            }
        }, aboveSeaLevel, belowSeaLevel);
    }

    public BeachDecorator(Predicate<Block> blockFilter, Provider<Block> beachBlockProvider, int aboveSeaLevel, int belowSeaLevel) {
        this.blockFilter = blockFilter;
        this.beachBlockProvider = beachBlockProvider;
        this.aboveSeaLevel = aboveSeaLevel;
        this.belowSeaLevel = belowSeaLevel;
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);
        TerrainVariationFacet terrainVariationFacet = chunkRegion.getFacet(TerrainVariationFacet.class);
        SeaLevelFacet seaLevelFacet = chunkRegion.getFacet(SeaLevelFacet.class);
        int seaLevel = chunkRegion.getFacet(SeaLevelFacet.class).getSeaLevel();

        for (Vector3i position : chunk.getRegion()) {
            int groundLevel = TeraMath.floorToInt(surfaceHeightFacet.getWorld(position.x, position.z));
            if (groundLevel <= seaLevel + aboveSeaLevel && groundLevel >= seaLevel - belowSeaLevel) {
                for (int y = seaLevel - belowSeaLevel; y < seaLevel + aboveSeaLevel; y++) {
                    if (blockFilter.apply(chunk.getBlock(position))) {
                        chunk.setBlock(position, beachBlockProvider.provide(terrainVariationFacet.get(position)));
                    }
                }
            }
        }
    }
}
