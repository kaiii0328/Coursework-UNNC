# BoCCategory Class (Note: YYYY/MM/DD refers to any date value)

## Constructor() 

| Description                                                  | Input               | Expected Output                                              | Actual Output                                               | Result(Pass/Fail)                                            |
| ------------------------------------------------------------ | ------------------- | ------------------------------------------------------------ | ----------------------------------------------------------- | ------------------------------------------------------------ |
| test default constructor (void input) and the length of default CategoryName | no input            | create an instance with Name = New Category<br/>Budget = 0.00<br/>Spend = 0.00<br/> | Name = New Category<br/>Budget = 0.00<br/>Spend = 0.00<br/> | Pass:(Date 1/5/2020)<br/>Reason: the input is what exactly defined |
| test constructor with one input (String length <= 15)        | "Social"            | create an instance with Name = Social<br/>Budget = 0.00<br/>Spend = 0.00 | Name = Social<br/>Budget = 0.00<br/>Spend = 0.00            | Pass:(Date 1/5/2020)<br/>Reason: the input is what exactly defined |
| test constructor with one input (String length > 15)         | "AVeryLongCategory" | create an instance with Name = AVeryLongCatego<br/>Budget = 0.00<br/>Spend = 0.00 | Name = AVeryLongCategory<br/>Budget = 0.00<br/>Spend = 0.00 | Fail:(Date 1/5/2020)<br/>Reason: the constructor doesn't check whether the name is more than characters<br />Pass: (Date:2/5/2020)   Reason: use a if-statement to check whether the name is more than 16 characters, if the name exceeds the limit, the program will cut off the extra part to fit the limit |

## Method Name: 2) public String CategoryName()

| Description                                      		| Input                      | Expected Output 										   | Actual Output | Result (Pass/Fail) | 
| ------------------------------------------------ 		|--------------------------- | --------------- 										   | ------------- | ------------------ |
| test if the method return CategoryName without input  | no input					 | create an instance with Name = New Category		       | Name = New Category              |  Pass:(Date 1/5/2020)<br/>Reason: The method was called correctly                  | 

​                                

## Method Name: 3) public BigDecimal CategoryBudget()

| Description                                            | Input                | Expected Output 										  | Actual Output | Result (Pass/Fail) | 
| ------------------------------------------------ 		 |--------------------- | --------------- 										  | ------------- | ------------------ | 
| test if the method return CategoryBudget without input | no input 			| create an instance with Budget = 0.00  										  | Budget = 0.00              | Pass:(Date 1/5/2020)<br/>Reason: The method was called correctly                   |       



## Method Name: 4) public BigDecimal CategorySpend() 

| Description                                            | Input                | Expected Output                                           | Actual Output | Result (Pass/Fail) |
| ------------------------------------------------          |--------------------- | --------------------------------------------------------| ------------- | ------------------ |
| test if the method return CategorySpend without input  | no input             | CategorySpend                                            | CategorySpend              |    Pass  (Date:1/5/2020)  Reason: the input is what exactly defined    |



​                     

## Method Name: 5) public void setCategoryName(String newName)

| Description                                                          |    Input            | Expected Output                                                                                               | Actual Output | Result (Pass/Fail) |
| --------------------------------                                       | --------------- | -------------                                                                                                 | ------------- | ------------------ |
| test if the method sets CategoryName properly with a String length <=15  | "newName"       | Name =  "newName"                                                                       |   CategoryName = "newName"           | Pass:(Date:1/5/2020)  Reason: the input is what exactly defined |
| test if the method fails with a String input length > 15  | "AVeryLongCategory"       |  Name = "AVeryLongCatego"                                                    |  CategoryName = "AVeryLongCategory"             | Fail: (Date:1/5/2020)Reason: the method doesn't check the length of input String<br />Pass:(Date: 2/5/2020)  Reason : cut off the extra characters |



## Method Name: 6) public void setCategoryBudget(Float newValue)

| Description                                                                        |    Input                         | Expected Output                                | Actual Output         | Result (Pass/Fail) |
| --------------------------------------------------------------------------------| ---------------              | -------------                                  | ------------------ | ------------------ |
| test if the method sets CategoryBudget properly with a float input >= total spend        | 100.00f       | The value of CategoryBudget is set to 100.00f| Error: The method setCategoryBudget(BigDecimal) in the type BoCCategory is not applicable for the arguments (float)                 |  Fail (Date:1/5/2020)  Reason: type conflict<br />Pass: (Date: 2/5/2020)  Reason: change  the input type to float and cast them into BigDecimal type. Then assign to budget  |
| test if the method fails with a float input < total spend      | budget:0.01f spend:99       | The value of CategoryBudget is set to 0.01f |  Error: The method setCategoryBudget(BigDecimal) in the type BoCCategory is not applicable for the arguments (float) | Fail (Date:1/5/2020) Reason: type conflict<br />Pass:(Date:2/5/2020) Reason: same as above |
| test if the method fails with a BigDecimal input                                        | new BigDecimal("1.00")                             | Error: The method setCategoryBudget(float) in the type BoCCategory is not applicable for the arguments (BigDecimal)|  CategoryBudget is set to BigDecimal("1.00")                  |  Fail (Date:1/5/2020) Reason: type conflict<br />Pass:(Date:2/5/2020) Reason: same as above  |

## Method Name: 7) public void addExpense(BigDecimal valueToAdd)

| Description                                                                        |    Input                  | Expected Output                          | Actual Output | Result (Pass/Fail) |
| --------------------------------                                                    | ---------------       | -------------                            | --------------| ------------------ |
| test if the method adds a positive value Category spend | Category with expense 100.00, input for method :new BigDecimal("1.00") | The value of CategorySpend is added by 1 | The value of CategorySpend is added by 1              | Pass (Date:1/5/2020) Reason: the input is what exactly defined |
| test if the method fails with a negative input | new BigDecimal("-1.00")| Fail: The method addExpense(BigDecimal) in the type BoCCategory didn't check whether the input is less than zero | The value of CategorySpend is subtracted by 1              | Fail (Date:1/5/2020)Reason: The method doesn't check if the expense is <0<br />Pass:(Date:2/5/2020)  Reason: method is changed to ignore negative input |

## Method Name: 8) public void removeExpense(BigDecimal valueToRemove)

| Description                                                                        |    Input                  | Expected Output | Actual Output | Result (Pass/Fail) |
| --------------------------------                                                    | ---------------       | -------------   | ------------------ | ----- |
| test if the method removes the input (BigDecimal number > 0) to CategorySpend            | Category with expense 10.00, removeExpense: new BigDecimal("1.00") | The value of CategorySpend is 9 | Spend = 9 | Pass:(Date: 1/5/2020) Reason: the input is what exactly defined |
| test if the method removes the input (BigDecimal number = 0) to CategorySpend            | Category with expense 10.00, removeExpense: new BigDecimal("1.00") | The value of CategorySpend is 10 | Spend =  10 | Pass: (Date:1/5/2020) Reason: the input is what exactly defined |
| test if the method removes the input (BigDecimal number < 0) to CategorySpend            | new BigDecimal("-1.00")| The program prompts a message saying the input can't be negative | Spend = 11 | Fail: (Date: 1/5/2020)   Reason: logic error. the spend to be removed cannot be negative<br />Pass:(Date: 2/5/2020)  Reason: the method is edited to prompt a error message saying input can't be negative |
| test if the method fails when the total spend is less than 0 after removing            | Category with expense 0.00, removeExpense: new BigDecimal("1.00") | The program prompts a message saying the total spend cannot  be zero | Spend = -1 | Fail: (Date:1/5/2020)Reason: logic error. the spend to be removed cannot be negative<br />Pass:(Date: 2/5/2020)  Reason: the method is edited to prompt a error message saying spend can't be negative |


## Method Name: 9) public void resetBudgetSpend()

| Description                                                                        |    Input                  | Expected Output | Actual Output | Result (Pass/Fail) |
| --------------------------------                                                    | ---------------       | -------------   | ------------------ | ----- |
| test if the method CategorySpend is reset without input                           | Category with expense 200.00, no inputs for method |  CategorySpend is reset to "0.00" | Spend = 0 | Pass:(Date 1/5/2020) |


## Method Name: 10) public BigDecimal getRemainingBudget()

| Description                                                            |    Input                  | Expected Output | Actual Output | Result (Pass/Fail) |
| --------------------------------                                       | ---------------       | -------------   | ------------------ | ----- |
| test if the method returns remainingBudget                           | Category with budget 250, spend 200, no inputs for method | 50              | 50 | Pass:(Date:1/5/2020) |


## Method Name: 11) public String toString()

| Description                                                            |    Input                  | Expected Output | Actual Output | Result (Pass/Fail) |
| --------------------------------                                       | ---------------       | -------------   | ------------------ | ----- |
| test if the method returns name, budget and spend of category       | Category with default name, value budget and spend is 0, no input for method | [New Category] (Budget:¥0.00) - ¥0.00 (¥0.00 Remaining) | New Category(¥0.00) - Est. ¥0.00 (¥0.00 Remaining) | Fail:(Date:1/5/2020) Reason : print format is wrong <br /><br />Pass: (Date:2/5/2020)   Reason: the output is expected |
| test if the method will print the overspent money | Category with default name, value budget(20f) and spend 40 BigDecimal, overspent amount 20.00, no input for method | [New Category](Budget:¥20.0) - ¥40.00 (¥20.00 Overspent) | New Category(¥0.00) - Est. ¥0.00 (¥0.00 Remaining) | Fail:(Date:2/5/2020)  Reason: print format is wrong<br /><br />Pass: (Date:2/5/2020)   Reason: the output is expected |




