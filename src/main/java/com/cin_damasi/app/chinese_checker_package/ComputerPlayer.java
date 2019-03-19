package com.cin_damasi.app.chinese_checker_package;

abstract class ComputerPlayer extends Player
{
    public ComputerPlayer(int whichPlayer)
    {
        super(whichPlayer);
        ;
    }

    public abstract Move getMove(GameState state);
}