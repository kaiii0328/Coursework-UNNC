	.data
uint: .word 0x0000002d 0x00000402
	.text
	.globl main
main:
	la $t0,uint	#load the base address
	lw $a0,($t0) #load the first integer
	li $v0,1
	syscall
	lw $a0,4($t0)
	li $v0,1
	syscall
	
	li $v0,10
	syscall
