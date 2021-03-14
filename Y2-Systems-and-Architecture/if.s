	.data
	.text
	.globl main
main:
	 li $v0,5
	 syscall
	 add $t1,$v0,$zero  #t1 stores x
	 
	 li $v0,5
	 syscall
	 add $t2,$v0,$zero  #t2 stores y
	 
	 li $v0,5
	 syscall
	 add $t3,$v0,$zero  #t3 stores z
	 
	 add $t4,$t1,$zero  #t4 represents m,m=x
	 li $s1,0
	 slt $s1,$t2,$t1	#if y<x,s1=1
	 beq $s1,$zero,else
	 add $t4,$t2,$zero   #t4 stores y
else:slt $s1,$t3,$t4    #if z<m, s1=1
	 beq $s1,$zero,secondelse
	 add $a0,$t4,$zero
	 li $v0,1
	 syscall
secondelse:
	 add $a0,$t4,$zero
	 li $v0,1
	 syscall
	 
	 li $v0,10
	 syscall
	 
	 
	 
	 
	 