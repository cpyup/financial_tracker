package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import static com.pluralsight.OutputFormatter.formattedTable;

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
                case "D" -> addTransaction(scanner, false);
                case "P" -> addTransaction(scanner, true);
                case "L" -> ledgerMenu(scanner);
                case "X" -> running = false;
                default -> System.out.println("\nInvalid option");
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\|");
                if (values.length == 5) {
                    LocalDate date = LocalDate.parse(values[0].trim());
                    LocalTime time = LocalTime.parse(values[1].trim());
                    String description = values[2].trim();
                    String vendor = values[3].trim();
                    double amount = Double.parseDouble(values[4].trim());
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }

        } catch (IOException e) {
            System.out.println("File Doesn't Exist, Creating...");
            File newFile = new File(fileName);

            try {
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
    }

    private static void addTransaction(Scanner scanner, boolean isPayment) {
        // Get validated date and time inputs
        LocalDate date = InputValidator.getValidatedDate(scanner);
        LocalTime time = InputValidator.getValidatedTime(scanner);

        // Prompt for the description and vendor
        System.out.print("Enter transaction description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine().trim();

        // Get validated amount input
        double amount = InputValidator.getValidatedAmount(scanner);

        // Adjust the amount based on the transaction type
        if (isPayment) {
            amount = -amount;
        }

        // Create a new Transaction object with the validated inputs
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);

        // Add the new transaction to the transactions list
        transactions.add(newTransaction);
        System.out.println(isPayment ? "Payment added successfully.\n" : "Deposit added successfully.\n");

        // Add the transaction to transactions.csv
        writeToFile(newTransaction);
    }

    private static void writeToFile(Transaction transactionToAdd) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bufferedWriter.write(transactionToAdd.toString());
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error Writing To File " + e);
        }
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
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

    private static void displayFilteredTransactions(Predicate<Transaction> filter) {
        // Using the desired filter, iterate the list, outputting matching transactions
        List<Transaction> filteredTransactions = FinancialTracker.transactions.stream()
                .filter(filter)
                .toList();

        if (!filteredTransactions.isEmpty()) {
            System.out.println(formattedTable(new ArrayList<>(filteredTransactions)));
        } else {
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
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1), LocalDate.now());
                    break;
                case "2":
                    filterTransactionsByDate(LocalDate.now().minusMonths(1).withDayOfMonth(1),
                            LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
                    break;
                case "3":
                    filterTransactionsByDate(LocalDate.now().withMonth(1).withDayOfMonth(1), LocalDate.now());
                    break;
                case "4":
                    filterTransactionsByDate(LocalDate.now().minusYears(1).withMonth(1).withDayOfMonth(1),
                            LocalDate.now().minusYears(1).withMonth(12).withDayOfMonth(31));
                    break;
                case "5":
                    System.out.println("Enter The Vendor Name To Search:");
                    String vendorName = scanner.nextLine().trim();
                    filterTransactionsByVendor(vendorName);
                    break;
                case "6":
                    // Custom search
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid option");
                    break;
            }
        }
    }

    private static void displayTransactionByType(boolean isDeposit) {
        displayFilteredTransactions(transaction ->
                (isDeposit && transaction.amount() > 0) || (!isDeposit && transaction.amount() < 0));
    }

    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        displayFilteredTransactions(transaction -> {
            LocalDate transactionDate = transaction.date();
            return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
        });
    }

    private static void filterTransactionsByVendor(String vendor) {
        displayFilteredTransactions(transaction -> transaction.vendor().equalsIgnoreCase(vendor));
    }
}
