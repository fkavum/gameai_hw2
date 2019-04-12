package com.cin_damasi.app.chinese_checker_package;

import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_NO_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_NONE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_RED;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_GREEN;


import java.util.ArrayList;
import java.util.List;

class ComputerPlayerMinimax extends ComputerPlayer
{
	
	public List<GameStateMinimax> gameStateTree = new ArrayList<GameStateMinimax>();
	public List<GameStateMinimax> unCalculatedNodes = new ArrayList<GameStateMinimax>();
	public List<GameStateMinimax> newLayer = new ArrayList<GameStateMinimax>();
	public List<GameStateMinimax> layer1 = new ArrayList<GameStateMinimax>();

	private int breakLevel = 4;
	
	
    public ComputerPlayerMinimax(int whichPlayer)
    {
        super(whichPlayer);
        ;
    }

    /* 
     * You will implement this function in homework
     */
    private void calculateStateHeuristicValue_1(GameStateMinimax state)
    {
    	
    	int winner = isGameFinished(state.stateArray);
    	if (winner == state.minMaxPlayer) {
    		state.score = 100;
    	}else if(winner == 0) {
		int score = 0;
		int tempScore = 0;
		int redCounter = 0; //00-01-02 // 10-11-12 // 20-21-22
		int greenCounter = 0; //55-56-57//65-66-67//75-76-77
		
		 for (int row = 0; row < 8; ++row)
	        {
	            for (int col = 0; col < 8; ++col)
	            {        	
	            	switch (state.stateArray[row][col]) {
	            	case 0:
	            		break;
	            	case PLAYER_RED:
	            		tempScore =  manhattanDist(row,col,PLAYER_RED,redCounter); //ne kadar kücükse o kadar iyi red için. 1-5
	            		if (state.minMaxPlayer == PLAYER_RED) {
	            			score -= tempScore;
	            		}else {
	            			score += tempScore;
	            		}
	            		redCounter +=1;
	            		break;
	            	case PLAYER_GREEN:
	            		tempScore =  manhattanDist(row,col,PLAYER_GREEN,greenCounter);
	            		if (state.minMaxPlayer == PLAYER_RED) {
	            			score += tempScore;
	            		}else {
	            			score -= tempScore;
	            		}
	            		greenCounter +=1;
	            		break;
	            	default:
	            		System.out.println("StateId:" + state.stateId + " has unexpected piece");
	            	}
	            }
	        }
		
		 state.score = score;}else {
			 state.score = -100;
		 }
    }

    /* 
     * You will implement this function in homework
     */
    private void calculateStateHeuristicValue_2(GameStateMinimax state)
    {
    	int winner = isGameFinished(state.stateArray);
    	if (winner == state.minMaxPlayer) {
    		state.score = 900;
    	}else if(winner == 0) {
		int score = 0;
		int tempScore = 0;
		int penalty =0;
		
		int row0 = 0;
		int row1 = 0;
		
		int col0 = 0;
		int col1= 0;
		
		
		
		int col6= 0;
		int col7= 0;
		
		int row6= 0;
		int row7= 0;
		
		 for (int row = 0; row < 8; ++row)
	        {
	            for (int col = 0; col < 8; ++col)
	            {        	
	            	switch (state.stateArray[row][col]) {
	            	case 0:
	            		break;
	            	case PLAYER_RED:
	            		tempScore =  Math.abs(row-0)+Math.abs(col-0); //ne kadar kücükse o kadar iyi red için. 1-5
	            		
	            		if (row == 7 && col == 7 ) {tempScore +=1;}
	            		if (row == 6 && col == 7 ) {tempScore +=1;}
	            		if(state.minMaxPlayer ==PLAYER_RED) {
	            		switch (row) {
	            		case 0:
	            			row0 += 1;
	            			break;
	            		case 1:
	            			row1 += 1;
	            			break;
	            	
	            		}switch(col) {
	            			case 0:
	            				col0 += 1;
	            				break;
	            			case 1:
	            				col1 += 1;
	            				break;
	            		
	            		}
	            		penalty = 0;
	            		if (row0 >3 || col0>3) {
	            			penalty = 100;
	            		}
	            		if (row1 >6 || col1>6) {
	            			penalty = 100;
	            		}
	            		if ((row1 >5 && row0 > 0)|| (col1>5 && col0 > 0 )) {
	            			penalty = 100;
	            		}
	            		if ((row1 >4 && row0 > 1)|| (col1>4 && col0 > 1 )) {
	            			penalty = 100;
	            		}
	            		if ((row1 >3 && row0 > 2)|| (col1>3 && col0 > 2 )) {
	            			penalty = 100;
	            		}}
	            			            		
	            		if (state.minMaxPlayer == PLAYER_RED) {
	            			score -= tempScore;
	            			score -= penalty;
	            		}else {
	            			score += tempScore;
	            			//score += penalty;
	            		}
	            		break;
	            	case PLAYER_GREEN:
	            		tempScore =  Math.abs(row-7)+Math.abs(col-7);
	            		if (row == 0 && col == 0 ) {tempScore +=1;}
	            		if (state.minMaxPlayer == PLAYER_GREEN) {
	            		switch (row) {
	            		
	            		case 6:
	            			row6 += 1;
	            			break;
	            		case 7:
	            			row7 += 1;
	            			break;
	            		}switch(col) {
	            			
	            			case 6:
	            				col6 += 1;
	            				break;
	            			case 7:
	            				col7 += 1;
	            				break;
	            		}
	            		
	            		penalty = 0;
	            		if (row7 >3 || col7>3) {
	            			penalty = 100;
	            		}
	            		if (row6 >6 || col6>6) {
	            			penalty = 100;
	            		}
	            		if ((row6 >5 && row7 > 0)|| (col6>5 && col7 > 0 )) {
	            			penalty = 100;
	            		}
	            		if ((row6 >4 && row7 > 1)|| (col6>4 && col7 > 1 )) {
	            			penalty = 100;
	            		}
	            		if ((row6 >3 && row7 > 2)|| (col6>3 && col7 > 2 )) {
	            			penalty = 100;
	            		}}
	            		
	            		if (state.minMaxPlayer == PLAYER_RED) {
	            			score += tempScore;
	            			//score += penalty;
	            		}else {
	            			score -= tempScore;
	            			score -= penalty;
	            		}
	            		
	            		break;
	            	default:
	            		System.out.println("StateId:" + state.stateId + " has unexpected piece");
	            	}
	            }
	        }
		
		 state.score = score;}else {
			 state.score = -900;
		 }
    }

    
	private int manhattanDist(int row,int col, int player,int counter) {
		
		int[] dest = null;
		
		
		if (player == PLAYER_RED) {
			dest = GameStateMinimax.redDestList.get(counter);
		}else if (player == PLAYER_GREEN) {
			dest = GameStateMinimax.greenDestList.get(counter);
		}
		int returnValue = Math.abs(row-dest[0])+Math.abs(col-dest[1]);
		return returnValue;
	}
	
    
    /* 
     * You will implement this function in homework
     */
    public Move getMove(GameState gameState)
    {  
        int [][] newStateArray = GameState.cloneGameState(gameState.getGameStateArray());
        GameStateMinimax originalState = new GameStateMinimax(newStateArray,null,null,null,this.whichPlayer);
        
        GameStateMinimax selectedNode = selectNode(originalState,999);
        
        Piece selectedPiece = null;
        for (Piece piece: this.pieces)
        {
    		int pieceRow = piece.getPosition().getRow();
    		int pieceCol = piece.getPosition().getColumn();
    		
    		if (selectedNode.prevPos.getRow() == pieceRow && selectedNode.prevPos.getColumn() == pieceCol) {
    			selectedPiece = piece;
    		}
    		
        }

        Move move = new Move(selectedPiece, selectedPiece.getPosition(), selectedNode.movedPos);
        System.out.println("MOVE YAPILDI");
        return move;   	
    }
    
    
    
public GameStateMinimax selectNode(GameStateMinimax gamestate, int givenScore) {
    	
    	List<GameStateMinimax> nodes = generateNextNodes(gamestate);
    	if(nodes.get(0).stateLayer == this.breakLevel) {
    		
    		for (GameStateMinimax node:nodes) {
    			calculateStateHeuristicValue_2(node);
    		}

    	}else {
    	
    		int tempBestScore = 999999;
    		boolean skip = false;
    		int deleteIndex = 0;
    		
    	for (GameStateMinimax node: nodes) {
    		if (skip) {
    			
    			while (nodes.size() > deleteIndex +1 ) {
    				//System.out.println("Deleted at level:" + node.stateLayer);
    				nodes.remove(nodes.size() -1);
    			}
    			
    			break;}
    		
    		if (isGameFinished(node.stateArray) != 0) {
    			calculateStateHeuristicValue_2(node);
    			continue;
    		}
    		
    		
    		node.score = selectNode(node,tempBestScore).score;
    		
    		if (this.whichPlayer != node.statePlayer) {
    			tempBestScore = node.score;
    		}
    		
    		if (this.whichPlayer == node.statePlayer) {
    			if (node.score > givenScore) {
    				deleteIndex = nodes.indexOf(node);
    				skip = true;
    			}
    		}
    	}}
    	
    	GameStateMinimax theBest = null;
    	
    	if (gamestate.stateOpponent == this.whichPlayer) {
    		int bestScore = -999999;
    		
    		for (GameStateMinimax nextNode: nodes) {
    			
    			if (nextNode.score > bestScore) {
    				bestScore = nextNode.score;
    				theBest = nextNode;
    			}
    			
    		}
    		
    		
    	}else {
    		
    		int bestScore = 999999;
    		
    		for (GameStateMinimax nextNode: nodes) {
    			
    			if (nextNode.score < bestScore) {
    				bestScore = nextNode.score;
    				theBest = nextNode;
    			}
    			
    		}
    		
    		
    		
    	}
    	
    	
    	return theBest;
    }
    
    
    
    
    
    public List<GameStateMinimax> generateNextNodes(GameStateMinimax gameState) {
    	
    	List<GameStateMinimax> nodes = getAvailableMovesArray(gameState);
    	
    	if(nodes.size() == 0) {gameState.lastLayer();}  //Burayi bir dusun.
    	
    	return nodes;
    }
    
    public List<GameStateMinimax> getAvailableMovesArray(GameStateMinimax gameStateArray)
    {
        List<GameStateMinimax> availableMoves = this.getAvailableForwardMovesArray(gameStateArray);

        //  if player does not have any forward moves left, return backward moves
        if (availableMoves.size() == 0)
        {
            availableMoves = this.getAvailableBackwardMovesArray(gameStateArray);
        }

        return availableMoves;
    }
    
    public List<GameStateMinimax> getAvailableForwardMovesArray(GameStateMinimax gameStateArray)
    {
        List<GameStateMinimax> availableForwardMoves = new ArrayList<GameStateMinimax>();

        for (int row = 0; row < 8; ++row)
        {
            for (int col = 0; col < 8; ++col)
            {
                //  check if there is a square in location
                if (gameStateArray.stateArray[row][col] == gameStateArray.stateOpponent)     //row -- column
                {
                	 availableForwardMoves.addAll(this.getNextMovesForwardArray(row,col,gameStateArray));
                }
            }
        }
        
       
        return availableForwardMoves;
    }

    public List<GameStateMinimax> getNextMovesForwardArray(int row, int col,GameStateMinimax gameStateArray)
    {

        List<GameStateMinimax> moves = new ArrayList<GameStateMinimax>();
        
        
        List<PiecePosition> forwardPositions = getForwardPositions(row, col,gameStateArray);
        for (PiecePosition forwardPosition: forwardPositions)
        {
            
            if (isPositionAvailable(forwardPosition,gameStateArray.stateArray))
            {

            	int [][] newState = createNewStateArray(row,col, forwardPosition, gameStateArray);
            	GameStateMinimax newNode = new GameStateMinimax(newState,gameStateArray,getPiecePosition(row,col),forwardPosition,this.whichPlayer);
                
            	if(this.breakLevel == newNode.stateLayer) {newNode.lastLayer();}
            	
            	moves.add(newNode);
            }
        }
        
        //-------------------------------------
        
        PiecePosition currentPos = new PiecePosition(row, col);
        List<PiecePosition> pieceStore = new ArrayList<PiecePosition>();
        pieceStore.add(currentPos);
        while(pieceStore.size() != 0) 
        {
        
        	PiecePosition takenPosition = pieceStore.get(0);	
        	List<PiecePosition> forwardJumpPositions = getForwardJumpPositions(takenPosition.getRow(),takenPosition.getColumn(),gameStateArray);
	        pieceStore.remove(takenPosition);
	        for (PiecePosition forwardJumpPosition: forwardJumpPositions)
	        {
	        	PiecePosition middlePosition = getBetweenPosition(takenPosition.getRow(),takenPosition.getColumn(),forwardJumpPosition);
	            //  check if positions are available
	            //available = empty AND within game board
	            if (!isPositionAvailable(middlePosition,gameStateArray.stateArray) && (isPositionAvailable(forwardJumpPosition,gameStateArray.stateArray)))
	            {
	                //  create a Move object from a piece, current position
	                //and the position after move
	            	pieceStore.add(forwardJumpPosition);
	            	int [][] newState = createNewStateArray(currentPos.getRow(),currentPos.getColumn(), forwardJumpPosition, gameStateArray);
	            	GameStateMinimax newNode = new GameStateMinimax(newState,gameStateArray,getPiecePosition(row,col),forwardJumpPosition,this.whichPlayer);
	            	if(this.breakLevel == newNode.stateLayer) {newNode.lastLayer();}
	            	moves.add(newNode);
         
	            }
	        }
	     }
        
        //  return list of found moves
        return moves;
    }

    
    public List<GameStateMinimax> getAvailableBackwardMovesArray(GameStateMinimax gameStateArray)
    {
    	List<GameStateMinimax> availableBackwardMoves = new ArrayList<GameStateMinimax>();

        for (int row = 0; row < 8; ++row)
        {
            for (int col = 0; col < 8; ++col)
            {
                //  check if there is a square in location
                if (gameStateArray.stateArray[row][col] == gameStateArray.stateOpponent)     //row -- column
                {
                	availableBackwardMoves.addAll(this.getNextMovesBackwardArray(row,col,gameStateArray));
                }
            }
        }
       
        return availableBackwardMoves;
    	
    }
    
    public List<GameStateMinimax> getNextMovesBackwardArray(int row, int col,GameStateMinimax gameStateArray)
    {

        List<GameStateMinimax> moves = new ArrayList<GameStateMinimax>();
        
        if (!isPieceInGoal(row,col,gameStateArray))
        {
            //  return empty list of moves
            return moves;
        }

        //  get adjacent squares in backward direction
        List<PiecePosition> backwardPositions = getBackwardPositions(row, col, gameStateArray);
        for (PiecePosition backwardPosition: backwardPositions)
        {
            //  check if positions are available
            //available = empty AND within game board
            if (isPositionAvailable(backwardPosition,gameStateArray.stateArray))
            {
                //  create a Move object from a piece, current position
                //and the position after move
            	int [][] newState = createNewStateArray(row,col, backwardPosition, gameStateArray);
            	GameStateMinimax newNode = new GameStateMinimax(newState,gameStateArray,getPiecePosition(row,col),backwardPosition,this.whichPlayer);
            	if(this.breakLevel == newNode.stateLayer) {newNode.lastLayer();}
            	moves.add(newNode);
            }
        }

        //  return list of found moves
        return moves;
    }
    
    
    
    /* for (int i = 0; i < 8; ++i)
    {
        for (int j = 0; j < 8; ++j)
        {
            //  check if there is a square in location
            if (originalState[i][j] != PIECE_COLOR_NO_PIECE)     //row -- column
            {
               
            }
        }
    }*/
    
    
    /* for (Piece piece: this.pieces)
        {
            availableForwardMoves.addAll(this.getNextMovesForwardArray(piece,gameStateArray));
        }*/
    
    
    
    public List<PiecePosition> getForwardPositions(int row, int col, GameStateMinimax currentGameState)
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();

        if (currentGameState.stateOpponent == PLAYER_RED)
        {
            //  red pieces go upper left when moving forward
            poses.add(new PiecePosition(row-1, col));
            poses.add(new PiecePosition(row, col-1));
        }
        else if (currentGameState.stateOpponent == PLAYER_GREEN)
        {
            //  green pieces go bottom right when moving forward
            poses.add(new PiecePosition(row+1, col));
            poses.add(new PiecePosition(row, col+1));
        }
        return poses;
    }
    
    
    public List<PiecePosition> getForwardJumpPositions(int row, int col, GameStateMinimax currentGameState)
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();


        if (currentGameState.stateOpponent == PLAYER_RED)
        {
            //  red pieces go upper left when moving forward
            poses.add(new PiecePosition(row-2, col));
            poses.add(new PiecePosition(row, col-2));
        }
        else if (currentGameState.stateOpponent == PLAYER_GREEN)
        {
            //  green pieces go bottom right when moving forward
            poses.add(new PiecePosition(row+2, col));
            poses.add(new PiecePosition(row, col+2));
        }

        return poses;
    }
    
    public List<PiecePosition> getBackwardPositions(int row, int col, GameStateMinimax currentGameState)
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();

        if (currentGameState.stateOpponent == PLAYER_RED)
        {
            //  red pieces go bottom right when going backward
            poses.add(new PiecePosition(row+1, col));
            poses.add(new PiecePosition(row, col+1));
            
        }
        else if (currentGameState.stateOpponent == PLAYER_GREEN)
        {
            //  green pieces go upper left when going backward
            poses.add(new PiecePosition(row-1, col));
            poses.add(new PiecePosition(row, col-1));
        }
        return poses;
    }

    public PiecePosition getPiecePosition(int row, int col) {
    	
    	return new PiecePosition(row, col);
    }
    
    
    public boolean isPositionAvailable(PiecePosition pos,int [][] gameStateArray)
    {
        return isSquareAvailable(pos.getRow(), pos.getColumn(),gameStateArray);
    }
    public boolean isSquareAvailable(int row, int column,int [][] gameStateArray)
    {
        return isSquareInBoard(row, column) && isSquareEmpty(row, column,gameStateArray);
    }
    public boolean isSquareEmpty(int row, int column,int [][] gameStateArray)
    {
        return gameStateArray[row][column] == PIECE_COLOR_NO_PIECE;
    }
    public boolean isSquareInBoard(int row, int column)
    {
        return (row<8) && (column<8) && (row>=0) && (column>=0);
    }
    
    
    
    
    public PiecePosition getBetweenPosition(int row1, int col1, PiecePosition pos2)
    {
       
        int row2 = pos2.getRow();
        int column2 = pos2.getColumn();

        return new PiecePosition((row1+row2)/2, (col1+column2)/2);
    }
    
    
    
    public boolean isPieceInGoal(int row, int col, GameStateMinimax currentGameState)
    {

        //  red pieces go to upper left
        if (currentGameState.stateOpponent == PLAYER_RED)
        {
            return row >= 0 && col>= 0 && row < 3 && col< 3;
        }

        //  green pieces go to bottom right
        else if (currentGameState.stateOpponent == PLAYER_GREEN)
        {
            return row >= 5 && col >= 5 && row < 8 && col< 8;
        }

        return false;
    }

    
    
    public int[][] createNewStateArray(int row, int col,PiecePosition newPosition,GameStateMinimax gameStateArray){
    	
    	int [][] newStateArray = GameStateMinimax.cloneGameState(gameStateArray.stateArray);
    	
    	newStateArray[row][col] = PLAYER_NONE;
    	newStateArray[newPosition.getRow()][newPosition.getColumn()] = gameStateArray.stateOpponent;
    	
    	return newStateArray;
    } 
    
    public int isGameFinished(int [][] gameStateArray)
    {
    	int result = 0; // 1 is redwon - 2 is greenWon 
        
        //  check if all red pieces at top left
        int redCounter = 0;
        for (int row = 0; row < 3; ++row)
        {
            for (int col = 0; col < 3; ++col)
            {
                if (gameStateArray[row][col] == PLAYER_RED)
                {
                    ++redCounter;
                }
            }
        }
        //  return red player as winning player
        if (redCounter == 9)
        {
        	result = PLAYER_RED;
            return result;
        }


        //  check if all green pieces at bottom right
        int greenCounter = 0;
        for (int row = 5; row < 8; ++row)
        {
            for (int col = 5; col < 8; ++col)
            {
                if (gameStateArray[row][col] == PLAYER_GREEN)
                {
                    ++greenCounter;
                }
            }
        }
        //  return green player as winning player
        if (greenCounter == 9)
        {
        	result = PLAYER_GREEN;
            return result;
        }


        /*// check if game is drawn
        if (GameState.isGameDraw(gameStateArray))
        {
            return GameResult.DRAW;
        }*/

        //  no player won
         return result;
    }

    
    
    
    
}