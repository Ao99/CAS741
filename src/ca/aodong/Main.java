package ca.aodong;

public class Main {

    public static void main(String[] args) {
        Input input = new Input();
        ThresCal thresCal = new ThresCal();
        Output output = new Output();

        input.loadInput("test\\input\\");
        int cnt = 0;
        for (int j = 0; j < input.numFrames(); j++) {
            if (input.isLoaded()[j]) {
                thresCal.calculation(j);
                if (thresCal.isValidThresholds()) output.displayThresholds();
                if (output.writeOutput("test\\output\\frame" + (j + 1))) cnt++;
            }
        }
        System.out.println(cnt + " segmented frames have been saved.");
    }
}
