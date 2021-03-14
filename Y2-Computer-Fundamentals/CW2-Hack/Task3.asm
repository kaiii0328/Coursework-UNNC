@ct
M=0   //initialize counter
@temp
M=0   //initialize temp to store multiplication result
@0
D=M-1    //D=X-1
@EQUAL
D;JEQ    //if D=0(X=1),JUMP TO EQUAL,ct=0
@4
D=A     //D=4
@0
D=D-M
@END1
D;JGT    //if 4>x,jump to END1,ct=1

@2
D=A  //D=2
@1
M=D   //RAM[1]=2, put the base into use
@3
M=D  //RAM[3]=2,storing base

(MULT)
@1
M=M-1  //RAM[1]=2-1
@3
D=M		//D=2
@temp
M=M+D   //temp=temp+2
@1
D=M    //D=RAM[1],the other multiplication operand
@MULT
D;JGT
@2
D=A
@ct
M=M+D   //first multiplication done,counter+2
@temp
D=M		//D=temp
@0
D=D-M	//D=temp=temp-x
@OUT	//if temp>x,jump to output
D;JGT

(LOOP)	
@2
D=A    //D=2
@1
M=D    //RAM[1]=2,
@t2
M=0    //initialize t2 to store temp mult result

(MULT2)
@1
M=M-1   //RAM[1]=RAM1-1
@temp
D=M  //D= last mult result
@t2
M=M+D    //t2=t2+temp
@1
D=M      //D=RAM1
@MULT2
D;JGT
@ct
M=M+1   //first multiplication done,counter+1
@t2
D=M
@temp
M=D		//temp=t2
@temp
D=M     //D=temp
@0
D=D-M   //D=temp-x
@LOOP
D;JLT   //if (temp-x)<0,jumpt to loop
@EQUAL
D;JEQ   // if (temp-x)=0,jump to equal

(OUT)	//if (temp-x)>0,output (ct=ct-1)
@ct
M=M-1	//ct=ct-1
(EQUAL)
@ct
D=M		//D=ct
@2
M=D		//RAM[2]=Z=ct
(END)
@END
0;JMP

(END1)   //case 2<=x<4
@2
M=1    //Z=RAM[2]=1
@END
0;JMP