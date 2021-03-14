import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;

/**
 * 
 * @author Zhihao Li This test class tests the method for Transaction class, the
 *         methods including setTransactionCategory() isComplete() and
 *         toString()
 *
 */
public class Test_Transaction_Part3 {

	static int input1;
	static int input2;
	static BoCTransaction transactionIsComplete1 = new BoCTransaction();
	static BoCTransaction transactionIsComplete2 = new BoCTransaction("Food", new BigDecimal("10"), 1);
	static BoCTransaction transactionIsComplete3 = new BoCTransaction("Breakfast", new BigDecimal("3000"), 1);
	static BoCTransaction transactionIsComplete4 = new BoCTransaction("Food", new BigDecimal("0.5"), 1);
	static BoCTransaction transactionToString1 = new BoCTransaction();
	static BoCTransaction transactionToString2 = new BoCTransaction("Food", new BigDecimal("10"), 1);
	static BoCTransaction transactionToString3 = new BoCTransaction("Drink", new BigDecimal("100"), 1);
	static BoCTransaction transactionToString4 = new BoCTransaction("Transport", new BigDecimal("1"), 2);

	@BeforeAll
	static void setup() {
		input1 = 0;
		input2 = 1;
	}

	/**
	 * the below function tests whether the method sets category of Transaction
	 * works with input
	 */
	@Test
	void test_Transaction_setTransactionCategory1() {
		/**
		 * the below function tests whether the method sets category of Transaction
		 * works with input in default constructor, expected to return 0
		 */
		BoCTransaction testTransaction = new BoCTransaction();
		int myAnswer = 0;

		testTransaction.setTransactionCategory(input1);
		myAnswer = testTransaction.transactionCategory();
		System.out.println("TestSetTransactionCategory1 -> setTransactoinCategory()");
		System.out.println("Input: Category = " + input1);
		System.out.println("Expected: 0");
		System.out.println("Actual: " + myAnswer);
		assertEquals(0, myAnswer);
		assertTrue(myAnswer == 0);
	}

	@Test
	void test_Transaction_setTransactionCategory2() {
		/**
		 * the below function tests whether the method sets category of Transaction
		 * works with input in default constructor, expected to return 1
		 */
		BoCTransaction testTransaction = new BoCTransaction();
		int myAnswer = 0;

		testTransaction.setTransactionCategory(input2);
		myAnswer = testTransaction.transactionCategory();
		System.out.println("TestSetTransactionCategory2 -> setTransactoinCategory()");
		System.out.println("Input: Category = " + input2);
		System.out.println("Expected: 1");
		System.out.println("Actual: " + myAnswer);
		assertEquals(1, myAnswer);
		assertTrue(myAnswer == 1);
	}

	/**
	 * the below function tests whether the function isComplete of Transaction works
	 * with input
	 */
	@Test
	void test_Transaction_isComplete1() {
		/**
		 * the below function tests whether the function isComplete of Transaction works
		 * with input in main constructor, expected to return false
		 */
		Boolean myAnswer = false;

		myAnswer = transactionIsComplete1.isComplete();
		System.out.println("TestIsComplete1 -> isComplete()");
		System.out.println("Input: Name = " + transactionIsComplete1.transactionName());
		System.out.println("Input: Value = " + transactionIsComplete1.transactionValue());
		System.out.println("Expected: false");
		System.out.println("Actual: " + myAnswer);
		assertEquals(false, myAnswer);
		assertFalse(myAnswer);
	}

	@Test
	void test_Transaction_isComplete2() {
		/**
		 * the below function tests whether the function isComplete of Transaction works
		 * with input in main constructor, expected to return true
		 */
		Boolean myAnswer = false;

		myAnswer = transactionIsComplete2.isComplete();
		System.out.println("TestIsComplete2 -> isComplete()");
		System.out.println("Input: Name = " + transactionIsComplete2.transactionName());
		System.out.println("Input: Value = " + transactionIsComplete2.transactionValue());
		System.out.println("Expected: true");
		System.out.println("Actual: " + myAnswer);
		assertEquals(true, myAnswer);
		assertTrue(myAnswer);
	}

	@Test
	void test_Transaction_isComplete3() {
		/**
		 * the below function tests whether the function isComplete of Transaction works
		 * with input in main constructor, expected to return true
		 */
		Boolean myAnswer = false;

		myAnswer = transactionIsComplete3.isComplete();
		System.out.println("TestIsComplete3 -> isComplete()");
		System.out.println("Input: Name = " + transactionIsComplete3.transactionName());
		System.out.println("Input: Value = " + transactionIsComplete3.transactionValue());
		System.out.println("Expected: true");
		System.out.println("Actual: " + myAnswer);
		assertEquals(true, myAnswer);
		assertTrue(myAnswer);
	}

	@Test
	void test_Transaction_isComplete4() {
		/**
		 * the below function tests whether the function isComplete of Transaction works
		 * with input in main constructor, expected to return true
		 */
		Boolean myAnswer = false;

		myAnswer = transactionIsComplete4.isComplete();
		System.out.println("TestIsComplete4 -> isComplete()");
		System.out.println("Input: Name = " + transactionIsComplete4.transactionName());
		System.out.println("Input: Value = " + transactionIsComplete4.transactionValue());
		System.out.println("Expected: true");
		System.out.println("Actual: " + myAnswer);
		assertEquals(true, myAnswer);
		assertTrue(myAnswer);
	}

	/**
	 * the below function tests whether the function toString of Transaction works
	 * with input
	 */
	@Ignore
	void test_Transaction_toString1() {
		/**
		 * the below function tests whether the function toString of Transaction works
		 * with input in main constructor, expected to return null, but there is an
		 * error of types
		 */
		String myAnswer = "";

		myAnswer = transactionToString1.toString();
		System.out.println("TestToString1 -> toString()");
		System.out.println("Input: Name = " + transactionToString1.transactionName());
		System.out.println("Input: Value = " + transactionToString1.transactionValue());
		System.out.println("Expected: null");
		System.out.println("Actual: " + myAnswer);
		assertEquals(null, myAnswer);
		assertTrue(myAnswer == null);
	}

	@Test
	void test_Transaction_toString2() {
		/**
		 * the below function tests whether the function toString of Transaction works
		 * with input in main constructor, expected to return "Food - ¥10 "+date
		 */
		String myAnswer = "";
		Date date = new Date();

		myAnswer = transactionToString2.toString();
		System.out.println("TestToString2 -> toString()");
		System.out.println("Input: Name = " + transactionToString2.transactionName());
		System.out.println("Input: Value = " + transactionToString2.transactionValue());
		System.out.println("Expected: Food - ¥10 " + date);
		System.out.println("Actual: " + myAnswer);
		assertEquals("Food - ¥10 " + date, myAnswer);
		assertTrue(myAnswer.equals("Food - ¥10 " + date));
	}

	@Test
	void test_Transaction_toString3() {
		/**
		 * the below function tests whether the function toString of Transaction works
		 * with input in main constructor, expected to return "Drink - ¥100 "+date
		 */
		String myAnswer = "";
		Date date = new Date();

		myAnswer = transactionToString3.toString();
		System.out.println("TestToString3 -> toString()");
		System.out.println("Input: Name = " + transactionToString3.transactionName());
		System.out.println("Input: Value = " + transactionToString3.transactionValue());
		System.out.println("Expected: Drink - ¥100 " + date);
		System.out.println("Actual: " + myAnswer);
		assertEquals("Drink - ¥100 " + date, myAnswer);
		assertTrue(myAnswer.equals("Drink - ¥100 " + date));
	}

	@Test
	void test_Transaction_toString4() {
		/**
		 * the below function tests whether the function toString of Transaction works
		 * with input in main constructor, expected to return "Wrong value"
		 */
		String myAnswer = "";
		Date date = new Date();

		myAnswer = transactionToString4.toString();
		System.out.println("TestToString4 -> toString()");
		System.out.println("Input: Name = " + transactionToString4.transactionName());
		System.out.println("Input: Value = " + transactionToString4.transactionValue());
		System.out.println("Expected: Transport - ¥1 " + date);
		System.out.println("Actual: " + myAnswer);
		assertEquals("Transport - ¥1 " + date, myAnswer);
		assertTrue(myAnswer.equals("Transport - ¥1 " + date));
	}
}
