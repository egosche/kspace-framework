// Erik Goesche ge76imih
package exercises;

import ij.IJ;
import ij.ImageJ;
import lme.DisplayUtils;
import mt.Image;
import mt.LinearImageFilter;

public class Exercise06 {
    public static void main(String[] args) {
        (new ImageJ()).exitWhenQuitting(true);
        Image cells = DisplayUtils.openImageFromInternet("https://upload.wikimedia.org/wikipedia/commons/8/86/Emphysema_H_and_E.jpg", ".jpg");

        LinearImageFilter sobelx = new LinearImageFilter(3, 3, "Sobelx");
        LinearImageFilter sobely = new LinearImageFilter(3, 3, "Sobely");
        sobelx.setBuffer(new float[]{1, 0, -1, 2, 0, -2, 1, 0, -1});
        sobely.setBuffer(new float[]{1, 2, 1, 0, 0, 0, -1, -2, -1});

        Image sobelxImage = sobelx.apply(cells);
        Image sobelyImage = sobely.apply(cells);
        sobelxImage.show();
        sobelyImage.show();

        // calculate the norm of the gradient for each pixel
        Image normImage = new Image(cells.width(), cells.height(), "Norm");
        for (int y = normImage.minIndexY(); y < normImage.maxIndexY(); y++) {
            for (int x = normImage.minIndexX(); x < normImage.maxIndexX(); x++) {
                float value = (float) Math.sqrt(Math.pow(sobelxImage.atIndex(x, y), 2) + Math.pow(sobelyImage.atIndex(x, y), 2));
                normImage.setAtIndex(x, y, value);
            }
        }
        normImage.show();

        // threshold
        Image thresholdImage = new Image(normImage.width(), normImage.height(), "Threshold");
        thresholdImage.setBuffer(normImage.buffer());

        for (int y = thresholdImage.minIndexY(); y < thresholdImage.maxIndexY(); y++) {
            for (int x = thresholdImage.minIndexX(); x < thresholdImage.maxIndexX(); x++) {
                if (thresholdImage.atIndex(x, y) < 150) {
                    thresholdImage.setAtIndex(x, y, 0.f);
                }
                else {
                    thresholdImage.setAtIndex(x, y, 1.f);
                }
            }
        }
        thresholdImage.show();
    }
}