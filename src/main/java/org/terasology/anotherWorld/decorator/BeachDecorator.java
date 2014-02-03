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

import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.GenerationParameters;
import org.terasology.anotherWorld.util.Filter;
import org.terasology.anotherWorld.util.Provider;
import org.terasology.math.Vector2i;
import org.terasology.world.block.Block;
import org.terasology.world.chunks.Chunk;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class BeachDecorator implements ChunkDecorator {
    private Filter<Block> blockFilter;
    private Provider<Block> beachBlockProvider;
    private int aboveSeaLevel;
    private int belowSeaLevel;

    public BeachDecorator(Filter<Block> blockFilter, final Block beachBlock, int aboveSeaLevel, int belowSeaLevel) {
        this(blockFilter, new Provider<Block>() {
            @Override
            public void initializeWithSeed(String seed) {
            }

            @Override
            public Block provide(int x, int y, int z) {
                return beachBlock;
            }
        }, aboveSeaLevel, belowSeaLevel);
    }

    public BeachDecorator(Filter<Block> blockFilter, Provider<Block> beachBlockProvider, int aboveSeaLevel, int belowSeaLevel) {
        this.blockFilter = blockFilter;
        this.beachBlockProvider = beachBlockProvider;
        this.aboveSeaLevel = aboveSeaLevel;
        this.belowSeaLevel = belowSeaLevel;
    }

    @Override
    public void initializeWithSeed(String seed) {
        beachBlockProvider.initializeWithSeed(seed);
    }

    @Override
    public void generateInChunk(Chunk chunk, GenerationParameters generationParameters) {
        int chunkStartX = chunk.getChunkWorldPosX();
        int chunkStartY = chunk.getChunkWorldPosY();
        int chunkStartZ = chunk.getChunkWorldPosZ();
        for (int x = 0; x < chunk.getChunkSizeX(); x++) {
            for (int z = 0; z < chunk.getChunkSizeZ(); z++) {
                int groundLevel = generationParameters.getLandscapeProvider().getHeight(new Vector2i(chunkStartX + x, chunkStartZ + z));
                int seaLevel = generationParameters.getSeaLevel();
                if (groundLevel <= seaLevel + aboveSeaLevel && groundLevel >= seaLevel - belowSeaLevel) {
                    for (int y = seaLevel - belowSeaLevel; y < seaLevel + aboveSeaLevel; y++) {
                        if (blockFilter.accepts(chunk.getBlock(x, y, z))) {
                            chunk.setBlock(x, y, z, beachBlockProvider.provide(chunkStartX + x, chunkStartY + y, chunkStartZ + z));
                        }
                    }
                }
            }
        }
    }
}
