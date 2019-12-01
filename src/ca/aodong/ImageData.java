package ca.aodong;

public class ImageData {
    private int width;
    private int height;
    private int[] pixelValue;

    public ImageData(int width, int height, int[] pixelValue) {
        this.width = width;
        this.height = height;
        this.pixelValue = pixelValue;
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
