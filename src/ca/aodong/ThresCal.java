package ca.aodong;

import java.util.Scanner;

public class ThresCal {
    private static int frameIndex;
    private static int chosenThresNum;
    private static boolean isThresValid;
    private static int k1;
    private static int k2;
    private static int k3;
    private static ImageData img;

    //The following state variables are not required by MIS.
    //They're here for the interests of conciseness and code performance
    private static double[] p;
    private static double mg;

    public static void calculation(int j) {
        frameIndex = j;
        if (!Input.isLoaded()[j]) {
            isThresValid = false;
            return;
        }
        img = Input.loadedImages()[j];
        getUserChoice();

        p = new double[256];
        for (int i = 0; i <= 255; i++) {
            p[i] = p(i);
        }
        mg = mg();

        if (chosenThresNum == 1) {
            double maxSigma2b = 0.0;
            for (int t = 1; t <= 254; t++) {
                double currSigma2b = sigma2b(t);
                if (currSigma2b > maxSigma2b) {
                    k1 = t;
                    maxSigma2b = currSigma2b;
                }
            }
            if (k1 >= 1 && k1 <= 254) isThresValid = true;
            else System.out.println("Error: incorrect calculation. The result does not follow the rule: 1≤k1≤254");

        } else if (chosenThresNum == 2) {
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
            if (k1 >= 1 && k1 < k2 && k2 <= 254) isThresValid = true;
            else System.out.println("Error: incorrect calculation. The result does not follow the rule: 1≤k1<k2≤254");

        } else if (chosenThresNum == 3) {
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
            if (k1 >= 1 && k1 < k2 && k2 < k3 && k3 <= 254) isThresValid = true;
            else System.out.println("Error: incorrect calculation. The result does not follow the rule: 1≤k1<k2<k3≤254");
        }
    }

    private static int n(int i) {
        int sum = 0;
        for (int k = 0; k < img.pixelValue().length; k++) {
            if (img.pixelValue()[k] == i) sum++;
        }
        return sum;
    }

    private static double p(int i) {
        return (double) n(i) / (img.width() * img.height());
    }

    private static double prb(int start, int end) {
        double sum = 0.0;
        for (int i = start; i <= end; i++) {
            sum += p[i];
        }
        return sum;
    }

    private static double m(int start, int end) {
        double sum = 0.0;
        for (int i = start; i <= end; i++) {
            sum += i * p[i];
        }
        return sum / prb(start, end);
    }

    private static double mg() {
        double sum = 0.0;
        for (int i = 0; i <= 255; i++) {
            sum += i * p[i];
        }
        return sum;
    }

    //a series of overloading method sigma2b(t1, t2, t3, ....)
    private static double sigma2b(int t1) {
        return prb(0, t1) * Math.pow(m(0, t1) - mg, 2) +
                prb(t1 + 1, 255) * Math.pow(m(t1 + 1, 255) - mg, 2);
    }

    private static double sigma2b(int t1, int t2) {
        return prb(0, t1) * Math.pow(m(0, t1) - mg, 2) +
                prb(t1 + 1, t2) * Math.pow(m(t1 + 1, t2) - mg, 2) +
                prb(t2 + 1, 255) * Math.pow(m(t2 + 1, 255) - mg, 2);
    }

    private static double sigma2b(int t1, int t2, int t3) {
        return prb(0, t1) * Math.pow(m(0, t1) - mg, 2) +
                prb(t1 + 1, t2) * Math.pow(m(t1 + 1, t2) - mg, 2) +
                prb(t2 + 1, t3) * Math.pow(m(t2 + 1, t3) - mg, 2) +
                prb(t3 + 1, 255) * Math.pow(m(t3 + 1, 255) - mg, 2);
    }

    private static void getUserChoice() {
        while (chosenThresNum == 0) {
            Scanner choiceInput = new Scanner(System.in);
            System.out.println("********************************************");
            System.out.println("Number of thresholds to use:");
            System.out.println("please input a number from the set " + Constants.numsThres.toString());
            System.out.println("********************************************");
            try {
                chosenThresNum = choiceInput.nextInt();
                if (chosenThresNum > 3 || chosenThresNum < 1) {
                    System.out.println("Error: input is not a number from the set,");
                    System.out.println("please read the following instructions carefully and try again:");
                    chosenThresNum = 0;
                }
            } catch (Exception e) {
                System.out.println("Error: input is not a number from the set,");
                System.out.println("please read the following instructions carefully and try again:");
            }
        }
    }

    public static int frameIndex() {
        return frameIndex;
    }

    public static int chosenThresNum() {
        return chosenThresNum;
    }

    public static boolean isThresValid() {
        return isThresValid;
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
