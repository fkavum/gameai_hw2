package com.cin_damasi.app.chinese_checker_package;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.util.List;
import java.util.ArrayList;


import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_RED_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_GREEN_PIECE;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PIECE_COLOR_NO_PIECE;


public class BoardCreator extends JFrame implements ActionListener {

    //  gui
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    
    //  chessboard
    private JPanel chessBoard;

    //  8x8 board buttons
    private JButton[][] chessBoardSquares = new JButton[8][8];

    private final JLabel message = new JLabel(
            "Chess Champ is ready to play!");
    private static final String COLS = "ABCDEFGH";

    
    //  Images for red and green pieces
    private Image redButtonImage = getImage("images/kirmizi_tas.png");
    private Image greenButtonImage = getImage("images/yesil_tas.png");

    

    //  list of pieces (including UI components)
    public List<PieceGUI> piecesGUI = new ArrayList<PieceGUI>();
    

    //  Instance of game class
    Game game = null;

    public GameState currentState;

    public BoardCreator() {
        initializeGui();
    }

    public BoardCreator(Game game)
    {
        this.game = game;
        initializeGui();
    }

    public Image getImage(String path){
        final URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    class BoardSquare extends JButton
    {
        public BoardSquare(String s)
        {
            super(s);
        }

        protected void paintComponent(Graphics g)
        {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }


    public final void initializeGui() {
        // set up the main GUI

        //  EmptyBorder(top, left, bottom, right)
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));



        //  Create toolbar
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("TODO")); // TODO - add functionality NEW!
        tools.add(new JButton("TODO")); // TODO - add functionality SAVE!
        tools.add(new JButton("TODO")); // TODO - add functionality RESTORE!
        tools.addSeparator();
        tools.add(new JButton("TODO")); // TODO - add functionality RESIGN!
        tools.addSeparator();
        tools.add(message);

        //  why this exists?
        //gui.add(new JLabel("?"), BorderLayout.LINE_START);
        gui.add(new JLabel(" "), BorderLayout.LINE_START);

        //  GridLayout(int rows, int cols)
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);

        // create the chess board squares
        for (int row = 0; row < chessBoardSquares.length; ++row)
        {
            for (int col = 0; col < chessBoardSquares[row].length; ++col)
            {
                //chessBoardSquares[row][col] = new JButton("");
                chessBoardSquares[row][col] = new BoardSquare("");
            }
        }
        
        //  draw squares
        this.drawBoardSquares();
        //this.drawPieces();

        //fill the chess board

        //  top left (where square letters meet square numbers)
        chessBoard.add(new JLabel(""));

        // fill the top row (letters ABCDEFGH)
        for (int col = 0; col < 8; ++col)
        {
            //  JLabel(String text, int horizontalAlignment)
            chessBoard.add(new JLabel(COLS.substring(col, col+1), SwingConstants.CENTER));
        }

        /*
        for (int row = 0; row < 8; ++row)
        {
            for (int col = 0; col < 8; ++col)
            {
                if (col == 0)
                {
                    chessBoard.add(new JLabel("" + (row+1)), SwingConstants.CENTER);
                }

                chessBoard.add(chessBoardSquares[row][col]);
                        
                //  add event listener
                chessBoardSquares[row][col].addActionListener(this);
                chessBoardSquares[row][col].putClientProperty("column", col);
                chessBoardSquares[row][col].putClientProperty("row", row);
            }
        }
        */
    

        // fill the black non-pawn piece row
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                switch (col) {
                    case 0:
                        chessBoard.add(new JLabel("" + (row + 1),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[row][col]);
                        
                        //  add event listener
                        chessBoardSquares[row][col].addActionListener(this);
                        chessBoardSquares[row][col].putClientProperty("column", col);
                        chessBoardSquares[row][col].putClientProperty("row", row);
                }
            }
        }

        //this.putBlackPiece(1, 1);
        //this.putgreenPiece(1, 0);
       // this.putPiece(4, 5, 1);
        //this.putPiece(6, 2, 2);


        
        chessBoard.addComponentListener(new ComponentAdapter() 
        {
            public void componentResized(ComponentEvent componentEvent)
            {
                //System.out.println("componentResized()");
                for (PieceGUI piece: piecesGUI)
                {
                    piece.ResizeWithParent();
                }
            }
        });


    }

    
    

    //  board onClick event
    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton btn = (JButton) ae.getSource();
        int row = (int) btn.getClientProperty("row");
        int column = (int) btn.getClientProperty("column");
        //System.out.println("clicked row " + row + ", column " + column);
        this.game.squareClicked(row, column);
    }
 

    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JComponent getGui() {
        return gui;
    }



    public void drawBoardSquares()
    {
        //  draw squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int row = 0; row < chessBoardSquares.length; ++row)
        {
            for (int col = 0; col < chessBoardSquares[row].length; ++col)
            {
                JButton b = chessBoardSquares[row][col];
                b.setMargin(buttonMargin);

                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                //  bu satir bisey yapiyo mu ki?
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);

                boolean bothOdd = (col % 2 == 1) && (row % 2 == 1);
                boolean bothEven = (col % 2 == 0) && (row % 2 == 0);
                if ( bothOdd || bothEven )
                {
                    b.setBackground(Color.WHITE);
                }
                else
                {
                    b.setBackground(Color.BLACK);
                }
                chessBoardSquares[row][col] = b;
            }
        }
    }
    
    public void drawPieces()
    {
        int counter = 0;
        for (PieceGUI piece: this.piecesGUI)
        {
            PiecePosition pos = piece.getPosition();
            int row = pos.getRow();
            int col = pos.getColumn();

            JButton oldParentButton = piece.getParentButton();
            JButton newParentButton = chessBoardSquares[row][col];
            
            if (oldParentButton != newParentButton)
            {
                oldParentButton.repaint();
                System.out.println("Piece changed location, " + piece.getPiece());
            }

            counter += 1;

            piece.setParentButton(newParentButton);

            
            newParentButton.repaint();
            piece.drawToParentComponent(newParentButton);
        }

        System.out.println("Number of drawn pieces:" + counter);
    }

    public Piece findPiece(PiecePosition pos)
    {
        PieceGUI foundPiece = null;
        for (PieceGUI piece: piecesGUI)
        {
            //System.out.println(piece.getPosition());
            if (piece.getPosition().equals(pos))
            {
                foundPiece = piece;
                break;
            }
        }
        return foundPiece.getPiece();
    }


    public void drawTransparentPieces(List<Move> moves)
    {
        for (Move move: moves)
        {
            Piece p = move.getPiece();
            PiecePosition nextPos = move.getNextPosition();
            int col = nextPos.getColumn();
            int row = nextPos.getRow();
            chessBoardSquares[row][col].setOpaque(false);
            if (p.getColor() == PIECE_COLOR_RED_PIECE)
            {
                chessBoardSquares[row][col].setBackground(new Color(255, 0, 0, 128));
            }
            else if (p.getColor() == PIECE_COLOR_GREEN_PIECE)
            {
                chessBoardSquares[row][col].setBackground(new Color(0, 0, 255, 128));
            }
        }
        //this.chessBoard.repaint();
    }

    //  takes a Piece and returns a PieceGUI
    public PieceGUI putPiece(Piece piece)
    {
        int row = piece.getPosition().getRow();
        int col = piece.getPosition().getColumn();
        int color = piece.getColor();
        PieceGUI newPiece = null;
        if (color != PIECE_COLOR_NO_PIECE)
        {
            Image pieceImage;
            if (color == PIECE_COLOR_RED_PIECE)
            {
                pieceImage = this.redButtonImage;
            }
            else if (color == PIECE_COLOR_GREEN_PIECE)
            {
                pieceImage = this.greenButtonImage;
            }
            else
            {
                //  error ?
                System.out.println("Error in " + this.getClass().getSimpleName() + " - invalid piece color");
                return null;
            }
            newPiece = new PieceGUI(chessBoardSquares[row][col], piece, pieceImage);
        }
        return newPiece;
    }

    public void createPiecesGUI(List<Piece> pieces)
    {
        System.out.println("createPiecesGUI start");
        for (Piece piece: pieces)
        {
            PieceGUI pieceGUI = this.putPiece(piece);
            this.piecesGUI.add(pieceGUI);
            
            pieceGUI.getButton().addActionListener(new PieceListenerClass(piece));
        }
        System.out.println("createPiecesGUI end");
    }


    //  inner class for piece ActionListener
    class PieceListenerClass implements ActionListener
    {
        private Piece piece;

        public PieceListenerClass(Piece piece)
        {
            this.piece = piece;
        }

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            JButton btn = (JButton) ae.getSource();

            //this.game.pieceClicked(this.piece);
            BoardCreator.this.game.pieceClicked(piece);
        }
    }


    public void changeLabel(String s)
    {
        message.setText(s);
    }

}
