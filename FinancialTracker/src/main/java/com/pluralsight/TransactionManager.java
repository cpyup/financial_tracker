package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static com.pluralsight.InputValidator.*;

public class TransactionManager {
    public static final String CSV_DELIMITER = "\\|";

    public static void loadTransactions(String fileName, ArrayList<Transaction> transactions) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(CSV_DELIMITER);
                if (values.length == 5) {
                    transactions.add(parseTransaction(values));
                }
            }
            // Reverse load order so the newest entries are displayed at the top
            Collections.reverse(transactions);

        } catch (IOException e) {
            System.out.println("File Doesn't Exist, Creating...");
            createNewFile(fileName);
        }
    }

    private static Transaction parseTransaction(String[] values){
        LocalDate date = LocalDate.parse(values[0].trim());
        LocalTime time = LocalTime.parse(values[1].trim());
        String description = values[2].trim();
        String vendor = values[3].trim();
        double amount = Double.parseDouble(values[4].trim());
        return new Transaction(date,time,description,vendor,amount);
    }

    private static void createNewFile(String fileName){
        try {
            File newFile = new File(fileName);
            if (newFile.createNewFile()) {
                System.out.println("File '" + fileName + "' Successfully Created");
            } else {
                System.out.println("File '" + fileName + "' Already Exists");
            }
        } catch (IOException ex) {
            System.out.println("Error Creating File " + fileName);
            throw new RuntimeException(ex);
        }
    }

    public static void addTransaction(Scanner scanner, boolean isPayment, ArrayList<Transaction> transactions, String targetFileName) {
        // Get validated date and time inputs
        LocalDate date = getValidatedDate(scanner);
        LocalTime time = getValidatedTime(scanner);

        // Prompt for the description and vendor
        System.out.print("Enter transaction description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine().trim();

        // Get validated amount input
        Double amount = getValidatedAmount(scanner);

        // Adjust the amount based on the transaction type
        if (isPayment) {
            amount = -amount;
        }

        // Create a new Transaction object with the validated inputs
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);

        // Add the new transaction to the transactions list
        transactions.add(0,newTransaction);
        System.out.println(isPayment ? "Payment added successfully.\n" : "Deposit added successfully.\n");

        // Add the transaction to transactions.csv
        writeToFile(newTransaction,targetFileName);
    }

    private static void writeToFile(Transaction transactionToAdd, String targetFileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetFileName, true))){
            bufferedWriter.write(transactionToAdd.toString());
        } catch (Exception e) {
            System.out.println("Error Writing To File " + targetFileName + " " + e);
        }
    }
}
