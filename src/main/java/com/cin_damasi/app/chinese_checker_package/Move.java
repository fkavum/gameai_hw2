package com.cin_damasi.app.chinese_checker_package;

public class Move {
    private Piece movedPiece;
    private PiecePosition previousPosition;
    private PiecePosition nextPosition;
    public Move(Piece piece, PiecePosition previousPosition, PiecePosition nextPosition){
        this.movedPiece = piece;
        this.previousPosition = previousPosition;
        this.nextPosition = nextPosition;
    }

    public Move(PiecePosition previousPosition, PiecePosition nextPosition){
        this.movedPiece = null;
        this.previousPosition = previousPosition;
        this.nextPosition = nextPosition;
    }


    public Piece getPiece()
    {
        return this.movedPiece;
    }

    public PiecePosition getPreviousPosition()
    {
        return this.previousPosition;
    }

    public PiecePosition getNextPosition()
    {
        return this.nextPosition;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
        {
            return false;
        }

        if (!Move.class.isAssignableFrom(other.getClass()))
        {
            return false;
        }

        final Move otherMove = (Move) other;

        if (this.movedPiece.equals(otherMove.movedPiece) 
            && this.previousPosition.equals(otherMove.previousPosition)
            && this.nextPosition.equals(otherMove.nextPosition))
            return true;
        else
            return false;

    }


    public String toString()
    {
        return "Move, piece:" + this.movedPiece
                + ", prevPos:" + this.previousPosition
                + ", nextPos:" + this.nextPosition;
    }


}