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
package org.terasology.anotherWorld.util.alpha;

import org.terasology.anotherWorld.util.AlphaFunction;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class UniformNoiseAlpha implements AlphaFunction {

    private static final float[] noiseUniformLookup = new float[]{
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 0.001f,
            0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 0.002f, 0.002f,
            0.002f, 0.002f, 0.002f, 0.002f, 0.002f, 0.002f, 0.002f, 0.002f, 0.003f, 0.003f,
            0.003f, 0.003f, 0.003f, 0.003f, 0.003f, 0.003f, 0.003f, 0.003f, 0.004f, 0.004f,
            0.004f, 0.004f, 0.004f, 0.004f, 0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 0.005f,
            0.005f, 0.006f, 0.006f, 0.006f, 0.006f, 0.006f, 0.006f, 0.007f, 0.007f, 0.007f,
            0.008f, 0.008f, 0.008f, 0.009f, 0.009f, 0.009f, 0.01f, 0.01f, 0.011f, 0.011f,
            0.011f, 0.012f, 0.012f, 0.012f, 0.013f, 0.013f, 0.014f, 0.014f, 0.014f, 0.015f,
            0.015f, 0.015f, 0.016f, 0.016f, 0.016f, 0.016f, 0.017f, 0.017f, 0.017f, 0.018f,
            0.02f, 0.021f, 0.022f, 0.023f, 0.023f, 0.024f, 0.024f, 0.025f, 0.025f, 0.025f,
            0.026f, 0.027f, 0.027f, 0.027f, 0.028f, 0.028f, 0.029f, 0.029f, 0.035f, 0.035f,
            0.036f, 0.037f, 0.037f, 0.038f, 0.039f, 0.039f, 0.04f, 0.041f, 0.042f, 0.042f,
            0.043f, 0.044f, 0.044f, 0.045f, 0.046f, 0.046f, 0.047f, 0.047f, 0.048f, 0.049f,
            0.05f, 0.051f, 0.051f, 0.051f, 0.052f, 0.052f, 0.053f, 0.053f, 0.054f, 0.055f,
            0.056f, 0.056f, 0.057f, 0.058f, 0.059f, 0.059f, 0.06f, 0.061f, 0.062f, 0.063f,
            0.063f, 0.065f, 0.066f, 0.067f, 0.068f, 0.069f, 0.07f, 0.075f, 0.077f, 0.078f,
            0.079f, 0.08f, 0.081f, 0.082f, 0.083f, 0.084f, 0.086f, 0.087f, 0.088f, 0.089f,
            0.09f, 0.091f, 0.092f, 0.094f, 0.094f, 0.095f, 0.097f, 0.098f, 0.099f, 0.101f,
            0.102f, 0.103f, 0.103f, 0.105f, 0.106f, 0.108f, 0.11f, 0.111f, 0.113f, 0.114f,
            0.116f, 0.117f, 0.119f, 0.12f, 0.122f, 0.123f, 0.125f, 0.126f, 0.127f, 0.129f,
            0.13f, 0.131f, 0.132f, 0.139f, 0.14f, 0.141f, 0.143f, 0.144f, 0.146f, 0.147f,
            0.149f, 0.15f, 0.151f, 0.153f, 0.159f, 0.167f, 0.17f, 0.171f, 0.173f, 0.176f,
            0.178f, 0.18f, 0.188f, 0.19f, 0.192f, 0.195f, 0.198f, 0.2f, 0.202f, 0.204f,
            0.206f, 0.207f, 0.208f, 0.21f, 0.211f, 0.213f, 0.214f, 0.216f, 0.219f, 0.22f,
            0.222f, 0.225f, 0.227f, 0.229f, 0.236f, 0.243f, 0.246f, 0.247f, 0.249f, 0.251f,
            0.253f, 0.255f, 0.258f, 0.26f, 0.261f, 0.263f, 0.264f, 0.265f, 0.266f, 0.267f,
            0.269f, 0.27f, 0.272f, 0.275f, 0.277f, 0.279f, 0.282f, 0.284f, 0.291f, 0.295f,
            0.297f, 0.304f, 0.306f, 0.309f, 0.311f, 0.312f, 0.314f, 0.318f, 0.319f, 0.321f,
            0.322f, 0.324f, 0.33f, 0.333f, 0.334f, 0.336f, 0.344f, 0.348f, 0.35f, 0.352f,
            0.361f, 0.365f, 0.368f, 0.371f, 0.38f, 0.383f, 0.384f, 0.386f, 0.394f, 0.396f,
            0.398f, 0.399f, 0.401f, 0.403f, 0.404f, 0.405f, 0.407f, 0.408f, 0.41f, 0.412f,
            0.413f, 0.416f, 0.418f, 0.42f, 0.422f, 0.425f, 0.432f, 0.435f, 0.437f, 0.44f,
            0.442f, 0.445f, 0.447f, 0.448f, 0.45f, 0.451f, 0.453f, 0.455f, 0.456f, 0.458f,
            0.46f, 0.462f, 0.464f, 0.466f, 0.467f, 0.47f, 0.476f, 0.478f, 0.48f, 0.483f,
            0.524f, 0.526f, 0.529f, 0.535f, 0.537f, 0.539f, 0.54f, 0.543f, 0.545f, 0.547f,
            0.548f, 0.55f, 0.552f, 0.553f, 0.555f, 0.556f, 0.558f, 0.56f, 0.562f, 0.565f,
            0.568f, 0.57f, 0.572f, 0.579f, 0.582f, 0.584f, 0.586f, 0.589f, 0.591f, 0.593f,
            0.595f, 0.596f, 0.598f, 0.599f, 0.6f, 0.602f, 0.604f, 0.605f, 0.607f, 0.609f,
            0.611f, 0.619f, 0.621f, 0.622f, 0.625f, 0.634f, 0.637f, 0.641f, 0.644f, 0.653f,
            0.655f, 0.657f, 0.661f, 0.669f, 0.671f, 0.672f, 0.674f, 0.681f, 0.683f, 0.684f,
            0.685f, 0.686f, 0.69f, 0.692f, 0.694f, 0.696f, 0.698f, 0.7f, 0.707f, 0.71f,
            0.713f, 0.72f, 0.722f, 0.725f, 0.727f, 0.73f, 0.732f, 0.734f, 0.736f, 0.737f,
            0.738f, 0.739f, 0.74f, 0.742f, 0.743f, 0.744f, 0.747f, 0.749f, 0.751f, 0.752f,
            0.754f, 0.756f, 0.758f, 0.76f, 0.767f, 0.773f, 0.775f, 0.777f, 0.779f, 0.781f,
            0.782f, 0.785f, 0.787f, 0.788f, 0.789f, 0.791f, 0.793f, 0.794f, 0.795f, 0.797f,
            0.799f, 0.8f, 0.802f, 0.805f, 0.808f, 0.81f, 0.812f, 0.82f, 0.822f, 0.824f,
            0.826f, 0.828f, 0.829f, 0.832f, 0.833f, 0.845f, 0.846f, 0.847f, 0.848f, 0.85f,
            0.851f, 0.852f, 0.854f, 0.856f, 0.857f, 0.858f, 0.864f, 0.865f, 0.866f, 0.867f,
            0.868f, 0.87f, 0.871f, 0.872f, 0.874f, 0.875f, 0.877f, 0.878f, 0.88f, 0.881f,
            0.882f, 0.884f, 0.885f, 0.887f, 0.888f, 0.889f, 0.891f, 0.891f, 0.892f, 0.893f,
            0.895f, 0.896f, 0.897f, 0.898f, 0.9f, 0.9f, 0.901f, 0.903f, 0.903f, 0.904f,
            0.906f, 0.907f, 0.908f, 0.909f, 0.91f, 0.911f, 0.913f, 0.914f, 0.915f, 0.916f,
            0.917f, 0.918f, 0.925f, 0.926f, 0.927f, 0.928f, 0.928f, 0.93f, 0.931f, 0.932f,
            0.933f, 0.933f, 0.934f, 0.935f, 0.936f, 0.937f, 0.937f, 0.938f, 0.939f, 0.94f,
            0.94f, 0.941f, 0.942f, 0.942f, 0.943f, 0.943f, 0.944f, 0.944f, 0.945f, 0.946f,
            0.946f, 0.947f, 0.948f, 0.949f, 0.949f, 0.95f, 0.951f, 0.951f, 0.952f, 0.953f,
            0.954f, 0.955f, 0.955f, 0.956f, 0.957f, 0.957f, 0.958f, 0.959f, 0.96f, 0.961f,
            0.961f, 0.967f, 0.968f, 0.968f, 0.969f, 0.97f, 0.97f, 0.97f, 0.971f, 0.972f,
            0.972f, 0.972f, 0.973f, 0.973f, 0.974f, 0.975f, 0.975f, 0.976f, 0.977f, 0.977f,
            0.981f, 0.981f, 0.981f, 0.982f, 0.982f, 0.982f, 0.982f, 0.983f, 0.983f, 0.983f,
            0.984f, 0.984f, 0.985f, 0.985f, 0.985f, 0.986f, 0.986f, 0.986f, 0.987f, 0.987f,
            0.987f, 0.988f, 0.988f, 0.989f, 0.989f, 0.989f, 0.99f, 0.99f, 0.99f, 0.991f,
            0.991f, 0.991f, 0.992f, 0.992f, 0.992f, 0.992f, 0.992f, 0.993f, 0.993f, 0.993f,
            0.993f, 0.993f, 0.993f, 0.993f, 0.994f, 0.994f, 0.994f, 0.994f, 0.994f, 0.995f,
            0.995f, 0.995f, 0.995f, 0.995f, 0.995f, 0.995f, 0.995f, 0.996f, 0.996f, 0.996f,
            0.996f, 0.996f, 0.996f, 0.996f, 0.996f, 0.997f, 0.997f, 0.997f, 0.997f, 0.997f,
            0.997f, 0.997f, 0.997f, 0.997f, 0.997f, 0.997f, 0.997f, 0.997f, 0.998f, 0.998f,
            0.998f, 0.998f, 0.998f, 0.998f, 0.998f, 0.998f, 0.998f, 0.998f, 0.998f, 0.998f,
            0.998f, 0.998f, 0.998f, 0.998f, 0.998f, 0.998f, 0.998f, 0.998f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};

    private int granularity = 1000;
    private AlphaFunction delegate;

    public UniformNoiseAlpha(AlphaFunction delegate) {
        this.delegate = delegate;
    }

    @Override
    public float execute(float value) {
        return noiseUniformLookup[(int) (delegate.execute(value) * granularity)];
    }

// This method was used for generating the lookupArray, if more granularity is required, it can be used again
//    public static void main(String[] args) throws IOException {
//        PerlinNoise perlin = new PerlinNoise(0);
//        final int size = 10000;
//
//        final float[] values = new float[size * size];
//        for (int x = 0; x < size; x++)
//            for (int y = 0; y < size; y++) {
//                final float value = (float) (1 + perlin.noise(x / 10f, y / 10f, 0)) / 2f;
//                values[x * size + y] = value;
//            }
//        System.out.println("Calculated");
//
//        Arrays.sort(values);
//        System.out.println("Sorted");
//
//        int granularity = 1000;
//        float[] result = new float[granularity];
//        int arrIndex =0;
//        for (int i = 0; i <result.length-1; i++) {
//            int index = (int) (1f*(i + 1) * values.length / granularity);
//            for (int j=arrIndex; j<(int) (values[index]*granularity); j++) {
//                result[j] = 1f*i/granularity;
//            }
//            arrIndex= (int) (values[index]*granularity);
//        }
//
//        for (int i=arrIndex; i<result.length; i++) {
//            result[i] = 1f;
//        }
//
//        for (int i=0; i<result.length; i++) {
//            System.out.print(result[i] + "f, ");
//            if (i%10==9)
//                System.out.println();
//        }
//    }
}
