package test;
import java.math.BigDecimal;

public class BoCCategory {
	private String CategoryName;
	private BigDecimal CategoryBudget;
	private BigDecimal CategorySpend;
	static int num=0;
	
	public BoCCategory() {
		CategoryName = "New Category"+num++;
		CategoryBudget = new BigDecimal("0.00");
		CategorySpend = new BigDecimal("0.00");
	}

	public BoCCategory(String newTitle) {
		/**
		 * changes made to cut the extra characters exceeding limit 
		 * last modified by :@author Wangkai JIN
		 * 
		 */
		if (newTitle.length()>15) {			
			CategoryName = newTitle.substring(0,15);	
		}
		else {
			CategoryName = newTitle;
		}
		CategoryBudget = new BigDecimal("0.00");
		CategorySpend = new BigDecimal("0.00");
	}

	public String CategoryName() {
		return CategoryName;
	}

	public BigDecimal CategoryBudget() {
		return CategoryBudget;
	}

	public BigDecimal CategorySpend() {
		return CategorySpend;
	}

	public void setCategoryName(String newName) {
		/*
		 * // changes made to cut the extra characters exceeding limit 
		 * /last modified by :@author Wangkai JIN
		 * 
		 */
		if (newName.length()>15) {			
			CategoryName = newName.substring(0,15);	
		}
		else {
			CategoryName = newName;
		}
	}

	public void setCategoryBudget(Float newValue) throws Exception {	
		/**
		 *  change the type of newValue from BigDecimal to Float
		 *  transform the type float to BigDeciaml to add  to the budget
		 *  last modified by @author Wangkai JIN
		 */
		BigDecimal value = new BigDecimal(((Number)newValue).toString());		 
		// 1 means bigger, -1 means smaller, 0 means same						
		if (value.compareTo(new BigDecimal("0.00")) == 1) {			
			CategoryBudget = value;
		} else {
            throw new ClassCastException("Not possible to coerce [" + value + "] from class " 
                    + value.getClass() + " into a BigDecimal.");
            }
	}

	public void addExpense(BigDecimal valueToAdd) {			// add a if-statement for checking the value	
		if (valueToAdd.compareTo(new BigDecimal("0.00")) == 1) {			
			CategorySpend = CategorySpend.add(valueToAdd);
		}
		
		
	}

	public void removeExpense(BigDecimal valueToRemove) {
		/*
		 * two if statements are added here to guarantee that the input 
		 *  the value to be removed can be negative and total spend can't be negative
		 *  last modified by: @author Wangkai JIN
		 */
		if (valueToRemove.compareTo(new BigDecimal("0.00"))==-1) {    
			System.out.println("Input cannot be negative!");		  
		}															  
		else {
			BigDecimal temp  = CategorySpend.subtract(valueToRemove);
			if (temp.compareTo(new BigDecimal("0.00"))==-1) {
				System.out.println("CategorySpend cannot be negative!");
			}
			else {
				CategorySpend = temp;
			}
		}		
	}

	public void resetBudgetSpend() {
		CategorySpend = new BigDecimal("0.00");
	}

	public BigDecimal getRemainingBudget() {
		BigDecimal remainingBudget = CategoryBudget.subtract(CategorySpend);
		return remainingBudget;
	}

	@Override
	public String toString() {		
		/**
		 * 	add a if-statement for two situations:1. budget remaining 2. overspent  
		 * last modified by Wangkai JIN

		 */
		if (this.CategoryBudget.compareTo(this.CategorySpend())!= -1)
			return "["+CategoryName + "]"+ "(Budget:¥" + CategoryBudget.toPlainString() + ") - ¥" + CategorySpend.toPlainString()
					+ " (¥" + getRemainingBudget().toPlainString() + " Remaining)";
		else 
			return "["+CategoryName + "]" + "(Budget:¥" +  CategoryBudget.toPlainString() + ") - ¥" + CategorySpend.toPlainString()
			+ " (¥" + CategorySpend.subtract(CategoryBudget).toPlainString() + " Overspent)";
	}

}
