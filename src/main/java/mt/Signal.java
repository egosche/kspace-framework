
/*
 * Signal.java
 * Copyright (C) 2020 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
// Erik Goesche ge76imih
package mt;

import lme.DisplayUtils;
import java.util.Random;


public class Signal {
	protected float[] buffer; //Array to store signal values
	protected String name; //Name of the signal
	protected int minIndex =  0 ;
	protected float spacing = 1.0f;

	public Signal(int length, String name) {
		buffer = new float[length];
		this.name = name;
	}

	public Signal(float[] buffer, String name) {
		this.buffer = buffer;
		this.name = name;
	}

	public void show() {
		DisplayUtils.showArray(buffer, name, /*start index =*/0, spacing);
	}

	public int size() {
		return buffer.length;
	}

	public float[] buffer() {
		return buffer;
	}

	public String name() {
		return name;
	}
	
	public int minIndex(){ return minIndex;}

	public int maxIndex(){
		this.minIndex();
		int maxIndex = this.minIndex() + this.size()-1;
		return  maxIndex;
	}

	public void setSpacing(float spacing) {
		this.spacing = spacing;
	}
	public float spacing() {
		return this.spacing;
	}

	public float atIndex(int i) {
		int arrayIdx = i - this.minIndex;
		if (arrayIdx < 0 || arrayIdx >= buffer.length) {
			return 0.f;
		} else {
			return buffer[arrayIdx];
		}
	}

	public void setAtIndex(int i, float value){
		int arrayIdx = i - this.minIndex();
		buffer[arrayIdx] = value;
	}

	public Signal plus(Signal other) {
		if (size() != other.size()) {
			throw new RuntimeException("Java sucks: both Signals don't have the same size!");
		}
		Signal result = new Signal(this.size(), name() + " + " + other.name());
		for (int i = 0; i < result.size(); i++) {
			result.buffer()[i] = buffer()[i] + other.buffer()[i];
		}
		return result;
	}

	public Signal times(float scalar) {
		Signal result = new Signal(this.size(), scalar + "*" + name());
		for (int i = 0; i < result.size(); i++) {
			result.buffer()[i] = buffer()[i] * scalar;
		}
		return result;
	}
	
	public float sum() {
		float rtn = 0;
		for (float f : buffer) {
			rtn += f;
		}
		return rtn;
	}

	public float max() {
		float max = Float.NEGATIVE_INFINITY;
		for (float f : buffer) {
			max = Math.max(f, max);
		}
		return max;
	}

	public void setName(String name){
		this.name = name;
	}

	public void addNoise(float mean, float standardDeviation) {
		Random rand = new Random();
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] += mean + rand.nextGaussian() * standardDeviation;
		}
	}

}
