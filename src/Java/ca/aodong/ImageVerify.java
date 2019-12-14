package ca.aodong;

public class ImageVerify {
    public static boolean verifyImageData(ImageData img) {
        if (img.width() < 0 || img.height() < 0) {
            System.out.println("Error: invalid image size.");
            return false;
        }
        if (img.width() == 0 || img.height() == 0) return false;
        for (int pv : img.pixelValue()) {
            if (pv < 0 || pv > 255) {
            System.out.println("Error: One or more pixel values are outside the bound of [0, 255].");
            return false;
            }
        }
        return true;
    }

    public static boolean compareSizes(ImageData img1, ImageData img2) {
        if(img1.width() != img2.width() || img1.height() != img2.height()) {
            System.out.println("Error: inconsistent image sizes.");
            return false;
        }
        return true;
    }
}
