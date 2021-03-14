This project is a custom 16-bit ALU chip which has several functions:
1) calculate subtraction of 2 numbers (x-y),by choosing S pin
2) calculate multiplication of 2 numbers(x*y), by choosing M pin
3) calculate x divided by 2, by choosing D pin
4) calculate an exponential expression(x^y), by choosing F pin
5) compare 2 numbers, by choosing GL pin
6) an of(overflow) pin to test whether all the calculation above overflows

Input limits(!!!important!!  if unexpected input is entered,the ALU won't work correctly!):
Multiplication :   Operands for multiplication should be in [-100,100] range
Division:  x for division should be positive only.
Power operation: base number should be in (0..9],power number should be in (0..5],overflow can happen in these two given range.
Only choose one function pin one time !


How to use?
1. load the CWALU file into emulator
2. input the value of x and y
3. select a pin that represents the corresponding operation you want to do and change  the number into 1 in the column
4. wait for the result coming out of out pin and of pin