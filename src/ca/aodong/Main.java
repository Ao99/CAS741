package ca.aodong;

public class Main {

    public static void main(String[] args) {
        Input input = new Input();
        ThresCal thresCal = new ThresCal();
        Output output = new Output();

        input.loadInput("test\\input\\");
        for (int i = 0; i < input.numFrames(); i++) {
            if (input.isLoaded()[i]) thresCal.calculation(i);
            if (thresCal.isValidThresholds()) output.displayThresholds();
            output.writeOutput("test\\output\\frame" + (i + 1));
        }
        System.out.println(Input.numFrames() + " frames have been segmented.");
    }
}
