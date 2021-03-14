import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Qicheng CHEN
 * This test class tests the method for transactionCategory, transactionTime,
 * setTransactionName and setTransactionValue
 */
class Test_Transaction_part2 {
	/**
	 * the below function tests whether the method to get category of Transaction
	 * works with input 
	 */
	@Test
	void testTransactionCategory() {

		/**
		 * the below function tests whether the method gets category of Transaction
		 * works with input in default constructor, expected to return 0
		 */
		System.out.println("TestTransactionCategory1 -> transactionCategory()");
		BoCTransaction test1 = new BoCTransaction();
		System.out.println("Input: Instantiated with  BoCTransaction()");
		System.out.println("Expected output: 0");
		System.out.println("Actual output: " + test1.transactionCategory());
		System.out.println("");
		assertEquals(0, test1.transactionCategory());

		
		/**
		 * the below function tests whether the method gets category of Transaction
		 * works with input in main constructor, expected to return 1
		 */
		System.out.println("TestTransactionCategory2 -> transactionCategory()");
		BoCTransaction test2 = new BoCTransaction("Lunch", new BigDecimal("20"), 1);
		System.out.println("Input: Instantiated with BoCTransaction(\"Lunch\", new BigDecimal(\"20\"), 1)");
		System.out.println("Expected output: 1");
		System.out.println("Actual output: " + test2.transactionCategory());
		System.out.println("");
		assertEquals(1, test2.transactionCategory());
		/**
		 * the below function tests whether the method gets category of Transaction
		 * works with input in main constructor, expected to return 300
		 */
		System.out.println("TestTransactionCategory3 -> transactionCategory()");
		BoCTransaction test3 = new BoCTransaction("Lunch", new BigDecimal("20"), 300);
		System.out.println("Input: Instantiated with BoCTransaction(\"Lunch\", new BigDecimal(\"20\"), 300)");
		System.out.println("Expected output: 300");
		System.out.println("Actual output: " + test3.transactionCategory());
		System.out.println("");
		assertEquals(300, test3.transactionCategory());
				
		/**
		 * the below function tests whether the method gets category of Transaction
		 * works with input in defined main constructor, expected to return 0
		 */
		System.out.println("TestTransactionCategory4 -> transactionCategory()");
		BoCTransaction test4 = new BoCTransaction("Dinner", new BigDecimal("20"), 0);
		System.out.println("Input: Instantiated with BoCTransaction(\"Dinner\", new BigDecimal(\"20\"), 0)");
		System.out.println("Expected output: 0");
		System.out.println("Actual output: " + test4.transactionCategory());
		System.out.println("");
		assertEquals(0, test4.transactionCategory());
		System.out.println("------------------------------------------------------------------------------");
	}
	
	/**
	 * the below function tests whether the method to get time of Transaction
	 * works with input 
	 */
	@Test
	void testTransactionTime() {
		/**
		 * the below function tests whether the method gets time of Transaction
		 * works with input in default constructor, expected to return null
		 */
		System.out.println("TestTransactionTime1 -> transactionTime()");
		BoCTransaction test1 = new BoCTransaction();
		System.out.println("Input: Instantiated with BoCTransaction()");
		System.out.println("Expected output: null");
		System.out.println("Actual output: " + test1.transactionTime());
		System.out.println("");
		assertNull(test1.transactionTime());
		
		/**
		 * the below function tests whether the method gets time of Transaction
		 * works with input in main constructor, expected to return a Date object
		 */
		System.out.println("TestTransactionTime2 -> transactionTime()");
		BoCTransaction test2 = new BoCTransaction("Lunch", new BigDecimal("20"), 1);
		System.out.println("Input: Instantiated with BoCTransaction(\"Lunch\", new BigDecimal(\"20\"), 1)");
		System.out.println("Expected output: Any date object");
		System.out.println("Actual output: " + test2.transactionTime());
		System.out.println("");
		assertThat(test2.transactionTime(), instanceOf(new Date().getClass()));
		
		/**
		 * the below function tests whether the method gets time of Transaction
		 * works with input in main constructor, expected to return a Date object
		 */
		System.out.println("TestTransactionTime3 -> transactionTime()");
		BoCTransaction test3 = new BoCTransaction("Unknown", new BigDecimal("30"), 2);
		System.out.println("Input: Instantiated with BoCTransaction(\"Unknown\", new BigDecimal(\"30\"), 2)");
		System.out.println("Expected output: Any date object");
		System.out.println("Actual output: " + test3.transactionTime());
		System.out.println("");
		assertThat(test3.transactionTime(), instanceOf(new Date().getClass()));
	}
	
	/**
	 * the below function tests whether the method sets time of Transaction
	 * works with input
	 */
	@Test
	void testSetTransactionName() {
		try {
			/**
			 * the below function tests whether the method sets name of Transaction
			 * works with input in default constructor, 
			 * transactionName is expected to return "Dinner"
			 */
			System.out.println("TestSetTransactionName1 -> setTransactionName(\"Dinner\")");
			BoCTransaction test1 = new BoCTransaction();
			test1.setTransactionName("Dinner");
			System.out.println("Input: Instantiated with BoCTransaction()");
			System.out.println("Expected output: Name = Dinner");
			System.out.println("Actual output: Name = " + test1.transactionName());
			System.out.println("");
			assertEquals("Dinner", test1.transactionName());
			
			/**
			 * the below function tests whether the method sets name of Transaction
			 * works with input in default constructor, 
			 * transactionName is expected to return "Unexpected Recovery"
			 */
			System.out.println("TestSetTransactionName2 -> setTransactionName(\"Unexpected Recovery\")");
			BoCTransaction test2 = new BoCTransaction();
			test2.setTransactionName("Unexpected Recovery");
			System.out.println("Input: Instantiated with BoCTransaction()");
			System.out.println("Expected output: Name = Unexpected Recovery");
			System.out.println("Actual output: Name = " + test2.transactionName());
			System.out.println("");
			assertEquals("Unexpected Recovery", test2.transactionName());
			
			/**
			 * the below function tests whether the method sets name of Transaction
			 * works with input in default constructor, 
			 * transactionName is expected to return null
			 */
			System.out.println("TestSetTransactionName3 -> setTransactionName(null)");
			BoCTransaction test3 = new BoCTransaction();
			test3.setTransactionName(null);
			System.out.println("Input: Instantiated with BoCTransaction()");
			System.out.println("Expected output: Name = null");
			System.out.println("Actual output: Name = " + test3.transactionName());
			System.out.println("");
			//System.out.println("good");
			//assertEquals(null,test3.transactionName());
			//System.out.println("good");
			
			/**
			 * the below function tests whether the method sets name of Transaction
			 * works with input in default constructor, 
			 * transactionName is expected to return "1235&^%#$545"
			 */
			System.out.println("TestSetTransactionName4 -> setTransactionName(\"1235&^%#$545\")");
			BoCTransaction test4 = new BoCTransaction();
			test4.setTransactionName("1235&^%#$545");
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.\"1235&^%#$545\"");
			System.out.println("Expected output: Name = 1235&^%#$545");
			System.out.println("Actual output: Name = " + test4.transactionName());
			System.out.println("");
			assertEquals("1235&^%#$545", test4.transactionName());

			/**
			 * the below function tests whether the method sets name of Transaction
			 * works with input in default constructor and then another input, 
			 * expected an exception
			 */
			BoCTransaction test5 = new BoCTransaction();
			test5.setTransactionName("1235&^%#$545");
			System.out.println("TestSetTransactionName5 -> setTransactionName(\"1235&^%#$545\") then setTransactionName(\"Lunch again\")");
			test5.setTransactionName("Lunch again");
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.\"1235&^%#$545\", 3.\"Lunch again\"");
			System.out.println("Expected output: 1235&^%#$545");
			System.out.println("Actual output: "+test5.transactionName());
			assertEquals("1235&^%#$545",test5.transactionName());
			System.out.println("");

			/**
			 * the below function tests whether the method sets name of Transaction
			 * works with input of more than 25 characters in default constructor, 
			 * transactionName is expected to return first 25 characters
			 */
			System.out.println("TestSetTransactionName6 -> setTransactionName(\"This is a string that is longer than the limit length which is 25\")");
			BoCTransaction test6 = new BoCTransaction();
			test6.setTransactionName("This is a string that is longer than the limit length which is 25");
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.\"This is a string that is longer than the limit length which is 25\"");
			System.out.println("Expected output: Name = This is a string that is ");
			System.out.println("Actual output: Name = " + test6.transactionName());
			System.out.println("");
			//assertEquals("This is a string that is ", test6.transactionName());
		} catch (Exception e) {
			System.out.println("Error");
		}
	}
	
	
	/**
	 * the below function tests whether the method sets value of Transaction
	 * works with input
	 */
	@Test
	void testSetTransactionValue() {
		try {
			/**
			 * the below function tests whether the method sets value of Transaction
			 * works with input in default constructor, expected to return 200
			 */
			System.out.println("TestSetTransactionValue1 -> setTransactionValue(new BigDecimal(\"200\")");
			BoCTransaction test1 = new BoCTransaction();
			test1.setTransactionValue(new BigDecimal("200"));
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"200\")");
			System.out.println("Expected output: TestSetTransactionValue1.transactionValue() ---> 200");
			System.out.println("Actual output: TestSetTransactionValue1.transactionValue() ---> " + test1.transactionValue());
			System.out.println("");
			assertEquals(new BigDecimal("200"), test1.transactionValue());
			
			/**
			 * the below function tests whether the method sets value of Transaction
			 * works with input in default constructor, expected to return 3.213
			 */
			System.out.println("TestSetTransactionValue2 -> setTransactionValue(new BigDecimal(\"3.213\")");
			BoCTransaction test2 = new BoCTransaction();
			test2.setTransactionValue(new BigDecimal("3.213"));
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"200\")");
			System.out.println("Expected output: TestSetTransactionValue2.transactionValue() ---> 3.213");
			System.out.println("Actual output: TestSetTransactionValue2.transactionValue() ---> " + test2.transactionValue());
			System.out.println("");
			
			assertEquals(new BigDecimal("3.213"), test2.transactionValue());
			
			/**
			 * the below function tests whether the method sets value of Transaction
			 * works with input in default constructor, expected to return 45321897
			 */
			System.out.println("TestSetTransactionValue3 -> setTransactionValue(new BigDecimal(\"45321897\")");
			BoCTransaction test3 = new BoCTransaction();
			test3.setTransactionValue(new BigDecimal("45321897"));
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"45321897\")");
			System.out.println("Expected output: TestSetTransactionValue3.transactionValue() ---> 45321897");
			System.out.println("Actual output: TestSetTransactionValue3.transactionValue() ---> " + test3.transactionValue());
			System.out.println("");
			
			assertEquals(new BigDecimal("45321897"), test3.transactionValue());
			
			/**
			 * the below function tests whether the method sets value of Transaction
			 * works with input in default constructor, expected to return null
			 */
			System.out.println("TestSetTransactionValue4 -> setTransactionValue(new BigDecimal(\"-500\")");
			BoCTransaction test4 = new BoCTransaction();
			test4.setTransactionValue(new BigDecimal("-500"));
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"-500\")");
			System.out.println("Expected output: TestSetTransactionValue4.transactionValue() ---> null");
			System.out.println("Actual output: TestSetTransactionValue4.transactionValue() ---> " + test4.transactionValue());
			System.out.println("");
			
			assertNull(test4.transactionValue());
			
			/**
			 * the below function tests whether the method sets value of Transaction
			 * works with input in default constructor and then another input, 
			 * expected an exception
			 */
			System.out.println("TestSetTransactionValue5 -> setTransactionValue(new BigDecimal(\"200\") then setTransactionValue(new BigDecimal(\"1000\")");
			BoCTransaction test5 = new BoCTransaction();
			System.out.println("Input: 1.Instantiated with BoCTransaction(), 2.new BigDecimal(\"200\"), 3.new BigDecimal(\"1000\")");
			System.out.println("Expected output: The value has already been set and can only be set once!");
			System.out.print("Actual output: ");
			test5.setTransactionValue(new BigDecimal("200"));
			test5.setTransactionValue(new BigDecimal("1000"));
			assertEquals(200,test5.transactionValue());
			System.out.println("");
		} catch (Exception e) {
			System.out.println("Other Error");
		}
	}

}
