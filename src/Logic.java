import java.util.ArrayList;


public class Logic {

	private Board board;
	public Logic(Board board)
	{
		this.board = board;
	}

	ArrayList<Integer> rowStored = new ArrayList<Integer>();
	ArrayList<Integer> columnStored = new ArrayList<Integer>();
	ArrayList<Character[][]> boardStored = new ArrayList<Character[][]>();

	public boolean solvable = true; //indicate whether the board is solvable or not.
	
	public void startSolving()
	{
		mainAlgorithm();
		startGuessing();
	}

	private boolean checkRules()
	{
		if(checkHorizontalConsecutiveViolation() == true || checkVerticalConsecutiveViolation() == true || checkNumberOfPebblesHorizontalViolation() == true || checkNumberOfPebblesVerticalViolation() == true)
		{
			return false; //implies rules are not being followed
		}
		else
		{
			return true; //implies rules are being followed
		}
	}

	private void startGuessing()
	{
		shortcutFill();
		guessing(getEmptyPositionRow(), getEmptyPositionColumn(), 0, board.getArray());
	}

	private int getEmptyPositionColumn()
	{
		for (int i = 0; i < board.getSize(); i ++)
		{
			for(int j = 0; j < board.getSize(); j ++)
			{
				if (board.getEntry(i, j) == 'o')
				{
					return j;
				}
			}
		}
		return -1;
	}

	private int getEmptyPositionRow()
	{
		for (int i = 0; i < board.getSize(); i ++)
		{
			for(int j = 0; j < board.getSize(); j ++)
			{
				if (board.getEntry(i, j) == 'o')
				{
					return i;
				}
			}
		}
		return -1;
	}

	private void guessing(int row, int column, int attempt, Character[][] boardArray) throws ArrayIndexOutOfBoundsException
	{
		//this recursive method attempts to make guesses in positions which could not be filled with the shortcutFill() method.
		if (row == -1)
		{
			return;
		}
		System.gc();
		shortcutFill();
		Character[][] temp = CopyArray.copyArray(board.getArray());
		if (attempt == 0) //put black
		{
			rowStored.add(row);
			columnStored.add(column);
			boardStored.add(CopyArray.copyArray(board.getArray()));
			board.setEntry('B', row, column);
			if (checkRules() == true)
			{
				guessing(getEmptyPositionRow(), getEmptyPositionColumn(), 0, CopyArray.copyArray(board.getArray()));
			}
			else
			{
				board.setArray(temp);
				guessing(row,column,1, temp);
			}
		}
		else if (attempt == 1) //put white
		{
			board.setArray(boardArray);
			board.setEntry('W', row, column);
			if (checkRules() == true)
			{
				guessing(getEmptyPositionRow(), getEmptyPositionColumn(), 0, CopyArray.copyArray(board.getArray()));
			}
			else
			{
				board.setArray(boardArray);
				guessing(rowStored.get(rowStored.size()-1),columnStored.get(columnStored.size()-1), 2, boardStored.get(boardStored.size()-1)); //go back and fix it!
			}
		}
		else if (attempt == 2) //backtrack to the previous board in the ArrayList.
		{
			board.setArray(boardArray);
			rowStored.remove(rowStored.size()-1);
			columnStored.remove(columnStored.size()-1);
			boardStored.remove(boardStored.size()-1);
			if(board.getEntry(row, column) == 'B')
			{
				board.setEntry('W', row,column);
				if (checkRules() == true)
				{
					guessing(getEmptyPositionRow(), getEmptyPositionColumn(), 0, board.getArray());
				}
				else
				{
					board.setEntry('o', rowStored.get(rowStored.size()-1), columnStored.get(columnStored.size()-1));
					board.setArray(boardArray);
					guessing(rowStored.get(rowStored.size()-1), columnStored.get(columnStored.size()-1),2, boardStored.get(boardStored.size()-1));
				}
			}
			else
			{
				try {
					guessing(rowStored.get(rowStored.size()-1), columnStored.get(columnStored.size()-1),2, CopyArray.copyArray(board.getArray()));
				} catch (Exception e) {
					System.out.println("UNSOLVABLE");
					solvable = false;
					return;
				}
			}
		}
	}


	private void shortcutFill() //sequentially goes through the board and tries to fill up the board quicker using specific rules.
	{	
		for(int i = 0; i < board.getSize(); i ++)
		{	
			for (int j = 0; j <board.getSize(); j ++)
			{
				if (board.getEntry(i, j) == 'o')
				{
					Character[][] tempArray = CopyArray.copyArray(board.getArray());
					board.setEntry('B', i, j);
					mainAlgorithm();
					if (checkRules() == true) //if Black works
					{
						board.setArray(CopyArray.copyArray(tempArray)); //try white
						board.setEntry('W', i,j);
						mainAlgorithm();
						if (checkRules() == false) //if white does not work then that implies only Black works.
						{
							board.setArray(CopyArray.copyArray(tempArray));
							board.setEntry('B',i,j);
							mainAlgorithm();
						}
						else //implies black and white both work, don't risk adding anything in this position.
						{
							board.setArray(CopyArray.copyArray(tempArray));
						}
					}
					else //implies black does not work, let's see if white works.
					{
						board.setArray(CopyArray.copyArray(tempArray));
						board.setEntry('W', i,j);
						mainAlgorithm();
						if (checkRules() == false) //implies white does not work, don't risk adding anything in this position.
						{
							board.setArray(CopyArray.copyArray(tempArray));
						}
						else //implies white works, ONLY black works, keep as is.
						{
	
						}
					}
				}
	
			}
		}
	}

	private void mainAlgorithm() {
		for (int i = 0; i < 100; i++) {
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
					
					return true;
				}
				if (board.getEntry(i,j) == 'W' && board.getEntry(i,j+1) == 'W' && board.getEntry(i,j+2) == 'W')
				{
					
					return true;
				}
			}
		}
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
				
					return true;
				}
				if (board.getEntry(j,i) == 'W' && board.getEntry(j+1,i) == 'W' && board.getEntry(j+2,i) == 'W')
				{
					
					return true;
				}
			}
		}

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
					
					return true;
				}
			}
			counter = 0;
		}
		counter = 0;
		return false;
	}





}