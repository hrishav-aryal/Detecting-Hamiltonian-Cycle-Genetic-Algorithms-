package HamiltonianCycle;

public class BruteForce {
	
	private static int solution_size = 10;
	private static boolean solution_found = false;
	private static int temp_solution[] = new int[solution_size];
	
	private static long start_time;
	private static long end_time;
	
	private static int graph[][] = {	{0,1,0,0,1,0,0,1,0,0}, 
			{1,0,0,1,0,0,0,0,1,1},
			{0,0,0,0,0,0,1,1,0,0},
			{0,1,0,0,0,1,1,0,0,0},
			{1,0,0,0,0,0,1,1,0,1},
			{0,0,1,1,0,0,0,0,0,0},
			{0,0,1,1,1,0,0,0,1,0},
			{1,0,1,0,1,0,0,0,0,1},
			{0,1,0,0,1,0,1,1,0,0},
			{1,1,0,0,1,0,0,1,0,0}	};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		start_time = System.nanoTime();
		
		int solution[] = new int[solution_size];
		for(int i=0; i<solution_size; i++) {
			solution[i] = i;
		}
		
		bruteForceHamiltonCycle(solution, 1);
		
		
		
		System.out.println("Solution does not exist.");
		
		end_time = System.nanoTime();
		System.out.println("\n\nTime take: " + ((end_time - start_time) / 1000000) + " milisecs");

	}
	
	 public static void bruteForceHamiltonCycle(int[] sol, int startindex) {
	        int size = sol.length;

	        if (size == startindex + 1) {
	            
	            for (int i = 0; i < size; i++) {
	                temp_solution[i] = sol[i];
	            }
	            System.out.print("temp_solution: ");
	            printArray(temp_solution);
	            System.out.println();
	            
	            if(isSolution(temp_solution)) {
	            	displaySolution(temp_solution);
	            	
	            	end_time = System.nanoTime();
	        		System.out.println("\n\nTime take: " + ((end_time - start_time) / 1000000) + " milisecs");
	            	System.exit(0);
	            }
	            
	        } else {
	            for (int i = startindex; i < size; i++) {

	                int temp = sol[i];
	                sol[i] = sol[startindex];
	                sol[startindex] = temp;
	                
	                bruteForceHamiltonCycle(sol, startindex + 1);
	                
	                temp = sol[i];
	                sol[i] = sol[startindex];
	                sol[startindex] = temp;
	            }
	        }
	    }

	private static void displaySolution(int[] sol) {
		
		int final_solution[] = new int[sol.length+1];
		for(int i=0; i<=sol.length; i++) {
			if(i!=sol.length) {
				final_solution[i] = sol[i];
			}else {
				final_solution[i] = final_solution[0];
			}
			
		}
		
		System.out.print("Hamiltonian Cycle Exists! The path is: \n\n");
		
		for(int i=0; i<final_solution.length; i++) {
			System.out.print(final_solution[i] + " ");
		}

	}

	private static boolean isSolution(int[] sol) {
		
		int num_of_edges = 0;
		
		int final_solution[] = new int[sol.length+1];
		for(int i=0; i<=sol.length; i++) {
			if(i!=sol.length) {
				final_solution[i] = sol[i];
			}else {
				final_solution[i] = final_solution[0];
			}
			
		}
		
		System.out.print("final_solution: ");
		printArray(final_solution);
		System.out.println("\n");
		
		for(int i=1; i<final_solution.length; i++) {
			
			//check if edge between two adjacent vertex exists or not
			//if exist increase the fitness value
			if(graph[final_solution[i]][final_solution[i-1]] == 1) {
				num_of_edges++;
			}
		}
		
		if(num_of_edges == final_solution.length-1) {
			return true;
		}
		
		
		return false;
	}
	
	public static void printArray(int[] arr){
	      for(int i=0; i<arr.length; i++){
	        System.out.print(arr[i] + " ");
	      }
	  }


}
