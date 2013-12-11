
public class Main {

	/**
	 * @param args
	 */
	
	private static int[] blackPebbles = {};
	private static int[] whitePebbles = {1};
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Board board = new Board(14, blackPebbles, whitePebbles); 
		
		populateBoard(board);
		board.print();
		Solver solver = new Solver(board);
		solver.startSolving();
		board.print();
	}
	
	public static void populateBoard(Board board)
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
