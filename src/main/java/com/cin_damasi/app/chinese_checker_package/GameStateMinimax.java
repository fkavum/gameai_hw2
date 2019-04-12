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
	//private static int idCounter = 0;
	//public int stateId;
	
	public int [][] stateArray;	
	public GameStateMinimax previousState;
	
	public PiecePosition prevPos;
	public PiecePosition movedPos;
	
	public int stateLayer;
	public int statePlayer;
	public int stateOpponent;
	public int minMaxPlayer;
	public int score;
	
	public boolean lastLayer = false;
	
	 public GameStateMinimax(int [][] stateArray,GameStateMinimax previousState,PiecePosition prevPos,PiecePosition movedPos,int minMaxPlayer) {
		 this.stateArray = stateArray;
		 this.previousState = previousState;
		 this.prevPos = prevPos;
		 this.movedPos = movedPos;
		 this.minMaxPlayer = minMaxPlayer;
		 //this.stateId = GameStateMinimax.idCounter;
		// GameStateMinimax.idCounter = GameStateMinimax.idCounter + 1;
		 
		 if (previousState == null) {
			 
			 if (minMaxPlayer == PLAYER_RED) {
				 this.statePlayer = PLAYER_GREEN;
				 this.stateOpponent = PLAYER_RED;
			 }else if (minMaxPlayer == PLAYER_GREEN) {
				 this.statePlayer = PLAYER_RED;
				 this.stateOpponent = PLAYER_GREEN;
			 }else {
				// System.out.println("Unexpected error while creating tree. StateId: "+this.stateId);
			 }
			 
			 this.stateLayer = 0;
		 }else {
			 
			 this.stateLayer = this.previousState.stateLayer + 1;
			 
			 if (this.previousState.statePlayer == PLAYER_RED) {
				 this.statePlayer = PLAYER_GREEN;
				 this.stateOpponent = PLAYER_RED;
			 }else if(this.previousState.statePlayer == PLAYER_GREEN) {
				 this.statePlayer = PLAYER_RED;
				 this.stateOpponent = PLAYER_GREEN;
			 }else {
				// System.out.println("Unexpected error while creating tree. StateId: "+this.stateId);
			 }
			 
		 }
	       
	    
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

    /*
   
    
    
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
