.data 
uint: .word 0x0000000A 0x10000000 
.text 
.globl main 
main: 
la $t0, uint #load the base address 
lw $a0,4($t0)
li $v0,1
syscall



li $v0,10
syscall
