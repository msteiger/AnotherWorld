package org.terasology.anotherWorld;

import org.terasology.anotherWorld.generation.HumidityFacet;
import org.terasology.anotherWorld.generation.TemperatureFacet;
import org.terasology.math.Vector3i;
import org.terasology.world.generation.Region;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class GenerationLocalParameters implements LocalParameters {
    private Vector3i location;
    private TemperatureFacet surfaceTemperatureFacet;
    private HumidityFacet surfaceHumidityFacet;


    public GenerationLocalParameters(Region chunkRegion, Vector3i location) {
        this.location = location;
        surfaceTemperatureFacet = chunkRegion.getFacet(TemperatureFacet.class);
        surfaceHumidityFacet = chunkRegion.getFacet(HumidityFacet.class);
    }

    @Override
    public float getTemperature() {
        return surfaceTemperatureFacet.get(location.x, location.y, location.z);
    }

    @Override
    public float getHumidity() {
        return surfaceHumidityFacet.get(location.x, location.y, location.z);
    }
}
