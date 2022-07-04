#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

/* global parameters */
int RAND_SEED[] = {1, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200};
int MAX_TIME = 15; //max amount of time
int num_of_problems;

/* declare parameters for PSO here*/
int PARTICLE_NUM = 2000; // particle number
int MAX_ITERATIONS = 10000;
int V_MAX = 25;
int W = 1;    // inertia weight
float C1 = 3; //cognitive learning
float C2 = 2; //social learning
int P = 5000; // penalty coefficient

struct solution_struct **pBest_slns; //personal best solution for each particle
struct solution_struct best_sln;     //global best solution

struct solution_struct
{
    struct problem_struct *prob; //maintain a shallow copy of the problem data
    float objective;             //objective of current solution
    int feasibility;             //indicate the feasiblity of the solution
    int *x;                      // solution position vector
    int *v;                      // solution velocity vector
    int *cap_left;               //capacity left in all dimensions
};

struct item_struct
{
    int dim;      //no. of dimensions
    int *size;    //volume of item in all dimensions
    int p;        // profit
    double ratio; //profit/sum of size (for greedy)
    int indx;     // item index
};

struct problem_struct
{
    int n;                     //number of items
    int dim;                   //number of dimensions
    struct item_struct *items; //item array
    int *capacities;           //knapsack capacities
};

//return a random number between 0 and 1
float rand_01()
{
    float number;
    number = (float)rand();
    number = number / RAND_MAX;
    //printf("rand01=%f\n", number);
    return number;
}

//return a random nunber ranging from min to max (inclusive)
int rand_int(int min, int max)
{
    int div = max - min + 1;
    int val = rand() % div + min;
    //printf("rand_range= %d \n", val);
    return val;
}

//compare the ratio of two items,in acsending order
int cmpfunc1(const void *a, const void *b)
{
    const struct item_struct *item1 = a;
    const struct item_struct *item2 = b;
    if (item1->ratio > item2->ratio)
        return -1;
    if (item1->ratio < item2->ratio)
        return 1;
    return 0;
}

//compare the index of two items, in ascending order
int cmpfunc2(const void *a, const void *b)
{
    const struct item_struct *item1 = a;
    const struct item_struct *item2 = b;
    if (item1->indx > item2->indx)
        return 1;
    if (item1->indx < item2->indx)
        return -1;
    return 0;
}

//compare the ratio of two items,in deceasing order
int cmpfunc3(const void *a, const void *b)
{
    const struct item_struct *item1 = a;
    const struct item_struct *item2 = b;
    if (item1->ratio > item2->ratio)
        return 1;
    if (item1->ratio < item2->ratio)
        return -1;
    return 0;
}

//sigmoid transformation
double sigmoidfunc(double v)
{
    return 1 / (1 + exp(-v));
}

//fitness function
double fitness_func(struct solution_struct *sln)
{
    int penalty = 0;
    for (int i = 0; i < sln->prob->dim; i++)
    {
        if (sln->cap_left[i] < 0)
        {
            penalty -= sln->cap_left[i];
        }
    }
    return sln->objective - P * penalty;
}

// free problems
void free_problem(struct problem_struct *prob)
{
    if (prob != NULL)
    {
        if (prob->capacities != NULL)
            free(prob->capacities);
        if (prob->items != NULL)
        {
            for (int j = 0; j < prob->n; j++)
            {
                if (prob->items[j].size != NULL)
                    free(prob->items[j].size);
            }
            free(prob->items);
        }
        free(prob);
    }
}

//initialize problems
void init_problem(int n, int dim, struct problem_struct **my_prob)
{
    struct problem_struct *new_prob = malloc(sizeof(struct problem_struct));
    new_prob->n = n;
    new_prob->dim = dim;
    new_prob->items = malloc(sizeof(struct item_struct) * n);
    for (int j = 0; j < n; j++)
        new_prob->items[j].size = malloc(sizeof(int) * dim);
    new_prob->capacities = malloc(sizeof(int) * dim);
    *my_prob = new_prob;
}

//load problem instances from file
struct problem_struct **load_problems(char *data_file)
{
    int i, j, k;
    //int num_of_probs;
    FILE *pfile = fopen(data_file, "r");
    if (pfile == NULL)
    {
        printf("Data file %s does not exist. Please check!\n", data_file);
        exit(2);
    }
    fscanf(pfile, "%d", &num_of_problems);

    struct problem_struct **my_problems = malloc(sizeof(struct problem_struct *) * num_of_problems);
    for (k = 0; k < num_of_problems; k++)
    {
        int n, dim, obj_opt;
        fscanf(pfile, "%d", &n);
        fscanf(pfile, "%d", &dim);
        fscanf(pfile, "%d", &obj_opt);

        init_problem(n, dim, &my_problems[k]); //allocate data memory
        for (j = 0; j < n; j++)
        {
            my_problems[k]->items[j].dim = dim;
            my_problems[k]->items[j].indx = j;
            fscanf(pfile, "%d", &(my_problems[k]->items[j].p)); //read profit data
            //printf("item[j].p=%d\n",my_problems[k]->items[j].p);
        }
        for (i = 0; i < dim; i++)
        {
            for (j = 0; j < n; j++)
            {
                fscanf(pfile, "%d", &(my_problems[k]->items[j].size[i])); //read size data
                //printf("my_problems[%i]->items[%i].size[%i]=%d\n",k,j,i,my_problems[k]->items[j].size[i]);
            }
        }
        for (i = 0; i < dim; i++)
        {
            fscanf(pfile, "%d", &(my_problems[k]->capacities[i]));
            //printf("capacities[i]=%d\n",my_problems[k]->capacities[i] );
        }
    }
    fclose(pfile); //close file
    return my_problems;
}

//free solution
void free_solution(struct solution_struct *sln)
{
    if (sln != NULL)
    {
        if (sln->x != NULL)
            free(sln->x);
        if (sln->cap_left != NULL)
            free(sln->cap_left);
        free(sln->v);
        sln->objective = 0;
        sln->prob = NULL;
        sln->feasibility = false;
    }
}

//copy a solution from another solution
bool copy_solution(struct solution_struct *dest_sln, struct solution_struct *source_sln)
{
    if (source_sln == NULL)
        return false;
    if (dest_sln == NULL)
    {
        dest_sln = malloc(sizeof(struct solution_struct));
    }
    else
    {
        free(dest_sln->cap_left);
        free(dest_sln->x);
    }
    int n = source_sln->prob->n;
    int m = source_sln->prob->dim;
    dest_sln->x = malloc(sizeof(int) * n);
    dest_sln->cap_left = malloc(sizeof(int) * m);
    for (int i = 0; i < m; i++)
        dest_sln->cap_left[i] = source_sln->cap_left[i];
    for (int j = 0; j < n; j++)
        dest_sln->x[j] = source_sln->x[j];
    dest_sln->prob = source_sln->prob;
    dest_sln->feasibility = source_sln->feasibility;
    dest_sln->objective = source_sln->objective;
    return true;
}

// evaluate the feasiblity of solutions, calcuate objective if feasible
void evaluate_solution(struct solution_struct *sln)
{
    //evaluate the feasibility and objective of the solution
    sln->objective = 0;
    sln->feasibility = 1;
    struct item_struct *items_p = sln->prob->items;

    for (int i = 0; i < items_p->dim; i++)
    {
        sln->cap_left[i] = sln->prob->capacities[i];
        for (int j = 0; j < sln->prob->n; j++)
        {
            sln->cap_left[i] -= items_p[j].size[i] * sln->x[j];
            if (sln->cap_left[i] < 0)
            {
                sln->feasibility = -1 * i; //exceeding capacity
                return;
            }
        }
    }

    //calculate objective
    if (sln->feasibility > 0)
    {
        for (int j = 0; j < sln->prob->n; j++)
        {
            sln->objective += sln->x[j] * items_p[j].p;
        }
    }
}

//output a given solution to a file
void output_solution(struct solution_struct *sln, char *out_file)
{
    if (out_file != NULL)
    {
        FILE *pfile = fopen(out_file, "a"); //append solution data
        fprintf(pfile, "%i\n", (int)sln->objective);
        for (int i = 0; i < sln->prob->n; i++)
        {
            fprintf(pfile, "%i ", sln->x[i]);
        }
        fprintf(pfile, "\n");
        fclose(pfile);
    }
    else
        printf("sln.feas=%d, sln.obj=%f\n", sln->feasibility, sln->objective);
}

//update global best solution from sln
void update_best_solution(struct solution_struct *sln)
{
    if (best_sln.objective < sln->objective)
    {
        copy_solution(&best_sln, sln);
    }
}

//repair the infeasible solutions in a greedy heuristic way
void feasibility_repair(struct solution_struct *curt_sln)
{
    for (int i = 0; i < curt_sln->prob->n; i++)
    {
        double avg_size = 0;
        struct item_struct *item_i = &(curt_sln->prob)->items[i];
        for (int d = 0; d < curt_sln->prob->dim; d++)
        {
            avg_size += (double)item_i->size[d] / curt_sln->prob->capacities[d];
        }
        item_i->ratio = item_i->p / avg_size;
    }
    qsort(curt_sln->prob->items, curt_sln->prob->n, sizeof(struct item_struct), cmpfunc3);
    for (int i = 0; i < curt_sln->prob->n; i++)
    {
        if (curt_sln->x[i] == 0) // if curretn item is not in the bag
        {
            continue;
        }
        curt_sln->x[i] = 0; //remove item in the bag
        evaluate_solution(curt_sln);
        if (curt_sln->feasibility > 0)
        {
            break;
        }
    }

    // sort back by index
    qsort(curt_sln->prob->items, curt_sln->prob->n, sizeof(struct item_struct), cmpfunc2);
    evaluate_solution(curt_sln);
}

//a greedy heuristic solution based on profit/volume ratio
struct solution_struct *greedy_heuristic(struct problem_struct *prob)
{
    for (int i = 0; i < prob->n; i++)
    {
        double avg_size = 0;
        struct item_struct *item_i = &prob->items[i];
        for (int d = 0; d < prob->dim; d++)
        {
            avg_size += (double)item_i->size[d] / prob->capacities[d];
        }
        item_i->ratio = item_i->p / avg_size;
    }
    qsort(prob->items, prob->n, sizeof(struct item_struct), cmpfunc1);

    struct solution_struct *init_sln = malloc(sizeof(struct solution_struct));
    init_sln->prob = prob;
    init_sln->objective = 0;
    init_sln->x = malloc(sizeof(int) * prob->n);
    init_sln->v = malloc(sizeof(int) * prob->n);
    init_sln->cap_left = malloc(sizeof(int) * prob->dim);
    int *cap = malloc(sizeof(int) * prob->dim);
    int i = 0, d = 0;
    for (d = 0; d < prob->dim; d++)
        cap[d] = 0; //aggregated volume
    for (i = 0; i < prob->n; i++)
    {
        struct item_struct *item_i = &prob->items[i];
        init_sln->v[item_i->indx] = rand_int(0, 10); // initialze velocity [0,10]
        for (d = 0; d < prob->dim; d++)
        {
            if (cap[d] + item_i->size[d] > prob->capacities[d])
                break; //infeasible to pack this item, try next
        }
        if (d >= prob->dim) // allowed to put in the bag
        {
            init_sln->x[item_i->indx] = 1;
            init_sln->objective += item_i->p;
            for (d = 0; d < prob->dim; d++)
            {
                cap[d] += item_i->size[d];
            }
        }
        else
            init_sln->x[item_i->indx] = 0;
    }
    for (d = 0; d < prob->dim; d++) //update left capacities in all dimensions
    {
        init_sln->cap_left[d] = prob->capacities[d] - cap[d];
    }
    free(cap);
    //sort item array back according to index
    qsort(prob->items, prob->n, sizeof(struct item_struct), cmpfunc2);

    evaluate_solution(init_sln);
    //output_solution(init_sln, "greedy_sln.txt");
    //printf("Init_sln obj=\t%d\tfeasiblity = %d.\n", init_sln->objective, init_sln->feasibility);
    return init_sln;
}

// return a random item index when the item is not in the bag
int rand_index(int n, struct solution_struct *sln)
{
    while (1)
    {
        int id = rand_int(0, n);
        if (sln->x[id] == 0) // if item is not in the bag
        {
            return id; //return item index
        }
    }
}

//randomly initialize particle population, regarding item positions and velocities
struct solution_struct **init_particles(struct problem_struct *prob)
{
    struct solution_struct **particles = (struct solution_struct **)malloc(sizeof(struct solution_struct *) * PARTICLE_NUM);
    particles[0] = greedy_heuristic(prob); // use greedy heuristic search as a starting point
    copy_solution(pBest_slns[0], particles[0]);
    for (int i = 1; i < PARTICLE_NUM; i++)
    {
        particles[i] = malloc(sizeof(struct solution_struct));
        particles[i]->objective = 0;
        particles[i]->x = malloc(sizeof(int) * prob->n);
        particles[i]->v = malloc(sizeof(int) * prob->n);
        particles[i]->cap_left = malloc(sizeof(int) * prob->dim);
        particles[i]->prob = prob;
        for (int d = 0; d < prob->dim; d++)
        {
            particles[i]->cap_left[d] = prob->capacities[d]; //init capacities
        }
        for (int j = 0; j < prob->n; j++)
        {
            //init positions and randomly init item velocities
            particles[i]->x[j] = 0;
            particles[i]->v[j] = rand_int(0, 10); // velocity between [0,10]
        }

        //randomly init item positions {0,1}
        for (int j = 0; j < prob->n; j++)
        {
            int index = rand_index(prob->n, particles[i]); // find an item that is not in the bag
            particles[i]->x[index] = 1;
            evaluate_solution(particles[i]);
            if (particles[i]->feasibility < 1) // if the item is not allowed to put in the bag
            {
                //remove item
                particles[i]->x[index] = 0;
                evaluate_solution(particles[i]);
            }
        }
        //pBest solution = init solutions
        copy_solution(pBest_slns[i], particles[i]);
    }
    return particles;
}

//initialize pBest solutions for each particle
void init_personal_best_slns(struct problem_struct *prob)
{
    int dim = prob->dim;
    int item_num = prob->n;
    pBest_slns = malloc(sizeof(struct solution_struct *) * PARTICLE_NUM);
    for (int i = 0; i < PARTICLE_NUM; i++)
    {
        pBest_slns[i] = malloc(sizeof(struct solution_struct));
        pBest_slns[i]->objective = 0;
        pBest_slns[i]->x = malloc(sizeof(int) * item_num);
        pBest_slns[i]->v = malloc(sizeof(int) * item_num);
        pBest_slns[i]->cap_left = malloc(sizeof(int) * dim);
    }
}

//main PSO algorithm
int Particle_Swarm_Optimisation(struct problem_struct *prob)
{
    clock_t time_start, time_fin;
    time_start = clock();
    double time_spent = 0;
    best_sln.prob = prob;
    int iter = 0;

    // init pBest solutions for each particle
    init_personal_best_slns(prob);

    // randomly init particle population
    struct solution_struct **particles = init_particles(prob);

    //update gBest after initialization
    int gBest_id = 0;
    float temp_gBest_objective = particles[0]->objective;
    for (int i = 0; i < PARTICLE_NUM; i++)
    {
        if (particles[i]->objective > temp_gBest_objective)
        {
            //update temporary gBest
            temp_gBest_objective = particles[i]->objective;
            gBest_id = i;
        }
    }
    // update gBest
    update_best_solution(particles[gBest_id]);

    //start searching
    while (iter < MAX_ITERATIONS && time_spent < MAX_TIME)
    {
        // update velocity and location
        for (int i = 0; i < PARTICLE_NUM; i++)
        {
            for (int j = 0; j < particles[i]->prob->n; j++)
            {
                // update velocity
                particles[i]->v[j] = W * particles[i]->v[j] + rand_01() * C1 * (pBest_slns[i]->x[j] - particles[i]->x[j]) + rand_01() * C2 * (best_sln.x[j] - particles[i]->x[j]);
                // keep the speed in the range
                if (particles[i]->v[j] > V_MAX)
                    particles[i]->v[j] = V_MAX;
                if (particles[i]->v[j] < -V_MAX)
                    particles[i]->v[j] = -V_MAX;

                // update location
                // if sigmoid funciton > rand number between 0,1, then put item in the bag
                particles[i]->x[j] = rand_01() <= sigmoidfunc(particles[i]->v[j]) ? 1 : 0;
            }

            //evaluate the feasibility and calculate objective
            evaluate_solution(particles[i]);
        }

        // apply fitness function to each particle
        for (int i = 0; i < PARTICLE_NUM; i++)
        {
            double curt_fitness = fitness_func(particles[i]);
            double pBest_fitness = fitness_func(pBest_slns[i]);
            if (curt_fitness > pBest_fitness)
            {
                //If current fitness is better than pBest fitness, update pBest solution
                copy_solution(pBest_slns[i], particles[i]);

                // if current fitness is better than gBest objective, update gBest solution
                update_best_solution(pBest_slns[i]);
            }
        }

        // repair infeasible best solutions
        if (best_sln.feasibility < 0)
        {
            feasibility_repair(&best_sln);
        }

        iter++;
        time_fin = clock();
        time_spent = (double)(time_fin - time_start) / CLOCKS_PER_SEC;
    }

    // free particles and pBest solutions
    for (int i = 0; i < PARTICLE_NUM; i++)
    {
        free_solution(particles[i]);
        free_solution(pBest_slns[i]);
    }

    return 0;
}

int main(int argc, const char *argv[])
{

    printf("Starting the run!\n");
    char data_file[50] = {"somefile"}, out_file[50] = {}, solution_file[50] = {}; //max 50 problem instances per run
    if (argc < 3)
    {
        printf("Insufficient arguments. Please use the following options:\n   -s data_file (compulsory)\n   -o out_file (default my_solutions.txt)\n   -c solution_file_to_check\n   -t max_time (in sec)\n");
        return 1;
    }
    else if (argc > 9)
    {
        printf("Too many arguments.\n");
        return 2;
    }
    else
    {
        for (int i = 1; i < argc; i = i + 2)
        {
            if (strcmp(argv[i], "-s") == 0)
                strcpy(data_file, argv[i + 1]);
            else if (strcmp(argv[i], "-o") == 0)
                strcpy(out_file, argv[i + 1]);
            else if (strcmp(argv[i], "-c") == 0)
                strcpy(solution_file, argv[i + 1]);
            else if (strcmp(argv[i], "-t") == 0)
                MAX_TIME = atoi(argv[i + 1]);
        }
    }
    struct problem_struct **my_problems = load_problems(data_file);
    if (strlen(solution_file) <= 0)
    {
        if (strcmp(out_file, "") == 0) // default output txt
            strcpy(out_file, "my_solutions.txt");
        FILE *pfile = fopen(out_file, "w");
        fprintf(pfile, "%d\n", num_of_problems);
        fclose(pfile);

        for (int k = 0; k < num_of_problems; k++)
        {
            best_sln.objective = 0;
            best_sln.feasibility = 0;
            srand(RAND_SEED[1]);

            printf("Running PSO Problem %d... \n", k + 1);
            Particle_Swarm_Optimisation(my_problems[k]);
            evaluate_solution(&best_sln);
            printf("Final Best Objective: %f\n", best_sln.objective);
            output_solution(&best_sln, out_file);
        }
    }
    for (int k = 0; k < num_of_problems; k++)
    {
        free_problem(my_problems[k]); //free problem data memory
    }
    free(my_problems); //free problems array
    if (best_sln.x != NULL && best_sln.cap_left != NULL)
    {
        free(best_sln.cap_left);
        free(best_sln.x);
    } //free global
    return 0;
}