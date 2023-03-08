/*
 * CosineWave.java
 * Copyright (C) 2021 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
// Erik Goesche ge76imih
package mt;

// Create your Cosine wave class here.
public class CosineWave extends Signal {
    public CosineWave(int numWaves, int numSamples) {
        super(numSamples, "Cosine " + numWaves);
        for (int i = 0; i < buffer.length; ++i) {
            buffer[i] = (float) Math.cos(i * Math.PI * 2.0 / numSamples * numWaves);
        }
    }
}
