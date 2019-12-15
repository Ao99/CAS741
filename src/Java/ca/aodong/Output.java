package ca.aodong;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Output {
    private static ImageData img;
    private static ImageData segImage;
    private static int j;

    public static void displayThresholds() {
        if (!ThresCal.isThresValid()) return;
        j = ThresCal.frameIndex();
        if (ThresCal.chosenThresNum() == 1) {
            System.out.println("The single threshold value for frame " + (j + 1) + " is k= " + ThresCal.k1() + ".");
        } else if (ThresCal.chosenThresNum() == 2) {
            System.out.println("The multiple threshold values for frame " + (j + 1) + " are k1=" + ThresCal.k1() +
                    ", k2=" + ThresCal.k2() + ".");
        } else if (ThresCal.chosenThresNum() == 3) {
            System.out.println("The multiple threshold values for frame " + (j + 1) + " are k1=" + ThresCal.k1() +
                    ", k2=" + ThresCal.k2() + ", k3=" + ThresCal.k3() + ".");
        }
    }

    public static boolean writeOutput(String s) {
        j = ThresCal.frameIndex();
        if (!ThresCal.isThresValid()) {
            System.out.println("Warning: frame " + (j + 1) + " is not segmented nor saved.");
            return false;
        }
        createSegmentation();
        if (!ImageVerify.verifyImageData(segImage) || !ImageVerify.compareSizes(img, segImage)) {
            System.out.println("Error: frame " + (j + 1) + " is not correctly segmented.");
            return false;
        }
        int x = segImage.width();
        int y = segImage.height();
        BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_BYTE_GRAY);
        for (int n = 0; n < y; n++) {
            for (int m = 0; m < x; m++) {
//expression in Input.java:  pixelValue[n * x + m] = img.getRGB(m, n) & 0xFF;
                img.setRGB(m, n, segImage.pixelValue()[n * x + m] * 0x00010101);
            }
        }
        s += "frame" + (j + 1) + "_" + ThresCal.chosenThresNum() + ".bmp";
        File outputFile = new File(s);
        try {
            ImageIO.write(img, "bmp", outputFile);
        } catch (IOException e) {
            System.out.println("Error: Cannot write the output image to directory " + s);
            return false;
        }
        return true;
    }

    private static void createSegmentation() {
        int c = ThresCal.chosenThresNum();
        int k1 = ThresCal.k1();
        int k2 = ThresCal.k2();
        int k3 = ThresCal.k3();
        img = Input.loadedImages()[j];

        int[] pixelValue = new int[img.width() * img.height()];

        if (c == 1) {
            for (int i = 0; i < pixelValue.length; i++) {
                if (img.pixelValue()[i] > k1) pixelValue[i] = 255;
                else pixelValue[i] = 0;
            }
        } else if (c == 2) {
            for (int i = 0; i < pixelValue.length; i++) {
                if (img.pixelValue()[i] > k2) pixelValue[i] = 255;
                else if (img.pixelValue()[i] > k1) pixelValue[i] = 188;
                else pixelValue[i] = 0;
            }
        } else if (c == 3) {
            for (int i = 0; i < pixelValue.length; i++) {
                if (img.pixelValue()[i] > k3) pixelValue[i] = 255;
                else if (img.pixelValue()[i] > k2) pixelValue[i] = 215;
                else if (img.pixelValue()[i] > k1) pixelValue[i] = 155;
                else pixelValue[i] = 0;
            }
        }
        segImage = new ImageData(img.width(), img.height(), pixelValue);
    }
}
