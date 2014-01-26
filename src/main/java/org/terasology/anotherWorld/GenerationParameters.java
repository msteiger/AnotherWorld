package org.terasology.anotherWorld;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class GenerationParameters {
    private LandscapeProvider landscapeProvider;
    private BiomeProvider biomeProvider;
    private int seaLevel;
    private int maxLevel;

    public GenerationParameters(LandscapeProvider landscapeProvider, BiomeProvider biomeProvider, int seaLevel, int maxLevel) {
        this.landscapeProvider = landscapeProvider;
        this.biomeProvider = biomeProvider;
        this.seaLevel = seaLevel;
        this.maxLevel = maxLevel;
    }

    public LandscapeProvider getLandscapeProvider() {
        return landscapeProvider;
    }

    public BiomeProvider getBiomeProvider() {
        return biomeProvider;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
