package HamiltonianCycle;

import java.util.Random;

public class UseMe {
	
	private static int randomNumber() {
		Random rand = new Random();
		
		return rand.nextInt(1000)%5;
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
