.data
buffer: .space 10
error:   .asciiz "invalid input!\n"
buf:	.space 2
	.text
	.globl main
	
main:
	li $v0,12
	syscall

	li $v0,11
	syscall
	
	li $v0,10
	syscall