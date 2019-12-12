package ca.aodong;

public class Main {

    public static void main(String[] args) {
        String filenameIn = "test/input/";
        String filenameOut = "test/output/frame";

        Input.loadInput(filenameIn);
        int cnt = 0;
        for (int j = 0; j < Input.numFrames(); j++) {
            ThresCal.calculation(j);
            Output.displayThresholds();
            if (Output.writeOutput(filenameOut)) cnt++;
        }
        System.out.println(cnt + " segmented frames have been saved.");
    }
}
