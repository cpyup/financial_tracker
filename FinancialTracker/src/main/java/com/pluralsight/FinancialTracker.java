package com.pluralsight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTracker {

    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

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
                case "D" -> addDeposit();
                case "P" -> addPayment(scanner);
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
        // This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // The transactions should be stored in the `transactions` ArrayList.
        // Each line of the file represents a single transaction in the following format:
        // <date>|<time>|<description>|<vendor>|<amount>
        // For example: 2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.
    }

    private static String formattedTable(ArrayList<Transaction> targetInventory) {
        StringBuilder output = new StringBuilder();

        // Define fixed widths for each column
        int dateWidth = 15;
        int timeWidth = 15;
        int descriptionWidth = 40;
        int vendorWidth = 40;
        int amountWidth = 9;

        // Format header
        output.append(String.format("\033[0;30;100m %-" + dateWidth + "s %-" + timeWidth + "s %-" + descriptionWidth + "s %-" + vendorWidth + "s %-" + amountWidth + "s\u001B[0m %n",
                "Date", "Time", "Description", "Vendor", "Amount"));

        // Format the output for each transaction
        int lineCount = 0;
        for (Transaction t : targetInventory) {
            // Alternate colors for rows
            String color = (lineCount % 2 == 0) ? "\u001B[30;48;5;236m" : "\u001B[100;48;5;235m";
            String resetColor = "\u001B[0m";

            output.append(color);
            output.append(String.format("%-" + dateWidth + "s %-" + timeWidth + "s %-" + descriptionWidth + "s %-" + vendorWidth + "s ",
                    t.date().toString(),
                    t.time().toString(),
                    t.description(),
                    t.vendor()));

            // Color the amount based on its value
            String amountColor = (t.amount() < 0) ? "\u001B[91m" : "\u001B[92m"; // Red for negative, green for positive
            output.append(amountColor).append(String.format("%-" + amountWidth + ".2f", t.amount())).append(" ").append(resetColor).append("\n");

            lineCount++;
        }

        return output.toString();
    }

    private static void addDeposit() {
        // This method should prompt the user to enter the date, time, description, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Transaction` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.
    }

    private static void addPayment(Scanner scanner) {
        // This method should prompt the user to enter the date, time, description, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount received should be a positive number, transformed to a negative number.
        // After validating the input, a new `Transaction` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.
        boolean validInput = false;

        System.out.print("Enter payment date (yyyy-MM-dd): ");

        // Validate the date input
        LocalDate date = null;
        while (!validInput){
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
        System.out.print("Enter payment time (HH:mm:ss): ");


        // Validate the time input
        LocalTime time = null;
        while (!validInput){
            String timeInput = scanner.nextLine().trim();
            try {
                time = LocalTime.parse(timeInput, TIME_FORMATTER);
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid time format. Please use HH:mm:ss.");
                break;
            }
        }
        validInput = false;

        // Prompt for the description and vendor
        System.out.print("Enter payment description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine().trim();

        // Prompt for the payment amount
        System.out.print("Enter payment amount: ");
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

        // Convert the amount to a negative number, since it's a payment
        amount = -amount;

        // Create a new Transaction object with the validated inputs
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);

        // Add the new transaction to the transactions list
        transactions.add(newTransaction);
        System.out.println("Payment added successfully.\n");
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

    private static void displayTransactionByType(Boolean isDeposit){
        ArrayList<Transaction> transactionTypeList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if(isDeposit){
                if (transaction.amount() > 0) transactionTypeList.add(transaction);
            }else{
                if (transaction.amount() < 0) transactionTypeList.add(transaction);
            }
        }

        if(!transactionTypeList.isEmpty()){
            System.out.println(formattedTable(transactionTypeList));
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
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.
        ArrayList<Transaction> dateList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.date();

            if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                dateList.add(transaction);
            }
        }

        if(!dateList.isEmpty()){
            System.out.println(formattedTable(dateList));
        }
    }

    private static void filterTransactionsByVendor(String vendor) {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
        ArrayList<Transaction> vendorList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.vendor().equalsIgnoreCase(vendor)) vendorList.add(transaction);
        }

        if(!vendorList.isEmpty()){
            System.out.println(formattedTable(vendorList));
        }
    }
}