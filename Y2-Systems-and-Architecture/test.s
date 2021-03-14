	.data
buffer: .space 10
error:   .asciiz "invalid input!\n"
	.text
	.globl main
	
main:
	la $a0,buffer
	li $a1,10
	li $v0,8
	syscall
	
	la $t0,buffer
	lb $a0,($t0)
	li $v0,1
	syscall
	
	li $v0,10
	syscall
