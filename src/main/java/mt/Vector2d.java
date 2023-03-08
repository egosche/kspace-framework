/*
 * Vector2d.java
 * Copyright (C) 2021 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
package mt;

public class Vector2d {
    float kx, ky;

    public Vector2d(float kx, float ky) {
        this.kx = kx;
        this.ky = ky;
    }
    public float dot(Vector2d other) {
        return kx * other.kx + ky * other.ky;
    }
}
