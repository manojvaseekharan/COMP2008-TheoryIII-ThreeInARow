import java.util.ArrayList;


public class Solver {
	
	private Board board;
	public Solver(Board board)
	{
		this.board = board;
	}
	
	ArrayList<Character[][]>boardState = new ArrayList<Character[][]>(); //stores array of pebbles
	ArrayList<String>locationOfGuessing = new ArrayList<String>(); //stores location where guess started
	ArrayList<Integer>typeOfGuess = new ArrayList<Integer>(); //stores type of guess
	ArrayList<Integer>counterValue = new ArrayList<Integer>();//stores counter
	int indexOfSafeStates = 0;
	
	public void startSolving()
	{
		mainAlgorithm();
		//initial state before commencing!
		startGuessing();
		checkHorizontalConsecutiveViolation();
		checkVerticalConsecutiveViolation();
		if(checkNumberOfPebblesHorizontalViolation() == true)
		{
			System.out.println("FUCK");
		}
		if(checkNumberOfPebblesVerticalViolation() == true)
		{
			System.out.println("YOU");
		}
	}

	private void mainAlgorithm() {
		for (int i = 0; i < 100; i++) {
			//look for two consecutive pebbles
			twoConsecutiveHorizontalBlackPebbles();
			twoConsecutiveHorizontalWhitePebbles();
			twoConsecutiveVerticalBlackPebbles();
			twoConsecutiveVerticalWhitePebbles();
			checkForGapHorizontalBlackPebbles();
			checkForGapHorizontalWhitePebbles();
			checkForGapVerticalBlackPebbles();
			checkForGapVerticalWhitePebbles();
			countBlacksHorizontal();
			countWhitesHorizontal();
			countBlacksVertical();
			countWhitesVertical();
		}
	}
	
	boolean test = false;
	
	public void startGuessing()
	{
		boolean marker = false;
		for (int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize(); j ++)
			{
				if (board.getEntry(i, j) == 'o')
				{
					boardState.add(CopyArray.copyArray(board.getArray()));
					locationOfGuessing.add(Integer.toString(i)+" "+Integer.toString(j));
					typeOfGuess.add(1);
					counterValue.add(0);
					marker = true;
					guessOneInARow(i,j);
				}
			}
			if(marker == true)
			{
				marker = false;
				break;
			}
		}
	}
	
	private void twoConsecutiveHorizontalBlackPebbles()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for(int j = 0; j < board.getSize()-2; j ++)
			{
				if(board.getEntry(i,j) == 'B' && board.getEntry(i,j+1) == 'B' && board.getEntry(i,j+2) == 'o')
				{
					board.setEntry('W',i,j+2);
		
				}
			}
		}
		for(int i = 0; i < board.getSize(); i ++)
		{
			for(int j = 1; j < board.getSize()-1; j ++)
			{
				if(board.getEntry(i,j) == 'B' && board.getEntry(i,j+1) == 'B' && board.getEntry(i,j-1) == 'o')
				{
					board.setEntry('W',i,j-1);
				}
			}
		}
	}

	private void twoConsecutiveHorizontalWhitePebbles()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for(int j = 0; j < board.getSize()-2; j ++)
			{
				if(board.getEntry(i,j) == 'W' && board.getEntry(i,j+1) == 'W' && board.getEntry(i,j+2) == 'o')
				{
					board.setEntry('B',i,j+2);
				}
			}
		}
		for(int i = 0; i < board.getSize(); i ++)
		{
			for(int j = 1; j < board.getSize()-1; j ++)
			{
				if(board.getEntry(i,j) == 'W' && board.getEntry(i,j+1) == 'W' && board.getEntry(i,j-1) == 'o')
				{
					board.setEntry('B',i,j-1);
				}
			}
		}
	}

	private void twoConsecutiveVerticalBlackPebbles()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize()-2; j ++)
			{
				if (board.getEntry(j, i) == 'B' && board.getEntry(j+1, i) == 'B' && board.getEntry(j+2, i) == 'o')
				{
					board.setEntry('W',j+2,i);
				}
			}
			for (int j = 1; j < board.getSize()-1; j ++)
			{
				if (board.getEntry(j, i) == 'B' && board.getEntry(j+1, i) == 'B' && board.getEntry(j-1, i) == 'o')
				{
					board.setEntry('W',j-1,i);
				}
			}
		}
	}
	
	private void twoConsecutiveVerticalWhitePebbles()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize()-2; j ++)
			{
				if (board.getEntry(j, i) == 'W' && board.getEntry(j+1, i) == 'W' && board.getEntry(j+2,i) == 'o')
				{
					board.setEntry('B',j+2,i);
				}
			}
			for (int j = 1; j < board.getSize()-1; j ++)
			{
				if (board.getEntry(j, i) == 'W' && board.getEntry(j+1, i) == 'W' && board.getEntry(j-1,i) =='o')
				{
					board.setEntry('B',j-1,i);
				}
			}
		}
	}
	
	private void checkForGapHorizontalBlackPebbles()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize()-2; j++)
			{
				if(board.getEntry(i, j) == 'B' && board.getEntry(i, j+1) == 'o' && board.getEntry(i, j+2) == 'B')
				{
					board.setEntry('W',i,j+1);
				}
			}
		}
	}
	
	private void checkForGapHorizontalWhitePebbles()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize()-2; j++)
			{
				if(board.getEntry(i, j) == 'W' && board.getEntry(i, j+1) == 'o' && board.getEntry(i, j+2) == 'W')
				{
					board.setEntry('B',i,j+1);
				}
			}
		}
	}

	private void checkForGapVerticalBlackPebbles()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize()-2; j ++)
			{
				if (board.getEntry(j,i) == 'B' && board.getEntry(j+1,i) == 'o' && board.getEntry(j+2,i) == 'B')
				{
					board.setEntry('W', j+1, i);
				}
			}
		}
	}
	
	private void checkForGapVerticalWhitePebbles()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize()-2; j ++)
			{
				if (board.getEntry(j,i) == 'W' && board.getEntry(j+1,i) == 'o' && board.getEntry(j+2,i) == 'W')
				{
					board.setEntry('B', j+1, i);
				}
			}
		}
	}
	
	private void countBlacksHorizontal()
	{
		int counter = 0;
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize(); j ++)
			{
				if (board.getEntry(i,j) == 'B')
				{
					counter++;
				}
			}
			if (counter == board.getSize()/2)
			{
				for (int k = 0; k < board.getSize(); k ++)
				{
					if (board.getEntry(i, k) == 'o')
					{
						board.setEntry('W',i,k);
					}
				}
				counter = 0;
			}
			else
			{
				counter = 0;
			}
			
		}
	}
	
	private void countWhitesHorizontal()
	{
		int counter = 0;
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize(); j ++)
			{
				if (board.getEntry(i,j) == 'W')
				{
					counter++;
				}
			}
			if (counter == board.getSize()/2)
			{
				for (int k = 0; k < board.getSize(); k ++)
				{
					if (board.getEntry(i, k) == 'o')
					{
						board.setEntry('B',i,k);
					}
				}
				counter = 0;
			}
			else
			{
				counter = 0;
			}
			
		}
	}

	private void countBlacksVertical()
	{
		int counter = 0;
		for (int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize(); j ++)
			{
				if (board.getEntry(j, i) == 'B')
				{
					counter ++;
				}
			}
			if (counter == board.getSize()/2)
			{
				for (int k = 0; k < board.getSize(); k ++)
				{
					if(board.getEntry(k, i) == 'o')
					{
						board.setEntry('W', k, i);
					}
				}
				counter = 0;
			}
			else
			{
				counter = 0;
			}
		}
	}

	private void countWhitesVertical()
	{
		int counter = 0;
		for (int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize(); j ++)
			{
				if (board.getEntry(j, i) == 'W')
				{
					counter ++;
				}
			}
			if (counter == board.getSize()/2)
			{
				for (int k = 0; k < board.getSize(); k ++)
				{
					if(board.getEntry(k, i) == 'o')
					{
						board.setEntry('B', k, i);
					}
				}
				counter = 0;
			}
			else
			{
				counter = 0;
			}
		}
	}

	private boolean checkHorizontalConsecutiveViolation()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize()-2; j ++)
			{
				if (board.getEntry(i,j) == 'B' && board.getEntry(i,j+1) == 'B' && board.getEntry(i,j+2) == 'B')
				{
					//System.out.println("Three consecutive Blacks found!");
					return true;
				}
				if (board.getEntry(i,j) == 'W' && board.getEntry(i,j+1) == 'W' && board.getEntry(i,j+2) == 'W')
				{
					//System.out.println("Three consecutive Whites found!");
					return true;
				}
			}
		}
		//System.out.println("No violations horizontally!");
		return false;
	}
	
	private boolean checkVerticalConsecutiveViolation()
	{
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize() - 2; j++)
			{
				if (board.getEntry(j,i) == 'B' && board.getEntry(j+1,i) == 'B' && board.getEntry(j+2,i) == 'B')
				{
					//System.out.println("Three consecutive Blacks found!");
					return true;
				}
				if (board.getEntry(j,i) == 'W' && board.getEntry(j+1,i) == 'W' && board.getEntry(j+2,i) == 'W')
				{
					//System.out.println("Three consecutive Whites found!");
					return true;
				}
			}
		}
		//System.out.println("No violations vertically!");
		return false;
	}
	
	private boolean checkNumberOfPebblesHorizontalViolation()
	{
		int counter = 0;
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize(); j ++ )
			{
				if (board.getEntry(i, j) == 'B')
				{
					counter++;
				}
				if (counter > board.getSize()/2)
				{
					//System.out.println("Too many horizontal blacks");
					return true;
				}
			}
			counter = 0;
			
		}
		counter = 0;
		
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize(); j ++ )
			{
				if (board.getEntry(i, j) == 'W')
				{
					counter++;
				}
				if (counter > board.getSize()/2)
				{
					//System.out.println("Too many horizontal whites");
					return true;
				}
			}
			counter = 0;
			
		}
		counter = 0;
		return false;
	}
	
	private boolean checkNumberOfPebblesVerticalViolation()
	{
		int counter = 0;
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize(); j ++ )
			{
				if (board.getEntry(j, i) == 'B')
				{
					counter++;
				}
				if (counter > board.getSize()/2)
				{
					//System.out.println("Too many vertical blacks");
					return true;
				}
			}
			counter = 0;
			
		}
		counter = 0;
		
		for(int i = 0; i < board.getSize(); i ++)
		{
			for (int j = 0; j < board.getSize(); j ++ )
			{
				if (board.getEntry(j, i) == 'W')
				{
					counter++;
				}
				if (counter > board.getSize()/2)
				{
					//System.out.println("Too many vertical whites");
					return true;
				}
			}
			counter = 0;
			
		}
		counter = 0;
		return false;
	}
	
	int counterGuessTwo = 0;
	private void guessTwoInARow(int row, int column)
	{
		//System.out.println("Guessing 2");
		if (counterGuessTwo > 3)
		{
			//System.out.println("can't anymore");
			//counterGuessTwo = 0;
			goBackToPreviousBoard(2);
		}
		else
		{
		//store state of the board
		Character temp[][] = CopyArray.copyArray(board.getArray());
		//access array of values to try
		String pebbles = new String();
		pebbles = ArraysOfGuesses.two[counterGuessTwo];
		//System.out.println("Currently guessing " + pebbles);
		char firstPebble = pebbles.charAt(0);
		char secondPebble = pebbles.charAt(2);
		board.setEntry(firstPebble, row, column);
		board.setEntry(secondPebble, row, column+1);
		mainAlgorithm();
		if(checkHorizontalConsecutiveViolation() == true || checkVerticalConsecutiveViolation() == true || checkNumberOfPebblesHorizontalViolation() == true || checkNumberOfPebblesVerticalViolation() == true)
		{
			board.setArray(temp);
			counterGuessTwo++;
			//System.out.println("cycle");
			guessTwoInARow(row,column);
		}
		else
		{
			//System.out.println("YES");
			boardState.add(CopyArray.copyArray(board.getArray()));
			locationOfGuessing.add(Integer.toString(row)+" "+Integer.toString(column));
			typeOfGuess.add(2);
			counterValue.add(counterGuessTwo);
			counterGuessTwo = 0;
			indexOfSafeStates++;
			startGuessing();
		}
	}
		
	}

	int counterGuessOne = 0;
	private void guessOneInARow(int row, int column)
	{
		//System.out.println("Guessing 1");
		if (counterGuessOne > 1)
		{
			//System.out.println("can't anymore");
			counterGuessOne = 0;
			goBackToPreviousBoard(1);
		}
		else
		{
			//System.out.println("BOARD BEFORE WE DO SHIT: ");
			//board.print();
			Character[][] temp = CopyArray.copyArray(board.getArray());
			Character input = ArraysOfGuesses.one[counterGuessOne];
			//System.out.println("Currently guessing " + input);
			board.setEntry(input, row, column);
			mainAlgorithm();
			if (checkHorizontalConsecutiveViolation() == true || checkVerticalConsecutiveViolation() == true || checkNumberOfPebblesHorizontalViolation() == true || checkNumberOfPebblesVerticalViolation() == true)
			{
				//System.out.println("Cycle");
				board.setArray(temp);
				counterGuessOne++;
				guessOneInARow(row,column);
			}
			else
			{
				//System.out.println("YESSSS");
				Character[][] goodArray = new Character[board.getSize()][board.getSize()];
				goodArray = CopyArray.copyArray(board.getArray());
				boardState.add(goodArray);
				locationOfGuessing.add(Integer.toString(row)+" "+Integer.toString(column));
				typeOfGuess.add(1);
				counterValue.add(counterGuessOne);
				indexOfSafeStates+= 1; 	//previous state is definitely safe.
				counterGuessOne = 0;
				startGuessing();
			}
		}
	}
	
	int counterGuessThree = 0;
	private void guessThreeInARow(int row, int column)
	{
		
	}

	private void goBackToPreviousBoard(int typeJustMade)
	{
	
		//System.out.println("TIME TO GO BACK!");
		//check type of guess of last safe state, if same, then go back again.
		switch(typeOfGuess.get(indexOfSafeStates-1))
		{
			case 1:
			{
				Character[][] tempArray = new Character [board.getSize()][board.getSize()];
				tempArray = boardState.get(indexOfSafeStates-1);
				board.setArray(tempArray);
				counterGuessOne = counterValue.get(indexOfSafeStates-1)+1;
				
				String row = locationOfGuessing.get(indexOfSafeStates-1).substring(0, locationOfGuessing.get(indexOfSafeStates-1).indexOf(" "));
				String column = locationOfGuessing.get(indexOfSafeStates-1).substring(locationOfGuessing.get(indexOfSafeStates-1).indexOf(" ")+1,locationOfGuessing.get(indexOfSafeStates-1).length());
				indexOfSafeStates --;
				//System.out.println(row);
				//System.out.println(column);
				guessOneInARow(Integer.valueOf(row), Integer.valueOf(column));
				break;
			}
		}
	}
	

}
