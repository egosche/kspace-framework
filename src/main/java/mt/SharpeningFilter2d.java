/*
 * GaussFilter2d.java
 * Copyright (C) 2020 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
package mt;

public class SharpeningFilter2d extends LinearImageFilter {

    public SharpeningFilter2d(float focus) {
        super(3, 3, "SharpeningFilter");

        for (int i = 0; i < buffer.length; i++){
            buffer[i] = - (focus-1)/8;
        }
        setAtIndex(0,0,focus);

    }
}
