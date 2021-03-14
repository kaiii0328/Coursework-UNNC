  #include <pthread.h>
#include <semaphore.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include "coursework.h"
#include "linkedlist.h"

typedef struct element* PtrtoNode;
typedef struct process* PtrtoProc;

sem_t empty, full;
PtrtoNode  head=NULL,pTail=NULL;	
int jobsGenerate =0,jobsDone=0;
pthread_mutex_t mutex;
int totalResTime=0,totalTTime=0;
FILE *fp;

void insert_sort(PtrtoNode *head,PtrtoProc process);
void FreeMemory(PtrtoNode *head);
void *producer(void *pno)
{   
	int item;
	while(1) {          				
		sem_wait(&empty);
		
		pthread_mutex_lock(&mutex);
		if (jobsGenerate >= MAX_NUMBER_OF_JOBS){
			pthread_mutex_unlock(&mutex);			
			break;
		}
        //generate process
		PtrtoProc pData = generateProcess();
        //insert to queue
		insert_sort(&head, pData);
		
		fprintf(fp,"Producer %d, Items Produced %d, New Process ID = %d, Burst Time = %d\n", *((int *)pno),jobsGenerate,pData->iProcessId,pData->iInitialBurstTime);	
		jobsGenerate++;
		
		pthread_mutex_unlock(&mutex);
		sem_post(&full);
	}
	pthread_exit(0);
}
void *consumer(void *cno)
{   
	int responseTime=0,turnaroundTime=0;
	while(1){
		sem_wait(&full);
		pthread_mutex_lock(&mutex);
		if (jobsDone < MAX_NUMBER_OF_JOBS){
        // runjob
			struct timeval startTime,endTime;
			startTime.tv_sec = 0; endTime.tv_sec = 0;
			PtrtoProc pCurProc = (PtrtoProc)removeFirst(&head->pNext,&pTail);
			pthread_mutex_unlock(&mutex);	
			
			runNonPreemptiveJob(pCurProc,&startTime,&endTime);
			
			pthread_mutex_lock(&mutex);
			responseTime = getDifferenceInMilliSeconds(pCurProc->oTimeCreated,startTime);
			turnaroundTime = responseTime + pCurProc->iInitialBurstTime;
			totalTTime += turnaroundTime;
			totalResTime += responseTime ;
			jobsDone++;
			fprintf(fp,"Consumer %d: Process ID %d, Previous Burst Time %d, New Burst Time 0, Response Time %d, Turnaround Time = %d\n",*((int *)cno),pCurProc->iProcessId,pCurProc->iPreviousBurstTime,responseTime,turnaroundTime);
			free(pCurProc);
		}
		pthread_mutex_unlock(&mutex);
		sem_post(&empty);
		if (jobsDone >= MAX_NUMBER_OF_JOBS){
			sem_post(&full);
			break;
		}
	}
	pthread_exit(0);
}

int main()
{   
	pthread_t pro[NUMBER_OF_PRODUCERS],con[NUMBER_OF_CONSUMERS];
	pthread_mutex_init(&mutex, NULL);
	sem_init(&empty,0,MAX_BUFFER_SIZE);
	sem_init(&full,0,0);
	fp = fopen("TASK2A.txt","w");
	head = (PtrtoNode)malloc(sizeof(struct element));
	if (head == NULL){
		printf("unable to allocate memory.\n");
		exit(-1);
	}

	int p[NUMBER_OF_PRODUCERS] = {0,1}; //number producers
	int c[NUMBER_OF_CONSUMERS] = {0,1,2,3}; //number consumers

	for(int i = 0; i < NUMBER_OF_PRODUCERS; i++) {
		pthread_create(&pro[i], NULL, (void *)producer, (void *)&p[i]);
	}
	for(int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
		pthread_create(&con[i], NULL, (void *)consumer, (void *)&c[i]);
	}
	for(int i = 0; i < NUMBER_OF_PRODUCERS; i++) {
		pthread_join(pro[i], NULL);
	}
    
	for(int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
		pthread_join(con[i], NULL);
	}
    
	fprintf(fp,"Average response time %.3f\n",(float)totalResTime/MAX_NUMBER_OF_JOBS);
	fprintf(fp,"Average turnaround time %.3f\n",(float)totalTTime/MAX_NUMBER_OF_JOBS);
	fclose(fp);
	pthread_mutex_destroy(&mutex);
	sem_destroy(&empty);
	sem_destroy(&full);
	FreeMemory(&head);

	return 0;
}

void insert_sort(PtrtoNode *head,PtrtoProc process){
	PtrtoNode pHead,pCurrent,pSecond,pEnd=NULL;

	PtrtoNode pNewElement = (PtrtoNode) malloc (sizeof(struct element));
	pNewElement->pData = process;
	pNewElement->pNext = NULL;
	pHead = *head;
	pCurrent =pHead->pNext;
	if (pCurrent ==NULL){
		pHead->pNext = pNewElement;
		pTail = pNewElement;
		return;
	}
	
	while (pCurrent->pNext != NULL ){
		PtrtoProc nextProc = (PtrtoProc)(pCurrent->pNext->pData);
		int nextProcTime = nextProc->iInitialBurstTime;
		if (nextProcTime > process->iInitialBurstTime)
			break;
		else{
			pCurrent = pCurrent->pNext;
		}
	}
	pNewElement->pNext = pCurrent->pNext;
	pCurrent->pNext = pNewElement;

	if (pNewElement->pNext == NULL){
		pTail = pNewElement;
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
