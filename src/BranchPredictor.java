import java.util.Arrays;
import java.util.List;

public class BranchPredictor {

    public static int predictions = 0;
    public static int correctPredictions;
    public static int[] ghr = new int[8];
    private static int[] states = new int[256];
    public static int ghrSize;

    public static void initialize(int setGhrSize){
        ghrSize = setGhrSize;
    }

    public static boolean predictTaken(){
        //printStates();
        predictions += 1;
        int ghrVal = getGhrValue();
        boolean prediction = states[ghrVal] >= 2;
        //System.out.println("New Prediction at GHR " + ghrVal + ": " + prediction);
        return prediction;
    }

    public static void wasTaken(boolean taken){
        int ghrVal = getGhrValue();
        int currState = states[ghrVal];
        int addState = currState + (taken ? 1 : -1);
        int clampState = clamp(addState, 0 ,3);
        //System.out.println("state before: " + currState);
        states[ghrVal] = clampState;
        //System.out.println("state after: " + clampState);
        shiftIn(taken);
    }

    public static void kudosCorrectPrediction(){
        correctPredictions += 1;
    }

    private static void shiftIn(boolean taken){
        //System.out.println("New Information: " + (taken ? "taken" : "not taken"));
        int i = ghrSize - 1;
        while(i != 0){
            ghr[i] = ghr[i - 1];
            i -= 1;
        }
        ghr[0] = taken ? 1 : 0;
        //System.out.println("GHR after shifting in: " + Arrays.toString(ghr));
    }

    private static int getGhrValue(){
        //System.out.println("Getting GHR value for " + Arrays.toString(ghr));
        int sum = 0;
        int currMult = 1;
        for(int i = 0; i < ghrSize; i+=1){
            sum += currMult * ghr[i];
            currMult *= 2;
        }
        //System.out.println("Resulting GHR value: " + sum);
        return sum;
    }

    private static int clamp(int val, int min, int max){
        return Math.min(Math.max(val, min), max);
    }

    private static void printStates(){
        int index = 0;
        int amnt = 4;
        System.out.println("\n\n====States====");
        for (int i = 0; i < amnt; i+=1){
            for(int j = 0; j < amnt; j+=1){
                index = (i*amnt) + j;
                System.out.print(index + ":" + states[index] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("========");
    }
}
