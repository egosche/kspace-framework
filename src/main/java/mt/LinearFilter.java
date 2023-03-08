/*
 * filter.java
 * Copyright (C) 2020 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
package mt;

public class LinearFilter extends Signal {


	public LinearFilter(float[] coefficients, String name) {
		super(coefficients, name);
		minIndex = -coefficients.length / 2;
		if (1 != (coefficients.length % 2)) {
			throw new RuntimeException("Filter not even");
		}

	}

	public Signal apply(Signal input) {
		Signal result = new Signal(input.size(), input.name() + " (filtered with " + name() + ")");
		for (int i = 0; i < result.size(); ++i) {
			float sum = 0.f;
			for (int j = this.minIndex(); j <= this.maxIndex(); j++) {
				sum += input.atIndex(i + j) * this.atIndex(-j);
			}
			result.buffer()[i] = sum;
		}

		return result;
	}

}
