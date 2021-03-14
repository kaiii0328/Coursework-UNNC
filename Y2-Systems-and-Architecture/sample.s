	.data
nl: .asciiz "\n"
	.text
	.globl main
main: li $v0,5
	  syscall
	  add $a0,$v0,$v0
	  li $v0,1
	  syscall
	  la $a0,nl
	  li $v0, 4
	  syscall
	  li $v0,10
	  syscall