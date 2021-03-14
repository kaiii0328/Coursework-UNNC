# fibonacci sequence
	.data
prompt1: .asciiz "Please enter a non-negative integer(If characters are entered,the first siginificant digits will be read in.e.g. the program will only read in \"2\" for input like \"2-r43\",or read in 2 for input like \"rq-v.2-gh4t5\")\n"
prompt1_append: .asciiz "(If a float number is entered,only the integer part will be read in. \t If no intergers or negative numbers are entered, the program will ouput \"0\") \n"
result:	 .asciiz "The result of Fibonacci number is:"
buffer: .space 10
    .text       
    .globl main
main:
	la $a0,prompt1
	li $v0,4 		#printf prompt1
	syscall
	la $a0,prompt1_append
	li $v0,4 		#printf prompt1_append
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
	bgt $t0,57,jump1
	blt $t0,48,jump1
	addi $t1,$t0,-48	#ascii code subtracting 48 is  the actual number
	mul $v0,$v0,10
	add $v0,$v0,$t1
	addi $s0,$s0,1	#s0 points to the next char in input string
	j loop
jump1:
    bgt $v0,0,start	#if $v0>0 and current char is not a number,goto start
	addi $s0,$s0,1	#s0 pointers to the next char in input string
	j loop
	####end of inputtest ####
	
start:	
	move $s2,$v0	#$s2 stores the counter
	move $t0,$v0	#move number to $t0
	blt $t0,2,End
	li $t0,0
	li $v0,1
Fib:
	add $t1,$t0,$v0
	move $t0,$v0		#$t0 = Fib(n-2)
	move $v0,$t1	#$v0 = Fib(n-1)
	sub $s2,$s2,1	
	bgt $s2,1,Fib
	
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
	
