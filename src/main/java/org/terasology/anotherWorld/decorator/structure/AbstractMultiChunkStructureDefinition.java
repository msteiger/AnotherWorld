/*
 * Copyright 2015 MovingBlocks
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
package org.terasology.anotherWorld.decorator.structure;

import org.terasology.anotherWorld.util.ChunkRandom;
import org.terasology.anotherWorld.util.PDist;
import org.terasology.math.Region3i;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.utilities.random.Random;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMultiChunkStructureDefinition implements StructureDefinition {
    private PDist frequency;

    protected AbstractMultiChunkStructureDefinition(PDist frequency) {
        this.frequency = frequency;
    }

    @Override
    public final Collection<Structure> generateStructures(Vector3i chunkSize, long seed, Region3i region) {
        List<Structure> result = new LinkedList<>();
        float maxRange = getMaxRange();

        int chunksRangeToEvaluateX = (int) Math.ceil(maxRange / chunkSize.x);
        int chunksRangeToEvaluateY = (int) Math.ceil(maxRange / chunkSize.y);
        int chunksRangeToEvaluateZ = (int) Math.ceil(maxRange / chunkSize.z);

        Vector3i minChunk = TeraMath.calcChunkPos(region.min());
        Vector3i maxChunk = TeraMath.calcChunkPos(region.max());

        for (int chunkX = minChunk.x - chunksRangeToEvaluateX; chunkX <= maxChunk.x + chunksRangeToEvaluateX; chunkX++) {
            for (int chunkY = minChunk.y - chunksRangeToEvaluateY; chunkY <= maxChunk.y + chunksRangeToEvaluateY; chunkY++) {
                for (int chunkZ = minChunk.z - chunksRangeToEvaluateZ; chunkZ <= maxChunk.z + chunksRangeToEvaluateZ; chunkZ++) {
                    generateStructuresForChunkWithFrequency(result, seed,
                            new Vector3i(chunkX, chunkY, chunkZ), chunkSize);
                }
            }
        }

        return result;
    }

    protected final void generateStructuresForChunkWithFrequency(List<Structure> result, long seed, Vector3i chunkPosition, Vector3i chunkSize) {
        Random random = ChunkRandom.getChunkRandom(seed, chunkPosition, getGeneratorSalt());

        float structuresInChunk = frequency.getValue(random);
        int structuresToGenerateInChunk = (int) structuresInChunk;

        // Check if we "hit" any leftover
        if (random.nextFloat() < structuresInChunk - structuresToGenerateInChunk) {
            structuresToGenerateInChunk++;
        }

        for (int i = 0; i < structuresToGenerateInChunk; i++) {
            generateStructuresForChunk(result, random, chunkPosition, chunkSize);
        }
    }

    protected abstract float getMaxRange();

    protected abstract int getGeneratorSalt();

    protected abstract void generateStructuresForChunk(List<Structure> result, Random random, Vector3i chunkPosition, Vector3i chunkSize);
}
