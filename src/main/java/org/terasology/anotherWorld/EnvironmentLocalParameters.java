package org.terasology.anotherWorld;

import org.terasology.climateConditions.ClimateConditionsSystem;
import org.terasology.math.Vector3i;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class EnvironmentLocalParameters implements LocalParameters {
    private ClimateConditionsSystem environmentSystem;
    private Vector3i location;

    public EnvironmentLocalParameters(ClimateConditionsSystem environmentSystem, Vector3i location) {
        this.environmentSystem = environmentSystem;
        this.location = location;
    }

    @Override
    public float getTemperature() {
        return environmentSystem.getTemperature(location.x, location.y, location.z);
    }

    @Override
    public float getHumidity() {
        return environmentSystem.getHumidity(location.x, location.y, location.z);
    }
}
