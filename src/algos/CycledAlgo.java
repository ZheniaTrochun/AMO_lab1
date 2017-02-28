package algos;

import exceptions.InvalidInputException;

/**
 * Created by zhenia on 27.02.17.
 */
public class CycledAlgo {
    public static int operations = 0;

    public static double calculate(int n, Double[] c, double f, double g) throws InvalidInputException {
        if(!checkInput(n, c)) throw new InvalidInputException("Incorrect input");

        double y = 0;

        for (int a = 0; a < n; a++) {
            operations += 6;
            y += a * a + 56 * c[a] * f * g;
        }

        return y;
    }

    private static boolean checkInput(int n, Double[] c){
        return n >= 0 && c.length == n;
    }
}
