package org.terasology.anotherWorld;

import org.terasology.math.Vector3i;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class GenerationLocalParameters implements LocalParameters {
    private GenerationParameters generationParameters;
    private Vector3i location;

    public GenerationLocalParameters(GenerationParameters generationParameters, Vector3i location) {
        this.generationParameters = generationParameters;
        this.location = location;
    }

    @Override
    public float getTemperature() {
        return generationParameters.getBiomeRegistry().getTemperature(location.x, location.y, location.z);
    }

    @Override
    public float getHumidity() {
        return generationParameters.getBiomeRegistry().getHumidity(location.x, location.y, location.z);
    }
}
