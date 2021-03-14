import static org.junit.jupiter.api.Assertions.*;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class TestMyMathModule {
	private static int input1,input2,input3;
	@BeforeAll 
	static void setup() {  input1 = 3;  input2 = 6; input3 =2000000000 ;} 
	 
	@Ignore
	void test() {
		fail("Not yet implemented");
	}
	@Test 
	void test1() {  
		int myAnswer = 0;
		try { 
			myAnswer = MathModule.myMultiply(input1, input2);
			} catch(Exception e) { }
		assertEquals(18, myAnswer); 
	} 
	@Test
	void testError() { 
		int myAnswer = 0; 
		try {   
			myAnswer = MathModule.myMultiply(input2, input3); 
			} catch(Exception e) {   
				if(e.getClass() == Exception.class) {   
					return; // it passed   
					} 
				}
		fail("it failed");
			}
	
	@Test
	void test2() {
		int myAnswer=0;
		try{
			myAnswer = MathModule.divide(input2, 0);
		}catch(Exception e) {
			if (e.getClass()==Exception.class)
				return;
		}
		assertEquals(2,myAnswer);
	}
}
