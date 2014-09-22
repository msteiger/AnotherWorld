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

/**
 * Standard implementation class for basic another world biomes.
 */
class AnotherWorldBiomeImpl implements AnotherWorldBiome {

    private final String id;

    private final String name;

    private final float rarity;

    private final float fog;

    private final SweetSpot sweetSpot;

    AnotherWorldBiomeImpl(String id, String name, float rarity, float fog, SweetSpot sweetSpot) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.fog = fog;
        this.sweetSpot = sweetSpot;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBiomeParent() {
        return null;
    }

    @Override
    public float getRarity() {
        return rarity;
    }

    @Override
    public SweetSpot getSweetSpot() {
        return sweetSpot;
    }

    @Override
    public float getFog() {
        return fog;
    }

    @Override
    public float getHumidity() {
        return sweetSpot.getHumidity();
    }

    @Override
    public float getTemperature() {
        return sweetSpot.getTemperature();
    }

}
