# BoCCategory Class (Note: YYYY/MM/DD refers to any date value)

## Constructor() 

| Description                                        					 | Input                        					      | Expected Output                                            							| Actual Output | Result(Pass/Fail) | Cause |
| -----------------------------------------------------------------------| -------------------------------------------------------| ------------------------------------------------------------------------------------| ------------- | ----------------- | ----- |
| test default constructor (void input)       		 					 | no input                     						  | CategoryName = “New Category”<br/>CategoryBudget = 0.00<br/>CategorySpend = 0.00    |               |                   |       |
| test constructor with one input (String) 	 		 					 | "Social"                     						  | CategoryName = "Social"<br/>CategoryBudget = 0.00<br/>CategorySpend = 0.00      	|               |                   |       |
| test constructor with one input (BigDecimal) 					 		 | new BigDecimal("100")        						  | error-- undefined constructor BoCTransaction(BigDecimal) 					        |               |                   |       |
| test constructor with multiple inputs (String, BigDecimal) 			 | "Social", new BigDecimal("100")						  | error-- undefined constructor BoCTransaction(String, BigDecimal)    				|               |                   |       |
| test constructor with multiple inputs (String, BigDecimal, BigDecimal) | "Social", new BigDecimal("1.0"), new BigDecimal("100") | error-- undefined constructor BoCTransaction(String, BigDecimal, BigDecimal) 		|               |                   |       |
|                  						                                 |                              						  |                                                              						|               |                   |       |
|                                                    					 |                              						  |                                                              						|               |                   |       |

## Method Name: 2) public String CategoryName()

| Description                                      		| Input                      | Expected Output 										   | Actual Output | Result (Pass/Fail) | Cause |
| ------------------------------------------------ 		|--------------------------- | --------------- 										   | ------------- | ------------------ | ----- |
| test if the method return CategoryName without input  | no input					 | 		           										   |               |  CategoryName      |       |
| Test if the method fails with input (String)			| "Name"					 | Error: The method CategoryName() in the type BoCCategory</br> is not applicable for the arguments (String) |               |                    |       |
|                                                  		|                                                              |                 |               |                    |       |
|                                                  		|                                                              |                 |               |                    |       |
                                

## Method Name: 3) public BigDecimal CategoryBudget()

| Description                                            | Input                | Expected Output 										  | Actual Output | Result (Pass/Fail) | Cause |
| ------------------------------------------------ 		 |--------------------- | --------------- 										  | ------------- | ------------------ | ----- |
| test if the method return CategoryBudget without input | no input 			| CategoryBudget  										  |               |                    |       |
| Test if the method fails with input (BigDecimal)   	 | new BigDecimal("100")| Error: The method CategoryBudget() in the type BoCCategory is not applicable for the arguments (BigDecimal)  |               |                    |       |
|                                                  |                                                              |                 |               |                    |       |
|                                                  |                                                              |                 |               |                    |       |

