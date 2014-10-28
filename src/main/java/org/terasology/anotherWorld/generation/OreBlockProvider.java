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
package org.terasology.anotherWorld.generation;

import com.google.common.base.Predicate;
import org.terasology.anotherWorld.decorator.ore.OreDefinition;
import org.terasology.anotherWorld.decorator.structure.Structure;
import org.terasology.anotherWorld.decorator.structure.StructureDefinition;
import org.terasology.math.Region3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.chunks.ChunkConstants;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Produces(OreBlockFacet.class)
public class OreBlockProvider implements FacetProvider {
    private Predicate<Block> blockFilter;
    private Map<String, StructureDefinition> oreDefinitions = new LinkedHashMap<>();
    private long seed;

    public OreBlockProvider(Predicate<Block> blockFilter) {
        this.blockFilter = blockFilter;
        loadOres();
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public void process(GeneratingRegion region) {
        OreBlockFacet oreBlockFacet = new OreBlockFacet(region.getRegion(), region.getBorderForFacet(OreBlockFacet.class), Block.class, blockFilter);

        Structure.StructureCallback callback = new StructureCallbackImpl(oreBlockFacet.getWorldRegion(), oreBlockFacet);

        for (StructureDefinition structureDefinition : oreDefinitions.values()) {
            Collection<Structure> structures = structureDefinition.generateStructures(ChunkConstants.CHUNK_SIZE, seed, oreBlockFacet.getWorldRegion());
            for (Structure structure : structures) {
                structure.generateStructure(callback);
            }
        }

        region.setRegionFacet(OreBlockFacet.class, oreBlockFacet);
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
        private Region3i region;
        private OreBlockFacet oreBlockFacet;

        private StructureCallbackImpl(Region3i region, OreBlockFacet oreBlockFacet) {
            this.region = region;
            this.oreBlockFacet = oreBlockFacet;
        }

        @Override
        public boolean canReplace(int x, int y, int z) {
            return region.encompasses(x, y, z);
        }

        @Override
        public void replaceBlock(int x, int y, int z, float force, Block block) {
            oreBlockFacet.setWorld(x, y, z, block);
        }
    }
}
