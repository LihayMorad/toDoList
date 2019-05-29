package il.ac.hit.todolist.model;

import java.util.regex.*;

import com.sun.media.sound.InvalidFormatException;

import java.security.InvalidParameterException;


public class UtilityFunctions {


    //Method for task description validation
    public static void OnlyLettersNumbersAndSpaces(String inputString) throws IllegalArgumentException {
        if (!inputString.matches("[^A-Za-z0-9 ]+"))
            throw new IllegalArgumentException("Inappropriate format\n");
    }

    //Method for user validation
    public static void onlyLettersAndNumbers(String inputString) throws IllegalArgumentException {
        if (!inputString.matches("[^A-Za-z0-9]{3,10}$"))
            throw new IllegalArgumentException("Inappropriate format\n");
    }

    public static void passwordValidation(String inputString) throws IllegalArgumentException {
        if (!inputString.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,15}$"))
            throw new IllegalArgumentException("Invalid password\n");
    }

    public static boolean BooleanValidator(String inputString) {
        String yes = "yes";
        return Boolean.parseBoolean(inputString) || yes == inputString.toLowerCase();
    }

    public static int integerParser(String inputString) throws NumberFormatException {
        inputString = inputString.trim();

        int parsedString = Integer.MIN_VALUE;

        if (!inputString.matches("[0-9]+"))
            throw new NumberFormatException();

        parsedString = Integer.parseInt(inputString);

        return parsedString;
    }

}
