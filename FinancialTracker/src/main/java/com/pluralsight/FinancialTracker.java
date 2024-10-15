package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FinancialTracker {

    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("\nChoose an option:");
            System.out.println("\tD) Add Deposit");
            System.out.println("\tP) Make Payment (Debit)");
            System.out.println("\tL) Ledger");
            System.out.println("\tX) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addTransaction(scanner,false);
                case "P" -> addTransaction(scanner,true);
                case "L" -> ledgerMenu(scanner);
                case "X" -> running = false;
                default -> System.out.println("\nInvalid option");
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = br.readLine())!=null){
                String[] values = line.split("\\|");
                if(values.length == 5){
                    LocalDate date = LocalDate.parse(values[0].trim());
                    LocalTime time = LocalTime.parse(values[1].trim());
                    String description = values[2].trim();
                    String vendor = values[3].trim();
                    double amount = Double.parseDouble(values[4].trim());
                    transactions.add(new Transaction(date,time,description,vendor,amount));
                }
            }

        }catch(IOException e){
            System.out.println("File Doesn't Exist, Creating...");
            File newFile = new File(fileName);

            try{
                if(newFile.createNewFile()){
                    System.out.println("File '"+ fileName +"' Successfully Created");
                }else{
                    System.out.println("File '"+ fileName +"' Already Exists");
                }
            } catch (IOException ex) {
                System.out.println("Error Creating File "+fileName);
                throw new RuntimeException(ex);
            }
        }
    }

    private static String formattedTable(ArrayList<Transaction> targetInventory) {
        if(targetInventory.isEmpty())return "No transaction data found in file";

        StringBuilder output = new StringBuilder();

        // Define fixed widths for each column
        int dateWidth = 12;
        int timeWidth = 10;
        int descriptionWidth = 40;
        int vendorWidth = 40;
        int amountWidth = 12;

        // Getting total pad size, plus the six spaces in the formatting
        int totalPadSize = dateWidth+timeWidth+descriptionWidth+vendorWidth+amountWidth+6;

        // Format header
        output.append(String.format("\033[0;30;100m  %-" + dateWidth + "s %-" + timeWidth + "s %-" + descriptionWidth + "s %-" + vendorWidth + "s %" + amountWidth + "s\u001B[0m %n",
                "Date", "Time", "Description", "Vendor", "Amount  "));

        // Format the output for each transaction
        int lineCount = 0;
        String resetColor = "\u001B[0m";
        for (Transaction t : targetInventory) {
            // Alternate colors for rows
            String color = (lineCount % 2 == 0) ? "\u001B[100;48;5;236m" : "\u001B[100;48;5;237m";

            // Format the date and time
            String formattedDate = t.date().format(FinancialTracker.DATE_FORMATTER);
            String formattedTime = t.time().format(FinancialTracker.TIME_FORMATTER);

            output.append(color);
            output.append(String.format(" %-" + dateWidth + "s %-" + timeWidth + "s %-" + descriptionWidth + "s %-" + vendorWidth + "s ",
                    formattedDate,
                    formattedTime,
                    t.description(),
                    t.vendor()));

            // Color the amount based on its value
            String amountColor = (t.amount() < 0) ? "\u001B[91m" : "\u001B[92m"; // Red for negative, green for positive
            output.append(amountColor).append(String.format("%" + amountWidth + ".2f ", t.amount())).append(resetColor).append("\n");

            lineCount++;
        }
        // Add a blank footer to the output string
        output.append("\033[0;30;100m").append(" ".repeat(totalPadSize)).append(resetColor);

        return output.toString();
    }

    private static void addTransaction(Scanner scanner, boolean isPayment) {
        // Prompt for date input
        boolean validInput = false;

        System.out.print("Enter transaction date (yyyy-MM-dd): ");
        LocalDate date = null;

        // Validate the date input
        while (!validInput) {
            String dateInput = scanner.nextLine().trim();
            try {
                date = LocalDate.parse(dateInput, DATE_FORMATTER);
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
        validInput = false;

        // Prompt for time input
        System.out.print("Enter transaction time (HH:mm:ss): ");
        LocalTime time = null;

        // Validate the time input
        while (!validInput) {
            String timeInput = scanner.nextLine().trim();
            try {
                time = LocalTime.parse(timeInput, TIME_FORMATTER);
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid time format. Please use HH:mm:ss.");
            }
        }
        validInput = false;

        // Prompt for the description and vendor
        System.out.print("Enter transaction description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine().trim();

        // Prompt for the amount
        System.out.print("Enter transaction amount: ");
        double amount = 0;
        while (!validInput) {
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount > 0) {
                    validInput = true;
                } else {
                    System.out.print("Amount must be a positive number. Please enter again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid amount. Please enter a valid positive number: ");
            }
        }

        // Adjust the amount based on the transaction type
        if (isPayment) {
            amount = -amount; // Convert to negative for payments
        }

        // Create a new Transaction object with the validated inputs
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);

        // Add the new transaction to the transactions list
        transactions.add(newTransaction);
        System.out.println(isPayment ? "Payment added successfully.\n" : "Deposit added successfully.\n");

        // Add the transaction to transactions.csv
        writeToFile(newTransaction);
    }

    private static void writeToFile(Transaction transactionToAdd){
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME,true));
            bufferedWriter.write(transactionToAdd.toString());
            bufferedWriter.close();
        }catch (Exception e){
            System.out.println("Error Writing To File "+e);
        }
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("\nChoose an option:");
            System.out.println("\tA) All");
            System.out.println("\tD) Deposits");
            System.out.println("\tP) Payments");
            System.out.println("\tR) Reports");
            System.out.println("\tH) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> displayLedger();
                case "D" -> displayTransactionByType(true);
                case "P" -> displayTransactionByType(false);
                case "R" -> reportsMenu(scanner);
                case "H" -> running = false;
                default -> System.out.println("\nInvalid option");
            }
        }
    }

    private static void displayLedger() {
        System.out.println(formattedTable(transactions));
    }

    private static void displayTransactionByType(boolean isDeposit) {
        // Use streams to filter the transactions
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(transaction -> (isDeposit && transaction.amount() > 0) || (!isDeposit && transaction.amount() < 0))
                .collect(Collectors.toList());

        // Display the transactions if the list is not empty
        if (!filteredTransactions.isEmpty()) {
            System.out.println(formattedTable((ArrayList<Transaction>) filteredTransactions));
        }else{
            System.out.println("No Results Found Matching Criteria.");
        }
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("\nChoose an option:");
            System.out.println("\t1) Month To Date");
            System.out.println("\t2) Previous Month");
            System.out.println("\t3) Year To Date");
            System.out.println("\t4) Previous Year");
            System.out.println("\t5) Search by Vendor");
            System.out.println("\t6) Custom Search");
            System.out.println("\t0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    // Generate a report for all transactions within the current month,
                    // including the date, time, description, vendor, and amount for each transaction.
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1),LocalDate.now());
                    break;
                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, time, description, vendor, and amount for each transaction.
                    break;
                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, time, description, vendor, and amount for each transaction.
                    break;
                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, time, description, vendor, and amount for each transaction.
                    break;
                case "5":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, time, description, vendor, and amount for each transaction.
                    System.out.println("Enter The Vendor Name To Search:");
                    String vendorName = scanner.nextLine().trim();
                    filterTransactionsByVendor(vendorName);
                    break;
                case "6":
                    // Custom search
                    break;
                case "0":
                    running = false;
                default:
                    System.out.println("\nInvalid option");
                    break;
            }
        }
    }

    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        ArrayList<Transaction> dateList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.date();

            if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                dateList.add(transaction);
            }
        }

        if(!dateList.isEmpty()){
            System.out.println(formattedTable(dateList));
        }else{
            System.out.println("No Results Found Matching Criteria.");
        }
    }

    private static void filterTransactionsByVendor(String vendor) {
        ArrayList<Transaction> vendorList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.vendor().equalsIgnoreCase(vendor)) vendorList.add(transaction);
        }

        if(!vendorList.isEmpty()){
            System.out.println(formattedTable(vendorList));
        }else{
            System.out.println("No Results Found Matching Criteria.");
        }
    }
}