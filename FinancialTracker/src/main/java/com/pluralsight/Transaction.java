package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public record Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
    @Override
    public String toString() {
        String formattedDate = date.format(FinancialTracker.DATE_FORMATTER);
        String formattedTime = time.format(FinancialTracker.TIME_FORMATTER);

        return String.format("%n%s|%s|%s|%s|%s", formattedDate, formattedTime, description, vendor, amount);
    }
}
