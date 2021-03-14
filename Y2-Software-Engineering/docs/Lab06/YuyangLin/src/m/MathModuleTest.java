package m;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

 public class MathModuleTest {

     private static int input1, input2, input3, input4;

     @BeforeAll
     static void setup() {
         input1 = 3;
         input2 = 6;
         input3 = 2000000000;
         input4 = -2000000000;
     }

     @Ignore
     void myMultiply() {
         fail("It is not implemented");
     }

     @Test
     void test1() {
         int myAnswer = 0;
         try {
             myAnswer = MathModule.myMultiply(input1, input2);
         } catch (Exception e) {}
         assertEquals(18, myAnswer);
     }

     @Test
     void testError() {
         int myAnswer = 0;
         try {
             myAnswer = MathModule.myMultiply(input2, input3);
         } catch (Exception e) {
             if (e.getClass() == Exception.class) {
                 return; // it passed
             }
         }
         fail("it failed");
     }

     @Test
     void testError2() {
         int myA = 0;
         try {
             myA = MathModule.myMultiply(input2, input4);
         } catch (Exception e) {
             if (e.getClass() == Exception.class) {
                 return;
             }
         }
         fail("it failed");
     }

     @Test
     void test2() {
         int ans = 0;
         try {
            ans = MathModule.myDiv(input2, input1);
         } catch (Exception e) {}
         assertEquals(2, ans);
     }

     @Test
     void test3() {
         double ans = 0;
         try {
            ans = MathModule.myDiv(7, 3);
         } catch (Exception e) {}
         assertEquals((double)(7/3), ans);                       
     }

     @Test
      void testError3() {
          int myA = 0;
          try {
              myA = MathModule.myDiv(input1, 0);
          } catch (Exception e) {
              if (e.getClass() == Exception.class) {
                  return;
              }
          }
          fail("it failed");
      }
}