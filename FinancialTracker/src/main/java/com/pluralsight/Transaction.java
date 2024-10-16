package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.pluralsight.InputValidator.*;

/**
 * Represents a financial transaction with a date, time, description, vendor, and amount.
 * <p>
 * This record encapsulates all relevant information for a transaction and provides
 * a custom string representation formatted for logging to data file.
 * </p>
 *
 * @param date        the date of the transaction
 * @param time        the time of the transaction
 * @param description a brief description of the transaction
 * @param vendor      the vendor associated with the transaction
 * @param amount      the amount of the transaction, where a positive value indicates a deposit
 *                    and a negative value indicates a payment
 */
public record Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
    @Override
    public String toString() {
        String formattedDate = date.format(DATE_FORMATTER);
        String formattedTime = time.format(TIME_FORMATTER);

        return String.format("%n%s|%s|%s|%s|%.2f", formattedDate, formattedTime, description, vendor, amount);
    }
}
