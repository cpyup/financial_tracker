package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.pluralsight.FinancialTracker.DATE_FORMATTER;
import static com.pluralsight.FinancialTracker.TIME_FORMATTER;

public class DisplayManager {
    private static final int DATE_WIDTH = 10;
    private static final int TIME_WIDTH = 9;
    private static final int DESCRIPTION_WIDTH = 40;
    private static final int VENDOR_WIDTH = 40;
    private static final int AMOUNT_WIDTH = 12;
    private static final String RESET_COLOR = "\u001B[0m";
    private static final String HEADER_COLOR = "\033[0;30;100m";
    private static final String TABLE_COLOR_0 = "\u001B[100;48;5;236m";
    private static final String TABLE_COLOR_1 = "\u001B[100;48;5;237m";
    private static final String POSITIVE_COLOR = "\u001B[92m";
    private static final String NEGATIVE_COLOR = "\u001B[91m";
    private static final String SEPARATOR_COLOR = "\u001B[48;5;235m";
    private static final String BORDER_STRING = String.format(HEADER_COLOR+" "+RESET_COLOR);
    private static final String COLUMN_SEPARATOR = String.format(SEPARATOR_COLOR + " " + RESET_COLOR);

    private static String createHeader() {
        return String.format(BORDER_STRING + HEADER_COLOR+" %-" + DATE_WIDTH + "s  %-" + TIME_WIDTH + "s  %-"
                        + DESCRIPTION_WIDTH + "s  %-" + VENDOR_WIDTH + "s  %" + AMOUNT_WIDTH + "s    " + BORDER_STRING + "%n",
                "   Date", "   Time", "               Description", "                  Vendor", "Amount");  // Padding to align header text with center of column
    }

    private static String formatTransaction(Transaction t, boolean isEvenRow) {
        String color = isEvenRow ? TABLE_COLOR_0 : TABLE_COLOR_1;
        String amountColor = (t.amount() < 0) ? NEGATIVE_COLOR : POSITIVE_COLOR;

        return String.format("%s %-" + DATE_WIDTH + "s %s%s %-" + TIME_WIDTH
                        + "s%s%s %-" + DESCRIPTION_WIDTH + "s %s%s %-" + VENDOR_WIDTH
                        + "s %s%s %s%" + AMOUNT_WIDTH + ".2f " + BORDER_STRING + "%n",
                color,
                t.date().format(DATE_FORMATTER),
                COLUMN_SEPARATOR,color,
                t.time().format(TIME_FORMATTER),
                COLUMN_SEPARATOR,color,
                inputLengthValidator(t.description()),
                COLUMN_SEPARATOR,color,
                inputLengthValidator(t.vendor()),
                COLUMN_SEPARATOR,color,
                amountColor,
                t.amount());
    }

    private static String inputLengthValidator(String input){
        String truncationString = "...";
        String output;

        if(input.length() > DESCRIPTION_WIDTH){
            output = input.substring(0,DESCRIPTION_WIDTH - truncationString.length());
            output += truncationString;
        }else{
            output = input;
        }

        return output;
    }

    private static String formattedTable(ArrayList<Transaction> targetInventory) {
        if (targetInventory.isEmpty()) return "No transaction data found in file";

        StringBuilder output = new StringBuilder();
        output.append("\n");
        output.append(createHeader());

        for (int i = 0; i < targetInventory.size(); i++) {
            Transaction t = targetInventory.get(i);
            output.append(BORDER_STRING);
            output.append(formatTransaction(t, i % 2 == 0));
        }

        // Get the total padding size, plus 14 for the additional spaces
        int totalPadSize = DATE_WIDTH + TIME_WIDTH + DESCRIPTION_WIDTH + VENDOR_WIDTH + AMOUNT_WIDTH + 13;
        output.append(BORDER_STRING).append(HEADER_COLOR).append(" ".repeat(totalPadSize)).append(RESET_COLOR).append(BORDER_STRING);

        return output.toString();
    }

    public static void displayLedger(ArrayList<Transaction> transactions) {
        System.out.println(formattedTable(transactions));
    }

    public static void displayFilteredTransactions(Predicate<Transaction> filter,ArrayList<Transaction> transactions) {
        // Using the desired filter, iterate the list, outputting matching transactions
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(filter)
                .toList();

        System.out.println(filteredTransactions.isEmpty() ? "No Results Found Matching Criteria."
                : formattedTable(new ArrayList<>(filteredTransactions)));
    }

    public static void filterTransactionsByType(boolean isDeposit, ArrayList<Transaction> transactions) {
        displayFilteredTransactions(transaction ->
                (isDeposit && transaction.amount() > 0) || (!isDeposit && transaction.amount() < 0),transactions);
    }

    public static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate, ArrayList<Transaction> transactions) {
        displayFilteredTransactions(transaction -> {
            LocalDate transactionDate = transaction.date();
            return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
        },transactions);
    }

    public static void filterTransactionsByVendor(String vendor, ArrayList<Transaction> transactions) {
        displayFilteredTransactions(transaction -> transaction.vendor().toLowerCase().contains(vendor.toLowerCase()),transactions);
    }

    public static void filterTransactionsByCustom(LocalDate startDate, LocalDate endDate, String description, String vendor,
                                                  Double minAmount, Double maxAmount, ArrayList<Transaction> transactions) {

        displayFilteredTransactions(transaction -> {
            LocalDate transactionDate = transaction.date();

            // Check date criteria
            boolean dateMatches = (startDate == null || !transactionDate.isBefore(startDate)) &&
                    (endDate == null || !transactionDate.isAfter(endDate));

            // Check description criteria
            boolean descriptionMatches = description == null || transaction.description().toLowerCase().contains(description.toLowerCase());

            // Check vendor criteria
            boolean vendorMatches = vendor == null || transaction.vendor().toLowerCase().contains(vendor.toLowerCase());

            // Check amount criteria
            boolean amountMatches = (minAmount == null || transaction.amount() >= minAmount) &&
                    (maxAmount == null || transaction.amount() <= maxAmount);

            // Combine all conditions
            return dateMatches && descriptionMatches && vendorMatches && amountMatches;
        }, transactions);
    }
}
