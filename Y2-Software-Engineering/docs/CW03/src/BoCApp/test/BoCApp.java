package test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class BoCApp {
	public static ArrayList<BoCTransaction> UserTransactions;
	public static ArrayList<BoCCategory> UserCategories;

	public static void main(String[] args) throws Exception {
		UserCategories = new ArrayList<BoCCategory>();
		UserTransactions = new ArrayList<BoCTransaction>();

		/**
		 * change the type in setCategoryBudget() to float
		 * last modified by Yuyang LIN
		 */
		UserCategories.add(new BoCCategory("Unknown"));
		BoCCategory BillsCategory = new BoCCategory("Bills");
		BillsCategory.setCategoryBudget(120.00f);
		UserCategories.add(BillsCategory);
		BoCCategory Groceries = new BoCCategory("Groceries");
		Groceries.setCategoryBudget(75.00f);
		UserCategories.add(Groceries);
		BoCCategory SocialSpending = new BoCCategory("Social");
		SocialSpending.setCategoryBudget(100.00f);
		UserCategories.add(SocialSpending);

		UserTransactions.add(new BoCTransaction("Rent", new BigDecimal("850.00"), 0));
		UserTransactions.add(new BoCTransaction("Phone Bill", new BigDecimal("37.99"), 1));
		UserTransactions.add(new BoCTransaction("Electricity Bill", new BigDecimal("75.00"), 1));
		UserTransactions.add(new BoCTransaction("Sainsbury's Checkout", new BigDecimal("23.76"), 2));
		UserTransactions.add(new BoCTransaction("Tesco's Checkout", new BigDecimal("7.24"), 2));
		UserTransactions.add(new BoCTransaction("RockCity Drinks", new BigDecimal("8.50"), 3));
		UserTransactions.add(new BoCTransaction("The Mooch", new BigDecimal("13.99"), 3));

		for (int x = 0; x < UserTransactions.size(); x++) {
			BoCTransaction temp = UserTransactions.get(x);
			int utCat = temp.transactionCategory();
			BoCCategory temp2 = UserCategories.get(utCat);
			temp2.addExpense(temp.transactionValue());
			UserCategories.set(utCat, temp2);
		}

		// MAIN FUNCTION LOOP
		CategoryOverview();
		System.out.println(
				"\nWhat do you want to do?\n T = List All [T]ransactions, [num] = Show Category [num], A = [A]dd Transaction, X = E[x]it");
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String s = in.next();
			try {
				if (s.equals("T")) {
					ListTransactions();
				} else if (s.equals("O")) {
					CategoryOverview();
				} else if (s.equals("C")) {
					ChangeTransactionCategory(in);
				} else if (s.equals("N")) {
					AddCategory(in);
				} else if (s.equals("A")) {
					AddTransaction(in);
				} else if (s.equals("X")) {
					System.out.println("Goodbye!");
					break;
				} else if (Integer.parseInt(s) != -1) {
				    // set correct index of category
					ListTransactionsForCategory((int) (Integer.parseInt(s) - 1));
				} else {
					System.out.println("Command not recognised");
				}
			} catch (Exception e) {
				System.out.println("Something went wrong: " + e.toString() + "\n");
			}

			System.out.println(
					"\nWhat do you want to do?\n O = [O]verview, T = List All [T]ransactions, [num] = Show Category [num], C = [C]hange Transaction Category, A = [A]dd Transaction, N = [N]ew Category, X = E[x]it");
		}
		in.close();
	}

	/**
	 * print out the list transaction in the form of "SanJiang shopping (groceries) - ¥186.90"
	 * last modified by Yuyang LIN
	 */
	public static void ListTransactions() {
		for (int x = 0; x < UserTransactions.size(); x++) {
			BoCTransaction temp = UserTransactions.get(x);
			System.out.println((x + 1) + ") " + temp.transactionName() + " (" + UserCategories.get(temp.transactionCategory()).CategoryName() + ")" + " - ¥" + temp.transactionValue().toString() );
		}
	}

	/**
	 * print out the list transaction in the form of "Bills (Budget: ¥120.0) - ¥112.99 (¥7.01 Remaining)"
	 * last modified by Yuyang LIN
	 */
	public static void CategoryOverview() {
		for (int x = 0; x < UserCategories.size(); x++) {
			BoCCategory temp = UserCategories.get(x);
			// Modify the overview, as the output of toString is not the same as expected ones (Yuyang LIN)
			if (temp.CategoryBudget().compareTo(temp.CategorySpend()) != -1) {
				System.out.println((x + 1) + ") " + temp.CategoryName() + " (Budget:¥" + temp.CategoryBudget() + ") - ¥" + temp.CategorySpend()
						+ " (¥" + temp.getRemainingBudget() + " Remaining)");
			} else {
				System.out.println((x + 1) + ") " + temp.CategoryName() + " (Budget:¥" + temp.CategoryBudget() + ") - ¥" + temp.CategorySpend()
						+ " (¥" + temp.CategorySpend().subtract(temp.CategoryBudget()) + " Overspent)");
			}
		}

	}

	/**
	 * add an if-statement to handle when the input category is not in the list, then print a sentence
	 * last modified by Yuyang LIN
	 */
	public static void ListTransactionsForCategory(int chosenCategory) {
		Boolean flag = false;
		for (int x = 0; x < UserTransactions.size(); x++) {
			BoCTransaction temp = UserTransactions.get(x);
			if (temp.transactionCategory() == chosenCategory) {
				flag = true;
				System.out.println((x + 1) + ") " + temp.transactionName() + " (" + UserCategories.get(temp.transactionCategory()).CategoryName() + ")" + " - ¥" + temp.transactionValue().toString() );
			}
		}
		if (!flag) {
			System.out.println("Sorry, the category ID you entered is not existed");
		}
	}


	/**
	 * @author Qicheng Chen
	 * @param in
	 * @throws IndexOutOfBoundsException
	 * @throws NumberFormatException
	 */
	private static void ChangeTransactionCategory(Scanner in) throws IndexOutOfBoundsException, NumberFormatException{
		System.out.println("Which transaction ID?");
		in.nextLine();
		int tID = Integer.parseInt(in.nextLine()) - 1;


		// Removed -1 after the 'tID' (Date 05/04/2020)
		System.out.println("\t- " + UserTransactions.get(tID).toString());
		System.out.println("Which category will it move to?");
		CategoryOverview();

		// Decrement user input by 1 to get the desired category ID (Date 05/04/2020)
		// Changed var name 'newCat' to newCatID (Date 05/04/2020)
		int newCatID = Integer.parseInt(in.nextLine()) - 1;

		// tID - 1 to get the desired transaction ID (Date 05/04/2020)
		// Changed var name 'temp' to 'transaction'
		BoCTransaction transaction = UserTransactions.get(tID);

		// Remember the old category for later use (Date 05/04/2020)
		int oldCatID = transaction.transactionCategory();
		BoCCategory oldCat = UserCategories.get(oldCatID);

		transaction.setTransactionCategory(newCatID);
		UserTransactions.set(tID, transaction);


		// Changed var name 'temp2' to 'newCat' (Date 05/04/2020)
		BoCCategory newCat = UserCategories.get(newCatID);
		newCat.addExpense(transaction.transactionValue());

		// Remove expense from the old category (Date 05/04/2020)
		oldCat.removeExpense(transaction.transactionValue());

		// Update (Date 05/04/2020)
		UserCategories.set(newCatID, newCat);

		// Also update old category (Date 05/04/2020)
		UserCategories.set(oldCatID, oldCat);

		// Messege for success (Date 05/04/2020)
		System.out.println(transaction.transactionName() + " is moved from "
				+ oldCat.CategoryName() + " to " + newCat.CategoryName());

	}


	/**
	 * @author Yizhou Chen
	 * @param in
	 * @throws IndexOutOfBoundsException
	 * @throws NumberFormatException
	 */
	private static void AddTransaction(Scanner in) throws IndexOutOfBoundsException, NumberFormatException {
		//initialize (Date 05/04/2020)
		int tcatID = 0;

		System.out.println("What is the title of the transaction?");
		in.nextLine(); // to remove read-in bug
		String title = in.nextLine();
		System.out.println("What is the value of the transaction?");
		BigDecimal tvalue = new BigDecimal(in.nextLine());

		/* (Date 05/04/2020)
		Ask if need to specify the belonging category
		Add to Unknown by default
		get catID if given
		*/
		System.out.println("What is the category ID of the transaction?"+System.lineSeparator()+"(Press \"Enter\" to set to \"Unknown\" by default)");
		String buffer = in.nextLine();

		//might get NumberFormatException (Date 05/04/2020)

		//Category will be set to "Unknown" by default if only "Enter" is pressed instead of giving CategoryID (Date 05/04/2020)
		if (!buffer.equals(""))
			tcatID = Integer.parseInt(buffer) -1;
		
		//check if tvalue is bigger than 0 (Date 05/06/2020)
		//if less than 0 then print a message to let user to select a new value (Date 05/06/2020)
		if (tvalue.compareTo(new BigDecimal("0.00")) == -1) {
			System.out.println("Transaction value must be bigger than 0");
			return;
		}
		
		//handle IndexOutOfBoundsException (Date 05/04/2020)
		if (tcatID>=0 && tcatID < UserCategories.size()) {
			//check if transaction name is given (Date 05/06/2020)
			if(!title.equals("")) {
				//if name is give add new transaction to UserTransaction list (Date 05/04/2020)
				BoCTransaction newTrans = new BoCTransaction(title, tvalue, tcatID);
				UserTransactions.add(newTrans);
				//add expense to target Category (Date 05/04/2020)
				UserCategories.get(tcatID).addExpense(tvalue);
				// Modified print statement (Date 05/04/2020)
				System.out.println("[ " + title + "(¥" + newTrans.transactionValue() + ")" + " added to " + UserCategories.get(tcatID).CategoryName() + " ]");
			}
			else{
				//if name is in default setting by pressing Enter (Date 05/06/2020)
				//catch Exception thrown by BoCTransaction.class to avoid setting Value and Category multiple times (Date 05/06/2020)
				try {
					//create a new Transaction by default constructor (Date 05/06/2020)
					//set Value and Category by calling internal method (Date 05/06/2020)
					BoCTransaction newTrans = new BoCTransaction();
					newTrans.setTransactionValue(tvalue);
					newTrans.setTransactionCategory(tcatID);
					UserTransactions.add(newTrans);
					//add expense to target Category (Date 05/04/2020)
					UserCategories.get(tcatID).addExpense(tvalue);
					// Modified print statement (Date 05/04/2020)
					System.out.println("[ " + title + "(¥" + newTrans.transactionValue() + ")" + " added to " + UserCategories.get(tcatID).CategoryName() + " ]");
				}catch (Exception e){
					System.out.println(e.getMessage());
				}
			}
		}
		else throw new IndexOutOfBoundsException();
	}


	/**
	 * @author Yizhou Chen
	 * @param in
	 * @throws Exception
	 */
	private static void AddCategory(Scanner in) throws Exception {

		System.out.println("What is the title of the category?");
		in.nextLine(); // to remove read-in bug
		String title = in.nextLine();
		System.out.println("What is the budget for this category?");
		Float cbudget = Float.valueOf(in.nextLine());

		//check if CategoryBudget is bigger than 0 (Date 05/04/2020)
		if (cbudget >0){

			//check if CategoryName is given or in default setting (Date 05/06/2020)
			if(!title.equals("")) {
				//check if CategoryName has existed (Date 05/06/2020)
				//if existed, print a message to let user select another name (Date 05/06/2020)
				for (BoCCategory i : UserCategories) {
					if(title.equals(i.CategoryName())){
						System.out.println("Category: "+ title +" has existed, please select a new name");
						return;
					}
				}

				//if not existed, create a new Category and add it to the UserCategories list (Date 05/06/2020)
				BoCCategory temp = new BoCCategory(title);
				temp.setCategoryBudget(cbudget);
				UserCategories.add(temp);
			}

			//if CategoryName is in default setting by press Enter (Date 05/06/2020)
			//then calling BoCCategory default Constructor	(Date 05/06/2020)
			else{
				BoCCategory temp = new BoCCategory();
				temp.setCategoryBudget(cbudget);
				UserCategories.add(temp);
			}

			//Modified print statement (Date 05/04/2020)
			System.out.println("[ Category: " + UserCategories.get(UserCategories.size()-1).CategoryName() + " (¥" + cbudget + ") was successfully added ]");
			System.out.println("CategoryOverview ----->");
			CategoryOverview();

		}
		else System.out.println("Budget must be bigger than 0!");
	}


}
