# BoCCategory Class


## Method Name: 8) public void removeExpense(BigDecimal valueToRemove)

| Description                            							  			  |	Input				  | Expected Output | Actual Output | Result (Pass/Fail) | Cause |
| -------------------------------- 									 	    	  | --------------- 	  | -------------   | ------------------ | ----- | ----- |
| test if the method fails without input          						    	  | no input        	  | Error: The method removeExpense(BigDecimal) in the type BoCCategory is not applicable for the arguments ()    |                    |       |
| test if the method subtracts the input BigDecimal number to CategorySpend  	  | new BigDecimal("1.00")| The value of CategorySpend is subtracted by 1 |       |



## Method Name: 9) public void resetBudgetSpend()

| Description                            							  			  |	Input				  | Expected Output | Actual Output | Result (Pass/Fail) | Cause |
| -------------------------------- 									 	    	  | --------------- 	  | -------------   | ------------------ | ----- | ------ |
| test if the method CategorySpend is reset without input     			    	  | no input        	  |  CategorySpend is reset to "0.00" |                    |       |
| test if the method fails with a BigDecimal input  	  						  | new BigDecimal("1.00")| Error: The method resetBudgetSpend() in the type BoCCategory is not applicable for the arguments (BigDecimal)|       |



## Method Name: 10) public BigDecimal getRemainingBudget()

| Description                            				  			  |	Input				  | Expected Output | Actual Output | Result (Pass/Fail) | Cause |
| -------------------------------- 									  | --------------- 	  | -------------   | ------------------ | ----- | ------|
| test if the method returns remainingBudget     			    	  | no input        	  | CategoryBudget - CategorySpend  |                    |       |
| test if the method fails with a BigDecimal input  	  			  | new BigDecimal("1.00")| Error: The method resetBudgetSpend() in the type BoCCategory is not applicable for the arguments (BigDecimal) |       |



## Method Name: 11) public String toString()

| Description                            				  			  |	Input				  | Expected Output | Actual Output | Result (Pass/Fail) | Cause |
| -------------------------------- 									  | --------------- 	  | -------------   | ------------------ | ----- | ----- |
| test if the method returns name, budget and spend of category       | no input        	  | "Name"(¥XXX) - Est. ¥XXX (¥XXX Remaining) |                    |       |
| test if the method fails with a BigDecimal input  	  			  | new BigDecimal("1.00")| Error: The method toString() in the type BoCCategory is not applicable for the arguments (BigDecimal) |       |
