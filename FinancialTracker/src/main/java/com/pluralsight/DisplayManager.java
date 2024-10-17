package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.pluralsight.InputValidator.*;

public class DisplayManager {
    private static final int DATE_WIDTH = 10;
    private static final int TIME_WIDTH = 9;
    private static final int DESCRIPTION_WIDTH = 40;
    private static final int VENDOR_WIDTH = 40;
    private static final int AMOUNT_WIDTH = 12;
    private static final int SPACING_OFFSET = 13;
    private static final String RESET_COLOR = "\u001B[0m";
    private static final String HEADER_COLOR = "\033[0;30;100m";
    private static final String TABLE_COLOR_0 = "\u001B[100;48;5;236m";
    private static final String TABLE_COLOR_1 = "\u001B[100;48;5;237m";
    private static final String POSITIVE_COLOR = "\u001B[92m";
    private static final String NEGATIVE_COLOR = "\u001B[91m";
    private static final String SEPARATOR_COLOR = "\u001B[48;5;235m";
    private static final String BORDER_STRING = String.format(HEADER_COLOR+" "+RESET_COLOR);
    private static final String COLUMN_SEPARATOR = String.format(SEPARATOR_COLOR + " " + RESET_COLOR);
    private static final String TABLE_TITLE = "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
    private static final String TRUNCATION_STRING = "...";

    /**
     * Creates a formatted header string for displaying transaction data.
     * <p>
     * The header includes column titles for date, time, description, vendor, and amount.
     * The string is formatted to ensure proper alignment based on predefined column widths.
     * Preset border string added to front and back of string, for table border padding.
     * </p>
     *
     * @return a formatted header string ready for display
     */
    private static String createHeader() {
        return String.format(BORDER_STRING + HEADER_COLOR+" %-" + DATE_WIDTH + "s  %-" + TIME_WIDTH + "s  %-"
                        + DESCRIPTION_WIDTH + "s  %-" + VENDOR_WIDTH + "s  %" + AMOUNT_WIDTH + "s    " + BORDER_STRING + "%n",
                "   Date", "   Time", "               Description", "                  Vendor", "Amount");  // Padding to align header text with center of columns
    }

    /**
     * Formats a {@link Transaction} object into a styled string for displaying as a table.
     * <p>
     * The method applies alternating row colors for visual distinction and changes the color
     * of the amount based on whether it is positive or negative. It formats the transaction's
     * date, time, description, vendor, and amount according to predefined widths for proper alignment.
     * </p>
     *
     * @param t           the {@link Transaction} object to be formatted
     * @param isEvenRow   a boolean indicating whether the transaction is in an even row
     * @return a single formatted table entry string representing the transaction, ready for display
     */
    private static String formatTransaction(Transaction t, boolean isEvenRow) {
        String color = isEvenRow ? TABLE_COLOR_0 : TABLE_COLOR_1;
        String amountColor = (t.amount() < 0) ? NEGATIVE_COLOR : POSITIVE_COLOR;

        return String.format("%s %-" + DATE_WIDTH + "s %s%s %-" + TIME_WIDTH
                        + "s%s%s %-" + DESCRIPTION_WIDTH + "s %s%s %-" + VENDOR_WIDTH
                        + "s %s%s %s%" + AMOUNT_WIDTH + ".2f " + BORDER_STRING + "%n",
                color, t.date().format(DATE_FORMATTER), COLUMN_SEPARATOR,
                color, t.time().format(TIME_FORMATTER), COLUMN_SEPARATOR,
                color, validateAndTruncate(t.description()), COLUMN_SEPARATOR,
                color, validateAndTruncate(t.vendor()), COLUMN_SEPARATOR,
                color, amountColor, t.amount());
    }

    /**
     * Validates and truncates a given input string to ensure it fits within a specified width.
     * <p>
     * If the input string exceeds the defined {@code DESCRIPTION_WIDTH}, it is truncated and
     * appended with an ellipsis ("...") to indicate that it has been shortened. If the input is
     * within the acceptable length, it is returned unchanged.
     * </p>
     *
     * @param input the string to be validated and potentially truncated
     * @return a string that fits within the {@code DESCRIPTION_WIDTH}, with an ellipsis appended if truncated
     */
    private static String validateAndTruncate(String input){
        String output;

        if(input.length() > DESCRIPTION_WIDTH){
            output = input.substring(0,DESCRIPTION_WIDTH - TRUNCATION_STRING.length());
            output += TRUNCATION_STRING;
        }else{
            output = input;
        }

        return output;
    }

    /**
     * Generates a formatted string representation of a table displaying a list of transactions.
     * <p>
     * If the provided list of transactions is empty, a message indicating that no data is found
     * is returned. Otherwise, the method constructs the table as a string by appending a header and formatting
     * each transaction, adding borders and spacing for visual clarity.
     * </p>
     *
     * @param targetInventory an {@link ArrayList} of {@link Transaction} objects to be displayed
     * @return a formatted string representing the transaction array as a table, or a message if no data is found
     */
    private static String formattedTable(ArrayList<Transaction> targetInventory) {
        if (targetInventory.isEmpty()) return "No transaction data found in file";

        StringBuilder output = new StringBuilder();
        output.append(createHeader());

        for (int i = 0; i < targetInventory.size(); i++) {
            Transaction t = targetInventory.get(i);
            output.append(BORDER_STRING);
            output.append(formatTransaction(t, i % 2 == 0));
        }

        // Construct the footer from the total row size
        int footerSize = DATE_WIDTH + TIME_WIDTH + DESCRIPTION_WIDTH + VENDOR_WIDTH + AMOUNT_WIDTH + SPACING_OFFSET;
        output.append(BORDER_STRING).append(HEADER_COLOR).append(" ".repeat(footerSize)).append(RESET_COLOR).append(BORDER_STRING);
        output.append("\nPress Enter To Continue");

        return output.toString();
    }

    /**
     * Displays the full ledger of transactions in a formatted table.
     * <p>
     * The method calls {@link #formattedTable(ArrayList)} to generate a string representation
     * of the transactions and prints it to the console. This provides a clear view of all
     * transactions in the ledger.
     * </p>
     *
     * @param transactions an {@link ArrayList} of {@link Transaction} objects to be displayed
     */
    public static void displayLedger(ArrayList<Transaction> transactions) {
        System.out.println(TABLE_TITLE+"FULL LEDGER TABLE");
        System.out.println(formattedTable(transactions));
    }

    /**
     * Returns a string representing the filtered array as a fully formatted table, ready to display.
     * <p>
     * The method applies the provided {@link Predicate} to the list of transactions,
     * filtering out those that do not match. It then sends the filtered results to
     * {@link #formattedTable(ArrayList)} for displaying. If no transactions match the criteria, a message indicating this
     * is displayed.
     * </p>
     *
     * @param filter a {@link Predicate} used to filter the transactions
     * @param transactions an {@link ArrayList} of {@link Transaction} objects to be filtered and displayed
     */
    private static void displayFilteredTransactions(Predicate<Transaction> filter,ArrayList<Transaction> transactions,String tableTitle) {
        List<Transaction> filteredTransactions = transactions.stream()  // Checking for entries matching indicated filters
                .filter(filter)
                .toList();

        System.out.println(filteredTransactions.isEmpty() ? "\nNo Results Found Matching Criteria.\nPress Enter To Continue"
                : tableTitle+"\n"+formattedTable(new ArrayList<>(filteredTransactions)));
    }

    /**
     * Filters and displays transactions based on their type (deposit or payment).
     * <p>
     * The method determines whether to filter for deposits (positive amounts) or payments
     * (negative amounts) based on the {@code isDeposit} flag. It then calls
     * {@link #displayFilteredTransactions(Predicate, ArrayList, String)} to display the matching
     * transactions.
     * </p>
     *
     * @param isDeposit a boolean indicating whether to filter for deposits (true) or payments (false)
     * @param transactions an {@link ArrayList} of {@link Transaction} objects to be filtered and displayed
     */
    public static void filterTransactionsByType(boolean isDeposit, ArrayList<Transaction> transactions) {
        String tableTitle = TABLE_TITLE+((isDeposit) ? "DEPOSITS" : "PAYMENTS")+" TABLE";
        displayFilteredTransactions(transaction ->
                (isDeposit && transaction.amount() > 0) || (!isDeposit && transaction.amount() < 0),transactions,tableTitle);
    }

    /**
     * Filters and displays transactions based on a specified date range.
     * <p>
     * The method uses the provided start and end dates to filter transactions, returning
     * only those that occur within the specified range (inclusive). It then calls
     * {@link #displayFilteredTransactions(Predicate, ArrayList, String)} to display the matching
     * transactions.
     * </p>
     *
     * @param startDate the start date of the range for filtering transactions
     * @param endDate   the end date of the range for filtering transactions
     * @param transactions an {@link ArrayList} of {@link Transaction} objects to be filtered and displayed
     */
    public static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate, ArrayList<Transaction> transactions) {
        String tableTitle = TABLE_TITLE+startDate+" TO "+endDate;
        displayFilteredTransactions(transaction -> {
            LocalDate transactionDate = transaction.date();
            return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
        },transactions,tableTitle);
    }

    /**
     * Filters and displays transactions based on the vendor name.
     * <p>
     * The method uses the provided vendor name to filter transactions, returning only
     * those that contain the specified string in vendor name (case-insensitive). It then calls
     * {@link #displayFilteredTransactions(Predicate, ArrayList, String)} to display the matching
     * transactions.
     * </p>
     *
     * @param vendor       the vendor name to filter transactions by
     * @param transactions an {@link ArrayList} of {@link Transaction} objects to be filtered and displayed
     */
    public static void filterTransactionsByVendor(String vendor, ArrayList<Transaction> transactions) {
        String tableTitle = TABLE_TITLE+"VENDOR: "+vendor.toUpperCase();
        displayFilteredTransactions(transaction -> transaction.vendor().toLowerCase().contains(vendor.toLowerCase()),
                transactions,tableTitle);
    }

    /**
     * Filters and displays transactions based on custom criteria, including date range,
     * description, vendor, and amount limits.
     * <p>
     * The method allows for filtering transactions based on specified criteria. Each criterion
     * can be ignored by passing {@code null} for date, description, vendor, or amount limits.
     * It then calls {@link #displayFilteredTransactions(Predicate, ArrayList, String)} to display the
     * matching transactions.
     * </p>
     *
     * @param startDate     the start date for filtering (inclusive), or {@code null} to ignore
     * @param endDate       the end date for filtering (inclusive), or {@code null} to ignore
     * @param description    the description substring to filter by, or {@code null} to ignore
     * @param vendor        the vendor substring to filter by, or {@code null} to ignore
     * @param minAmount     the minimum amount for filtering, or {@code null} to ignore
     * @param maxAmount     the maximum amount for filtering, or {@code null} to ignore
     * @param transactions  an {@link ArrayList} of {@link Transaction} objects to be filtered and displayed
     */
    public static void filterTransactionsByCustom(LocalDate startDate, LocalDate endDate, String description, String vendor,
                                                  Double minAmount, Double maxAmount, ArrayList<Transaction> transactions) {
        String tableTitle = TABLE_TITLE+"CUSTOM SEARCH";

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
        }, transactions,tableTitle);
    }
}
