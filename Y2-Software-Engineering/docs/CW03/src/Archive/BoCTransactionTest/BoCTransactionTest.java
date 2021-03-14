import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import java.util.Date;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * This test file tests all the methods in BoCTransaction class they are made by
 * three authors and merged afterwards. Authors are:
 * 
 * @author Wangkai Jin
 * @author Qicheng Chen
 * @author Zhihao Li
 *
 */

public class BoCTransactionTest {

	/**
	 * @author Wangkai JIN the below function tests whether the default constructor
	 *         new an instance which Name = "[Pending Transaction]", Category = 0
	 */
	@Test
	void test_Transaction_DefaultConstructor() {
		BoCTransaction defaultconstructor = new BoCTransaction();
		System.out.println("Defaultconstructor -> new BoCTransaction()");
		System.out.println("Input: No inputs ");
		System.out.println("Expected = [Pending Transaction], Category = 0 ");
		System.out.println("Actual = " + defaultconstructor.transactionName() + ", Category = "
				+ defaultconstructor.transactionCategory());
		System.out.println("");
		assertEquals("[Pending Transaction]", defaultconstructor.transactionName());
	}

	/**
	 * @author Wangkai JIN the below function tests whether the main constructor can
	 *         new an instance in two different situations
	 */
	@Test
	void test_Transaction_MainConstructor() {
		/**
		 * the below function tests whether the main constructor can new an instance
		 * when giving expected input
		 */
		BoCTransaction MainConstructor = new BoCTransaction("Rent", new BigDecimal("850.00"), 0);
		System.out.println("MainConstructor -> new BoCTransaction(\"Rent\", new BigDecimal(\"850.00\"), 0)");
		System.out.println("Input: \"Rent\", new BigDecimal(\"850.00\"), 0 ");
		System.out.println("Expected = Rent, Value = 850.00, Category = 0 ");
		System.out.println("Actual = " + MainConstructor.transactionName() + ", Value = "
				+ MainConstructor.transactionValue() + ", Category = " + MainConstructor.transactionCategory());
		System.out.println("");

		assertTrue(MainConstructor.transactionName().equals("Rent"));
		assertTrue(MainConstructor.transactionValue().compareTo(new BigDecimal("850.00")) == 0);
		assertTrue(MainConstructor.transactionCategory() == 0);

		/**
		 * the below function tests whether the main constructor can new an instance
		 * which has an name input more than 25 characters and cut off the extra
		 * characters
		 */

		BoCTransaction MainConstructor2 = new BoCTransaction("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new BigDecimal("850.00"), 0);
		System.out.println(
				"MainConstructor2 -> new BoCTransaction(\"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", new BigDecimal(\"850.00\"), 0)");
		System.out.println("Input: \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", new BigDecimal(\"850.00\"), 0 ");
		System.out.println("Expected = ABCDEFGHIJKLMNOPQRSTUVWXY ");
		System.out.println("Actual = " + MainConstructor2.transactionName());
		System.out.println("");

		/**
		 * the below function tests whether the main constructor can new an instance
		 * which has an negative value input and handle it
		 */

		BoCTransaction MainConstructor3 = new BoCTransaction("Rent", new BigDecimal("-2"), 0);
		System.out.println("MainConstructor3 -> new BoCTransaction(\"Rent\", new BigDecimal(\"-2\"), 0)");
		System.out.println("Input: \"Rent\", new BigDecimal(\"-2\"), 0 ");
		System.out.println("Expected > 0.00 ");
		System.out.println("Actual = " + MainConstructor3.transactionValue());
		System.out.println("");
		assertTrue(MainConstructor3.transactionName().equals("Rent"));
		assertFalse(MainConstructor3.transactionValue().compareTo(new BigDecimal("0.00")) < 0);
	}

	/**
	 * @author Wangkai JIN the below function tests whether the method to get name
	 *         of Transaction works properly
	 */
	@Test
	void test_Transaction_transactionName() {
		BoCTransaction TestTransactoinName = new BoCTransaction("Rent", new BigDecimal("850.00"), 0);
		System.out.println("TestTransactoinName -> new BoCTransaction(\"Rent\", new BigDecimal(\"850.00\"), 0)");
		System.out.println("Input: \"Rent\", new BigDecimal(\"850.00\"), 0");
		System.out.println("Expected = Rent");
		System.out.println("Actual = " + TestTransactoinName.transactionName());
		System.out.println("");

		assertEquals("Rent", TestTransactoinName.transactionName());
	}

	/**
	 * @author Wangkai JIN the below function tests whether the method to get name
	 *         of Transaction works with input
	 */
	@Test
	void test_Transaction_transactionValue() {
		BoCTransaction TestTransactionValue = new BoCTransaction("Rent", new BigDecimal("850.00"), 1);
		System.out.println("TestTransactionValue -> new BoCTransaction(\"Rent\", new BigDecimal(\"850.00\"), 0)");
		System.out.println("Input: \"Rent\", new BigDecimal(\"850.00\"), 0");
		System.out.println("Expected = 850.00");
		System.out.println("Actual = " + TestTransactionValue.transactionValue());
		System.out.println("");
		assertEquals(new BigDecimal("850.00"), TestTransactionValue.transactionValue());
	}

	/**
	 * @author Qicheng CHEN the below function tests whether the method to get
	 *         category of Transaction works with input
	 */
	@Test
	void test_Transaction_transactionCategory() {

		/**
		 * @author Qicheng CHEN the below function tests whether the method gets
		 *         category of Transaction works with input in default constructor,
		 *         expected to return 0
		 */
		System.out.println("TestTransactionCategory1 -> transactionCategory()");
		BoCTransaction test1 = new BoCTransaction();
		System.out.println("Input: Instantiated with  BoCTransaction()");
		System.out.println("Expected = 0");
		System.out.println("Actual   = " + test1.transactionCategory());
		System.out.println("");
		assertEquals(0, test1.transactionCategory());

		/**
		 * @author Qicheng CHEN the below function tests whether the method gets
		 *         category of Transaction works with input in main constructor,
		 *         expected to return 1
		 */
		System.out.println("TestTransactionCategory2 -> transactionCategory()");
		BoCTransaction test2 = new BoCTransaction("Lunch", new BigDecimal("20"), 1);
		System.out.println("Input: Instantiated with BoCTransaction(\"Lunch\", new BigDecimal(\"20\"), 1)");
		System.out.println("Expected = 1");
		System.out.println("Actual   = " + test2.transactionCategory());
		System.out.println("");
		assertEquals(1, test2.transactionCategory());

		/**
		 * @author Qicheng CHEN the below function tests whether the method gets
		 *         category of Transaction works with input in main constructor,
		 *         expected to return 300
		 */
		System.out.println("TestTransactionCategory3 -> transactionCategory()");
		BoCTransaction test3 = new BoCTransaction("Lunch", new BigDecimal("20"), 300);
		System.out.println("Input: Instantiated with BoCTransaction(\"Lunch\", new BigDecimal(\"20\"), 300)");
		System.out.println("Expected = 300");
		System.out.println("Actual = " + test3.transactionCategory());
		System.out.println("");
		assertEquals(300, test3.transactionCategory());

		/**
		 * @author Qicheng CHEN the below function tests whether the method gets
		 *         category of Transaction works with input in defined main constructor,
		 *         expected to return 0
		 */
		System.out.println("TestTransactionCategory4 -> transactionCategory()");
		BoCTransaction test4 = new BoCTransaction("Dinner", new BigDecimal("20"), 0);
		System.out.println("Input: Instantiated with BoCTransaction(\"Dinner\", new BigDecimal(\"20\"), 0)");
		System.out.println("Expected = 0");
		System.out.println("Actual = " + test4.transactionCategory());
		System.out.println("");
		assertEquals(0, test4.transactionCategory());
	}

	/**
	 * @author Qicheng CHEN the below function tests whether the method to get time
	 *         of Transaction works with input
	 */
	@Test
	void test_Transaction_transactionTime() {
		/**
		 * @author Qicheng CHEN the below function tests whether the method gets time of
		 *         Transaction works with input in default constructor, expected to
		 *         return time created
		 */
		Date date = new Date();
		System.out.println("TestTransactionTime1 -> transactionTime()");
		BoCTransaction test1 = new BoCTransaction();
		System.out.println("Input: Instantiated with BoCTransaction()");
		System.out.println("Expected = " + date);
		System.out.println("Actual   = " + test1.transactionTime());
		System.out.println("");
		assertEquals(date,test1.transactionTime());

		/**
		 * @author Qicheng CHEN the below function tests whether the method gets time of
		 *         Transaction works with input in main constructor, expected to return
		 *         a Date object
		 */
		System.out.println("TestTransactionTime2 -> transactionTime()");
		BoCTransaction test2 = new BoCTransaction("Lunch", new BigDecimal("20"), 1);
		System.out.println("Input: Instantiated with BoCTransaction(\"Lunch\", new BigDecimal(\"20\"), 1)");
		System.out.println("Expected = " + new Date());
		System.out.println("Actual   = " + test2.transactionTime());
		System.out.println("");
		assertThat(test2.transactionTime(), instanceOf(new Date().getClass()));

		/**
		 * @author Qicheng CHEN the below function tests whether the method gets time of
		 *         Transaction works with input in main constructor, expected to return
		 *         a Date object
		 */
		System.out.println("TestTransactionTime3 -> transactionTime()");
		BoCTransaction test3 = new BoCTransaction("Unknown", new BigDecimal("30"), 2);
		System.out.println("Input: Instantiated with BoCTransaction(\"Unknown\", new BigDecimal(\"30\"), 2)");
		System.out.println("Expected = " + new Date());
		System.out.println("Actual   = " + test3.transactionTime());
		System.out.println("");
		assertThat(test3.transactionTime(), instanceOf(new Date().getClass()));
	}

	/**
	 * @author Qicheng CHEN the below function tests whether the method sets name of
	 *         Transaction works with input
	 * 
	 *         tbd
	 * @throws Exception 
	 */
	@Test
	void test_Transaction_setTransactionName() throws Exception {

		/**
		 * @author Qicheng CHEN the below function tests whether the method sets name of
		 *         Transaction works with input in default constructor, transactionName
		 *         is expected to return "Dinner"
		 */
		
		System.out.println("TestSetTransactionName1 -> setTransactionName(\"Dinner\")");
		BoCTransaction test1 = new BoCTransaction();
		test1.setTransactionName("Dinner");
		System.out.println("Input: Instantiated with BoCTransaction()");
		System.out.println("Expected = Dinner");
		System.out.println("Actual   = " + test1.transactionName());
		System.out.println("");
		assertEquals("Dinner", test1.transactionName());

		/**
		 * @author Qicheng CHEN the below function tests whether the method sets name of
		 *         Transaction works with input in default constructor, transactionName
		 *         is expected to return "Unexpected Recovery"
		 */
		System.out.println("TestSetTransactionName2 -> setTransactionName(\"Unexpected Recovery\")");
		BoCTransaction test2 = new BoCTransaction();
		test2.setTransactionName("Unexpected Recovery");
		System.out.println("Input: Instantiated with BoCTransaction()");
		System.out.println("Expected = Unexpected Recovery");
		System.out.println("Actual   = " + test2.transactionName());
		System.out.println("");
		assertEquals("Unexpected Recovery", test2.transactionName());

		/**
		 * @author Qicheng CHEN the below function tests whether the method sets name of
		 *         Transaction works with input in default constructor, transactionName
		 *         is expected to return null
		 */
		System.out.println("TestSetTransactionName3 -> setTransactionName(null)");
		BoCTransaction test3 = new BoCTransaction();
		test3.setTransactionName(null);
		System.out.println("Input: Instantiated with BoCTransaction()");
		System.out.println("Expected = null");
		System.out.println("Actual = " + test3.transactionName());
		System.out.println("");
		assertEquals(null, test3.transactionName());
		/**
		 * @author Qicheng CHEN the below function tests whether the method sets name of
		 *         Transaction works with input in default constructor, transactionName
		 *         is expected to return "1235&^%#$545"
		 */
		System.out.println("TestSetTransactionName4 -> setTransactionName(\"1235&^%#$545\")");
		BoCTransaction test4 = new BoCTransaction();
		test4.setTransactionName("1235&^%#$545");
		System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.\"1235&^%#$545\"");
		System.out.println("Expected = 1235&^%#$545");
		System.out.println("Actual = " + test4.transactionName());
		System.out.println("");
		assertEquals("1235&^%#$545", test4.transactionName());

		/**
		 * @author Qicheng CHEN the below function tests whether the method sets name of
		 *         Transaction works with input in default constructor and then another
		 *         input, expected an exception
		 */
		try {
			BoCTransaction test5 = new BoCTransaction();
			test5.setTransactionName("1235&^%#$545");
			System.out.println(
					"TestSetTransactionName5 -> setTransactionName(\"1235&^%#$545\") then setTransactionName(\"Lunch again\")");
			test5.setTransactionName("Lunch again");
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.\"1235&^%#$545\", 3.\"Lunch again\"");
			System.out.println("Expected = 1235&^%#$545");
			System.out.println("Actual   = " + Exception.class);
			System.out.println("");
			// System.out.println("Actual = " + test5.transactionName());
			// System.out.println("");
			assertThrows(Exception.class, () -> {
				test5.setTransactionName("Lunch again");
			});
			assertEquals("1235&^%#$545", test5.transactionName());
		}catch (Exception e) {
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.\"1235&^%#$545\", 3.\"Lunch again\"");
			System.out.println("Expected = 1235&^%#$545");
			System.out.println("Actual   =" + e.getMessage());
			System.out.println("");
		}
		/**
		 * @author Qicheng CHEN the below function tests whether the method sets name of
		 *         Transaction works with input of more than 25 characters in default
		 *         constructor, transactionName is expected to return first 25
		 *         characters
		 */
		System.out.println(
				"TestSetTransactionName6 -> setTransactionName(\"This is a string that is longer than the limit length which is 25\")");
		BoCTransaction test6 = new BoCTransaction();
		test6.setTransactionName("This is a string that is longer than the limit length which is 25");
		System.out.println(
				"Input: 1.Instantiated with BoCTransaction(), 2.\"This is a string that is longer than the limit length which is 25\"");
		System.out.println("Expected = This is a string that is ");
		System.out.println("Actual = " + test6.transactionName());
		System.out.println("");
		assertEquals("This is a string that is ", test6.transactionName());
	
	}

	/**
	 * @author Qicheng CHEN the below function tests whether the method sets value
	 *         of Transaction works with input
	 */
	@Test
	void test_Transaction_setTransactionValue() {
		try {
			/**
			 * @author Qicheng CHEN the below function tests whether the method sets value
			 *         of Transaction works with input in default constructor, expected to
			 *         return 200
			 */
			System.out.println("TestSetTransactionValue1 -> setTransactionValue(new BigDecimal(\"200\")");
			BoCTransaction test1 = new BoCTransaction();
			test1.setTransactionValue(new BigDecimal("200"));
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"200\")");
			System.out.println("Expected = 200");
			System.out.println("Actual   = " + test1.transactionValue());
			System.out.println("");
			assertEquals(new BigDecimal("200"), test1.transactionValue());

			/**
			 * @author Qicheng CHEN the below function tests whether the method sets value
			 *         of Transaction works with input in default constructor, expected to
			 *         return 3.213
			 */
			System.out.println("TestSetTransactionValue2 -> setTransactionValue(new BigDecimal(\"3.213\")");
			BoCTransaction test2 = new BoCTransaction();
			test2.setTransactionValue(new BigDecimal("3.213"));
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"3.213\")");
			System.out.println("Expected = 3.213");
			System.out.println("Actual = " + test2.transactionValue());
			System.out.println("");
			assertEquals(new BigDecimal("3.213"), test2.transactionValue());

			/**
			 * @author Qicheng CHEN the below function tests whether the method sets value
			 *         of Transaction works with input in default constructor, expected to
			 *         return 45321897
			 */
			System.out.println("TestSetTransactionValue3 -> setTransactionValue(new BigDecimal(\"45321897\")");
			BoCTransaction test3 = new BoCTransaction();
			test3.setTransactionValue(new BigDecimal("45321897"));
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"45321897\")");
			System.out.println("Expected = 45321897");
			System.out.println("Actual = " + test3.transactionValue());
			System.out.println("");
			assertEquals(new BigDecimal("45321897"), test3.transactionValue());

			/**
			 * @author Qicheng CHEN the below function tests whether the method sets value
			 *         of Transaction works with input in default constructor, expected to
			 *         return null
			 */
			System.out.println("TestSetTransactionValue4 -> setTransactionValue(new BigDecimal(\"-500\")");
			BoCTransaction test4 = new BoCTransaction();
			test4.setTransactionValue(new BigDecimal("-500"));
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"-500\")");
			System.out.println("Expected = null");
			System.out.println("Actual   = " + test4.transactionValue());
			System.out.println("");

			assertNull(test4.transactionValue());

			/**
			 * @author Qicheng CHEN the below function tests whether the method sets value
			 *         of Transaction works with input in default constructor and then
			 *         another input, expected an exception
			 */
			System.out.println(
					"TestSetTransactionValue5 -> setTransactionValue(new BigDecimal(\"200\") then setTransactionValue(new BigDecimal(\"1000\")");
			BoCTransaction test5 = new BoCTransaction();
			System.out.println(
					"Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"200\"), 3.new BigDecimal(\"1000\")");
			System.out.println("Expected = The value has already been set and can only be set once!");
			System.out.print("Actual   = ");
			test5.setTransactionValue(new BigDecimal("200"));
			test5.setTransactionValue(new BigDecimal("1000"));
			System.out.println("");
			assertEquals(new BigDecimal("200"), test5.transactionValue());
			assertThrows(Exception.class, () -> {
				test5.setTransactionValue(new BigDecimal("1000"));

			});
			System.out.println("Actual output: " + Exception.class);
			System.out.println("");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("");
		}
	}

	/**
	 * 
	 * @author Zhihao LI This test class tests the method for Transaction class, the
	 *         methods including setTransactionCategory() isComplete() and
	 *         toString()
	 *
	 */
	/**
	 * @author Zhihao LI the below function tests whether the method sets category
	 *         of Transaction works with input
	 */
	@Test
	void test_Transaction_setTransactionCategory1() {
		/**
		 * @author Zhihao LI the below function tests whether the method sets category
		 *         of Transaction works with input in default constructor, expected to
		 *         return 0
		 */
		BoCTransaction testTransaction = new BoCTransaction();
		int myAnswer = 0;
		int input1 = 0;

		testTransaction.setTransactionCategory(input1);
		myAnswer = testTransaction.transactionCategory();
		System.out.println("TestSetTransactionCategory1 -> setTransactoinCategory()");
		System.out.println("Input: Category = " + input1);
		System.out.println("Expected = 0");
		System.out.println("Actual   = " + myAnswer);
		assertEquals(0, myAnswer);
		assertTrue(myAnswer == 0);
		System.out.println("");
	}

	@Test
	void test_Transaction_setTransactionCategory2() {
		/**
		 * @author Zhihao LI the below function tests whether the method sets category
		 *         of Transaction works with input in default constructor, expected to
		 *         return 1
		 */
		BoCTransaction testTransaction = new BoCTransaction();
		int myAnswer = 0;
		int input2 = 1;

		testTransaction.setTransactionCategory(input2);
		myAnswer = testTransaction.transactionCategory();
		System.out.println("TestSetTransactionCategory2 -> setTransactoinCategory()");
		System.out.println("Input: Category = " + input2);
		System.out.println("Expected = 1");
		System.out.println("Actual   = " + myAnswer);
		assertEquals(1, myAnswer);
		assertTrue(myAnswer == 1);
		System.out.println("");
	}

	/**
	 * @author Zhihao LI the below function tests whether the function isComplete of
	 *         Transaction works with input
	 */
	@Test
	void test_Transaction_isComplete1() {
		/**
		 * @author Zhihao LI the below function tests whether the function isComplete of
		 *         Transaction works with input in main constructor, expected to return
		 *         false
		 */
		Boolean myAnswer = false;
		BoCTransaction transactionIsComplete1 = new BoCTransaction();

		myAnswer = transactionIsComplete1.isComplete();
		System.out.println("TestIsComplete1 -> isComplete()");
		System.out.print("Input: Name = " + transactionIsComplete1.transactionName());
		System.out.println(", Value = " + transactionIsComplete1.transactionValue());
		System.out.println("Expected = false");
		System.out.println("Actual = " + myAnswer);
		assertEquals(false, myAnswer);
		assertFalse(myAnswer);
		System.out.println("");
	}

	@Test
	void test_Transaction_isComplete2() {
		/**
		 * @author Zhihao LI the below function tests whether the function isComplete of
		 *         Transaction works with input in main constructor, expected to return
		 *         true
		 */
		Boolean myAnswer = false;
		BoCTransaction transactionIsComplete2 = new BoCTransaction("Food", new BigDecimal("10"), 1);

		myAnswer = transactionIsComplete2.isComplete();
		System.out.println("TestIsComplete2 -> isComplete()");
		System.out.print("Input: Name = " + transactionIsComplete2.transactionName());
		System.out.println(", Value = " + transactionIsComplete2.transactionValue());
		System.out.println("Expected = true");
		System.out.println("Actual = " + myAnswer);
		assertEquals(true, myAnswer);
		assertTrue(myAnswer);
		System.out.println("");
	}

	@Test
	void test_Transaction_isComplete3() {
		/**
		 * @author Zhihao LI the below function tests whether the function isComplete of
		 *         Transaction works with input in main constructor, expected to return
		 *         true
		 */
		Boolean myAnswer = false;
		BoCTransaction transactionIsComplete3 = new BoCTransaction("Breakfast", new BigDecimal("3000"), 1);

		myAnswer = transactionIsComplete3.isComplete();
		System.out.println("TestIsComplete3 -> isComplete()");
		System.out.print("Input: Name = " + transactionIsComplete3.transactionName());
		System.out.println(", Value = " + transactionIsComplete3.transactionValue());
		System.out.println("Expected = true");
		System.out.println("Actual = " + myAnswer);
		System.out.println("");
		assertEquals(true, myAnswer);
	}

	@Test
	void test_Transaction_isComplete4() {
		/**
		 * @author Zhihao LI the below function tests whether the function isComplete of
		 *         Transaction works with input in main constructor, expected to return
		 *         true
		 */
		Boolean myAnswer = false;
		BoCTransaction transactionIsComplete4 = new BoCTransaction("Food", new BigDecimal("0.5"), 1);

		myAnswer = transactionIsComplete4.isComplete();
		System.out.println("TestIsComplete4 -> isComplete()");
		System.out.print("Input: Name = " + transactionIsComplete4.transactionName());
		System.out.println(", Value = " + transactionIsComplete4.transactionValue());
		System.out.println("Expected = true");
		System.out.println("Actual = " + myAnswer);
		assertEquals(true, myAnswer);
		assertTrue(myAnswer);
		System.out.println("");
	}

	/**
	 * @author Zhihao LI the below function tests whether the function toString of
	 *         Transaction works with input
	 */
	@Test
	void test_Transaction_toString1() {
		/**
		 * @author Zhihao LI the below function tests whether the function toString of
		 *         Transaction works with input in main constructor, expected to return
		 *         "[Pending Transaction] - ¥0.00 "+date
		 */
		String myAnswer = "";
		Date date = new Date();
		BoCTransaction transactionToString1 = new BoCTransaction();

		myAnswer = transactionToString1.toString();
		System.out.println("TestToString1 -> toString()");
		System.out.print("Input: Name = " + transactionToString1.transactionName());
		System.out.println(", Value = " + transactionToString1.transactionValue());
		System.out.println("Expected = [Pending Transaction] - ¥0.00 " + date);
		System.out.println("Actual = " + myAnswer);
		assertEquals("[Pending Transaction] - ¥0.00 " + date, myAnswer);
		assertTrue(myAnswer.equals("[Pending Transaction] - ¥0.00 " + date));
		System.out.println("");
	}

	@Test
	void test_Transaction_toString2() {
		/**
		 * @author Zhihao LI the below function tests whether the function toString of
		 *         Transaction works with input in main constructor, expected to return
		 *         "Food - ¥10 "+date
		 */
		String myAnswer = "";
		Date date = new Date();
		BoCTransaction transactionToString2 = new BoCTransaction("Food", new BigDecimal("10"), 1);

		myAnswer = transactionToString2.toString();
		System.out.println("TestToString2 -> toString()");
		System.out.print("Input: Name = " + transactionToString2.transactionName());
		System.out.println(", Value = " + transactionToString2.transactionValue());
		System.out.println("Expected = Food - ¥10 " + date);
		System.out.println("Actual = " + myAnswer);
		assertEquals("Food - ¥10 " + date, myAnswer);
		assertTrue(myAnswer.equals("Food - ¥10 " + date));
		System.out.println("");
	}

	@Test
	void test_Transaction_toString3() {
		/**
		 * @author Zhihao LI the below function tests whether the function toString of
		 *         Transaction works with input in main constructor, expected to return
		 *         "Drink - ¥100 "+date
		 */
		String myAnswer = "";
		Date date = new Date();
		BoCTransaction transactionToString3 = new BoCTransaction("Drink", new BigDecimal("100"), 1);

		myAnswer = transactionToString3.toString();
		System.out.println("TestToString3 -> toString()");
		System.out.print("Input: Name = " + transactionToString3.transactionName());
		System.out.println(", Value = " + transactionToString3.transactionValue());
		System.out.println("Expected = Drink - ¥100 " + date);
		System.out.println("Actual = " + myAnswer);
		assertEquals("Drink - ¥100 " + date, myAnswer);
		assertTrue(myAnswer.equals("Drink - ¥100 " + date));
		System.out.println("");
	}

	@Test
	void test_Transaction_toString4() {
		/**
		 * @author Zhihao LI the below function tests whether the function toString of
		 *         Transaction works with input in main constructor, expected to return
		 *         "Transport - ¥1 " + date
		 */
		String myAnswer = "";
		Date date = new Date();
		BoCTransaction transactionToString4 = new BoCTransaction("Transport", new BigDecimal("1"), 2);

		myAnswer = transactionToString4.toString();
		System.out.println("TestToString4 -> toString()");
		System.out.print("Input: Name = " + transactionToString4.transactionName());
		System.out.println(", Value = " + transactionToString4.transactionValue());
		System.out.println("Expected = Transport - ¥1 " + date);
		System.out.println("Actual = " + myAnswer);
		assertEquals("Transport - ¥1 " + date, myAnswer);
		assertTrue(myAnswer.equals("Transport - ¥1 " + date));
		System.out.println("");
	}
}