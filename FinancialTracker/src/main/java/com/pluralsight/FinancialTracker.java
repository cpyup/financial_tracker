package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

import static com.pluralsight.MenuManager.*;
import static com.pluralsight.TransactionManager.*;

public class FinancialTracker {

    private static final ArrayList<Transaction> TRANSACTIONS = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static boolean running;

    public static void main(String[] args) {
        applicationStartup();

        while (running) {
            displayMainMenu();
            handleMainMenuInput();
        }

        SCANNER.close();
    }

    private static void applicationStartup(){
        running = true;
        loadTransactionsFromFile(FILE_NAME, TRANSACTIONS);
        System.out.println("\nWelcome to TransactionApp!");
    }

    private static void handleMainMenuInput(){
        String input = SCANNER.nextLine().trim();
        switch (input.toUpperCase()) {
            case "D" -> displayTransactionAddMenu(SCANNER, false, TRANSACTIONS, FILE_NAME);
            case "P" -> displayTransactionAddMenu(SCANNER, true, TRANSACTIONS, FILE_NAME);
            case "L" -> displayLedgerMenu(SCANNER, TRANSACTIONS);
            case "X" -> running = false;
            default -> System.out.println("\nInvalid option");
        }
    }

}
