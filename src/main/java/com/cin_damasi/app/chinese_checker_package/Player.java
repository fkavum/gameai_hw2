package com.cin_damasi.app.chinese_checker_package;

import java.util.List;
import java.util.ArrayList;

abstract class Player
{
    //  PLAYER_RED or PLAYER_GREEN
    int whichPlayer;


    //  player has a list of its pieces in the game
    List<Piece> pieces = new ArrayList<Piece>();


    /* Takes a GameState, and calculates the move to be performed by the player.
     * This function MUST be implemented by child classes that extends Abstract Player class.
     * 
     * @param   gameState - state of the game
     * @return  A single Move that player will perform
     */
    public abstract Move getMove(GameState state);



    public Player(int whichPlayer)
    {
        this.whichPlayer = whichPlayer;
    }


    /* Adds a piece to the list of pieces owned by player
     *
     * @param   piece - the piece that will be assigned to player
     */
    public void addPiece(Piece piece)
    {
        this.pieces.add(piece);
    }


    /* Returns list of pieces the player has
     *
     * @return  a list containing all the pieces player has
     */
    public List<Piece> getPieces()
    {
        return this.pieces;
    }


    /* Returns number of pieces the player has
     *
     * @return  an integer representing number of pieces player has
     */
    public int getPieceCount()
    {
        return this.pieces.size();
    }



    /* Takes a GameState, and returns all forward moves the player can perform
     * with its pieces.
     * 
     * @param   gameState - state of the game
     * @return  A list containing forward Moves that player can perform with all its pieces
     */
    public List<Move> getAvailableForwardMoves(GameState gameState)
    {
        List<Move> availableForwardMoves = new ArrayList<Move>();

        for (Piece piece: this.pieces)
        {
            availableForwardMoves.addAll(gameState.getNextMovesForward(piece));
        }
        return availableForwardMoves;
    }



    /* Takes a GameState, and returns all backward moves the player can perform
     * with its pieces.
     * 
     * @param   gameState - state of the game
     * @return  A list containing backward Moves that player can perform with all its pieces
     */
    public List<Move> getAvailableBackwardMoves(GameState gameState)
    {
        List<Move> availableBackwardMoves = new ArrayList<Move>();

        for (Piece piece: this.pieces)
        {
            availableBackwardMoves.addAll(gameState.getNextMovesBackward(piece));
        }
        return availableBackwardMoves;
    }



    /* Takes a GameState, and returns all moves the player can perform
     * with its pieces. This function is a wrapper for getAvailableForwardMoves
     * and getAvailableBackwardMoves. It first checks whether player has any
     * available perform moves and returns them if there is any. If there is no
     * available forward moves, it returns backward moves.
     * 
     * @param   gameState - state of the game
     * @return  A list containing all Moves that player can perform with all its pieces
     */
    public List<Move> getAvailableMoves(GameState gameState)
    {
        List<Move> availableMoves = this.getAvailableForwardMoves(gameState);

        //  if player does not have any forward moves left, return backward moves
        if (availableMoves.size() == 0)
        {
            availableMoves = this.getAvailableBackwardMoves(gameState);
        }

        return availableMoves;
    }
}