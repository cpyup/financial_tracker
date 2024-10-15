package com.pluralsight;

import java.util.ArrayList;

import static com.pluralsight.FinancialTracker.DATE_FORMATTER;
import static com.pluralsight.FinancialTracker.TIME_FORMATTER;

public class OutputFormatter {
    private static final int DATE_WIDTH = 12;
    private static final int TIME_WIDTH = 10;
    private static final int DESCRIPTION_WIDTH = 40;
    private static final int VENDOR_WIDTH = 40;
    private static final int AMOUNT_WIDTH = 12;
    private static final String RESET_COLOR = "\u001B[0m";
    private static final String HEADER_COLOR = "\033[0;30;100m";
    private static final String TABLE_COLOR_0 = "\u001B[100;48;5;236m";
    private static final String TABLE_COLOR_1 = "\u001B[100;48;5;237m";
    private static final String POSITIVE_COLOR = "\u001B[92m";
    private static final String NEGATIVE_COLOR = "\u001B[91m";

    private static String createHeader() {
        return String.format(HEADER_COLOR+"  %-" + DATE_WIDTH + "s %-" + TIME_WIDTH + "s %-" + DESCRIPTION_WIDTH + "s %-" + VENDOR_WIDTH + "s %" + AMOUNT_WIDTH + "s"+RESET_COLOR+"%n",
                "Date", "Time", "Description", "Vendor", "Amount  ");
    }

    private static String formatTransaction(Transaction t, boolean isEvenRow) {
        String color = isEvenRow ? TABLE_COLOR_0 : TABLE_COLOR_1;
        String amountColor = (t.amount() < 0) ? NEGATIVE_COLOR : POSITIVE_COLOR;

        return String.format("%s %-" + DATE_WIDTH + "s %-" + TIME_WIDTH + "s %-" + DESCRIPTION_WIDTH + "s %-" + VENDOR_WIDTH + "s %s%" + AMOUNT_WIDTH + ".2f %s%n",
                color,
                t.date().format(DATE_FORMATTER),
                t.time().format(TIME_FORMATTER),
                t.description(),
                t.vendor(),
                amountColor,
                t.amount(),
                RESET_COLOR);
    }

    public static String formattedTable(ArrayList<Transaction> targetInventory) {
        if (targetInventory.isEmpty()) return "No transaction data found in file";

        StringBuilder output = new StringBuilder();
        output.append(createHeader());

        for (int i = 0; i < targetInventory.size(); i++) {
            Transaction t = targetInventory.get(i);
            output.append(formatTransaction(t, i % 2 == 0));
        }

        // Get the total padding size, plus 6 for the additional spaces
        int totalPadSize = DATE_WIDTH + TIME_WIDTH + DESCRIPTION_WIDTH + VENDOR_WIDTH + AMOUNT_WIDTH + 6;
        output.append(HEADER_COLOR).append(" ".repeat(totalPadSize)).append(RESET_COLOR);

        return output.toString();
    }
}
