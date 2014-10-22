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

import com.google.common.base.Function;
import org.terasology.anotherWorld.util.alpha.IdentityAlphaFunction;
import org.terasology.anotherWorld.util.alpha.UniformNoiseAlpha;
import org.terasology.math.Region3i;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseFacet3D;
import org.terasology.world.generation.facets.base.BaseFieldFacet3D;

public class TerrainVariationFacet extends BaseFacet3D {
    private UniformNoiseAlpha alpha = new UniformNoiseAlpha(IdentityAlphaFunction.singleton());
    private Noise3D noise;

    public TerrainVariationFacet(Region3i targetRegion, Border3D border, Noise3D noise) {
        super(targetRegion, border);
        this.noise = noise;
    }

    public float get(int x, int y, int z) {
        return alpha.apply((1 + noise.noise(x * 0.01f, y * 0.01f, z * 0.01f)) / 2f);
    }
}
