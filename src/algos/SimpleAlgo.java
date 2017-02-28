package algos;

import exceptions.InvalidInputException;

/**
 * Created by zhenia on 27.02.17.
 */
public class SimpleAlgo {
    public static double calculate(double a, double b, double c) throws InvalidInputException{
        if(!checkInput(a, b, c)) throw new InvalidInputException("div by zero");
        return ((a/b + b/a) * (a/c + c/a))/(a + b + c);
    }

    public static boolean checkInput(double a, double b, double c){
        return a != 0 && b !=0 && c != 0 && a + b + c != 0;
    }
}
