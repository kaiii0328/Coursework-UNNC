	.data 
prompt1: 	.asciiz "Please type in a string no more than 99 characters: "
rs_string:  .asciiz "The updated string is: "
masks: 		.word 0x00FF0000 0xFF00FFFF #masks 
buffer: 	.space 100 # space to store the string, 1 extra byte to store null
	.text
	.globl main
main:
	la $a0,prompt1 #prompt for string
	li $v0,4	
	syscall
	
	la $a0,buffer	#buffer address to $a0
	li $a1,100		#string length to $a1
	li $v0,8		#read string
	syscall			#读入的字符串是在 buffer 里吗还是圿 v0 里面？？＿
	
	la $a0,re_string	#the result =
	li $v0,4
	syscall
	
	#update the string
	la $t0,buffer
	lw $s0,($t0) #4 letters in s0
	la $t1,masks
	lw $s1,($t1)	#masks in s1
	lw $s2,4($t1)	#masks in s2
	and $s3,$s0,$s1	#masks out bytes 1,2,4
	srl $s3,$s3,16	#get the third character of the string
	addi $s3,$s3,-32 	#up upper case letters
	sll $s3,$s3,16		#shift upper case letter in 3rd byte
	and $s4,$s0,$s2		#mask out third letter
	or $s4,$s4,$s3		#new string in #s4
	
	la $a0,buffer
	sw $s4,($a0)
	li $v0,4
	syscall
	
	li $v0, 10 
	syscall

	
	
	


