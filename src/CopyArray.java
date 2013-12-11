
public class CopyArray {
	
	public static int size;
	
	public static Character[][] copyArray(Character[][] inputArray)
	{
		Character[][] outputArray = new Character[size][size];
		for(int i = 0; i < size; i ++)
		{
			for (int j = 0; j < size; j ++)
			{
				outputArray[i][j] = inputArray[i][j];
			}
		}
		return outputArray;
	}

}
