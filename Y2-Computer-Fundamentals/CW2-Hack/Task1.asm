@sign
M=0  	//initialize sign=0,sign to store the result sign
@Temp	//initialize temp=0,
M=0		//Temp stores the result of repetitive addition
@3
M=1		//initialize RAM[3]=1
@2
M=1		//initialize Z=RAM[2]=1 
@0
D=M		//D=RAN[0]=X
@0
D=M
@10
M=D		//RAM[10]=initial value of x
@1
D=M
@11
M=D		//RAM[11]=initial value of y
@0
D=M
@CHECKY
D;JGT	//if x>0,check the sign of y
		//if x<0,do the following
@0		
M=!M
@0
M=M+1  //x=-x
@sign
M=1		//sign=RAM[16]=1
(CHECKY)
@1
D=M 	//D=RAM[1]=y
@START
D;JGT	//if y>0,go to start
@ZERO
D;JEQ	//if y=0,set RAM[3] and RAM[2]=-1
		//if y<0,y=-y
@1		
M=!M
@1
M=M+1 	//y=-y
@sign
M=!M
@2
D=A				//if sign=1,sign=0
@sign			//if  sign=0,sign=1
M=M+D		//hence,sign=!sign+2 
(START)
//decide whether x>y or x=y or x<y
@1
D=M		//D=RAM[1]=y
@6
M=D		//RAM[6]=D=y
@0
D=M   	//D=RAM[0]=x
@6
D=D-M   //D=x-y
@OUTPUT
D;JEQ	//if x=y,go to OUTPUT
@OUTPUT2
D;JLT	//if x<y,go to OUTPUT2
		// X>Y, do repetitive addition
@2
M=0		//initialize Z=RAM[2]=0 
(ADDITION)
@1
D=M 	//D=y
@Temp
M=M+D	//temp=temp+y
@2
M=M+1	//Z=Z+1
@Temp
D=M		//D=Temp
@4
M=D		//RAM[4]=Temp
@0
D=D-M	//Temp=Temp-X
@ADDITION
D;JLT	//if (temp-x)<0,go to addition
@OUTPUT
D;JEQ	//if (temp=x),output directly
@Temp	//Temp>x
D=M
@Temp2	//RAM[19]=Temp2
M=D 	//Temp2=Temp
@0
D=M		//D=X
@Temp2
M=M-D	//Temp2=Temp2-x
@Temp2	
D=M 
@Temp2	//
M=M+D	//Temp2=2(Temp2-x)
@1
D=M		//D=RAM[1]=y
@Temp2
M=M-D	//Temp2=2(Temp2-x)-y
// if (temp-x)>x-(temp-y),the result will  not be rounded
@Temp2
D=M
@OUTPUT
D;JLT	//if 2(Temp-x)-y<0,rounded up
@HALF
D;JEQ	//if 2(Temp-x)=y,x/y=(2n-1)/2,goto 0.5 situation
@2
M=M-1	//else 2(Temp-x)-y>0,rounded down

(OUTPUT)
@sign
D=M		//D=sign
@INITIAL
D;JEQ	//if sign=0,go to INITIAL
@2		// if sign=1,do the following
M=!M
@2
M=M+1	//z=-z
(INITIAL)
@10
D=M
@0
M=D		//x=RAM[10]=initial x
@11
D=M
@1
M=D		//y=RAM[11]=initial y
(END)
@END
0;JMP

(OUTPUT2)
@0
D=M		//D=RAM[0]=x
@7
M=M-D
@7
M=M-D	//RAM[7]=y-2x
@7
D=M
@HALF
D;JEQ	//if y=2x,go to half to deal with 0.5 situation
@OUTPUT
D;JLT	//if y-2x<0, round up,go to OUTPUT
@2
M=M-1	//if y-2x>0,no need to round up
@OUTPUT
0;JMP

(HALF)
@sign
D=M		//D=sign
@INITIAL
D;JEQ	//if sign=0,go to INITIAL
@2		// if sign=1,do the following
M=!M
@2
M=M+1	//z=-z
@2
M=M+1	//Z+=1
@10
D=M
@0
M=D		//x=RAM[10]=initial x
@11
D=M
@1
M=D		//y=RAM[11]=initial y
(END1)
@END1
0;JMP

(ZERO)
@3
M=-1	//RAM[3]=-1
@2
M=-1	//RAM[2]=-1
@10
D=M
@0
M=D		//x=RAM[10]=initial x
(END2)
@END2
0;JMP
