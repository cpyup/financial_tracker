package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import static com.pluralsight.FinancialTracker.DATE_FORMATTER;
import static com.pluralsight.FinancialTracker.TIME_FORMATTER;

public class InputValidator {
    public static LocalDate getValidatedDate(Scanner scanner) {
        LocalDate date;
        while (true) {
            System.out.print("Enter transaction date (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine().trim();
            try {
                date = LocalDate.parse(dateInput, DATE_FORMATTER);
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
        return date;
    }

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

    public static double getValidatedAmount(Scanner scanner) {
        double amount;
        while (true) {
            System.out.print("Enter transaction amount: ");
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount > 0) {
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
}
