package com.cin_damasi.app.chinese_checker_package;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_RED_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_GREEN_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_NO_PIECE;

import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_NONE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_RED;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_GREEN;

import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.GameResult;

public class GameState 
{

    private static long [][] zobrist_table;
    private static Map<Long, Integer> stateVisitCounter = new HashMap<Long, Integer>();


    static {
        //  initialize zobrist table
        GameState.zobrist_table = new long[8*8][2];
        for (int i = 0; i < 64; ++i)
        {
            for (int j = 0; j < 2; ++j)
            {
                zobrist_table[i][j] = Util.getRandomLong();
            }
        }

    }

    private int [][] gameState = new int[8][8];

    public GameState() {
        // initilaze initial state if no arguments constructor
        initializeBoard();
        
    }

    public GameState(int [][] gameState){
        this.gameState = gameState;
    }


    public int[][] getGameStateArray()
    {
        return this.gameState;
    }


    public static long calculateZobristHash(GameState gameState)
    {
        long hashValue = 0;

        int [][] arr = gameState.getGameStateArray();

        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j)
            {
                //  check if there is a square in location
                if (arr[i][j] != PIECE_COLOR_NO_PIECE)
                {
                    int p =  arr[i][j] - 1;  //  value of piece
                    //System.out.println("i:" + i*8+j + ", j:" + p);
                    hashValue = hashValue ^ zobrist_table[i*8+j][p];
                }
            }
        }

        return hashValue;
    }

    //  TO DO: Take hash value as parameter also
    public static void increaseVisitCounter(GameState gameState)
    {
        long hashValue = GameState.calculateZobristHash(gameState);
        Integer currentCounter = stateVisitCounter.get(hashValue);
        if (currentCounter == null)
        {
            stateVisitCounter.put(hashValue, 1);
        }
        else
        {
            stateVisitCounter.put(hashValue, currentCounter+1);
        }
    }

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
    }

    //  creates initial state of the game
    //3 (green) pieces at top left, 3 (red) pieces at bottom right 
    private void initializeBoard(){
        for(int row = 0; row < 8; ++row){
            for(int col = 0; col < 8; ++col){
                if(row < 3 && col < 3){
                    gameState[row][col] = PIECE_COLOR_GREEN_PIECE;
                } else if(row > 4 && col > 4){
                    gameState[row][col] = PIECE_COLOR_RED_PIECE;
                } else {
                    gameState[row][col] = PIECE_COLOR_NO_PIECE;
                }
            }
        }
    }

    //  checks whether given square is in the boundaries of 8x8x board
    public boolean isSquareInBoard(int row, int column)
    {
        return (row<8) && (column<8) && (row>=0) && (column>=0);
    }

    public boolean isSquareInBoard(PiecePosition pos)
    {
        return isSquareInBoard(pos.getRow(), pos.getColumn());
    }


    //  checks whether there is a piece or not in given square
    public boolean isSquareEmpty(int row, int column)
    {
        return gameState[row][column] == PIECE_COLOR_NO_PIECE;
    }
    public boolean isSquareEmpty(PiecePosition pos)
    {
        return isSquareEmpty(pos.getRow(), pos.getColumn());
    }


    //
    public boolean isSquareAvailable(int row, int column)
    {
        return isSquareInBoard(row, column) && isSquareEmpty(row, column);
    }
    public boolean isPositionAvailable(PiecePosition pos)
    {
        return isSquareAvailable(pos.getRow(), pos.getColumn());
    }


    /* Takes two PiecePosition, two board squares, and returns the square
     * between the two by averaging their rows and columns.
     *
     * @param   pos1 - first position
     *          pos2 - second position
     * @return  A position between pos1 and pos2
     */
    public PiecePosition getBetweenPosition(PiecePosition pos1, PiecePosition pos2)
    {
        int row1 = pos1.getRow();
        int column1 = pos1.getColumn();

        int row2 = pos2.getRow();
        int column2 = pos2.getColumn();

        return new PiecePosition((row1+row2)/2, (column1+column2)/2);
    }


    /* A wrapper function for cloning 2d game state array
     *
     * @param   preState - 2d int array (internal state representation)
     * @return  A deep copied version of 2d game state array
     */
    public static int[][] cloneGameState(int [][]preState){
        int [][] myInt = new int[preState.length][];
        for(int i = 0; i < preState.length; i++)
            myInt[i] = preState[i].clone();
        
        return myInt;
    }



    /* Takes a piece, and returns moves it can perform in forward direction.
     * For example, red pieces in game moves to the upper left. For a red piece,
     * this function should return 1 square up and 1 square left if they are empty,
     * and squares that piece can reach by jumping over other pieces.
     *
     * @param   piece - the piece whose moves are calculated
     * @return  A list containing forward Moves that given piece can perform
     */
    public List<Move> getNextMovesForward(Piece piece)
    {
        //  function will returns list of possible Moves
        //the piece can perform
        List<Move> moves = new ArrayList<Move>();
        
        //  get position information of piece
        int i = piece.getPosition().getRow();
        int j = piece.getPosition().getColumn();
        PiecePosition currentPosition = piece.getPosition();

        /*
        //
        //    YOUR CODE HERE
        //
        */
        
        List<PiecePosition> backwardPositionsz = piece.getForwardPositions();
        for (PiecePosition backwardPosition: backwardPositionsz)
        {
            
        	
        	
            if (isPositionAvailable(backwardPosition))
            {
                //  create a Move object from a piece, current position
                //and the position after move
                Move move = new Move(piece, currentPosition, backwardPosition);
                moves.add(move);
            }
        }
        
        
        List<PiecePosition> backwardPositions = piece.getForwardJumpPositions(currentPosition);
        for (PiecePosition backwardPosition: backwardPositions)
        {
        	
        	PiecePosition middlePosition = getBetweenPosition(currentPosition,backwardPosition);
            //  check if positions are available
            //available = empty AND within game board
            if (!isPositionAvailable(middlePosition) && (isPositionAvailable(backwardPosition)))
            {
                //  create a Move object from a piece, current position
                //and the position after move
                Move move = new Move(piece, currentPosition, backwardPosition);
                moves.add(move);
            }
        }
        
        
       
        //  return list of found moves
        return moves;
    }


    /* Takes a piece, and returns moves it can perform in backward direction.
     * A piece can only move backwards if its in the 3x3 destination area
     *
     * @param   piece - the piece whose moves are calculated
     * @return  A list containing backward Moves that given piece can perform
     */
    public List<Move> getNextMovesBackward(Piece piece)
    {
        //  function will returns list of possible Moves
        //the piece can perform
        List<Move> moves = new ArrayList<Move>();
        
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
            if (isPositionAvailable(backwardPosition))
            {
                //  create a Move object from a piece, current position
                //and the position after move
                Move move = new Move(piece, currentPosition, backwardPosition);
                moves.add(move);
            }
        }

        //  return list of found moves
        return moves;
    }



    /* Takes list of all pieces in game, returns a GameState object
     * according to given list of pieces
     *
     * @param   pieces - List containing all pieces in the game
     * @return  created GameState object
     */
    public static GameState createState(List<Piece> pieces)
    {
        int [][] newGameStateArray = new int[8][8];

        //  create an empty game state array
        for(int row = 0; row < 8; ++row){
            for(int col = 0; col < 8; ++col){
                newGameStateArray[row][col] = PIECE_COLOR_NO_PIECE;
            }
        }

        //  put pieces to their corresponding locations in 2d array
        for (Piece piece: pieces)
        {
            PiecePosition pos = piece.getPosition();
            int row = pos.getRow();
            int col = pos.getColumn();
            newGameStateArray[row][col] = piece.getColor();
        }

        //  create a GameState object from constructed 2d state array
        return new GameState(newGameStateArray);
    }


    /* Takes current state of the game and played move
     * and returns the next state.
     *
     * @param   pieces - List containing all pieces in the game
     * @return  created GameState object
     */
    public static GameState createState(GameState previousState, Move playedMove)
    {
        //  copy game state array
        int [][] newStateArray = GameState.cloneGameState(previousState.getGameStateArray());

        //  clear previous position of the piece
        int prevRow = playedMove.getPreviousPosition().getRow();
        int prevColumn = playedMove.getPreviousPosition().getColumn();
        newStateArray[prevRow][prevColumn] = PIECE_COLOR_NO_PIECE;

        //  put piece to new position
        int nextRow = playedMove.getNextPosition().getRow();
        int nextColumn = playedMove.getNextPosition().getColumn();
        newStateArray[nextRow][nextColumn] = playedMove.getPiece().getColor();

        //  create a GameState object from constructed 2d state array
        return new GameState(newStateArray);
    }



    /* Checks whether game is finished or not,
     * returns winning player (or no player) as integer
     *
     * @param   gameState - GameState to check winning player
     * @return  integer corresponding to winning player or no player(0)
     */
    public static GameResult isGameFinished(GameState gameState)
    {
        //  get actual array of GameState object
        int gameStateArray [][] = gameState.getGameStateArray();

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


        // check if game is drawn
        if (GameState.isGameDraw(gameState))
        {
            return GameResult.DRAW;
        }

        //  no player won
        return GameResult.CONTINUE;
    }



    /* Checks whether the piece is goal position, the position
     * where player gathers its pieces to won the game
     *
     * @param   piece - Piece to check location
     */
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
}