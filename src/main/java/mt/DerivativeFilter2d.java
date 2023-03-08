/*
 * GaussFilter2d.java
 * Copyright (C) 2020 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
package mt;


public class DerivativeFilter2d extends LinearImageFilter {

    public DerivativeFilter2d(boolean transpose) {
        super(3, 1, "1st xDerivative");

        if(transpose == true) {
            height = 3;
            width = 1;
            name = "1st yDerivative";
        }
        buffer[0] = -1.0f;
        buffer[1] = 0.0f;
        buffer[2] = 1.0f;

    }
}
