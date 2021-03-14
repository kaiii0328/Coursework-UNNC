public class MathModule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		try {  
			int test = myMultiply(2000000000, 6);  
			System.out.println(test); 
			} catch(Exception e) { 
				System.out.println(e.toString()); } 
		 
	}
	public static int myMultiply(int firstNum, int secondNum) throws Exception { 
		long newAnswer = (long)firstNum * secondNum; 
		if (newAnswer > Integer.MAX_VALUE) {  
			throw new Exception("Number too big"); } 
		return firstNum * secondNum;
		}
	public static int divide(int first,int second) throws Exception {
		if (second==0)
			throw new Exception("divisor can't be zero!");
		return first/second;
	}
}
