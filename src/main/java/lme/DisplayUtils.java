/*
 * DisplayUtils.java
 * Copyright (C) 2020 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
// Erik Goesche ge76imih
package lme;

import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Plot;
import ij.measure.Calibration;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageConverter;
import ij.plugin.FFT;
import ij.process.ImageProcessor;
import mt.Image;
import net.imglib2.Cursor;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.labeling.ConnectedComponents;
import net.imglib2.converter.Converters;
import net.imglib2.converter.RealARGBConverter;
import net.imglib2.converter.RealTypeConverters;
import net.imglib2.img.array.ArrayCursor;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.basictypeaccess.array.FloatArray;
import net.imglib2.img.basictypeaccess.array.IntArray;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.FloatType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;


public class DisplayUtils {

    static List<Integer> randomColors;


    public static void showArray(float[] yValues, String title, double origin, double spacing) {
        showArray(yValues, new Plot(title, "X", "Y"), origin, spacing);
    }

    public static void showArray(float[] yValues, Plot plot, double origin, double spacing) {
        double[] yValuesDouble = new double[yValues.length];
        double[] xValues = new double[yValues.length];

        for (int i = 0; i < xValues.length; i++) {
            xValues[i] = origin + i * spacing;
            yValuesDouble[i] = (double) yValues[i];
        }

        // plot.setColor("red");
        plot.add("lines", xValues, yValuesDouble);
        plot.show();
    }

    public static void showImage(float[] buffer, String title, int width) {
        showImage(buffer, title, width, new float[]{0, 0}, 1.0, false);
    }

    public static void showImage(float[] buffer, String title, long width, float[] origin, double spacing, boolean replaceWindowWithSameName) {
        FloatProcessor processor = new FloatProcessor((int) width, buffer.length / (int) width, buffer);
        ImagePlus plus = new ImagePlus();
        if (replaceWindowWithSameName && ij.WindowManager.getImage(title) != null) {
            plus = ij.WindowManager.getImage(title);
        }
        plus.setProcessor(title, processor);

        Calibration calibration = new Calibration();
        calibration.setUnit("mm");
        calibration.xOrigin = origin[0];
        calibration.yOrigin = origin[1];
        calibration.pixelHeight = spacing;
        calibration.pixelWidth = spacing;

        plus.setCalibration(calibration);
        plus.show();
        ij.IJ.run("Tile");
    }

    public  static void FFT(float[] buffer, String title, long width, float[] origin, double spacing){
        FloatProcessor processor = new FloatProcessor((int) width, buffer.length / (int) width, buffer);
        ImagePlus plus = new ImagePlus();
        plus.setProcessor(title, processor);

        Calibration calibration = new Calibration();
        calibration.setUnit("mm");
        calibration.xOrigin = origin[0];
        calibration.yOrigin = origin[1];
        calibration.pixelHeight = spacing;
        calibration.pixelWidth = spacing;
        plus.setCalibration(calibration);

        FFT transform = new FFT();
        ImagePlus fft = transform.forward(plus);

        fft.show();
        ij.IJ.run("Tile");

    }

    public static mt.Image plusToImage(ImagePlus plus, String title) {
        ImageConverter converter = new ImageConverter(plus);
        converter.convertToGray32();
        FloatProcessor processor = (FloatProcessor) plus.getProcessor();
        return floatProcessorToImage(processor, title);
    }

    public static mt.Image floatProcessorToImage(FloatProcessor processor, String title) {
        Image image = new mt.Image(processor.getWidth(), processor.getHeight(), title);
        image.setBuffer((float[]) processor.getPixels());
        return image;
    }

    public static mt.Image openImageFromInternet(String url, String fileType) {
        if (new File(url).exists()) {
            return openImage(url);
        }
        try {
            Path tempFile = Files.createTempFile("mt2", fileType);
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile.toFile());
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            ImagePlus plus = ij.IJ.openImage(tempFile.toString());
            return plusToImage(plus, tempFile.toFile().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static mt.Image openImage(String path) {
        ImagePlus plus = ij.IJ.openImage(path);
        return plusToImage(plus, (new File(path)).getName());
    }
}


