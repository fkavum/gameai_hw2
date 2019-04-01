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
	
	public int stateLayer;
	public int statePlayer;
	public int score;
	
	 public GameStateMinimax(int [][] stateArray,GameStateMinimax previousState) {
		 this.stateArray = stateArray;
		 this.previousState = previousState;
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
	
	
	private void calculateScore() {
		
		
		this.score = 0;
		
	}
	
	
	
	
	
	
	
}
