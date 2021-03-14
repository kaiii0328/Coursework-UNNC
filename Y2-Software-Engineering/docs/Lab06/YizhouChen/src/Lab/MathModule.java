package Lab;
//import java.lang.Exception;

public class MathModule {
    public static void main(String[] args) {
        //TODO auto-generated method stub
        try{
            System.out.println(myMultiply(2000000000, 6));
            System.out.println(myDivision(8,0));
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }
    public static int myMultiply(int firstNum, int secondNum) throws Exception {
        long newAnswer = (long) firstNum*secondNum;
        if(newAnswer>Integer.MAX_VALUE){
            throw new Exception("Number too big");
        }
        else if(newAnswer<Integer.MIN_VALUE){
            throw new Exception("Number too small");
        }
        return firstNum*secondNum;
    }
    public static int myDivision(int firstNum, int secondNum) throws Exception{
        if(secondNum == 0){
            throw new Exception("Divided by 0");
        }
        return firstNum/secondNum;
    }
}
