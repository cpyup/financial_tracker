package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static com.pluralsight.InputValidator.*;

public class TransactionManager {
    private static final String CSV_DELIMITER = "\\|";

    /**
     * Loads transactions from a specified CSV file and adds them to the provided list.
     * <p>
     * The method reads each line of the file, splits the line into values using the
     * specified CSV delimiter, and parses each valid line into a {@link Transaction}
     * object using the {@link #parseTransaction(String[])} method. Only lines with
     * exactly five values are processed.
     * </p>
     * <p>
     * After loading, the order of the transactions is reversed so that the newest entries
     * appear at the top of the list.
     * </p>
     * <p>
     * If the specified file does not exist, an error message is printed and a new file
     * is created.
     * </p>
     *
     * @param fileName the name of the file from which to load transactions
     * @param transactions the list to which loaded transactions will be added
     */
    public static void loadTransactionsFromFile(String fileName, ArrayList<Transaction> transactions) {
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

    /**
     * Parses an array of string values into a {@link Transaction} object.
     * <p>
     * The input array must contain the following elements in the specified order:
     * <ul>
     *     <li>0: Date in the format recognized by {@link LocalDate}</li>
     *     <li>1: Time in the format recognized by {@link LocalTime}</li>
     *     <li>2: Description of the transaction</li>
     *     <li>3: Vendor associated with the transaction</li>
     *     <li>4: Amount as a string that can be parsed into a double</li>
     * </ul>
     * </p>
     * <p>
     * The method trims whitespace from each input value and parses the date,
     * time, and amount accordingly.
     * </p>
     *
     * @param values an array of string values representing the transaction details
     * @return a {@link Transaction} object populated with the parsed values
     */
    private static Transaction parseTransaction(String[] values){
        LocalDate date = LocalDate.parse(values[0].trim());
        LocalTime time = LocalTime.parse(values[1].trim());
        String description = values[2].trim();
        String vendor = values[3].trim();
        double amount = Double.parseDouble(values[4].trim());
        return new Transaction(date,time,description,vendor,amount);
    }

    /**
     * Creates a new file with the specified name.
     * <p>
     * If the file does not already exist, it will be created, and a message will
     * be printed indicating success. If the file already exists, a message will
     * be printed indicating that the file already exists.
     * </p>
     * <p>
     * In case of an I/O error during the file creation process, an exception
     * will be caught and a runtime exception will be thrown with the original
     * exception as the cause.
     * </p>
     *
     * @param fileName the name of the file to be created
     * @throws RuntimeException if an I/O error occurs during file creation
     */
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

    /**
     * Adds a new transaction to the current array based on user input and initiates writing it to file.
     * <p>
     * The method prompts the user for the transaction details, including date, time,
     * description, vendor, and amount. The amount is adjusted based on whether the
     * transaction is a payment (negative) or a deposit (positive).
     * </p>
     * <p>
     * The new transaction is added to the beginning of the provided list of transactions
     * and is also written to the specified target file.
     * </p>
     *
     * @param scanner a {@link Scanner} instance for reading user input
     * @param isPayment a boolean indicating whether the transaction is a payment (true)
     *                  or a deposit (false)
     * @param transactions an {@link ArrayList} to which the new transaction will be added
     * @param targetFileName the name of the file to which the transaction will be saved
     */
    public static void addTransaction(Scanner scanner, boolean isPayment, ArrayList<Transaction> transactions, String targetFileName) {
        String verbiage = (isPayment) ? "Payment" : "Deposit";
        System.out.println("\n"+verbiage+" Adding Menu\nType 'Exit' To Return Home\n");
        // Get validated date and time inputs
        LocalDate date = getValidatedDate(scanner);
        if(date == null)return;
        LocalTime time = getValidatedTime(scanner);
        if(time == null)return;

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

    /**
     * Writes a {@link Transaction} object to a specified file.
     * <p>
     * The transaction is appended to the file in text format as defined by the
     * {@link Transaction#toString()} method. If the file does not exist, it will
     * be created.
     * </p>
     * <p>
     * If an error occurs during the writing process, an error message is printed
     * to the console.
     * </p>
     *
     * @param transactionToAdd the {@link Transaction} object to be written to the file
     * @param targetFileName the name of the file where the transaction will be saved
     */
    private static void writeToFile(Transaction transactionToAdd, String targetFileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetFileName, true))){
            bufferedWriter.write(transactionToAdd.toString());
        } catch (Exception e) {
            System.out.println("Error Writing To File " + targetFileName + " " + e);
        }
    }
}
