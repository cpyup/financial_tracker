# Financial Tracker Application

## Project Summary

This Java CLI application tracks user transactions and displays them in an easy to understand table. Display is able to be filtered by any desired values, indicated by the user.

This application offers an elegant and streamlined solution for tracking your current expenses and income, allowing you to maintain a better overall grasp on your finances.

## User Stories

Collection of user stories that guided the design of my application:

> - As a user, I require my transaction data to be handled as safely and efficiently as possible.
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

## Demo

[TODO]

## Future Work

### Further improvements to the table formatting methods

> Originally, I wrote the table formatting to be very flexible, adjusting the size of the table based on the string size of the data. This got in the way during the course of development, as I was constantly testing different ideas for the final table format. During that process, I eventually just set it as fixed size padding, with truncation on overly large strings. Over time, I plan to re-orient myself to some of my original ideas for handling output, such as:

- Dynamically sized columns in table
- Dynamic row counts, allowing future parameters if need be
- Better implementation of headers/footers, namely, improving length assignments (remove magic numbers in strings) and removing manual alignment spacing in strings

## Resources

Some resources that I found helpful over the course of this project:

- [Streams Ref](https://stackify.com/streams-guide-java-8/)
- [Predicate Ref](https://www.geeksforgeeks.org/java-8-predicate-with-examples/)

## Project Highlights

[TODO]

## Thank You

Thank you for taking a look at my project!