package check;

public class brute {

    public static int counter = 0;

    public static void Permute(int[] input, int startindex) {
        int size = input.length;

        if (size == startindex + 1) {
            System.out.println(counter + "Permutation is");
            for (int i = 0; i < size; i++) {
                System.out.print(input[i] + ",  ");
            }
            System.out.println();
            System.out.println("##########################");
            
            
            
            
            
            
            counter++;
        } else {
            for (int i = startindex; i < size; i++) {

                int temp = input[i];
                input[i] = input[startindex];
                input[startindex] = temp;
                Permute(input, startindex + 1);
                temp = input[i];
                input[i] = input[startindex];
                input[startindex] = temp;
            }
        }
    }

    public static void main(String[] args) {
       
        int[] input = {1,2,3};
        counter = 0;
        Permute(input, 0);
        System.out.println(counter + "  number of permutations obtained");
    }

}