
public class Board {
	
	private Character[][] array;
	private int size;
	private int blackPebbles[];
	private int whitePebbles[];
	
	public Board(int size, int[] blackPebbles, int[] whitePebbles)
	{
		this.array = new Character[size][size];
		this.size = size;
		this.blackPebbles = blackPebbles;
		this.whitePebbles = whitePebbles;
		CopyArray.size = size;
	}
	
	public Character getEntry (int row, int column)
	{
		return array[row][column];
	}
	
	public Character[][] getArray()
	{
		return array;
	}
	
	public void setArray(Character[][] newArray)
	{
		array = newArray;
	}
	
	public void setEntry (char pebble, int row, int column)
	{
		array[row][column] = pebble;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void print()
	{
		for(int i = 0; i < getSize(); i ++)
		{
			for (int j = 0; j < getSize(); j ++)
			{
				System.out.print(getEntry(i,j));
			}
			System.out.println();
		}
		System.out.println("---------");
	}

}
