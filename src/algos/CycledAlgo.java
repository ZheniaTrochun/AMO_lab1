package algos;

import exceptions.InvalidInputException;

/**
 * Created by zhenia on 27.02.17.
 */
public class CycledAlgo {
    public static double calculate(int n, double[] c, double f, double g) throws InvalidInputException {
        if(!checkInput(n, c)) throw new InvalidInputException("Incorrect input");

        double y = 0;

        for (int a = 0; a < n; a++) {
            y += a * a + 56 * c[a] * f * g;
        }

        return y;
    }

    private static boolean checkInput(int n, double[] c){
        return n >= 0 && c.length == n;
    }
}
