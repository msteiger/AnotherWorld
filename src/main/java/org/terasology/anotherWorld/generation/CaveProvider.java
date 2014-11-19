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
import org.terasology.anotherWorld.decorator.structure.Structure;
import org.terasology.anotherWorld.decorator.structure.StructureDefinition;
import org.terasology.anotherWorld.decorator.structure.VeinsStructureDefinition;
import org.terasology.anotherWorld.util.PDist;
import org.terasology.math.Region3i;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.ChunkConstants;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

import java.util.Collection;

@Produces(CaveFacet.class)
public class CaveProvider implements FacetProvider {
    private Predicate<Block> blockFilter;
    private StructureDefinition caveDefinition;
    private long seed;

    public CaveProvider(Predicate<Block> blockFilter, PDist caveFrequency, PDist mainCaveRadius, PDist mainCaveYLevel,
                        PDist tunnelLength, PDist tunnelRadius) {
        this.blockFilter = blockFilter;

        caveDefinition = new VeinsStructureDefinition(caveFrequency,
                new VeinsStructureDefinition.VeinsBlockProvider() {
                    @Override
                    public Block getClusterBlock(float distanceFromCenter) {
                        return BlockManager.getAir();
                    }

                    @Override
                    public Block getBranchBlock() {
                        return BlockManager.getAir();
                    }
                }, mainCaveRadius, mainCaveYLevel, new PDist(4f, 1f), new PDist(0f, 0.1f), tunnelLength,
                new PDist(1000f, 0f), new PDist(0f, 0f), new PDist(0.25f, 0f), new PDist(5f, 0f), new PDist(0.5f, 0.5f),
                tunnelRadius, new PDist(1f, 0f), new PDist(1f, 0f));
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public void process(GeneratingRegion region) {
        CaveFacet caveFacet = new CaveFacet(region.getRegion(), region.getBorderForFacet(OreBlockFacet.class), blockFilter);

        Structure.StructureCallback callback = new StructureCallbackImpl(caveFacet.getWorldRegion(), caveFacet);

        Collection<Structure> structures = caveDefinition.generateStructures(ChunkConstants.CHUNK_SIZE, seed, caveFacet.getWorldRegion());
        for (Structure structure : structures) {
            structure.generateStructure(callback);
        }

        region.setRegionFacet(CaveFacet.class, caveFacet);
    }

    private static final class StructureCallbackImpl implements Structure.StructureCallback {
        private Region3i region;
        private CaveFacet oreBlockFacet;

        private StructureCallbackImpl(Region3i region, CaveFacet oreBlockFacet) {
            this.region = region;
            this.oreBlockFacet = oreBlockFacet;
        }

        @Override
        public boolean canReplace(int x, int y, int z) {
            return region.encompasses(x, y, z);
        }

        @Override
        public void replaceBlock(int x, int y, int z, float force, Block block) {
            oreBlockFacet.setWorld(x, y, z, true);
        }
    }
}
