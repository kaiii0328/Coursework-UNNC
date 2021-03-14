//20124870 scywj1 Wangkai JIN
#include <stdio.h>
#include <stdlib.h>
#include <string.h> 
#include <math.h>
#include <ctype.h>
#include <stdbool.h>
typedef struct location * Ptrtolocation ;
struct location{
	char name[17];
	float latitude;
	float longitude;
	Ptrtolocation next;
};
typedef Ptrtolocation spot;
void printmenu(){
	printf("1) Load a target file \n");
	printf("2) Show current targets \n");
	printf("3) Search a target \n");
	printf("4) Plan an airstrike \n");
	printf("5) Execute an airstrike \n");
	printf("6) Quit \n");
}
char* s_gets(char* st, int n){		//a  function to read string
	char* ret_val;
	char * find;

	ret_val = fgets(st, n, stdin);
	if (ret_val)
	{
		find = strchr(st, '\n');
		if (find)
			*find = '\0';
		else
			while (getchar() != '\n')
				continue;
	}
	return ret_val;
}

bool insert(spot *s,char name[],float latitude,float longitude,int i){
	spot tmp,pre;
	int ct=0;
	
	pre=*s;
	while (pre && ct<i-1){
		pre=pre->next;
		ct++;
	}
	if (ct!=i-1){
		printf("insertion error.\n");
		return false;
	}
	else {
		tmp=(spot)malloc(sizeof (struct location));
		strcpy(tmp->name,name);
		tmp->latitude=latitude;
		tmp->longitude = longitude;		
		tmp->next=NULL;
		pre->next=tmp;
		return true;
	}
}
void printmap(spot s, int num){
	int i,x,y,j;
	char map[40][80];
	for (i=0;i<40;i++)
		for (j=0;j<80;j++)	{
			if (i==0 || i==39)	map[i][j]='*';
			else if (j==0 || j==79)	map[i][j]='*';
			else
				map[i][j]=' ';
	
	}	
	while (s){
		x=s->latitude/1.25;
		y=s->longitude / 2.5;
		map[y][x]='#';
		s=s->next;
	}

	
	for (i=0;i<40;i++)
		for (j=0;j<80;j++){
			printf("%c",map[i][j]);
			if (j==79)
				printf("\n");
		}
}
int find(spot *s,char a[]){
	spot pre=*s;
	int i=0;
	while (pre && strcmp(pre->name,a)){
		pre=pre->next;
		i++;
	}
	if (!strcmp(pre->name,a))
		return i;
	else
		return 0;		
}
int Delete(spot *s,int i){
	spot tmp,pre;
	int ct=0;
	pre=*s;
	while (pre && ct<i-1){
		pre=pre->next;
		ct++;
	}
	if (pre==NULL || ct!=i-1)
		return false;
	else
		tmp=pre->next;
		pre->next=tmp->next;
		free(tmp);
		return true;	
}
int main(){
	char input[5];		//array input[5] stores input value
	char tmp[17];		//array tmp[15] is uesed to store the value scanned from file
	char filename[10];	//store the input file name
	spot s,head;
	s=(spot) malloc (sizeof (struct location));
	s->next=NULL;	
	head=s;
		
	struct location in;		// in  temporarily stores the infor for one spot
	int ct,j,spotnum,n,i=1;		int flag=0;
	FILE *fp;
	
	printmenu();
	printf("Option: ");
	while (s_gets(input,5)!=NULL && strcmp(input,"6")){
		if (input[0]=='1'){
			printf("Enter a target file: ");
			s_gets(filename,20);
			if ((fp = fopen(filename,"r"))==NULL){
				printf("Invalid file1.\n");
			}
				
		while (fscanf(fp,"%s",tmp)==1 ){
			if (strlen(tmp)>15){
				printf("Invalid file.12\n");	//if the digit of  name/latitude/longitude 
				break;						// is greater than 15,printf()
			}
			
			if (ct==0)	{	//copy the name of spot in temporar
					strcpy(in.name,tmp);
					ct++;
				}				
			else if (ct==1){	//check input of latitude
    			if(strspn(tmp, ".0123456789")==strlen(tmp)){
						in.latitude=atof(tmp);
						if (in.latitude <0 || in.latitude > 100){	//if latitude is not in[0..100]
							printf("Invalid file4.\n");
        					break;
						}
						ct++;
				}
    			else{
        			printf("Invalid file5.\n");
        			break;
    			}
    		}
			
			else if (ct==2){	//check input of longitude
					if(strspn(tmp, ".0123456789")==strlen(tmp))	{	// check if string contains number and dot only
						in.longitude=atof(tmp);
						
						if (in.longitude <0 || in.longitude > 100){		//if longitude is not in [0..100]
							printf("Invalid file6.\n");
        					break;
						}
						if (i==1){
							j=insert(&s,in.name,in.latitude,in.longitude,i);
							++i;							
						}
						else{		
							s=head->next;			
							while (s){
								if (sqrt((s->latitude-in.latitude)*(s->latitude-in.latitude)+(s->longitude-in.longitude)*(s->longitude-in.longitude))<0.1 ){
									strcpy(in.name,'\0');
									in.latitude=0;
									in.longitude=0;
									flag=1;
									break;							
								}
								s=s->next;
							}
							s=head;
							if (!flag) {								
								j=insert(&s,in.name,in.latitude,in.longitude,i);
								++i;	
								
							}
																
						}
						ct++;	//3rd ct++,finish one spot input
					}
    				else{
        				printf("Invalid file7.\n");
        				break;
    				}
			}
    		else{
        			printf("Invalid file8.\n");
        			break;
    		}					
			if (ct==3) {
				ct=0; 	//renew ct
			}
		}
		
		spotnum=i;
		if (fclose(fp) != 0)
			fprintf(stderr, "Error closing file,\n");
		 
		}
		
		else if (input[0]=='2'){
			if (spotnum>1) {	//if the array is empty,return to main menu
				s=head->next;
				while (s){
					printf("%s %f %f \n",s->name,s->latitude,s->longitude);
					s=s->next;
				}					
				//printmap(head->next,i);
			}
		}
			
		else if (input[0]=='3'){
			printf("Enter the name: ");
			int l,flag=0;
			char tem[16];
			if (s_gets(tem,15)!=NULL && tem[0]!='\n'){
				s=head->next;
				l=strlen(tem);
			 	while (s){
			 		if (!strncmp(tem,s->name,l))	{		//if the name matches,print all the infor about this location
			 			flag=1;
			 			printf("%s %f %f \n",s->name,s->latitude,s->longitude);
						break;
					}
					s=s->next;
				}							 
			}
			if (!flag)	printf("Entry does not exit.\n");
			
			 
		}
	
		else if (input[0]=='4'){
			double  r;
			printf("Enter predicted latitude: ");
			scanf("%f",&in.latitude);
			printf("Enter predicted longitude: ");
			scanf("%f",&in.longitude);
			printf("Enter ratio of damage zone: ");
			scanf("%lf",&r);
			getchar();
			s=head->next;
			while (s){
				if (sqrt((s->latitude-in.latitude)*(s->latitude-in.latitude)+(s->longitude-in.longitude)*(s->longitude-in.longitude))<r ){
						printf("%s %f %f \n",s->name,s->latitude,s->longitude);
				}
				s=s->next;
			}
		}
		else if (input[0]=='5'){
			double r; int l;	
			spot pre;
			ct=0;
			struct location dest[10];
			printf("Enter targeted latitude: ");
			scanf("%f",&in.latitude);
			printf("Enter targeted longitude: ");
			scanf("%f",&in.longitude);
			printf("Enter ratio of damage zone: ");
			scanf("%lf",&r);	
			getchar();	//read '\n'
			s=head->next;
			while (s){
				int flag=0;
				if (sqrt((s->latitude-in.latitude)*(s->latitude-in.latitude)+(s->longitude-in.longitude)*(s->longitude-in.longitude))<r){
				
					j=find(&head,s->name);
					l=strlen(s->name);
					strncpy(dest[ct].name,s->name,l);
					dest[ct].latitude=s->latitude;
					dest[ct].longitude = s->longitude;
					ct++;	flag=1;
					i=Delete(&head,j);					
				}	
				if (flag)	s=pre->next;	
				else{
					pre=s;
					s=s->next;		
				}			
			} 
			printf("%d target destroyed.\n",ct);
			for (j=0;j<ct;j++){
				printf("%s %f %f\n",dest[j].name,dest[j].latitude,dest[j].longitude);
			}
		}
		else
			printf("Unknown option.\n");
		printf("Option: ");
	}
	
	
	return 0;
}
