package test;

import static test.BoCApp.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;


import org.junit.jupiter.api.*;


public class Test_BoCApp_part2 {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    static void setup(){//

        System.setOut(new PrintStream(outContent));

    }
    @BeforeEach
    void setupList(){
        UserCategories = new ArrayList<BoCCategory>();
        UserTransactions = new ArrayList<BoCTransaction>();

        // SETUP EXAMPLE DATA //
        try {
            UserCategories.add(new BoCCategory("Unknown"));
            BoCCategory BillsCategory = new BoCCategory("Bills");
            BillsCategory.setCategoryBudget(120.00f);
            UserCategories.add(BillsCategory);
            BoCCategory Groceries = new BoCCategory("Groceries");
            Groceries.setCategoryBudget(75.00f);
            UserCategories.add(Groceries);
            BoCCategory SocialSpending = new BoCCategory("Social");
            SocialSpending.setCategoryBudget(100.00f);
            UserCategories.add(SocialSpending);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        UserTransactions.add(new BoCTransaction("Rent", new BigDecimal("850.00"), 0));
        UserTransactions.add(new BoCTransaction("Phone Bill", new BigDecimal("37.99"), 1));
        UserTransactions.add(new BoCTransaction("Electricity Bill", new BigDecimal("75.00"), 1));
        UserTransactions.add(new BoCTransaction("Sainsbury's Checkout", new BigDecimal("23.76"), 2));
        UserTransactions.add(new BoCTransaction("Tesco's Checkout", new BigDecimal("7.24"), 2));
        UserTransactions.add(new BoCTransaction("RockCity Drinks", new BigDecimal("8.50"), 3));
        UserTransactions.add(new BoCTransaction("The Mooch", new BigDecimal("13.99"), 3));

        for (int x = 0; x < UserTransactions.size(); x++) {
            BoCTransaction temp = UserTransactions.get(x);
            int utCat = temp.transactionCategory();
            BoCCategory temp2 = UserCategories.get(utCat);
            temp2.addExpense(temp.transactionValue());
            UserCategories.set(utCat, temp2);
        }

    }
    

    /**
     * @author Yizhou Chen
     * Functionality: for testing ChangeTransactionCategory in some special scenarios (testing if existing problems tackled)
     * Problem 1) Change Transaction Category will not change the original Category's Budget
     * Problem 2) Change Transaction Category to its current Category will accumulate the Category's Spend
     * Problem 3) Change Transaction Category can't be changed to "Unknown"
     * Problem 4) Check if NumberFormatException is raised if the accepted String can't be parsed into Int
     * Problem 4) Check if IndexOutfBoundException is raised if the accepted number is out of the bound of list
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    @Test
    void test_App_ChangeTransactionCategory() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        BoCApp app = new BoCApp();

        //test1

        //Give a test about normal functionality
        //get Access to ChangeTransactionCategory method
        Method m = BoCApp.class.getDeclaredMethod("ChangeTransactionCategory", Scanner.class);
        m.setAccessible(true);

        //pretending to input 5 for TransactionID, input 2 for new CategoryID
        String input = System.lineSeparator()+"5" + System.lineSeparator()+"2"+System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        m.invoke(app, scanner);

        outContent.reset();
        //test if the Category of the Transaction has been changed
        ListTransactions();
        assertEquals("1) Rent (Unknown) - ¥850.00" + System.lineSeparator() +
                "2) Phone Bill (Bills) - ¥37.99" + System.lineSeparator() +
                "3) Electricity Bill (Bills) - ¥75.00" + System.lineSeparator() +
                "4) Sainsbury's Checkout (Groceries) - ¥23.76" + System.lineSeparator() +
                "5) Tesco's Checkout (Bills) - ¥7.24" + System.lineSeparator() +
                "6) RockCity Drinks (Social) - ¥8.50" + System.lineSeparator() +
                "7) The Mooch (Social) - ¥13.99" +System.lineSeparator(), outContent.toString());

        outContent.reset();
        //test if the Budget of the Category has been updated
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥120.23 (¥0.23 Overspent)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥23.76 (¥51.24 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+System.lineSeparator(), outContent.toString());


        /* test history

           1. Fail (Date: 05/04/2020)

           Reason:
           1. The date was printed at the end of each transaction which is not expected.
           2. Expect message displayed when success but nothing was shown
           3. Expect Groceries spend to decrease to 23.76 but got 39.5, similar for Bills

           Actual Output:
          ListTransactions() ------>
          1) Rent - ¥850.00 Mon May 04 14:29:20 CST 2020
          2) Phone Bill - ¥37.99 Mon May 04 14:29:20 CST 2020
          3) Electricity Bill - ¥75.00 Mon May 04 14:29:20 CST 2020
          4) Sainsbury's Checkout - ¥23.76 Mon May 04 14:29:20 CST 2020
          5) Tesco's Checkout - ¥7.24 Mon May 04 14:29:20 CST 2020
          6) RockCity Drinks - ¥8.50 Mon May 04 14:29:20 CST 2020
          7) The Mooch - ¥13.99 Mon May 04 14:29:20 CST 2020

          CategoryOverview() ------>
          1) Unknown(¥0.00) - ¥850.00 (¥-850.00 Remaining)
          2) Bills(¥120.00) - ¥112.99 (¥7.01 Remaining)
          3) Groceries(¥75.00) - ¥39.5 (¥35.5 Remaining)
          4) Social(¥100.00) - ¥22.49 (¥77.51 Remaining)


           2. Pass (Date: 05/04/2020)

           Reason:
           1. Date is not displayed anymore after modifying toString() method in BoCTransaction
           2. Success message prompted when the operation success.
           3. All changes to the expense are reflected after adding some lines of code to process the previous category of the chosen transaction.

         */


        
        //test2

        //To test the problem of a transaction's category can't be changed to "Unknown"

        //pretending to input 4 for TransactionID, input 1 for new CategoryID
        input = System.lineSeparator()+"4" + System.lineSeparator()+"1"+System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        m.invoke(app, scanner);


        //test if the Category of the Transaction has been changed
        outContent.reset();
        BoCApp.CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥873.76 (¥873.76 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥120.23 (¥0.23 Overspent)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥0.00 (¥75.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+System.lineSeparator(), outContent.toString());
        outContent.reset();
        BoCApp.ListTransactions();
        assertEquals("1) Rent (Unknown) - ¥850.00"+System.lineSeparator() +
                "2) Phone Bill (Bills) - ¥37.99"+System.lineSeparator() +
                "3) Electricity Bill (Bills) - ¥75.00"+System.lineSeparator() +
                "4) Sainsbury's Checkout (Unknown) - ¥23.76"+System.lineSeparator() +
                "5) Tesco's Checkout (Bills) - ¥7.24"+System.lineSeparator() +
                "6) RockCity Drinks (Social) - ¥8.50"+System.lineSeparator() +
                "7) The Mooch (Social) - ¥13.99"+System.lineSeparator(), outContent.toString());
        
        /* test history

           Pass (Date 05/04/2020)

           Reason:
           1. The actual output matches the expected output
           2. Desired success message displayed
           3. Changes reflected in CategoryOverview
           4. Changes reflect in ListTransactions()

         */



        //test3

        //To test the problem of a transaction allowed to change to its current category and accumulate the CategorySpend

        //pretending to input 6 for TransactionID, input 4 for new CategoryID
        input = System.lineSeparator()+"6" + System.lineSeparator()+"4"+System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        m.invoke(app, scanner);

        //test if the CategorySpend doesn't change as expected
        outContent.reset();
        BoCApp.CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥873.76 (¥873.76 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥120.23 (¥0.23 Overspent)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥0.00 (¥75.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+System.lineSeparator(), outContent.toString());
        
        /* test history
            
            Pass (Date 05/04/2020)

            Reason:
            1. The correct message displayed
            2. Correct success message displayed
            3. Nothing was changed in Category list which is expected
            4. Category of RockCity Drinks was not changed, meets expected outcome

         */


        
        //test4

        //pretending to input "String" for TransactionID
        input = System.lineSeparator()+"String"+System.lineSeparator()+"5"+System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //test if the NumberFormat Exception has been thrown
        scanner = new Scanner(System.in);
        Scanner finalScanner = scanner;
        assertEquals(NumberFormatException.class, assertThrows(InvocationTargetException.class, ()-> m.invoke(app, finalScanner)).getCause().getClass());

        /* test history

        1. Fail (Date: 05/05/2020)

        Reason:
        Expect NumberFormatException but InvocationTargetException was thrown

        2. Pass (Date: 05/05/2020)

        Reason:
        NumberFormatException is thrown after adding getCause().getClass() after assertThrows()

         */



        //test5

        //pretending to input "String" for TransactionID
        input = System.lineSeparator()+"4"+ System.lineSeparator() + "100" +System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //test if the IndexOutOfBounds Exception has been thrown
        Scanner finalScanner1 = new Scanner(System.in);
        assertEquals(IndexOutOfBoundsException.class, assertThrows(InvocationTargetException.class, ()-> m.invoke(app, finalScanner1)).getCause().getClass());


        /* test history

        Pass (Date: 05/05/2020)

        Reason:
        Expect IndexOutOfBoundsException and IndexOutOfBoundsException was thrown

         */

    }



    /**
     * @author Yizhou Chen
     * Functionality: testing the functionality of AddTransaction method and if Exception would be thrown correctly in special scenarios
     * Scenario 1) 2 normal inputs for transaction title and value respectively, and press "Enter" for default CategoryID
     * Scenario 2) 3 normal inputs for transaction title, value and CategoryID respectively
     * Scenario 3) 2 normal inputs for transaction title and value respectively, and a OutOfBounds index
     * Scenario 4) Exchange inputs for transaction title and value(String type for value), and a normal CategoryID
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    @Test
    void test_App_AddTransaction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {


        //test1

        //testing the functionality when given two normal inputs and "Enter"

        //setup
        BoCApp app = new BoCApp();
        outContent.reset();

        //Get access to the method AddTransaction
        Method m = BoCApp.class.getDeclaredMethod("AddTransaction", Scanner.class);
        m.setAccessible(true);

        //pretending to input
        //give "Interest Expense" for title
        //give "600" for value
        String input = System.lineSeparator()+ "Interest Expense" + System.lineSeparator() +"600" + System.lineSeparator() + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        m.invoke(app, scanner);

        //test the printed information
        assertEquals("What is the title of the transaction?"+System.lineSeparator()+"" +
                "What is the value of the transaction?"+System.lineSeparator()+
                "What is the category ID of the transaction?"+System.lineSeparator()+
                "(Press \"Enter\" to set to \"Unknown\" by default)"+System.lineSeparator()+
                "[ Interest Expense(¥600) added to Unknown ]"+System.lineSeparator(), outContent.toString());

        //test Category information
        outContent.reset();
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥1450.00 (¥1450.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+System.lineSeparator(), outContent.toString());

        //test Transaction information
        outContent.reset();
        ListTransactions();
        assertEquals("1) Rent (Unknown) - ¥850.00"+System.lineSeparator() +
                "2) Phone Bill (Bills) - ¥37.99"+System.lineSeparator() +
                "3) Electricity Bill (Bills) - ¥75.00"+System.lineSeparator() +
                "4) Sainsbury's Checkout (Groceries) - ¥23.76"+System.lineSeparator() +
                "5) Tesco's Checkout (Groceries) - ¥7.24"+System.lineSeparator() +
                "6) RockCity Drinks (Social) - ¥8.50"+System.lineSeparator() +
                "7) The Mooch (Social) - ¥13.99"+System.lineSeparator() +
                "8) Interest Expense (Unknown) - ¥600"+System.lineSeparator(), outContent.toString());

        /* test history

           1. Fail (Date: 05/05/2020)

            Reason:
            Expect message asking category but nothing was not prompted

           2. Fail (Date: 05/05/2020)

            Reason:
            Success message displayed but didn't sepcify transaction name, value and category name

            Actual Output:
            [Transaction added]


            3. Pass (Date: 05/05/2020)

            Reason:
            1. Disired message displayed after modifying the print statement to also take in transaction name, value,
            and category name from UserCatogories, couple of lines of codes to add the transaction to the category list were also written
            2. The actual output matches the expected output

         */



        //test2
        //test the functionality when given three normal inputs

        //setup
        outContent.reset();

        //give "Interest Expense" for title
        //give "600" for value
        //give "2" for CatID
        input = System.lineSeparator()+ "Interest Expense" + System.lineSeparator() +"600" + System.lineSeparator() + "2" + System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        m.invoke(app, scanner);

        //check printed information
        assertEquals("What is the title of the transaction?"+System.lineSeparator()+"" +
                "What is the value of the transaction?"+System.lineSeparator()+
                "What is the category ID of the transaction?"+System.lineSeparator()+
                "(Press \"Enter\" to set to \"Unknown\" by default)"+System.lineSeparator()+
                "[ Interest Expense(¥600) added to Bills ]"+System.lineSeparator(), outContent.toString());

        //check Category information
        outContent.reset();
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥1450.00 (¥1450.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥712.99 (¥592.99 Overspent)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+System.lineSeparator(), outContent.toString());
        outContent.reset();
        //check Transaction information
        ListTransactions();
        assertEquals("1) Rent (Unknown) - ¥850.00"+System.lineSeparator() +
                "2) Phone Bill (Bills) - ¥37.99"+System.lineSeparator() +
                "3) Electricity Bill (Bills) - ¥75.00"+System.lineSeparator() +
                "4) Sainsbury's Checkout (Groceries) - ¥23.76"+System.lineSeparator() +
                "5) Tesco's Checkout (Groceries) - ¥7.24"+System.lineSeparator() +
                "6) RockCity Drinks (Social) - ¥8.50"+System.lineSeparator() +
                "7) The Mooch (Social) - ¥13.99"+System.lineSeparator() +
                "8) Interest Expense (Unknown) - ¥600"+System.lineSeparator()+
                "9) Interest Expense (Bills) - ¥600" + System.lineSeparator(), outContent.toString());
        
        /* test history

           Pass(Date: 05/05/2020)

           Reason:
            1. Message displayed
            2. Correct success message displayed
            3. Changes reflected in CategoryOverview()
            4. Changes reflected in ListTransactions()

         */



        //test3

        //test if a "value must be bigger than 0" message is displayed with a minus value and two Enter for default title and catID

        //set up
        outContent.reset();

        //Enter for title
        //give "-200" for value
        //Enter for catID
        input = System.lineSeparator()+ System.lineSeparator()  +"-200" + System.lineSeparator() + System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        m.invoke(app, scanner);

        //check if the message is displayed
        assertEquals("What is the title of the transaction?"+System.lineSeparator()+"" +
                "What is the value of the transaction?"+System.lineSeparator()+
                "What is the category ID of the transaction?"+System.lineSeparator()+
                "(Press \"Enter\" to set to \"Unknown\" by default)"+System.lineSeparator()+
                "Transaction value must be bigger than 0"+System.lineSeparator(), outContent.toString());


        //check if the transaction is accidentally added
        outContent.reset();
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥1450.00 (¥1450.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥712.99 (¥592.99 Overspent)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+System.lineSeparator(), outContent.toString());
        outContent.reset();
        ListTransactions();
        assertEquals("1) Rent (Unknown) - ¥850.00"+System.lineSeparator() +
                "2) Phone Bill (Bills) - ¥37.99"+System.lineSeparator() +
                "3) Electricity Bill (Bills) - ¥75.00"+System.lineSeparator() +
                "4) Sainsbury's Checkout (Groceries) - ¥23.76"+System.lineSeparator() +
                "5) Tesco's Checkout (Groceries) - ¥7.24"+System.lineSeparator() +
                "6) RockCity Drinks (Social) - ¥8.50"+System.lineSeparator() +
                "7) The Mooch (Social) - ¥13.99"+System.lineSeparator() +
                "8) Interest Expense (Unknown) - ¥600"+System.lineSeparator()+
                "9) Interest Expense (Bills) - ¥600" + System.lineSeparator(), outContent.toString());
        
        /* test history
            Pass (Date: 05/05/2020)

            Reason:
            1. Message displayed
            2. Wrong message displayed
            3. Nothing changed in CategoryOverview()
            4. Nothing changed in ListTransactions()

         */



        //test4
        //test if Exception thrown when given two normal inputs and a OutOfBounds index

        //set up
        outContent.reset();

        //give "Interest Expense" for title
        //give "600" for value
        //give "5" for CatID which doesn't exist
        input = System.lineSeparator()+ "Interest Expense" + System.lineSeparator() +"600" + System.lineSeparator() + "5" + System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        Scanner finalScanner = scanner;

        //check if IndexOutOfBoundsException is thrown
        assertEquals(IndexOutOfBoundsException.class, assertThrows(InvocationTargetException.class, ()-> m.invoke(app, finalScanner)).getCause().getClass());

        //check if the transaction is accidentally added
        outContent.reset();
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥1450.00 (¥1450.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥712.99 (¥592.99 Overspent)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+System.lineSeparator(), outContent.toString());
        outContent.reset();
        ListTransactions();
        assertEquals("1) Rent (Unknown) - ¥850.00"+System.lineSeparator() +
                "2) Phone Bill (Bills) - ¥37.99"+System.lineSeparator() +
                "3) Electricity Bill (Bills) - ¥75.00"+System.lineSeparator() +
                "4) Sainsbury's Checkout (Groceries) - ¥23.76"+System.lineSeparator() +
                "5) Tesco's Checkout (Groceries) - ¥7.24"+System.lineSeparator() +
                "6) RockCity Drinks (Social) - ¥8.50"+System.lineSeparator() +
                "7) The Mooch (Social) - ¥13.99"+System.lineSeparator() +
                "8) Interest Expense (Unknown) - ¥600"+System.lineSeparator()+
                "9) Interest Expense (Bills) - ¥600" + System.lineSeparator(), outContent.toString());


        /* test history
            Pass (Date: 05/05/2020)

            Reason:
            IndexOutOfBoundsException is thrown

         */


        
        //test5
        //test if Exception thrown when given a normal input and a String type value and a "Enter" for default index

        //set up
        outContent.reset();

        //give "10000" for title
        //give "Interest Expense" for value
        //give Enter for CatID which doesn't exist
        input = System.lineSeparator()+ "10000" + System.lineSeparator() +"Interest Expense" + System.lineSeparator() + System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        Scanner finalScanner1 = scanner;

        //check if NumberFormatException is thrown
        assertEquals(NumberFormatException.class, assertThrows(InvocationTargetException.class, ()-> m.invoke(app, finalScanner1)).getCause().getClass());

        //check if the transaction is accidentally added
        outContent.reset();
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥1450.00 (¥1450.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥712.99 (¥592.99 Overspent)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+System.lineSeparator(), outContent.toString());
        outContent.reset();
        ListTransactions();
        assertEquals("1) Rent (Unknown) - ¥850.00"+System.lineSeparator() +
                "2) Phone Bill (Bills) - ¥37.99"+System.lineSeparator() +
                "3) Electricity Bill (Bills) - ¥75.00"+System.lineSeparator() +
                "4) Sainsbury's Checkout (Groceries) - ¥23.76"+System.lineSeparator() +
                "5) Tesco's Checkout (Groceries) - ¥7.24"+System.lineSeparator() +
                "6) RockCity Drinks (Social) - ¥8.50"+System.lineSeparator() +
                "7) The Mooch (Social) - ¥13.99"+System.lineSeparator() +
                "8) Interest Expense (Unknown) - ¥600"+System.lineSeparator()+
                "9) Interest Expense (Bills) - ¥600" + System.lineSeparator(), outContent.toString());

        /* test history
            Pass (Date: 05/05/2020)

            Reason:
            NumberFormatException is thrown

         */

    }


    /**
     * @author Yizhou Chen
     * Functionality: test the functionality of AddCategory method and if the method tackle some problems
     * Problem 1) The budget is not bigger than 0
     * Problem 2) The budget is given by a String type variable
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    @Test
    void test_App_AddCategory() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {


        //test1

        //test the functionality of AddCategory with two normal inputs

        //setup
        BoCApp app = new BoCApp();
        outContent.reset();

        //Get access to the method AddTransaction
        Method m = BoCApp.class.getDeclaredMethod("AddCategory", Scanner.class);
        m.setAccessible(true);

        //pretending to input
        //give "Entertainment" for title
        //give "200" for budget
        String input = System.lineSeparator()+ "Entertainment" + System.lineSeparator() +"200" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        m.invoke(app, scanner);

        //test printed information
        assertEquals("What is the title of the category?"+System.lineSeparator() +
                "What is the budget for this category?"+System.lineSeparator() +
                "[ Category: Entertainment (¥200.0) was successfully added ]"+System.lineSeparator() +
                "CategoryOverview ----->"+System.lineSeparator() +
                "1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" + System.lineSeparator() +
                "5) Entertainment (Budget:¥200.0) - ¥0.00 (¥200.00 Remaining)" + System.lineSeparator(), outContent.toString());

        /* test history

            1. Fail(Date: 05/05/2020)

            Reason:
            Message incorrect, expect statement of new category and budget and none

            Actual Output:
            [Category added]


           2. Pass (Date: 05/05/2020)

            Reason:
            1. Message corrected after modifying print statement
            2. Actual output matched expected output

         */


        
        //test2

        //test if it's not allowed to add new Category with a normal title and a 0 for budget

        //setup
        outContent.reset();

        //give "Entertainment" for title
        //give "0" for budget
        input = System.lineSeparator()+ "Entertainment" + System.lineSeparator() +"0" + System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        m.invoke(app, scanner);

        //test printed information
        assertEquals("What is the title of the category?"+System.lineSeparator() +
                "What is the budget for this category?"+System.lineSeparator()+
                "Budget must be bigger than 0!" + System.lineSeparator(), outContent.toString());

        //test if Category is accidentally added
        outContent.reset();
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" + System.lineSeparator() +
                "5) Entertainment (Budget:¥200.0) - ¥0.00 (¥200.00 Remaining)" + System.lineSeparator(), outContent.toString());

        /* test history

           1. Fail (Date: 05/05/2020)

           Reason:
           Expect system to prompt a message but got an exception

           Actual Output:
           java.lang.ClassCastException: Not possible to coerce [0, 0] from class class java.math.BigDecimal into a BigDecimal

           2. Pass (Date: 05/05/2020)

           Reason:
           1. Message displayed after adding if-else blocks
           2. Category list remains unchanged

         */


        
        //test3

        //test if it's not allowed to add new Category with a normal title and a minus budget

        //setup
        outContent.reset();

        //give "Entertainment" for title
        //give "-100" for budget
        input = System.lineSeparator()+ "Entertainment" + System.lineSeparator() +"-100" + System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        m.invoke(app, scanner);

        //test printed information
        assertEquals("What is the title of the category?"+System.lineSeparator() +
                "What is the budget for this category?"+System.lineSeparator()+
                "Budget must be bigger than 0!" + System.lineSeparator(), outContent.toString());

        //test if Category is accidentally added
        outContent.reset();
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" + System.lineSeparator() +
                "5) Entertainment (Budget:¥200.0) - ¥0.00 (¥200.00 Remaining)" + System.lineSeparator(), outContent.toString());
        
        
        /* test history
        
            Pass (Date: 05/05/2020)
            
            Reason:
            1. Message displayed when user entered -100 budget
            2. Category list remains unchanged
            
         */

        
        
        //test4

        //test the functionality if a default title will be given with Enter for title and a normal value

        //setup
        outContent.reset();

        //Enter for title
        //give "200" for value
        input = System.lineSeparator() + System.lineSeparator() +"200" + System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);
        m.invoke(app, scanner);

        //test printed information
        assertEquals("What is the title of the category?"+System.lineSeparator() +
                "What is the budget for this category?"+System.lineSeparator() +
                "[ Category: New Category0 (¥200.0) was successfully added ]"+System.lineSeparator() +
                "CategoryOverview ----->"+System.lineSeparator() +
                "1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" + System.lineSeparator() +
                "5) Entertainment (Budget:¥200.0) - ¥0.00 (¥200.00 Remaining)" + System.lineSeparator() +
                "6) New Category0 (Budget:¥200.0) - ¥0.00 (¥200.00 Remaining)"+ System.lineSeparator(), outContent.toString());

        /* test history
        
            Pass(Date: 05/05/2020)
            
            Reason:
            1. Correct message displayed when success
            2. Actual output matched expected output
           
         */
        
        
        
        //test5

        //setup
        outContent.reset();

        //give "Food" for title
        //give "Food" for value
        input = System.lineSeparator()+ "Food" + System.lineSeparator() +"Food" + System.lineSeparator();
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner finalscanner = new Scanner(System.in);

        //test if Exception correctly thrown
        assertEquals(NumberFormatException.class, assertThrows(InvocationTargetException.class, ()->m.invoke(app, finalscanner)).getCause().getClass());

        //test if Category is accidentally added
        outContent.reset();
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" + System.lineSeparator() +
                "5) Entertainment (Budget:¥200.0) - ¥0.00 (¥200.00 Remaining)" + System.lineSeparator() +
                "6) New Category0 (Budget:¥200.0) - ¥0.00 (¥200.00 Remaining)"+ System.lineSeparator(), outContent.toString());
        
        /* test history
        
            Pass(Date: 05/05/2020)
            
            Reason:
            1. Expect NumberFormatException, and NumberFormatException was thrown
            2. Expect nothing changed and nothing was changed
            
         */
        
    }

    @AfterAll
    static void cleanUpStreams(){
        System.setOut(null);
    }
        


}
