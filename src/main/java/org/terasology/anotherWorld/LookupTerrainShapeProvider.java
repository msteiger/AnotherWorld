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

import org.terasology.math.TeraMath;
import org.terasology.math.Vector2i;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class LookupTerrainShapeProvider implements TerrainShapeProvider {
    private LandscapeProvider landscapeProvider;
    private int range = 5;

    public LookupTerrainShapeProvider(LandscapeProvider landscapeProvider) {
        this.landscapeProvider = landscapeProvider;
    }

    @Override
    public float getHillyness(final int x, final int z) {
        int baseHeight = landscapeProvider.getHeight(new Vector2i(x, z));
        int count = 0;
        int diffSum = 0;
        for (int testX = x - range; testX <= x + range; testX++) {
            int zRange = (int) Math.sqrt(range * range - (testX - x) * (testX - x));
            for (int testZ = z - zRange; testZ <= z + zRange; testZ++) {
                count++;
                diffSum += Math.abs(landscapeProvider.getHeight(new Vector2i(testX, testZ)) - baseHeight);
            }
        }
        float diffAverage = 1f * diffSum / count;

        return (float) TeraMath.clamp(diffAverage / 2);
    }
}
