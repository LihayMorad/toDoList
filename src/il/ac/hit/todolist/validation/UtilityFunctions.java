package il.ac.hit.todolist.validation;




public class UtilityFunctions {


    //Method for task description validation
    public static void OnlyLettersNumbersAndSpaces(String inputString) throws IllegalArgumentException {
        if (!inputString.matches("^[A-Za-z0-9-, ]{3,50}$"))
            throw new IllegalArgumentException("Error : the expression might contain letters,numbers,commas and dashes only\nMinimal length - 3\n");
    }

    //Method for user validation
    public static void onlyLettersAndNumbers(String inputString) throws IllegalArgumentException {
        if (!inputString.matches("^[A-Za-z0-9]{3,20}$"))
            throw new IllegalArgumentException("Error : the expression might contain letters and numbers only\nMinimal length - 3.\n");
    }

    public static void passwordValidation(String inputString) throws IllegalArgumentException {
        if (!inputString.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,15}$"))
            throw new IllegalArgumentException("Password must contain at least 1 small letter, 1 big letter, 1 number, 1 special symbol(@#$%^&+=)\nLength 7-15 symbols.");
    }

    public static boolean BooleanValidator(String inputString) {
        String yes = "yes";
        return Boolean.parseBoolean(inputString) || yes == inputString.toLowerCase();
    }

    public static int integerParser(String inputString) throws NumberFormatException {
        inputString = inputString.trim();

        int parsedString = Integer.MIN_VALUE;

        if (!inputString.matches("^[0-9]+$"))
            throw new NumberFormatException("Your input must be numeric value");

        parsedString = Integer.parseInt(inputString);

        return parsedString;
    }

    public static void DateValidation (String inputString) {
        if(!inputString.matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))
            throw new IllegalArgumentException("Inappropriate date format\nYou may use the following formats: mm/dd/yy, mm/dd/yyyy, dd/mm/yy, dd/mm/yyyy");
    }

}
