package Lab;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class MathModuleTest {
    static int input1, input2;
    @BeforeAll
    static void setup() {
        input1 = 3;
        input2 = 6; }

    @Test
    void testError() {
        int mulAnswer = 0;
        int divAnswer = 0;

        Exception thrown = assertThrows(
                Exception.class,
                ()->{
                    MathModule.myMultiply(200000000, 1000);
                });
        Exception thrown2 = assertThrows(
                Exception.class,
                ()->{
                    MathModule.myDivision(8, 0);
                });
        assertTrue(thrown.getMessage().contains("Number too big"));
        assertTrue(thrown2.getMessage().contains("Divided by 0"));
        /*try{
            mulAnswer = MathModule.myMultiply(200000000, 1000);
            divAnswer = MathModule.myDivision(0, 0);
        }catch (Exception e){
        }
        fail("it failed");*/
    }
    @Test
    void testFunc(){
        int mulAnswer = 0;
        int divAnswer = 0;
        try {
            assertEquals(18, MathModule.myMultiply(input1, input2));
            assertEquals(0, MathModule.myDivision(input1, input2));
        }catch (Exception e){
            return;
        }
    }
}
