	.data
yes: 	.asciiz "true"
no:	 .asciiz "false"
	.text

	.globl main
main:
	li $v0,5
	syscall
	add $t0,$zero,$v0 #t0 stores A
	
	li $v0,5
	syscall
	add $t1,$zero,$v0 #t1 stores B
	
	slt $t3,$t0,$t1 #which one is bigger
	beq $t3,1,case1
case2:	#t0>t1 
	sub $t0,$t0,$t1
	beq $t0,$zero,success
	slt $t4,$t0,$zero
	beq $t4,1,fail
	j case2

case1:  #t0<t1
	sub $t1,$t1,$t0
	beq $t1,$zero,success  #==0
	slt $t4,$t1,$zero,	#<0
	beq $t4,1,fail
	j case1
	
success:
	la $a0,yes
	li $v0,4
	syscall
	
	li $v0,10
	syscall
fail:
	la $a0,no
	li $v0,4
	syscall
	
	li $v0,10
	syscall
	
	
	
	
	
	
