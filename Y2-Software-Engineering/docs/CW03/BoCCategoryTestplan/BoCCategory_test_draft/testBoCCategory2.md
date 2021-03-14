
## Method Name: 4) public BigDecimal CategorySpend() 

| Description                                            | Input                | Expected Output 										  | Actual Output | Result (Pass/Fail) | Cause |
| ------------------------------------------------ 		 |--------------------- | --------------------------------------------------------| ------------- | ------------------ | ----- |
| test if the method return CategorySpend without input  | no input 			| CategorySpend  										  |               |                    |       |
| Test if the method fails with input (BigDecimal)   	 | new BigDecimal("100")| Error: The method CategorySpend() in the type BoCCategory is not applicable for the arguments (BigDecimal)  |               |                    |       |


                     

## Method Name: 5) public void setCategoryName(String newName)

| Description                            							  |	Input			| Expected Output 																							  | Actual Output | Result (Pass/Fail) | Cause |
| -------------------------------- 									  | --------------- | -------------   																							  | ------------- | ------------------ | ----- |
| test if the method fails without input          					  | no input        | Error: The method setCategoryName(String) in the type BoCCategory is not applicable for the arguments ()    |               |                    |       |
| test if the method sets CategoryName properly with a String input   | "newName"       | CategoryName is set to "newName"               															  |               |                    |       |
| test if the method fails with an int input	 			     	  | 100             | Error: The method setCategoryName(String) in the type BoCCategory is not applicable for the arguments (int) |               |                    |       |



## Method Name: 6) public void setCategoryBudget(BigDecimal newValue)

| Description                            							  			  |	Input						 | Expected Output 							   | Actual Output 		| Result (Pass/Fail) | Cause |
| --------------------------------------------------------------------------------| --------------- 			 | -------------   							   | ------------------ | ------------------ | ----- |
| test if the method fails without input          						    	  | no input        			 | Error: The method setCategoryBudget(BigDecimal) in the type BoCCategory is not applicable for the arguments ()|                    |                    |       |
| test if the method sets CategoryBudget properly with a BigDecimal input >0  	  | new BigDecimal("1.00")       | The value of CategoryBudget is set to "1.00"(BigDecimal)|                    |                    |       |
| test if the method doesn't update CategoryBudget with a BigDecimal input <0	  | new BigDecimal("-1.00")      | The value of CategoryBudget doesn't change  |                    |                    |       |
| test if the method doesn't update CategoryBudget with a BigDecimal input ==0	  | new BigDecimal("0.00")       | The value of CategoryBudget doesn't change  |                    |                    |       |
| test if the method fails with a String input          						  | "1.00"        			     | Error: The method setCategoryBudget(BigDecimal) in the type BoCCategory is not applicable for the arguments (String)|                    |                    |       |
| test if the method fails with an int input          						      | 1                			 | Error: The method setCategoryBudget(BigDecimal) in the type BoCCategory is not applicable for the arguments (int)|                    |                    |       |

## Method Name: 7) public void addExpense(BigDecimal valueToAdd)

| Description                            							  			  |	Input				  | Expected Output 						 | Actual Output | Result (Pass/Fail) | Cause |
| -------------------------------- 									 	    	  | --------------- 	  | -------------   						 | --------------| ------------------ | ----- |
| test if the method fails without input          						    	  | no input        	  | Error: The method addExpense(BigDecimal) in the type BoCCategory is not applicable for the arguments ()|               |                    |       |
| test if the method adds the input BigDecimal number to CategorySpend  	  	  | new BigDecimal("1.00")| The value of CategorySpend is added by 1 |               |       |
| test if the method fails with a String input	 								  | "String"        	  | Error: The method addExpense(BigDecimal) in the type BoCCategory is not applicable for the arguments (String)|               |                    |       |

