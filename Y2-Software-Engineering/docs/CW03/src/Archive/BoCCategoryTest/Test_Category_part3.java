import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yuyang Lin
 * This test class tests the methods for Category class, including removeExpense(),
 *  resetBudgetSpend(), getRemainingBudget() and toString().
 */

public class Test_Category_part3 {
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); 
	static PrintStream stdout ;
    @BeforeAll
    public static void setUp() {
    	 stdout = System.out;
    }
    @AfterAll
    public static void cleanUp() {
    	outContent.reset();
    	System.setOut(null); 
    }
    /**
     * the below function tests whether the method subtract input from the
     * current total spend (input >= 0)
     */ 
    @Test
    void Test_Category_removeExpense() {
        BoCCategory TestremoveExpense = new BoCCategory();
        TestremoveExpense.addExpense(new BigDecimal("10.00"));
        TestremoveExpense.removeExpense(new BigDecimal("1.00"));
        System.out.println("TestremoveExpense -> removeExpense(new BigDecimal(\"1.00\"))");
        System.out.println("Input: Category with expense 10.00, removeExpense:new BigDecimal(\"1.00\")");
        System.out.println("Expected: " + new BigDecimal("9.00"));
        System.out.println("Actual: " + TestremoveExpense.CategorySpend());
        System.out.println("");
       
    

    /**
     * the below function tests whether the method subtract input from the
     * current total spend (input = 0)
     */
   
        BoCCategory TestremoveExpense2 = new BoCCategory();
        TestremoveExpense2.addExpense(new BigDecimal("10.00"));
        TestremoveExpense2.removeExpense(new BigDecimal("0.00"));
        System.out.println("TestremoveExpense2 -> removeExpense(new BigDecimal(\"0.00\"))");
        System.out.println("Input: Category with expense 10.00, removeExpense: new BigDecimal(\"0.00\")");
        System.out.println("Expected: " + new BigDecimal("10.00"));
        System.out.println("Actual: " + TestremoveExpense2.CategorySpend());
        System.out.println("");
    

    /**
     * the below function tests whether the method subtract input from the
     * current total spend (input < 0)
     */

        BoCCategory TestremoveExpense3 = new BoCCategory();
        TestremoveExpense3.addExpense(new BigDecimal("10.00"));
        System.setOut(new PrintStream(outContent));
        TestremoveExpense3.removeExpense(new BigDecimal("-1.00"));
        assertEquals("Input cannot be negative!", outContent.toString().trim());
        outContent.reset();
        System.setOut(stdout);
        System.out.println("TestremoveExpense3 -> removeExpense(new BigDecimal(\"-1.00\"))");
        System.out.println("Input: Category with expense 10.00 removeExpense: new BigDecimal(\"-1.00\")");
        System.out.println("Expected: " + new BigDecimal("10.00") + "\nPrint: \"Input cannot be negative!\"");
        System.out.println("Actual: " + TestremoveExpense3.CategorySpend());        
        System.out.println("");
        
       
    

    /**
     * the below function tests whether the method subtract input from the
     * current total spend when final spend is less than zero
     */
    	BoCCategory TestremoveExpense4 = new BoCCategory();
    	System.setOut(new PrintStream(outContent));
        TestremoveExpense4.removeExpense(new BigDecimal("1.00"));
        assertEquals("CategorySpend cannot be negative!", outContent.toString().trim());
        outContent.reset();
        System.setOut(stdout);
        System.out.println("TestremoveExpense4 -> removeExpense(new BigDecimal(\"1.00\"))");
        System.out.println("Input: Category with spend 0, removeExpense: new BigDecimal(\"1.00\")");
        System.out.println("Expected: " + new BigDecimal("0.00")+ "\nPrint: \"CategorySpend cannot be negative!\"");
        System.out.println("Actual: " + TestremoveExpense4.CategorySpend());
        System.out.println("");
        
        assertTrue(TestremoveExpense.CategorySpend().equals(new BigDecimal("9.00")));
        assertTrue(TestremoveExpense2.CategorySpend().equals(new BigDecimal("10.00")));
        assertTrue(TestremoveExpense3.CategorySpend().equals(new BigDecimal("10.00")));
        assertTrue(TestremoveExpense4.CategorySpend().compareTo(new BigDecimal("0.00")) == 0);
        
    }

    /**
     * the below function tests whether the method to reset budget spend
     * works properly
     */
    @Test
    void Test_Category_resetBudgetSpend() {
        BoCCategory TestresetBudgetSpend = new BoCCategory();
        TestresetBudgetSpend.addExpense(new BigDecimal("200.00"));
        TestresetBudgetSpend.resetBudgetSpend();
        System.out.println("TestresetBudgetSpend -> resetBudgetSpend()");
        System.out.println("Input: Category with spend 200, no inputs for method");
        System.out.println("Expected: " + new BigDecimal("0.00"));
        System.out.println("Actual: " + TestresetBudgetSpend.CategorySpend());
        System.out.println("");
        assertEquals(new BigDecimal("0.00"), TestresetBudgetSpend.CategorySpend());
    }


    /**
     * the below function tests whether the method to get remaining budget
     * works properly
     * @throws Exception 
     */
    @Test
    void Test_Category_getRemainingBudget() throws Exception  {
        BoCCategory TestgetRemainingBudget = new BoCCategory();
        TestgetRemainingBudget.setCategoryBudget(250f);
        TestgetRemainingBudget.addExpense(new BigDecimal("200.00"));
        TestgetRemainingBudget.getRemainingBudget();
        System.out.println("TestgetRemainingBudget -> getRemainingBudget()");
        System.out.println("Input: Category with budget 250, spend 200, no inputs for method");
        System.out.println("Expected: " + new BigDecimal("50.00"));
        System.out.println("Actual: " + TestgetRemainingBudget.getRemainingBudget());
        System.out.println("");
        assertEquals(new BigDecimal("50.00"), TestgetRemainingBudget.getRemainingBudget());
    }

    /**
     * the below function tests whether the method toString
     * works properly
     * @throws Exception 
     */
    @Test
    void Test_Category_testToString() throws Exception {
        BoCCategory TesttoString = new BoCCategory();
        System.out.println("TesttoString -> toString()");
        System.out.println("Expected: " + "New Category(¥0.00) - Est. ¥0.00 (¥0.00 Remaining)");
        System.out.println("Actual: " + TesttoString.toString());
        System.out.println("");
        assertEquals(TesttoString.toString(), "New Category(¥0.00) - Est. ¥0.00 (¥0.00 Remaining)");
        
        BoCCategory TesttoString2 = new BoCCategory();
        TesttoString2.setCategoryBudget(20f);
        TesttoString2.addExpense(new BigDecimal("40"));
        System.out.println("TesttoString2 -> toString()");
        System.out.println("Expected: " + "New Category(¥20.0) - Est. ¥40.00 (¥20.00 Overspent)");
        System.out.println("Actual: " + TesttoString2.toString());
        System.out.println("");
        assertEquals(TesttoString2.toString(), "New Category(¥20.0) - Est. ¥40.00 (¥20.00 Overspent)");
    }
}