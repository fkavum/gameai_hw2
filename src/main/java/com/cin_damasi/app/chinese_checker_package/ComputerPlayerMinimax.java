package com.cin_damasi.app.chinese_checker_package;

import java.util.Random;

import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_GREEN_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_NO_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_RED_PIECE;

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
	public int currentNodePlayer;
	public GameStateMinimax currentNode;
	
    public ComputerPlayerMinimax(int whichPlayer)
    {
        super(whichPlayer);
        ;
    }

    /* 
     * You will implement this function in homework
     */
    private int calculateStateHeuristicValue_1(GameState state)
    {
        return 0;
    }

    /* 
     * You will implement this function in homework
     */
    private int calculateStateHeuristicValue_2(GameState state)
    {
        return 0;
    }

    
    /* 
     * You will implement this function in homework
     */
    public Move getMove(GameState gameState)
    {
    //  get possible moves the player can make
        List<Move> availableMoves = this.getAvailableMoves(gameState);

        //  select a random move	//
        Random random = new Random();
        int randomInt = random.nextInt(availableMoves.size());
        Move randomMove = availableMoves.get(randomInt);
        
        int breakLevel = 2;
        
        
        int [][] newStateArray = GameState.cloneGameState(gameState.getGameStateArray());
        GameStateMinimax originalState = new GameStateMinimax(newStateArray,null);
        
        this.gameStateTree = new ArrayList<GameStateMinimax>();
        this.unCalculatedNodes = new ArrayList<GameStateMinimax>();
        this.gameStateTree.add(originalState);
        this.unCalculatedNodes.add(originalState);
         
        while (unCalculatedNodes.size() != 0 ) {

        	this.currentNode = this.unCalculatedNodes.get(0);
        	if (this.currentNode.stateLayer >= breakLevel) {break;}
        	
        	List<int [][]> nodes = generateNextNodes(this.currentNode);
        	
        	for (int [][] node: nodes)
            {
        		GameStateMinimax newNode = new GameStateMinimax(node,this.currentNode);
        		this.gameStateTree.add(newNode);
                this.newLayer.add(newNode);
            }
        	
        	this.unCalculatedNodes.remove(this.currentNode);
        	
        	if(this.unCalculatedNodes.size() == 0 ) {
        		this.unCalculatedNodes.addAll(this.newLayer);
        		this.newLayer =  new ArrayList<GameStateMinimax>();
        	}
        	
        	
        }
        
        
        //Move move = new Move(piece, currentPosition, backwardPosition);
        System.out.println("MOVE YAPILDI");
        return randomMove;   
    	
    	//Move move = new Move(piece, currentPosition, forwardPosition);
        //return null;
    }
    
    
    
    
    public List<int [][]> generateNextNodes(GameStateMinimax gameState) {
    	
    	if(gameState.statePlayer == PLAYER_RED) {
    		this.currentNodePlayer = PLAYER_GREEN;
    	}else {
    		this.currentNodePlayer = PLAYER_RED;
    	}
    	
    	
    	List<int [][]> nodes = getAvailableMovesArray(gameState.stateArray);
    	
    	return nodes;
    }
    
    public List<int [][]> getAvailableMovesArray(int [][] gameStateArray)
    {
        List<int [][]> availableMoves = this.getAvailableForwardMovesArray(gameStateArray);

        //  if player does not have any forward moves left, return backward moves
        if (availableMoves.size() == 0)
        {
            availableMoves = this.getAvailableBackwardMovesArray(gameStateArray);
        }

        return availableMoves;
    }
    
    public List<int [][]> getAvailableForwardMovesArray(int [][] gameStateArray)
    {
        List<int [][]> availableForwardMoves = new ArrayList<int [][]>();

        for (int row = 0; row < 8; ++row)
        {
            for (int col = 0; col < 8; ++col)
            {
                //  check if there is a square in location
                if (gameStateArray[row][col] == this.currentNodePlayer)     //row -- column
                {
                	 availableForwardMoves.addAll(this.getNextMovesForwardArray(row,col,gameStateArray));
                }
            }
        }
        
       
        return availableForwardMoves;
    }

    public List<int [][]> getNextMovesForwardArray(int row, int col,int [][] gameStateArray)
    {
        //  function will returns list of possible Moves
        //the piece can perform
        List<int [][]> moves = new ArrayList<int [][]>();
        
        //  get position information of piece
        int i = row;
        int j = col;

        
        List<PiecePosition> forwardPositions = getForwardPositions(row, col);
        for (PiecePosition forwardPosition: forwardPositions)
        {
            
            if (isPositionAvailable(forwardPosition,gameStateArray))
            {
                //  create a Move object from a piece, current position
                //and the position after move
            	int [][] newState = createNewStateArray(row,col, forwardPosition, gameStateArray);
                moves.add(newState);
            }
        }
        
        
        List<PiecePosition> forwardJumpPositions = getForwardJumpPositions(row, col);
        for (PiecePosition forwardJumpPosition: forwardJumpPositions)
        {
        	
        	PiecePosition middlePosition = getBetweenPosition(row,col,forwardJumpPosition);
            //  check if positions are available
            //available = empty AND within game board
            if (!isPositionAvailable(middlePosition,gameStateArray) && (isPositionAvailable(forwardJumpPosition,gameStateArray)))
            {
                //  create a Move object from a piece, current position
                //and the position after move
            	int [][] newState = createNewStateArray(row,col, forwardJumpPosition, gameStateArray);
                moves.add(newState);
            }
        }
        
        //  return list of found moves
        return moves;
    }

    
    public List<int [][]> getAvailableBackwardMovesArray(int [][] gameStateArray)
    {
    	List<int [][]> availableBackwardMoves = new ArrayList<int [][]>();

        for (int row = 0; row < 8; ++row)
        {
            for (int col = 0; col < 8; ++col)
            {
                //  check if there is a square in location
                if (gameStateArray[row][col] == this.currentNodePlayer)     //row -- column
                {
                	availableBackwardMoves.addAll(this.getNextMovesBackwardArray(row,col,gameStateArray));
                }
            }
        }
       
        return availableBackwardMoves;
    	
    }
    
    public List<int [][]> getNextMovesBackwardArray(int row, int col,int [][] gameStateArray)
    {

        //  function will returns list of possible Moves
        //the piece can perform
        List<int [][]> moves = new ArrayList<int [][]>();
        
        //  get position information of piece
        int i = row;
        int j = col;

        //  check if piece in destination area
        if (!isPieceInGoal(row,col))
        {
            //  return empty list of moves
            return moves;
        }

        //  get adjacent squares in backward direction
        List<PiecePosition> backwardPositions = getBackwardPositions(row, col);
        for (PiecePosition backwardPosition: backwardPositions)
        {
            //  check if positions are available
            //available = empty AND within game board
            if (isPositionAvailable(backwardPosition,gameStateArray))
            {
                //  create a Move object from a piece, current position
                //and the position after move
            	int [][] newState = createNewStateArray(row,col, backwardPosition, gameStateArray);
            	
                moves.add(newState);
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
    
    
    
    public List<PiecePosition> getForwardPositions(int row, int col)
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();

        if (this.currentNodePlayer == PLAYER_RED)
        {
            //  red pieces go upper left when moving forward
            poses.add(new PiecePosition(row-1, col));
            poses.add(new PiecePosition(row, col-1));
        }
        else if (this.currentNodePlayer == PLAYER_GREEN)
        {
            //  green pieces go bottom right when moving forward
            poses.add(new PiecePosition(row+1, col));
            poses.add(new PiecePosition(row, col+1));
        }
        return poses;
    }
    
    
    public List<PiecePosition> getForwardJumpPositions(int row, int col)
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();


        if (this.currentNodePlayer == PLAYER_RED)
        {
            //  red pieces go upper left when moving forward
            poses.add(new PiecePosition(row-2, col));
            poses.add(new PiecePosition(row, col-2));
        }
        else if (this.currentNodePlayer == PLAYER_GREEN)
        {
            //  green pieces go bottom right when moving forward
            poses.add(new PiecePosition(row+2, col));
            poses.add(new PiecePosition(row, col+2));
        }

        return poses;
    }
    
    public List<PiecePosition> getBackwardPositions(int row, int col)
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();

        if (this.currentNodePlayer == PLAYER_RED)
        {
            //  red pieces go bottom right when going backward
            poses.add(new PiecePosition(row+1, col));
            poses.add(new PiecePosition(row, col+1));
            
        }
        else if (this.currentNodePlayer == PLAYER_GREEN)
        {
            //  green pieces go upper left when going backward
            poses.add(new PiecePosition(row-1, col));
            poses.add(new PiecePosition(row, col-1));
        }
        return poses;
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
    
    
    
    public boolean isPieceInGoal(int row, int col)
    {

        //  red pieces go to upper left
        if (this.currentNodePlayer == PLAYER_RED)
        {
            return row >= 0 && col>= 0 && row < 3 && col< 3;
        }

        //  green pieces go to bottom right
        else if (this.currentNodePlayer == PLAYER_GREEN)
        {
            return row >= 5 && col >= 5 && row < 8 && col< 8;
        }

        return false;
    }

    
    
    public int[][] createNewStateArray(int row, int col,PiecePosition newPosition,int [][] gameStateArray){
    	
    	int [][] newStateArray = GameStateMinimax.cloneGameState(gameStateArray);
    	
    	newStateArray[row][col] = PLAYER_NONE;
    	newStateArray[newPosition.getRow()][newPosition.getColumn()] = currentNodePlayer;
    	
    	return newStateArray;
    } 
    
}