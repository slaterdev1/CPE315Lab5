import java.util.List;

public class BranchPredictor {

    public static int predictions;
    public static int correctPredictions;
    public static int[] ghr = new int[8];
    private static int[] states = new int[1000];
    public static int ghrSize;

    public void initialize(int setGhrSize){
        ghrSize = setGhrSize;
    }

    public static boolean predictTaken(int pc){
        return states[getGhrValue()] >= 2;
    }

    public static void wasTaken(int pc, boolean taken){
        // don't know which of the two following lines should come first
        shiftIn(taken);
        int currState = states[getGhrValue()];
        int addState = currState + (taken ? 1 : -1);
        int clampState = clamp(addState, 0 ,3);
        states[getGhrValue()] = clampState;
    }

    private static void shiftIn(boolean taken){
        System.out.println("New Prediction: " + (taken ? "taken" : "not taken"));
        int i = ghrSize - 1;
        while(i != 0){
            ghr[i] = ghr[i - 1];
            i -= 1;
        }
        ghr[0] = taken ? 1 : 0;
        System.out.println("GHR after shifting in: " + ghr.toString());
    }

    private static int getGhrValue(){
        int sum = 0;
        int currMult = 1;
        for(int i = 0; i < ghrSize; i+=1){
            sum += currMult * ghr[i];
            currMult *= 2;
        }
        return sum;
    }

    private static int clamp(int val, int min, int max){
        return Math.min(Math.max(val, min), max);
    }
}
