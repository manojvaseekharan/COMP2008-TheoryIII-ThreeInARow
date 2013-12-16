import java.util.ArrayList;


public class Solver {
	
	private ArrayList<Integer> blackPebblesArrayList = new ArrayList<Integer>();
	private ArrayList<Integer> whitePebblesArrayList = new ArrayList<Integer>();
	private static int boardSize = 0;
	
	public static void main(String[] args) throws InterruptedException {
		Solver main = new Solver();
		InputReader.readFileInput(args); //reads file
		main.assignParameters();
		
		Integer[] blackPebblesArray = new Integer[main.blackPebblesArrayList.size()];
		blackPebblesArray = main.blackPebblesArrayList.toArray(blackPebblesArray);
		Integer[] whitePebblesArray = new Integer[main.whitePebblesArrayList.size()];
		whitePebblesArray = main.whitePebblesArrayList.toArray(whitePebblesArray);
		
		Board board = new Board(boardSize);
		main.populateBoard(board, blackPebblesArray, whitePebblesArray);
		Logic solver = new Logic(board);
		solver.startSolving();
		if (solver.solvable == true)
		{
			PrintResults pr = new PrintResults(CopyArray.copyArray(board.getArray()));
			pr.printResults();
		}
	}
	
	private void assignParameters()
	{
		boardSize = Integer.parseInt(InputReader.contents.get(0));
		String whitePebbles = InputReader.contents.get(1);
		addWhitePebbles(whitePebbles);
		String blackPebbles = InputReader.contents.get(2);
		addBlackPebbles(blackPebbles);
	}
	
	private void addWhitePebbles(String input)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < input.length(); i ++)
		{
			if (i == input.length()-1)
			{
				return;
			}
			if (input.charAt(i) != ' ')
			{
				sb.append(input.charAt(i));
			}
			else
			{
				whitePebblesArrayList.add(Integer.parseInt(sb.toString()));
				sb = sb.delete(0,sb.toString().length());
			}
		}
	}
	
	private void addBlackPebbles(String input)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < input.length(); i ++)
		{
			if (i == input.length()-1)
			{
				return;
			}
			if (input.charAt(i) != ' ')
			{
				sb.append(input.charAt(i));
			}
			else
			{
				blackPebblesArrayList.add(Integer.parseInt(sb.toString()));
				sb = sb.delete(0,sb.toString().length());
			}
		}
	}
	
	private void populateBoard(Board board, Integer[] blackPebbles, Integer[] whitePebbles)
	{
		for(int i = 0; i < blackPebbles.length; i ++)
		{
			board.setEntry('B', (blackPebbles[i]-1)/board.getSize(),(blackPebbles[i]-1)%board.getSize());
		}
		for(int i = 0; i < whitePebbles.length; i ++)
		{
			board.setEntry('W', (whitePebbles[i]-1)/board.getSize(),(whitePebbles[i]-1)%board.getSize());
		}
		for(int i = 0; i < board.getSize(); i ++)
		{
			for(int j = 0; j < board.getSize(); j ++)
			{
				if (board.getEntry(i,j) != Character.valueOf('B') && board.getEntry(i,j) != Character.valueOf('W'))
				{
					board.setEntry('o', i, j);
				}
			}
		}
	}

}
