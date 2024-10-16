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

    private static boolean isValidDate(String dateString) {
        try {
            // Split the string into components
            String[] parts = dateString.split("-");
            if (parts.length != 3) {
                return false; // Invalid format
            }

            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            // Validate month
            if (month < 1 || month > 12) {
                return false;
            }

            int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

            // Check for leap year
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                daysInMonth[1] = 29; // February has 29 days in a leap year
            }

            // Validate day
            return day > 0 && day <= daysInMonth[month - 1];
        } catch (NumberFormatException e) {
            return false; // Return false if parsing fails
        } catch (Exception e) {
            return false; // Handle any other unexpected exceptions
        }
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

            if(isNullable && dateInput.isBlank())return null;

            try{
                if(isValidDate(dateInput)){
                    try {
                        date = LocalDate.parse(dateInput, DATE_FORMATTER);
                        break;
                    } catch (RuntimeException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                }
                throw new RuntimeException();
            }catch(Exception e){
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
                    System.out.print("Amount must be a positive number. Please enter again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid amount. Please enter a valid positive number: ");
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
