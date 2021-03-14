# BoCApp Main Class (Note: YYYY/MM/DD refers to any date value)




## Method Name: 1) public static void ListTransactions()

| Description                                      		| Input                      | Expected Output 										   | Actual Output | Result (Pass/Fail) | 
| ------------------------------------------------ 		|--------------------------- | --------------- 										   | ------------- | ------------------ |
| test if the method print the right formatted output without input  | no input					 | create an instance list with and formatted output:<br/>1) Rent (Unknown) - ¥850.00<br>2) Phone Bill (Bills) - ¥37.99<br>3) Electricity Bill (Bills) - ¥75.00<br>4) Sainsbury's Checkout (Groceries) - ¥23.76<br>5) Tesco's Checkout (Groceries) - ¥7.24<br>6) RockCity Drinks (Social) - ¥8.50<br>7) The Mooch (Social) - ¥13.99		       | 1) Rent - ¥850.00<br/>2) Phone Bill - ¥37.99<br/>3) Electricity Bill - ¥75.00<br/>4) Sainsbury's Checkout - ¥23.76<br/>5) Tesco's Checkout - ¥7.24<br/>6) RockCity Drinks - ¥8.50<br/>7) The Mooch - ¥13.99              |   **Fail(Date: 05/03/2020)** <br/> The category of transactions is not displayed               | 
| test if the method print the right formatted output without input  | the same as above		 |     | 1) Rent (Unknown) - ¥850.00<br>2) Phone Bill (Bills) - ¥37.99<br>3) Electricity Bill (Bills) - ¥75.00<br>4) Sainsbury's Checkout (Groceries) - ¥23.76<br>5) Tesco's Checkout (Groceries) - ¥7.24<br>6) RockCity Drinks (Social) - ¥8.50<br>7) The Mooch (Social) - ¥13.99            |    **Pass(Date: 05/03/2020)** <br/> Add the category name              | 

## Method Name: 2) public static void CategoryOverview()

| Description                                      		| Input                      | Expected Output 										   | Actual Output | Result (Pass/Fail) | 
| ------------------------------------------------ 		|--------------------------- | --------------- 										   | ------------- | ------------------ |
| test if the method print the right formatted output without input  | no input					 | create an instance list with and formatted output:<br/>1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)<br/>2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)<br/>3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining))<br/>4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)		       | 1) [Unknown] (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)<br/>2) [Bills] (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)<br/>3) [Groceries] (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)<br/>4) [Social] (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)             |   **Fail(Date: 05/03/2020)** <br/> The "[ ]" is not needed               | 
| test if the method print the right formatted output without input  | the same as above				 | 	       | 1) Unknown (Budget:¥0.00) - ¥850.00 (¥850.00 Overspent)<br/>2) Bills (Budget:¥120.0) - ¥112.99 (¥7.01 Remaining)<br/>3) Groceries (Budget:¥75.0) - ¥31.00 (¥44.00 Remaining)<br/>4) Social (Budget:¥100.0) - ¥22.49 (¥77.51 Remaining)             |   **Pass(Date: 05/03/2020)** <br/> Make a new toString() and remove "[ ]"               | 


## Method Name: 3) public static void ListTransactionsForCategory(int chosenCategory)
| Description                                            | Input                | Expected Output 										  | Actual Output | Result (Pass/Fail) | 
| ------------------------------------------------ 		 |--------------------- | --------------- 										  | ------------- | ------------------ | 
| test if the method prints the existed items according to the category | 1(index within the boundary) 			| create an instance list with and formatted output:<br/>2) Phone Bill - ¥37.99<br/>3) Electricity Bill - ¥75.00   | 2) Phone Bill - ¥37.99<br/>3) Electricity Bill - ¥75.00              |   **Pass(Date: 05/04/2020)** <br/>                 |       
| test if the method prints the inexisted items according to the category    | 4(index out of the boundary)    |   "Sorry, the category ID you entered is not existed"              | No output              |   **Fail(Date: 05/04/2020)** <br/>The warning message is not ready                 |       |
| test if the method prints the inexisted items according to the category    | the same as above          |                 | "Sorry, the category ID you entered is not existed"              |   **Pass(Date: 05/04/2020)** <br/>Modify the code to add a message                 |       |



