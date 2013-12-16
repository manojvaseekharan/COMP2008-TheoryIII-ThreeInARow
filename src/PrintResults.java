import java.util.ArrayList;
import java.util.List;


public class PrintResults {

	Character[][] array;
	List<Character> list = new ArrayList<Character>();
	public PrintResults(Character[][] array)
	{
		this.array = array;
	}
	
	public void printResults()
	{
		convertToSingleDimensionalArray();
		Character[] results = new Character[list.size()];
		results = list.toArray(results);
		for (int i = 0; i < results.length; i ++)
		{
			if (results[i] == 'W')
			{
				System.out.print(i+1);
				System.out.print(" ");
			}
		}
	}
	
	private void convertToSingleDimensionalArray()
	{
		for (int i = 0; i < array.length; i++) {
	        for (int j = 0; j < array[i].length; j++) { 
	            list.add(array[i][j]); 
	        }
	    }
	}
	
	
	
	

}
