package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

import static com.pluralsight.MenuManager.*;
import static com.pluralsight.TransactionManager.*;

public class FinancialTracker {

    private static final ArrayList<Transaction> TRANSACTIONS = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";
    public static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        loadTransactionsFromFile(FILE_NAME, TRANSACTIONS);
        boolean running = true;

        System.out.println("\nWelcome to TransactionApp!");

        while (running) {
            System.out.println("\nMain Menu\nChoose an option:");
            System.out.println("\tD) Add Deposit");
            System.out.println("\tP) Make Payment (Debit)");
            System.out.println("\tL) Ledger");
            System.out.println("\tX) Exit");

            String input = SCANNER.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addTransaction(SCANNER, false, TRANSACTIONS, FILE_NAME);
                case "P" -> addTransaction(SCANNER, true, TRANSACTIONS, FILE_NAME);
                case "L" -> displayLedgerMenu(SCANNER, TRANSACTIONS);
                case "X" -> running = false;
                default -> System.out.println("\nInvalid option");
            }
        }

        SCANNER.close();
    }
}
