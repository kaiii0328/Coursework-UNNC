package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static test.BoCApp.*;
 
public class Test_BoCApp_part1 {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    static void setUp() throws Exception {
        UserCategories = new ArrayList<BoCCategory>();
        UserTransactions = new ArrayList<BoCTransaction>();

        // SETUP EXAMPLE DATA //
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

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void cleanUpStreams() {
        System.setOut(null);
    }

    /**
     * @author Yuyang LIN
     * the below function tests whether the method ListTransactions()
     * prints out the correct output
     */
    @Test
    void Test_App_ListTransactions() {
        ListTransactions();
        assertEquals("1) Rent (Unknown) - ¥850.00" + System.lineSeparator() +
                "2) Phone Bill (Bills) - ¥37.99" + System.lineSeparator() +
                "3) Electricity Bill (Bills) - ¥75.00" + System.lineSeparator() +
                "4) Sainsbury's Checkout (Groceries) - ¥23.76" + System.lineSeparator() +
                "5) Tesco's Checkout (Groceries) - ¥7.24" + System.lineSeparator() +
                "6) RockCity Drinks (Social) - ¥8.50" + System.lineSeparator() +
                "7) The Mooch (Social) - ¥13.99" + System.lineSeparator(), outContent.toString());
        outContent.reset();
    }
    /*
     * Test and modify by Yuyang LIN
     * Fail(Date:05/03/2020)
     * Actual output:
     *
     * 1) Rent - ¥850.00
     * 2) Phone Bill - ¥37.99
     * 3) Electricity Bill - ¥75.00
     * 4) Sainsbury's Checkout - ¥23.76
     * 5) Tesco's Checkout - ¥7.24
     * 6) RockCity Drinks - ¥8.50
     * 7) The Mooch - ¥13.99
     *
     * Modify: Add the category name
     *
     * Pass(Date:05/03/2020)
     * */

    /**
     * @author Yuyang LIN
     * the below function tests whether the method CategoryOverview()
     * prints out the correct output
     */
    @Test
    void Test_App_CategoryOverview() {
        CategoryOverview();
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" +System.lineSeparator()+
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)"+ System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)"+ System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+System.lineSeparator(), outContent.toString());
        outContent.reset();
    }
    /*
     * Test and modify by Yuyang LIN
     * Fail(Date:05/03/2020)
     * Actual output:
     *
     * 1) [Unknown] (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)
     * 2) [Bills] (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)
     * 3) [Groceries] (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)
     * 4) [Social] (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)
     *
     * Modify: Make a new toString() and remove "[ ]"
     *
     * Pass(Date:05/03/2020)
     * */

    /**
     * @author Rongze LI
     * the below function tests whether the method ListTransactionsForCategory()
     * prints out the correct output
     */
    @ParameterizedTest
    @MethodSource("data")
    public void Test_App_ListTransactionsForCategory(int fInput, String fExpected) {
        BoCApp.ListTransactionsForCategory(fInput);
        assertEquals(fExpected, outContent.toString());
        outContent.reset();
    }

    static Stream<Arguments> data(){
        return Stream.of(Arguments.of(1, "2) Phone Bill (Bills) - ¥37.99" + System.lineSeparator() + "3) Electricity Bill (Bills) - ¥75.00" + System.lineSeparator()), Arguments.of(4, "Sorry, the category ID you entered is not existed" + System.lineSeparator()));
    }
    /*
     * Test and modify by Rongze LI
     * Fail(Date:05/04/2020)
     * (input = 4) Actual output:
     * (no output)
     *
     * Modify: Modify the code to add a message
     *
     * Pass(Date:05/04/2020)
     * */

}

