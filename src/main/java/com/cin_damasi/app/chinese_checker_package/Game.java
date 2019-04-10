package com.cin_damasi.app.chinese_checker_package;


import java.util.List;
import java.util.ArrayList;

import javax.swing.JFrame;




//  piece color constants
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_RED_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_GREEN_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_NO_PIECE;

//  player color constants
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_NONE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_RED;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_GREEN;

import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.GameResult;


public class Game implements PieceListener, SquareListener, Runnable
{
    //  class that handles GUI stuff
    private BoardCreator board;

    //  game window, outer JFrame
    private JFrame gameWindow;

    //  class that keeps (current) game state
    private GameState currentState;

    
    //  player information
    private Player playerRed;
    private Player playerGreen;
    private Player currentPlayer;

    //  currently clicked piece;
    private Piece selectedPiece = null;
    private List<Move> availableMoves = new ArrayList<Move>();
    private Move playedMove = null;

    //  list of pieces in the game
    public List<Piece> pieces = new ArrayList<Piece>();


    private void initializePlayers()
    {
        
        this.playerRed = new HumanPlayer(PLAYER_RED);
        //this.playerRed = new ComputerPlayerRandom(PLAYER_RED);
        //this.playerRed = new ComputerPlayerMinimax(PLAYER_RED);


        //this.playerGreen = new HumanPlayer(PLAYER_GREEN);
        //this.playerGreen = new ComputerPlayerRandom(PLAYER_GREEN);
        this.playerGreen = new ComputerPlayerMinimax(PLAYER_GREEN);


        GameStateMinimax.initializeDestinations();     // ---->  Added.
        //  red player starts game
        this.currentPlayer = this.playerRed;
        this.board.changeLabel("RED player's turn");
    }

    public Game()
    {
        //  create game board
        this.board = new BoardCreator(this);

        //  create container window
        this.gameWindow = new JFrame("Corners");
        this.gameWindow.add(this.board.getGui());
        this.gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.gameWindow.setLocationByPlatform(true);
        this.gameWindow.setResizable(false);

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        this.gameWindow.pack();
        // ensures the minimum size is enforced.
        this.gameWindow.setMinimumSize(this.gameWindow.getSize());
        this.gameWindow.setVisible(true);
        
        //  initial game state
        this.currentState = new GameState();


        //  create players
        this.initializePlayers();
        
        
        
        //  create pieces from state
        this.createPieces();
        System.out.println("Player " + PLAYER_RED + " has " + this.playerRed.getPieceCount() + " pieces.");
        System.out.println("Player " + PLAYER_GREEN + " has " + this.playerGreen.getPieceCount() + " pieces.");
        
        
        


        //  render board
        //  TO DO: Game class should only call something
        //like this.board.render()
        this.Redraw();

        
        //  start playing thread
        Thread gameThread = new Thread(this);
        gameThread.start();
    }


    public void run()
    {
        //  increase visit count of initial state
        GameState.increaseVisitCounter(this.currentState);
        
        //  main game loop
        boolean gameFinished = false;
        while (!gameFinished)
        {
            //  first, check if game is finished
            //  TO DO: currently causes infinite message loop
            GameResult result = GameState.isGameFinished(this.currentState);
            if (result != GameResult.CONTINUE)
            {
                if (result == GameResult.RED_WON)
                {
                    //  red player won
                    System.out.println("RED WON");
                    Util.infoBox("GAME FINISHED", "RED PLAYER WON");
                    gameFinished = true;
                }
                else if (result == GameResult.GREEN_WON)
                {
                    //  green player won
                    System.out.println("GREEN WON");
                    Util.infoBox("GAME FINISHED", "GREEN PLAYER WON");
                    gameFinished = true;
                }
                else if (result == GameResult.DRAW)
                {
                    //  game draw
                    System.out.println("DRAW");
                    Util.infoBox("GAME FINISHED", "DRAW");
                    gameFinished = true;
                }
            }

            //  check if player made a move
            if (this.playedMove != null)
            {
                //  player made its move
                //  BURDA HAMLE YAPILIYO
                System.out.println("MOVE YAPILDI");
                System.out.println(this.currentState.getGameStateArray()[4][5]);
                
                Piece movedPiece = this.playedMove.getPiece();
                System.out.println("Piece position before move:" + movedPiece.getPosition());
                movedPiece.MakeMove(this.playedMove);
                System.out.println("Piece position after move:" + movedPiece.getPosition());
                
                //  update game state
                this.currentState = GameState.createState(pieces);

                //  update visited information of state
                GameState.increaseVisitCounter(this.currentState);

                //  clear current move information
                this.availableMoves = null;
                this.playedMove = null;
                this.selectedPiece = null;

                //  redraw game
                this.Redraw();
               
                //  switch to next player
                this.SwitchTurn();
            }
            else
            {
                //  player still considering its move
                if (this.currentPlayer instanceof HumanPlayer)
                {
                    //  human player
                    //wait for user input to make move
                }
                else if (this.currentPlayer instanceof ComputerPlayer)
                {
                    //  computer player
                    //wait for search/function to finish
                    this.playedMove = this.currentPlayer.getMove(this.currentState);
                }
                else if (this.currentPlayer instanceof ComputerPlayerMinimax)
                {
                    //  computer player
                    //wait for search/function to finish
                    this.playedMove = this.currentPlayer.getMove(this.currentState);
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public void createPieces()
    {
        //  loop through game state array to determine which pieces
        //are going to be created
        for (int row = 0; row < 8; ++row)
        {
            for (int col = 0; col < 8; ++col)
            {
                int color = this.currentState.getGameStateArray()[row][col];
                if (color != PIECE_COLOR_NO_PIECE)
                {
                    //  create a new piece
                    Player owner = null;
                    if (color == PIECE_COLOR_RED_PIECE)
                    {
                        owner = this.playerRed;
                    }
                    else if (color == PIECE_COLOR_GREEN_PIECE)
                    {
                        owner = this.playerGreen;
                    }
                    Piece piece = new Piece(row, col, color, owner);
                    this.pieces.add(piece);
                }
            }
        }

        //  create pieces with GUI
        this.board.createPiecesGUI(this.pieces);
    }

    
    
    @Override
    public void pieceClicked(Piece piece)
    {
        System.out.println("Clicked piece:" + piece);

        //  if currently playing player is a HumanPlayer
        if (this.currentPlayer instanceof HumanPlayer)
        {
            //  check if player selected one of its pieces
            this.selectedPiece = null;
            if (piece.getOwner() == this.currentPlayer)
            {
                this.selectedPiece = piece;

                List<Move> playerForwardMoves = this.currentPlayer.getAvailableForwardMoves(this.currentState);
                
                //  check if player has forward move
                if (playerForwardMoves.size() != 0)
                {
                    //  player has available forward move
                    this.availableMoves = this.currentState.getNextMovesForward(piece);
                }
                else
                {
                    //  player does not have any forward move
                    //return available backward moves of piece
                    this.availableMoves = this.currentState.getNextMovesBackward(piece);
                }
                this.Redraw();
            }
            else
            {
                //  player clicked opponent's piece
                this.selectedPiece = null;
                this.availableMoves = null;
                this.Redraw();
            }
        }

        if (this.selectedPiece == null)
        {
            System.out.println("Selected piece  null");
        }
    }

    @Override
    public void squareClicked(int row, int column)
    {
        PiecePosition pos = new PiecePosition(row, column);
        System.out.println("Square clicked:" + pos);

        //  if currently playing player is a HumanPlayer
        if (this.currentPlayer instanceof HumanPlayer)
        {
            //  if player currently has a selected piece
            if (this.selectedPiece != null)
            {
                //  check if player made a move with its selected piece
                System.out.println("Checking if move is one of moves");
                PiecePosition clickedPosition = new PiecePosition(row, column);
                boolean movePlayed = false;
                for (Move move: this.availableMoves)
                {
                    PiecePosition movePosition = move.getNextPosition();
                    if (movePosition.equals(clickedPosition))
                    {
                        //  notify game loop thread about move
                        this.playedMove = new Move(this.selectedPiece, move.getPreviousPosition(), move.getNextPosition());
                        movePlayed = true;
                    }
                }

                //  if no move is made, player may clicked to 
                //clear its selected piece
                if (!movePlayed)
                {
                    this.selectedPiece = null;
                    this.availableMoves = null;
                    this.Redraw();
                }
            }
        }
        
    }

    public void Redraw()
    {
        //System.out.println("Redraw start");
        
        this.board.drawBoardSquares();
        this.board.drawPieces();

        if (this.availableMoves != null)
        {
            this.board.drawTransparentPieces(availableMoves);
        }
        this.board.repaint();
        //System.out.println("Redraw end");
    }

    public void SwitchTurn()
    {
        if (this.currentPlayer == this.playerGreen)
        {
            this.currentPlayer = this.playerRed;
            this.board.changeLabel("RED player's turn");
        }
        else
        {
            this.currentPlayer = this.playerGreen;
            this.board.changeLabel("GREEN player's turn");
        }
    }


}