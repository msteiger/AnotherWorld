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

import org.terasology.climateConditions.ConditionsBaseField;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

/**
 * Created by Marcin on 2014-10-20.
 */
@Produces(HumidityFacet.class)
public class HumidityProvider implements FacetProvider {
    private ConditionsBaseField conditionsBaseField;

    public HumidityProvider(ConditionsBaseField conditionsBaseField) {
        this.conditionsBaseField = conditionsBaseField;
    }

    @Override
    public void setSeed(long seed) {
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(HumidityFacet.class);

        HumidityFacet facet = new HumidityFacet(region.getRegion(), border, conditionsBaseField);
        region.setRegionFacet(HumidityFacet.class, facet);
    }
}
