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
package org.terasology.anotherWorld.decorator;

import org.terasology.anotherWorld.AnotherWorldBiome;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.generation.BiomeFacet;
import org.terasology.world.chunks.ChunkConstants;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;

/**
 * Rasterizes the Biome Facet into the Chunk.
 */
public class BiomeDecorator implements ChunkDecorator {

    @Override
    public void initialize() {
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {

        BiomeFacet biomeFacet = chunkRegion.getFacet(BiomeFacet.class);

        if (biomeFacet == null) {
            throw new IllegalStateException("World generator does not provide a biome facet.");
        }

        for (int x = 0; x < ChunkConstants.CHUNK_SIZE.x; x++) {
            for (int z = 0; z < ChunkConstants.CHUNK_SIZE.z; z++) {
                // Biome is the same for each value of y and depends only on x and z
                AnotherWorldBiome biome = biomeFacet.get(x, z);
                for (int y = 0; y < ChunkConstants.CHUNK_SIZE.y; y++) {
                    chunk.setBiome(x, y, z, biome);
                }
            }
        }
    }

}
