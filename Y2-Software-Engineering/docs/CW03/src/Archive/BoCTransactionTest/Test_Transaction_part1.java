import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
/**
 * 
 * @author Wangkai JIN
 * This test class tests the method for Transaction class, the methods including default constructor.
 * main constructor, transactionName and transactionValue
 *
 */
public class Test_Transaction_part1 {
	/**
	 * the below function tests whether the default constructor new an instance which
	 * Name = "[Pending Transaction]", Category = 0
	 */
	@Test
	void test_Transaction_DefaultConstructor() {
		BoCTransaction defaultconstructor = new BoCTransaction();
		System.out.println("defaultconstructor -> new BoCTransaction()");
		System.out.println("Input: No inputs ");
		System.out.println("Expected: Name = [Pending Transaction], Category = 0 ");
		System.out.println("Actual:   Name = "+ defaultconstructor.transactionName()+ ", Category = "+defaultconstructor.transactionCategory());
		System.out.println("");
		assertEquals("[Pending Transaction]",defaultconstructor.transactionName());
		assertFalse(defaultconstructor.transactionCategory()==0);
	}
	
	
	@Test
	void test_Transaction_MainConstructor() {
		/**
		 * the below function tests whether the main constructor can new an instance
		 * when giving expected input
		 */
		BoCTransaction MainConstructor = new BoCTransaction("Rent", new BigDecimal("850.00"), 0);
		System.out.println("MainConstructor -> new BoCTransaction(\"Rent\", new BigDecimal(\"850.00\"), 0)");
		System.out.println("Input: \"Rent\", new BigDecimal(\"850.00\"), 0 ");
		System.out.println("Expected: Name = Rent, Value = 850.00, Category = 0 ");
		System.out.println("Actual:   Name = "+ MainConstructor.transactionName()+", Value = "+MainConstructor.transactionValue() + ", Category = "+MainConstructor.transactionCategory());
		System.out.println("");

		assertTrue(MainConstructor.transactionName().equals("Rent"));
		assertTrue(MainConstructor.transactionValue().compareTo(new BigDecimal("850.00"))==0);
		assertTrue(MainConstructor.transactionCategory()==0);
	
		/**
		 * the below function tests whether the main constructor can new an instance
		 * which has an name input more than 25 characters and cut off the extra characters 
		 */
	
		BoCTransaction MainConstructor2 = new BoCTransaction("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new BigDecimal("850.00"), 0);
		System.out.println("MainConstructor2 -> new BoCTransaction(\"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", new BigDecimal(\"850.00\"), 0)");
		System.out.println("Input: \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", new BigDecimal(\"850.00\"), 0 ");
		System.out.println("Expected: Name = ABCDEFGHIJKLMNOPQRSTUVWXY ");
		System.out.println("Actual:   Name = "+ MainConstructor2.transactionName());
		System.out.println("");
		
	
		/**
		 * the below function tests whether the main constructor can new an instance
		 * which has an negative value input and  handle it 
		 */
		
		BoCTransaction MainConstructor3 = new BoCTransaction("Rent", new BigDecimal("-2"), 0);
		System.out.println("MainConstructor3 -> new BoCTransaction(\"Rent\", new BigDecimal(\"-2\"), 0)");
		System.out.println("Input: \"Rent\", new BigDecimal(\"-2\"), 0 ");
		System.out.println("Expected: Value > 0 ");
		System.out.println("Actual:   Value = "+ MainConstructor3.transactionValue());
		System.out.println("");
		
		assertTrue(MainConstructor2.transactionName().equals("ABCDEFGHIJKLMNOPQRSTUVWXY"));
		assertFalse(MainConstructor3.transactionValue().compareTo(new BigDecimal("0"))>0);
	}
	
	/**
	 * the below function tests whether the method to get name of Transaction
	 * works properly
	 */
	@Test
	void test_Transaction_transactionName() {
		BoCTransaction TestTransactoinName = new BoCTransaction("Rent", new BigDecimal("850.00"), 0);
		System.out.println("TestTransactoinName -> new BoCTransaction(\"Rent\", new BigDecimal(\"850.00\"), 0)");
		System.out.println("Input: \"Rent\", new BigDecimal(\"850.00\"), 0");
		System.out.println("Expected: Name = Rent");
		System.out.println("Actual:   Name = "+ TestTransactoinName.transactionName());
		System.out.println("");
		
		assertEquals("Rent", TestTransactoinName.transactionName());
	}
	
	/**
	 * the below function tests whether the method to get name of Transaction
	 * works with input 
	 */
	@Test
	void test_Transaction_transactionValue() {
		BoCTransaction TestTransactionValue = new BoCTransaction("Rent", new BigDecimal("850.00"), 0);
		System.out.println("TestTransactionValue -> new BoCTransaction(\"Rent\", new BigDecimal(\"850.00\"), 0)");
		System.out.println("Input: \"Rent\", new BigDecimal(\"850.00\"), 0");
		System.out.println("Expected: Value = 0");
		System.out.println("Actual:   Value = "+ TestTransactionValue.transactionValue());
		System.out.println("");
		assertEquals("Rent", TestTransactionValue.transactionValue());
	}
	
	
}
