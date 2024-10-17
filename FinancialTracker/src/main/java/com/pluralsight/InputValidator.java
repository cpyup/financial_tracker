package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputValidator {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    /**
     * Checks if the given date string is in the valid format of yyyy-MM-dd.
     *
     * <p>This method uses a regular expression to determine if the input string
     * matches the expected date format. The format must consist of four digits
     * for the year, followed by a hyphen, two digits for the month, another
     * hyphen, and two digits for the day. For example, "2023-10-17" is a valid
     * date format, while "10-17-2023" is not.</p>
     *
     * @param dateString the date string to be validated
     * @return {@code true} if the date string is in the valid format;
     *         {@code false} otherwise
     */
    private static boolean isValidDateFormat(String dateString) {
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        return dateString.matches(regex);
    }

    /**
     * Validates whether the given date string represents a valid date.
     *
     * <p>This method expects the date string to be in the format yyyy-MM-dd.
     * It first splits the string into year, month, and day components. It checks
     * that there are exactly three parts and that each part can be parsed as an
     * integer. The method also validates the month (should be between 1 and 12)
     * and the day, taking into account the varying number of days in each month,
     * as well as leap years.</p>
     *
     * @param dateString the date string to be validated
     * @return {@code true} if the date string represents a valid date;
     *         {@code false} otherwise
     */
    private static boolean isValidDate(String dateString) {
        String[] parts = dateString.split("-");
        if (parts.length != 3) {
            return false; // Invalid format
        }

        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            // Validate month
            if (month < 1 || month > 12) {
                return false;
            }

            // Validate day based on month
            int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                daysInMonth[1] = 29; // Leap year
            }

            return day > 0 && day <= daysInMonth[month - 1];
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean checkForEscape(String input){
        return input.equalsIgnoreCase("exit");
    }

    /**
     * Prompts the user to enter a validated date and returns it as a {@link LocalDate} object.
     * <p>
     * The method repeatedly asks for user input until a valid date in the format "yyyy-MM-dd"
     * is provided. If the {@code isNullable} parameter is {@code true} and the user enters
     * a blank input, the method returns {@code null}, rather than continuing the prompt.
     * This behaviour is to accommodate custom filter inputs.
     * </p>
     *
     * @param scanner     a {@link Scanner} instance for reading user input
     * @param isNullable  a boolean indicating whether a blank input is allowed (returns {@code null} if true)
     * @return a {@link LocalDate} representing the validated date input by the user, or {@code null} if input is blank
     */
    public static LocalDate getValidatedDate(Scanner scanner, boolean isNullable) {
        LocalDate date;
        while (true) {
            System.out.print("Enter transaction date (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine().trim();

            if (isNullable && dateInput.isBlank()) {
                return null;
            }

            if(checkForEscape(dateInput)){
                return null;
            }

            try {
                if (!isValidDateFormat(dateInput)) {
                    System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    continue;
                }

                if(isValidDate(dateInput)){
                    date = LocalDate.parse(dateInput, DATE_FORMATTER);
                    break; // Exit loop on successful parsing
                }else{
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Date does not exist. Please enter a valid date.");
            }
        }
        return date;
    }

    /**
     * Prompts the user to enter a validated date and returns it as a {@link LocalDate} object.
     * <p>
     * This overload of the method does not allow for a blank input. It repeatedly asks for
     * user input until a valid date in the format "yyyy-MM-dd" is provided.
     * </p>
     *
     * @param scanner a {@link Scanner} instance for reading user input
     * @return a {@link LocalDate} representing the validated date input by the user
     */
    public static LocalDate getValidatedDate(Scanner scanner){
        return  getValidatedDate(scanner,false);
    }

    /**
     * Prompts the user to enter a validated time and returns it as a {@link LocalTime} object.
     * <p>
     * The method repeatedly asks for user input until a valid time in the format "HH:mm:ss"
     * is provided. It parses the input and returns the corresponding {@link LocalTime}
     * instance.
     * </p>
     *
     * @param scanner a {@link Scanner} instance for reading user input
     * @return a {@link LocalTime} representing the validated time input by the user
     */
    public static LocalTime getValidatedTime(Scanner scanner) {
        LocalTime time;
        while (true) {
            System.out.print("Enter transaction time (HH:mm:ss): ");
            String timeInput = scanner.nextLine().trim();

            if(checkForEscape(timeInput)){
                return null;
            }

            try {
                time = LocalTime.parse(timeInput, TIME_FORMATTER);
                break;
            } catch (Exception e) {
                System.out.println("Invalid time format. Please use HH:mm:ss.");
            }
        }
        return time;
    }

    /**
     * Prompts the user to enter a validated transaction amount and returns it as a {@code Double}.
     * <p>
     * The method repeatedly asks for user input until a valid positive amount is provided.
     * If the {@code isNullable} parameter is {@code true} and the user enters a blank input,
     * the method returns {@code null}.
     * </p>
     *
     * @param scanner     a {@link Scanner} instance for reading user input
     * @param isNullable  a boolean indicating whether a blank input is allowed (returns {@code null} if true)
     * @return a {@code Double} representing the validated positive amount input by the user, or {@code null} if input is blank
     */
    public static Double getValidatedAmount(Scanner scanner, boolean isNullable) {
        double amount;
        while (true) {
            System.out.print("Enter transaction amount: ");

            try {
                String input = scanner.nextLine();

                if(isNullable && input.isBlank())return null;

                amount = Double.parseDouble(input);
                if (amount > 0 || isNullable) {
                    break;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid amount.Please enter a valid positive number.\n");
            }
        }
        return amount;
    }

    /**
     * Prompts the user to enter a validated transaction amount and returns it as a {@code Double}.
     * <p>
     * This overload of the method does not allow for a blank input. It repeatedly asks for
     * user input until a valid positive amount is provided.
     * </p>
     *
     * @param scanner a {@link Scanner} instance for reading user input
     * @return a {@code Double} representing the validated positive amount input by the user
     */
    public static Double getValidatedAmount(Scanner scanner){
        return getValidatedAmount(scanner,false);
    }
}
