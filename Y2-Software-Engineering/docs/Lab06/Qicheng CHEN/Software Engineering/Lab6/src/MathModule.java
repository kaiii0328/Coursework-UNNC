public class MathModule {
    public static void main(String[] args) {
    // TODO Auto-generated method stub
        MathModule Test = new MathModule();
        try{
            int test = Test.myMultiply(2000000000, 6);
            System.out.println(test);
        } catch (Exception e){
            System.out.println(e.toString());
        }

    }
    public static int myMultiply(int firstNum, int secondNum) throws Exception {
        long newAnswer = (long) firstNum * secondNum;
        if(newAnswer > Integer.MAX_VALUE){
            throw new Exception("Number too large!");
        }
        return firstNum * secondNum;
    }

    public static int division(int a, int b) throws Exception {
        if(b == 0){
            throw new ArithmeticException("Divide by Zero Error!");
        }
        return a / b;
    }
}
