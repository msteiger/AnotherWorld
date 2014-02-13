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
package org.terasology.anotherWorld.decorator.ore;

import com.google.common.base.Predicate;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.GenerationParameters;
import org.terasology.anotherWorld.decorator.structure.Structure;
import org.terasology.anotherWorld.decorator.structure.StructureDefinition;
import org.terasology.math.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.chunks.Chunk;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class OreDecorator implements ChunkDecorator {
    private Map<String, StructureDefinition> oreDefinitions = new LinkedHashMap<>();
    private Predicate<Block> blockFilter;
    private String seed;

    public OreDecorator(Predicate<Block> blockFilter) {
        this.blockFilter = blockFilter;
    }

    @Override
    public void initializeWithSeed(String worldSeed) {
        this.seed = worldSeed;
        loadOres();
    }

    @Override
    public void generateInChunk(Chunk chunk, GenerationParameters generationParameters) {
        Structure.StructureCallback callback = new StructureCallbackImpl(chunk);

        for (StructureDefinition structureDefinition : oreDefinitions.values()) {
            Collection<Structure> structures = structureDefinition.generateStructures(chunk, seed, generationParameters);
            for (Structure structure : structures) {
                structure.generateStructure(callback);
            }
        }
    }

    private void loadOres() {
        WorldGeneratorPluginLibrary pluginLibrary = CoreRegistry.get(WorldGeneratorPluginLibrary.class);
        List<OreDefinition> loadedOreDefinitions = pluginLibrary.instantiateAllOfType(OreDefinition.class);
        for (OreDefinition oreDefinition : loadedOreDefinitions) {
            String oreId = oreDefinition.getOreId();
            oreDefinitions.put(oreId, oreDefinition);
        }
    }

    private final class StructureCallbackImpl implements Structure.StructureCallback {
        private Chunk chunk;

        private StructureCallbackImpl(Chunk chunk) {
            this.chunk = chunk;
        }

        @Override
        public boolean canReplace(int x, int y, int z) {
            boolean validCoords = (x >= 0 && y >= 1 && z >= 0
                    && x < chunk.getChunkSizeX() && y < chunk.getChunkSizeY() && z < chunk.getChunkSizeZ());
            return validCoords && blockFilter.apply(chunk.getBlock(x, y, z));

        }

        @Override
        public void replaceBlock(Vector3i position, float force, Block block) {
            if (canReplace(position.x, position.y, position.z)) {
                chunk.setBlock(position, block);
            }
        }
    }
}
