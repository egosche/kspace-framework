/*
 * GaussFilter2d.java
 * Copyright (C) 2020 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
package mt;

public class GaussFilter2d extends LinearImageFilter {
    public GaussFilter2d(int filterSize, float sigma) {
        /// 1 P für super call und normalize
        super(filterSize, filterSize, "Gauss2d (" + filterSize + ", " + sigma + ")");
        /// 1 P für Formel richtig angewandt
        for (int y = minIndexY; y < minIndexY + height(); ++y) {
            for (int x = minIndexX; x < minIndexX + width(); ++x) {
                setAtIndex(x, y, (float) (1.f / (2 * Math.PI * sigma * sigma)
                        * Math.exp(-(x * x + y * y) / (2.f * sigma * sigma))));

            }
        }
        normalize();
    }
}
