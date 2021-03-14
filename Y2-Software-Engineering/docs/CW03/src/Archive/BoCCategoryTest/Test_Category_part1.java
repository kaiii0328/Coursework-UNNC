import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
/**
 * @author Rongze LI
 * @class This test class tests the method for Transaction class, 
 * the methods including constructors, CategoryName() and CategoryBudget()
 *
 */ 

class Test_Category_part1 {
	BoCApp bp = new BoCApp();
	/**
	 * the below function tests whether the constructor new an instance which
	 * Name = "New Category"
	 */
	@Test
	void test_Category_DefaultConstructor() {
		BoCCategory DefaultConstructor = new BoCCategory();
		System.out.println("DefaultConstructor -> new BoCCategory()");
		System.out.println("Input: No inputs ");
		System.out.println("Expected: Name = New Category, Budget = 0.00, Spend = 0.00");
		System.out.println("Actual:   Name = "+ DefaultConstructor.CategoryName()+ ", Budget = "+DefaultConstructor.CategoryBudget()+", Spend = "+DefaultConstructor.CategorySpend());
		System.out.println("");
		assertEquals("New Category",DefaultConstructor.CategoryName());
		assertFalse(DefaultConstructor.CategoryBudget()== new BigDecimal("0.00"));
		assertFalse(DefaultConstructor.CategorySpend()== new BigDecimal("0.00"));
		
	}
	/**
	 * the below function tests whether the main constructor new an instance with
	 * different lengths of names properly
	 */
	
	@Test
	void test_Category_MainConstructor() {
		/**
		 * the below part tests whether the main constructor can new an instance
		 * when giving expected input
		 */
		BoCCategory MainConstructor = new BoCCategory("Social");
		System.out.println("MainConstructor -> new BoCCategory(\"Social\")");
		System.out.println("Input: \"Social\"");
		System.out.println("Expected: Name = Social, Budget = 0.00, Spend = 0.00");
		System.out.println("Actual:   Name = "+ MainConstructor.CategoryName()+ ", Budget = "+MainConstructor.CategoryBudget()+", Spend = "+MainConstructor.CategorySpend());
		System.out.println("");

		assertTrue(MainConstructor.CategoryName().equals("Social"));
		assertTrue(MainConstructor.CategoryBudget().compareTo(new BigDecimal("0.00"))==0);
		assertTrue(MainConstructor.CategorySpend().compareTo(new BigDecimal("0.00"))==0);
	
		/**
		 * the below part tests whether the constructor can new an instance
		 * which has an name input more than 15 characters and cut off the extra characters 
		 */
	
		BoCCategory MainConstructor2 = new BoCCategory("AVertLongCategory");
		System.out.println("MainConstructor2 -> new BoCCategory(\"AVertLongCategory\"");
		System.out.println("Input: \"AVertLongCategory\"");
		System.out.println("Expected: Name = AVertLongCatego, Budget = 0.00, Spend = 0.00");
		System.out.println("Actual:   Name = "+ MainConstructor2.CategoryName()+ ", Budget = "+MainConstructor2.CategoryBudget()+", Spend = "+MainConstructor2.CategorySpend());
		System.out.println("");
		assertTrue(MainConstructor2.CategoryName().equals("AVertLongCatego"));
		assertTrue(MainConstructor2.CategoryBudget().compareTo(new BigDecimal("0.00"))==0);
		assertTrue(MainConstructor2.CategorySpend().compareTo(new BigDecimal("0.00"))==0);
	}
	
	/**
	 * the below function tests whether the method to get name of Category
	 * works properly
	 */
	@Test
	void test_Category_CategoryName() {
		BoCCategory TestCategoryName = new BoCCategory();
		System.out.println("TestcategoryName -> CategoryName()");
		System.out.println("Input: TestCategoryName = new BoCCategory()");
		System.out.println("Expected: Name = New Category");
		System.out.println("Actual:   Name = "+ TestCategoryName.CategoryName());
		System.out.println("");
		
		assertEquals("New Category", TestCategoryName.CategoryName());
	}
	
	/**
	 * the below function tests whether the method to get budget of Category
	 * works without input 
	 */
	@Test
	void test_Category_CategoryBudget() {
		BoCCategory TestCategoryBudget = new BoCCategory();
		System.out.println("TestCategoryBudget -> CategoryBudget()");
		System.out.println("Input: TestCategoryBudget = new BoCCategory()");
		System.out.println("Expected: Budget = 0.00");
		System.out.println("Actual:   Budget = "+ TestCategoryBudget.CategoryBudget());
		System.out.println("");
		assertEquals(new BigDecimal("0.00"), TestCategoryBudget.CategoryBudget());
	}


}
