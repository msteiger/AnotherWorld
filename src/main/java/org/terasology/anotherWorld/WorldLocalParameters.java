package org.terasology.anotherWorld;

import org.terasology.math.Region3i;
import org.terasology.math.Vector3i;
import org.terasology.world.WorldProvider;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.facets.SurfaceHumidityFacet;
import org.terasology.world.generation.facets.SurfaceTemperatureFacet;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class WorldLocalParameters implements LocalParameters {
    private WorldProvider worldProvider;
    private Vector3i location;

    public WorldLocalParameters(WorldProvider worldProvider, Vector3i location) {
        this.worldProvider = worldProvider;
        this.location = location;
    }

    @Override
    public float getTemperature() {
        float temperature = 0.5f;
        Region region = worldProvider.getWorldData(Region3i.createFromCenterExtents(location, 1));
        if (region != null) {
            SurfaceTemperatureFacet surfaceTemperatureFacet = region.getFacet(SurfaceTemperatureFacet.class);
            temperature = surfaceTemperatureFacet.getWorld(location.x, location.z);
        }
        return temperature;
    }

    @Override
    public float getHumidity() {
        float humidity = 0.5f;
        Region region = worldProvider.getWorldData(Region3i.createFromCenterExtents(location, 1));
        if (region != null) {
            SurfaceHumidityFacet surfaceHumidityFacet = region.getFacet(SurfaceHumidityFacet.class);
            humidity = surfaceHumidityFacet.getWorld(location.x, location.z);
        }
        return humidity;
    }
}
