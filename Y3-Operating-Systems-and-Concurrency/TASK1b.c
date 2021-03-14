#include <stdio.h>
#include <stdlib.h>
#include "coursework.h"
#include "linkedlist.h"

typedef struct element* PtrtoNode;
typedef struct process* PtrtoProc;
typedef struct {
	PtrtoNode elem;
	PtrtoNode pTail;
}PriorityQueue;
typedef struct{
	PriorityQueue *que;
	int tableSize;
}ProcessTable;

void printfInitialInfo(ProcessTable *p);
void InitProcessTable(ProcessTable *p);
void InsertProcessTable(ProcessTable *p,PtrtoProc proc,int index);
void startExecution(ProcessTable *p);
void freeMemory(ProcessTable *p);
int main(){
	FILE *fp;
	int i;
	ProcessTable p;
	InitProcessTable(&p);
	for (i=0;i<NUMBER_OF_PROCESSES;i++){
		PtrtoProc pData = generateProcess();
		InsertProcessTable(&p,pData,pData->iPriority);
	}
	
	//print processes info	
	printfInitialInfo(&p);
	startExecution(&p);
	freeMemory(&p);
	
	
	return 0;
}

void freeMemory(ProcessTable *p){
	for (int i=0;i<(p->tableSize);i++){
		if (p->que[i].elem != NULL)
			free(p->que[i].elem);
	}
	free(p->que);
}

void startExecution(ProcessTable *p){
	FILE *fp = fopen("TASK1B.txt","a");
	int totalResponseTime=0,totalTurnaroundTime=0;
	int responseTime =0;
	int turnaroundTime = 0;
	//execution	
	for (int i=0;i<(p->tableSize);i++){
		if (p->que[i].elem != NULL){
			PtrtoNode head = p->que[i].elem;
			PtrtoNode pTail = p->que[i].pTail;
			while (head->pNext != NULL){
				struct timeval startTime,endTime;
				startTime.tv_sec = 0; endTime.tv_sec = 0;
				PtrtoProc pCurProc = removeFirst(&(head->pNext),&pTail);
				runPreemptiveJob(pCurProc,&startTime,&endTime);
								
				if (pCurProc->iRemainingBurstTime == 0){	
					//printf info + tt time	
					
					responseTime += pCurProc->iPreviousBurstTime;
					turnaroundTime = responseTime;
					totalTurnaroundTime += turnaroundTime;
					fprintf(fp,"Process id = %d, Priority = %d,Previous Burst Time = %d, Remaining BurstTime = %d, Turnaround Time = %d\n", pCurProc->iProcessId,pCurProc->iPriority, pCurProc->iPreviousBurstTime,pCurProc->iRemainingBurstTime, turnaroundTime);
					free(pCurProc);
				}else{
					//printf info( plus response time if first executed)
					if ((pCurProc->iInitialBurstTime - pCurProc->iRemainingBurstTime) == TIME_SLICE){
						fprintf(fp,"Process id = %d, Priority = %d,Previous Burst Time = %d, Remaining BurstTime = %d, Response Time = %d\n", pCurProc->iProcessId,pCurProc->iPriority, pCurProc->iPreviousBurstTime,pCurProc->iRemainingBurstTime, responseTime);
						totalResponseTime +=responseTime;
						
					}
					else{
						fprintf(fp,"Process id = %d, Priority = %d,Previous Burst Time = %d, Remaining BurstTime = %d\n", pCurProc->iProcessId,pCurProc->iPriority, pCurProc->iPreviousBurstTime,pCurProc->iRemainingBurstTime);
					}
					responseTime += TIME_SLICE;
					
					//add to last
					addLast(pCurProc,&(head->pNext),&pTail);
				}
			}
		}
	}
	
	fprintf(fp,"Average response time %.3f\n",(float)totalResponseTime/NUMBER_OF_PROCESSES);
	fprintf(fp,"Average turnaround time %.3f\n",(float)totalTurnaroundTime/NUMBER_OF_PROCESSES);
	fclose(fp);
}
void printfInitialInfo(ProcessTable *p){
	FILE *fp;
	fp = fopen("TASK1B.txt","w");
	fprintf(fp,"Process List\n");
	for (int i=0;i<p->tableSize;i++){
		if (p->que[i].elem !=NULL){
			fprintf(fp,"Priority %d\n",i);
			PtrtoNode head = p->que[i].elem;
			PtrtoNode pIterator = head->pNext;
			while (pIterator != NULL){
				PtrtoProc pCurProc = pIterator->pData;
				fprintf(fp,"    Process id = %d, Priority = %d,Initial Burst Time = %d, Remaining BurstTime = %d\n", pCurProc->iProcessId,pCurProc->iPriority, pCurProc->iInitialBurstTime,pCurProc->iRemainingBurstTime);
				pIterator = pIterator->pNext;
			}
		}
	}
	fprintf(fp,"END\n\n");
	fclose(fp);
}

void InitProcessTable(ProcessTable *p)  {  
     int i;           
     p->tableSize=MAX_PRIORITY;  
     p->que=malloc(MAX_PRIORITY*sizeof(PriorityQueue));  
     for(i=0;i<(p->tableSize);i++){
        p->que[i].elem = NULL;  
        p->que[i].pTail = NULL;
     }             
              
  }  
  
void InsertProcessTable(ProcessTable *p,PtrtoProc proc,int index){  
	if (p->que[index].elem == NULL){
		PtrtoNode head = (PtrtoNode)malloc(sizeof(struct element));
		PtrtoNode next = (PtrtoNode)malloc(sizeof(struct element));
		next->pData = proc;
		next->pNext = NULL;
		head->pNext = next;		
		p->que[index].elem = head;		
		p->que[index].pTail = next;
	}else {
		addLast(proc,&(p->que[index].elem->pNext),&(p->que[index].pTail));
	}

}   
 

