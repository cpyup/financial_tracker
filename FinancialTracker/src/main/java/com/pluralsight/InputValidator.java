package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import static com.pluralsight.FinancialTracker.DATE_FORMATTER;
import static com.pluralsight.FinancialTracker.TIME_FORMATTER;

public class InputValidator {

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

            try {
                date = LocalDate.parse(dateInput, DATE_FORMATTER);
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
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
