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
	public int currentLayer;
	private int breakLevel = 1;
	
	
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
        /*Random random = new Random();
        int randomInt = random.nextInt(availableMoves.size());
        Move randomMove = availableMoves.get(randomInt);*/
        
        this.currentLayer = 0;
        
        
        int [][] newStateArray = GameState.cloneGameState(gameState.getGameStateArray());
        GameStateMinimax originalState = new GameStateMinimax(newStateArray,null,null,null);
        
        this.gameStateTree = new ArrayList<GameStateMinimax>();
        this.unCalculatedNodes = new ArrayList<GameStateMinimax>();
        this.gameStateTree.add(originalState);
        this.unCalculatedNodes.add(originalState);
         
        while (unCalculatedNodes.size() != 0 ) {

        	this.currentNode = this.unCalculatedNodes.get(0);
        	if (this.currentNode.stateLayer >= this.breakLevel) {break;}
        	
        	List<GameStateMinimax> nodes = generateNextNodes(this.currentNode);
        	
        	for (GameStateMinimax newNode: nodes)
            {
        		
        		this.gameStateTree.add(newNode);
                this.newLayer.add(newNode);
            }
        	
        	this.unCalculatedNodes.remove(this.currentNode);
        	
        	if(this.unCalculatedNodes.size() == 0 ) {
        		if(this.newLayer.size() != 0 ) {
        		this.unCalculatedNodes.addAll(this.newLayer);
        		this.newLayer =  new ArrayList<GameStateMinimax>();
        		
        		}
        	}
        	
        	
        }
        
        GameStateMinimax selectedNode = gameStateTree.get(10);
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
    
    
    
    
    public List<GameStateMinimax> generateNextNodes(GameStateMinimax gameState) {
    	
    	if(gameState.statePlayer == PLAYER_RED) {
    		this.currentNodePlayer = PLAYER_GREEN;
    	}else {
    		this.currentNodePlayer = PLAYER_RED;
    	}
    	
    	
    	List<GameStateMinimax> nodes = getAvailableMovesArray(gameState.stateArray);
    	
    	if(nodes.size() == 0) {gameState.lastLayer();}  //Burayi bir dusun.
    	
    	return nodes;
    }
    
    public List<GameStateMinimax> getAvailableMovesArray(int [][] gameStateArray)
    {
        List<GameStateMinimax> availableMoves = this.getAvailableForwardMovesArray(gameStateArray);

        //  if player does not have any forward moves left, return backward moves
        if (availableMoves.size() == 0)
        {
            availableMoves = this.getAvailableBackwardMovesArray(gameStateArray);
        }

        return availableMoves;
    }
    
    public List<GameStateMinimax> getAvailableForwardMovesArray(int [][] gameStateArray)
    {
        List<GameStateMinimax> availableForwardMoves = new ArrayList<GameStateMinimax>();

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

    public List<GameStateMinimax> getNextMovesForwardArray(int row, int col,int [][] gameStateArray)
    {
        //  function will returns list of possible Moves
        //the piece can perform
        List<GameStateMinimax> moves = new ArrayList<GameStateMinimax>();
        
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
            	GameStateMinimax newNode = new GameStateMinimax(newState,this.currentNode,getPiecePosition(row,col),forwardPosition);
                
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
        	List<PiecePosition> forwardJumpPositions = getForwardJumpPositions(takenPosition.getRow(),takenPosition.getColumn());
	        pieceStore.remove(takenPosition);
	        for (PiecePosition forwardJumpPosition: forwardJumpPositions)
	        {
	        	PiecePosition middlePosition = getBetweenPosition(takenPosition.getRow(),takenPosition.getColumn(),forwardJumpPosition);
	            //  check if positions are available
	            //available = empty AND within game board
	            if (!isPositionAvailable(middlePosition,gameStateArray) && (isPositionAvailable(forwardJumpPosition,gameStateArray)))
	            {
	                //  create a Move object from a piece, current position
	                //and the position after move
	            	pieceStore.add(forwardJumpPosition);
	            	int [][] newState = createNewStateArray(currentPos.getRow(),currentPos.getColumn(), forwardJumpPosition, gameStateArray);
	            	GameStateMinimax newNode = new GameStateMinimax(newState,this.currentNode,getPiecePosition(row,col),forwardJumpPosition);
	            	if(this.breakLevel == newNode.stateLayer) {newNode.lastLayer();}
	            	moves.add(newNode);
         
	            }
	        }
	     }
        
        
        
        
        
        //-----------------------------------
        /*
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
            	GameStateMinimax newNode = new GameStateMinimax(newState,this.currentNode,getPiecePosition(row,col),forwardJumpPosition);
            	if(this.breakLevel == newNode.stateLayer) {newNode.lastLayer();}
            	moves.add(newNode);
            }
        }*/
        
        //  return list of found moves
        return moves;
    }

    
    public List<GameStateMinimax> getAvailableBackwardMovesArray(int [][] gameStateArray)
    {
    	List<GameStateMinimax> availableBackwardMoves = new ArrayList<GameStateMinimax>();

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
    
    public List<GameStateMinimax> getNextMovesBackwardArray(int row, int col,int [][] gameStateArray)
    {

        //  function will returns list of possible Moves
        //the piece can perform
        List<GameStateMinimax> moves = new ArrayList<GameStateMinimax>();
        
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
            	GameStateMinimax newNode = new GameStateMinimax(newState,this.currentNode,getPiecePosition(row,col),backwardPosition);
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