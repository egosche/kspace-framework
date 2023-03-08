/*
 * GaussFilter2d.java
 * Copyright (C) 2020 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
package mt;


public class AverageFilter2d extends LinearImageFilter {

    public AverageFilter2d(int filtersize) {
        super(filtersize, filtersize, "AverageFilter");

        for (int i = 0; i < buffer.length; i++){
            buffer[i] = (float) 1/(filtersize*filtersize);
        }

    }
}
