	.data
prompt1: .asciiz "please type in a string no more than 100"
re_string: .asciiz "the reverse order is "
buffer: .space 100
	.text
	.globl main
	
main:
	la $a0,prompt1 #prompt for string
	li $v0,4	
	syscall
	
	la $a0,buffer	#buffer address to $a0
	li $a1,100		#string length to $a1
	li $v0,8		#read string
	syscall			#读入的字符串是在 buffer 里吗还是�? v0 里面？？�?
	
	la $a0,re_string	#the result =
	li $v0,4
	syscall
	
	la $s0,buffer
loop:
	lb $t0,($s0)
	addi $s0,$s0,1
	bne $t0,$zero,loop
	addi $s0,$s0,-1#????  与下面一句话一样，删掉之后对结果么有影响
	la $s1,buffer
	addi $s1,$s1,-1
loop2:
	addi $s0,$s0,-1	#???
	lb $a0,($s0)
	beq $s0,$s1,stop
	li $v0,11	#print char
	syscall
	j loop2	
stop:
	li $v0,10
	syscall
	
