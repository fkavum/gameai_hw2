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
	public int currentNodePlayer;
	
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
        int currentLevel = 0;
        GameStateMinimax originalState = new GameStateMinimax(gameState.getGameStateArray(),null);
        
        this.gameStateTree = new ArrayList<GameStateMinimax>();
        this.unCalculatedNodes = new ArrayList<GameStateMinimax>();
        this.gameStateTree.add(originalState);
        this.unCalculatedNodes.add(originalState);
         
        while (unCalculatedNodes.size() != 0 && currentLevel < breakLevel ) {

        	GameStateMinimax currentNode = this.unCalculatedNodes.get(0);
        	List<int [][]> nodes = generateNextNodes(this.unCalculatedNodes.get(0));
        	
        	for (int [][] node: nodes)
            {
        		GameStateMinimax newNode = new GameStateMinimax(node,currentNode);
        		this.gameStateTree.add(newNode);
                this.unCalculatedNodes.add(newNode);
            }
        	
        	this.unCalculatedNodes.add(currentNode);
        	break;
        }
        
        
        
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

        for (Piece piece: this.pieces)
        {
            availableBackwardMoves.addAll(this.getNextMovesBackwardArray(piece, gameStateArray));
        }
        return availableBackwardMoves;
    }
    
    public List<int [][]> getNextMovesBackwardArray(Piece piece,int [][] gameStateArray)
    {
        //  function will returns list of possible Moves
        //the piece can perform
        List<int [][]> moves = new ArrayList<int [][]>();
        
        //  get position information of piece
        int i = piece.getPosition().getRow();
        int j = piece.getPosition().getColumn();
        PiecePosition currentPosition = piece.getPosition();

        //  check if piece in destination area
        if (!isPieceInGoal(piece))
        {
            //  return empty list of moves
            return moves;
        }

        //  get adjacent squares in backward direction
        List<PiecePosition> backwardPositions = piece.getBackwardPositions();
        for (PiecePosition backwardPosition: backwardPositions)
        {
            //  check if positions are available
            //available = empty AND within game board
            if (isPositionAvailable(backwardPosition,gameStateArray))
            {
                //  create a Move object from a piece, current position
                //and the position after move
                Move move = new Move(piece, currentPosition, backwardPosition);
                //moves.add(move);
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
    
    
    
    public static boolean isPieceInGoal(Piece piece)
    {
        int row = piece.getPosition().getRow();
        int column = piece.getPosition().getColumn();

        //  red pieces go to upper left
        if (piece.getColor() == PIECE_COLOR_RED_PIECE)
        {
            return row >= 0 && column >= 0 && row < 3 && column < 3;
        }

        //  green pieces go to bottom right
        else if (piece.getColor() == PIECE_COLOR_GREEN_PIECE)
        {
            return row >= 5 && column >= 5 && row < 8 && column < 8;
        }

        return false;
    }

    
    
    public int[][] createNewStateArray(int row, int col,PiecePosition newPosition,int [][] gameStateArray){
    	
    	int [][] newStateArray = gameStateArray;
    	
    	newStateArray[row][col] = PLAYER_NONE;
    	newStateArray[newPosition.getRow()][newPosition.getColumn()] = currentNodePlayer;
    	
    	return newStateArray;
    } 
    
}