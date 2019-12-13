package ca.aodong;

public class ImageVerify {
    public static boolean verifyImageData(ImageData img) {
        if (img.width() < 1 || img.height() < 1) return false;
        for (int pv : img.pixelValue()) {
            if (pv < 0 || pv > 255) return false;
        }
        return true;
    }

    public static boolean compareSizes(ImageData img1, ImageData img2) {
        return (img1.width() == img2.width() && img1.height() == img2.height());
    }
}
