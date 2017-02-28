package tests;

import algos.SimpleAlgo;
import exceptions.InvalidInputException;

/**
 * Created by zhenia on 27.02.17.
 */
public class Main {
    public static void main(String[] args) {
        double y;

        try{
            y = SimpleAlgo.calculate(1, 2, 3);
            System.out.println(y);
        } catch (ArithmeticException e){
            System.out.println(e.getMessage());
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
    }
}
