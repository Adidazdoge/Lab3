package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        // Use JSONTranslator for actual implementation
        Translator translator = new JSONTranslator(); // Initialize the JSONTranslator

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {

//        List<String> countries = translator.getCountries();
//
//        if (countries.isEmpty()) {
//            System.out.println("No countries loaded.");
//        } else {
//            System.out.println("Available countries: ");
//            for (String country : countries) {
//                System.out.println(country);
//            }
//        }

        CountryCodeConverter countryCodeConverter = new CountryCodeConverter(); // Initialize the CountryCodeConverter
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter(); // Initialize the LanguageCodeConverter

        while (true) {
            String country = promptForCountry(translator, countryCodeConverter);  // Get the country code and name
            if ("quit".equalsIgnoreCase(country)) {
                break;
            }

            String language = promptForLanguage(translator, country, languageCodeConverter);  // Get the language code and name
            if ("quit".equalsIgnoreCase(language)) {
                break;
            }

            // Translate and print the result using the actual country name and language name
            String translatedName = translator.translate(country, language);
            System.out.println(countryCodeConverter.fromCountryCode(country) + " in "
                    + languageCodeConverter.fromLanguage(language) + " is "
                    + translatedName);
            System.out.println("Press enter to continue or type 'quit' to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if ("quit".equalsIgnoreCase(textTyped)) {
                break;
            }
        }
    }

    private static String promptForCountry(Translator translator, CountryCodeConverter countryCodeConverter) {
        List<String> countryCodes = translator.getCountries();  // List of 3-letter country codes
        List<String> countryNames = new ArrayList<>();

        // Convert country codes to names
        for (String code : countryCodes) {
            countryNames.add(countryCodeConverter.fromCountryCode(code));
        }

        System.out.println(countryNames);

        // Sort the country names alphabetically
        Collections.sort(countryNames, String::compareToIgnoreCase);

        // Print each country name
        for (String countryName : countryNames) {
            System.out.println(countryName);
        }

        System.out.println("Select a country from the list above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    private static String promptForLanguage(Translator translator, String country, LanguageCodeConverter languageCodeConverter) {
        List<String> languageCodes = translator.getCountryLanguages(country);  // Get available language codes for the selected country
        List<String> languageNames = new ArrayList<>();

        // Convert language codes to names
        for (String code : languageCodes) {
            languageNames.add(languageCodeConverter.fromLanguage(code));
        }

        // Sort the language names alphabetically
        Collections.sort(languageNames, String::compareToIgnoreCase);

        // Print each language name
        for (String languageName : languageNames) {
            System.out.println(languageName);
        }

        System.out.println("Select a language from the list above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
