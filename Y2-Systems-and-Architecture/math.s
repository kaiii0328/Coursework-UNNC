	.data
	.text
	.globl main
main: li $v0, 5				#read a number from console
		syscall
	  add $t1,$v0,$zero #$t1 has x
	  
	  li $v0, 5				#read a number from console
		syscall
	  add $t2,$v0,$v0   #t2 stores 2y
	  sub $s3,$t1,$t2	#s3 stores x-2y
	  li $t4,-40
	  add $a0,$s3,$t4
	  
	  li $v0,1
	  syscall
	  
	  li $v0,10
	  syscall
	  