package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public record Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
    @Override
    public String toString(){
        return date.toString()+" "+time.toString()+" "+description+" "+vendor+" "+amount;
    }
}
