package check;


import java.util.Random;

public class checkcrossover {
	
	
  public static void main(String[] args) {
    System.out.println("Hello world!");

    int p1[] = {0, 2, 1, 4, 3, 5, 0};
    int p2[] = {0, 2, 5, 3, 4, 1, 0};

    int child[] = crossover(p1,p2);
    
    for (int i=0; i<30; i++) {
    	System.out.println("\nRun: " + (i+1));
    	child = crossover(p1,p2);
    }

    
    System.out.print("\n\nParent 1: ");
    printArray(p1);
    System.out.print("\n\nParent 2: ");
    printArray(p2);
    System.out.print("\n\nChild: ");
    printArray(child);
    

  }

 
  
  public static int[] crossover(int[] p1, int[] p2){
      
      int len = p1.length;
      int new_len = len-2;
      int temp_child[] = new int[new_len];

      for(int i=0; i<new_len; i++){
        temp_child[i] = -1;
      }
      
      int temp_p1[] = new int[new_len];
      int temp_p2[] = new int[new_len];
      
      int x=1;
      for(int i=0; i<new_len; i++) {
    	  temp_p1[i] = p1[x];
    	  temp_p2[i] = p2[x];
    	  x++;
      }
      
      printArray(temp_p1);
      printArray(temp_p2);
      
      //System.exit(0);
      int r1 = randomNumber(0, new_len);
      int r2 = randomNumber(0, new_len);
      
      System.out.println();
      System.out.print("Two random numbers before: ");
      System.out.print(r1 + " " + r2);
      
      while(r1 >= r2){
    	  if(r1>=new_len-2) {
    		  r1 = randomNumber(0, new_len);
    	  }
        r2 = randomNumber(0, new_len);
      }

      System.out.println();
      System.out.print("Two random numbers: ");
      System.out.print(r1 + " " + r2);

      for(int i=r1; i<=r2; i++){
        temp_child[i] = temp_p1[i];
      }

      System.out.println();
      System.out.print("Child at initial stage: ");
      printArray(temp_child);

      int temp1[] = temp_p2.clone();
      temp1 = rotateArray(temp1, r2);

      System.out.println();
      System.out.print("temp1 array: ");
      printArray(temp1);

      int temp2[] = new int[new_len-(r2-r1)-1];
      int j = 0;
      for(int i=0; i<new_len; i++){
        if(!arrayContains(temp_child, temp1[i])){
            temp2[j] = temp1[i];
            j++;
        }
      }

      System.out.println();
      System.out.print("temp2 array: ");
      printArray(temp2);

      for(int i=0; i<temp2.length; i++){
        temp_child[(i+r2+1)%new_len] = temp2[i];
      }

      System.out.println();
      System.out.print("Final child array: ");
      printArray(temp_child);
      
      int child[] = new int[len];
      int k=0;
      for(int i=0; i<len; i++) {
    	  if(i==0 || i==len-1) {
    		  child[i] = p2[i];
    	  }else {
    		  child[i] = temp_child[k++];
    	  }
    	  
      }

      return child;
  }

  public static boolean arrayContains(int[] arr, int x){
      for(int i=0; i<arr.length; i++){
        if(arr[i] == x){
          return true;
        }
      }

      return false;
  }

  public static int[] rotateArray(int[] arr, int p){
    int a[] = arr.clone();
    for(int i=0; i<arr.length; i++){
        a[i] = arr[(i+p+1) % arr.length];
    }

    return a;
  }

  public static void printArray(int[] arr){
      for(int i=0; i<arr.length; i++){
        System.out.print(arr[i] + " ");
      }
  }

  public static int randomNumber(int min, int max){
    Random rand = new Random();
    return rand.nextInt(max-min) + min;
  }

}
