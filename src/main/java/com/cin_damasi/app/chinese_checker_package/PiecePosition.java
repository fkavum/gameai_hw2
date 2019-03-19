package com.cin_damasi.app.chinese_checker_package;

public class PiecePosition {
    private int row;
    private int column;

    public PiecePosition(int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
        {
            return false;
        }

        if (!PiecePosition.class.isAssignableFrom(other.getClass()))
        {
            return false;
        }

        final PiecePosition otherPiecePosition = (PiecePosition) other;

    
        if (this.row == otherPiecePosition.row && this.column == otherPiecePosition.column)
            return true;
        else
            return false;

    }

    public String toString()
    {
        return "PiecePosition, row:" + Integer.toString(this.getRow())
                + ", column:" + Integer.toString(this.getColumn());
    }
}