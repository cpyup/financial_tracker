package com.pluralsight;

import org.junit.jupiter.api.Test;

import java.io.File;


import static org.junit.jupiter.api.Assertions.*;

class FinancialTrackerTest {

    @Test
    void loadTransactions_nonExistingFile() {
        String testFileName = "non_existing_file.txt";

        // Call the method to test
        //FinancialTracker.loadTransactions(testFileName);

        // Check if the file has been created
        File file = new File(testFileName);
        assertTrue(file.exists());

        // Clean up the test file
        if(file.delete()){
            System.out.println("Deleted Test File");
        }
    }

    @Test
    void addPayment_validInput(){

    }

    @Test
    void addPayment_largeAmount(){

    }

    @Test
    void addPayment_smallAmount(){

    }

    @Test
    void addPayment_invalidDate(){

    }

    @Test
    void addPayment_invalidTime(){

    }

    @Test
    void addPayment_invalidAmount(){

    }

    @Test
    void addPayment_multipleInvalid(){

    }

    @Test
    void addPayment_validateTrimming(){

    }
}
