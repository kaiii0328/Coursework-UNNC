## How to use the program?
##1. Enter the string that contains the format character,at most 3 formats can be entered in one string(e.g. "I'm %s","Wangkai Jin")
##2. Enter the datatype of the first value.Choices are {d,s,S,%,c},press [enter] on the keyborad if you dont need to enter variables anymore
##3. Enter the value of corresponding datatype after the prompt.Press [enter] on the keyborad if there is no  variable for this type(e.g. for %%,no valriable should be provided,even if 
##a value is input,it won't be printed.)
##	for inputting value,it is legal if user inputs characters and the program will print out decimal values or inputs decimal number and the program will print out character value.
##4. repeat input datatype and data value until all the values are input, please notice if the input variables are more than the number of ESCs, only the first several values(number equals to the number of ESCs) will be transformed( if needed) and printed.

## NOTE:1.The program will crash if the user want to do "string to character" or "character to string" or "decimal to string" or "string to decimal " transformation.The program will end with message in console showing"program terminates with error."
##		2.The input valriables should be input in the corresponding order in the format string,or strange output may occur.The ESC ,%%,can not be in a part of data transformation,make sure that no variable is input.
##		3.At most one string can be input in a format string .

## 10 normal test
##1. fmt.string: "%d%c%s"  input in the console with prompts: d 10  c y  s string	  ,output "10 y string"
##2. fmt_string: "%d%c"    input in the console with prompts: d 2   c 8  [enter]	  ,output "2 8 "
##3. fmt_string: "%d%s%%"  input in the console with prompts: c A   s message % [enter],output "65 message %"
##4. fmt_string: "%s%c%d"  input in the console with prompts: s asdf  d 1 d 20         , output "asdf 48 20"
##5. fmt_string: "%S%c"	   input in the console with prompts: s sdvg  d 0	[enter]		,output "SDVG 47"
##6. fmt_string: "%c%%%d"  input in the console with prompts: c i  % [enter]  d  0		,output "i  %  0"
##7. fmt_string: "I'm %d years old." input in the console after prompts: d 6 [enter]	,output "I'm 6 years old."
##8. fmt_string: "%c%%"		input in the console with prompts: c 5 % [enter]			,output "5 %"
##9. fmt_string:  "%d"		input in the console with prompts: d 34	 [enter]			,output "34"
##10. fmt_string: "%s"		input in the console with prompts:s message	 [enter]		,output "message"

## 10 abnormal test (I consider "decimal to char" and "char to decimal" as normal and they are showed in the normal tests)
##1. fmt_string: "%d"		input in the console with prompts:% d	[enter]				,output"100"    reason:'d' is read in as char,and its ASCII code is 100,when print out as decimal type, it shows "100"
##2. fmt_string: "%c"		input in the console with prompts:% a   [enter]				,output"a"		reason:'a' is read in as char,and print as char
##3. fmt_string: "%s"		input in the console with prompts:d 12	[enter]				,crash,  "execution terminates with error."	reason:these two datetypes cannot be converted
##4. fmt_string: "%s"		input in the console with prompts:c a   [enter]				,crash,  "execution terminates with error." reason:these two datetypes cannot be converted
##5. fmt_string: "%d"		input in the console with prompts:s abc   [enter]			,crash,  "execution terminates with error." reason:these two datetypes cannot be converted
##6. fmt_string: "%c"		input in the console with prompts:s abc   [enter]			,crash,  "execution terminates with error." reason:these two datetypes cannot be converted
##7. fmt_string: "%s %c"	input in the console with prompts:c a  c 5 [enter]			,crash,  "execution terminates with error." reason:the first  pair of  datetypes cannot be converted
##8. fmt_string: "%s %d"	input in the console with prompts:c a  d 80[enter]		    ,crash,  "execution terminates with error."	reason:the first pair of datetypes cannot be converted
##9. fmt_string: "%s %%"	input in the console with prompts:c a  % [enter] [enter]	,crash,  "execution terminates with error."	reason:the first datetypes cannot be converted
##10. fmt_string: "%d"		input in the console with prompts:d a [enter]				,crash,  "execution terminates with error."	reason:input error.The user is supposed to enter a number instead of a char.
.data 
space:		.asciiz " "
nl:			.asciiz "\n"
table:		.asciiz "\t"
string_buf:	.space 20
printf_buf: .space 2 
fmt_buf:	.space 100
prompt:		.asciiz "The variables will be printed in input order.\n"
note:		.asciiz "NOTE:\n1.The program will crash if the user want to do \"string to character\" or \"character to string\" or \"decimal to string\" or \"string to decimal \" transformation.The program will end with message in console showing\"program terminates with error.\"\n2.The input valriables should be input in the corresponding order in the format string,or strange output may occur.The ESC ,%%,can not be in a part of data transformation,make sure that no variable is input for %% operation.\n"
note2:		.asciiz "3.At most one string can be input in a format string .\n4.please input one char when entering char,or the second or third char will be read after the next prompt,which may cause program crash.\n5.When entering decimal numbers,the program will crash if the user enter characters.\n"
prompt1:	.asciiz "Please enter the format string that contains  %+datatype (input like %d%c%d,at most 3 embedded formats,only input one string once or the program will only print the latest one):\n"
argument:	.asciiz "\nEnter the variable (press [Enter] if no variable provided):"
inputtype:	.asciiz "\nEnter the type of input value(d,s,%,c),press [Enter] if no other variable types:"
	.text 
	.globl main 
 main: 
	la $a0,note		#print note
	li $v0,4
	syscall
	
	la $a0,note2	#print rest of note
	li $v0,4
	syscall	
	la $a0,prompt1
	li $v0,4
	syscall			#print prompt1
	
	la $a0,fmt_buf
   	 li $a1,100		#limits the length of input format string
	li $v0,8
	syscall
	
	li $s4,0		#initialize $s4 to count 
input:	
	beq $s4,3,start1
	la $a0,inputtype
	li $v0,4
	syscall			#prompt for the input type
	
	li $v0,12
	syscall
	move $t0,$v0
	
	beq $t0,10, start1
	beq $t0,'d',input_int
	beq $t0,'s',input_str 
	beq $t0,'c',input_char
	beq $t0,'S',input_str
	beq $t0,'%',input_per
start1:
	la $a0,nl		# go to a new line
	li $v0,4
	syscall
	
	la $a0,fmt_buf
	bgt $s4,1 case2		
	move $a1,$s1		#case only one input
	jal printf
	j exit
case2:					#case only two inputs
	beq $s4,3 case3		
	move $a1,$s2		
	move $a2,$s1
	jal printf
	j exit
case3:					#case 3 inputs
	move $a1,$s3
	move $a2,$s2
	move $a3,$s1
	jal printf
	j exit
	
printf: 	
 	subu  $sp, $sp, 36 	# set up the stack frame 
 	sw 	$ra, 32($sp)  	
	sw 	$fp, 28($sp)  	
	sw 	$s0, 24($sp)  	
	sw 	$s1, 20($sp)  	
	sw 	$s2, 16($sp)  	
	sw 	$s3, 12($sp)  	
	sw 	$s4, 8($sp)  	
 	sw  $s5, 4($sp)  	
	sw 	$s6, 0($sp) 	# save local variables 
 	addu  $fp, $sp, 36 
 	 
 	# grab the arguments 
 	move $s0, $a0 	 	# fmt string 
 	move $s1, $a1 	 	# arg1, optional 
 	move $s2, $a2 	 	# arg2, optional 
 	move $s3, $a3 	 	# arg3, optional 
 	li 	$s4, 0 	 	# set # of fmt = 0 
 	la 	$s6, printf_buf 	# set s6 = base of buffer 
	la $a0,prompt
	li $v0,4
	syscall
 
printf_loop: 	 	# process each character at fmt  	
	lb 	$s5, 0($s0)  	# get the next character  	
	addu $s0, $s0, 1  	# $s0 pointer increases 
 	beq $s5, '%', printf_fmt 	 	 	 	 
 	beq $0, $s5, printf_end 	# if zero, finish 
 	 
printf_putc:   #?????
 	sb 	$s5, 0($s6) # otherwise, put this char  	
	sb 	$0, 1($s6) # into the print buffer  	
	move $a0, $s6 	# and print it using syscall 
 	li 	$v0, 4  	
	syscall 
 	j  	printf_loop 
printf_fmt: 
 	lb 	$s5, 0($s0) # get the char after '%'  	
	addu $s0, $s0, 1 
	# check if already processed 3 args.  	
	beq $s4, 3, printf_loop 	#if 3 args processed,ingnore the rest	 	 
 
 
# if 'd', then print as a decimal integer 
 	beq $s5, 'd', printf_int 	 
# if 's', then print as a string 
 	beq $s5, 's', printf_str 	 
# if 'c', then print as an ASCII char  	
	beq $s5, 'c', printf_char  
# if '%', then print as a '%'  	
	beq $s5, '%', printf_perc
# if 'S', then print as a all-upper-case string
	beq $s5,'S',printf_S
 	j  	printf_loop 
 printf_shift_args: 
 	move $s1, $s2  	
	move $s2, $s3 
 	addi $s4, $s4, 1 # increment no. of args processed 
 	j 	printf_loop 
 	 
printf_int:  	 	# printf('%d', 100)  	
	move $a0, $s1 	# print the value stored in $s1 
 	li 	$v0, 1  	
	syscall 

	la $a0,space		# print some spaces
	li $v0,4
	syscall	
 	j 	printf_shift_args 
printf_char:		#printf("%c",'A')
	move $a0,$s1	#print the value stored in $s1
	li $v0,11
	syscall
	
	la $a0,space		# print some spaces
	li $v0,4
	syscall	
	
	j printf_shift_args
printf_perc:		#printf "%" char
	li $a0,37		#ascii code of "%" is 37 
	li $v0,11
	syscall

	la $a0,space		# print some spaces
	li $v0,4
	syscall	
	j printf_shift_args
printf_str:			#printf("%s",str)
	move $a0,$s1
	li $v0,4
	syscall			#print the value stored in $s1

	la $a0,space		# print some spaces
	li $v0,4
	syscall	
	
	j printf_shift_args
printf_S:		#print the uppcase of string
	lb $t3,($s1) 
	addi $s1,$s1,1	#$s1 pointers to the next char
	bgt $t3,122,notlower	#go to notlower if it is not in the lowercase ascii range
	blt $t3,97,notlower		#go to notlower if it is not in the lowercase ascii range
	addi $a0,$t3,-32	#the ascii code between lowercase and uppcase is 32
	li $v0,11
	syscall
	bne $t3,$zero,printf_S

	j printf_shift_args
notlower:
	move $a0,$t3
	li $v0,11				#if it is not lower letter,print directly
	syscall
	bne $t3,$zero,printf_S
	
	j printf_shift_args	 
printf_end: 
 	lw 	$ra, 32($sp)  	
	lw 	$fp, 28($sp)  	
	lw 	$s0, 24($sp)  	
	lw 	$s1, 20($sp)  	
	lw 	$s2, 16($sp)  	
 	lw  $s3, 12($sp)  
	lw 	$s4, 8($sp)
	lw 	$s5, 4($sp)  	
	lw 	$s6, 0($sp) 
 	addu $sp, $sp, 36 
 	jr 	$ra 
exit:  	
	li 	$v0, 10 
 	syscall 
 
input_per:
	la $a0,argument
	li $v0,4		#prompt for argument
	syscall
	
	li $v0,12
	syscall
	move $s0,$v0
	j input_shiftargs
input_int:
	la $a0,argument
	li $v0,4		#prompt for argument
	syscall
	
	li $v0,5		#input value is decimal
	syscall
	move $s0,$v0
	j input_shiftargs
input_str:
	la $a0,argument
	li $v0,4		#prompt for argument
	syscall
	
	la $a0,string_buf
	li $a1,20
	li $v0,8
	syscall
	la $t1, string_buf
change:
	addi $t1,$t1,1
	lb $t0,($t1)	
	bne $t0,10,change
	move $t0,$zero
	sb $t0,($t1)
	
	la $s0,string_buf 
	j input_shiftargs
input_char:
	la $a0,argument
	li $v0,4		#prompt for argument
	syscall
	
	li $v0,12		
	syscall
	move $s0,$v0
	j input_shiftargs
input_shiftargs:
	move $s3,$s2
	move $s2,$s1
	move $s1,$s0
	addi $s4,$s4,1
	j input
 
 
