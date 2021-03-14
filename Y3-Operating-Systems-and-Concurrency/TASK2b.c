#define _BSD_SOURCE
#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <unistd.h>
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

int jobsGenerate =0,jobsDone=0;
pthread_mutex_t mutex;
sem_t empty, full;
int totalResponseTime=0,totalTurnaroundTime=0;
ProcessTable p;
FILE *fp;

void InitProcessTable(ProcessTable *p);
bool InsertProcessTable(ProcessTable *p,PtrtoProc proc,int index);
bool printProcessInfo(PtrtoProc pCurProc,int cNo,struct timeval startTime);
void freeMemory();

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
		jobsGenerate++;
		fprintf(fp,"Producer %d: Items Produced %d, New Process ID = %d, Burst Time = %d, Priority %d\n", *((int *)pno),jobsGenerate,pData->iProcessId,pData->iInitialBurstTime,pData->iPriority);
		//insert to process table
		InsertProcessTable(&p,pData,pData->iPriority);
        
		pthread_mutex_unlock(&mutex);
		sem_post(&full);
		if (jobsGenerate > 5)
			usleep(10);
	}
	pthread_exit(0);
}
void *consumer(void *cno)
{   
	bool finishJob;
	int i;
	while(1){
		sem_wait(&full);
		usleep(10);
		pthread_mutex_lock(&mutex);
		if (jobsDone < MAX_NUMBER_OF_JOBS){
			// runjob
			finishJob = false;
			for (i=0;i<(p.tableSize);i++){
				if (p.que[i].elem != NULL && p.que[i].elem->pNext !=NULL){
					break;
				}
			}
			if (i == p.tableSize)
				continue;
			
			PtrtoNode head = p.que[i].elem;
			PtrtoNode pTail = p.que[i].pTail;
			struct timeval startTime,endTime;
			startTime.tv_sec = 0; endTime.tv_sec = 0;
		
			PtrtoProc pCurProc = removeFirst(&(head->pNext),&pTail);
			pthread_mutex_unlock(&mutex);

			runPreemptiveJob(pCurProc,&startTime,&endTime);
					
			pthread_mutex_lock(&mutex);	
			finishJob = printProcessInfo(pCurProc,*((int *)cno),startTime);
		}

		pthread_mutex_unlock(&mutex);
		if (finishJob){
			sem_post(&empty);
		}
		if (jobsDone >= MAX_NUMBER_OF_JOBS){
			sem_post(&full);
			break;
		}
		
	}
	pthread_exit(0);
        
}

int main(){
 	pthread_t pro[NUMBER_OF_PRODUCERS],con[NUMBER_OF_CONSUMERS];
	pthread_mutex_init(&mutex, NULL);
   	sem_init(&empty,0,MAX_BUFFER_SIZE);
   	sem_init(&full,0,0);
   	fp = fopen("TASK2B.txt","w");
	
	InitProcessTable(&p);
	
	int p[NUMBER_OF_PRODUCERS] = {0,1}; //number of  producers
  	int c[NUMBER_OF_CONSUMERS] = {0,1,2,3}; //number of consumers

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
    
	fprintf(fp,"Average response time %.3f\n",(float)totalResponseTime/MAX_NUMBER_OF_JOBS);
	fprintf(fp,"Average turnaround time %.3f\n",(float)totalTurnaroundTime/MAX_NUMBER_OF_JOBS);
	fclose(fp);
	pthread_mutex_destroy(&mutex);
	sem_destroy(&empty);
	sem_destroy(&full);
	freeMemory();
	return 0;
}

void freeMemory(){
	for (int i=0;i<(p.tableSize);i++){
		if (p.que[i].elem != NULL)
			free(p.que[i].elem);
	}
	free(p.que);
}

bool printProcessInfo(PtrtoProc pCurProc,int cNo, struct timeval startTime){
	int responseTime=0,turnaroundTime=0;	
	if (pCurProc->iRemainingBurstTime == 0){	
		//printf process info + tt time	
			
		turnaroundTime = getDifferenceInMilliSeconds(pCurProc->oTimeCreated,pCurProc->oMostRecentTime);
		totalTurnaroundTime += turnaroundTime;
		jobsDone++;
		fprintf(fp,"Consumer = %d,Process id = %d, Priority = %d,Previous Burst Time = %d, New Burst Time = %d, Turnaround Time = %d\n", cNo,pCurProc->iProcessId,pCurProc->iPriority, pCurProc->iPreviousBurstTime,pCurProc->iRemainingBurstTime,turnaroundTime);
		free(pCurProc);
		return true;
	}else{
		//printf info( plus response time if it is first executed) 
		if ((pCurProc->iInitialBurstTime - pCurProc->iRemainingBurstTime) == TIME_SLICE){
			responseTime = getDifferenceInMilliSeconds(pCurProc->oTimeCreated,startTime);
			fprintf(fp,"Consumer = %d, Process id = %d, Priority = %d,Previous Burst Time = %d, New Burst Time = %d, Response Time = %d\n", cNo,pCurProc->iProcessId,pCurProc->iPriority, pCurProc->iPreviousBurstTime,pCurProc->iRemainingBurstTime, responseTime);
			totalResponseTime +=responseTime;
		}
		else{
			//print info without response time
			fprintf(fp,"Consumer = %d, Process id = %d, Priority = %d,Previous Burst Time = %d, New Burst Time = %d\n", cNo,pCurProc->iProcessId,pCurProc->iPriority, pCurProc->iPreviousBurstTime,pCurProc->iRemainingBurstTime);
		}
				
		//insert back to process table if not finished
		InsertProcessTable(&p,pCurProc,pCurProc->iPriority);
		sem_post(&full);
		return false;
	}

}

void InitProcessTable(ProcessTable *p)  {  
	int i;           
	p->tableSize=MAX_PRIORITY;  
	p->que=malloc(MAX_PRIORITY*sizeof(PriorityQueue));  
	if (p->que == NULL){
		printf("unable to allocate memory.\n");
		exit(-1);
	}
	for(i=0;i<(p->tableSize);i++){
		p->que[i].elem = NULL;  
		p->que[i].pTail = NULL;
	}             
}  
  
bool InsertProcessTable(ProcessTable *p,PtrtoProc proc,int index){  
	if (p->que[index].elem == NULL){
		PtrtoNode head = (PtrtoNode)malloc(sizeof(struct element));
		PtrtoNode next = (PtrtoNode)malloc(sizeof(struct element));
		next->pData = proc;
		next->pNext = NULL;
		head->pNext = next;		
		p->que[index].elem = head;		
		p->que[index].pTail = next;
		return true;
	}else {
		addLast(proc,&(p->que[index].elem->pNext),&(p->que[index].pTail));
		return true;
	}
	
	return false;

}   
 
