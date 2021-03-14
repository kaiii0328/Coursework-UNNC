@0
D=M 	//D=X
@base
M=D	//base=x
@3
M=1		//initialize RAM[3]=1
@1
D=M		//D=RAM[1]=y
@5
M=D		//RAM[5] stores y
@temp
M=0		// initialize temp=RAM[17]=0,temp to stores the first multiplication result
@temp2
M=0		//initialize temp2=RAM[18]=0,temp2 to stores the other mult result except first one
@2
M=0	//RAM[2]==0
@1
D=M	//D= RAM[1]=Y

@CASE1
D;JEQ	//if y=0,jump to CASE1, do z=1
@1
D=M-1
@CASE2
D;JEQ	//if y=1,jump to CASE2, z=x

(MULT)
@base
M=M-1	//x=x-1
@0
D=M
@temp
M=M+D  //temp=temp+x
@base
D=M
@MULT	//if base>0,do multiplication until base=0
D;JGT

@2
D=A			//D=2
@5
M=M-D		//y=RAM[5]=y-2



(loop)
@0
D=M 	//D=X
@base
M=D	//base=x
@temp2
M=0	//initialize temp2
@5
M=M-1	//y=y-1

(MULT2)
@base
M=M-1	//x=x-1
@temp
D=M
@temp2
M=M+D
@base
D=M
@MULT2	//if base>0,do multiplication until base=0
D;JGT

@temp2	//move temp2 to temp
D=M
@temp
M=D	//temp stores the temporary result
@5
D=M		//D=Y
@loop	//if D>0,repetitive multiplication
D;JGT

(ENDhalf)
@temp
D=M
@2
M=D 	//Z=RAM[2]=temp

(END3)
@END3
0;JMP

(CASE1)
@0
D=M	//D=x
@ERROR
D;JEQ	//if x=y=0,RAM[3]=RAM[2]=-1
@1
D=A	//D=1
@2
M=D	//Z=RAM[2]=1

(END1)
@END1
0;JMP

(CASE2)
@0
D=M	//D=RAM[0]=X
@2
M=D //Z=RAM[2]=X
(END2)
@END2
0;JMP

(ERROR)
@2
M=-1	//RAM[2]=-1
@3
M=-1	//RAM[3]=-1
@END1
0;JMP





