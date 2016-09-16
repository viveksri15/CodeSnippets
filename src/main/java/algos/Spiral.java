package algos;

import java.util.Scanner;

public class Spiral{

	static int[][] sprial;


	public static void main(String []args){



		int metrixSize = 10;

		sprial = new int[metrixSize+1][metrixSize+1];
		sprial(metrixSize,0,1,1);

		for(int i = 1; i <= metrixSize ; i++){
			for(int j = 1; j <= metrixSize ; j++){
				System.out.print("\t"+ sprial[i][j]);
			}
			System.out.println();
		}

	}


	public static void  sprial(int size, int initialValue, int startI, int startJ)
	{
		if (size == 0)
			return;
		if (size == 1)
		{
			sprial[startI][startJ] = initialValue +1;
			return;

		}
		int value = 1;
		int i = startI;
		int j = startJ;

		int counter = 0;
		while (value <= 4 * (size -1))
		{
			sprial[i][j] = initialValue + value;

			//	System.out.println("i,j"+ i + ","+ j);

			if ((value-1) % (size-1) == 0)
				counter++;

			//	System.out.println("value "+ value);
			//	System.out.println("counter "+ counter);
			//    System.out.println("size "+ size);

			switch(counter){
				case 1: j++; break;
				case 2: i++; break;
				case 3: j--; break;
				case 4: i--; break;

			}

			value++;
		}

		sprial(size -2 ,sprial[startI+1][startJ],startI+1, startJ+1 );
	}

}