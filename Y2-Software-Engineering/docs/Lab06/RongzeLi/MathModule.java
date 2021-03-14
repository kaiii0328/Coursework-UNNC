
public class MathModule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		try {
			int test = myMultiply(-2000000000, 6);
			System.out.println(test);
			} catch(Exception e) {
			System.out.println(e.toString()); //print out the exception
			}
		
		try {
			float test2 = myDivision(1,0);// 1/0
			System.out.println(test2);
		} catch (Exception ex) {
			System.out.println(ex.toString());//print out the exception
		}
		
		}
		public static int myMultiply(int firstNum, int secondNum) throws Exception {
			long newAnswer = (long)firstNum * secondNum;
			if (newAnswer > Integer.MAX_VALUE) {
			throw new Exception("Number too big");// out of range
			}
			if (newAnswer < Integer.MIN_VALUE) {
				throw new Exception("Number too small");// out of range
			}
			return firstNum * secondNum;}
		
		public static float myDivision(int firstNum, int secondNum) throws Exception {
			float newAnswer=0;
			if(secondNum==0) {
				throw new Exception("divisor is 0");
			}
			newAnswer=(float) firstNum/secondNum;
			return newAnswer;
		}
 
	
}
