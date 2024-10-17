# Financial Tracker Application

## Project Summary

This Java CLI application tracks user transactions and displays them in an easy to understand table. Display is able to be filtered by any desired values, indicated by the user.

This application offers an elegant and streamlined solution for tracking your current expenses and income, allowing you to maintain a better overall grasp on your finances.

### Class Diagram

![Class Diagram](https://www.plantuml.com/plantuml/png/jLTHRwGs47xFh_3ZhYwfsjUgEfq3veAqm0gSzA6gLZwOx5arDh9DSgkg_xruMJP66mgvNFS5rlw-RsRicR7vP2pJjgt5rLN3oh_O7b2fwfyQqMec3Dz_jUZlAzJzIi6CGH6NJ9QS2QexDEZJfFlTe4xAyX8zTXY1iDRiiE76_jP1fM6bvKf-H3J7QO436cTfCKKjhEPoZw9uGtOfJiWahcHIWaP5WDEKv6VO-UK3gXcNgrxlZpyHqtkpFix-S-K5vJcOCCdsBgmPtuBYOHUIJPp4z5N30-b9yGe9nIhFXecqgYCkuEHOfomWPJLSJosQzSZhIyyyB6gODk0Dl0J_n4GBOxhdOwc1MKZX-JN7rXE1iQhoJGtxOVhdDVgYb00c4JTRTgX1saKHNW-BR9dUWuq69oOyVzRS0bL7lprDNv8gN5LJifVva81ipuam4Nh5JIEwIA3gK8vm4VrtRgQldaEZj3N_btpG6glg0fWklxxJncZzmbv-jfQuj2Z4bEn-ZqDwzrye6YSBK24fWZpUkjuo3tuWQPZbypYSPFSfdSSLMnp4wUTT5aK5eJFj8oSTPXTacooVGTuH799y4PJYsmtfaRkVtu3zPGQxpOgOnWzaaHCf-OmNWmkonJccsRBuRhFSBKL1yswTPx2TtdsIxWO3ovQEndH3vf3vVHfWbs9JZWp8lgFU0Qj0hzQJi4UbQsQzyXetCR-9aYUGkNgUrdfYWbVC0fOLrQqicJstSYwRrivvOQ6Yx8k0rKJTzvqpbfqVLkd3UablNBs_JqLSMD0mEaDNssw0kmZ7iez7x1lF6HSpvLPC7dIzgc__za0F3QnUDYU4HXc-x8HxYvdGRU96bKouDmVNrhrTevT1a9Lx_d3R3vsmqgiXkux_VhYH_XHQ7EB9cGfCgNdZDFenpy5GjTs6effBNAjMsfSHzgqV-TwJBNPLzD1NcPgz9VQ7MvJb2QOpI1ed5yWFOwGBd_8QebEfQb-UadmnppVcymRFl8vp3DK9zEGX4roFwL_NYzXlv95lt0Qghfci9jhN4AAN87iODWPEDgTlesahXAlvTmbEaIclnsGtC4yUSjHdzicw99Btw9tpt3qll_1kRZwERiI_ejQ0cSPwzNH9kPpjIUF2yWdEz3pIGL-5Z--Ydh0tCwFThSmda9NxHluN)

## User Stories

Collection of user stories that guided the design of my application:

>- As a user, I require my transaction data to be handled as safely and efficiently as possible.
>
>- As a user, I want to load transactions from a specified file so that I can view my financial history.
>
>- As a user, I want to add a payment so that I can track my outgoing expenses.
>
>- As a user, I want to add a deposit so that I can track my incoming funds.
>
>- As a user, I want to view all of my transactions in a clear format so that I can easily track my financial activities.
>
>- As a user, I want to view only my deposit transactions so that I can assess my incoming funds.
>
>- As a user, I want to view only my payment transactions so that I can assess my outgoing expenses.
>
>- As a user, I want to filter my transactions by date range so that I can review transactions during a specific period.
>
>- As a user, I want to filter my transactions by vendor so that I can analyze my spending with specific vendors.
>
>- As a user, I want to perform a custom search for transactions by various criteria (start date, end date, description, vendor, amount) so that I can easily find specific transactions that meet my needs.
>
>- As a potential user, I require detailed information about this application before deciding whether to use it or not.
>
>- As a potential contributor to the project, I want the codebase to be clean and efficient so that I can easily understand, navigate, and contribute to it without unnecessary obstacles.
>
>- As a user who experiences colorblindness, I require options to adjust the display colors, so that I will be able to view my data as clearly as possible.
>
>- As an indecisive user, I would like a way for me to back out of adding a new transaction at any point during the process.

## Setup Instructions

Instructions on how to set up and run the project using IntelliJ IDEA.

### Prerequisites

- IntelliJ IDEA: Ensure you have IntelliJ IDEA installed, which you can download from [here](https://www.jetbrains.com/idea/download/).
- Java SDK: Make sure Java SDK is installed and configured in IntelliJ.

### Running the Application in IntelliJ

Follow these steps to get your application running within IntelliJ IDEA:

1. Open IntelliJ IDEA.
2. Select "Open" and navigate to the directory where you cloned or downloaded the project.
3. After the project opens, wait for IntelliJ to index the files and set up the project.
4. Find the main class with the `public static void main(String[] args)` method.
5. Right-click on the file and select 'Run 'FinancialTracker.main()'' to start the application.

## Technologies Used

- IntelliJ IDEA Community Edition 2022.3.2.0
- Java Version 17

## Additional Tools

- [GIMP](https://www.gimp.org/) (for visualizing final design of table output)
- [Notepad++](https://notepad-plus-plus.org/) (script edits, README.md creation)
- [CherryTree](https://www.giuspen.net/cherrytree/) (hierarchical note taking application, really helped me with keeping track of everything)
- [Google](https://www.google.com/) (always and forever)
- [PlantUML](https://www.plantuml.com/) (for class diagram)
- [Makeareadme](https://www.makeareadme.com/) (visualizing readme)

## Display Examples

![Full ledger display](https://github.com/cpyup/financial_tracker/blob/main/screenshots/full_ledger_display.png?raw=true)

![Deposits and payments](https://github.com/cpyup/financial_tracker/blob/main/screenshots/deposits_and_payments.png?raw=true)

![Month to date and previous month](https://github.com/cpyup/financial_tracker/blob/main/screenshots/month_to_date_and_previous.png?raw=true)

![Previous year and vendor search](https://github.com/cpyup/financial_tracker/blob/main/screenshots/previous_year_and_vendor_search.png?raw=true)

![Custom search filters](https://github.com/cpyup/financial_tracker/blob/main/screenshots/custom_search.png?raw=true)

![Example input errors](https://github.com/cpyup/financial_tracker/blob/main/screenshots/error_examples.png?raw=true)

## Project Highlights

> While the formatted table output seems like the most obvious highlight, it is actually my most disliked part of this project. I ended up spending too much time focusing on the visual aspects of it, and was unable to dedicate enough to having it perform as originally intended. What I think is the most interesting part of the project is how I ended up handling table filters. Before starting custom searches, I did a large refactor on my filtration methods, as the code was a big mess at that point, with large methods constructed of repetitive code. During my research, I learned about predicates and was able to use them to massively reduce the redundancy in a few very clean, encapsulated methods. Example below:

```java
// This method constructs the desired filter criteria based on boolean value then passes it to the method below
public static void filterTransactionsByType(boolean isDeposit, ArrayList<Transaction> transactions) {
        displayFilteredTransactions(transaction ->
                (isDeposit && transaction.amount() > 0) || (!isDeposit && transaction.amount() < 0),transactions);
    }
```

```java
// This method receives generic filters and applies them to the array, calling to display the results
private static void displayFilteredTransactions(Predicate<Transaction> filter,ArrayList<Transaction> transactions) {
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(filter)
                .toList();

        System.out.println(filteredTransactions.isEmpty() ? "No Results Found Matching Criteria."
                : formattedTable(new ArrayList<>(filteredTransactions)));
    }
```

## Future Work

### Further improvements to the table formatting methods

> Originally, I wrote the table formatting to be very flexible, adjusting the size of the table based on the string size of the data. This got in the way during the course of development, as I was constantly testing different ideas for the final table format. During that process, I eventually just set it as fixed size padding, with truncation on overly large strings. Over time, I plan to re-orient myself to some of my original ideas for handling output, such as:

- Dynamically sized columns in table
- Dynamic row counts, allowing future parameters if need be
- Better implementation of headers/footers, namely, improving length assignments (remove magic numbers in strings) and removing manual alignment spacing in header text

> Outside of that, there is still a lot of redundancy remaining between methods. Error handling needs to be massively extended, with more specific error messages added. Classes are still poorly encapsulated, likely need to be majorly refactored in a more sensible manner. Additionally, multiple desired features were never implemented:

- Accessibility settings to disable green/red text (can be difficult for some to read)
- Additional settings related to table format/display
- External config file used for storing application settings between sessions

## Resources

Some resources that I found helpful over the course of this project:

- [Streams Ref](https://stackify.com/streams-guide-java-8/)
- [Predicate Ref](https://www.geeksforgeeks.org/java-8-predicate-with-examples/)
- [ANSI Escape](https://en.wikipedia.org/wiki/ANSI_escape_code)

## Thank You

Thank you for taking a look at my project!