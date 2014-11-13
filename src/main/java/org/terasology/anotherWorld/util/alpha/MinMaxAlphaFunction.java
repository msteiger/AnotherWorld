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
package org.terasology.anotherWorld.util.alpha;

import com.google.common.base.Function;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class MinMaxAlphaFunction implements Function<Float, Float> {
    private Function<Float, Float> delegate;
    private float min;
    private float max;

    public MinMaxAlphaFunction(Function<Float, Float> delegate, float min, float max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum cannot be higher than maximum");
        }
        this.delegate = delegate;
        this.min = min;
        this.max = max;
    }

    @Override
    public Float apply(Float input) {
        float result = delegate.apply(input);
        return min + (max - min) * result;
    }
}
