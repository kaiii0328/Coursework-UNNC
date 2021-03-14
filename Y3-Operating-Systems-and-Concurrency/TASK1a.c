#include "stdlib.h"
#include "stdio.h"
#include "coursework.h"
#include "linkedlist.h"

typedef struct element* PtrtoNode;
typedef struct process* PtrtoProc;

void bubble_sort(PtrtoNode *head);
void FreeMemory(PtrtoNode *head);
void startExecution(PtrtoNode *head,PtrtoNode *pTail);
int main(){
	int i=0;
	PtrtoNode  head=NULL,pTail=NULL,pIterator=NULL;
	head = (PtrtoNode)malloc(sizeof(struct element));
	if (head == NULL){
		printf("unable to allocate memory.\n");
		exit(-1);
	}
	head->pNext = NULL;
	for (i=0;i<NUMBER_OF_PROCESSES;i++){
		PtrtoProc pData = generateProcess();
		addFirst(pData,&head->pNext,&pTail);
	}
	bubble_sort(&head);	
	startExecution(&head,&pTail);
	
	FreeMemory(&head);

	return 0;
}

void startExecution(PtrtoNode *head,PtrtoNode *tail){
	FILE *fp = fopen("TASK1A.txt","w");
	PtrtoNode pHead = *head;
	PtrtoNode pTail = *tail;
	int responseTime = 0, turnaroundTime = 0,totalResTime=0,totalTTime=0;
	while (pHead->pNext != NULL){
		PtrtoProc pCurProc = (PtrtoProc)removeFirst(&pHead->pNext,&pTail);
		struct timeval startTime,endTime;
		startTime.tv_sec = 0; endTime.tv_sec = 0;
		runNonPreemptiveJob(pCurProc,&startTime,&endTime);
		responseTime=turnaroundTime;
		turnaroundTime = responseTime +pCurProc->iPreviousBurstTime;
		totalResTime +=responseTime;
		totalTTime +=turnaroundTime;		
		fprintf(fp,"Process id = %d, Previous Burst Time = %d, Remaining BurstTime = %d, Response Time = %d, Turnaround Time = %d\n",pCurProc->iProcessId,pCurProc->iPreviousBurstTime,pCurProc->iRemainingBurstTime,responseTime,turnaroundTime);
	}
	fprintf(fp,"Average response time %.3f\n",(float)totalResTime/NUMBER_OF_PROCESSES);
	fprintf(fp,"Average turnaround time %.3f\n",(float)totalTTime/NUMBER_OF_PROCESSES);
	fclose(fp);
}

void bubble_sort(PtrtoNode *head){
	PtrtoNode pHead,pFirst,pSecond,pEnd=NULL;
	pHead = *head;
	int i =0, j=0;
	for (i=NUMBER_OF_PROCESSES-1;i>=0;i--){
		pFirst =pHead->pNext;
		pSecond = pFirst->pNext;
		for (j=0;j<i;j++){
			PtrtoProc pFirstData = pFirst->pData;
			PtrtoProc pNextData = pSecond->pData;
			if (pFirstData->iInitialBurstTime > pNextData->iInitialBurstTime){
				PtrtoProc temp = pFirstData;
				pFirst->pData = pNextData;
				pFirst->pNext->pData=temp;
			}
			pFirst = pSecond;
			pSecond = pSecond->pNext;
		}
	}
}
void FreeMemory(PtrtoNode *head){
	PtrtoNode temp=NULL,pHead;
	pHead = *head;
	while (pHead->pNext != NULL){
		temp = pHead->pNext;
		pHead->pNext = temp->pNext;
		free(temp);
	}
	free(pHead);

}
