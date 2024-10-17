package com.pluralsight;

import org.junit.jupiter.api.Test;


import java.io.File;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {

    @Test
    void loadTransactions_properFile() {
        String testFileName = "transactions.csv";
        ArrayList<Transaction> testArray = new ArrayList<>();
        TransactionManager.loadTransactionsFromFile(testFileName,testArray);

        assertFalse(testArray.isEmpty());
    }

    @Test
    void loadTransactions_nonExistingFile() {
        String testFileName = "non_existing_file.txt";
        ArrayList<Transaction> testArray = new ArrayList<>();

        TransactionManager.loadTransactionsFromFile(testFileName,testArray);

        // Check if the file has been created
        File file = new File(testFileName);
        assertTrue(file.exists());

        // Clean up the test file
        if(file.delete()){
            System.out.println("Deleted Test File");
        }
    }
}