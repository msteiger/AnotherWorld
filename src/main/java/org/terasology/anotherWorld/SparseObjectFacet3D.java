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
package org.terasology.anotherWorld;

import org.terasology.math.Region3i;
import org.terasology.math.Vector3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseFacet3D;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcin on 2014-10-21.
 */
public class SparseObjectFacet3D<T> extends BaseFacet3D {
    private Map<Vector3i, T> positions = new HashMap<>();

    public SparseObjectFacet3D(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }

    public void setFlag(Vector3i position, T value) {
        positions.put(position, value);
    }

    public Map<Vector3i, T> getFlaggedPositions() {
        return Collections.unmodifiableMap(positions);
    }
}
