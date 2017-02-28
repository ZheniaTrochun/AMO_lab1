package algos;

import exceptions.InvalidInputException;

/**
 * Created by zhenia on 27.02.17.
 */
public class BranchedAlgo {
    public static int operations = 0;

    public static double calculate(double d, double m, int i) throws InvalidInputException{
        if(i % 2 == 1){
            if(i * d == 0) throw new InvalidInputException("Div by zero");

            operations = 15;

            return (d * Math.pow(m, 5) - Math.pow(d, 5) * m)/(i * d);
        }

        double divider = d * Math.pow(m, 3) - Math.pow(d, 3) * m;

        if(divider == 0) throw new InvalidInputException("Div by zero");

        operations = 11;

        return (i * d)/(divider);
    }
}
