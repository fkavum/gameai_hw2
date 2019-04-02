package com.cin_damasi.app.chinese_checker_package;

import java.util.ArrayList;
import java.util.List;


import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_NONE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_RED;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_GREEN;

public class GameStateMinimax {


	private static int idCounter = 0;
	public int stateId;
	public int [][] stateArray;	
	public GameStateMinimax previousState;
	
	public PiecePosition prevPos;
	public PiecePosition movedPos;
	
	public int stateLayer;
	public int statePlayer;
	public int score;
	
	public boolean lastLayer = false;
	
	 public GameStateMinimax(int [][] stateArray,GameStateMinimax previousState,PiecePosition prevPos,PiecePosition movedPos) {
		 this.stateArray = stateArray;
		 this.previousState = previousState;
		 this.prevPos = prevPos;
		 this.movedPos = movedPos;
		 this.stateId = GameStateMinimax.idCounter;
		 GameStateMinimax.idCounter = GameStateMinimax.idCounter + 1;
		 
		 if (previousState == null) {
			 this.statePlayer = PLAYER_RED;
			 this.stateLayer = 0;
		 }else {
			 
			 this.stateLayer = this.previousState.stateLayer + 1;
			 
			 if (this.previousState.statePlayer == PLAYER_RED) {
				 this.statePlayer = PLAYER_GREEN;
			 }else if(this.previousState.statePlayer == PLAYER_GREEN) {
				 this.statePlayer = PLAYER_RED;
			 }else {
				 System.out.println("Unexpected error while creating tree.");
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
		
		int point = 0;
		
		this.score = 0;
		
	}
	
	
	
	
	
	
	
}
