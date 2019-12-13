package ca.aodong;

public class ImageData {
    private int width;
    private int height;
    private int[] pixelValue;

    public ImageData(int width, int height, int[] pixelValue) {
        if (width >= 0) {
            this.width = width;
        } else {
            System.out.println("Error: image width cannot be negative.");
        }
        if (height >= 0) {
            this.height = height;
        } else {
            System.out.println("Error: image height cannot be negative.");
        }
        if (pixelValue.length == width * height) {
            this.pixelValue = pixelValue;
        } else {
            System.out.println("Error: the length of pixel value sequence must equal to width × height.");
        }
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int[] pixelValue() {
        return pixelValue;
    }
}
