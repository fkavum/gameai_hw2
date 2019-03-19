package com.cin_damasi.app.chinese_checker_package;

public final class ProjectDefinitions
{

    //  Prevent construction
    private ProjectDefinitions()
    {

    }


    /*
    PIECE COLORS
    */
    public static final int PIECE_COLOR_NO_PIECE = 0;
    public static final int PIECE_COLOR_RED_PIECE = 1;
    public static final int PIECE_COLOR_GREEN_PIECE = 2;

    /*
    PLAYERS
    */
    public static final int PLAYER_NONE = 0;
    public static final int PLAYER_RED = 1;
    public static final int PLAYER_GREEN = 2;


    /*
    DIRECTIONS
    */
    public enum Direction
    {
        RIGHT,
        UP,
        LEFT,
        DOWN
    }

    /*
    GAME RESULT
    */
    public enum GameResult
    {
        RED_WON,
        GREEN_WON,
        CONTINUE,
        DRAW
    }
}