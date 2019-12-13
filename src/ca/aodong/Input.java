package ca.aodong;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Input {
    private static ImageData[] loadedImages;
    private static int numFrames;
    private static boolean[] isLoaded;

    public static void loadInput(String s) {
        //Assume that the DICOM file has 6 frames, some frames can be damaged
        numFrames = 6;
        loadedImages = new ImageData[numFrames];
        for (int j = 0; j < numFrames; j++) {
            String name = s + "frame" + (j + 1) + ".bmp";
            File inputFile = new File(name);
            int x = 0;
            int y = 0;
            int[] pixelValue = new int[0];
            try {
                BufferedImage img = ImageIO.read(inputFile);
                x = img.getWidth();
                y = img.getHeight();
                pixelValue = new int[x * y];
                for (int n = 0; n < y; n++) {
                    for (int m = 0; m < x; m++) {
                        pixelValue[n * x + m] = img.getRGB(m, n) & 0xFF;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error: cannot find the file " + name);
            } catch (Exception e) {
                System.out.println("Error: the format of frame " + (j + 1) + " is not supported.");
            }
            loadedImages[j] = new ImageData(x, y, pixelValue);
        }
        verifyInput();
    }

    private static void verifyInput() {
        isLoaded = new boolean[numFrames];
        int cnt = 0;
        for (int j = 0; j < numFrames; j++) {
            isLoaded[j] = ImageVerify.verifyImageData(loadedImages[j]);
            if (isLoaded[j]) cnt++;
            else System.out.println("Warning: frame " + (j + 1) + " is not loaded.");
        }
        System.out.println(cnt + " image frames have been loaded.");
    }

    public static ImageData[] loadedImages() {
        return loadedImages;
    }

    public static int numFrames() {
        return numFrames;
    }

    public static boolean[] isLoaded() {
        return isLoaded;
    }
}
