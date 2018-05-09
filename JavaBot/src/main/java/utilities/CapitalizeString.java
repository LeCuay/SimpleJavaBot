package utilities;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Simple class to capitalize Strings.
 * @author LeCuay
 * @version 0.1
 */
public class CapitalizeString {
	
    /**
     * Simple method to capitalize the very first letter of a String.
     * @param text The String to capitalize.
     * @return The String with the first letter capitalized.
     */
    public static String byFirstLetter(final String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    /**
     * Simple method to capitalize a String separating words by whitespaces.
     * @param text The String to capitalize.
     * @return The String with every word capitalized.
     */
    public static String byWhiteSpaces(final String text) {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(text.split(" ")));
        StringBuilder finalText = new StringBuilder("");
        words.forEach(word -> finalText.append(byFirstLetter(word)).append(" "));
        return finalText.toString();
    }

    /**
     * Simple method to capitalize a letter by Index.
     * @param text The String to capitalize.
     * @param index The index of the letter to capitalize.
     * @return The String with the letter capitalized.
     */
    public static String byIndex(final String text, int index) {
            return text.substring(0, index) + Character.toUpperCase(text.charAt(index)) + text.substring(index + 1);
    }

    /**
     * Decapitalizes the very first letter of a String.
     * @param text The String to decapitalize.
     * @return The String with the first letter decapitalized.
     */
    public static String decapitalizeByFirstLetter(final String text) {
        return Character.toLowerCase(text.charAt(0))+text.substring(1);
    }
	
}
