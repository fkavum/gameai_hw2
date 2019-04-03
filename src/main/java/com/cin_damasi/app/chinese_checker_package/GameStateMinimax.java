package com.cin_damasi.app.chinese_checker_package;

import java.util.ArrayList;
import java.util.List;

import com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.GameResult;

import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_NONE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_RED;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_GREEN_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_RED_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_GREEN;

public class GameStateMinimax {


	public static List<int[]> redDestList = new ArrayList<int[]>(); 
	public static List<int[]> greenDestList = new ArrayList<int[]>();
	private static int idCounter = 0;
	public int stateId;
	
	public int [][] stateArray;	
	public GameStateMinimax previousState;
	
	public PiecePosition prevPos;
	public PiecePosition movedPos;
	
	public int stateLayer;
	public int statePlayer;
	public int minMaxPlayer;
	public int score;
	
	public boolean lastLayer = false;
	
	 public GameStateMinimax(int [][] stateArray,GameStateMinimax previousState,PiecePosition prevPos,PiecePosition movedPos,int minMaxPlayer) {
		 this.stateArray = stateArray;
		 this.previousState = previousState;
		 this.prevPos = prevPos;
		 this.movedPos = movedPos;
		 this.minMaxPlayer = minMaxPlayer;
		 this.stateId = GameStateMinimax.idCounter;
		 GameStateMinimax.idCounter = GameStateMinimax.idCounter + 1;
		 
		 if (previousState == null) {
			 
			 if (minMaxPlayer == PLAYER_RED) {
				 this.statePlayer = PLAYER_GREEN;
			 }else if (minMaxPlayer == PLAYER_GREEN) {
				 this.statePlayer = PLAYER_RED;
			 }else {
				 System.out.println("Unexpected error while creating tree. StateId: "+this.stateId);
			 }
			 
			 this.stateLayer = 0;
		 }else {
			 
			 this.stateLayer = this.previousState.stateLayer + 1;
			 
			 if (this.previousState.statePlayer == PLAYER_RED) {
				 this.statePlayer = PLAYER_GREEN;
			 }else if(this.previousState.statePlayer == PLAYER_GREEN) {
				 this.statePlayer = PLAYER_RED;
			 }else {
				 System.out.println("Unexpected error while creating tree. StateId: "+this.stateId);
			 }
			 
		 }
	        // initilaze initial state if no arguments constructor
	        calculateScore();
	    
	    }
	
	 public static int[][] cloneGameState(int [][]preState){
	        int [][] myInt = new int[preState.length][];
	        for(int i = 0; i < preState.length; i++)
	            myInt[i] = preState[i].clone();
	        
	        return myInt;
	    }
	 
	 
	 public void lastLayer() {
		 this.lastLayer = true;
	 }
	 
	private void calculateScore() {
		
		int score = 0;
		int tempScore = 0;
		int redCounter = 0; //00-01-02 // 10-11-12 // 20-21-22
		int greenCounter = 0; //55-56-57//65-66-67//75-76-77
		
		 for (int row = 0; row < 8; ++row)
	        {
	            for (int col = 0; col < 8; ++col)
	            {        	
	            	switch (this.stateArray[row][col]) {
	            	case 0:
	            		break;
	            	case PLAYER_RED:
	            		tempScore =  manhattanDist(row,col,PLAYER_RED,redCounter); //ne kadar kücükse o kadar iyi red için. 1-5
	            		if (this.minMaxPlayer == PLAYER_RED) {
	            			score -= tempScore;
	            		}else {
	            			score += tempScore;
	            		}
	            		redCounter +=1;
	            		break;
	            	case PLAYER_GREEN:
	            		tempScore =  manhattanDist(row,col,PLAYER_GREEN,greenCounter);
	            		if (this.minMaxPlayer == PLAYER_RED) {
	            			score += tempScore;
	            		}else {
	            			score -= tempScore;
	            		}
	            		greenCounter +=1;
	            		break;
	            	default:
	            		System.out.println("StateId:" + this.stateId + " has unexpected piece");
	            	}
	            }
	        }
		
		this.score = score;
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void initializeDestinations() {
		 //00-01-02 // 10-11-12 // 20-21-22
		for (int i=0 ; i<3 ; ++i) {
			for (int j=0 ; j<3 ; ++j) {
				int[] dest = {i,j};
				GameStateMinimax.redDestList.add(dest);
			}
		}
		
		for (int i=5 ; i<8 ; ++i) {
			for (int j=5 ; j<8 ; ++j) {
				int[] dest = {i,j};
				GameStateMinimax.greenDestList.add(dest);
			}
		}
		
		
	}
	
	/*
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
    }*/

    
    public GameResult isGameFinished(int [][] gameStateArray)
    {
        
        //  check if all red pieces at top left
        int redCounter = 0;
        for (int row = 0; row < 3; ++row)
        {
            for (int col = 0; col < 3; ++col)
            {
                if (gameStateArray[row][col] == PIECE_COLOR_RED_PIECE)
                {
                    ++redCounter;
                }
            }
        }
        //  return red player as winning player
        if (redCounter == 9)
        {
            return GameResult.RED_WON;
        }


        //  check if all green pieces at bottom right
        int greenCounter = 0;
        for (int row = 5; row < 8; ++row)
        {
            for (int col = 5; col < 8; ++col)
            {
                if (gameStateArray[row][col] == PIECE_COLOR_GREEN_PIECE)
                {
                    ++greenCounter;
                }
            }
        }
        //  return green player as winning player
        if (greenCounter == 9)
        {
            return GameResult.GREEN_WON;
        }

/*
        // check if game is drawn
        if (GameState.isGameDraw(gameStateArray))
        {
            return GameResult.DRAW;
        }*/

        //  no player won
        return GameResult.CONTINUE;
    }

    
    
    
	/*
    //  TO DO: Take hash value as parameter also
    public static boolean isGameDraw(GameState currentState)
    {
        long hashValue = GameState.calculateZobristHash(currentState);
        Integer currentCounter = stateVisitCounter.get(hashValue);
        //System.out.println("Visit counter of state:" + currentCounter);
        if (currentCounter >= 3)
        {
            return true;
        }
        else
        {
            return false;
        }
    }*/
	
	
	
	
}
