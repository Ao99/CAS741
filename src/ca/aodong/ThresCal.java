package ca.aodong;

import java.util.Scanner;

public class ThresCal {
    private static int imageIndex;
    private static int methodChoice;
    private static boolean validThresholds;
    private static int k1;
    private static int k2;
    private static int k3;

    private ImageData img;
    private double[] p;
    private double mg;

    public void calculation(int j) {
        imageIndex = j;
        getMethodChoice();
        img = Input.loadedImages()[j];

        p = new double[256];
        for (int i = 0; i <= 255; i++) {
            p[i] = p(i);
        }
        mg = mg();

        if (methodChoice == 1) {
            double maxSigma2b = 0.0;
            for (int t = 1; t <= 254; t++) {
                double currSigma2b = sigma2b(t);
                if (currSigma2b > maxSigma2b) {
                    k1 = t;
                    maxSigma2b = currSigma2b;
                }
            }
            if (k1 >= 1 && k1 <= 254) validThresholds = true;
        } else if (methodChoice == 2) {
            double maxSigma2b = 0.0;
            for (int t1 = 1; t1 <= 253; t1++) {
                for (int t2 = t1 + 1; t2 <= 254; t2++) {
                    double currSigma2b = sigma2b(t1, t2);
                    if (currSigma2b > maxSigma2b) {
                        k1 = t1;
                        k2 = t2;
                        maxSigma2b = currSigma2b;
                    }
                }
            }
            if (k1 >= 1 && k1 < k2 && k2 <= 254) validThresholds = true;
        } else if (methodChoice == 3) {
            double maxSigma2b = 0.0;
            for (int t1 = 1; t1 <= 252; t1++) {
                for (int t2 = t1 + 1; t2 <= 253; t2++) {
                    for (int t3 = t2 + 1; t3 <= 254; t3++) {
                        double currSigma2b = sigma2b(t1, t2, t3);
                        if (currSigma2b > maxSigma2b) {
                            k1 = t1;
                            k2 = t2;
                            k3 = t3;
                            maxSigma2b = currSigma2b;
                        }
                    }
                }
            }
            if (k1 >= 1 && k1 < k2 && k2 <= 254) validThresholds = true;
        }
    }

    private int n(int i) {
        int sum = 0;
        for (int k = 0; k < img.pixelValue().length; k++) {
            if (img.pixelValue()[k] == i) sum++;
        }
        return sum;
    }

    private double p(int i) {
        return (double) n(i) / (img.width() * img.height());
    }

    private double prb(int start, int end) {
        double sum = 0.0;
        for (int i = start; i <= end; i++) {
            sum += p[i];
        }
        return sum;
    }

    private double m(int start, int end) {
        double sum = 0.0;
        for (int i = start; i <= end; i++) {
            sum += i * p[i];
        }
        return sum / prb(start, end);
    }

    private double mg() {
        double sum = 0.0;
        for (int i = 0; i <= 255; i++) {
            sum += i * p[i];
        }
        return sum;
    }

    //a series of overloading method sigma2b(t1, t2, t3, ....)
    private double sigma2b(int t1) {
        return prb(0, t1) * Math.pow(m(0, t1) - mg, 2) +
                prb(t1 + 1, 255) * Math.pow(m(t1 + 1, 255) - mg, 2);
    }

    private double sigma2b(int t1, int t2) {
        return prb(0, t1) * Math.pow(m(0, t1) - mg, 2) +
                prb(t1 + 1, t2) * Math.pow(m(t1 + 1, t2) - mg, 2) +
                prb(t2 + 1, 255) * Math.pow(m(t2 + 1, 255) - mg, 2);
    }

    private double sigma2b(int t1, int t2, int t3) {
        return prb(0, t1) * Math.pow(m(0, t1) - mg, 2) +
                prb(t1 + 1, t2) * Math.pow(m(t1 + 1, t2) - mg, 2) +
                prb(t2 + 1, t3) * Math.pow(m(t2 + 1, t3) - mg, 2) +
                prb(t3 + 1, 255) * Math.pow(m(t3 + 1, 255) - mg, 2);
    }

    private void getMethodChoice() {
        while (methodChoice == 0) {
            Scanner choiceInput = new Scanner(System.in);
            System.out.println("********************************************");
            System.out.println("Number of thresholds to use:");
            System.out.println("please input a number from the set " + Constants.numThres.toString());
            System.out.println("********************************************");
            try {
                methodChoice = choiceInput.nextInt();
                if (methodChoice > 3 || methodChoice < 1) {
                    System.out.println("Error: input is not a number from the set,");
                    System.out.println("please read the following instructions carefully and try again:");
                    methodChoice = 0;
                }
            } catch (Exception e) {
                System.out.println("Error: input is not a number from the set,");
                System.out.println("please read the following instructions carefully and try again:");
            }
        }
    }

    public static int imageIndex() {
        return imageIndex;
    }

    public static int methodChoice() {
        return methodChoice;
    }

    public static boolean isValidThresholds() {
        return validThresholds;
    }

    public static int k1() {
        return k1;
    }

    public static int k2() {
        return k2;
    }

    public static int k3() {
        return k3;
    }
}
