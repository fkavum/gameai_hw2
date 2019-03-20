package com.cin_damasi.app.chinese_checker_package;

import java.util.Random;
import java.util.List;

class ComputerPlayerRandom extends ComputerPlayer
{
    public ComputerPlayerRandom(int whichPlayer)
    {
        super(whichPlayer);
        ;
    }

    
    public Move getMove(GameState gameState)
    {
        //  get possible moves the player can make
        List<Move> availableMoves = this.getAvailableMoves(gameState);
        
        System.out.println((availableMoves.toArray()[0]));
        //  select a random move
        Random random = new Random();
        int randomInt = random.nextInt(availableMoves.size());
        Move randomMove = availableMoves.get(randomInt);

        return randomMove;
    }
}