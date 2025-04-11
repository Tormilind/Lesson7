package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Challenge7 {

    private static final Random random = new Random();
    
    public static void main(String[] args) {

        // The first part. Similar to the previous lesson. Not using transform method.
        // ---------------------------------------------------------------------------------------------------
        // Creating inputs
        String[] names1 = {"Mari", "Jaan", "Rein", "Kati", "Meelis", "Ruta", "Erik", "Anna", "Johan", "Lars"};
        List<String> backingArray = Arrays.asList(names1);
        System.out.println("Starting original array: " + Arrays.toString(names1));
        
        //Creating functions
        UnaryOperator<String> upperCaseOp = String::toUpperCase;
        UnaryOperator<String> addMiddleLetter = s -> s.concat(" " +  getRandomChar('A', 'Z'));
        UnaryOperator<String> addReversedName = s -> s + (" " + getReversedName(s.split(" ")[0]));
        List<Function<String, String>> listOfFunctions = new ArrayList<>(List.of(upperCaseOp, addMiddleLetter, addReversedName));
        
        //Applying functions
        for (int i = 0; i < backingArray.size(); i++) {
            String currentName = backingArray.get(i);
            
            for (Function<String, String> function : listOfFunctions) {
                currentName = function.apply(currentName);
            }
            //Save the transformation
            backingArray.set(i, currentName);
        }
        
        //Check that the original array got changed.
        System.out.println("Ending original array: " + Arrays.toString(names1));

        
        // Advanced transformations. Now using transform
        // ---------------------------------------------------------------------------------------------------
        // Creating inputs
        
        String[] solutionNames = {"Mari", "Jaan", "Rein", "Kati", "Meelis", "Ruta", "Erik", "Anna", "Johan", "Lars", "Markus", "Tanel", "Kiir"};

        System.out.println("Solution starting array: " + Arrays.toString(solutionNames));
        
        //Creating functions
        List<Function<String, String>> solutionFunctionList = createSolutionFunctionList();
        
        //Use the created methods
        applyAllTransformationsToArray(solutionNames, solutionFunctionList);
        
        //Check the original array
        System.out.println("Solution ending array: " + Arrays.toString(solutionNames));
    }
    
    public static List<Function<String, String>> createSolutionFunctionList(){
        List<Function<String, String>> listOfFunctions = new ArrayList<>();
        
        //Remove all J-s from names
        UnaryOperator<String> removeAllJ = s -> s.replaceAll("(?i)j", "");
        
        //Add text to all names
        Function<String, String> addNimiToStart = s -> "Nimi: " + s;
        Function<String, String> addOnToEnd = s -> s.concat(" On");
        Function<String, String> addTextToNames = addNimiToStart.andThen(addOnToEnd);
        
        // Uppercase strings
        Function<String, String> upperCase = String::toUpperCase;
        
        // Replace all names with the total length of their names
        Function<String, Integer> toLength = String::length;
        Function<String, String> toLengthString = toLength
                .andThen(Object::toString);
        
        listOfFunctions.add(removeAllJ);
        listOfFunctions.add(addTextToNames);
        listOfFunctions.add(upperCase);
        listOfFunctions.add(toLengthString);
        
        return listOfFunctions;
    }
    
    public static void applyAllTransformationsToArray(String[] nameArray, List<Function<String, String>> functionList){
        List<String> solutionBackingList = Arrays.asList(nameArray);
        for (int i = 0; i < solutionBackingList.size(); i++) {

            String currentName = solutionBackingList.get(i);
            System.out.println("Starting name: " + currentName);
            
            for (Function<String, String> function : functionList) {
                currentName = currentName.transform(function);
                System.out.println("After transformation: " + currentName);
            }
            //Save the transformation
            solutionBackingList.set(i, currentName);
        }
    }

    public static char getRandomChar(char startChar, char endChar) {
        return (char) random.nextInt(startChar, endChar + 1);
    }

    private static String getReversedName(String firstName) {
        return new StringBuilder(firstName).reverse().toString();
    }
}
