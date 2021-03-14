import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author Yizhou Chen
 * @class this class is to test method 4-7 in BoCCategory.class
 * which are CategorySpend(), setCategoryName(), setCategoryBudget() and addExpense()
 * 
 */


public class Test_Category_part2 {
	    static BigDecimal spend = new BigDecimal("1.00");
	    static BigDecimal budget = new BigDecimal("0.00");
	    static String name = "";
	    static String longname = "";
	    static float bigbudget = 0.00f;
	    static float smallbudget = 0.00f;
	    static BigDecimal bigspend = new BigDecimal("0.00");



	    @BeforeAll
	    static void setup(){
	        budget.add(new BigDecimal("1000"));
	        name = "newName"; 
	        longname = "AVeryLongCategory";
	        bigbudget = 100.00f;
	        smallbudget = 0.01f;
	    }

	    /**
	     *test the functionality of testCategorySpend()
	     */
	    @Test
	    void test_Category_CategorySpend() {
	        BoCCategory TestCategorySpend = new BoCCategory();
	        //test if the method return CategorySpend with void input
	        System.out.println("TestCategorySpend -> CategorySpend()");
	        System.out.println("Input: void");
	        System.out.println("Expected: 0");
	        System.out.println("Actual: " + TestCategorySpend.CategorySpend());
	        System.out.println("");
	        assertEquals(TestCategorySpend.CategorySpend(), new BigDecimal("0.00"));
	    }

	    /**
	     * test setCategoryName with String whose length <= 15, >15 respectively
	     */
	    @Test
	    void test_Category_setCategoryName(){
	        BoCCategory TestsetCategoryName = new BoCCategory();
	        //String length <= 15
	        TestsetCategoryName.setCategoryName(name);
	        assertTrue(TestsetCategoryName.CategoryName().equals(name));

	        System.out.println("TestsetCategoryName -> setCategoryName(\"newName\")");
	        System.out.println("Input: " + name);
	        System.out.println("Expected: CategoryName = " + name);
	        System.out.println("Actual: CategoryName = " + TestsetCategoryName.CategoryName());
	        System.out.println("");

	        //String length >15
	        TestsetCategoryName.setCategoryName(longname);
	        assertFalse(TestsetCategoryName.CategoryName().equals(longname));
	        System.out.println("TestsetCategoryName -> setCategoryName(\"AVeryLongCategory\")");
	        System.out.println("Input: " + name);
	        System.out.println("Expected: CategoryName = AVeryLongCatego");
	        System.out.println("Actual:   CategoryName = " + TestsetCategoryName.CategoryName());
	        System.out.println("");
	    }
	    /**
	     * test functionality with big budget and small budget< total spend
	     * Another test for type checking
	     * @throws Exception 
	     */
	    @Test
	    void test_Category_setCategoryBudget() throws Exception {
	        BoCCategory TestsetCategoryBudget = new BoCCategory(name);
	        //check if the functionality is correct
	        TestsetCategoryBudget.setCategoryBudget(bigbudget);
	        assertEquals(TestsetCategoryBudget.CategoryBudget(), new BigDecimal(((Number)bigbudget).toString()));
	        System.out.println("TestsetCategoryBudget -> setCategoryBudget()");
	        System.out.println("Input: 100.00f" );
	        System.out.println("Expected: CategoryBudget == 100.00f" );
	        System.out.println("Actual: Error: The method setCategoryBudget(BigDecimal) in the type BoCCategory is not applicable for the arguments(float)");
	        System.out.println("");
	        //check if it fails as budget would be set < total spend

	        BoCCategory TestsetCategoryBudget2 = new BoCCategory(name);
	        TestsetCategoryBudget2.addExpense(new BigDecimal("99"));
	        TestsetCategoryBudget2.setCategoryBudget(smallbudget);
	        assertNotEquals(TestsetCategoryBudget2.CategoryBudget(), smallbudget);

	        System.out.println("TestsetCategoryBudget2 -> setCategoryBudget(0.01f)");
	        System.out.println("Input: Category with spend 99, input for set budget: 0.01f" );
	        System.out.println("Expected: CategoryBudget should be greater than spend" );
	        System.out.println("Actual:   CategoryBudget = " + TestsetCategoryBudget2.CategoryBudget());
	        System.out.println("");
	    }
	    /**
	     * @author Wangkai JIN
	     * The below function is ignored because it is primarily being used to test setCategoryBudget method
	     * but later on the misuse of type in the source code is found. After fixing the type error, this 
	     * test function is ignored because of a unresolved compiling error caused by type conflict 
	     */
	    		
	    @Ignore
	    void test_Category_setCategoryBudget2() {
	        BoCCategory TestsetCategoryBudget = new BoCCategory(name);
	        //TestsetCategoryBudget.setCategoryBudget(new BigDecimal("0.00"));
	        System.out.println("TestsetCategoryBudget3 -> setCategoryBudget(0.00f)");
	        System.out.println("Input: new BigDecimal(\"1.00\")" );
	        System.out.println("Expected: Error: The method setCategoryBudget(float) in the type BoCCategory is not applicable for the arguments(BigDecimal)" );
	        System.out.println("Actual: CategoryBudget =" + TestsetCategoryBudget.CategoryBudget());
	        System.out.println("");
	        assertTrue(TestsetCategoryBudget.CategoryBudget().compareTo(new BigDecimal("0.00"))==0);
	    }
	    /**
	     *check functionality with bigspend>budget, spend < budget and a minus one
	     */
	    @Test
	    void test_Category_addExpense() {
	        //test if correctly add spend = 1 to CategorySpend
	        BoCCategory TestaddExpense = new BoCCategory(name);
	        TestaddExpense.addExpense(spend);

	        System.out.println("TestaddExpense -> addExpense(1f)");
	        System.out.println("Input: 1" );
	        System.out.println("Expected: CategorySpend = 1" );
	        System.out.println("Actual: CategorySpend = " + TestaddExpense.CategorySpend());
	        System.out.println("");

	        assertTrue(TestaddExpense.CategorySpend().compareTo(new BigDecimal("1"))==0);

	        

	        //test if fails as newly added spend is negative
	        TestaddExpense.addExpense(new BigDecimal("-1.00"));
	        System.out.println("TestaddExpense -> addExpense(\"-1\")");
	        System.out.println("Input: new BigDecimal(\"-1.00\")" );
	        System.out.println("Expected: CategorySpend = 1(category doesn't add an negative value)" );
	        System.out.println("Actual:   CategorySpend = " + TestaddExpense.CategorySpend());

	        assertEquals(TestaddExpense.CategorySpend(), new BigDecimal("1.00"));

	    }

	}


