	.data
prompt: .asciiz "please type in a string"
result:	.asciiz "the result is "
buffer:	.space 10
	.text
	.globl main
	
main:
	la $a0,prompt
	li $v0,4
	syscall
	
	la $a0,buffer
	li $v0,8		#read string
	syscall
	
	la $a0,result	#the result =
	li $v0,4
	syscall
	
	la $s0,buffer
	#print and upcase
loop0:	#point to the end of the string
	lb $t0,($s0)
	addi $s0,$s0,1
	bne $t0,$zero,loop0
	addi $s0,$s0,-1	
	
	la $s1,buffer
loop:
	lb $a0,($s1)
	addi $s1,$s1,1	
	addi $t1,$t1,1	#t1 to count 3rd char
	beq $t1,3,printu
printn:
	li $v0,11		#print normal char
	syscall
	beq $s1,$s0,stop
	j loop
printu:
	sub $a0,$a0,32
	li $v0,11
	syscall
	j printn
stop:
	li $v0,10
	syscall
