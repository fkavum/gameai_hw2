package com.cin_damasi.app.chinese_checker_package;



import java.util.List;
import java.util.ArrayList;
import com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.Direction;



import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_RED_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_GREEN_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_NO_PIECE;


public class Piece
{
    private int color;
    private PiecePosition pos;
    private Player owner;

    private static int idCounter = 0;

    private int id = 0;

    List<Direction> forwardDirections = new ArrayList<Direction>();
    List<Direction> backwardDirections = new ArrayList<Direction>();
    
    //  this constructor is not being used currently
    public Piece(PiecePosition pos, int color, Player owner, 
                 List<Direction> forwardDirs, List<Direction> backwardDirs)
    {
        this.pos = pos;
        this.color = color;
        this.owner = owner;
        this.id = Piece.idCounter;
        Piece.idCounter = Piece.idCounter + 1;
        System.out.println("Piece id counter:" + Piece.idCounter);
    }

    //  (row, column, color, owner)
    //  this constructor is not being used currently
    public Piece(int row, int col, int color, Player owner,
                 List<Direction> forwardDirs, List<Direction> backwardDirs)
    {
        this(new PiecePosition(row, col), color, owner, forwardDirs, backwardDirs);
    }

    public Piece(PiecePosition pos, int color, Player owner)
    {
        this.pos = pos;
        this.color = color;
        this.owner = owner;
        this.id = Piece.idCounter;
        Piece.idCounter = Piece.idCounter + 1;
        //System.out.println("Piece id counter:" + Piece.idCounter);
        this.owner.addPiece(this);
    }

    //  (row, column, color, owner)
    public Piece(int row, int col, int color, Player owner)
    {
        this(new PiecePosition(row, col), color, owner);
    }


    public String toString()
    {
        return "Piece, ID:" + this.getId() + ", position:" + this.getPosition()
                + ", color:" + Integer.toString(this.getColor());
    }

    public void MakeMove(PiecePosition newPosition)
    {
        this.pos = newPosition;
    }

    public void MakeMove(Move move)
    {
        this.pos = move.getNextPosition();
    }

    public int getColor()
    {
        return this.color;
    }

    public PiecePosition getPosition()
    {
        return this.pos;
    }

    public Player getOwner()
    {
        return this.owner;
    }

    public int getId()
    {
        return this.id;
    }


    /* Returns adjacent squares to piece in forward direction
     * based on current position of the piece
     *
     * @return  A list containing squares adjacent to piece in forward direction
     */
    public List<PiecePosition> getForwardPositions()
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();

        int row = this.pos.getRow();
        int column = this.pos.getColumn();

        if (this.color == PIECE_COLOR_RED_PIECE)
        {
            //  red pieces go upper left when moving forward
            poses.add(new PiecePosition(row-1, column));
            poses.add(new PiecePosition(row, column-1));
        }
        else if (this.color == PIECE_COLOR_GREEN_PIECE)
        {
            //  green pieces go bottom right when moving forward
            poses.add(new PiecePosition(row+1, column));
            poses.add(new PiecePosition(row, column+1));
        }
        return poses;
    }

    /* Returns adjacent squares to piece in backward direction
     * based on current position of the piece
     *
     * @return  A list containing squares adjacent to piece in backward direction
     */
    public List<PiecePosition> getBackwardPositions()
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();

        int row = this.pos.getRow();
        int column = this.pos.getColumn();

        if (this.color == PIECE_COLOR_RED_PIECE)
        {
            //  red pieces go bottom right when going backward
            poses.add(new PiecePosition(row+1, column));
            poses.add(new PiecePosition(row, column+1));
            
        }
        else if (this.color == PIECE_COLOR_GREEN_PIECE)
        {
            //  green pieces go upper left when going backward
            poses.add(new PiecePosition(row-1, column));
            poses.add(new PiecePosition(row, column-1));
        }
        return poses;
    }

    /* Returns jump squares of piece in forward direction
     * based on current position of the piece
     *
     * @return  A list containing forward jump squares
     */
    public List<PiecePosition> getForwardJumpPositions()
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();

        int row = this.pos.getRow();
        int column = this.pos.getColumn();

        if (this.color == PIECE_COLOR_RED_PIECE)
        {
            //  red pieces go upper left when moving forward
            poses.add(new PiecePosition(row-2, column));
            poses.add(new PiecePosition(row, column-2));
        }
        else if (this.color == PIECE_COLOR_GREEN_PIECE)
        {
            //  green pieces go bottom right when moving forward
            poses.add(new PiecePosition(row+2, column));
            poses.add(new PiecePosition(row, column+2));
        }

        return poses;
    }

    /* Returns jump squares of piece in forward direction
     * based on GIVEN position for the piece
     *
     * @return  A list containing forward jump squares
     */
    public List<PiecePosition> getForwardJumpPositions(PiecePosition currentPosition)
    {
        List<PiecePosition> poses = new ArrayList<PiecePosition>();

        int row = currentPosition.getRow();
        int column = currentPosition.getColumn();

        if (this.color == PIECE_COLOR_RED_PIECE)
        {
            //  red pieces go upper left when moving forward
            poses.add(new PiecePosition(row-2, column));
            poses.add(new PiecePosition(row, column-2));
        }
        else if (this.color == PIECE_COLOR_GREEN_PIECE)
        {
            //  green pieces go bottom right when moving forward
            poses.add(new PiecePosition(row+2, column));
            poses.add(new PiecePosition(row, column+2));
        }

        return poses;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
        {
            return false;
        }

        if (!Piece.class.isAssignableFrom(other.getClass()))
        {
            return false;
        }

        final Piece otherPiece = (Piece) other;
        return this.id == otherPiece.id;
    }

}
