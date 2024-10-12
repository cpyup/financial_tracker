package com.pluralsight;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinancialTrackerTest {

    @Test
    void loadTransactions_nonExistingFile() {
        String testFileName = "non_existing_file.txt";

        // Call the method to test
        FinancialTracker.loadTransactions(testFileName);

        // Check if the file has been created
        File file = new File(testFileName);
        assertTrue(file.exists());

        // Clean up the test file
        if(file.delete()){
            System.out.println("Deleted Test File");
        }
    }
}
