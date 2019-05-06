package com.hit.todo.model;


import com.sun.media.sound.InvalidFormatException;

import java.security.InvalidParameterException;


public class UtilityFunctions  {

    public static void OnlyLettersNumbersAndSpaces(String inputString)  throws  IllegalArgumentException{
        if(!inputString.matches("[^A-Za-z0-9 ]+"))
              throw new IllegalArgumentException("Inappropriate format\n");
    }

    public static int IntegerParser(String inputString){
        inputString=inputString.trim();
        return Integer.parseInt(inputString);
    }
}
