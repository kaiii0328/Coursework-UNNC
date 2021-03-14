import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathModuleTest {
    private static int input1;
    private static int input2;
    private static int input3;

    @BeforeAll
    static void setup() {
        input1 = 3;
        input2 = 6;
        input3 = 2000000000;
    }

    @Ignore
    public void testMathModule() {
        fail();
    }

    @Test
    public void test1() {
        int myAnswer = 0;
        try {
            myAnswer = MathModule.myMultiply(input1, input2);
            assertEquals(18, myAnswer);
        } catch (Exception e){

        }

    }

    @Test
    public void testError() {
        int myAnswer = 0;
        assertThrows(Exception.class, () -> {
            MathModule.myMultiply(input2, input3);
        });


        /*try {
            int myAnswer = MathModule.myMultiply(intput2, input3);
        } catch (Exception e){
            if(e.getClass() == Exception.class) {
                return; // It passed
            }
        }
        fail("It failed");*/
    }

    @Test
    public void testDivision(){
        try{
            int answer = MathModule.division(input1, input2);
            assertEquals(0, answer);
        } catch (Exception e){
            // If there is an error
            assertThrows(ArithmeticException.class, ()->{
                MathModule.division(input1, input2);
            });
        }


    }

    @Test
    public void testDivisionError() {

        assertThrows(ArithmeticException.class, ()->{
            MathModule.division(input1, 0);
        });
    }

}