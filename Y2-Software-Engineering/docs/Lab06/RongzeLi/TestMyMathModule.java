import static org.junit.jupiter.api.Assertions.*;

import org.junit.*;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestMyMathModule {
	private static int input1;
	private static int input2;
	private static int input3;
	private static int input4;
	private static int input5;
	private static int input6;
	
	@BeforeAll
	static void setup() {
		input1 = 3;
		input2 = 6;
		input3 = 2000000000;
		input4 = -2000000000;
		input5 = 7;
		input6 = 0;
	}
	
	@Ignore
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void test1() {
		int myAnswer = 0;
		try {
		myAnswer = MathModule.myMultiply(input1, input2);
		} catch(Exception e) {}
		assertEquals(18, myAnswer);
	}
	
	@Test
	void testError1() {
		int myAnswer = 0;
		try {
			myAnswer = MathModule.myMultiply(input2,input3);
		} catch(Exception e) {
		if(e.getClass() == Exception.class) {
				return; // it passed
			}
		}
		assertThrows(Exception.class, () -> {
			MathModule.myMultiply(input2,input3);
		});
		fail("it failed");
	}
	
	@Test
	void testError2() {
		int myAnswer = 0;
		try {
			myAnswer = MathModule.myMultiply(input2,input4);
		} catch(Exception e) {
			if(e.getClass() == Exception.class) {
				return; // it passed
			}
		}
		assertThrows(Exception.class, () -> {
			MathModule.myMultiply(input2,input4);
		});
		fail("it failed");
	}
	
	@Test
	void testDivision1() {
		float myAnswer=0;
		try {
		myAnswer=MathModule.myDivision(input2, input1);
		}catch(Exception e) {}
		assertEquals(2,myAnswer);
	}
	
	@Test
	void testDivision2() {
		float myAnswer=0;
		try {
		myAnswer=MathModule.myDivision(input5, input1);
		}catch(Exception e) {}
//		System.out.println(myAnswer);
		assertEquals((float)7/3,myAnswer);
	}
	
	@Test
	void testDivisionError() {
		float myAnswer=0;
		try {
		myAnswer=MathModule.myDivision(input1, input6);
		}catch(Exception e) {
			if(e.getClass() == Exception.class) {
				return; // it passed
			}
		}
		assertThrows(Exception.class, () -> {
			MathModule.myDivision(input1,input6);
		});
		fail("it failed");
	}
	
}
