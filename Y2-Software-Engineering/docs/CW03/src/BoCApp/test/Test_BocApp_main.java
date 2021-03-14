package test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Test_BocApp_main {
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@BeforeAll
    static void setup(){//
        System.setOut(new PrintStream(outContent));
    }

    /**
     * @author Rongze LI
     * Senario: Input an "X" to exit
     * @throws Exception
     */
	@Test
	public void Test_App_Main1() throws Exception {
		Method m = BoCApp.class.getDeclaredMethod("main",String[].class);
        m.setAccessible(true);
        // set input
        String input = "X" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        // call the main method
        String[] args = null;
        BoCApp.main(args);
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" + System.lineSeparator() +
                "\nWhat do you want to do?\n" +
                " T = List All [T]ransactions, [num] = Show Category [num], A = [A]dd Transaction, X = E[x]it" +
                System.lineSeparator()+"Goodbye!"+System.lineSeparator(), outContent.toString());
        
		
	}
    /*
     * Test by Yuyang LIN
     * Pass(Date:05/06/2020)
     * */

    /**
     * @author Rongze LI
     * Senario: Input a "T" to see list transaction, and an "X" to exit
     * @throws Exception
     */
	@Test
	public void Test_App_Main2() throws Exception {
		outContent.reset();
		Method m = BoCApp.class.getDeclaredMethod("main",String[].class);
        m.setAccessible(true);
        String input = "T" + System.lineSeparator()+"X"+System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String[] args = null;
        BoCApp.main(args);
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" +System.lineSeparator()+ 
                "\nWhat do you want to do?\n"+
                " T = List All [T]ransactions, [num] = Show Category [num], A = [A]dd Transaction, X = E[x]it" +
                System.lineSeparator()+"1) Rent (Unknown) - ¥850.00" + System.lineSeparator()
                +"2) Phone Bill (Bills) - ¥37.99" + System.lineSeparator()
                +"3) Electricity Bill (Bills) - ¥75.00" + System.lineSeparator()
                +"4) Sainsbury's Checkout (Groceries) - ¥23.76" + System.lineSeparator()
                +"5) Tesco's Checkout (Groceries) - ¥7.24" + System.lineSeparator()
                +"6) RockCity Drinks (Social) - ¥8.50" + System.lineSeparator()
                +"7) The Mooch (Social) - ¥13.99"+System.lineSeparator()+"\nWhat do you want to do?\n" +
                " O = [O]verview, T = List All [T]ransactions, [num] = Show Category [num], C = [C]hange Transaction Category, A = [A]dd Transaction, N = [N]ew Category, X = E[x]it" +
                        System.lineSeparator()+"Goodbye!"+System.lineSeparator(), outContent.toString());
		
	}
    /*
     * Test by Yuyang LIN
     * Pass(Date:05/06/2020)
     * */

    /**
     * @author Rongze LI
     * last modified Yuyang LIN
     * Senario: Input an "A" and create a transaction called "hanging out"
     * @throws Exception
     */
    @Test
    public void Test_App_Main3() throws Exception {
        outContent.reset();
        Method m = BoCApp.class.getDeclaredMethod("main",String[].class);
        m.setAccessible(true);
        String input = "A" + System.lineSeparator()+"hanging out"+System.lineSeparator()+1+System.lineSeparator()+1+System.lineSeparator()+"X"+System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String[] args = null;
        BoCApp.main(args);
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" +System.lineSeparator() +
                "\nWhat do you want to do?\n" +
                " T = List All [T]ransactions, [num] = Show Category [num], A = [A]dd Transaction, X = E[x]it" +
                System.lineSeparator()+"What is the title of the transaction?"+System.lineSeparator()+"What is the value of the transaction?"+System.lineSeparator()+"What is the category ID of the transaction?" + System.lineSeparator()
                +"(Press \"Enter\" to set to \"Unknown\" by default)"+System.lineSeparator()+"[ hanging out(¥1) added to Unknown ]" + System.lineSeparator()

                +"\nWhat do you want to do?\n" +
                " O = [O]verview, T = List All [T]ransactions, [num] = Show Category [num], C = [C]hange Transaction Category, A = [A]dd Transaction, N = [N]ew Category, X = E[x]it" +
                System.lineSeparator()+"Goodbye!"+System.lineSeparator(), outContent.toString());
    }
    /*
     * Test by Yuyang LIN
     * Pass(Date:05/06/2020)
     * */

    /**
     * @author Rongze LI
     * last modified Yuyang LIN
     * Senario: Input a "T" and input a "C" and change transaction
     * @throws Exception
     */
    @Test
    public void Test_App_Main4() throws Exception{
        outContent.reset();
        BoCTransaction tran = new BoCTransaction();
        Method m = BoCApp.class.getDeclaredMethod("main",String[].class);
        m.setAccessible(true);
        String input = "T" + System.lineSeparator()+"C"+System.lineSeparator()+2+System.lineSeparator()+2+System.lineSeparator()+"X"+System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String[] args = null;
        BoCApp.main(args);
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" +System.lineSeparator()+
                "\nWhat do you want to do?\n" +
                " T = List All [T]ransactions, [num] = Show Category [num], A = [A]dd Transaction, X = E[x]it" +
                System.lineSeparator()+"1) Rent (Unknown) - ¥850.00" + System.lineSeparator()
                +"2) Phone Bill (Bills) - ¥37.99" + System.lineSeparator()
                +"3) Electricity Bill (Bills) - ¥75.00" + System.lineSeparator()
                +"4) Sainsbury's Checkout (Groceries) - ¥23.76" + System.lineSeparator()
                +"5) Tesco's Checkout (Groceries) - ¥7.24" + System.lineSeparator()
                +"6) RockCity Drinks (Social) - ¥8.50" + System.lineSeparator()
                +"7) The Mooch (Social) - ¥13.99"+System.lineSeparator()+"\nWhat do you want to do?\n" +
                " O = [O]verview, T = List All [T]ransactions, [num] = Show Category [num], C = [C]hange Transaction Category, A = [A]dd Transaction, N = [N]ew Category, X = E[x]it"+System.lineSeparator()
                +"Which transaction ID?"+System.lineSeparator()
                +"	- Phone Bill - ¥37.99 "+tran.transactionTime()+System.lineSeparator()+"Which category will it move to?"+System.lineSeparator()+"1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)"+ System.lineSeparator()
                +"2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)"+System.lineSeparator()+"3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)"+System.lineSeparator()+"4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"+ System.lineSeparator()
                +"Phone Bill is moved from Bills to Bills"+System.lineSeparator()+"\nWhat do you want to do?\n" +
                " O = [O]verview, T = List All [T]ransactions, [num] = Show Category [num], C = [C]hange Transaction Category, A = [A]dd Transaction, N = [N]ew Category, X = E[x]it" +
                System.lineSeparator()
                +"Goodbye!"+System.lineSeparator(), outContent.toString());
    }
    /*
     * Test by Yuyang LIN
     * Pass(Date:05/06/2020)
     * */

    /**
     * @author Rongze LI
     * last modified Yuyang LIN
     * Senario: Input a "T" and a "N" create a new category
     * @throws Exception
     */
    @Test
    public void Test_App_Main5() throws Exception{
        outContent.reset();
        Method m = BoCApp.class.getDeclaredMethod("main",String[].class);
        m.setAccessible(true);
        String input = "T" + System.lineSeparator()+"N"+System.lineSeparator()+"Entertainment"+System.lineSeparator()+123+System.lineSeparator()+"X"+System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String[] args = null;
        BoCApp.main(args);
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator()+
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator()+
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator()+
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" +System.lineSeparator()+
                "\nWhat do you want to do?\n"+
                " T = List All [T]ransactions, [num] = Show Category [num], A = [A]dd Transaction, X = E[x]it" +
                System.lineSeparator()+"1) Rent (Unknown) - ¥850.00" + System.lineSeparator()
                +"2) Phone Bill (Bills) - ¥37.99" + System.lineSeparator()
                +"3) Electricity Bill (Bills) - ¥75.00" + System.lineSeparator()
                +"4) Sainsbury's Checkout (Groceries) - ¥23.76" + System.lineSeparator()
                +"5) Tesco's Checkout (Groceries) - ¥7.24" + System.lineSeparator()
                +"6) RockCity Drinks (Social) - ¥8.50" + System.lineSeparator()
                +"7) The Mooch (Social) - ¥13.99"+System.lineSeparator()+"\nWhat do you want to do?\n" +
                " O = [O]verview, T = List All [T]ransactions, [num] = Show Category [num], C = [C]hange Transaction Category, A = [A]dd Transaction, N = [N]ew Category, X = E[x]it" +
                System.lineSeparator()
                +"What is the title of the category?"+System.lineSeparator()+"What is the budget for this category?"+System.lineSeparator()
                +"[ Category: Entertainment (¥123.0) was successfully added ]"  + System.lineSeparator()
                +"CategoryOverview ----->"  + System.lineSeparator()
                +"1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator()
                +"2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator()
                +"3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator()
                +"4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)"  + System.lineSeparator()
                +"5) Entertainment (Budget:¥123.0) - ¥0.00 (¥123.00 Remaining)" + System.lineSeparator()
                +"\nWhat do you want to do?\n" +
                " O = [O]verview, T = List All [T]ransactions, [num] = Show Category [num], C = [C]hange Transaction Category, A = [A]dd Transaction, N = [N]ew Category, X = E[x]it" +
                System.lineSeparator()
                +"Goodbye!"+System.lineSeparator(), outContent.toString());
    }
    /*
     * Test by Yuyang LIN
     * Pass(Date:05/06/2020)
     * */

    /**
     * @author Rongze LI
     * last modified Yuyang LIN
     * Senario: Input a "1" and an "X"
     * @throws Exception
     */
    @Test
    public void Test_App_Main6() throws Exception{
        outContent.reset();
        Method m = BoCApp.class.getDeclaredMethod("main",String[].class);
        m.setAccessible(true);
        String input = 1+System.lineSeparator()+"X" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String[] args = null;
        BoCApp.main(args);
        assertEquals("1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)" + System.lineSeparator() +
                "2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)" + System.lineSeparator() +
                "3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)" + System.lineSeparator() +
                "4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)" +
                System.lineSeparator()+"\nWhat do you want to do?\n" +
                " T = List All [T]ransactions, [num] = Show Category [num], A = [A]dd Transaction, X = E[x]it" +
                System.lineSeparator()+
                "1) Rent (Unknown) - ¥850.00" + System.lineSeparator() + "\nWhat do you want to do?\n" + " O = [O]verview, T = List All [T]ransactions, [num] = Show Category [num], C = [C]hange Transaction Category, A = [A]dd Transaction, N = [N]ew Category, X = E[x]it"+
                System.lineSeparator()+"Goodbye!"+System.lineSeparator(), outContent.toString());
    }
    /*
     * Test and modified by Yuyang LIN
     * Fail(Date:05/06/2020)
     * Actual Output:
     *
     * 1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)
     * 2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)
     * 3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)
     * 4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)
     *
     * What do you want to do?
     * T = List All [T]ransactions, [num] = Show Category [num], A = [A]dd Transaction, X = E[x]it
     *
     * 2) Phone Bill (Bills) - ¥37.99
     * 3) Electricity Bill (Bills) - ¥75.00
     *
     * What do you want to do?
     * O = [O]verview, T = List All [T]ransactions, [num] = Show Category [num], C = [C]hange Transaction Category, A = [A]dd Transaction, N = [N]ew Category, X = E[x]it
     *
     * Goodbye!
     *
     * Pass(Date:05/06/2020)
     * */

    @AfterAll
    public static void cleanUpStreams() {
        System.setOut(null);
    }
}
