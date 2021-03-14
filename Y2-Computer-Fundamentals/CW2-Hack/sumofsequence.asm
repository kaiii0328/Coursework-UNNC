@16	//ram[16] represents i
M=1
@17	//ram[17] represents sum
M=0

@16
D=M
@0
D=D-M
@18		//if i>ram[0] goto 18
D;JGT

@16
D=M
@17
M=M+D	//SUM+=i
@16
M=M+1	//i++
@4	//GOTO 4
0;JMP

@17
D=M
@1
M=D	//RAM[1]=SUM
@22
0;JMP
