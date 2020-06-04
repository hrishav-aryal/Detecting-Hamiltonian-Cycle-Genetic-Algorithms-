package HamiltonianCycle;

import java.util.Random;
import java.util.Scanner;


public class main {
	
	private static int pop_size = 30;
	private static int chrom_size = 11;
	private static double mutation_rate = 0.3;
	private static double crossover_rate = 0.4;
	private static int max_gen = 100;
	
	
	private static int[][] population = new int[pop_size][chrom_size];
	private static int[] each_chrom_fitness = new int[pop_size];
	private static boolean solution_found = false;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		long start_time = System.nanoTime();
		
		
		int graph[][] = {	{0,1,0,0,1,0,0,1,0,0}, 
				{1,0,0,1,0,0,0,0,1,1},
				{0,0,0,0,0,0,1,1,0,0},
				{0,1,0,0,0,1,1,0,0,0},
				{1,0,0,0,0,0,1,1,0,1},
				{0,0,1,1,0,0,0,0,0,0},
				{0,0,1,1,1,0,0,0,1,0},
				{1,0,1,0,1,0,0,0,0,1},
				{0,1,0,0,1,0,1,1,0,0},
				{1,1,0,0,1,0,0,1,0,0}	};
		
		
		int solution = findHamiltonianCycle(graph);
		
		if(solution_found) {
			displaySolution(solution);
		} else {
			System.out.println("It is highly probably that the solution does not exists.");
		}
		
		long end_time = System.nanoTime();
		
		System.out.println("\n\nThe program took: " + ((end_time-start_time)/1000000)+ "miliseconds.");
		
	}
	
	public static int findHamiltonianCycle(int graph[][]) {
		
		//generating the random initial population where each chromosome represents a solution array
		initialPopulation();
		
		int fittest_chrom = 0;
		int besth_chrom_so_far = 0;
		
		for(int generation = 0; generation<=max_gen; generation++) {
			
			System.out.println("\nThis is generation: " + generation +"\n\n");
			
			//assign fitness to each chromosome
			each_chrom_fitness = assignFitness(graph);
			
			//get fittest member
			fittest_chrom = getFittestChrom();
			System.out.println("\n\nThe fittest chromosome is chromosome number: "+fittest_chrom +
					", with fitness value " + each_chrom_fitness[fittest_chrom] + "\n");
			
			if(each_chrom_fitness[fittest_chrom] == chrom_size - 1) {
				//solution found
				solution_found = true;
				return fittest_chrom;
			}else {
				if(each_chrom_fitness[fittest_chrom] > each_chrom_fitness[besth_chrom_so_far]) {
					besth_chrom_so_far = fittest_chrom;
				}
				
				System.out.println("\n\nBest chromosome so far: " + besth_chrom_so_far + 
						", with fitness value " + each_chrom_fitness[besth_chrom_so_far] + "\n");
				
				evolve_population();
			}
			
		}
		
		return -1;
				
	}
	
	
	public static void evolve_population() {
		int new_population[][] = new int[pop_size][chrom_size];
		
		System.out.println("\n\nI am inside evolve population.\n\n");
		
		int a = 0, b = 0;
		int winner, loser;
		
		for(int i=0; i<pop_size; i++) {
			
			do {
				a = select_chrom_using_roulette_wheel();
				b = select_chrom_using_roulette_wheel();
			}while(a==b);
			
			System.out.print("a - " + a +" | b -" + b +"\n");
			
			if(each_chrom_fitness[a] > each_chrom_fitness[b]) {
				winner = a;
				loser = b;
			} else {
				loser = a;
				winner = b;
			}
			System.out.println("Winner is " + winner);
			
			int[] temp_chrom = population[winner];
			
			System.out.println("\ntemp chrom before crossover: ");
			OrderCrossover.printArray(temp_chrom);
			System.out.println();
			OrderCrossover.printArray(population[loser]);
			System.out.println();
			
			if(randomDoubleZeroToOne() < crossover_rate) {
				System.out.println("Crossover done: ");
				temp_chrom = OrderCrossover.crossover(population[winner], population[loser]);
			}
			
			new_population[i] = temp_chrom;
			
			System.out.println("\n\ntemp chrom after crossover:\nPopulation " + i + ": ") ;
			OrderCrossover.printArray(temp_chrom);
			System.out.println();
		}
		
		for(int i=0; i<pop_size; i++) {
			if(randomDoubleZeroToOne() < mutation_rate) {
				new_population[i] = performSwapMutation(new_population[i]);
			}
		}
		
		
		population = new_population;
		
	}
	
	private static int[] performSwapMutation(int[] parent) {
		
		int[] clone_parent = parent.clone();
		int len = clone_parent.length;
		
		int r1 = randomNumberBetween(1,len-1);
		int r2 = randomNumberBetween(1,len-1);
		
		while(r1==r2) {
			r2 = randomNumberBetween(1,len-1);
		}
		
		int temp = clone_parent[r2];
		clone_parent[r2] = clone_parent[r1];
		clone_parent[r1] = temp;
			
		
		return clone_parent;
	}


	private static int select_chrom_using_roulette_wheel() {
		
		System.out.print("\nI am inside roulette wheel.\n");
		
		int fitness_sum = 0;
		for(int i=0; i<pop_size; i++) {
			//System.out.print(each_chrom_fitness[i] + " ");
			fitness_sum = fitness_sum + each_chrom_fitness[i];
			System.out.print(fitness_sum + " ");
		}
		
		System.out.print("\nfitness sum: " + fitness_sum);
		
		Random rand = new Random();
		int pick_number = rand.nextInt(fitness_sum);
		
		System.out.println("\n" + pick_number);
		
		int partial_fitness_sum = 0;
		
		for(int i=pop_size-1; i>=0; i--) {
			partial_fitness_sum += each_chrom_fitness[i];
			if(partial_fitness_sum > pick_number) {
				System.out.println("this item is picked by roulette wheel: "+i);
				return i;
			}
		}
		
		return -1;
	}
	

	public static int getFittestChrom() {
		int fittest = 0;
		System.out.println();
		for (int i=0; i<pop_size; i++) {
			
			if(each_chrom_fitness[i] > each_chrom_fitness[fittest]) {
				fittest = i;
			}
		}
		
		return fittest;
	}
	
	

	
	private static int[] assignFitness(int graph[][]) {
		int[] chrom_fitness = new int [pop_size];
		
		for(int i=0; i<pop_size; i++) {
			
			chrom_fitness[i] = 0;
			
			for(int j=1; j<chrom_size; j++) {
				
				//check if edge between two adjacent vertex exists or not
				//if exist increase the fitness value
				if(graph[population[i][j]][population[i][j-1]] == 1) {
					chrom_fitness[i]++;
				}
			}
			
			OrderCrossover.printArray(population[i]);
			System.out.print(" - " + chrom_fitness[i] + "\n");
		}
		
		
		return chrom_fitness;
	}
	
	private static void initialPopulation() {
		
		for(int i=0; i<pop_size; i++) {
			
			for(int j=0; j<chrom_size; j++) {
				
				if(j==0 || j==chrom_size-1) {
					population[i][j] = 0;
				}else {
					
					int chrom_val = randomNumber();
					int check = 0;
					
					while(true) {
						for(int a=0; a<j; a++) {
							if(population[i][a] == chrom_val) {
								check = 1;
							}
						}
						
						if (check == 1) {
							chrom_val =  randomNumber();
							check = 0;
						}else {
							population[i][j] = chrom_val;
							break;
						}
					}
					
				}
	
			}
		}
		
	}
	
	public static void displaySolution(int sol) {
		System.out.print("Hamiltonian Cycle Exists! The path is: \n\n");
		for(int i=0; i<chrom_size; i++) {
			System.out.print(population[sol][i] + " ");
		}

	}

	private static int randomNumber() {
		Random rand = new Random();
		
		return rand.nextInt(1000)% (chrom_size-1);
	}

	private static double randomDoubleZeroToOne() {
		Random rand = new Random();
		
		return rand.nextInt(1000) / 1000.0;
	}
	
	private static int randomNumberBetween(int min, int max) {
		Random rand = new Random();
		return min + rand.nextInt(max-min);
	}
	
	public static boolean arrayContains(int[] arr, int e){
		for(int i = 0; i < arr.length; i++){
			if(arr[i] == e)
				return true;
		}
		return false;
	}
}
