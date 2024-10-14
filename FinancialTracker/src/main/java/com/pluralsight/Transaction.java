package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public record Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
    @Override
    public String toString(){
        return String.format(date.toString(), FinancialTracker.DATE_FORMATTER)+"|"+
                String.format(time.toString(), FinancialTracker.TIME_FORMATTER)+"|"+
                description+"|"+vendor+"|"+amount;
    }
}
