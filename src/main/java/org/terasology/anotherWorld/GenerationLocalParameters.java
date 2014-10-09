package org.terasology.anotherWorld;

import org.terasology.math.Vector3i;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.facets.SurfaceHumidityFacet;
import org.terasology.world.generation.facets.SurfaceTemperatureFacet;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class GenerationLocalParameters implements LocalParameters {
    private Vector3i location;
    private SurfaceTemperatureFacet surfaceTemperatureFacet;
    private SurfaceHumidityFacet surfaceHumidityFacet;


    public GenerationLocalParameters(Region chunkRegion, Vector3i location) {
        this.location = location;
        surfaceTemperatureFacet = chunkRegion.getFacet(SurfaceTemperatureFacet.class);
        surfaceHumidityFacet = chunkRegion.getFacet(SurfaceHumidityFacet.class);
    }

    @Override
    public float getTemperature() {
        return surfaceTemperatureFacet.getWorld(location.x, location.z);
    }

    @Override
    public float getHumidity() {
        return surfaceHumidityFacet.getWorld(location.x, location.z);
    }
}
