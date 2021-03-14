# fibonacci sequence
	.data
prompt1: .asciiz "Please enter a non-negative integer:"
prompt1_append1: .asciiz "Notice:1.If characters are entered,the first siginificant digits will be read in(e.g. the program will only read in \"2\" for input like \"2-r43\",or read in 2 for input like \"rq-v.2-gh4t5\")\n2.If a float number is entered,only the integer part will be read in. \n3.If no intergers are entered, the program will ouput \"0\" \n4.If a negative number is entered,the program will calculate its positive value.\n"
result:	 .asciiz "The result of Fibonacci number is:"
buffer: .space 10
    .text       
    .globl main
main:
	la $a0,prompt1_append1
	li $v0,4 		#printf prompt1_append
	syscall
	la $a0,prompt1
	li $v0,4 		#printf prompt1
	syscall
inputtest:
	la $a0,buffer
	la $a1,10		#the length of input string is 9
	li $v0,8
	syscall			#read the string that contains integer
	li $v0,0		#initialize $v0 to store input number later
	la $s0,buffer	#load string address to $s0
loop:	
	lb $t0,($s0)
	beq $t0, 10 ,start	#if $t0='\n',it it is the end of input,goto start
	bgt $t0,57,jump1	#if the ASCII code of current char is not in the range of [48,57],
	blt $t0,48,jump1	# goto jump1
	addi $t1,$t0,-48	#ascii code subtracting 48 is  the actual number
	mul $v0,$v0,10
	add $v0,$v0,$t1
	addi $s0,$s0,1	#s0 points to the next char in input string
	j loop
jump1:
    bgt $v0,0,start	#if $v0>0 and current char is not a number,goto start
	addi $s0,$s0,1	#s0 pointers to the next char in input string
	j loop
	####end of reading input ####
	
start:	
	move $a0,$v0	#$a0 stores the valid input number
	jal Fib			#call Fib function to calculate fibonacci result
End:
	move $s1,$v0	#$s1 stores the result
	la $a0,result
	li $v0,4		#print result string
	syscall
	
	move $a0,$s1
	li $v0,1		#printf the fibonacci result
	syscall			
	
	li $v0,10
	syscall			#end program

Fib:
	beq $a0,$zero,case0		#if $a0=0,return 0
	beq $a0,1,case1			#if $a0=1.return 1
	addi $sp,$sp,-4			#allocate memory in stack
	sw $ra,0($sp)
	addi $a0,$a0,-1
	jal Fib					#call Fib(n-1)
	addi $a0,$a0,1			#let $a0=n
	lw $ra,0($sp)			#pop the return address when call Fib(n) from stack
	add $sp,$sp,4
	sub $sp,$sp,4
	sw $v0,0($sp)			#push return of Fib(n-1) into stack
	sub $sp,$sp,4
	sw $ra,0($sp)			#push the return address when call Fib(n)
	
	sub $a0,$a0,2
	jal Fib					#call Fib(n-2)
	addi $a0,$a0,2			#let a0=n again
	lw $ra,0($sp)			#pop the return addresss when call Fib(n) from the stack
	addi $sp,$sp,4
	
	lw $s0,0($sp)			#pop the result of Fib(n-1) from the stack
	addi $sp,$sp,4
	
	add $v0,$v0,$s0			#return Fib(n-1)+Fib(n-2)
	jr $ra


case0:
	li $v0,0
	jr $ra
case1:
	li $v0,1
	jr $ra