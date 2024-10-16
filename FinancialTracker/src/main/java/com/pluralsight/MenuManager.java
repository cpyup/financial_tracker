package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static com.pluralsight.DisplayManager.*;
import static com.pluralsight.InputValidator.*;

public class MenuManager {

    public static void ledgerMenu(Scanner scanner, ArrayList<Transaction> transactions) {
        while (true) {
            System.out.println("\nLedger");
            System.out.println("Choose an option:");
            System.out.println("\tA) All");
            System.out.println("\tD) Deposits");
            System.out.println("\tP) Payments");
            System.out.println("\tR) Reports");
            System.out.println("\tH) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> {displayLedger(transactions);
                    System.out.println("Press Enter To Continue");
                    scanner.nextLine();
                }
                case "D" -> filterTransactionsByType(true, transactions);
                case "P" -> filterTransactionsByType(false, transactions);
                case "R" -> reportsMenu(scanner,transactions);
                case "H" -> {
                    return;
                }
                default -> System.out.println("\nInvalid option");
            }
        }
    }

    private static void reportsMenu(Scanner scanner, ArrayList<Transaction> transactions) {
        while (true) {
            System.out.println("\nReports");
            System.out.println("Choose an option:");
            System.out.println("\t1) Month To Date");
            System.out.println("\t2) Previous Month");
            System.out.println("\t3) Year To Date");
            System.out.println("\t4) Previous Year");
            System.out.println("\t5) Search by Vendor");
            System.out.println("\t6) Custom Search");
            System.out.println("\t0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> filterTransactionsByDate(LocalDate.now().withDayOfMonth(1), LocalDate.now(), transactions);
                case "2" -> filterTransactionsByDate(LocalDate.now().minusMonths(1).withDayOfMonth(1),
                        LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth())
                        , transactions);
                case "3" ->
                        filterTransactionsByDate(LocalDate.now().withMonth(1).withDayOfMonth(1), LocalDate.now(), transactions);
                case "4" -> filterTransactionsByDate(LocalDate.now().minusYears(1).withMonth(1).withDayOfMonth(1),
                        LocalDate.now().minusYears(1).withMonth(12).withDayOfMonth(31), transactions);
                case "5" -> {
                    System.out.println("Enter The Vendor Name To Search: ");
                    String vendorName = scanner.nextLine().trim();
                    filterTransactionsByVendor(vendorName, transactions);
                }
                case "6" ->
                        customSearchMenu(scanner, transactions);
                case "0" -> {
                    return;
                }
                default -> System.out.println("\nInvalid option");
            }
            System.out.println("Press Enter To Continue");
            scanner.nextLine();
        }
    }

    private static void customSearchMenu(Scanner scanner, ArrayList<Transaction> transactions){
        LocalDate startDate;
        LocalDate endDate;
        String description = null;
        String vendor = null;
        Double maxAmount;
        Double minAmount;

        System.out.println("\nTo filter for a specific value, input for the corresponding prompt\nTo ignore a filter, press 'Enter'");

        System.out.println("\nStart Date Filter (oldest)");
        startDate = getValidatedDate(scanner,true);

        System.out.println("\nEnd Date Filter (newest)");
        endDate = getValidatedDate(scanner,true);

        System.out.println("\nDescription Filter\nDescription Text: ");
        String input = scanner.nextLine().trim();
        if(!input.isBlank())description = input;

        System.out.println("\nVendor Filter\nVendor Text: ");
        input = scanner.nextLine().trim();
        if(!input.isBlank())vendor = input;

        System.out.println("\nMaximum Amount Filter");
        maxAmount = getValidatedAmount(scanner,true);

        System.out.println("\nMinimum Amount Filter");
        minAmount = getValidatedAmount(scanner,true);

        filterTransactionsByCustom(startDate,endDate,description,vendor,minAmount,maxAmount,transactions);
    }
}
