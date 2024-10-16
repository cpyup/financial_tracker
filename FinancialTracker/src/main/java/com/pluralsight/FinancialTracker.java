package com.pluralsight;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static com.pluralsight.MenuManager.*;
import static com.pluralsight.TransactionManager.*;

public class FinancialTracker {

    private static final ArrayList<Transaction> TRANSACTIONS = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME, TRANSACTIONS);
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("\tD) Add Deposit");
            System.out.println("\tP) Make Payment (Debit)");
            System.out.println("\tL) Ledger");
            System.out.println("\tX) Exit");

            String input = SCANNER.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addTransaction(SCANNER, false, TRANSACTIONS, FILE_NAME);
                case "P" -> addTransaction(SCANNER, true, TRANSACTIONS, FILE_NAME);
                case "L" -> ledgerMenu(SCANNER, TRANSACTIONS);
                case "X" -> running = false;
                default -> System.out.println("\nInvalid option");
            }
        }

        SCANNER.close();
    }
}
