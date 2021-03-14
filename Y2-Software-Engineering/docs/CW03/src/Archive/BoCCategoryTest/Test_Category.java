import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Test_Category {
	static BigDecimal spend = new BigDecimal("1.00");
	static BigDecimal budget = new BigDecimal("0.00");
	static String name = "";
	static String longname = "";
	static float bigbudget = 0.00f;
	static float smallbudget = 0.00f;
	static BigDecimal bigspend = new BigDecimal("0.00");
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	static PrintStream stdout;

	@BeforeAll
	static void setup() {
		stdout = System.out;
		budget.add(new BigDecimal("1000"));
		name = "newName";
		longname = "AVeryLongCategory";
		bigbudget = 100.00f;
		smallbudget = 0.01f;
	}

	/**
	 * @author Rongze LI 
	 * the below function tests whether the constructor new an
	 * instance which Name = "New Category"
	 */
	@Test
	void test_Category_DefaultConstructor() {
		BoCCategory DefaultConstructor = new BoCCategory();
		System.out.println("DefaultConstructor -> new BoCCategory()");
		System.out.println("Input: No inputs ");
		System.out.println("Expected: Name = New Category, Budget = 0.00, Spend = 0.00");
		System.out.println("Actual:   Name = " + DefaultConstructor.CategoryName() + ", Budget = "
				+ DefaultConstructor.CategoryBudget() + ", Spend = " + DefaultConstructor.CategorySpend());
		System.out.println("");
		assertEquals("New Category", DefaultConstructor.CategoryName());
		assertFalse(DefaultConstructor.CategoryBudget() == new BigDecimal("0.00"));
		assertFalse(DefaultConstructor.CategorySpend() == new BigDecimal("0.00"));

	}

	/**
	 * @author Rongze LI the below function tests whether the main constructor new
	 *         an instance with different lengths of names properly
	 */

	@Test
	void test_Category_MainConstructor() {
		/**
		 * the below part tests whether the main constructor can new an instance when
		 * giving expected input
		 */
		BoCCategory MainConstructor = new BoCCategory("Social");
		System.out.println("MainConstructor -> new BoCCategory(\"Social\")");
		System.out.println("Input: \"Social\"");
		System.out.println("Expected: Name = Social, Budget = 0.00, Spend = 0.00");
		System.out.println("Actual:   Name = " + MainConstructor.CategoryName() + ", Budget = "
				+ MainConstructor.CategoryBudget() + ", Spend = " + MainConstructor.CategorySpend());
		System.out.println("");

		assertTrue(MainConstructor.CategoryName().equals("Social"));
		assertTrue(MainConstructor.CategoryBudget().compareTo(new BigDecimal("0.00")) == 0);
		assertTrue(MainConstructor.CategorySpend().compareTo(new BigDecimal("0.00")) == 0);

		/**
		 * the below part tests whether the constructor can new an instance which has an
		 * name input more than 15 characters and cut off the extra characters
		 */

		BoCCategory MainConstructor2 = new BoCCategory("AVertLongCategory");
		System.out.println("MainConstructor2 -> new BoCCategory(\"AVertLongCategory\"");
		System.out.println("Input: \"AVertLongCategory\"");
		System.out.println("Expected: Name = AVertLongCatego, Budget = 0.00, Spend = 0.00");
		System.out.println("Actual:   Name = " + MainConstructor2.CategoryName() + ", Budget = "
				+ MainConstructor2.CategoryBudget() + ", Spend = " + MainConstructor2.CategorySpend());
		System.out.println("");
		assertTrue(MainConstructor2.CategoryName().equals("AVertLongCatego"));
		assertTrue(MainConstructor2.CategoryBudget().compareTo(new BigDecimal("0.00")) == 0);
		assertTrue(MainConstructor2.CategorySpend().compareTo(new BigDecimal("0.00")) == 0);
	}

	/**
	 * @author Rongze LI the below function tests whether the method to get name of
	 *         Category works properly
	 */
	@Test
	void test_Category_CategoryName() {
		BoCCategory TestCategoryName = new BoCCategory();
		System.out.println("TestcategoryName -> CategoryName()");
		System.out.println("Input: TestCategoryName = new BoCCategory()");
		System.out.println("Expected: Name = New Category");
		System.out.println("Actual:   Name = " + TestCategoryName.CategoryName());
		System.out.println("");

		assertEquals("New Category", TestCategoryName.CategoryName());
	}

	/**
	 * @author Rongze LI the below function tests whether the method to get budget
	 *         of Category works without input
	 */
	@Test
	void test_Category_CategoryBudget() {
		BoCCategory TestCategoryBudget = new BoCCategory();
		System.out.println("TestCategoryBudget -> CategoryBudget()");
		System.out.println("Input: TestCategoryBudget = new BoCCategory()");
		System.out.println("Expected: Budget = 0.00");
		System.out.println("Actual:   Budget = " + TestCategoryBudget.CategoryBudget());
		System.out.println("");
		assertEquals(new BigDecimal("0.00"), TestCategoryBudget.CategoryBudget());
	}

	/**
	 * @Yizhou CHEN
	 * test the functionality of testCategorySpend()
	 */
	@Test
	void test_Category_CategorySpend() {
		BoCCategory TestCategorySpend = new BoCCategory();
		// test if the method return CategorySpend with void input
		System.out.println("TestCategorySpend -> CategorySpend()");
		System.out.println("Input: void");
		System.out.println("Expected: 0.00");
		System.out.println("Actual:   " + TestCategorySpend.CategorySpend());
		System.out.println("");
		assertEquals(TestCategorySpend.CategorySpend(), new BigDecimal("0.00"));
	}

	/**
	 * @Yizhou CHEN
	 * test setCategoryName with String whose length <= 15, >15 respectively
	 */
	@Test
	void test_Category_setCategoryName() {
		BoCCategory TestsetCategoryName = new BoCCategory();
		// String length <= 15
		TestsetCategoryName.setCategoryName(name);
		assertTrue(TestsetCategoryName.CategoryName().equals(name));

		System.out.println("TestsetCategoryName -> setCategoryName(\"newName\")");
		System.out.println("Input: " + name);
		System.out.println("Expected: CategoryName = " + name);
		System.out.println("Actual: CategoryName = " + TestsetCategoryName.CategoryName());
		System.out.println("");

		// String length >15
		TestsetCategoryName.setCategoryName(longname);
		assertFalse(TestsetCategoryName.CategoryName().equals(longname));
		System.out.println("TestsetCategoryName -> setCategoryName(\"AVeryLongCategory\")");
		System.out.println("Input: " + name);
		System.out.println("Expected: CategoryName = AVeryLongCatego");
		System.out.println("Actual:   CategoryName = " + TestsetCategoryName.CategoryName());
		System.out.println("");
	}

	/**
	 * @Yizhou CHEN
	 * test functionality with big budget and small budget< total spend Another test
	 * for type checking
	 * 
	 * @throws Exception
	 */
	@Test
	void test_Category_setCategoryBudget() throws Exception {
		BoCCategory TestsetCategoryBudget = new BoCCategory(name);
		// check if the functionality is correct
		TestsetCategoryBudget.setCategoryBudget(bigbudget);
		assertEquals(TestsetCategoryBudget.CategoryBudget(), new BigDecimal(((Number) bigbudget).toString()));
		System.out.println("TestsetCategoryBudget -> setCategoryBudget()");
		System.out.println("Input: 100.00f");
		System.out.println("Expected: CategoryBudget = 100.00f");
		System.out.println("Actual:   CategoryBudget = " + TestsetCategoryBudget.CategoryBudget());
		System.out.println("");
		// check if it fails as budget would be set < total spend

		BoCCategory TestsetCategoryBudget2 = new BoCCategory(name);
		TestsetCategoryBudget2.addExpense(new BigDecimal("99"));
		TestsetCategoryBudget2.setCategoryBudget(smallbudget);
		assertNotEquals(TestsetCategoryBudget2.CategoryBudget(), smallbudget);

		System.out.println("TestsetCategoryBudget2 -> setCategoryBudget(0.01f)");
		System.out.println("Input: Category with spend 99, input for set budget: 0.01f");
		System.out.println("Expected: CategoryBudget should be greater than spend");
		System.out.println("Actual:   CategoryBudget = " + TestsetCategoryBudget2.CategoryBudget());
		System.out.println("");
	}

	/**
	 * @author Wangkai JIN The below function is ignored because it is primarily
	 *         being used to test setCategoryBudget method but later on the misuse
	 *         of type in the source code is found. After fixing the type error,
	 *         this test function is ignored because of a unresolved compiling error
	 *         caused by type conflict
	 */

	@Ignore
	void test_Category_setCategoryBudget2() {
		BoCCategory TestsetCategoryBudget = new BoCCategory(name);
		// TestsetCategoryBudget.setCategoryBudget(new BigDecimal("0.00"));
		System.out.println("TestsetCategoryBudget3 -> setCategoryBudget(0.00f)");
		System.out.println("Input: new BigDecimal(\"1.00\")");
		System.out.println(
				"Expected: Error: The method setCategoryBudget(float) in the type BoCCategory is not applicable for the arguments(BigDecimal)");
		System.out.println("Actual: CategoryBudget =" + TestsetCategoryBudget.CategoryBudget());
		System.out.println("");
		assertTrue(TestsetCategoryBudget.CategoryBudget().compareTo(new BigDecimal("0.00")) == 0);
	}

	/**
	 * @Yizhou CHEN
	 * check functionality with bigspend>budget, spend < budget and a minus one
	 */
	@Test
	void test_Category_addExpense() {
		// test if correctly add spend = 1 to CategorySpend
		BoCCategory TestaddExpense = new BoCCategory(name);
		TestaddExpense.addExpense(spend);
		System.out.println("TestaddExpense -> addExpense(1f)");
		System.out.println("Input: 1");
		System.out.println("Expected: CategorySpend = 1.00");
		System.out.println("Actual: CategorySpend = " + TestaddExpense.CategorySpend());
		System.out.println("");
		assertTrue(TestaddExpense.CategorySpend().compareTo(new BigDecimal("1")) == 0);

		// test if fails as newly added spend is negative
		TestaddExpense.addExpense(new BigDecimal("-1.00"));
		System.out.println("TestaddExpense -> addExpense(\"-1\")");
		System.out.println("Input: new BigDecimal(\"-1.00\")");
		System.out.println("Expected: CategorySpend = 1(category doesn't add an negative value)");
		System.out.println("Actual:   CategorySpend = " + TestaddExpense.CategorySpend());
		System.out.println("");
		assertEquals(TestaddExpense.CategorySpend(), new BigDecimal("1.00"));

	}

	@AfterAll
	public static void cleanUp() {
		outContent.reset();
	}

	/**
	 * @author Yuyang LI
	 * the below function tests whether the method subtract input from the current
	 * total spend (input >= 0)
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
		 * the below function tests whether the method subtract input from the current
		 * total spend (input = 0)
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
		 * the below function tests whether the method subtract input from the current
		 * total spend (input < 0)
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
		 * the below function tests whether the method subtract input from the current
		 * total spend when final spend is less than zero
		 */
		BoCCategory TestremoveExpense4 = new BoCCategory();
		System.setOut(new PrintStream(outContent));
		TestremoveExpense4.removeExpense(new BigDecimal("1.00"));
		assertEquals("CategorySpend cannot be negative!", outContent.toString().trim());
		outContent.reset();
		System.setOut(stdout);
		System.out.println("TestremoveExpense4 -> removeExpense(new BigDecimal(\"1.00\"))");
		System.out.println("Input: Category with spend 0, removeExpense: new BigDecimal(\"1.00\")");
		System.out.println("Expected: " + new BigDecimal("0.00") + "\nPrint: \"CategorySpend cannot be negative!\"");
		System.out.println("Actual: " + TestremoveExpense4.CategorySpend());
		System.out.println("");

		assertTrue(TestremoveExpense.CategorySpend().equals(new BigDecimal("9.00")));
		assertTrue(TestremoveExpense2.CategorySpend().equals(new BigDecimal("10.00")));
		assertTrue(TestremoveExpense3.CategorySpend().equals(new BigDecimal("10.00")));
		assertTrue(TestremoveExpense4.CategorySpend().compareTo(new BigDecimal("0.00")) == 0);

	}

	/**
	 * @author Yuyang LI
	 * the below function tests whether the method to reset budget spend works
	 * properly
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
	 * @author Yuyang LI
	 * the below function tests whether the method to get remaining budget works
	 * properly
	 * 
	 * @throws Exception
	 */
	@Test
	void Test_Category_getRemainingBudget() throws Exception {
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
	 * @author Yuyang LI
	 * the below function tests whether the method toString works properly
	 * @throws Exception 
	 */
	@Test
	void Test_Category_testToString() throws Exception {
		BoCCategory TesttoString = new BoCCategory();
        System.out.println("TesttoString -> toString()");
        System.out.println("Expected: " + "[New Category](Budget:¥0.00) - ¥0.00 (¥0.00 Remaining)");
        System.out.println("Actual: " + TesttoString.toString());
        System.out.println("");
        assertEquals(TesttoString.toString(), "[New Category](Budget:¥0.00) - ¥0.00 (¥0.00 Remaining)");
        
        BoCCategory TesttoString2 = new BoCCategory();
        TesttoString2.setCategoryBudget(20f);
        TesttoString2.addExpense(new BigDecimal("40"));
        System.out.println("TesttoString2 -> toString()");
        System.out.println("Expected: " + "[New Category](Budget:¥20.0) - ¥40.00 (¥20.00 Overspent)");
        System.out.println("Actual: " + TesttoString2.toString());
        System.out.println("");
        assertEquals(TesttoString2.toString(), "[New Category](Budget:¥20.0) - ¥40.00 (¥20.00 Overspent)");
		
	}

}
